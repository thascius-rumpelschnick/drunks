package org.kappa.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  @GetMapping("/login")
  public String loginGet() {
    return "";
  }

  @PostMapping("/login")
  public String loginPost() {
    return "";
  }

}
