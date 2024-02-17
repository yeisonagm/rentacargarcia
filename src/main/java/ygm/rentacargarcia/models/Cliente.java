package ygm.rentacargarcia.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(unique = true)
    private String dni;

    private String nombre;

    private String direccion;

    private String telefono;

    @OneToOne
    private Cliente aval;

    // Relación con Reserva
    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference
    private List<Reserva> reservas = new ArrayList<>();
}
