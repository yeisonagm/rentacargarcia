package ygm.rentacargarcia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ygm.rentacargarcia.models.Agencia;
import ygm.rentacargarcia.models.Reserva;
import ygm.rentacargarcia.services.AgenciaService;
import ygm.rentacargarcia.services.ClienteService;
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

    @Autowired
    private ClienteService clienteService;

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
            reservaUpdated.setCliente(reserva.getCliente());
            reservaUpdated.setAgencia(reserva.getAgencia());
            reservaUpdated.setAutos(reserva.getAutos());
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaService.saveReserva(reservaUpdated));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idReserva}/auto/{idAuto}")
    public ResponseEntity<?> addAutoToReserva(@PathVariable Long idReserva, @PathVariable Long idAuto) {
        Reserva reserva = reservaService.addAutoToReserva(idReserva, idAuto);
        if (reserva == null || reserva.getIdReserva() == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Reserva> reservaOpt = reservaService.getReservaById(id);
        if (reservaOpt.isPresent()){
            reservaService.deleteReserva(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
