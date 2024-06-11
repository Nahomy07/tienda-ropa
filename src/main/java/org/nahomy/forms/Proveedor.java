package org.nahomy.forms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.entity.Cliente01;
import org.nahomy.entity.Producto01;
import org.nahomy.entity.Proveedor01;
import org.nahomy.services.GenericServiceImpl;
import org.nahomy.services.IGenericService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Proveedor extends JInternalFrame
{
    private DefaultTableModel tablaModelo;
    static Proveedor miProveedor;

    public Proveedor()
    {
        super("Proveedor", true, true, true, true);
        inicializarProveedor();
        miProveedor = this;
    }

    private void inicializarProveedor()
    {
        setSize(745, 745);
        setToolTipText("Datos del proveedor");
        setName("Proveedores");
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(131,177,248));
        getContentPane().add(panel1, BorderLayout.CENTER);
        //getContentPane().setPreferredSize(new Dimension(400, 400));

        //nuevo
        panel1.setLayout(new GridLayout(4,2,5,5));
        panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //panel de la tabla
        tablaModelo = new DefaultTableModel();
        tablaModelo.addColumn("id");
        tablaModelo.addColumn("Nombre");
        tablaModelo.addColumn("Apellido");
        tablaModelo.addColumn("Telefono");
        JTable tabla = new JTable(tablaModelo);

        JScrollPane tablaScrollPane = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel label = new JLabel("Nombre");
        label.setBounds(0, 40, 60, 60);
        JTextField nombre = new JTextField(30);
        nombre.setBounds(10, 60, 140, 25);
        nombre.setOpaque(false);
        label.setForeground(new Color(250,249,249));
        nombre.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label1 = new JLabel("Apellido");
        label1.setBounds(0, 40, 60, 60);
        JTextField apellido = new JTextField(30);
        apellido.setBounds(10, 60, 140, 25);
        apellido.setOpaque(false);
        label1.setForeground(new Color(250, 249, 249));
        apellido.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label2 = new JLabel("Telefono");
        label2.setBounds(0, 40, 60, 60);
        JTextField telefono = new JTextField(30);
        telefono.setBounds(10, 60, 140, 25);
        telefono.setOpaque(false);
        label2.setForeground(new Color(250, 249, 249));
        telefono.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JButton enviarButton = new JButton("Guardar");
        enviarButton.setBounds(100, 180, 80, 40);
        enviarButton.setForeground(new Color(131, 177, 248));
        addFilas(tablaModelo);
        enviarButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String nombretxt = nombre.getText();
                String apellidotxt = apellido.getText();
                String telefonotxt = telefono.getText();
                Proveedor01 proveedor01 = new Proveedor01(nombretxt, apellidotxt, telefonotxt);
                saveProveedor01(proveedor01);
                addFilas(tablaModelo);
                List<Proveedor01> proveedor01s = getProveedor();
                proveedor01s.forEach(System.out::println);
            }
        });

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBounds(100,180,80,40);
        actualizarButton.setForeground(new Color(131, 177, 248));
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List <Proveedor01> proveedores = getProveedor01();
                int i = tabla.getSelectedRow();
                boolean entradaValida = true;
                if(i < 0)
                {
                    JOptionPane.showMessageDialog(null,"Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    String nombre1 = nombre.getText().trim();
                    String apellido1 = apellido.getText();
                    String telefono1 = telefono.getText().trim();
                    String id = (String) tabla.getValueAt(i, 0);
                    if (nombre1.isEmpty()|| apellido1.isEmpty()||telefono1.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                        entradaValida = false;
                    }
                    if(entradaValida){
                        for (Proveedor01 proveedor : proveedores) {
                            if (id.equalsIgnoreCase(proveedor.getNombre())) {
                                proveedor.setNombre(nombre1);
                                proveedor.setApellido(apellido1);
                                proveedor.setTelefono(telefono1);
                                updateProveedor01(proveedor);
                                addFilas(tablaModelo);
                                List<Proveedor01> proveedor01s = getProveedor();
                                proveedor01s.forEach(System.out::println);
                            }
                        }
                    }
                }
            }
        });

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.setBounds(100,180,80,40);
        eliminarButton.setForeground(new Color(131, 177, 248));
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List <Proveedor01> proveedores = getProveedor01();
                int i = tabla.getSelectedRow();
                if (i < 0){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int id = Integer.parseInt( tabla.getValueAt(i, 0).toString());
                    for (Proveedor01 proveedor : proveedores)
                    {
                        if (proveedor.getId() == id)
                        {
                            deleteProveedor01(proveedor);
                            addFilas(tablaModelo);
                        }
                    }

                }
            }
        });

        panel1.add(label);
        panel1.add(label1);
        panel1.add(label2);
        panel1.add(nombre);
        panel1.add(apellido);
        panel1.add(telefono);
        panel1.add(enviarButton);
        panel1.add(actualizarButton);
        panel1.add(eliminarButton);
        add(panel1, BorderLayout.NORTH);
        add(tablaScrollPane, BorderLayout.CENTER);


        setVisible(true);
    }

    public static Proveedor getInstancia()
    {
        return null == miProveedor ? (new Proveedor()) : miProveedor;
    }

    private static List<Proveedor01> getProveedor() {
        Transaction transaction = null;
        List<Proveedor01> proveedor01s = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            proveedor01s = session.createQuery("from Proveedor01", Proveedor01.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

        }

        return proveedor01s;
    }

    private static List<Proveedor01> getProveedor01(){
        List<Proveedor01> proveedor = new ArrayList<>();
        IGenericService<Proveedor01> proveedorService = new GenericServiceImpl<>(Proveedor01.class, HibernateUtil.getSessionFactory());
        proveedor = proveedorService.getAll();
        return  proveedor;
    }
    private static void saveProveedor01(Proveedor01 proveedor01){
        IGenericService<Proveedor01> proveedorService = new GenericServiceImpl<>(Proveedor01.class, HibernateUtil.getSessionFactory());
        proveedorService.save(proveedor01);
    }
    private static void updateProveedor01(Proveedor01 proveedor01){
        IGenericService<Proveedor01> proveedorService = new GenericServiceImpl<>(Proveedor01.class, HibernateUtil.getSessionFactory());
        proveedorService.update(proveedor01);
    }
    private static void deleteProveedor01(Proveedor01 proveedor01){
        IGenericService<Proveedor01> proveedorService = new GenericServiceImpl<>(Proveedor01.class, HibernateUtil.getSessionFactory());
        proveedorService.delete(proveedor01);
    }

    //destrucracion de cliente (datos de la tabla clientes)
    private static void addFilas (DefaultTableModel model) {
        model.setRowCount(0);
        List<Proveedor01> proveedores = getProveedor01();
        for (Proveedor01 proveedor : proveedores) {
            int id = proveedor.getId();
            String nombretext = proveedor.getNombre();
            String apellidotext = proveedor.getApellido();
            String telefonotext = proveedor.getTelefono();
            model.addRow(new Object[]{id, nombretext, apellidotext, telefonotext});
        }
    }
}