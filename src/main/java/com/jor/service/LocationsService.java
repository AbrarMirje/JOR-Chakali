package com.jor.service;

import com.jor.entity.Locations;

import java.util.List;

public interface LocationsService {
    Locations addLocation(Locations locations);
    Locations getLocation(Long id);

    List<Locations> getLocations();

    Boolean deleteLocation(Long id);

    Boolean updateLocation(Locations location, Long id);

}
