package org.kappa.server.service;

import org.kappa.server.entity.User;
import org.kappa.server.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;


  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final User user = this.userRepository.findUserByUserName(username);

    if (user == null) {
      throw new UsernameNotFoundException("User not found: " + username);
    }

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.getAuthorities()
    );
  }

}

