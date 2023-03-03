package com.frank.compras.microcompras.repository;

import com.frank.compras.microcompras.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findAllByUserId(Long userId);
}
