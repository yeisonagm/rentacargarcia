package ygm.rentacargarcia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ygm.rentacargarcia.models.Agencia;
import ygm.rentacargarcia.services.AgenciaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/agencias")
public class AgenciaController {
    @Autowired
    private AgenciaService agenciaService;

    @GetMapping
    public List<Agencia> getAllAgencias() {
        return agenciaService.getAllAgencias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAgenciaById(@PathVariable Long id) {
        Optional<Agencia> agenciaOpt = agenciaService.getAgenciaById(id);
        if (agenciaOpt.isPresent()) {
            return ResponseEntity.ok(agenciaOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Error 415 al momento de hacer el POST
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Agencia agencia) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(agenciaService.saveAgencia(agencia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Agencia agencia) {
        Optional<Agencia> agenciaOpt = agenciaService.getAgenciaById(id);
        if (agenciaOpt.isPresent()) {
            Agencia agenciaUpdated = agenciaOpt.get();
            agenciaUpdated.setNombre(agencia.getNombre());
            agenciaUpdated.setDireccion(agencia.getDireccion());
            agenciaUpdated.setTelefono(agencia.getTelefono());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(agenciaService.saveAgencia(agenciaUpdated));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Agencia> agenciaOpt = agenciaService.getAgenciaById(id);
        if (agenciaOpt.isPresent()){
            agenciaService.deleteAgencia(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
