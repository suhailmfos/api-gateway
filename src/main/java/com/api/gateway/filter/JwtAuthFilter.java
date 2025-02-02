package com.api.gateway.filter;

import com.api.gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class JwtAuthFilter  implements GlobalFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String token = extractToken(exchange.getRequest());

        if(token == null || !jwtUtil.validateToken(token)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    private String extractToken(ServerHttpRequest request){
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return (authHeader != null && authHeader.startsWith("BEARER ")) ? authHeader.substring(7) : null;
    }
}
