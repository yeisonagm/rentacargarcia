package ygm.rentacargarcia.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idCliente")
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
    @JoinColumn(name = "id_aval")
    @JsonIdentityReference(alwaysAsId = true)
    private Cliente aval;

    // Relación con Reserva
    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference
    private List<Reserva> reservas = new ArrayList<>();
}
