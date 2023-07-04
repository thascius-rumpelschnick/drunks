package org.kappa.client.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import static org.kappa.client.http.Status.*;


public class DrunksClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(DrunksClient.class);
  private static final String GET_USER_URI = "http://localhost:8080/api/v1/user";
  private static final String REGISTER_USER_URI = "http://localhost:8080/api/v1/user/register";
  private static final String DELETE_USER_URI = "http://localhost:8080/api/v1/user/delete";
  private static final String USER_DATA_URI = "http://localhost:8080/api/v1/user-data";
  private static final String AUTHORIZATION = "Authorization";
  private static final String PATCH = "PATCH";
  public static final String APPLICATION_JSON = "application/json";
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String ACCEPT = "Accept";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;


  private DrunksClient(final HttpClient httpClient, final ObjectMapper mapper) {
    this.httpClient = httpClient;
    this.mapper = mapper;
  }

  public Status getUser(final String username, final String password) {
    final var request = HttpRequest.newBuilder()
        .uri(URI.create(GET_USER_URI))
        .GET()
        .header(AUTHORIZATION, basicHttpAsBase64DecodedString(username, password))
        .header(ACCEPT, APPLICATION_JSON)
        .build();

    return this.requestUser(request);
  }

  public Status registerUser(final String username, final String password) throws JsonProcessingException {
    final var payload = this.mapper.writeValueAsString(new User(username, password));

    final var request = HttpRequest.newBuilder()
        .uri(URI.create(REGISTER_USER_URI))
        .POST(HttpRequest.BodyPublishers.ofString(payload))
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .header(ACCEPT, APPLICATION_JSON)
        .build();

    return this.requestUser(request);
  }


  public Status deleteUser(final String username, final String password) {
    final var request = HttpRequest.newBuilder()
        .uri(URI.create(DELETE_USER_URI))
        .GET()
        .header(AUTHORIZATION, basicHttpAsBase64DecodedString(username, password))
        .header(ACCEPT, APPLICATION_JSON)
        .build();

    return this.requestUser(request);
  }


  private Status requestUser(final HttpRequest request) {
    try {
      final HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      final var statusCode = response.statusCode();
      final var responseBody = response.body();

      if (statusCode == 200) {
        LOGGER.info(responseBody);
        return ACCEPTED;
      }

      if (statusCode == 401) {
        LOGGER.info(responseBody);
        return DENIED;
      }

      if (statusCode == 500) {
        LOGGER.info(responseBody);
        return INTERNAL_SERVER_ERROR;
      }

    } catch (final Exception exception) {
      LOGGER.error(exception.getMessage());
      Thread.currentThread().interrupt();

      return INTERNAL_SERVER_ERROR;
    }

    return BAD_REQUEST;
  }


  public Optional<UserData> saveUserData(final String username, final String password, final UserData userData) throws JsonProcessingException {
    final var payload = this.mapper.writeValueAsString(userData);

    final var request = HttpRequest.newBuilder()
        .uri(URI.create(USER_DATA_URI))
        .method(PATCH, HttpRequest.BodyPublishers.ofString(payload))
        .header(AUTHORIZATION, basicHttpAsBase64DecodedString(username, password))
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .header(ACCEPT, APPLICATION_JSON)
        .build();

    return this.requestUserData(request);
  }


  public Optional<UserData> getUserData(final String username, final String password) {
    final var request = HttpRequest.newBuilder()
        .uri(URI.create(USER_DATA_URI + "?username=" + username))
        .GET()
        .header(AUTHORIZATION, basicHttpAsBase64DecodedString(username, password))
        .header(ACCEPT, APPLICATION_JSON)
        .build();

    return this.requestUserData(request);
  }


  private Optional<UserData> requestUserData(final HttpRequest request) {
    try {
      final HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      final var statusCode = response.statusCode();
      final var responseBody = response.body();

      if (statusCode == 200) {
        LOGGER.info(responseBody);

        return Optional.of(this.mapper.readValue(responseBody, UserData.class));
      }


    } catch (final Exception exception) {
      LOGGER.error(exception.getMessage());
      Thread.currentThread().interrupt();

      return Optional.empty();
    }

    return Optional.empty();
  }


  private static String basicHttpAsBase64DecodedString(final String username, final String password) {
    final byte[] toEncode = (username + ":" + password).getBytes(StandardCharsets.UTF_8);

    return "Basic " + new String(Base64.getEncoder().encode(toEncode), StandardCharsets.UTF_8);
  }


  public static DrunksClient create() {
    return new DrunksClient(HttpClient.newHttpClient(), new ObjectMapper());
  }


  public static DrunksClient create(final HttpClient httpClient, final ObjectMapper objectMapper) {
    return new DrunksClient(httpClient, objectMapper);
  }

}

