package com.example.proyectois2;


import android.os.Parcel;
import android.os.Parcelable;

public class Usuarios implements Parcelable {
    private String id;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String nick;
    private String correo;
    private String id_tip;
    private String nom_tip;
    private String id_tutor;


    public Usuarios() {
    }
    public Usuarios(String id, String nombre,String apellido,String nick,String correo, String contraseña, String id_tip, String nom_tip,String id_tutor) {
        this.id = id;
        this.nombre = nombre;
        this.apellido=apellido;
        this.nick=nick;
        this.correo=correo;
        this.contrasena = contraseña;
        this.id_tip = id_tip;
        this.nom_tip = nom_tip;
        this.id_tutor=id_tutor;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNom_tip(String nom_tip) {
        this.nom_tip = nom_tip;
    }

    public void setId_tutor(String id_tutor) {
        this.id_tutor = id_tutor;
    }

    public String getCorreo() {
        return correo;
    }

    public String getId_tutor() {
        return id_tutor;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNick() {
        return nick;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getId_tip() {
        return id_tip;
    }

    public String getNom_tip() {
        return nom_tip;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setId_tip(String id_tip) {
        this.id_tip = id_tip;
    }



    @Override
    public String toString() {
        return "" + id + "     " + nombre;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.contrasena);
        dest.writeString(this.id_tip);
        dest.writeString(this.nom_tip);
    }

    protected Usuarios(Parcel in) {
        this.id = in.readString();
        this.nombre = in.readString();
        this.apellido = in.readString();
        this.nick = in.readString();
        this.correo=in.readString();
        this.contrasena = in.readString();
        this.id_tip = in.readString();
        this.nom_tip = in.readString();
        this.id_tutor=in.readString();
    }

    public static final Parcelable.Creator<Usuarios> CREATOR = new Parcelable.Creator<Usuarios>() {
        @Override
        public Usuarios createFromParcel(Parcel source) {
            return new Usuarios(source);
        }

        @Override
        public Usuarios[] newArray(int size) {
            return new Usuarios[size];
        }
    };
}
