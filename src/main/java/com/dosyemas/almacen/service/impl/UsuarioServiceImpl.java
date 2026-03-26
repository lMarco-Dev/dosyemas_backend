package com.dosyemas.almacen.service.impl;

import com.dosyemas.almacen.dto.request.UsuarioRequestDTO;
import com.dosyemas.almacen.dto.response.UsuarioResponseDTO;
import com.dosyemas.almacen.entity.Rol;
import com.dosyemas.almacen.entity.Usuario;
import com.dosyemas.almacen.exception.ResourceNotFoundException;
import com.dosyemas.almacen.repository.RolRepository;
import com.dosyemas.almacen.repository.UsuarioRepository;
import com.dosyemas.almacen.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + dto.getIdRol()));

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .passwordHash(dto.getPassword()) // Temporal hasta integrar Spring Security
                .rol(rol)
                .activo(dto.getActivo()) // Usamos el booleano que viene en tu DTO
                .createdAt(LocalDateTime.now()) // Generamos la fecha de creación
                .build();

        return toResponseDTO(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizar(Integer id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + dto.getIdRol()));

        if (!usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado por otro usuario");
        }

        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setRol(rol);
        usuario.setActivo(dto.getActivo());

        // Actualizamos contraseña solo si nos envían una nueva (importante para el método PUT)
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPasswordHash(dto.getPassword());
        }

        return toResponseDTO(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public void cambiarEstado(Integer id, Boolean estado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        usuario.setActivo(estado);
        usuarioRepository.save(usuario);
    }

    // --- MAPPER PRIVADO CORREGIDO ---
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreCompleto(usuario.getNombre() + " " + usuario.getApellido())
                .email(usuario.getEmail())
                .rol(usuario.getRol().getNombre())
                .idRol(usuario.getRol().getIdRol()) // Pasamos el ID del rol para los combos del Frontend
                .activo(usuario.getActivo())
                .build();
    }
}