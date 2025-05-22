package com.kolosov;

import com.kolosov.config.ApplicationConfig;
import com.kolosov.config.DatabaseConfig;
import com.kolosov.config.WebAppInitializer;
import com.kolosov.config.WebConfig;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class App {
    public static void main(String[] args) throws LifecycleException, IOException {
        int port = 8181;
        String appBase = ".";
        File baseDir = Files.createTempDirectory("embedded-tomcat").toFile();

        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(WebAppInitializer.class, ApplicationConfig.class, WebConfig.class, DatabaseConfig.class);

        final Connector connector = new Connector();
        connector.setPort(port);
        connector.setScheme("http");
        connector.setSecure(false);

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setSilent(false);
        tomcat.getHost().setAppBase(appBase);
        tomcat.setConnector(connector);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);

        Context context = tomcat.addWebapp("", baseDir.getAbsolutePath());
        Tomcat.addServlet(context, "dispatcherServlet", dispatcherServlet);
        context.addServletMappingDecoded("/*", "dispatcherServlet");

        tomcat.start();
        tomcat.getServer().await();
    }
}
