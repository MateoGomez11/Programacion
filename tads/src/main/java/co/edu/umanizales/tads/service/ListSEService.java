package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.controller.dto.LocationDTO;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Node;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class ListSEService {
    private ListSE kids;
    public ListSEService() {
        kids = new ListSE();
    }
/*
    public List<Location> getCitiesOfKids() {
        List<Location> cities2 = new ArrayList<>();
        if (kids.getHead() != null) {
            Node temp = kids.getHead();
            cities2.add(temp.getData().getLocation());
            while (temp.getNext() != null) {
                temp = temp.getNext();
                if (!cities2.contains(temp.getData().getLocation())) {
                    cities2.add(temp.getData().getLocation());
                }
            }
        }
        return cities2;
    }

    public List<LocationDTO> getreportCities() {
        Node temp = kids.getHead();
        List<LocationDTO> locationDTOList = new ArrayList<>();

        while (temp != null) {
            String cityCount = temp.getData().getLocation().getCode();
            Location findLocation = locationService.getCityById(cityCount);

            if (findLocation != null) {
                boolean found = false;
                for (LocationDTO locationDTO : locationDTOList) {
                    if (locationDTO.getCityID().equals(cityCount)) {
                        locationDTO.setQuantity(locationDTO.getQuantity() + 1);
                        found = true;
                    }
                }
                if (!found) {
                    LocationDTO locationDTO = new LocationDTO(temp.getData().getLocation().getName(), cityCount, 1);
                    locationDTOList.add(locationDTO);
                }
            }
            temp = temp.getNext();
        }
        return locationDTOList;
    }
    */







    public float averageKidsByAge() {
        return kids.averageKidsAge();
    }
    public void invert(){
        kids.invert();
    }

}









