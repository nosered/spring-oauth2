package br.eti.esabreu.oauth2.resourceserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.esabreu.oauth2.resourceserver.domain.Foo;

public interface FooRepository extends JpaRepository<Foo, Long> { }
