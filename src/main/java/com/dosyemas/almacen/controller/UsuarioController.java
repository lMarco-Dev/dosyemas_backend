package com.dosyemas.almacen.controller;

import com.dosyemas.almacen.dto.request.UsuarioRequestDTO;
import com.dosyemas.almacen.dto.response.UsuarioResponseDTO;
import com.dosyemas.almacen.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // 1. GET /api/v1/usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // 2. GET /api/v1/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // 3. POST /api/v1/usuarios
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO nuevoUsuario = usuarioService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    // 4. PUT /api/v1/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Integer id,
                                                         @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    // 5. PATCH /api/v1/usuarios/{id}/estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id,
                                              @RequestBody Map<String, Boolean> payload) {
        Boolean estado = payload.get("activo");
        usuarioService.cambiarEstado(id, estado);
        return ResponseEntity.ok().build();
    }
}