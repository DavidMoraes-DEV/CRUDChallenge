package com.daviddev.CRUDChallenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daviddev.CRUDChallenge.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {}