package com.bizislife.keycloak.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error {
    private Long errorId; // timestamp
    private String message;
}
