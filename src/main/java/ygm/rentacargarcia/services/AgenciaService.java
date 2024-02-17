package ygm.rentacargarcia.services;

import ygm.rentacargarcia.models.Agencia;

import java.util.List;
import java.util.Optional;

public interface AgenciaService {
    List<Agencia> getAllAgencias();

    Optional<Agencia> getAgenciaById(Long idAgencia);

    Agencia saveAgencia(Agencia agencia);

    void deleteAgencia(Long idAgencia);
}
