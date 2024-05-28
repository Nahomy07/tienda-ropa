package org.nahomy.forms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.entity.Producto01;
import org.nahomy.entity.Proveedor01;
import org.nahomy.entity.Vendedor01;
import org.nahomy.services.GenericServiceImpl;
import org.nahomy.services.IGenericService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Vendedor extends JInternalFrame
{
    static Vendedor miVendedor;

    public Vendedor()
    {
        super("Vendedor", true, true, true, true);
        inicializarVendedor();
        miVendedor = this;
    }

    private void inicializarVendedor()
    {
        setSize(400,400);
        setToolTipText("Datos del vendedor");
        setName("Vendedor");
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
                Vendedor01 vendedor01 = new Vendedor01(nombretxt, apellidotxt, telefonotxt);
                guardarUsuario(vendedor01);
                List<Vendedor01> vendedor01s = getVendedor();
                vendedor01s.forEach(System.out::println);
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
    public static Vendedor getInstancia()
    {
        return null == miVendedor ? (new Vendedor()) : miVendedor;
    }

    public static void guardarUsuario(Vendedor01 vendedor01)
    {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.save(vendedor01);
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

    private static List<Vendedor01> getVendedor() {
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

    private static List<Vendedor01> getVendedors(){
        List<Vendedor01> vendedor = new ArrayList<>();
        IGenericService<Vendedor01> vendedorService = new GenericServiceImpl<>(Vendedor01.class, HibernateUtil.getSessionFactory());
        vendedor = vendedorService.getAll();
        return  vendedor;
    }
    private static void saveVendedors(Vendedor01 vendedor){
        IGenericService<Vendedor01> vendedorService = new GenericServiceImpl<>(Vendedor01.class, HibernateUtil.getSessionFactory());
        vendedorService.save(vendedor);
    }
    private static void updateVendedors(Vendedor01 vendedor){
        IGenericService<Vendedor01> vendedorService = new GenericServiceImpl<>(Vendedor01.class, HibernateUtil.getSessionFactory());
        vendedorService.update(vendedor);
    }
    private static void deleteVendedors(Vendedor01 vendedor){
        IGenericService<Vendedor01> vendedorService = new GenericServiceImpl<>(Vendedor01.class, HibernateUtil.getSessionFactory());
        vendedorService.delete(vendedor);
    }
}
