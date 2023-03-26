package com.udacity.vehicles.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"_links"})
public class CarResponse {
    @JsonProperty("_embedded")
    private CarList _embedded;

    public CarList get_embedded() {
        return _embedded;
    }
}
