package com.dosyemas.almacen.controller;

import com.dosyemas.almacen.dto.request.ProductoRequestDTO;
import com.dosyemas.almacen.dto.response.ProductoResponseDTO;
import com.dosyemas.almacen.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*") //Conectar a react
public class ProductoController {

    private final ProductoService productoService;

    //1. GET /api/v1/productos
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarActivos() {
        return  ResponseEntity.ok(productoService.listarActivos());
    }

    //2. GET /api/v1/productos/stock-bajo
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<ProductoResponseDTO>> listarStockBajo() {
        return ResponseEntity.ok(productoService.listarConStockCritico());
    }

    //3. GET /api/v1/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    //4. POST /api/v1/productos
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        ProductoResponseDTO nuevoProducto = productoService.crear(dto);

        // HttpStatus.CREATED -> para cumplir con la tabla
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    //5. PUT /api/v1/productos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoRequestDTO dto){
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    //6. PATCH /api/v1/productos/{id}/estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id,
                                              @RequestBody Map<String, Boolean> payload) {

        Boolean estado = payload.get("activo");
        productoService.cambiarEstado(id, estado);
        return ResponseEntity.ok().build();
    }
}
