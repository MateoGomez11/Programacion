package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.InformDTO;
import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.LocationDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;


    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        boolean identification = listSEService.getKids().searchKidIdentification(kidDTO.getIdentification());
        if(location == null){
            return new ResponseEntity<>(new ResponseDTO(404,"La ubicación no existe", null), HttpStatus.OK);
        }else if(identification == true){
            return new ResponseEntity<>(new ResponseDTO(404,"El Id ya se encuentra registrado", null), HttpStatus.OK);
        }
        listSEService.getKids().add(new Kid(kidDTO.getIdentification(), kidDTO.getName(), kidDTO.getAge(),
                        kidDTO.getGender(), location));
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha adicionado el niño", null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids().getHead(),null), HttpStatus.OK);
    }
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert(){
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200,"SE ha invertido la lista",
                null), HttpStatus.OK);

    }
    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"SE han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation(){
        List<LocationDTO> LocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getTotalKidsByLocationCode(loc.getCode());
            if(count>0){
                LocationDTOList.add(new LocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,LocationDTOList, null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbydepartment")
    public ResponseEntity<ResponseDTO> getKidsByDepartment(){
        List<LocationDTO> LocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getTotalKidsByDeptCode(loc.getCode());
            if(count>0){
                LocationDTOList.add(new LocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,LocationDTOList, null), HttpStatus.OK);
    }
    @GetMapping(path = "/kidsbycountry")
    public ResponseEntity<ResponseDTO> getKidsByCountries(){
        List<LocationDTO> LocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getTotalKidsByCountrieCode(loc.getCode());
            if(count>0){
                LocationDTOList.add(new LocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,LocationDTOList, null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbycountryandgender/{code}")
    public ResponseEntity<ResponseDTO> getKidsByCountryAndGender(@PathVariable int code){
        return new ResponseEntity<>(new ResponseDTO(200,listSEService.getKids().geKidsByLocationAndAgeCode(code),null),HttpStatus.OK);
    }

    @GetMapping(path = "/averagekidsage")
    public ResponseEntity<ResponseDTO>getAverageKidsAge(){

        return new ResponseEntity<>(new ResponseDTO(200,listSEService.averageKidsByAge(),null),HttpStatus.OK);
    }

}
