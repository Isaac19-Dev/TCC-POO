package com.parqueadero.api.aplication.Request;

// Record de Java (inmutable): contiene los datos que el cliente envía en el body del login.
// Un record genera automáticamente: constructor, getters (username(), password()),
// equals(), hashCode() y toString(). Es ideal para datos de entrada (DTOs de request).
// Campos:
//   username → nombre de usuario (ej: "admin")
//   password → contraseña (ej: "admin123")
public record AuthRequest(String username, String password) {}
