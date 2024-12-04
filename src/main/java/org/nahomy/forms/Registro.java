package org.nahomy.forms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nahomy.Util.HibernateUtil;
import org.nahomy.entity.Inventario01;
import org.nahomy.entity.Registro01;
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

public class Registro extends JInternalFrame{

    private DefaultTableModel tablaModelo;
    static Registro miRegistro;
    public Registro()
    {
        super("Registro", true, true, true, true);
        inicializarRegistro();
        miRegistro = this;
    }
    private void inicializarRegistro()
    {
        setSize(745,800);
        setToolTipText("Datos de Registro");
        setName("Registro");
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(131, 177, 248));
        getContentPane().add(panel1, BorderLayout.CENTER);

        //nuevo
        panel1.setLayout(new GridLayout(4,2,5,5));
        panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //panel de la tabla
//        tablaModelo = new DefaultTableModel();
//        tablaModelo.addColumn("id Movimiento");
//        tablaModelo.addColumn("Fecha y hora");
//        tablaModelo.addColumn("Producto");
//        tablaModelo.addColumn("Tipo de movimiento");
//        tablaModelo.addColumn("Cantidad");
//        tablaModelo.addColumn("Usuario");
//        tablaModelo.addColumn("Motivo");
        String[] columnas={"id","Fecha y hora","Producto","Tipo de movimiento","Cantidad","Usuario","Motivo"};
        tablaModelo = new DefaultTableModel(null,columnas)
        {
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        JTable tabla = new JTable(tablaModelo);

        JScrollPane tablaScrollPane = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel label1 = new JLabel("Fecha y hora");
        label1.setBounds(20,40,60,60);
        JTextField fecha_hora = new JTextField(30);
        fecha_hora.setBounds(70,45,140,25);
        fecha_hora.setOpaque(false);
        label1.setForeground(new Color(250, 249, 249));
        fecha_hora.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label2 = new JLabel("Producto");
        label2.setBounds(10,30,60,60);
        JTextField producto = new JTextField(30);
        producto.setOpaque(false);
        label2.setForeground(new Color(250, 249, 249));
        producto.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label3 = new JLabel("Tipo de movimiento");
        label3.setBounds(10,30,60,60);
        JTextField tipo_movimiento = new JTextField(30);
        tipo_movimiento.setOpaque(false);
        label3.setForeground(new Color(250, 249, 249));
        tipo_movimiento.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label4 = new JLabel("Cantidad");
        label4.setBounds(10,30,60,60);
        JTextField cantidad = new JTextField(30);
        cantidad.setOpaque(false);
        label4.setForeground(new Color(250, 249, 249));
        cantidad.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label5 = new JLabel("Usuario");
        label5.setBounds(10,30,60,60);
        JTextField usuario = new JTextField(30);
        usuario.setOpaque(false);
        label5.setForeground(new Color(250, 249, 249));
        usuario.setBorder(new MatteBorder(0,0,1,0, Color.white));

        JLabel label6 = new JLabel("Motivo");
        label6.setBounds(10,30,60,60);
        JTextField motivo = new JTextField(30);
        motivo.setOpaque(false);
        label6.setForeground(new Color(250, 249, 249));
        motivo.setBorder(new MatteBorder(0,0,1,0, Color.white));


        JButton enviarButton = new JButton("Guardar");
        enviarButton.setBounds(100,180,80,40);
        enviarButton.setForeground(new Color(131, 177, 248));
        addFilas(tablaModelo);//nuevo
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fecha_horatxt = fecha_hora.getText();
                String productotext = producto.getText();
                String tipo_movimientotxt = tipo_movimiento.getText();
                String cantidadtext = cantidad.getText();
                String usuariotext = usuario.getText();
                String motivotext = motivo.getText();
                if (fecha_horatxt.isEmpty() || productotext.isEmpty() || tipo_movimientotxt.isEmpty() || cantidadtext.isEmpty() || usuariotext.isEmpty() || motivotext.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Por favor, complete todos los campos antes de guardar.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return; // Salir si no se cumplen las condiciones
                }
                Registro01 registro01 = new Registro01(fecha_horatxt,productotext,tipo_movimientotxt,cantidadtext,usuariotext,motivotext);
                saveRegistro01(registro01);
                addFilas(tablaModelo);
                java.util.List<Registro01> registro01s = getRegistro();
                registro01s.forEach(System.out::println);

            }
        });

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBounds(100,180,80,40);
        actualizarButton.setForeground(new Color(131, 177, 248));
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Registro01> registros = getRegistro01();
                int i = tabla.getSelectedRow();
                boolean entradaValida = true;
                if (i < 0){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                    entradaValida = false;
                }
                else {
                    String fecha_hora1 = fecha_hora.getText().trim();
                    String producto1 = producto.getText().trim();
                    String tipo_movimiento1 = tipo_movimiento.getText().trim();
                    String cantidad1 = cantidad.getText().trim();
                    String usuario1 = usuario.getText();
                    String motivo1 = motivo.getText().trim();
                    int id = Integer.parseInt(tabla.getValueAt(i, 0).toString());
                    if (cantidad1.isEmpty()|| fecha_hora1.isEmpty()|| producto1.isEmpty() || tipo_movimiento1.isEmpty() || usuario1.isEmpty() || motivo1.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                        entradaValida = false;
                    }
                    if (entradaValida){
                        for (Registro01 registro : registros){
                            if (registro.getId() == id){
                                registro.setFecha_hora(fecha_hora1);
                                registro.setProducto(producto1);
                                registro.setTipo_movimiento(tipo_movimiento1);
                                registro.setCantidad(cantidad1);
                                registro.setUsuario(usuario1);
                                registro.setMotivo(motivo1);
                                updateRegistro01(registro);
                                addFilas(tablaModelo);
                                java.util.List<Registro01> registro01s = getRegistro();
                                registro01s.forEach(System.out::println);
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
                java.util.List<Registro01> registros = getRegistro01();
                int i = tabla.getSelectedRow();
                if (i < 0){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int id = Integer.parseInt( tabla.getValueAt(i, 0).toString());
                    for (Registro01 registro : registros)
                    {
                        if (registro.getId() == id)
                        {
                            deleteRegistro01(registro);
                            addFilas(tablaModelo);
                        }
                    }

                }
            }
        });


        panel1.add(label1);
        panel1.add(fecha_hora);
        panel1.add(label2);
        panel1.add(producto);
        panel1.add(label3);
        panel1.add(tipo_movimiento);
        panel1.add(label4);
        panel1.add(cantidad);
        panel1.add(label5);
        panel1.add(usuario);
        panel1.add(label6);
        panel1.add(motivo);
        panel1.add(enviarButton);
        panel1.add(actualizarButton);
        panel1.add(eliminarButton);
        add(panel1, BorderLayout.NORTH);
        add(tablaScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static Registro getInstancia()
    {
        return null == miRegistro ? (new Registro()) : miRegistro;

    }
    private static java.util.List<Registro01> getRegistro()
    {
        Transaction transaction= null;
        java.util.List<Registro01> registro01s = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            registro01s = session.createQuery("from Registro01", Registro01.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if (transaction != null)
            {
                transaction.rollback();
            }
        }

        return registro01s;
    }

    private static java.util.List<Registro01> getRegistro01(){
        java.util.List<Registro01> registro = new ArrayList<>();
        IGenericService<Registro01> registroService = new GenericServiceImpl<>(Registro01.class, HibernateUtil.getSessionFactory());
        registro = registroService.getAll();
        return  registro;
    }

    private static void saveRegistro01(Registro01 registro01)
    {
        IGenericService<Registro01> registro01Service = new GenericServiceImpl<>(Registro01.class, HibernateUtil.getSessionFactory());
        registro01Service.save(registro01);
    }
    private static void updateRegistro01(Registro01 registro01){
        IGenericService<Registro01> registroService = new GenericServiceImpl<>(Registro01.class, HibernateUtil.getSessionFactory());
        registroService.update(registro01);
    }
    private static void deleteRegistro01(Registro01 registro01){
        IGenericService<Registro01> registroService = new GenericServiceImpl<>(Registro01.class, HibernateUtil.getSessionFactory());
        registroService.delete(registro01);
    }
    //destrucracion de cliente (datos de la tabla clientes)
    private static void addFilas (DefaultTableModel model){
        model.setRowCount(0);
        List<Registro01> registros= getRegistro01();
        for (Registro01 registro : registros){
            int id = registro.getId();
            String fecha_horatxt = registro.getFecha_hora();
            String productotxt = registro.getProducto();
            String tipo_movimiento = registro.getTipo_movimiento();
            String cantidadtext = registro.getCantidad();
            String usuariotext = registro.getUsuario();
            String motivotext = registro.getMotivo();
            model.addRow(new Object[]{id,fecha_horatxt, productotxt, tipo_movimiento, cantidadtext, usuariotext, motivotext});
        }
    }
}
