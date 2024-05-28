package org.nahomy.forms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.entity.Producto01;
import org.nahomy.entity.Proveedor01;
import org.nahomy.services.GenericServiceImpl;
import org.nahomy.services.IGenericService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Proveedor extends JInternalFrame
{
    static Proveedor miProveedor;

    public Proveedor()
    {
        super("Proveedor", true, true, true, true);
        inicializarProveedor();
        miProveedor = this;
    }

    private void inicializarProveedor()
    {
        setSize(400, 400);
        setToolTipText("Datos del proveedor");
        setName("Proveedores");
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.yellow);
        getContentPane().add(panel1, BorderLayout.CENTER);
        getContentPane().setPreferredSize(new Dimension(400, 400));
        setVisible(true);

        JLabel label = new JLabel("Nombre");
        label.setBounds(10, 30, 60, 60);
        JTextField nombre = new JTextField(30);
        panel1.setBounds(70, 45, 200, 200);
        panel1.setForeground(Color.black);

        JLabel label1 = new JLabel("Apellido");
        label1.setBounds(10, 30, 60, 60);
        JTextField apellido = new JTextField(30);
        apellido.setBounds(70, 45, 200, 25);

        JLabel label2 = new JLabel("Telefono");
        label2.setBounds(10, 30, 60, 60);
        JTextField telefono = new JTextField(30);
        telefono.setBounds(70, 45, 200, 25);

        JButton enviarButton = new JButton("Enviar");
        enviarButton.setBounds(100, 180, 80, 40);
        enviarButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String nombretxt = nombre.getText();
                String apellidotxt = apellido.getText();
                String telefonotxt = telefono.getText();
                Proveedor01 proveedor01 = new Proveedor01(nombretxt, apellidotxt, telefonotxt);
                guardarUsuario(proveedor01);
                List<Proveedor01> proveedor01s = getProveedor();
                proveedor01s.forEach(System.out::println);
            }
        });

        panel1.add(label);
        panel1.add(nombre);
        panel1.add(label1);
        panel1.add(apellido);
        panel1.add(label2);
        panel1.add(telefono);
        panel1.add(enviarButton);

        setVisible(true);
    }

    public static Proveedor getInstancia()
    {
        return null == miProveedor ? (new Proveedor()) : miProveedor;
    }

    public static void guardarUsuario(Proveedor01 proveedor01)
    {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.save(proveedor01);
            transaction.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if ((transaction != null))
            {
                transaction.rollback();
            }
        }
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

    private static List<Proveedor01> getProveedors(){
        List<Proveedor01> proveedor = new ArrayList<>();
        IGenericService<Proveedor01> proveedorService = new GenericServiceImpl<>(Proveedor01.class, HibernateUtil.getSessionFactory());
        proveedor = proveedorService.getAll();
        return  proveedor;
    }
    private static void saveProveedors(Proveedor01 proveedor){
        IGenericService<Proveedor01> proveedorService = new GenericServiceImpl<>(Proveedor01.class, HibernateUtil.getSessionFactory());
        proveedorService.save(proveedor);
    }
    private static void updateProveedors(Proveedor01 proveedor){
        IGenericService<Proveedor01> proveedorService = new GenericServiceImpl<>(Proveedor01.class, HibernateUtil.getSessionFactory());
        proveedorService.update(proveedor);
    }
    private static void deleteProveedors(Proveedor01 proveedor){
        IGenericService<Proveedor01> proveedorService = new GenericServiceImpl<>(Proveedor01.class, HibernateUtil.getSessionFactory());
        proveedorService.delete(proveedor);
    }
}