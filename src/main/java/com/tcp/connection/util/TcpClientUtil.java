package com.tcp.connection.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

@Service
public class TcpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(TcpClientUtil.class);

    private final TcpClient tcpClient;

    @Autowired
    public TcpClientUtil(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public Mono<? extends Connection> connect() {
        return tcpClient
                .connect()
                .doOnSuccess(connection ->
                        logger.info("Connected to the server successfully."))
                .doOnError(error ->
                        logger.error("Failed to connect to the server: {}", error.getMessage()));
    }

    public Mono<String> sendAndReceiveMessage(Connection connection, String message) {
        return connection
                .outbound()
                .sendString(Mono.just(message))
                .then()
                .then(connection.inbound().receive().asString().next())
                .doOnNext(ack -> {
                    logger.info("Client received acknowledgment: {}", ack);
                })
                .doOnError(error -> logger.error("Error while receiving acknowledgment: {}", error.getMessage()))
                .doFinally(signalType -> {
                    connection.dispose();
                });
    }
}
