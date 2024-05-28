package org.nahomy.Views;

import org.hibernate.mapping.Property;
import org.nahomy.forms.Cliente;
import org.nahomy.forms.Producto;
import org.nahomy.forms.Proveedor;
import org.nahomy.forms.Vendedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

public class MiVentana extends JFrame
{
    public static JDesktopPane desktopPane;

    public MiVentana()
    {
        desktopPane = new JDesktopPane();
        setLayout(new BorderLayout());
        setSize(800,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400,400));
        //Barra de menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuApp = new JMenu("App");
        //Salir
        JMenu menuEntidades = new JMenu("Entidades");
        //Cliente
        //Producto
        //Proveedor
        //Vendedor
        JMenu menuAyuda = new JMenu("Ayuda");
        //Acerca de


        ActionListener  clienteListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                desktopPane = new JDesktopPane();
                getContentPane().add(desktopPane, BorderLayout.CENTER);
                AgregarAventana(Cliente.getInstancia());
                setVisible(true);
                JOptionPane.showMessageDialog(null, "Cliente");
            }
        };

        ActionListener productoListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Producto producto = new Producto();
                desktopPane = new JDesktopPane();
                desktopPane.add(producto);
                producto.setVisible(true);
                JOptionPane.showMessageDialog(null, "Producto");
            }
        };

        ActionListener proveedorListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                desktopPane = new JDesktopPane();
                getContentPane().add(desktopPane, BorderLayout.CENTER);
                AgregarAventana(Proveedor.getInstancia());
                setVisible(true);
                JOptionPane.showMessageDialog(null,"Proveedor");
            }
        };

        ActionListener vendedorListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Vendedor vendedor = new Vendedor();
                desktopPane.add(vendedor);
                vendedor.setVisible(true);
                JOptionPane.showMessageDialog(null, "Vendedor");
            }
        };

        ActionListener acercadeListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Acerca de");
            }
        };

        //Creamos los JMenuItem
        JMenuItem salir = new JMenuItem("Salir");
        JMenuItem cliente = new JMenuItem("Cliente");
        this.getContentPane().add(desktopPane, BorderLayout.CENTER);
        JMenuItem producto = new JMenuItem("Producto");
        this.getContentPane().add(desktopPane, BorderLayout.CENTER);
        JMenuItem proveedor = new JMenuItem("Proveedor");
        JMenuItem vendedor = new JMenuItem("Vendedor");
        JMenuItem acercade = new JMenuItem("Acerca de");


        salir.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                {
                    JOptionPane.showMessageDialog(null, "Salir");
                    dispose();
                }
            }
        });



        cliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // desktopPane = new JDesktopPane();
                //getContentPane().add(desktopPane, BorderLayout.CENTER);
                AgregarAventana(Cliente.getInstancia());
                setVisible(true);
            }
        });

        producto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // desktopPane = new JDesktopPane();
                //getContentPane().add(desktopPane, BorderLayout.CENTER);
                AgregarAventana(Producto.getInstancia());
                setVisible(true);
            }
        });


        proveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopPane = new JDesktopPane();
                getContentPane().add(desktopPane, BorderLayout.CENTER);
                AgregarAventana(Proveedor.getInstancia());
                setVisible(true);
            }
        });

        vendedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopPane = new JDesktopPane();
                getContentPane().add(desktopPane, BorderLayout.CENTER);
                AgregarAventana(Vendedor.getInstancia());
                setVisible(true);
            }
        });

        //agregar los items a los menus
        menuApp.add(salir);

        menuEntidades.setPreferredSize(new Dimension(80,30));
        menuEntidades.add(cliente);
        menuEntidades.add(producto);
        menuEntidades.add(proveedor);
        menuEntidades.add(vendedor);


        menuAyuda.add(acercade);

        //agregar todos los menu a la barra
        menuBar.setPreferredSize(new Dimension(200,40));
        menuBar.add(menuApp);
        menuBar.add(menuEntidades);
        menuBar.add(menuAyuda);



        //agregar la barra menu al panel
        panel.add(menuBar, BorderLayout.NORTH);
        add(panel);
    }

//
//        desktopPane = new JDesktopPane();
//        getContentPane().add(desktopPane, BorderLayout.NORTH);
//        AgregarAventana(Proveedor.getInstancia());
//        setVisible(true);
//
//        desktopPane = new JDesktopPane();
//        getContentPane().add(desktopPane, BorderLayout.WEST);
//        AgregarAventana(Vendedor.getInstancia());
//        setVisible(true);

   // }

    public static void AgregarAventana(JInternalFrame ventanaInterna) //nuevo
    {
        desktopPane.add(ventanaInterna);
        Dimension dsKsize = desktopPane.getSize();
        Dimension frmSize = ventanaInterna.getSize();
        ventanaInterna.setLocation(0, 0);
        ventanaInterna.setVisible(true);

    }

//    private static void mostrarVentana(JInternalFrame frm)
//    {
//        try {
//            //Agregar ventana si la ventana no esta visible
//            if(!frm.isVisible()){
//                MiVentana.AgregarAventana(frm);
//            }
//            else {
//                // si la ventana esta visible pero debajo de otras se mueve al frente
//                frm.moveToFront();
//                if (!frm.isSelected()) {
//                    frm.setSelected(true);
//                }
//            }
//        }
//        catch (PropertyVetoException e)
//        {
//            System.out.println("Error al activar la ventana");
//        }
//    }
//
}


