package com.example.nobs.catFact;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.nobs.Query;

@Service
public class CatFactService implements Query<Integer, CatFactDTO> {

    private final RestTemplate restTemplate;
    private final String url = "https://catfact.ninja/fact";
    private final String MAX_LENGTH = "max_length";

    public CatFactService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<CatFactDTO> execute(Integer input) {

        //sets up our URL with query string parameters
        @SuppressWarnings("deprecation")
        URI uri = UriComponentsBuilder
                    .fromHttpUrl(url)
                    .queryParam(MAX_LENGTH, input)
                    .build()
                    .toUri();

        //headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json"); // could abstract

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // handle a cat fact error
        try {
            ResponseEntity<CatFactResponse> response = restTemplate
                .exchange(uri, HttpMethod.GET, entity, CatFactResponse.class);
        
            CatFactDTO catFactDTO = new CatFactDTO(response.getBody().getFact());
            return ResponseEntity.ok(catFactDTO);
        } catch (Exception exception) {
            throw new RuntimeException("Cat Facts API is down");
        }

        
    }

}
