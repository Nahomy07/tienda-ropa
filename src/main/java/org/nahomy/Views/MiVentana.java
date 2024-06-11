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
import java.net.URL;

public class MiVentana extends JFrame
{
    public static JDesktopPane desktopPane;

    public MiVentana()
    {
        super("Venta de ropa");
        desktopPane = new JDesktopPane();
        setLayout(new BorderLayout());
        setSize(750,750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        getContentPane().add(desktopPane, BorderLayout.CENTER);
        setResizable(false);

        URL rutaImagen = MiVentana.class.getResource("/PsStore.png");
        ImageIcon imagen = new ImageIcon(rutaImagen);
        setIconImage(imagen.getImage());
        setVisible(true);

       // JPanel panel = new JPanel();
        //panel.setPreferredSize(new Dimension(800,800));

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

        ActionListener salirListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Salir");
            }
        };

        //Creamos los JMenuItem
        JMenuItem salir = new JMenuItem("Salir");
        salir.setMnemonic('A');
        salir.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
        salir.addActionListener(salirListener);
        JMenuItem cliente = new JMenuItem("Cliente");
        this.getContentPane().add(desktopPane, BorderLayout.CENTER);
        JMenuItem producto = new JMenuItem("Producto");
        this.getContentPane().add(desktopPane, BorderLayout.CENTER);
        JMenuItem proveedor = new JMenuItem("Proveedor");
        this.getContentPane().add(desktopPane, BorderLayout.CENTER);
        JMenuItem vendedor = new JMenuItem("Vendedor");
        this.getContentPane().add(desktopPane, BorderLayout.CENTER);
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
                AgregarAventana(Proveedor.getInstancia());
                setVisible(true);
            }
        });

        vendedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        menuBar.setPreferredSize(new Dimension(160,40));
        menuBar.add(menuApp);
        menuBar.add(menuEntidades);
        menuBar.add(menuAyuda);


        setJMenuBar(menuBar);
        //agregar la barra menu al panel
//        panel.add(menuBar, BorderLayout.NORTH);
//        add(panel);
    }

    public static void AgregarAventana(JInternalFrame ventanaInterna) //nuevo
    {
        desktopPane.add(ventanaInterna);
        Dimension dsKsize = desktopPane.getSize();
        Dimension frmSize = ventanaInterna.getSize();
        ventanaInterna.setLocation(0, 0);
        ventanaInterna.setVisible(true);

    }
}


