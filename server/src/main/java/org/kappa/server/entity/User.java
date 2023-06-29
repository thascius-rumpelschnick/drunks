package org.kappa.server.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;


@Document
public class User implements UserDetails {
  private final @MongoId ObjectId id;
  private final String username;
  private final String password;
  private final Set<GrantedAuthority> authorities;


  public User(ObjectId id, String username, String password, Set<GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }


  public ObjectId getId() {
    return id;
  }


  @Override
  public String getUsername() {
    return username;
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
    return password;
  }


  @Override
  public Set<GrantedAuthority> getAuthorities() {
    return authorities;
  }
}

