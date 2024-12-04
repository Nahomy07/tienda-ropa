package org.nahomy.forms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.entity.Inventario01;
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

public class Inventario extends JInternalFrame{
    private DefaultTableModel tablaModelo;
    static Inventario miInventario;
    public Inventario()
    {
        super("Inventario", true, true, true, true);
        inicializarInventario();
        miInventario = this;
    }
    private void inicializarInventario()
    {
        setSize(745,745);
        setToolTipText("Datos del Inventario");
        setName("Inventario");
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(131, 177, 248));
        getContentPane().add(panel1, BorderLayout.CENTER);

        //nuevo
        panel1.setLayout(new GridLayout(4,2,5,5));
        panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //panel de la tabla
        String[] columnas={"id","cantidad","Nombre del producto","Precio"};
//        tablaModelo = new DefaultTableModel();
//        tablaModelo.addColumn("id");
//        tablaModelo.addColumn("Cantidad");
//        tablaModelo.addColumn("Nombre del producto");
//        tablaModelo.addColumn("Precio");
        tablaModelo = new DefaultTableModel(null,columnas)
        {
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        JTable tabla = new JTable(tablaModelo);

        JScrollPane tablaScrollPane = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel label = new JLabel("Cantidad");
        label.setBounds(0,40,60,60);
        JTextField cantidad = new JTextField(30);
        cantidad.setBounds(10,60,140,25);
        cantidad.setOpaque(false);
        label.setForeground(new Color(250, 249, 249));
        cantidad.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label1 = new JLabel("Nombre del producto");
        label1.setBounds(20,40,60,60);
        JTextField nombreProducto = new JTextField(30);
        nombreProducto.setBounds(70,45,140,25);
        nombreProducto.setOpaque(false);
        label1.setForeground(new Color(250, 249, 249));
        nombreProducto.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label2 = new JLabel("Precio");
        label2.setBounds(10,30,60,60);
        JTextField precio = new JTextField(30);
        precio.setOpaque(false);
        label2.setForeground(new Color(250, 249, 249));
        precio.setBorder(new MatteBorder(0,0,1,0, Color.white));


        JButton enviarButton = new JButton("Guardar");
        enviarButton.setBounds(100,180,80,40);
        enviarButton.setForeground(new Color(131, 177, 248));
        addFilas(tablaModelo);//nuevo
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cantidadtext = cantidad.getText();
                String nombreProductotext = nombreProducto.getText();
                String preciotext = precio.getText();
                if (cantidadtext.isEmpty() || nombreProductotext.isEmpty() || preciotext.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Por favor, complete todos los campos antes de guardar.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return; // Salir si no se cumplen las condiciones
                }
                Inventario01 inventario01 = new Inventario01(cantidadtext,nombreProductotext,preciotext);
                saveInventario01(inventario01);
                addFilas(tablaModelo);
                java.util.List<Inventario01> inventario01s = getInventario();
                inventario01s.forEach(System.out::println);

            }
        });

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBounds(100,180,80,40);
        actualizarButton.setForeground(new Color(131, 177, 248));
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Inventario01> inventarios = getInventario01();
                int i = tabla.getSelectedRow();
                boolean entradaValida = true;
                if (i < 0){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                    entradaValida = false;
                }
                else {
                    String cantidad1 = cantidad.getText().trim();
                    String nombreProducto1 = nombreProducto.getText();
                    String precio1 = precio.getText().trim();
                    int id = Integer.parseInt(tabla.getValueAt(i, 0).toString());
                    if (cantidad1.isEmpty()|| nombreProducto1.isEmpty()||precio1.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                        entradaValida = false;
                    }
                    if (entradaValida){
                        for (Inventario01 inventario : inventarios){
                            if (inventario.getId() == id){
                                inventario.setCantidad(cantidad1);
                                inventario.setNombreProducto(nombreProducto1);
                                inventario.setPrecio(precio1);
                                updateInventario01(inventario);
                                addFilas(tablaModelo);
                                java.util.List<Inventario01> inventario01s = getInventario();
                                inventario01s.forEach(System.out::println);
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
                List<Inventario01> inventarios = getInventario01();
                int i = tabla.getSelectedRow();
                if (i < 0){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int id = Integer.parseInt( tabla.getValueAt(i, 0).toString());
                    for (Inventario01 inventario : inventarios)
                    {
                        if (inventario.getId() == id)
                        {
                            deleteInventario01(inventario);
                            addFilas(tablaModelo);
                        }
                    }

                }
            }
        });

        panel1.add(label);
        panel1.add(label1);
        panel1.add(label2);
        panel1.add(cantidad);
        panel1.add(nombreProducto);
        panel1.add(precio);
        panel1.add(enviarButton);
        panel1.add(actualizarButton);
        panel1.add(eliminarButton);
        add(panel1, BorderLayout.NORTH);
        add(tablaScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static Inventario getInstancia()
    {
        return null == miInventario ? (new Inventario()) : miInventario;

    }
    private static java.util.List<Inventario01> getInventario()
    {
        Transaction transaction= null;
        java.util.List<Inventario01> inventario01s = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            inventario01s = session.createQuery("from Inventario01", Inventario01.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if (transaction != null)
            {
                transaction.rollback();
            }
        }

        return inventario01s;
    }

    private static java.util.List<Inventario01> getInventario01(){
        java.util.List<Inventario01> inventario = new ArrayList<>();
        IGenericService<Inventario01> inventarioService = new GenericServiceImpl<>(Inventario01.class, HibernateUtil.getSessionFactory());
        inventario = inventarioService.getAll();
        return  inventario;
    }

    private static void saveInventario01(Inventario01 inventario01)
    {
        IGenericService<Inventario01> inventario01Service = new GenericServiceImpl<>(Inventario01.class, HibernateUtil.getSessionFactory());
        inventario01Service.save(inventario01);
    }
    private static void updateInventario01(Inventario01 inventario01){
        IGenericService<Inventario01> clienteService = new GenericServiceImpl<>(Inventario01.class, HibernateUtil.getSessionFactory());
        clienteService.update(inventario01);
    }
    private static void deleteInventario01(Inventario01 inventario01){
        IGenericService<Inventario01> inventarioService = new GenericServiceImpl<>(Inventario01.class, HibernateUtil.getSessionFactory());
        inventarioService.delete(inventario01);
    }
    //destrucracion de cliente (datos de la tabla clientes)
    private static void addFilas (DefaultTableModel model){
        model.setRowCount(0);
        List<Inventario01> inventarios= getInventario01();
        for (Inventario01 inventario : inventarios){
            int id = inventario.getId();
            String cantidadtext = inventario.getCantidad();
            String nombreProductotext = inventario.getNombreProducto();
            String preciotext = inventario.getPrecio();
            model.addRow(new Object[]{id,cantidadtext, nombreProductotext, preciotext});
        }
    }
}
