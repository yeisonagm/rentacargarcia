package ygm.rentacargarcia.repository;

import org.springframework.data.repository.CrudRepository;
import ygm.rentacargarcia.models.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{
}
