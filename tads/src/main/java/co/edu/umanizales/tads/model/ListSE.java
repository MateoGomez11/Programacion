package co.edu.umanizales.tads.model;

import lombok.Data;

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
    public void invert(){
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }
    public void orderBoysToStart(){
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getGender()=='M')
                {
                    listCp.addToStart(temp.getData());
                }
                else{
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void changeExtremes(){
        if(this.head !=null && this.head.getNext() !=null)
        {
            Node temp = this.head;
            while(temp.getNext()!=null)
            {
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



