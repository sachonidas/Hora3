package es.luissachaarancibiabazan.hora3;

import android.support.v7.widget.RecyclerView;

import java.sql.Date;

public class Hora  {

    protected int id;
    protected String numeroHoras;
    protected String fechaHora;
    protected String descripcion;

    public int getId() {
        return id;
    }

    public String getNumeroHoras() {return numeroHoras;}

    public void setNumeroHoras(String numeroHoras) {this.numeroHoras = numeroHoras;}

    public void setId(int id) {this.id = id;}

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
}
