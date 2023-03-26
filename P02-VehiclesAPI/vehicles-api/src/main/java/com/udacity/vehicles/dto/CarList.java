package com.udacity.vehicles.dto;

import com.udacity.vehicles.domain.car.Car;

import java.util.List;

public class CarList {

    private List<Car> carList;

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
}
