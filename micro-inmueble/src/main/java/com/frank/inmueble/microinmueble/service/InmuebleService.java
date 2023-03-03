package com.frank.inmueble.microinmueble.service;

import com.frank.inmueble.microinmueble.model.Inmueble;

import java.util.List;

public interface InmuebleService {
    Inmueble saveInmueble(Inmueble inmueble);

    void deleteInmueble(Long id);

    List<Inmueble> findAllInmueble();
}
