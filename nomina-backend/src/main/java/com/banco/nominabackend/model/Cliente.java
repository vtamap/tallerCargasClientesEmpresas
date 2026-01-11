package com.banco.nominabackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos que deben venir del archivo TXT
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String fechaIngreso;
    private Double valorNomina;
    private String correoElectronico;
    private String numeroCelular;

    // Datos que pide que se consulte al servicio externo (Databook)
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;

    // Campo unicod
    @Column(unique = true)
    private String numeroCuenta;
}