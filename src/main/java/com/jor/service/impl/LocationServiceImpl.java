package com.jor.service.impl;

import com.jor.entity.Locations;
import com.jor.repository.LocationsRepository;
import com.jor.service.LocationsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationsService {

    private final LocationsRepository locationsRepository;

    @Override
    public Locations addLocation(Locations locations) {
        return locationsRepository.save(locations);
    }

    @Override
    public Locations getLocation(Long id) {
        return locationsRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Location not found with id " + id)
        );
    }

    @Override
    public List<Locations> getLocations() {
        return locationsRepository.findAll();
    }

    @Override
    @Transactional
    public Boolean deleteLocation(Long id) {
        try {
            locationsRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Location not found with id " + id)
            );

            locationsRepository.deleteById(id);
            return true;

        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional
    public Boolean updateLocation(Locations location, Long id) {
        Locations oldLocation = locationsRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Location not found with id " + id)
        );

        if (location.getLocationName() != null) oldLocation.setLocationName(location.getLocationName());

        locationsRepository.save(oldLocation);
        return true;
    }
}
