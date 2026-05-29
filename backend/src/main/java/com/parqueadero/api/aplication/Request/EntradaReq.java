package com.parqueadero.api.aplication.Request; // Paquete peticiones
public record EntradaReq(Long vehiculoId, Long espacioId) {} // Objeto inmutable que recibe el JSON del frontend cuando se va a registrar la entrada de un vehículo. Puede recibir el espacioId nulo si se desea asignación automática.
