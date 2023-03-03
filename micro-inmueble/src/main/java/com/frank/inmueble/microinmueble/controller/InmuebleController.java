package com.frank.inmueble.microinmueble.controller;

import com.frank.inmueble.microinmueble.model.Inmueble;
import com.frank.inmueble.microinmueble.service.InmuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inmueble")
public class InmuebleController {

    @Autowired
    private InmuebleService inmuebleService;

    @PostMapping
    public ResponseEntity<Inmueble> saveInmueble(@RequestBody Inmueble request) {
        return new ResponseEntity<>(inmuebleService.saveInmueble(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{inmuebleId}")
    public ResponseEntity<?> deleteInmueble(@PathVariable Long inmuebleId) {
        inmuebleService.deleteInmueble(inmuebleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Inmueble>> getList() {
        return ResponseEntity.ok(inmuebleService.findAllInmueble());
    }
}
