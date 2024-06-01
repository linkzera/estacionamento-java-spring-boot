package br.com.douglasvinicius.estacionamento.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.douglasvinicius.estacionamento.models.Establishment;
import br.com.douglasvinicius.estacionamento.models.DTO.UpdateEstablishmentDTO;
import br.com.douglasvinicius.estacionamento.repository.EstablishmentRepository;

@Service
public class EstablishmentService {
  private EstablishmentRepository establishmentRepository;

  public EstablishmentService(EstablishmentRepository establishmentRepository) {
    this.establishmentRepository = establishmentRepository;
  }

  public List<Establishment> create(Establishment establishment) {
    establishmentRepository.save(establishment);
    return findAll();
  }

  public Establishment findById(Long id) {
    return establishmentRepository.findById(id).orElse(null);
  }

  public List<Establishment> findAll() {
    var sort = Sort.by(Sort.Direction.ASC, "name");
    return establishmentRepository.findAll(sort);
  }

  public List<Establishment> update(Long id, UpdateEstablishmentDTO establishment) {
    var establishmentToUpdate = establishmentRepository.findById(id).orElse(null);

    if (establishmentToUpdate == null) {
      return null;
    }

    BeanUtils.copyProperties(establishment, establishmentToUpdate);
    establishmentRepository.save(establishmentToUpdate);

    return findAll();
  }

  public List<Establishment> delete(Long id) {
    var establishment = establishmentRepository.findById(id).orElse(null);
    if (establishment == null) {
      return null;
    }

    establishmentRepository.deleteById(id);
    return findAll();
  }

}
