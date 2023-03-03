package com.frank.inmueble.microinmueble.service;

import com.frank.inmueble.microinmueble.model.Inmueble;
import com.frank.inmueble.microinmueble.repository.InmuebleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InmuebleServiceImpl implements InmuebleService {
    @Autowired
    private InmuebleRepository inmuebleRepository;

    @Override
    public Inmueble saveInmueble(Inmueble inmueble) {
        inmueble.setFechaCreacion(LocalDateTime.now());
        return inmuebleRepository.save(inmueble);
    }

    @Override
    public void deleteInmueble(Long id) {
        inmuebleRepository.deleteById(id);
    }

    @Override
    public List<Inmueble> findAllInmueble() {
        return  inmuebleRepository.findAll();
    }
}
