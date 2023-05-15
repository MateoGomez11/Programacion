package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exceptions.ListDEException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDECircularService;
import co.edu.umanizales.tads.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/listdecircular")
public class ListDECircularController {
    @Autowired
    private ListDECircularService listDECircularService;
    @Autowired
    private LocationService locationService;

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

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        return new ResponseEntity<>(new ResponseDTO(200, listDECircularService.getPets().getPets(), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@Valid @RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
        }
        try {
            listDECircularService.getPets().add(new Pet(petDTO.getIdentification(), petDTO.getName(), petDTO.getAge(), petDTO.getGender(), location, false));
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado el pet", null), HttpStatus.OK);
    }


    @PostMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@Valid @RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
        }try{
            listDECircularService.getPets().addToStart(new Pet(petDTO.getIdentification(), petDTO.getName(), petDTO.getAge(), petDTO.getGender(), location,false));

        }catch(ListDEException e){
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
        }
         return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado el pet al incio", null), HttpStatus.OK);
    }
    @PostMapping(path = "/addxpos/{pos}")
    public ResponseEntity<ResponseDTO> addToStart(@Valid @RequestBody PetDTO petDTO ,@PathVariable int pos) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
        }
        try{
            listDECircularService.getPets().addxPos((new Pet(petDTO.getIdentification(), petDTO.getName(), petDTO.getAge(), petDTO.getGender(), location,false)), pos);
        } catch(ListDEException e){
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado el pet en esa posicion", null), HttpStatus.OK);
    }

    @GetMapping(path = "/takeashower")
    public ResponseEntity<ResponseDTO> takeAShower() {
        int numAleatorio;
        try{
            numAleatorio = listDECircularService.getPets().takeAShower();
        } catch(ListDEException e){
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha bañado el pet en la posicion: " + numAleatorio, null), HttpStatus.OK);
    }



}
