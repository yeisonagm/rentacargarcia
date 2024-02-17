package ygm.rentacargarcia.services;

import ygm.rentacargarcia.models.Auto;

import java.util.List;
import java.util.Optional;

public interface AutoService {
    List<Auto> getAllAutos();

    Optional<Auto> getAutoById(Long idAuto);

    Auto saveAuto(Auto auto);

    void deleteAuto(Long idAuto);
}
