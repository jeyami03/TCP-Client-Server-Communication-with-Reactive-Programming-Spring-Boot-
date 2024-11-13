package com.tcp.connection.config;

import com.tcp.connection.service.server.ReactiveTcpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ServerRunner implements CommandLineRunner {

    @Autowired
    private ReactiveTcpServer reactiveTcpServer;

    @Override
    public void run(String... args) {
        reactiveTcpServer.startServer();
    }
}
