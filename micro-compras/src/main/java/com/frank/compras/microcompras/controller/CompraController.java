package com.frank.compras.microcompras.controller;

import com.frank.compras.microcompras.model.Compra;
import com.frank.compras.microcompras.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/compra")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @PostMapping
    public ResponseEntity<Compra> saveCompra(@RequestBody Compra compra) {
        return new ResponseEntity<>(compraService.saveCompra(compra), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Compra>> listaCompra(@PathVariable Long userId) {
        return ResponseEntity.ok(compraService.findAllComprasOfUser(userId));
    }
}
