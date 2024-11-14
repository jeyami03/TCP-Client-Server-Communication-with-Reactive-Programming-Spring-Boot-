package com.tcp.connection.service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.tcp.connection.util.TcpClientUtil;

@Service
public class ReactiveTcpClient {
    private static final Logger logger = LoggerFactory.getLogger(ReactiveTcpClient.class);

    private final TcpClientUtil tcpClientUtil;

    @Autowired
    public ReactiveTcpClient(TcpClientUtil tcpClientUtil) {
        this.tcpClientUtil = tcpClientUtil;
    }

    public Mono<String> sendMessage(String message) {
        return tcpClientUtil
                .connect()
                .flatMap(connection -> {
                    logger.info("Message sent: {}", message);
                    return this.tcpClientUtil.sendAndReceiveMessage(connection, message);
                }).doOnError(error -> {
                    logger.error("Connection failed: {}", error.getMessage());
                });
    }
}
