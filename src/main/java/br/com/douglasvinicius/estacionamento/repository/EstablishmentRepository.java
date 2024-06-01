package br.com.douglasvinicius.estacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.douglasvinicius.estacionamento.models.Establishment;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long>{
  
}
