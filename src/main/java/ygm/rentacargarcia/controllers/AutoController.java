package ygm.rentacargarcia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ygm.rentacargarcia.models.Auto;
import ygm.rentacargarcia.models.Garaje;
import ygm.rentacargarcia.services.AutoService;
import ygm.rentacargarcia.services.GarajeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/autos")
public class AutoController {
    @Autowired
    private AutoService autoService;

    @Autowired
    private GarajeService garajeService;

    @GetMapping
    public List<Auto> getAllAutos() {
        return autoService.getAllAutos();
    }

    @GetMapping("/{id}") // No esta funcionando, error 500
    public ResponseEntity<?> getAutoById(@PathVariable Long id) {
        Optional<Auto> autoOpt = autoService.getAutoById(id);
        if (autoOpt.isPresent()) {
            return ResponseEntity.ok(autoOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Auto auto) {
        if (auto.getGaraje() != null && auto.getGaraje().getIdGaraje() != null) {
            Optional<Garaje> garajeOpt = garajeService.getGarajeById(auto.getGaraje().getIdGaraje());
            if (garajeOpt.isEmpty()) {
                //return ResponseEntity.notFound().build();
                String messaje = "Garaje que desea asignar no existe id(" + auto.getGaraje().getIdGaraje() + ")";
                return new ResponseEntity<>(messaje, HttpStatus.NOT_FOUND);
            }
            auto.setGaraje(garajeOpt.get());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(autoService.saveAuto(auto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Auto auto) {
        Optional<Auto> autoOpt = autoService.getAutoById(id);
        if (autoOpt.isPresent()) {
            Auto autoUpdated = autoOpt.get();
            autoUpdated.setMatricula(auto.getMatricula());
            autoUpdated.setMarca(auto.getMarca());
            autoUpdated.setModelo(auto.getModelo());
            autoUpdated.setColor(auto.getColor());
            autoUpdated.setPrecioReserva(auto.getPrecioReserva());
            autoUpdated.setGasolina(auto.getGasolina());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(autoService.saveAuto(autoUpdated));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Auto> autoOpt = autoService.getAutoById(id);
        try {
            if (autoOpt.isPresent()) {
                autoService.deleteAuto(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El auto no se puede eliminar, tiene reservas asociadas");
        }
    }
}