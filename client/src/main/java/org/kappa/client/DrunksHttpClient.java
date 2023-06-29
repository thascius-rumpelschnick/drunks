package org.kappa.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DrunksHttpClient {

    public static void main(String[] args) {

        // Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create a GET request to a specified URL
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.example.com/data"))
                .GET()
                .build();

        // Send the request and retrieve the response
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response status code
            int statusCode = response.statusCode();
            System.out.println("Response Status Code: " + statusCode);

            // Print the response body
            String responseBody = response.body();
            System.out.println("Response Body: " + responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

