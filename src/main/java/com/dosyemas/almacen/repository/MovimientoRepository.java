package com.dosyemas.almacen.repository;

import com.dosyemas.almacen.entity.Movimiento;
import com.dosyemas.almacen.entity.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    // Ordenar por fecha
    List<Movimiento> findByProductoIdProductoOrderByFechaDesc(Integer idProducto);

    // Filtrar movimientos por tipo (Entrada / Salida)
    List<Movimiento> findByTipo(TipoMovimiento tipo);

    // Movimientos de hoy
    @Query(value = "SELECT * FROM movimientos WHERE DATE(fecha) = CURDATE() ORDER BY fecha DESC", nativeQuery = true)
    List<Movimiento> findMovimientosDeHoy();
}
