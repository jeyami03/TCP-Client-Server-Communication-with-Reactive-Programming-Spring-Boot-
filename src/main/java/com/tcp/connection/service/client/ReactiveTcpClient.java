package com.tcp.connection.service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;
import reactor.netty.Connection;

@Service
public class ReactiveTcpClient {
    private static final Logger logger = LoggerFactory.getLogger(ReactiveTcpClient.class);

    private final TcpClient tcpClient;

    @Autowired
    public ReactiveTcpClient(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public Mono<String> sendMessage(String message) {
        return tcpClient
                .connect()
                .flatMap(connection -> sendAndReceiveMessage(connection, message))
                .doOnTerminate(() -> {
                    logger.info("Message sent: {}", message);
                })
                .doOnError(error -> {
                    logger.error("Connection failed: {}", error.getMessage());
                });
    }

    private Mono<String> sendAndReceiveMessage(Connection connection, String message) {
        return connection.outbound()
                .sendString(Mono.just(message))
                .then()
                .then(connection.inbound().receive().asString().next())
                .doOnNext(ack -> {
                    logger.info("Client received acknowledgment: {}", ack);
                })
                .doFinally(signalType -> connection.dispose());
    }
}
