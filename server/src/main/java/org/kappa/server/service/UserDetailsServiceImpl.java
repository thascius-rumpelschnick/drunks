package org.kappa.server.service;

import org.kappa.server.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;


  public UserDetailsServiceImpl(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final var user = this.userRepository.findUserByUserName(username);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found: " + username);
    }

    return new org.springframework.security.core.userdetails.User(
        user.get().getUsername(),
        user.get().getPassword(),
        user.get().getAuthorities()
    );
  }

}

