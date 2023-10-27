package com.api.rest.trabajadorsede.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.trabajadorsede.entidades.Trabajador;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {

}
