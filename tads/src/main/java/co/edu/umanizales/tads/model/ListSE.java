package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.AgeDTO;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
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

    public int getTotalKids() {
        int cont = 1;
        Node temp = head;
        while (temp.getNext() != null) {
            cont++;
            temp = temp.getNext();
        }
        return cont;
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

    public void alternateBoysAndGirls() {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            ListSE listBoys = new ListSE();
            ListSE listGirls = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'F') {
                    if (listGirls.getHead() == null) {
                        listGirls.addToStart(temp.getData());
                    } else {
                        listGirls.add(temp.getData());
                    }

                }
                if (temp.getData().getGender() == 'M') {
                    if (listBoys.getHead() == null) {
                        listBoys.addToStart(temp.getData());
                    } else {
                        listBoys.add(temp.getData());
                    }
                }
                temp = temp.getNext();
            }
            Node tempBoys = listBoys.getHead();
            Node tempGirls = listGirls.getHead();
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
            Node temp = head;

            while (temp.getNext() != null) {
                if (temp.getNext().getData().getAge() == age) {
                    temp.setNext(temp.getNext().getNext());
                } else {
                    temp = temp.getNext();
                }
            }
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

    public void passPositions(String id, int pos) {
        ListSE listCp = new ListSE();
        Node temp = this.head;
        int cont = 1;
        if (this.head != null) {
            while (temp.getNext() != null) {
                if (this.head.getData().getIdentification().equals(id)) {
                    listCp.addToStart(this.head.getData());
                    head = head.getNext();
                    break;

                } else if (temp.getNext().getData().getIdentification().equals(id)) {
                    listCp.addToStart(temp.getNext().getData());
                    temp.setNext(temp.getNext().getNext());
                    cont++;
                    break;
                }
                temp = temp.getNext();
                cont++;
            }

            int pos2 = cont + pos;
            Node temp2 = listCp.getHead();
            addxPos(temp2.getData(), pos2);

        }
    }

    public void losePositions(String id, int pos) {
        ListSE listCp = new ListSE();
        Node temp = this.head;
        int cont = 0;
        if (this.head != null) {
            while (temp.getNext() != null) {
                if (temp.getNext().getData().getIdentification().equals(id)) {
                    listCp.addToStart(temp.getNext().getData());
                    temp.setNext(temp.getNext().getNext());
                    cont++;
                    break;
                }
                temp = temp.getNext();
                cont++;
            }
            int pos2 = cont - pos;
            Node temp2 = listCp.getHead();
            if (pos2 == 0 || pos2 < 0) {
                addToStart(temp2.getData());
            } else {
                addxPos(temp2.getData(), pos2);
            }
        }
    }

    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report) {
        if (head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getAge() > age) {
                    report.updateQuantity(
                            temp.getData().getLocation().getName(),
                            temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }

    public List<AgeDTO> getReportKidsByAge() {
        List<AgeDTO> ageDTOList = new ArrayList<>();
        if (this.head != null) {
            Node temp = this.head;
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

    public void addLastByName(char initial) {
        ListSE listCp = new ListSE();
        Node temp = this.head;
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
                            temp.setNext(temp.getNext().getNext());
                        }
                        break;
                    } else {
                        listCp.addToStart(temp.getNext().getData());
                        temp.setNext(temp.getNext().getNext());
                    }
                }
            }
            temp = temp.getNext();
        }
        Node temp2 = listCp.getHead();
        while (temp2 != null) {
            add(temp2.getData());
            temp2 = temp2.getNext();
        }
    }
   /* public List<InformDTO> geKidsByLocationAndAgeCode(int code) {
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

    */

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

    public int getTotalKidsByCountryCode(String code) {
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



