package org.scd.controller;
import org.scd.config.exception.BusinessException;
import org.scd.model.UserLocation;
import org.scd.model.dto.UserLocationDTO;
import org.scd.model.dto.UserLocationFilterDTO;
import org.scd.service.UserLocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


    @RestController()
    @RequestMapping("/locations")
    public class UserLocationController {
        private final UserLocationServiceImpl locationServiceImpl;

        @Autowired
        public UserLocationController(UserLocationServiceImpl locationServiceImpl){
            this.locationServiceImpl = locationServiceImpl;
        }

        @CrossOrigin(origins="http://localhost:4200/")
        @GetMapping(path = "/all")
        public List<UserLocation> getAllLocations(){
            return locationServiceImpl.getAllLocations();
        }


        @GetMapping(path = "/id/{locationid}")
        @CrossOrigin(origins="http://localhost:4200")
        public UserLocation getLocation(@PathVariable("locationid")Long locationid){
            return locationServiceImpl.getLocationById(locationid);
        }


        @DeleteMapping(path = "/delete/{locationid}")
        @CrossOrigin(origins="http://localhost:4200")
        public void deleteLocation(@PathVariable("locationid")Long locationid){
            locationServiceImpl.deleteById(locationid);
        }

        @RequestMapping(method = RequestMethod.PUT, value="/update/{id}")
        @CrossOrigin(origins="http://localhost:4200")
        public UserLocation updateLocation(@RequestBody UserLocation userLocation,@PathVariable long id){
            return locationServiceImpl.update(userLocation,id);
        }

        @PostMapping(path = "/create")
        public ResponseEntity<UserLocationDTO> addLocation(@RequestBody final UserLocationDTO userLocationDTO){
            return ResponseEntity.ok(this.locationServiceImpl.addLocation(userLocationDTO));
        }

        @PostMapping(path="/filter")
        public List<UserLocation> getFilterDates(@RequestBody UserLocationFilterDTO userLocationFilterDTO) throws BusinessException{
            return locationServiceImpl.getLocationsBetweenDates(userLocationFilterDTO);
        }

    }

