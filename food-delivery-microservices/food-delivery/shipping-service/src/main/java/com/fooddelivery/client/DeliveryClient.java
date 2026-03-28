package com.fooddelivery.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DeliveryClient {

    private final RestTemplate restTemplate;

    @Value("${delivery.service.url}")
    private String deliveryServiceUrl;

    public DeliveryClient() {
        this.restTemplate = new RestTemplate();
    }

    public boolean deliveryExists(Long deliveryId) {
        try {
            String url = deliveryServiceUrl + "/delivery/" + deliveryId;
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
