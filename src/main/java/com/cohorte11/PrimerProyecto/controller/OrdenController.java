package com.cohorte11.PrimerProyecto.controller;

import com.cohorte11.PrimerProyecto.model.Orden;
import com.cohorte11.PrimerProyecto.model.EstadoOrden;
import com.cohorte11.PrimerProyecto.repository.OrdenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenRepository ordenRepository;

    public OrdenController(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    @GetMapping
    public ResponseEntity<List<Orden>> obtenerTodas() {
        return ResponseEntity.ok(ordenRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerPorId(@PathVariable Long id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con id: " + id));
        return ResponseEntity.ok(orden);
    }

    @PostMapping
    public ResponseEntity<Orden> crear(@RequestBody Orden orden) {
        Orden guardada = ordenRepository.save(orden);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Orden> actualizarEstado(@PathVariable Long id,
                                                  @RequestParam EstadoOrden estado) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con id: " + id));
        orden.setEstado(estado);
        return ResponseEntity.ok(ordenRepository.save(orden));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!ordenRepository.existsById(id)) {
            throw new RuntimeException("Orden no encontrada con id: " + id);
        }
        ordenRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
