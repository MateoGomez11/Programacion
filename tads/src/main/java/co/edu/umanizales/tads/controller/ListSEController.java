package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exceptions.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@Valid @RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null){
            return new ResponseEntity<>(new ResponseDTO(404,"La ubicación no existe", null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().add(new Kid(kidDTO.getIdentification(), kidDTO.getName(), kidDTO.getAge(), kidDTO.getGender(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha adicionado el Kid", null), HttpStatus.OK);
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
                200,"Se ha invertido la lista",
                null), HttpStatus.OK);

    }
    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO> orderBoysStart() {
        try {
            listSEService.getKids().orderBoysToStart();
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha ordenado los niños al inicio",
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/alternateboysandgirls")
    public ResponseEntity<ResponseDTO> alternateBoysAndGirls() {
        try {
            listSEService.getKids().alternateBoysAndGirls();
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha alternado los niños y las niñas",
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/deletebyage/{code}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable int code) {
        listSEService.getKids().deleteByAge(code);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha eliminado los niños con esa edad",
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/passpositions/{id}/{code}")
    public ResponseEntity<ResponseDTO> passPositions(@PathVariable String id, @PathVariable int code) {
        listSEService.getKids().passPositions(id,code);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adelantado el niño",
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/losepositions/{id}/{code}")
    public ResponseEntity<ResponseDTO> losePositions(@PathVariable String id, @PathVariable int code) {
        listSEService.getKids().losePositions(id,code);
        return new ResponseEntity<>(new ResponseDTO(
                200, "el niño ha perdido las posiciones",
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
    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKidsLocationGenders(@PathVariable byte age) {
        ReportKidsLocationGenderDTO report = new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listSEService.getKids().getReportKidsByLocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(200,report, null), HttpStatus.OK);
    }

    @GetMapping(path = "/reportkidsbyage")
    public ResponseEntity<ResponseDTO> getReportKidsByAge() {
        return new ResponseEntity<>(new ResponseDTO(200,listSEService.getKids().getReportKidsByAge(), null), HttpStatus.OK);
    }
    @GetMapping(path = "/addlastbyname/{initial}")
    public ResponseEntity<ResponseDTO> addLasByName(@PathVariable char initial) {
        try {
            listSEService.getKids().addLastByName(initial);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(), null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(200,"Se han agregado al final", null), HttpStatus.OK);
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
    public ResponseEntity<ResponseDTO> getKidsByCountry(){
        List<LocationDTO> LocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getTotalKidsByCountryCode(loc.getCode());
            if(count>0){
                LocationDTOList.add(new LocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,LocationDTOList, null), HttpStatus.OK);
    }

    /*
    @GetMapping(path = "/kidsbycountryandgender/{code}")
    public ResponseEntity<ResponseDTO> getKidsByCountryAndGender(@PathVariable int code){
        return new ResponseEntity<>(new ResponseDTO(200,listSEService.getKids().geKidsByLocationAndAgeCode(code),null),HttpStatus.OK);
    }
     */
    @GetMapping(path = "/averagekidsage")
    public ResponseEntity<ResponseDTO>getAverageKidsAge(){

        return new ResponseEntity<>(new ResponseDTO(200,listSEService.averageKidsByAge(),null),HttpStatus.OK);
    }





}
