package com.dosyemas.almacen.repository;

import com.dosyemas.almacen.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Buscar productos por su estado activo
    List<Producto> findByActivoTrue(); // Un poco más limpio que pasar el boolean como parámetro

    // Filtra productos por categoría y estado activo
    List<Producto> findByCategoriaIdCategoriaAndActivoTrue(Integer idCategoria);

    // SOLUCIÓN PARA EL STOCK: Compara las dos columnas directamente usando JPQL
    @Query("SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo AND p.activo = true")
    List<Producto> findProductosConStockCritico();

    // Busca producto por Codigo
    Optional<Producto> findByCodigo(String codigo);
}