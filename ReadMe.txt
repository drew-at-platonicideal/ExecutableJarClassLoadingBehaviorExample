This project demonstrates a behavioral defect in using dynamically loading classes which extend Spring resources
within a Spring Boot executable jar.

When executed as a simple java class, this code executes as expected, loading the classes "FooService" and "FooController" 
from the external resource "external_resource-0.0.1-SNAPSHOT.jar"

When executed by building as a Spring Boot executable jar, using

> mvn clean package
> java -jar /target/demo-0.0.1-SNAPSHOT.jar

It instead produces the following:
class com.example.external_resource.FooService
Exception in thread "main" java.lang.reflect.InvocationTargetException
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
        at java.lang.reflect.Method.invoke(Unknown Source)
        at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:48)
        at org.springframework.boot.loader.Launcher.launch(Launcher.java:87)
        at org.springframework.boot.loader.Launcher.launch(Launcher.java:51)
        at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:52)
Caused by: java.lang.NoClassDefFoundError: org/springframework/boot/web/servlet/error/ErrorController
        at java.lang.ClassLoader.defineClass1(Native Method)
        at java.lang.ClassLoader.defineClass(Unknown Source)
        at java.security.SecureClassLoader.defineClass(Unknown Source)
        at java.net.URLClassLoader.defineClass(Unknown Source)
        at java.net.URLClassLoader.access$100(Unknown Source)
        at java.net.URLClassLoader$1.run(Unknown Source)
        at java.net.URLClassLoader$1.run(Unknown Source)
        at java.security.AccessController.doPrivileged(Native Method)
        at java.net.URLClassLoader.findClass(Unknown Source)
        at java.lang.ClassLoader.loadClass(Unknown Source)
        at java.lang.ClassLoader.loadClass(Unknown Source)
        at com.example.demo.App.main(App.java:14)
        ... 8 more
Caused by: java.lang.ClassNotFoundException: org.springframework.boot.web.servlet.error.ErrorController
        at java.net.URLClassLoader.findClass(Unknown Source)
        at java.lang.ClassLoader.loadClass(Unknown Source)
        at java.lang.ClassLoader.loadClass(Unknown Source)
        ... 20 more
        
What appears to be happening is that the FooService, having no dependencies on anything outside of 
"external_resource-0.0.1-SNAPSHOT.jar", is successfully loaded. 

FooController, which extends org.springframework.boot.web.servlet.error.ErrorController,
is unable to be loaded.
       
This behavior is **not** observed if the URLClassLoader's parent is set to the thread's current context classloader.
 