package org.kappa.server.web.controller;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kappa.server.DrunksServerApplication;
import org.kappa.server.persistence.entity.User;
import org.kappa.server.persistence.repository.UserRepository;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {DrunksServerApplication.class, UserRepository.class})
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser(username = "user1", password = "pwd", roles = "USER")
class UserControllerTest {

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private MockMvc mvc;

  @Mock
  private UserRepository userRepository;


  @BeforeEach
  void beforeEach() {
    final var user = new User(new ObjectId(), "foo", "bar", Set.of(new SimpleGrantedAuthority("USER")));
    when(this.userRepository.findUserByUserName(anyString())).thenReturn(Optional.of(user));
  }


  @Test
  void loginPost() throws Exception {
    this.mvc.perform(post("/api/v1/user/login")).andExpect(status().isOk());
  }


  @Test
  void test1() throws Exception {
    this.mvc.perform(get("/api/v1/user/test").with(httpBasic("foo", "bar")))
        .andExpect(status().isOk());
  }
}

// ToDo: Adjust tests!