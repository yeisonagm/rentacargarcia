package ygm.rentacargarcia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ygm.rentacargarcia.models.Auto;
import ygm.rentacargarcia.models.Reserva;
import ygm.rentacargarcia.repository.AutoRepository;
import ygm.rentacargarcia.repository.ReservaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImp implements ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private AutoRepository autoRepository;

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

    @Override
    @Transactional
    public Reserva addAutoToReserva(Long idReserva, Long idAuto) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(idReserva);
        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            Optional<Auto> autoOpt = autoRepository.findById(idAuto);
            if (autoOpt.isPresent()) {
                Auto auto = autoOpt.get();
                reserva.getAutos().add(auto);
                return reservaRepository.save(reserva);
            }
        }
        return reservaOpt.get();
    }
}
