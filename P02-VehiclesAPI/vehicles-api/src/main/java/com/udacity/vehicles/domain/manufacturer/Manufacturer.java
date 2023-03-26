package com.udacity.vehicles.domain.manufacturer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Declares class to hold car manufacturer information.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true, value = {"_links"})
public class Manufacturer {

    @Id
    private Integer code;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(name, that.name);
    }

    public Manufacturer() { }

    public Manufacturer(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
