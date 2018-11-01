package br.eti.esabreu.oauth2.resourceserver.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.eti.esabreu.oauth2.resourceserver.domain.Foo;
import br.eti.esabreu.oauth2.resourceserver.service.FooService;

import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.HAS_SCOPE_SESSION_AND_AUTHORITY_FOO_READ;
import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.HAS_SCOPE_SIGN_AND_AUTHORITY_FOO_WRITE;

@Controller
@RequestMapping("/foos")
public class FooController {

	@Autowired
	private FooService fooService;
	
	@GetMapping
	@PreAuthorize(HAS_SCOPE_SESSION_AND_AUTHORITY_FOO_READ)
	public ResponseEntity<List<Foo>> findAll() {
		List<Foo> foos = fooService.findAll();
		return ResponseEntity.ok().body(foos);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize(HAS_SCOPE_SESSION_AND_AUTHORITY_FOO_READ)
	public ResponseEntity<Optional<Foo>> findOne(@PathVariable("uuid") Long id) {
		Optional<Foo> responseBodyFoo = fooService.findOne(id);
		return ResponseEntity.ok().body(responseBodyFoo);
	}
	
	@PostMapping
	@PreAuthorize(HAS_SCOPE_SIGN_AND_AUTHORITY_FOO_WRITE)
	public ResponseEntity<Foo> create(@RequestBody Foo foo) {
		foo = fooService.create(foo);
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequestUri()
					.path("/{id}")
					.buildAndExpand(foo.getId())
					.toUri();
		return ResponseEntity.created(uri).body(foo);
	}
}