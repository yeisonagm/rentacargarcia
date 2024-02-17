package ygm.rentacargarcia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ygm.rentacargarcia.models.Agencia;
import ygm.rentacargarcia.repository.AgenciaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AgenciaServiceImp implements AgenciaService {
    @Autowired
    private AgenciaRepository agenciaRepository;

    @Override
    public List<Agencia> getAllAgencias() {
        return (List<Agencia>) agenciaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Agencia> getAgenciaById(Long idAgencia) {
        return agenciaRepository.findById(idAgencia);
    }

    @Override
    public Agencia saveAgencia(Agencia agencia) {
        return agenciaRepository.save(agencia);
    }

    @Override
    public void deleteAgencia(Long idAgencia) {
        agenciaRepository.deleteById(idAgencia);
    }
}
