package ygm.rentacargarcia.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idAuto")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAuto;

    @Column(unique = true)
    private String matricula;

    private String marca;

    private String modelo;

    private String color;

    private double precioReserva;

    private double gasolina;

    // Relación con Garaje
    @ManyToOne
    @JsonBackReference
    private Garaje garaje;

    // Relación con Reserva
    @ManyToMany(mappedBy = "autos")
    private Set<Reserva> reservas = new HashSet<>();
}
