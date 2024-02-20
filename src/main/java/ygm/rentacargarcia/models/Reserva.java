package ygm.rentacargarcia.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idReserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFinal;

    private Boolean autosEntregados;

    private Double precioTotal;

    // Relación con Agencia
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Agencia agencia;

    // Relación con Cliente
    @ManyToOne
    @JsonBackReference
    private Cliente cliente;

    // Relación con Auto
    @ManyToMany
    @JoinTable(name = "reserva_auto",
            joinColumns = @JoinColumn(name = "idReserva"),
            inverseJoinColumns = @JoinColumn(name = "idAuto"))
    private Set<Auto> autos = new HashSet<>();
}
