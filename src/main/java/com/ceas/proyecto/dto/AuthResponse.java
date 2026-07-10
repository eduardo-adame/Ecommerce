package com.ceas.proyecto.dto;

public class AuthResponse {
    private String token;
    private String username;
    private String nombre;

    public AuthResponse() {
    }

    public AuthResponse(String token, String username, String nombre) {
        this.token = token;
        this.username = username;
        this.nombre = nombre;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
