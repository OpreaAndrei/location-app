package org.scd.service;
import org.scd.config.exception.BusinessException;
import org.scd.model.UserLocation;
import org.scd.model.dto.UserLocationDTO;
import org.scd.model.dto.UserLocationFilterDTO;

import java.util.List;

public interface UserLocationService {

    List<UserLocation> getAllLocations();

    UserLocation getLocationById(final Long id);

    void deleteById(final Long id);

    UserLocation update(UserLocation userLocation, Long id);

    UserLocationDTO addLocation(final UserLocationDTO userLocationDTO);

    List<UserLocation> getLocationsBetweenDates(UserLocationFilterDTO userLocationFilterDTO) throws BusinessException ;

}
