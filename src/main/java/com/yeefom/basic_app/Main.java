package com.yeefom.basic_app;


import com.google.inject.Guice;
import com.yeefom.basic_app.app.App;
import com.yeefom.basic_app.app.AppModule;

public class Main {
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Provide a username to look up profile image");
    } else {
      var injector = Guice.createInjector(new AppModule());
      var app = injector.getInstance(App.class);

      var image = app.getProfileImage(args[0])
          .exceptionally(Throwable::getMessage)
          .join();

      System.out.println(image);
    }
  }
}
