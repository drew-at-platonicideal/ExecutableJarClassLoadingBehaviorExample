package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class App {
    
    public static void main(String a[]) throws ClassNotFoundException, IOException {
        URL externalResource = new File("./src/main/resources/external_resource-0.0.1-SNAPSHOT.jar").toURI().toURL();
        
        try(URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {externalResource})) {
            Class<?> klazz = urlClassLoader.loadClass("com.example.external_resource.FooController");
            System.out.println(klazz);
        }
        
    }

}
