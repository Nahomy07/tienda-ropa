package org.nahomy.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Clientes")
public class Cliente01
{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido")
    private String apellido;

    @Column(name = "Telefono")
    private String telefono;

    public Cliente01(){

    }

    public Cliente01(String nombre, String apellido, String telefono)
    {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public int getId(){return  id;}

    public void setId(int id){this.id = id;}

    public String getNombre(){return nombre;}

    public void setNombre(String nombre){this.nombre = nombre;}

    public String setApellido(){return apellido;}

    public void setApellido(String nombre){this.apellido = apellido;}

    public String setTelefono(){return telefono;}

    public void setTelefono(String telefono){this.telefono = telefono;}

    @Override
    public  String toString()
    {
        return "Cliente{" +
                "id=" + id +
                ", Nombre='" + nombre + '\'' +
                ", Apellido='" + apellido + '\'' +
                ", Telefono='" + telefono + '\'' +
                '}';
    }
}
