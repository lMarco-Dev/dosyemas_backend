package com.dosyemas.almacen.controller;

import com.dosyemas.almacen.dto.request.CategoriaRequestDTO;
import com.dosyemas.almacen.dto.response.CategoriaResponseDTO;
import com.dosyemas.almacen.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:5173") // Descomenta esto luego para permitir que tu React (Vite) se conecte
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas()); // Devuelve 200 OK
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crear(@Valid @RequestBody CategoriaRequestDTO dto) {
        // @Valid activa las validaciones que pusimos en el DTO (@NotBlank, etc.)
        CategoriaResponseDTO creada = categoriaService.crear(dto);
        return new ResponseEntity<>(creada, HttpStatus.CREATED); // Devuelve 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable Integer id,
                                                           @Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.ok(categoriaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }
}