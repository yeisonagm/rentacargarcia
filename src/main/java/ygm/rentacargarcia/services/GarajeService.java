package ygm.rentacargarcia.services;

import ygm.rentacargarcia.models.Garaje;

import java.util.List;
import java.util.Optional;

public interface GarajeService {
    List<Garaje> getAllGarajes();

    Optional<Garaje> getGarajeById(Long idGaraje);

    Garaje saveGaraje(Garaje garaje);

    void deleteGaraje(Long idGaraje);
}
