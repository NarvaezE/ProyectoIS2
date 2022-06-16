package com.example.proyectois2;

public class Meds {
    private String nombre;
    private String id;
    private String cantidad;
    private String tipoCatalogo;

    public Meds() {
    }
    public Meds(String nombre, String id, String cantidad, String tipoCatalogo) {
        this.nombre = nombre;
        this.id = id;
        this.cantidad = cantidad;
        this.tipoCatalogo = tipoCatalogo;
    }

    @Override
    public String toString() {
        return nombre+"\n"+cantidad;
    }
    public String verId(){
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setTipoCatalogo(String tipoCatalogo) {
        this.tipoCatalogo = tipoCatalogo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getTipoCatalogo() {
        return tipoCatalogo;
    }





}
