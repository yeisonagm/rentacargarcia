package ygm.rentacargarcia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ygm.rentacargarcia.models.Agencia;
import ygm.rentacargarcia.models.Reserva;
import ygm.rentacargarcia.services.AgenciaService;
import ygm.rentacargarcia.services.ReservaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private AgenciaService agenciaService;

    @GetMapping
    public List<Reserva> getAllReservas() {
        return reservaService.getAllReservas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservaById(@PathVariable Long id) {
        Optional<Reserva> reservaOpt = reservaService.getReservaById(id);
        if (reservaOpt.isPresent()) {
            return ResponseEntity.ok(reservaOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Reserva reserva) {
        if (reserva.getAgencia() != null && reserva.getAgencia().getIdAgencia() != null) {
            Optional<Agencia> agenciaOpt = agenciaService.getAgenciaById(reserva.getAgencia().getIdAgencia());
            if (agenciaOpt.isEmpty()) {
                String messaje = "Agencia no existe: id(" + reserva.getAgencia().getIdAgencia() + ")";
                return new ResponseEntity<>(messaje, HttpStatus.NOT_FOUND);
            }
            reserva.setAgencia(agenciaOpt.get());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaService.saveReserva(reserva));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Reserva reserva) {
        Optional<Reserva> reservaOpt = reservaService.getReservaById(id);
        if (reservaOpt.isPresent()) {
            Reserva reservaUpdated = reservaOpt.get();
            reservaUpdated.setFechaInicio(reserva.getFechaInicio());
            reservaUpdated.setFechaFinal(reserva.getFechaFinal());
            reservaUpdated.setPrecioTotal(reserva.getPrecioTotal());
            if (reserva.getCliente() != null) {
                reservaUpdated.setCliente(reserva.getCliente());
            }
            if (reserva.getAgencia() != null) {
                reservaUpdated.setAgencia(reserva.getAgencia());
            }
            if (reserva.getAutos() != null) {
                reservaUpdated.setAutos(reserva.getAutos());
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(reservaService.saveReserva(reservaUpdated));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Reserva> reservaOpt = reservaService.getReservaById(id);
        if (reservaOpt.isPresent()) {
            reservaService.deleteReserva(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
