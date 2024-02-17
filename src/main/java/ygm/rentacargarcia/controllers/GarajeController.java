package ygm.rentacargarcia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ygm.rentacargarcia.models.Garaje;
import ygm.rentacargarcia.services.GarajeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/garajes")
public class GarajeController {
    @Autowired
    private GarajeService garajeService;

    @GetMapping
    public List<Garaje> getAllGarajes() {
        return garajeService.getAllGarajes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGarajeById(@PathVariable Long id) {
        Optional<Garaje> garajeOpt = garajeService.getGarajeById(id);
        if (garajeOpt.isPresent()) {
            return ResponseEntity.ok(garajeOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Garaje garaje) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(garajeService.saveGaraje(garaje));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Garaje garaje) {
        Optional<Garaje> garajeOpt = garajeService.getGarajeById(id);
        if (garajeOpt.isPresent()) {
            Garaje garajeUpdated = garajeOpt.get();
            garajeUpdated.setNombre(garaje.getNombre());
            garajeUpdated.setDireccion(garaje.getDireccion());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(garajeService.saveGaraje(garajeUpdated));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Garaje> garajeOpt = garajeService.getGarajeById(id);
        if (garajeOpt.isPresent()){
            garajeService.deleteGaraje(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
