package org.nahomy.forms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.entity.Venta01;
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

public class Venta extends JInternalFrame {

    private DefaultTableModel tablaModelo;
    static Venta miVenta;

    public Venta()
    {
        super("Venta", true, true, true, true);
        inicializarVenta();
        miVenta = this;
    }

    private void inicializarVenta()
    {
        setSize(745,745);
        setToolTipText("Datos de la venta");
        setName("Ventas");
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(131, 177, 248));
        getContentPane().add(panel1, BorderLayout.CENTER);

        panel1.setLayout(new GridLayout(4, 2, 5, 5));
        panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//        tablaModelo = new DefaultTableModel();
//        tablaModelo.addColumn("id");
//        tablaModelo.addColumn("Cantidad");
//        tablaModelo.addColumn("Producto");
//        tablaModelo.addColumn("Precio");
//        tablaModelo.addColumn("Precio total");
        String[] columnas={"id","Cantidad","Producto","Precio","Precio total"};
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
        label.setBounds(20, 40, 60, 60);
        label.setForeground(new Color(250, 249,249));
        JTextField cantidad = new JTextField(30);
        cantidad.setBounds(70,40,140,25);//Aparece al lado del JLabel
        cantidad.setOpaque(false);//Fondo transparente
        cantidad.setBorder(new MatteBorder(0,0,1,0, Color.white));//Linea inferior

        JLabel label1 = new JLabel("Producto");
        label.setBounds(10, 30, 60, 60);
        label1.setForeground(new Color(250,249,249));
        JTextField producto = new JTextField(30);
        producto.setOpaque(false);
        producto.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label2 = new JLabel("Precio");
        label.setBounds(0, 40, 60, 60);
        label2.setForeground(new Color(250,249,249));
        JTextField precio = new JTextField(30);
        precio.setOpaque(false);
        precio.setBorder(new MatteBorder(0,0,1,0,Color.white));

        JButton enviarButton = new JButton("Guardar");
        enviarButton.setBounds(100,180,80,40);
        enviarButton.setForeground(new Color(131,177,248));
        addFilas(tablaModelo);

        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cantidadtxt = cantidad.getText();
                String productotxt = producto.getText();
                String preciotxt = precio.getText();
                if (cantidadtxt.isEmpty() || productotxt.isEmpty() || preciotxt.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Por favor, complete todos los campos antes de guardar.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return; // Salir si no se cumplen las condiciones
                }
                Venta01 venta01 = new Venta01(cantidadtxt,productotxt,preciotxt);
                saveVenta01(venta01);
                addFilas(tablaModelo);
                List<Venta01> venta01s = getVenta();
                venta01s.forEach(System.out::println);
            }
        });

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBounds(100,180,80,40);
        actualizarButton.setForeground(new Color(131,177,248));
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Venta01> ventas = getVenta01();
                int i = tabla.getSelectedRow();
                boolean entradaValida = true;
                if(i < 0)
                {
                    JOptionPane.showMessageDialog(null,"Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                    entradaValida = false;
                }
                else{
                    String cantidad1 = cantidad.getText();
                    String producto1 = producto.getText();
                    String precio1 = precio.getText();
                    int id = Integer.parseInt(tabla.getValueAt(i,0).toString());
                    if (cantidad1.isEmpty() || producto1.isEmpty() || precio1.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                        entradaValida = false;
                    }
                    if(entradaValida){
                        for (Venta01 venta : ventas){
                            if (venta.getId() == id) {
                                venta.setCantidad(cantidad1);
                                venta.setProducto(producto1);
                                venta.setPrecio(precio1);
                                updateVenta01(venta);
                                addFilas(tablaModelo);
                                List<Venta01> venta01s = getVenta();
                                venta01s.forEach(System.out::println);

                            }
                        }
                    }
                }
            }
        });

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.setBounds(100,180,80,40);
        eliminarButton.setForeground(new Color(131,177,248));
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List <Venta01> ventas = getVenta01();
                int i = tabla.getSelectedRow();
                if (i < 0) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    int id = Integer.parseInt(tabla.getValueAt(i,0).toString());
                    for (Venta01 venta : ventas)
                    {
                        if (venta.getId() == id)
                        {
                            deleteVenta01(venta);
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

        panel1.add(producto);

        panel1.add(precio);
        panel1.add(enviarButton);
        panel1.add(actualizarButton);
        panel1.add(eliminarButton);

        add(panel1, BorderLayout.NORTH);
        add(tablaScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static Venta getInstancia(){return null == miVenta ? (new Venta()) : miVenta;}

    private static List<Venta01> getVenta()
    {
        Transaction transaction = null;
        List<Venta01> venta01s = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            venta01s = session.createQuery("from Venta01", Venta01.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return venta01s;
    }

    private static List<Venta01> getVenta01(){
        List<Venta01> venta = new ArrayList<>();
        IGenericService<Venta01> ventaService = new GenericServiceImpl<>(Venta01.class,HibernateUtil.getSessionFactory());
        venta = ventaService.getAll();
        return venta;
    }

    private static void saveVenta01(Venta01 venta01){
        IGenericService<Venta01> ventaService = new GenericServiceImpl<>(Venta01.class, HibernateUtil.getSessionFactory());
        ventaService.save(venta01);
    }

    private static void updateVenta01(Venta01 venta01){
        IGenericService<Venta01> ventaService = new GenericServiceImpl<>(Venta01.class, HibernateUtil.getSessionFactory());
        ventaService.update(venta01);
    }

    private static void deleteVenta01(Venta01 venta01){
        IGenericService<Venta01> ventaService = new GenericServiceImpl<>(Venta01.class, HibernateUtil.getSessionFactory());
        ventaService.delete(venta01);
    }

    private static void addFilas (DefaultTableModel model){
        model.setRowCount(0);
        List<Venta01> ventas = getVenta01();
        for (Venta01 venta : ventas) {
            int id = venta.getId();
            String cantidadtxt = venta.getCantidad();
            String productotxt = venta.getProducto();
            String preciotxt = venta.getPrecio();
            model.addRow(new Object[]{id, cantidadtxt,productotxt,preciotxt});
        }
    }
}

