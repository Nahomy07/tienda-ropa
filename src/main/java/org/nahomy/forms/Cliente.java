package org.nahomy.forms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.entity.Cliente01;
import org.nahomy.entity.Usuario;
import org.nahomy.entity.Vendedor01;
import org.nahomy.services.GenericServiceImpl;
import org.nahomy.services.IGenericService;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends JInternalFrame
{
    private DefaultTableModel tablaModelo;
    static Cliente miCliente;
    public Cliente()
    {
        super("Cliente", true, true, true, true);
        inicializarCliente();
        miCliente = this;
    }
    private void inicializarCliente()
    {
        setSize(745,745);
        setToolTipText("Datos del cliente");
        setName("Clientes");
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(131, 177, 248));
        getContentPane().add(panel1, BorderLayout.CENTER);

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
        label.setBounds(0,40,60,60);
        JTextField nombre = new JTextField(30);
        nombre.setBounds(10,60,140,25);
        nombre.setOpaque(false);
        label.setForeground(new Color(250, 249, 249));
        nombre.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label1 = new JLabel("Apellido");
        label1.setBounds(20,40,60,60);
        JTextField apellido = new JTextField(30);
        apellido.setBounds(70,45,140,25);
        apellido.setOpaque(false);
        label1.setForeground(new Color(250, 249, 249));
        apellido.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label2 = new JLabel("Telefono");
        label2.setBounds(10,30,60,60);
        JTextField telefono = new JTextField(30);
        telefono.setOpaque(false);
        label2.setForeground(new Color(250, 249, 249));
        telefono.setBorder(new MatteBorder(0,0,1,0, Color.white));


        JButton enviarButton = new JButton("Guardar");
        enviarButton.setBounds(100,180,80,40);
        enviarButton.setForeground(new Color(131, 177, 248));
        addFilas(tablaModelo);//nuevo
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombretext = nombre.getText();
                String apellidotext = apellido.getText();
                String telefonotext = telefono.getText();
                Cliente01 cliente01 = new Cliente01(nombretext,apellidotext,telefonotext);
                saveCliente01(cliente01);
                addFilas(tablaModelo);
                List <Cliente01> cliente01s = getCliente();
                cliente01s.forEach(System.out::println);

            }
        });

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBounds(100,180,80,40);
        actualizarButton.setForeground(new Color(131, 177, 248));
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Cliente01> clientes = getCliente01();
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
                    String id = (String) tabla.getValueAt(i, 0);
                    if (nombre1.isEmpty()|| apellido1.isEmpty()||telefono1.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                        entradaValida = false;
                    }
                if (entradaValida){
                    for (Cliente01 cliente : clientes){
                        if (id.equalsIgnoreCase(cliente.getNombre())){
                            cliente.setNombre(nombre1);
                            cliente.setApellido(apellido1);
                            cliente.setTelefono(telefono1);
                            updateCliente01(cliente);
                            addFilas(tablaModelo);
                            List <Cliente01> cliente01s = getCliente();
                            cliente01s.forEach(System.out::println);
                        }
                    }}
                }
            }
        });

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.setBounds(100,180,80,40);
        eliminarButton.setForeground(new Color(131, 177, 248));
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Cliente01> clientes = getCliente01();
                int i = tabla.getSelectedRow();
                if (i < 0){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int id = Integer.parseInt( tabla.getValueAt(i, 0).toString());
                    for (Cliente01 cliente : clientes)
                    {
                        if (cliente.getId() == id)
                        {
                            deleteCliente01(cliente);
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

    public static Cliente getInstancia()
    {
        return null == miCliente ? (new Cliente()) : miCliente;

    }
    private static java.util.List<Cliente01> getCliente()
    {
        Transaction transaction= null;
        List<Cliente01> cliente01s = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            cliente01s = session.createQuery("from Cliente01", Cliente01.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if (transaction != null)
            {
                transaction.rollback();
            }
        }

        return cliente01s;
    }

    private static List<Cliente01> getCliente01(){
        List<Cliente01> cliente = new ArrayList<>();
        IGenericService<Cliente01> clienteService = new GenericServiceImpl<>(Cliente01.class, HibernateUtil.getSessionFactory());
        cliente = clienteService.getAll();
        return  cliente;
    }

    private static void saveCliente01(Cliente01 cliente01)
    {
        IGenericService<Cliente01> cliente01Service = new GenericServiceImpl<>(Cliente01.class, HibernateUtil.getSessionFactory());
        cliente01Service.save(cliente01);
    }
    private static void updateCliente01(Cliente01 cliente01){
        IGenericService<Cliente01> clienteService = new GenericServiceImpl<>(Cliente01.class, HibernateUtil.getSessionFactory());
        clienteService.update(cliente01);
    }
    private static void deleteCliente01(Cliente01 cliente01){
        IGenericService<Cliente01> clienteService = new GenericServiceImpl<>(Cliente01.class, HibernateUtil.getSessionFactory());
        clienteService.delete(cliente01);
    }
    //destrucracion de cliente (datos de la tabla clientes)
    private static void addFilas (DefaultTableModel model){
        model.setRowCount(0);
        List<Cliente01> clientes= getCliente01();
        for (Cliente01 cliente : clientes){
            int id = cliente.getId();
            String nombretext = cliente.getNombre();
            String apellidotext = cliente.getApellido();
            String telefonotext = cliente.getTelefono();
            model.addRow(new Object[]{id,nombretext, apellidotext, telefonotext});
        }
    }
}

