package com.daviddev.CRUDChallenge.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daviddev.CRUDChallenge.dto.ClientDTO;
import com.daviddev.CRUDChallenge.entities.Client;
import com.daviddev.CRUDChallenge.repositories.ClientRepository;
import com.daviddev.CRUDChallenge.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		
		Page<Client> pageClient = repository.findAll(pageRequest);
		Page<ClientDTO> pageClientDTO = pageClient.map(x -> new ClientDTO(x));
		
		return pageClientDTO;
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> aux = repository.findById(id);
		
		Client entity = aux.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO clientDTO) {
		
		Client entity = new Client();
		copyDtoToEntity(clientDTO, entity);
		
		entity = repository.save(entity);
		
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDTO) {
		
		try {
			Client entity = repository.getReferenceById(id);
			copyDtoToEntity(clientDTO, entity);
			
			entity = repository.save(entity);
			
			return new ClientDTO(entity);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id: " + id + " - inexistente");
		}
	}
	
	@Transactional
	public void delete(Long id) {
		
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id: " + id + " - inexistente");
		}
	}
	
	private void copyDtoToEntity(ClientDTO clientDTO, Client entity) {
		
		entity.setName(clientDTO.getName());
		entity.setCpf(clientDTO.getCpf());
		entity.setIncome(clientDTO.getIncome());
		entity.setBirthDate(clientDTO.getBirthDate());
		entity.setChildren(clientDTO.getChildren());
	}
}
