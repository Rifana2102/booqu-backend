package com.booqu.booqu_backend.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@RestController
public class EndpointController {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public EndpointController(
        @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping requestMappingHandlerMapping
    ) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @GetMapping("/all-endpoints")
    public List<String> getAllEndpoints() {
        return requestMappingHandlerMapping.getHandlerMethods().keySet().stream()
                .flatMap(info -> info.getPatternValues().stream())
                .distinct()
                .sorted()
                .toList();
    }
}