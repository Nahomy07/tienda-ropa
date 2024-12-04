package org.nahomy;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.Views.MiVentana;
import org.nahomy.entity.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        Usuario usuario = new Usuario("nahomy@gmail.com", "P@ssw0rd!");
//        guardarUsuario(usuario);
//        List<Usuario>usuarios = getUsuario();
//        usuarios.forEach(System.out::println);
//
//        JFrame frame = new JFrame();
//
        new MiVentana().setVisible(true);
//
//        frame.setSize(300, 300);
//        frame.setTitle("Inicio de sesión");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
//        frame.setLayout(null);
//
//
//        JLabel label = new JLabel("Correo");
//        label.setBounds(10,30,60,60);
//        JTextField correo = new JTextField(30);
//        correo.setBounds(70,45,200,25);
//
//
//        JLabel label1 = new JLabel("Contraseña");
//        label1.setBounds(10,85,60,60);
//        JPasswordField contraseña = new JPasswordField(30);
//        contraseña.setBounds(70,100,200,25);
//
//
//        JButton enviarButton = new JButton("Enviar");
//        enviarButton.setBounds(100,180,80,40);
//        enviarButton.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                String usuario = correo.getText();
//                String emPtrn =
//                        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//                Pattern pattern1 = Pattern.compile(emPtrn);
//                Matcher match2 = pattern1.matcher(usuario);
//
//                String password = new String(contraseña.getPassword());
//                String passPattern =
//                        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,30}$";
//                Pattern pattern = Pattern.compile(passPattern);
//                Matcher match = pattern.matcher(password);
//                boolean usuarioValido = false;
//                for (Usuario usuario1 : usuarios){
//                    if (usuario1.getUsuario().equalsIgnoreCase(usuario)&& usuario1.getContraseña().equals(password)){
//                        usuarioValido = true;
//                    }
//                }
//                if (match2.matches()&& match.matches()){
//                    if (usuarioValido){
//                        frame.dispose();
//                        new  MiVentana().setVisible(true);
//                    } else {
//                        JOptionPane.showMessageDialog(null, "No registrado");
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "Campos no validos","Error", JOptionPane.ERROR_MESSAGE);
//                }
//
//            }
//        });
//
//        frame.add(label);
//        frame.add(correo);
//        frame.add(label1);
//        frame.add(contraseña);
//        frame.add(enviarButton);
//
//
//        URL rutaImagen = App.class.getResource("/RiAccountCircleFill.png");
//        ImageIcon image = new ImageIcon(rutaImagen);
//        frame.setIconImage(image.getImage());
//        frame.setVisible(true);
//
//
//       // new MiVentana();
    }

    public static void guardarUsuario(Usuario usuario)
    {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.save(usuario);
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

    private static List<Usuario> getUsuario()
    {
        Transaction transaction= null;
        List<Usuario> usuarios = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            usuarios = session.createQuery("from Usuario", Usuario.class).list();
        }

        catch (Exception e)
        {
            e.printStackTrace();
            if (transaction != null)
            {
                transaction.rollback();
            }
        }

        return usuarios;
    }
}
