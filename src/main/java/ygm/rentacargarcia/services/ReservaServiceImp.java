package ygm.rentacargarcia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ygm.rentacargarcia.models.Reserva;
import ygm.rentacargarcia.repository.ReservaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImp implements ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public List<Reserva> getAllReservas() {
        return (List<Reserva>) reservaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reserva> getReservaById(Long idReserva) {
        return reservaRepository.findById(idReserva);
    }

    @Override
    public Reserva saveReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public void deleteReserva(Long idReserva) {
        reservaRepository.deleteById(idReserva);
    }
}
