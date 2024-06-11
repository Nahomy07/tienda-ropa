package org.nahomy.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Productos")
public class Producto01
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Precio")
    private String precio;

    @Column(name = "Descripcion")
    private String descripcion;

    public Producto01(String nombre, String precio, String descripcion)
    {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Producto01() {
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getNombre(){return nombre;}

    public void setNombre(String nombre){this.nombre = nombre;}

    public String getPrecio(){return precio;}

    public void setPrecio(String precio){this.precio = precio;}

    public String getDescripcion(){return descripcion;}

    public void setDescripcion(String descripcion){this.descripcion = descripcion;}

    @Override
    public  String toString()
    {
        return "Producto{" +
                "id=" + id +
                ", Nombre='" + nombre + '\'' +
                ", Precio='" + precio + '\'' +
                ", Descripcion='" + descripcion + '\'' +
                '}';
    }
}
