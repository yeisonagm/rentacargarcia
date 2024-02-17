package ygm.rentacargarcia.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Garaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGaraje;

    private String nombre;

    private String direccion;

    // Relación con Auto
    @OneToMany(mappedBy = "garaje")
    @JsonManagedReference
    private List<Auto> autos = new ArrayList<>();
}
