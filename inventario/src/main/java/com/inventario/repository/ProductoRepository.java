package com.inventario.repository;

import com.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.cantidad <= p.stockMinimo")
    List<Producto> findProductosConStockBajo();

    @Query("SELECT COUNT(p) FROM Producto p")
    long countTotalProductos();

    @Query("SELECT SUM(p.cantidad) FROM Producto p")
    Long sumTotalUnidades();
}