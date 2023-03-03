package com.frank.compras.microcompras.service;

import com.frank.compras.microcompras.model.Compra;

import java.util.List;

public interface CompraService {
    Compra saveCompra(Compra compra);

    List<Compra> findAllComprasOfUser(Long userId);
}
