package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.AgeDTO;
import co.edu.umanizales.tads.exceptions.ListDEException;
import co.edu.umanizales.tads.exceptions.ListSEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListDE {
    private NodeDE head;
    private int size;


    public List<Pet> getPets(){
        List<Pet> pets = new ArrayList<>();
        NodeDE temp = head;
        if (head != null){
            while(temp != null){
                pets.add(temp.getData());
                temp = temp.getNext();
            }
        }
        return pets;
    }

    public void add(Pet pet) throws ListDEException {
        NodeDE newNodeDE = new NodeDE(pet);
        if(head != null){
            NodeDE temp = head;
            while (temp.getNext() != null){
                if (temp.getData().getIdentification().equals(pet.getIdentification())) {
                    throw new ListDEException("Ya existe un pet");
                }
                temp = temp.getNext();
            }if (temp.getData().getIdentification().equals(pet.getIdentification())) {
                throw new ListDEException("Ya existe un niño");
            }
            temp.setNext(newNodeDE);
            newNodeDE.setPrevious(temp);
        } else {
            head = new NodeDE(pet);
        }
        size ++;
    }
    public void removeById(String id) {
        if (head != null) {
            NodeDE temp = head;

            if (head.getData().getIdentification().equals(id)) {
                head = head.getNext();
                head.setPrevious(null);
            } else {
                while (!temp.getNext().getData().getIdentification().equals(id)) {
                    temp = temp.getNext();
                }
                temp.getNext().getNext().setPrevious(temp);
                temp.setNext(temp.getNext().getNext());

            }
        }
    }

    public void addToStart(Pet pet){
        NodeDE newNodeDE = new NodeDE(pet);
        if(head != null){
            head.setPrevious(newNodeDE);
            newNodeDE.setNext(head);
            head = newNodeDE;
        }
        else{
            head = new NodeDE(pet);
            head.setPrevious(null);
        }
        size ++;
    }
    public boolean searchPetIdentification(String id) {
        boolean petFound = false;
        if (head != null) {
            NodeDE temp = head;
            while (temp != null) {
                if (temp.getData().getIdentification().equals(id)) {
                    petFound = true;
                    break;
                }
                temp = temp.getNext();
            }
        }
        return petFound;
    }

    public void addxPos(Pet pet, int pos) {
        if (head != null) {
            NodeDE temp = head;
            int contador = 1;
            while (contador < pos - 1 && temp.getNext() != null) {
                temp = temp.getNext();
                contador++;
            }
            NodeDE newNode = new NodeDE(pet);
            if (temp.getNext() != null) {
                newNode.setNext(temp.getNext());
                temp.getNext().setPrevious(newNode);
            }
            newNode.setPrevious(temp);
            temp.setNext(newNode);

        } else {
            head = new NodeDE(pet);
        }
    }

    public void invert() {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void orderBoysToStart() throws ListDEException {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender().equals("M")) {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }
    public void alternateBoysAndGirls() throws ListDEException {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            ListDE listBoys = new ListDE();
            ListDE listGirls = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender().equals("F")) {
                    if (listGirls.getHead() == null) {
                        listGirls.addToStart(temp.getData());
                    } else {
                        listGirls.add(temp.getData());
                    }

                }
                if (temp.getData().getGender().equals("M")) {
                    if (listBoys.getHead() == null) {
                        listBoys.addToStart(temp.getData());
                    } else {
                        listBoys.add(temp.getData());
                    }
                }
                temp = temp.getNext();
            }
            NodeDE tempBoys = listBoys.getHead();
            NodeDE tempGirls = listGirls.getHead();
            while (tempBoys != null || tempGirls != null) {
                if (tempBoys != null) {
                    listCp.add(tempBoys.getData());
                    tempBoys = tempBoys.getNext();
                }
                if (tempGirls != null) {
                    listCp.add(tempGirls.getData());
                    tempGirls = tempGirls.getNext();
                }
            }
            this.head = listCp.getHead();
        }
    }
    public void deleteByAge(int age) {
        while (head != null && head.getData().getAge() == age) {
            head = head.getNext();
        }
        if (head != null) {
            NodeDE temp = head;

            while (temp.getNext() != null) {
                if (temp.getNext().getData().getAge() == age) {
                    temp.getNext().getNext().setPrevious(temp);
                    temp.setNext(temp.getNext().getNext());

                } else {
                    temp = temp.getNext();
                }
            }
        }
    }
    public int getTotalPets() {
        int cont = 1;
        NodeDE temp = head;
        while (temp.getNext() != null) {
            cont++;
            temp = temp.getNext();
        }
        return cont;
    }

    public float averagePetsAge() {
        int cont = 0;
        int cont2 = 0;
        NodeDE temp = head;
        int totalPets = getTotalPets();
        while (totalPets > cont2) {
            int ages = temp.getData().getAge();
            cont = cont + ages;
            temp = temp.getNext();
            cont2++;
        }
        return (cont / (float) getTotalPets());
    }

    public int getTotalPetsByLocationCode(String code) {
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }
    public void passPositions(String id, int pos) {
        ListDE listCp = new ListDE();
        NodeDE temp = this.head;
        int cont = 1;
        if (this.head != null) {
            while (temp.getNext() != null) {
                if (this.head.getData().getIdentification().equals(id)) {
                    listCp.addToStart(this.head.getData());
                    head = head.getNext();
                    break;

                } else if (temp.getNext().getData().getIdentification().equals(id)) {
                    listCp.addToStart(temp.getNext().getData());
                    temp.getNext().getNext().setPrevious(temp);
                    temp.setNext(temp.getNext().getNext());
                    cont++;
                    break;
                }
                temp = temp.getNext();
                cont++;
            }

            int pos2 = cont + pos;
            NodeDE temp2 = listCp.getHead();
            addxPos(temp2.getData(), pos2);
        }
    }

    public void losePositions(String id, int pos) {
        ListDE listCp = new ListDE();
        NodeDE temp = this.head;
        int cont = 1;
        if (this.head != null) {
            while (temp.getNext() != null) {
                if (temp.getNext().getData().getIdentification().equals(id)) {
                    listCp.addToStart(temp.getNext().getData());
                    if (temp.getNext().getNext() != null) {
                        temp.getNext().getNext().setPrevious(temp);
                    }
                    temp.setNext(temp.getNext().getNext());
                    cont++;
                    break;
                }
                temp = temp.getNext();
                cont++;
            }
            int pos2 = cont - pos;
            NodeDE temp2 = listCp.getHead();
            if (pos2 == 1 || pos2 < 0) {
                addToStart(temp2.getData());
            } else {
                addxPos(temp2.getData(), pos2);
            }
        }
    }

    public List<AgeDTO> getReportKidsByAge() {
        List<AgeDTO> ageDTOList = new ArrayList<>();
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                boolean found = false;
                for (AgeDTO age : ageDTOList) {
                    if (age.getAge() == temp.getData().getAge()) {
                        age.setQuantity(age.getQuantity() + 1);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    AgeDTO ageDTO = new AgeDTO(temp.getData().getAge(), 1);
                    ageDTOList.add(ageDTO);
                }
                temp = temp.getNext();
            }
        }
        return ageDTOList;
    }

    public void addLastByName(char initial) throws ListDEException{
        ListDE listCp = new ListDE();
        NodeDE temp = this.head;
        while (temp.getNext() != null) {
            if (this.head != null) {
                if (this.head.getData().getName().charAt(0) == initial) {
                    if(listCp.getHead() != null){
                        listCp.add(temp.getData());
                    }else {
                        listCp.addToStart(this.head.getData());
                    }
                    head= head.getNext();
                }
                else
                    while (temp.getNext().getData().getName().charAt(0) == initial) {
                        if (listCp.getHead() != null) {
                            if(temp.getNext().getNext() != null){
                                listCp.add(temp.getNext().getData());
                                temp.getNext().getNext().setPrevious(temp);
                                temp.setNext(temp.getNext().getNext());
                            }
                            break;
                        } else {
                            listCp.addToStart(temp.getNext().getData());
                            temp.getNext().getNext().setPrevious(temp);
                            temp.setNext(temp.getNext().getNext());
                        }
                    }
            }
            temp = temp.getNext();
        }
        NodeDE temp2 = listCp.getHead();
        while (temp2 != null) {
            add(temp2.getData());
            temp2 = temp2.getNext();
        }
    }



}
