package ygm.rentacargarcia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ygm.rentacargarcia.models.Cliente;
import ygm.rentacargarcia.services.ClienteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        Optional<Cliente> clienteOpt = clienteService.getClienteById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            if (cliente.getAval() != null) {
                cliente.getAval().setAval(null);
                cliente.getAval().setReservas(null);
            }
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Cliente cliente) {
        if (cliente.getAval() != null && cliente.getAval().getIdCliente() != null) {
            Optional<Cliente> avalOpt = clienteService.getClienteById(cliente.getAval().getIdCliente());
            if (avalOpt.isEmpty()) {
                return new ResponseEntity<>("Aval no encontrado", HttpStatus.NOT_FOUND);
            }
            cliente.setAval(avalOpt.get());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.saveCliente(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        Optional<Cliente> clienteOpt = clienteService.getClienteById(id);
        if (clienteOpt.isPresent()) {
            Cliente clienteUpdated = clienteOpt.get();
            clienteUpdated.setDni(cliente.getDni());
            clienteUpdated.setNombre(cliente.getNombre());
            clienteUpdated.setDireccion(cliente.getDireccion());
            clienteUpdated.setTelefono(cliente.getTelefono());
            if (cliente.getAval() != null && cliente.getAval().getIdCliente() != null) {
                Optional<Cliente> avalOpt = clienteService.getClienteById(cliente.getAval().getIdCliente());
                if (avalOpt.isEmpty()) {
                    return new ResponseEntity<>("Aval no encontrado", HttpStatus.NOT_FOUND);
                }
                clienteUpdated.setAval(avalOpt.get());
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(clienteService.saveCliente(clienteUpdated));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Cliente> clienteOpt = clienteService.getClienteById(id);
        try {
            if (clienteOpt.isPresent()) {
                clienteService.deleteCliente(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar el cliente porque es aval de otro cliente.");
        }
    }
}
