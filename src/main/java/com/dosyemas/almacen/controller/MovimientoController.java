package com.dosyemas.almacen.controller;

import com.dosyemas.almacen.dto.request.MovimientoRequestDTO;
import com.dosyemas.almacen.dto.response.MovimientoResponseDTO;
import com.dosyemas.almacen.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movimientos")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class MovimientoController {

    private final MovimientoService movimientoService;

    // 1. POST -> Registrar /api/v1/movimientos
    @PostMapping
    public ResponseEntity<MovimientoResponseDTO> registrar(@Valid @RequestBody MovimientoRequestDTO dto) {
        // TODO: reemplazar con idUsuario del token JWT
        MovimientoResponseDTO registrarMovimiento = movimientoService.registrar(dto, 1);

        return ResponseEntity.status(HttpStatus.CREATED).body(registrarMovimiento);
    }

    // 2. GET -> listarPorProducto /api/v1/movimientos/producto/{idProducto}
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<MovimientoResponseDTO>> listarPorProducto(@PathVariable Integer idProducto){
        return ResponseEntity.ok(movimientoService.listarPorProducto(idProducto));
    }

    // 3. GET -> listarDeHoy /api/v1/movimientos/hoy
    @GetMapping("/hoy")
    public ResponseEntity<List<MovimientoResponseDTO>> listarDeHoy() {
        return ResponseEntity.ok(movimientoService.listarDeHoy());
    }
}