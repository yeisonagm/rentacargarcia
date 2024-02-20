package ygm.rentacargarcia.services;

import ygm.rentacargarcia.models.Reserva;

import java.util.List;
import java.util.Optional;

public interface ReservaService {
    List<Reserva> getAllReservas();

    Optional<Reserva> getReservaById(Long idReserva);

    Reserva saveReserva(Reserva reserva);

    void deleteReserva(Long idReserva);

    Reserva addAutoToReserva(Long idReserva, Long idAuto);
}
