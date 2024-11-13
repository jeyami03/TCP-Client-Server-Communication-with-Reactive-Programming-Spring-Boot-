//package com.tcpclientserverconnection.tcp.server;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//import reactor.netty.DisposableServer;
//import reactor.netty.tcp.TcpServer;
//
//@Component
//public class ReactiveTcpServer implements CommandLineRunner {
//
//    @Override
//    public void run(String... args) {
//        DisposableServer server = TcpServer.create()
//                .host("localhost")
//                .port(5000)
//                .handle((inbound, outbound) -> inbound
//                        .receive()
//                        .asString()
//                        .doOnNext(message -> {
//                            System.out.println("Server received: " + message);
//                        })
//                        .flatMap(message -> outbound.sendString(Mono.just("Echo: " + message)).then())
//                )
//                .bindNow();
//
//        System.out.println("TCP Server started on port 8080");
//        server.onDispose().block(); // Keeps server running
//    }
//}
