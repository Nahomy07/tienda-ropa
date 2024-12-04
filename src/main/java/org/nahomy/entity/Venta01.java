package org.nahomy.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Venta")
public class Venta01 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "Cantidad")
    private String cantidad;

    @Column(name = "Producto")
    private String producto;

    @Column(name = "Precio")
    private String precio;

    public Venta01() {
    }

    public Venta01(String cantidad, String producto, String precio) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.precio = precio;
    }

    public int getId(){
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

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


    @Override
    public String toString() {
        return "Venta01{" +
                "id=" + id +
                ", cantidad='" + cantidad + '\'' +
                ", producto='" + producto + '\'' +
                ", precio='" + precio + '\'' +
                //", precioTotal='" + precioTotal + '\'' +
                '}';
    }
}
