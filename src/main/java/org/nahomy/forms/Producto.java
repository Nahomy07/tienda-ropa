package org.nahomy.forms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.entity.Cliente01;
import org.nahomy.entity.Producto01;
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

public class Producto extends JInternalFrame
{
    private DefaultTableModel tablaModelo;
    static Producto miProducto;

    public Producto()
    {
        super("Producto", true, true, true, true);
        inicializarProducto();
        miProducto = this;
    }

    private void inicializarProducto()
    {
        setSize(740, 745);
        setToolTipText("Datos del producto");
        setName("Productos");
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(131, 177, 248));
        getContentPane().add(panel1, BorderLayout.CENTER);
        //getContentPane().setPreferredSize(new Dimension(400, 400));

        //nuevo
        panel1.setLayout(new GridLayout(4,2,5,5));
        panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //panel de la tabla

        String[] columnas={"id","Nombre","Precio","Descripcion"};
//        tablaModelo.addColumn("id");
//        tablaModelo.addColumn("Nombre");
//        tablaModelo.addColumn("Precio");
//        tablaModelo.addColumn("Descripcion");
        tablaModelo = new DefaultTableModel(null,columnas)
        {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(tablaModelo);

        JScrollPane tablaScrollPane = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel label = new JLabel("Nombre");
        label.setBounds(20, 40, 60, 60);
        JTextField nombre = new JTextField(30);
        nombre.setBounds(70,45,140,25);
        nombre.setOpaque(false);
        label.setForeground(new Color(250, 249, 249));
        nombre.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label1 = new JLabel("Precio");
        label1.setBounds(10, 30, 60, 60);
        JTextField precio = new JTextField(30);
        precio.setOpaque(false);
        label1.setForeground(new Color(250, 249, 249));
        precio.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label2 = new JLabel("Descripcion");
        label2.setBounds(10, 30, 60, 60);
        JTextField descripcion = new JTextField(30);
        descripcion.setOpaque(false);
        label2.setForeground(new Color(250, 249, 249));
        descripcion.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JButton enviarButton = new JButton("Guardar");
        enviarButton.setBounds(100, 180, 80, 40);
        enviarButton.setForeground(new Color(131, 177, 248));
        addFilas(tablaModelo);
        enviarButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombretext = nombre.getText();
                String preciotext = precio.getText();
                String descripciontext = descripcion.getText();
                if (nombretext.isEmpty() || preciotext.isEmpty() || descripciontext.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Por favor, complete todos los campos antes de guardar.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return; // Salir si no se cumplen las condiciones
                }
                Producto01 producto01 = new Producto01(nombretext, preciotext, descripciontext);
                saveProducto01(producto01);
                addFilas(tablaModelo);
                List<Producto01> producto01s = getProducto();
                producto01s.forEach(System.out::println);
            }
        });

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBounds(100,180,80,40);
        actualizarButton.setForeground(new Color(131, 177, 248));
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List <Producto01> productos = getProducto01();
                int i = tabla.getSelectedRow();
                boolean entradaValida = true;
                if(i < 0)
                {
                    JOptionPane.showMessageDialog(null,"Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String nombre1 = nombre.getText().trim();
                    String precio1 = precio.getText();
                    String descripcion1 = descripcion.getText().trim();
                    int id = Integer.parseInt(tabla.getValueAt(i, 0).toString());
                    if (nombre1.isEmpty()|| precio1.isEmpty()||descripcion1.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                        entradaValida = false;
                    }
                    if (entradaValida){
                        for (Producto01 producto : productos) {
                            if (producto.getId() == id){
                                producto.setNombre(nombre1);
                                producto.setPrecio(precio1);
                                producto.setDescripcion(descripcion1);
                                updateProducto01(producto);
                                addFilas(tablaModelo);
                                List<Producto01> producto01s = getProducto();
                                producto01s.forEach(System.out::println);
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
                List <Producto01> productos = getProducto01();
                int i = tabla.getSelectedRow();
                if (i < 0){
                    JOptionPane.showMessageDialog(null, "Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    int id = Integer.parseInt(tabla.getValueAt(i, 0).toString());
                    for (Producto01 producto : productos)
                    {
                        if (producto.getId() == id)
                        {
                            deleteProducto01(producto);
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
        panel1.add(precio);
        panel1.add(descripcion);
        panel1.add(enviarButton);
        panel1.add(actualizarButton);
        panel1.add(eliminarButton);
        add(panel1, BorderLayout.NORTH);
        add(tablaScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static Producto getInstancia() {
        return null == miProducto ? (new Producto()) : miProducto;
    }

    private static List<Producto01> getProducto()
    {
        Transaction transaction = null;
        List<Producto01> producto01s = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            producto01s = session.createQuery("from Producto01", Producto01.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if (transaction != null)
            {
                transaction.rollback();
            }
        }

        return producto01s;
    }

    private static List<Producto01> getProducto01(){
        List<Producto01> producto = new ArrayList<>();
        IGenericService<Producto01> productService = new GenericServiceImpl<>(Producto01.class, HibernateUtil.getSessionFactory());
        producto = productService.getAll();
        return  producto;
    }

    private static void saveProducto01(Producto01 producto01){
        IGenericService<Producto01> productoService = new GenericServiceImpl<>(Producto01.class, HibernateUtil.getSessionFactory());
        productoService.save(producto01);
    }
    private static void updateProducto01(Producto01 producto01){
        IGenericService<Producto01> productoService = new GenericServiceImpl<>(Producto01.class, HibernateUtil.getSessionFactory());
        productoService.update(producto01);
    }
    private static void deleteProducto01(Producto01 producto01){
        IGenericService<Producto01> productoService = new GenericServiceImpl<>(Producto01.class, HibernateUtil.getSessionFactory());
        productoService.delete(producto01);
    }

    //destrucracion de producto (datos de la tabla productos)
    private static void addFilas (DefaultTableModel model) {
        model.setRowCount(0);
        List<Producto01> productos = getProducto01();
        for (Producto01 producto : productos) {
            int id = producto.getId();
            String nombretext = producto.getNombre();
            String apellidotext = producto.getPrecio();
            String telefonotext = producto.getDescripcion();
            model.addRow(new Object[]{id, nombretext, apellidotext, telefonotext});
        }
    }
}
