package com.jesus.domain;

import com.jesus.domain.AgenteCasting;
import com.jesus.domain.Candidato;
import com.jesus.domain.Cliente;

import java.util.Date;

public class Casting {
    private int id;
    private String nombre;
    private String descripcion;
    private Cliente idCliente;
    private AgenteCasting idAgente;
    private Candidato idCandidato;
    private Date fechaContratacion;

    public Casting(int id, String nombre, String descripcion, Cliente idCliente, AgenteCasting idAgente, Candidato idCandidato, Date fechaContratacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCliente = idCliente;
        this.idAgente = idAgente;
        this.idCandidato = idCandidato;
        this.fechaContratacion = fechaContratacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void FechaContratacion(Date fechaDeContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public AgenteCasting getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(AgenteCasting idAgente) {
        this.idAgente = idAgente;
    }

    public Candidato getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Candidato idCandidato) {
        this.idCandidato = idCandidato;
    }
}
