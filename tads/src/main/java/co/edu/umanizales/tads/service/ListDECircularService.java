package co.edu.umanizales.tads.service;
import co.edu.umanizales.tads.model.ListDECircular;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListDECircularService {
    private ListDECircular pets;
    public ListDECircularService(){
        pets = new ListDECircular();
    }
}
