package ru.hisoakende.mycloud.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class URIBuilder<IdType> {
    private final IdType id;

    public URIBuilder(IdType id) {
        this.id = id;
    }

    public URI build() {
        return ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
