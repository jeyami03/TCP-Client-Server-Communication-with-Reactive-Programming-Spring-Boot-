//package com.tcpclientserverconnection.tcp.client;
//
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//import reactor.netty.tcp.TcpClient;
//
//@Service
//public class ReactiveTcpClient {
//
//    private final String serverAddress = "localhost";
//    private final int serverPort = 5000;
//
//    public Mono<String> sendMessage(String message) {
//        return TcpClient.create()
//                .host(serverAddress)
//                .port(serverPort)
//                .handle((inbound, outbound) ->
//                        outbound.sendString(Mono.just(message))
//                                .then(inbound.receive().asString().then()))
//                .connect()
//                .flatMap(connection -> {
//                    return connection.isDisposed() ? Mono.error(new RuntimeException("Connection failed"))
//                            : connection.inbound().receive().asString().next();
//                })
//                .doOnTerminate(() -> System.out.println("Message sent: " + message))
//                .doOnError(error -> System.out.println("Connection failed: " + error.getMessage()));
//    }
//}
