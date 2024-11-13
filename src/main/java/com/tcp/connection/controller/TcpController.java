package com.tcp.connection.controller;


import com.tcp.connection.service.client.ReactiveTcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tcp")
public class TcpController {
    @Autowired
    private ReactiveTcpClient tcpClient;

    @GetMapping("/sendToServer")
    public Mono<String> sendMessageToServer(@RequestParam String message) {
        return tcpClient.sendMessage(message)
                .map(response -> "Server response: " + response);
    }
}
