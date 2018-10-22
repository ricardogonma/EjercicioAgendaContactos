package com.example.ricardo.ejercicioagendacontactos;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Contacto implements Parcelable {

    private String nombre;
    private int numero;

    public Contacto(String nombre, int numero){
        this.nombre = nombre;
        this.numero = numero;
    }

    protected Contacto(Parcel in) {
        nombre = in.readString();
        numero = in.readInt();
    }

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @NonNull
    @Override
    public String toString() {
        return "Nombre: "+this.getNombre()+" Numero: "+this.getNumero();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeInt(numero);
    }
}
