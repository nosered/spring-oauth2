package br.eti.esabreu.oauth2.resourceserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.eti.esabreu.oauth2.resourceserver.domain.Foo;
import br.eti.esabreu.oauth2.resourceserver.repository.FooRepository;

@Service
public class FooService {

	@Autowired
	private FooRepository fooRepository;
	
	public List<Foo> findAll() {
		return fooRepository.findAll();
	}
	
	public Optional<Foo> findOne(Long id) {
		return fooRepository.findById(id);
	}
	
	public Foo create(Foo foo) {
		return fooRepository.save(foo);
	}
}