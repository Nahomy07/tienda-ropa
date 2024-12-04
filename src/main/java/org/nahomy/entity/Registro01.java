package org.nahomy.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Registro")
public class Registro01
{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "Fecha y hora")
    private String fecha_hora;

    @Column(name = "Producto")
    private String producto;

    @Column(name = "Tipo de movimiento")
    private String tipo_movimiento;

    @Column(name = "Cantidad")
    private String cantidad;

    @Column(name = "Usuario")
    private String usuario;

    @Column(name = "Motivo")
    private String motivo;

    public Registro01() {
    }

    public Registro01(String fecha_hora, String producto, String tipo_movimiento, String cantidad, String usuario, String motivo) {
        this.fecha_hora = fecha_hora;
        this.producto = producto;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
        this.usuario = usuario;
        this.motivo = motivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Registro01{" +
                "id=" + id +
                ", fecha_hora='" + fecha_hora + '\'' +
                ", producto='" + producto + '\'' +
                ", tipo_movimiento='" + tipo_movimiento + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", usuario='" + usuario + '\'' +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
