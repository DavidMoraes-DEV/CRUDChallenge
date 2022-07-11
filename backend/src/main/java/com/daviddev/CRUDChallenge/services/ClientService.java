package com.daviddev.CRUDChallenge.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		Client entity = aux.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ClientDTO(entity);
	}
	
}
