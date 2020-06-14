package com.yeefom.basic_app.app;

import com.google.inject.AbstractModule;
import com.yeefom.basic_app.clients.Client;
import com.yeefom.basic_app.clients.GithubClient;

public class AppModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(Client.class).to(GithubClient.class);
  }
}
