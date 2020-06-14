package com.yeefom.basic_app.clients;

import java.util.concurrent.CompletableFuture;

public interface Client {

  CompletableFuture<String> getProfileImage(String username);

}
