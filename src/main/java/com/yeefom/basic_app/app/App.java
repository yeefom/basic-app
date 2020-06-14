package com.yeefom.basic_app.app;

import com.yeefom.basic_app.clients.Client;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public class App {
  private final Client client;

  @Inject
  public App(Client client) {
    this.client = client;
  }

  public CompletableFuture<String> getProfileImage(String username) {
    return client.getProfileImage(username);
  }
}
