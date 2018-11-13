package br.eti.esabreu.oauth2.authorizationserver.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.eti.esabreu.oauth2.authorizationserver.domain.User;
import br.eti.esabreu.oauth2.authorizationserver.dto.UserDto;
import br.eti.esabreu.oauth2.authorizationserver.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user) {
		user = userService.create(user);
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequestUri()
					.path("/{uuid}")
					.buildAndExpand(user.getUuid())
					.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{uuid}")
	public ResponseEntity<UserDto> findOne(@PathVariable("uuid") UUID uuid) {
		UserDto user = userService.findOne(uuid);
		return ResponseEntity.ok().body(user);
	}
}