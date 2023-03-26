package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.Price;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final ManufacturerRepository manufacturerRepository;
    private final PriceClient priceClient;
    private final MapsClient mapsClient;
    private final String urlGetPriceService = "http://localhost:8082/services/price?vehicleId={vehicleId}";

    public CarService(CarRepository repository,
                      ManufacturerRepository manufacturerRepository,
                      PriceClient priceClient, MapsClient mapsClient) {
        this.repository = repository;
        this.manufacturerRepository = manufacturerRepository;
        this.priceClient = priceClient;
        this.mapsClient = mapsClient;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {

        Optional<Car> carOptional = this.repository.findById(id);

        if (!carOptional.isPresent()){
            throw new CarNotFoundException("throw a CarNotFoundException");
        }

        Car car = carOptional.get();
        String price = priceClient.getPrice((long)1);
        car.setPrice(price);

        Location location = mapsClient.getAddress(new Location((double)0,(double)0));
        car.setLocation(location);
        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setPrice(car.getPrice());
                        carToBeUpdated.setCondition(car.getCondition());
                        carToBeUpdated.setCreatedAt(car.getCreatedAt());
                        carToBeUpdated.setModifiedAt(car.getModifiedAt());
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }
        Optional<Manufacturer> manufacturer =
                manufacturerRepository.findById(car.getDetails().getManufacturer().getCode());
        if (manufacturer.isPresent()){
            car.getDetails().setManufacturer(manufacturer.get());
        }
        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {

        Optional<Car> carOptional = this.repository.findById(id);

        if (!carOptional.isPresent()){
            throw new CarNotFoundException("throw a CarNotFoundException");
        }
        this.repository.delete(carOptional.get());
    }
}
