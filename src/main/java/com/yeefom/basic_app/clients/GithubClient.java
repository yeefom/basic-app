package com.yeefom.basic_app.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GithubClient implements Client{
  private final HttpClient client;
  private static final String PROFILE_IMAGE_KEY = "avatar_url";

  public GithubClient() {
    client = HttpClient.newBuilder()
        .build();
  }

  private CompletableFuture<Map<String, String>> getUser(String username) {
    var mapper = new ObjectMapper();

    var request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.github.com/users/" + username))
        .header("Accept", "application/vnd.github.v3+json")
        .GET()
        .build();

    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(res -> {
          if (res.statusCode() == 200) {
            return res;
          }

          if (res.statusCode() == 404) {
            throw new CompletionException(new RuntimeException("User not found"));
          }

          throw new CompletionException(
              new RuntimeException("Received error from GitHub: " + res.statusCode() + " " + res.body()));
        })
        .thenApply(res -> {
          try {
            return mapper.readValue(res.body(), new TypeReference<>(){});
          } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CompletionException(e);
          }
        });
  }

  @Override
  public CompletableFuture<String> getProfileImage(String username) {
    return getUser(username)
        .thenApply(user -> user.get(PROFILE_IMAGE_KEY));
  }
}
