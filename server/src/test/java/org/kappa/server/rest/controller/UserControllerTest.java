package org.kappa.server.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kappa.server.domain.User;
import org.kappa.server.service.UserService;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
@WithMockUser(username = "foo", password = "foobar", roles = "USER")
class UserControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  private final ObjectMapper mapper = new ObjectMapper();


  @Test
  void testRegister_GivenRequestBodyWithUser_ShouldReturnStatusOk() throws Exception {
    // Given
    final var user = new User("foo", "foobar");
    final var payload = this.mapper.writeValueAsString(user);

    when(this.userService.findUserByUserName(anyString())).thenReturn(Optional.empty());

    // When
    final var response = this.mvc.perform(
            post("/api/v1/user/register", user)
                .with(csrf())
                .content(payload).header("Content-Type", "application/json")
                .accept("application/json")
        )
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();

    // Then
    verify(this.userService, times(1)).findUserByUserName(anyString());
    verify(this.userService, times(1)).saveUser(user);

    assertEquals("User created.", response.getContentAsString());
  }

  @Test
  void testRegister_GivenRequestBodyWithExistingUser_ShouldReturnStatusBadRequest() throws Exception {
    // Given
    final var user = new User("foo", "foobar");
    final var payload = this.mapper.writeValueAsString(user);

    when(this.userService.findUserByUserName(anyString())).thenReturn(Optional.of(user));

    // When
    final var response = this.mvc.perform(
            post("/api/v1/user/register", user)
                .with(csrf())
                .content(payload).header("Content-Type", "application/json")
                .accept("application/json")
        )
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse();

    // Then
    verify(this.userService, times(1)).findUserByUserName(user.username());
    verify(this.userService, times(0)).saveUser(any());

    assertEquals("User with username: foo is already registered.", response.getContentAsString());
  }


  @Test
  void testRegister_GivenNonValidRequestBodyWithUser_ShouldReturnStatusInternalServerError() throws Exception {
    // Given
    final var user1 = new User(null, null);
    final var user2 = new User("foo", null);
    var payload = this.mapper.writeValueAsString(user1);

    when(this.userService.findUserByUserName(any())).thenReturn(Optional.empty());

    // When
    var response = this.mvc.perform(
            post("/api/v1/user/register", user1)
                .with(csrf())
                .content(payload).header("Content-Type", "application/json")
                .accept("application/json")
        )
        .andExpect(status().isInternalServerError())
        .andReturn()
        .getResponse();

    // Then
    verify(this.userService, times(0)).findUserByUserName(any());
    verify(this.userService, times(0)).saveUser(any());

    assertTrue(response.getContentAsString().contains("Field error in object 'user' on field 'username'"));

    // When
    payload = this.mapper.writeValueAsString(user2);

    response = this.mvc.perform(
            post("/api/v1/user/register", user1)
                .with(csrf())
                .content(payload).header("Content-Type", "application/json")
                .accept("application/json")
        )
        .andExpect(status().isInternalServerError())
        .andReturn()
        .getResponse();

    // Then
    verify(this.userService, times(0)).findUserByUserName(any());
    verify(this.userService, times(0)).saveUser(any());

    assertTrue(response.getContentAsString().contains("Field error in object 'user' on field 'password'"));
  }


  @Test
  void testDelete_GivenAuthorizedRequestWithUser_ShouldReturnStatusOk() throws Exception {
    // Given
    final var user = new User("foo", "foobar");

    when(this.userService.deleteUser(anyString())).thenReturn(Optional.of(user));

    // When
    final var response = this.mvc.perform(
            delete("/api/v1/user/delete")
                .with(httpBasic(user.username(), user.password()))
                .with(csrf())
                .accept("application/json")
        )
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();

    // Then
    verify(this.userService, times(1)).deleteUser(user.username());

    assertEquals("User deleted.", response.getContentAsString());
  }

  @Test
  void testDelete_GivenAuthorizedRequestWithNonExistingUser_ShouldReturnStatusBadRequest() throws Exception {
    // Given
    final var user = new User("foo", "foobar");
    when(this.userService.deleteUser(anyString())).thenReturn(Optional.empty());

    // When
    final var response = this.mvc.perform(
            delete("/api/v1/user/delete")
                .with(httpBasic(user.username(), user.password()))
                .with(csrf())
                .accept("application/json")
        )
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse();

    // Then
    verify(this.userService, times(1)).deleteUser(user.username());

    assertEquals("User with username: foo could not be deleted.", response.getContentAsString());
  }


  @Test
  void testDelete_GivenNonAuthorizedRequestWithUser_ShouldReturnStatusUnauthorized() throws Exception {
    // Given
    final var user = new User("bar", "foo");
    when(this.userService.deleteUser(anyString())).thenReturn(Optional.empty());

    // When
    final var response = this.mvc.perform(
            delete("/api/v1/user/delete")
                .with(httpBasic(user.username(), user.password()))
                .with(csrf())
                .accept("application/json")
        )
        .andExpect(status().is4xxClientError())
        .andReturn()
        .getResponse();

    // Then
    verify(this.userService, times(0)).deleteUser(anyString());

    assertEquals("Unauthorized", response.getErrorMessage());
  }

}
