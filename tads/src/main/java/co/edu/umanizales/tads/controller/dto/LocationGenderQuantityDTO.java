package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor

public class LocationGenderQuantityDTO {
    private String city;
    private List<GenderDTO> genders;
    private int total;

    public LocationGenderQuantityDTO(String city) {
        this.city = city;
        this.total=0;
        this.genders = new ArrayList<>();
        this.genders.add(new GenderDTO('M',0));
        this.genders.add(new GenderDTO('F',0));
    }
}
