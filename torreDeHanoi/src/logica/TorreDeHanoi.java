/*

Programa hecho por:
Estrada Zermeño Raúl de Jesús

 */
package logica;

import java.util.ArrayList;

public class TorreDeHanoi {

    // pripiedades que necesita el programa
    private ArrayList<ArrayList<Integer>> estado = new ArrayList<>();
    public int discos, inicio, temp, fin;
    public int paso = 0;

    // constructor
    public TorreDeHanoi(int discos, int fin) {
        this.discos = discos;
        this.inicio = 1;
        this.temp = fin == 3 ? 2 : 3;
        this.fin = fin;
        initialize();
    }

    /*
    funcion para inicializar nuestra torre de hanoi dependiendo de cuantos 
    discos se le asigne
     */
    private void initialize() {
        // agregamos la lista con los discos
        for (int i = 0; i < 3; i++) {
            ArrayList<Integer> listDisc = new ArrayList<Integer>();
            if (i == 0) {
                for (int j = 1; j < this.discos + 1; j++) {
                    listDisc.add(j);
                }
            } else {
                for (int j = 1; j < this.discos + 1; j++) {
                    listDisc.add(0);
                }
            }
            this.estado.add(listDisc);
        }
    }

    /*
    funcion que nos va a servir para regresar el estado actual de la torre
    de hanoi, nos lo va a mostrar como un string.
     */
    private String showState() {

        String torre = "\n\nEstado de las Torres: \n\n";

        for (int i = 0; i < this.discos; i++) {
            for (int j = 0; j < 3; j++) {
                torre += String.valueOf(this.estado.get(j).get(i)) + "|\t";
            }
            torre += "\n";
        }
        torre += "------------------\n\n";
        return torre;
    }

    /*
    Este metodo nos sirve para realizar los cambios dentor del array 2d
    que tiene el estado actual de nuestra torre de hanoi
     */
    private void setChange(int fromPalo, int toPalo) {
        int disco = 0;
        // obtenemos disco de arriba
        for (int i = 0; i < this.discos; i++) {
            if (this.estado.get(fromPalo - 1).get(i) != 0) {
                // realizar cambio por 0
                disco = this.estado.get(fromPalo - 1).get(i);
                this.estado.get(fromPalo - 1).set(i, 0);
                break;
            }
        }
        // colocamos el disco 
        for (int i = this.discos - 1; i >= 0; i--) {
            if (this.estado.get(toPalo - 1).get(i) == 0) {
                // realizar cambio por el disco
                this.estado.get(toPalo - 1).set(i, disco);
                break;
            }
        }
        this.paso += 1;
        System.out.println(showState());
    }

    /*
    
    Metodo que realiza todos los procedimintos de la torre de hanoi utilizando
    recursividad
    
     */
    public void getSteps(int discos, int inicio, int temp, int fin) {
        if (discos == 1) {
            System.out.println("Operador: Movemos el disco de arriba del palo " + inicio + " al palo " + fin);
            setChange(inicio, fin);
        } else {
            getSteps(discos - 1, inicio, fin, temp);
            System.out.println("Operador: Movemos el disco de arriba del palo " + inicio + " al palo " + fin);
            setChange(inicio, fin);
            getSteps(discos - 1, temp, inicio, fin);
        }
    }

}
