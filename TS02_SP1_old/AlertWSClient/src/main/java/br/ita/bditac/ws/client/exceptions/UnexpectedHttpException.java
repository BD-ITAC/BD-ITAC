package br.ita.bditac.ws.client.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;

public class UnexpectedHttpException extends IOException {

    private static final long serialVersionUID = 1L;
    
    private HttpStatus status;
    
    public UnexpectedHttpException(HttpStatus status) {
        this.status = status;
    }
    
    public HttpStatus getStatus() {
        return status;
    }

}
