package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.LocationDTO;
import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listde")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {

        return new ResponseEntity<>(new ResponseDTO(200, listDEService.getPets().getPets(), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        boolean identification = listDEService.getPets().searchPetIdentification((petDTO.getIdentification()));
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
        } else if (identification == true) {
            return new ResponseEntity<>(new ResponseDTO(404, "El Id ya se encuentra registrado", null), HttpStatus.OK);
        }

        listDEService.getPets().add(new Pet(petDTO.getIdentification(), petDTO.getName(), petDTO.getAge(),
                petDTO.getGender(), location));
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado el niño", null), HttpStatus.OK);
    }

    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert() {
        listDEService.getPets().invert();
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha invertido la lista", null), HttpStatus.OK);
    }

    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO> orderBoysStart() {
        listDEService.getPets().orderBoysToStart();
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha ordenado los niños al inicio", null), HttpStatus.OK);
    }

    @GetMapping(path = "/alternateboysandgirls")
    public ResponseEntity<ResponseDTO> alternateBoysAndGirls() {
        listDEService.getPets().alternateBoysAndGirls();
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha alternado los niños y las niñas",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/deletebyage/{code}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable int code) {
        listDEService.getPets().deleteByAge(code);
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha eliminado los niños con esa edad", null), HttpStatus.OK);
    }

    @GetMapping(path = "/averagepetsage")
    public ResponseEntity<ResponseDTO> getAveragePetsAge() {
        return new ResponseEntity<>(new ResponseDTO(200, listDEService.getPets().averagePetsAge(), null), HttpStatus.OK);
    }

    @GetMapping(path = "/petsbylocations")
    public ResponseEntity<ResponseDTO> getPetsByLocation() {
        List<LocationDTO> LocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocations()) {
            int count = listDEService.getPets().getTotalPetsByLocationCode(loc.getCode());
            if (count > 0) {
                LocationDTOList.add(new LocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200, LocationDTOList, null), HttpStatus.OK);
    }
    @GetMapping(path = "/passpositions/{id}/{code}")
    public ResponseEntity<ResponseDTO> passPositions(@PathVariable String id, @PathVariable int code) {
        listDEService.getPets().passPositions(id,code);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adelantado el niño",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/losepositions/{id}/{code}")
    public ResponseEntity<ResponseDTO> losePositions(@PathVariable String id, @PathVariable int code) {
        listDEService.getPets().losePositions(id,code);
        return new ResponseEntity<>(new ResponseDTO(
                200, "el niño ha perdido las posiciones",
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/reportkidsbyage")
    public ResponseEntity<ResponseDTO> getReportKidsByAge() {
        return new ResponseEntity<>(new ResponseDTO(200,listDEService.getPets().getReportKidsByAge(), null), HttpStatus.OK);
    }

    @GetMapping(path = "/addlastbyname/{initial}")
    public ResponseEntity<ResponseDTO> addLasByName(@PathVariable char initial) {
        listDEService.getPets().addLastByName(initial);
        return new ResponseEntity<>(new ResponseDTO(200,"Se han agregado al final", null), HttpStatus.OK);
    }




}
