package org.kappa.server.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kappa.server.domain.Level;
import org.kappa.server.domain.UserData;
import org.kappa.server.service.UserDataService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserDataController.class)
@ExtendWith(MockitoExtension.class)
@WithMockUser(username = "foo", password = "foobar", roles = "USER")
class UserDataControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserDataService userDataService;

  private final ObjectMapper mapper = new ObjectMapper();


  @Test
  void testGetUserData_GivenUsername_WhenUserDataExists_ShouldReturnStatusOkAndDomainUserData() throws Exception {
    // Given
    final var username = "foo";
    final var password = "foobar";
    final var userData = new UserData(username, 100, Level.ONE);

    when(this.userDataService.findUserDataByUserName(anyString())).thenReturn(Optional.of(userData));

    // When
    final var response = this.mvc.perform(
            get("/api/v1/user-data")
                .with(csrf())
                .with(httpBasic(username, password))
                .param("username", username)
                .accept("application/json")
        )
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();

    final var responseBody = this.mapper.readValue(response.getContentAsString(), UserData.class);

    // Then
    verify(this.userDataService, times(1)).findUserDataByUserName(username);

    assertEquals(userData, responseBody);
  }


  @Test
  void testGetUserData_GivenUsername_WhenUserDataDoesNotExist_ShouldReturnStatusBadRequest() throws Exception {
    // Given
    final var username = "foo";
    final var password = "foobar";

    when(this.userDataService.findUserDataByUserName(anyString())).thenReturn(Optional.empty());

    // When
    final var response = this.mvc.perform(
            get("/api/v1/user-data")
                .with(csrf())
                .with(httpBasic(username, password))
                .param("username", username)
                .accept("application/json")
        )
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse();

    // Then
    verify(this.userDataService, times(1)).findUserDataByUserName(username);

    assertTrue(response.getContentAsString().isEmpty());
  }


  @Test
  void saveUserData() {
  }


  @Test
  void handleException() {
  }
}