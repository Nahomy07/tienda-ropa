package org.nahomy.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "Usuarios")

public class Usuario
{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "Usuario")
    private  String Usuario;

    @Column(name = "Contraseña")
    private String Contraseña;

    public Usuario()
    {

    }

    public Usuario(String Usuario, String Contraseña)
    {
        this.Usuario = Usuario;
        this.Contraseña = Contraseña;
    }

    public int getId() {return id;}

    public void setId(int id){this.id = id;}

    public String getUsuario(){return Usuario;}

    public void setUsuario(String usuario){this.Usuario = usuario;}

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña){this.Contraseña = contraseña;}

    public  String toString()
    {
        return "Usuario{" +
                "id=" + id +
                ", Usuario='" + Usuario + '\'' +
                ", Contraseña='" + Contraseña + '\'' +
                '}';
    }
}


