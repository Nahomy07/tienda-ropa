package org.nahomy.forms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.entity.Vendedor01;
import org.nahomy.services.GenericServiceImpl;
import org.nahomy.services.IGenericService;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Vendedor extends JInternalFrame
{
    private DefaultTableModel tablaModelo;
    static Vendedor miVendedor;

    public Vendedor()
    {
        super("Vendedor", true, true, true, true);
        inicializarVendedor();
        miVendedor = this;
    }

    private void inicializarVendedor()
    {
        setSize(745,745);
        setToolTipText("Datos del cliente");
        setName("Vendedor");
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(131,177,248));
        getContentPane().add(panel1, BorderLayout.CENTER);
        //getContentPane().setPreferredSize(new Dimension(400, 400));

        //nuevo
        panel1.setLayout(new GridLayout(4,2,5,5));
        panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //panel de la tabla
//        tablaModelo = new DefaultTableModel();
//        tablaModelo.addColumn("id");
//        tablaModelo.addColumn("Nombre");
//        tablaModelo.addColumn("Apellido");
//        tablaModelo.addColumn("Telefono");
        String[] columnas={"id","Nombre","Apellido","Telefono"};
        tablaModelo = new DefaultTableModel(null,columnas)
        {
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
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
        apellido.setBounds(70, 45, 140, 25);
        apellido.setOpaque(false);
        label1.setForeground(new Color(250, 249, 249));
        apellido.setBorder(new MatteBorder(0,0,1,0, Color.white));


        JLabel label2 = new JLabel("Telefono");
        label2.setBounds(0, 40, 60, 60);
        JTextField telefono = new JTextField(30);
        telefono.setOpaque(false);
        label2.setForeground(new Color(250, 249, 249));
        telefono.setBorder(new MatteBorder(0,0,1,0,Color.white));

        //Creacion del boton y configuracion del Listener
        JButton enviarButton = new JButton("Guardar");
        enviarButton.setBounds(100, 180, 80, 40);
        enviarButton.setForeground(new Color(131, 177, 248));
        addFilas(tablaModelo);
        enviarButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String nombretext = nombre.getText();
                String apellidotext = apellido.getText();
                String telefonotext = telefono.getText();
                if (nombretext.isEmpty() || apellidotext.isEmpty() || telefonotext.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Por favor, complete todos los campos antes de guardar.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return; // Salir si no se cumplen las condiciones
                }
                Vendedor01 vendedor01 = new Vendedor01(nombretext, apellidotext, telefonotext);
                saveVendedor01(vendedor01);
                addFilas(tablaModelo);
                List<Vendedor01> vendedor01s = getVendedor();
                vendedor01s.forEach(System.out::println);
            }
        });

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBounds(100,180,80,40);
        actualizarButton.setForeground(new Color(131, 177, 248));
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List <Vendedor01> vendedores = getVendedor01();
                int i = tabla.getSelectedRow();
                boolean entradaValida = true;
                if (i < 0){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                    entradaValida = false;
                }
                else {
                    String nombre1 = nombre.getText().trim();
                    String apellido1 = apellido.getText();
                    String telefono1 = telefono.getText().trim();
                    int id = Integer.parseInt(tabla.getValueAt(i,0).toString());
                    if (nombre1.isEmpty() || apellido1.isEmpty() || telefono1.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                        entradaValida = false;
                    }
                    if (entradaValida) {
                        for (Vendedor01 vendedor : vendedores) {
                            if (vendedor.getId() == id) {
                                vendedor.setNombre(nombre1);
                                vendedor.setApellido(apellido1);
                                vendedor.setTelefono(telefono1);
                                updateVendedor01(vendedor);
                                addFilas(tablaModelo);
                                List<Vendedor01> vendedor01s = getVendedor();
                                vendedor01s.forEach(System.out::println);
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
                List <Vendedor01> vendedores = getVendedor01();
                int i = tabla.getSelectedRow();
                if (i < 0){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int id = Integer.parseInt( tabla.getValueAt(i, 0).toString());
                    for (Vendedor01 vendedor : vendedores)
                    {
                        if (vendedor.getId() == id)
                        {
                            deleteVendedor01(vendedor);
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
    public static Vendedor getInstancia()
    {
        return null == miVendedor ? (new Vendedor()) : miVendedor;
    }

    private static List<Vendedor01> getVendedor()
    {
        Transaction transaction = null;
        List<Vendedor01> vendedor01s = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vendedor01s = session.createQuery("from Vendedor01", Vendedor01.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return vendedor01s;
    }

    private static List<Vendedor01> getVendedor01(){
        List<Vendedor01> vendedor = new ArrayList<>();
        IGenericService<Vendedor01> vendedorService = new GenericServiceImpl<>(Vendedor01.class, HibernateUtil.getSessionFactory());
        vendedor = vendedorService.getAll();
        return  vendedor;
    }
    private static void saveVendedor01(Vendedor01 vendedor01){
        IGenericService<Vendedor01> vendedorService = new GenericServiceImpl<>(Vendedor01.class, HibernateUtil.getSessionFactory());
        vendedorService.save(vendedor01);
    }
    private static void updateVendedor01(Vendedor01 vendedor01){
        IGenericService<Vendedor01> vendedorService = new GenericServiceImpl<>(Vendedor01.class, HibernateUtil.getSessionFactory());
        vendedorService.update(vendedor01);
    }
    private static void deleteVendedor01(Vendedor01 vendedor01){
        IGenericService<Vendedor01> vendedorService = new GenericServiceImpl<>(Vendedor01.class, HibernateUtil.getSessionFactory());
        vendedorService.delete(vendedor01);
    }

    private static void addFilas (DefaultTableModel model){
        model.setRowCount(0);
        List<Vendedor01> vendedores= getVendedor01();
        for (Vendedor01 vendedor : vendedores){
            int id = vendedor.getId();
            String nombretext = vendedor.getNombre();
            String apellidotext = vendedor.getApellido();
            String telefonotext = vendedor.getTelefono();
            model.addRow(new Object[]{id,nombretext, apellidotext, telefonotext});
        }
    }
}
