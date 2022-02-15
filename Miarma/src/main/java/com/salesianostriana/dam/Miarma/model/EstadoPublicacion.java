package com.salesianostriana.dam.Miarma.model;

public enum EstadoPublicacion {

    PUBLICO("PUBLICO"), PRIVADO("PRIVADO");

    private String valor;

    private EstadoPublicacion(String valor){this.valor= valor;}

    public String getValor(){return valor;}

    public void setValor(String valor){this.valor= valor;}

}
