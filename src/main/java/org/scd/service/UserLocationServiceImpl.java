package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.model.UserLocation;
import org.scd.model.dto.UserLocationDTO;
import org.scd.model.dto.UserLocationFilterDTO;
import org.scd.repository.UserLocationRepository;
import org.scd.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserLocationServiceImpl implements UserLocationService {

    private final UserLocationRepository userLocationRepository;

    private final UserRepository userRepository;

    public UserLocationServiceImpl(UserLocationRepository locationRepository, UserRepository userRepository) {
        this.userLocationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserLocation> getAllLocations() {
        List<UserLocation> userLocations = new ArrayList<>();
        userLocationRepository.findAll().forEach(userLocations::add);
        return userLocations;
    }

    @Override
    public UserLocation getLocationById(Long id) {
        return userLocationRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        userLocationRepository.deleteById(id);
    }

    @Override
    public UserLocation update(UserLocation userLocation, Long id) {
        UserLocation userLocationUpdated = userLocationRepository.getById(id);
        if(!(userLocation.getLatitude()==null)) userLocationUpdated.setLatitude(userLocation.getLatitude());
        if(!(userLocation.getLongitude()==null)) userLocationUpdated.setLongitude(userLocation.getLongitude());
        return userLocationRepository.save(userLocationUpdated);
    }
    @Override
    public UserLocationDTO addLocation(UserLocationDTO userLocationDTO) {
        userLocationRepository.save(new UserLocation(userLocationDTO.getLatitude(), userLocationDTO.getLongitude(),userLocationDTO.getDate(), userRepository.findByEmail(userLocationDTO.getEmail())));
        return null;
    }

    @Override
    public List<UserLocation> getLocationsBetweenDates(UserLocationFilterDTO userLocationFilterDTO) throws BusinessException{
        if (userLocationFilterDTO.getStartDate().compareTo(userLocationFilterDTO.getEndDate()) > 0)
            throw new BusinessException(403, "Error !");
        return userLocationRepository.dateQuery(userLocationFilterDTO.getUserId(),userLocationFilterDTO.getStartDate(),userLocationFilterDTO.getEndDate());
    }
}
