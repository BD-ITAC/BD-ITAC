package br.ita.bditac.ws.client;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import br.ita.bditac.ws.client.exceptions.ResourceNotFoundException;
import br.ita.bditac.ws.client.exceptions.UnexpectedHttpException;

public class ClientErrorHandler implements ResponseErrorHandler {

    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ResourceNotFoundException();
        }

        throw new UnexpectedHttpException(response.getStatusCode());
    }

    public boolean hasError(ClientHttpResponse response) throws IOException {
        if ((response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) || (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR)) {
            return true;
        }
        
        return false;
    }

}