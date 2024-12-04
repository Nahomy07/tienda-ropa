package org.nahomy.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Inventario")
public class Inventario01 {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "Cantidad")
    private String cantidad;

    @Column(name = "Nombre del producto")
    private String nombreProducto;

    @Column(name = "Precio")
    private String precio;

    public Inventario01() {
    }

    public Inventario01(String cantidad, String nombreProducto, String precio) {
        this.cantidad = cantidad;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "id=" + id +
                ", cantidad='" + cantidad + '\'' +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precio='" + precio + '\'' +
                '}';
    }
}
