package org.kappa.server.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;


@Document
public class User implements UserDetails {
  @MongoId
  private final ObjectId id;
  private final String username;
  private final String password;
  private final Set<GrantedAuthority> authorities;


  public User(final ObjectId id, final String username, final String password, final Set<GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }


  public ObjectId getId() {
    return this.id;
  }


  @Override
  public String getUsername() {
    return this.username;
  }


  @Override
  public boolean isAccountNonExpired() {
    return false;
  }


  @Override
  public boolean isAccountNonLocked() {
    return false;
  }


  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }


  @Override
  public boolean isEnabled() {
    return true;
  }


  @Override
  public String getPassword() {
    return this.password;
  }


  @Override
  public Set<GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

}

