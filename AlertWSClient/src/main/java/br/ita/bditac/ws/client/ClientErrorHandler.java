package br.ita.bditac.ws.client;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class ClientErrorHandler implements ResponseErrorHandler {

    public void handleError(ClientHttpResponse response) throws IOException {

    }

    public boolean hasError(ClientHttpResponse response) throws IOException {
        if ((response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) || (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR)) {
            return true;
        }
        
        return false;
    }

}