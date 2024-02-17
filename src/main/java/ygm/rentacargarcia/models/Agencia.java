package ygm.rentacargarcia.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Agencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgencia;

    @Column(unique = true)
    private String nombre;

    private String direccion;

    private String telefono;

    // Relación con Reserva
    @OneToMany(mappedBy = "agencia")
    @JsonManagedReference
    private List<Reserva> reservas = new ArrayList<>();

}
