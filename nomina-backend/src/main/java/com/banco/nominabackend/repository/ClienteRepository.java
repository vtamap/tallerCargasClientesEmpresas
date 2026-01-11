package com.banco.nominabackend.repository;

import com.banco.nominabackend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Esto nos servirá para validar que no existan duplicados por número de cuenta
    Optional<Cliente> findByNumeroCuenta(String numeroCuenta);

    // También para validar si la identificación ya existe
    Optional<Cliente> findByNumeroIdentificacion(String numeroIdentificacion);
}