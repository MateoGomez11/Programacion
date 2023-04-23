package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.GenderDTO;
import co.edu.umanizales.tads.controller.dto.InformDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListSE {
    private Node head;
    private int size;

    public void add(Kid kid) {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
            head = new Node(kid);
        }
        size++;
    }

    public boolean searchKidIdentification(String id) {
        boolean kidFound = false;
        if (head != null) {
            Node temp = head;
            while (temp != null) {
                if (temp.getData().getIdentification().equals(id)) {
                    kidFound = true;
                    break;
                }
                temp = temp.getNext();
            }
        }
        return kidFound;
    }

    public void addToStart(Kid kid) {
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else {
            head = new Node(kid);
        }
        size++;
    }

    public void invert() {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void orderBoysToStart() {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void changeExtremes() {
        if (this.head != null && this.head.getNext() != null) {
            Node temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }

    }

    public void addxPos(Kid kid, int pos) {
        if (head != null) {
            Node temp = head;
            int contador = 1;
            while (contador < pos - 1) {
                temp = temp.getNext();
                contador = contador + 1;
            }
            Node newNode = new Node(kid);
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        } else {
            head = new Node(kid);
        }
    }

    public void removeById(String id) {
        if (head != null) {
            Node temp = head;

            if (head.getData().getIdentification().equals(id)) {
                head = head.getNext();
            } else {
                while (!temp.getNext().getData().getIdentification().equals(id)) {
                    temp = temp.getNext();
                }
                temp.setNext(temp.getNext().getNext());
            }
        }
    }

    public int getTotalKids() {
        int cont = 1;
        Node temp = head;
        while (temp.getNext() != null) {
            cont++;
            temp = temp.getNext();
        }
        return cont;
    }

    public float averageKidsAge() {
        int cont = 0;
        int cont2 = 0;
        Node temp = head;
        int totalKids = getTotalKids();
        while (totalKids > cont2) {
            int ages = temp.getData().getAge();
            cont = cont + ages;
            temp = temp.getNext();
            cont2++;
        }
        return (cont / (float) getTotalKids());
    }


    public List<InformDTO> geKidsByLocationAndAgeCode(int code) {
        List<InformDTO> informDTOList = new ArrayList<>();
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getAge() > code) {
                    Location location = temp.getData().getLocation();
                    boolean found = false;
                    for (InformDTO informDTO : informDTOList) {
                        if (informDTO.getLocation().equals(location)) {
                            informDTO.setTotal(informDTO.getTotal() + 1);
                            List<GenderDTO> genders = informDTO.getGenders();
                            for (GenderDTO genderDTO : genders) {
                                if (genderDTO.getGender() == temp.getData().getGender()) {
                                    genderDTO.setQuantity(genderDTO.getQuantity() + 1);
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                GenderDTO genderDTO = new GenderDTO(temp.getData().getGender(), 1);
                                genders.add(genderDTO);
                            }
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        List<GenderDTO> genders = new ArrayList<>();
                        GenderDTO genderDTO = new GenderDTO(temp.getData().getGender(), 1);
                        genders.add(genderDTO);
                        InformDTO informDTO = new InformDTO(location, genders, 1);
                        informDTOList.add(informDTO);
                    }
                }
                temp = temp.getNext();
            }
        }
        return informDTOList;
    }

    public int getTotalKidsByLocationCode(String code) {
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public int getTotalKidsByDeptCode(String code) {
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().substring(0, 5).equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public int getTotalKidsByCountrieCode(String code) {
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().substring(0, 3).equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }


}



