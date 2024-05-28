package org.nahomy.forms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.entity.Producto01;
import org.nahomy.services.GenericServiceImpl;
import org.nahomy.services.IGenericService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Producto extends JInternalFrame
{
    static Producto miProducto;

    public Producto()
    {
        super("Producto", true, true, true, true);
        inicializarProducto();
        miProducto = this;
    }

    private void inicializarProducto()
    {
        setSize(400, 400);
        setToolTipText("Datos del producto");
        setName("Productos");
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.red);
        getContentPane().add(panel1, BorderLayout.CENTER);
        getContentPane().setPreferredSize(new Dimension(400, 400));

        JLabel label = new JLabel("Nombre");
        label.setBounds(10, 30, 60, 60);
        JTextField nombre = new JTextField(30);
        panel1.setBounds(70, 45, 200, 200);
        panel1.setForeground(Color.black);

        JLabel label1 = new JLabel("Precio");
        label1.setBounds(10, 30, 60, 60);
        JTextField precio = new JTextField(30);
        precio.setBounds(70, 45, 200, 25);

        JLabel label2 = new JLabel("Descripcion");
        label2.setBounds(10, 30, 60, 60);
        JTextField descripcion = new JTextField(30);
        descripcion.setBounds(70, 45, 200, 25);

        JButton enviarButton = new JButton("Enviar");
        enviarButton.setBounds(100, 180, 80, 40);
        enviarButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombretxt = nombre.getText();
                String preciotxt = precio.getText();
                String descripciontxt = descripcion.getText();
                Producto01 producto01 = new Producto01(nombretxt, preciotxt, descripciontxt);
                guardarUsuario(producto01);
                List<Producto01> producto01s = getProducto();
                producto01s.forEach(System.out::println);
            }
        });

        panel1.add(label);
        panel1.add(nombre);
        panel1.add(label1);
        panel1.add(precio);
        panel1.add(label2);
        panel1.add(descripcion);
        panel1.add(enviarButton);

        setVisible(true);
    }

    public static Producto getInstancia() {
        return null == miProducto ? (new Producto()) : miProducto;

    }

    public static void guardarUsuario(Producto01 producto01) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(producto01);
            transaction.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            if ((transaction != null)) {
                transaction.rollback();
            }
        }
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

    private static List<Producto01> getProducts(){
        List<Producto01> productos = new ArrayList<>();
        IGenericService<Producto01> productService = new GenericServiceImpl<>(Producto01.class, HibernateUtil.getSessionFactory());
        productos = productService.getAll();
        return  productos;
    }
    private static void saveProducts(Producto01 producto){
        IGenericService<Producto01> productoService = new GenericServiceImpl<>(Producto01.class, HibernateUtil.getSessionFactory());
        productoService.save(producto);
    }
    private static void updateProducts(Producto01 producto){
        IGenericService<Producto01> productoService = new GenericServiceImpl<>(Producto01.class, HibernateUtil.getSessionFactory());
        productoService.update(producto);
    }
    private static void deleteProducts(Producto01 producto){
        IGenericService<Producto01> productoService = new GenericServiceImpl<>(Producto01.class, HibernateUtil.getSessionFactory());
        productoService.delete(producto);
    }

}
