package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exceptions.ListDEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class ListDECircular {
    private NodeDE head;
    private int size;



    /*
    Listar:
    Crear un nuevo arreglo donde se guardaran los pets
    Llamar un ayudante y que se posicione en la cabeza de la lista
       La idea es recorrer toda la lista e ir agregando al arreglo de uno en uno

    Si la cabeza tiene datos:
            Mientras El ayudante.next sea distinto a la cabeza(Aqui sabremos cual es el final de la lista)
            Y mientras el ayudante.next sea distinto a nulo(Esto es cuando solo haya un dato en la lista)
                   agregar al arreglo creado
                   El ayudante pasara al siguiente

            Cuando se salga del ciclo sabremos que estamos parados en el ultimo dato de la lista, pues es el que tiene
            en su next a la cabeza, por lo tanto debemos agregarlo tambien al arreglo

            Devolvemos la lista
     */

    public List<Pet> getPets() {
        List<Pet> pets = new ArrayList<>();
        NodeDE temp = head;
        if (head != null) {
            while (temp.getNext() != head && temp.getNext() != null) {
                pets.add(temp.getData());
                temp = temp.getNext();
            }
            pets.add(temp.getData());
        }
        return pets;
    }

    /*
    ListaDE circular
    la cabeza agarra con el previous al ultimo
    el ultimo agarra con el next a la cabeza

     */


    /*
    Adicionar:
    2 Momentos:
    Cabeza vacia o cabeza con datos

    Metemos al nuevo pet en un costal

    Si la cabeza contiene datos:
    Llamamos a un ayudante y se ubica en la cabeza

    Mientras el ayudante.next sea distinto a la cabeza (Nuevamente asi identificamos que estemos en la ultima posicion)
    Y el ayudante.next sea disntinto a null (Esto es cuando solo haya un dato en la lista)
            Si cumple las 2 condiciones el ayudante podra avanzar
                    VALIDAR: Identificacion no sea igual a la de los demas

    Parados ya en la ultima posicion despues de que se salga del ciclo, debemos agregar al pet:
        El siguiente de donde esta parado el ayudante sera el costal
        El costal.next sera la cabeza, pues es el ultimo dato
        El costal.previous sera donde esta parado el ayudante
        Y debemos cambiar el previous de la cabeza por el nuevo costal(Ultimo en la lista)

    Si la cabeza no tiene datos
    Costal = cabeza
     */
    public void add(Pet pet) throws ListDEException {
        NodeDE newNodeDE = new NodeDE(pet);
        if (head != null) {
            NodeDE temp = head;
            while (temp.getNext() != head && temp.getNext() != null) {
                if (temp.getData().getIdentification().equals(pet.getIdentification())) {
                    throw new ListDEException("Ya existe un pet");
                }
                temp = temp.getNext();
            }
            if (temp.getData().getIdentification().equals(pet.getIdentification())) {
                throw new ListDEException("Ya existe un pet");
            }
            temp.setNext(newNodeDE);
            newNodeDE.setNext(head);
            newNodeDE.setPrevious(temp);
            head.setPrevious(newNodeDE);
        } else {
            head = new NodeDE(pet);
        }
        size++;
    }

    /*
    Adicionar al incio:
    Metemos al pet en un nuevoCostal

    Si la cabeza es distinta a null
    (Validar identificaciones)

            Tenemos 3 casos: No hay datos, hay 1 dato o hay mas de 1 dato

     Si solo hay 1 dato:
            La cabeza.previous y .next debe ser el nuevo costal
            El nuevo costal.next y .previous debe ser la cabeza

     Si hay mas de 1 dato:
            El nuevo costal.previous es el previous de la cabeza actual
            el next del previous de la cabeza actual es el nuevo costal
            El previous de la cabeza actual es el nuevo costal
            El next del costal actual esla cabeza actual

  En ambos caso se finaliza con que el nuevo costal es la nueva cabeza de la lista


            Si en la cabeza no hay datos
            cabeza = nuevo costal

     */
    public void addToStart(Pet pet) throws ListDEException {
        NodeDE newNodeDE = new NodeDE(pet);
        if (head != null) {
            if (size == 1) {
                if (head.getData().getIdentification().equals(pet.getIdentification())) {
                    throw new ListDEException("Ya existe un pet");
                }
                head.setPrevious(newNodeDE);
                head.setNext(newNodeDE);
                newNodeDE.setNext(head);
                newNodeDE.setPrevious(head);
            } else {
                NodeDE temp = head;
                while (temp.getNext() != head) {
                    if (temp.getData().getIdentification().equals(pet.getIdentification())) {
                        throw new ListDEException("Ya existe un pet");
                    }
                    temp = temp.getNext();
                }
                if (temp.getData().getIdentification().equals(pet.getIdentification())) {
                    throw new ListDEException("Ya existe un pet");
                }
                newNodeDE.setPrevious(head.getPrevious());
                head.getPrevious().setNext(newNodeDE);
                head.setPrevious(newNodeDE);
                newNodeDE.setNext(head);
            }
            head = newNodeDE;
        } else {
            head = new NodeDE(pet);
        }
        size++;
    }


    /*
    Adicionarxpos
    VALIDACIONES antes del metodo:

    1) como en este metodo es incierto si se recorrera o no toda la lista, crear un ciclo donde
    recorra todas las posiciones, en busca de que el id no se repita

    2) Cuando el usuario coloque una posicion mayor a la del tamaño de la lista, no tendria sentido agregar el pet

    3) Cuando el usuario coloque una posicion menor a 3, ya sea 0, un numero negativo o 1(Creo que si el objetivo es
    agregarlo en la primera posicion para eso se creo el metodo de agregar al inicio)

    Las Entradas del metodo son: pet y posicion

    Llamar a un ayudante y se posicione en la cabeza
    Inicializar un contador en 1
        Mientras el contador sea menor a la posicion ingresada por el usuario -1 (Es decir la posicion antes de la objetivo)
            El ayudante avanzara y el contador ira sumando\

        Cuando se salga del ciclo estaremos una posicion antes de la que se espera agregar

        Metemos al pet en un nuevo costal
        El next del nuevo costal sera el next de donde esta el ayudante
        el previous del siguiente donde esta el ayudante sera el nuevo costal
        El previous del nuevo costal sera donde esta el ayudante
        el siguiente de donde esta el ayudante sera el nuevo costal


Si no hay datos en la cabeza
    cabeza = nuevo costal

     */
    public void addxPos(Pet pet, int pos) throws ListDEException {
        if (head != null) {
            NodeDE temp2 = head;
            while (temp2.getNext() != head) {
                if (temp2.getData().getIdentification().equals(pet.getIdentification())) {
                    throw new ListDEException("Ya existe un pet");
                }
                temp2 = temp2.getNext();
            }
            if (temp2.getData().getIdentification().equals(pet.getIdentification())) {
                throw new ListDEException("Ya existe un pet");
            }
            if (pos > size) {
                throw new ListDEException("El numero es mayor a las posiciones en la lista");
            }
            if (pos < 2) {
                throw new ListDEException("El numero debe ser mayor a 1");
            }
            NodeDE temp = head;
            int contador = 1;
            while (contador < pos - 1) {
                if (temp.getData().getIdentification().equals(pet.getIdentification())) {
                    throw new ListDEException("Ya existe un pet");
                }
                temp = temp.getNext();
                contador++;
            }
            NodeDE newNode = new NodeDE(pet);
            newNode.setNext(temp.getNext());
            temp.getNext().setPrevious(newNode);
            newNode.setPrevious(temp);
            temp.setNext(newNode);

        } else {
            head = new NodeDE(pet);
        }
        size++;
    }

    /*
        Investigar sobre como crear el numero aleatorio

        Cuando tenga el numero aleatorio:
            Inciar un contador en 1

            Si la cabeza tiene datos:
                Si la cabeza.next es distinta de null (Esto quiere decir que hay mas de 1 dato)
                El ayudante estara en la cabeza
                    Mientras el contador sea menor al numero aleatorio
                        El ayudante avanzara y el contador ira sumando

                    CUando se salga del ciclo ya estaremos en la posicion del numero aleatorio
                    Preguntamos si el pet ya ha sido bañado:
                    SI:
                        Devolver que el perro ya ha sido banado y el numero que salio

                    NO:
                        Cambiar el estado del baño al pet con el ayudante


                    Si la cabeza.next es null
                        Imprimir mensaje de que la idea es tener mas de 1 dato

     */
    public int takeAShower() throws ListDEException {
        int numAleatorio = generarNumeroAleatorio();
        int cont = 1;
        if (head != null) {
            if (head.getNext() != null) {
                NodeDE temp = head;

                while (cont < numAleatorio) {
                    temp = temp.getNext();
                    cont++;
                }
                if (temp.getData().isShower()) {
                    throw new ListDEException("El pet ya ha sido bañado, el numero fue: " + numAleatorio);
                }
                temp.getData().setShower(true);
            } else {
                throw new ListDEException("Debe haber mas de 1 pet para realizar el metodo");
            }
        }

        return numAleatorio;
    }

    public static int generarNumeroAleatorio() {
        Random random = new Random();
        int numero = random.nextInt(50);
        return numero;
    }


}
