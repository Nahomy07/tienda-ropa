package org.nahomy.forms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.entity.Cliente01;
import org.nahomy.entity.Usuario;
import org.nahomy.services.GenericServiceImpl;
import org.nahomy.services.IGenericService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends JInternalFrame
{
    static Cliente miCliente;

    public Cliente()
    {
        super("Cliente", true, true, true, true);
        inicializarCliente();
        miCliente = this;
    }

    private void inicializarCliente()
    {
        setSize(400,400);
        setToolTipText("Datos del cliente");
        setName("Clientes");
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.blue);
        getContentPane().add(panel1, BorderLayout.CENTER);
        getContentPane().setPreferredSize(new Dimension(400, 400));

        JLabel label = new JLabel("Nombre");
        label.setBounds(10,30,60,60);
        JTextField nombre = new JTextField(30);
        panel1.setForeground(Color.black);

        JLabel label1 = new JLabel("Apellido");
        label1.setBounds(10,30,60,60);
        JTextField apellido = new JTextField(30);
        apellido.setBounds(70,45,200,25);

        JLabel label2 = new JLabel("Telefono");
        label2.setBounds(10,30,60,60);
        JTextField telefono = new JTextField(30);
        telefono.setBounds(70,45,200,25);

        JButton enviarButton = new JButton("Enviar");
        enviarButton.setBounds(100,180,80,40);
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombretxt = nombre.getText();
                String apellidotxt = apellido.getText();
                String tel = telefono.getText();
                Cliente01 cliente01 = new Cliente01(nombretxt,apellidotxt,tel);
                guardarUsuario(cliente01);
                List <Cliente01> cliente01s = getCliente();
                cliente01s.forEach(System.out::println);

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

    public static Cliente getInstancia()
    {
        return null == miCliente ? (new Cliente()) : miCliente;

    }
    public static void guardarUsuario(Cliente01 cliente01)
    {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.save(cliente01);
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

    Cliente01 cliente01 = new Cliente01("nahomy","estrada","57627797");
    List<Cliente01> cliente01s = getCliente01();



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
    private static void updateCliente01(Cliente01 cliente){
        IGenericService<Cliente01> clienteService = new GenericServiceImpl<>(Cliente01.class, HibernateUtil.getSessionFactory());
        clienteService.update(cliente);
    }
    private static void deleteCliente01(Cliente01 cliente){
        IGenericService<Cliente01> clienteService = new GenericServiceImpl<>(Cliente01.class, HibernateUtil.getSessionFactory());
        clienteService.delete(cliente);
    }
}

