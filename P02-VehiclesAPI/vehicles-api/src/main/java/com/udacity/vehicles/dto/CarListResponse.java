package com.udacity.vehicles.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"_links"})
public class CarListResponse{

    @JsonProperty("_embedded")
    private CarList _embedded;

    public CarList get_embedded() {
        return _embedded;
    }
}
