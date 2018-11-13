package br.eti.esabreu.oauth2.authorizationserver.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.eti.esabreu.oauth2.authorizationserver.domain.Authority;
import br.eti.esabreu.oauth2.authorizationserver.domain.User;
import br.eti.esabreu.oauth2.authorizationserver.dto.UserDto;
import br.eti.esabreu.oauth2.authorizationserver.enums.AuthorityEnum;
import br.eti.esabreu.oauth2.authorizationserver.repository.AuthorityRepository;
import br.eti.esabreu.oauth2.authorizationserver.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public User create(User user) {
		user.setEnabled(true);
		user.setUuid(UUID.randomUUID());
		user.setPassword(this.hashRawPassword(user.getPassword()));
		Authority defaultAuthority = authorityRepository.findOneByAuthority(AuthorityEnum.FOO_READ);
		user.addAuthority(defaultAuthority);
		return userRepository.save(user);
	}
	
	public UserDto findOne(UUID uuid) {
		return userRepository.findOneByUuid(uuid);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findOneByUsername(username);
	}
	
	private String hashRawPassword(String rawPassword) {
		String hashPassword = "{bcrypt}";
		hashPassword += new BCryptPasswordEncoder().encode(rawPassword);
		return hashPassword;
	}
}