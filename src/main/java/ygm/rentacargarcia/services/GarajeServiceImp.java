package ygm.rentacargarcia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ygm.rentacargarcia.models.Garaje;
import ygm.rentacargarcia.repository.GarajeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GarajeServiceImp implements GarajeService {
    @Autowired
    private GarajeRepository garajeRepository;

    @Override
    public List<Garaje> getAllGarajes() {
        return (List<Garaje>) garajeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Garaje> getGarajeById(Long idGaraje) {
        return garajeRepository.findById(idGaraje);
    }

    @Override
    public Garaje saveGaraje(Garaje garaje) {
        return garajeRepository.save(garaje);
    }

    @Override
    public void deleteGaraje(Long idGaraje) {
        garajeRepository.deleteById(idGaraje);
    }
}
