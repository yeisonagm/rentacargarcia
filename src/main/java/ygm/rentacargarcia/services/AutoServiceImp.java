package ygm.rentacargarcia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ygm.rentacargarcia.models.Auto;
import ygm.rentacargarcia.repository.AutoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AutoServiceImp implements AutoService {
    @Autowired
    private AutoRepository autoRepository;

    @Override
    public List<Auto> getAllAutos() {
        return (List<Auto>) autoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Auto> getAutoById(Long idAuto) {
        return autoRepository.findById(idAuto);
    }

    @Override
    public Auto saveAuto(Auto auto) {
        return autoRepository.save(auto);
    }

    @Override
    public void deleteAuto(Long idAuto) {
        autoRepository.deleteById(idAuto);
    }
}
