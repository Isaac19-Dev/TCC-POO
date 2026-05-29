package com.parqueadero.api.aplication.Request; // Paquete para peticiones HTTP
public record AuthRequest(String username, String password) {} // Objeto inmutable que define la estructura JSON que se espera recibir del cliente al iniciar sesión ({"username": "", "password": ""})
