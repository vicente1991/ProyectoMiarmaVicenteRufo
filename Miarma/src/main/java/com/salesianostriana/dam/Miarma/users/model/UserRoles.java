package com.salesianostriana.dam.Miarma.users.model;

public enum UserRoles{
    PUBLICO("PUBLICO"), PRIVADO("PRIVADO");

    private String valor;

    private UserRoles(String valor){this.valor= valor;}

    public String getValor(){return valor;}

    public void setValor(String valor){this.valor= valor;}
}

