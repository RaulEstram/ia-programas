/*

Programa hecho por:
Estrada Zermeño Raúl de Jesús

 */
package logica;

import java.util.ArrayList;

public class TorreDeHanoi {

    // elementos que vamos a necesitar para nuestra torre de hanoi
    private ArrayList<ArrayList<Integer>> estado = new ArrayList<>();
    private int discos;
    private int palo;
    private String operador = "";
    private int paso = 1;

    // constructor para definir cuantos discos va a tener nuestra torre
    public TorreDeHanoi(int discos, int palo) {
        this.discos = discos;
        this.palo = palo;
        initialize();
    }

    public TorreDeHanoi(int discos) {
        this.discos = discos;
        this.palo = 2;
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
    funcion que va a realizar los movimientos de los discos paso por paso.
     */
    private void changeDisc() {
        paso += 1;
        outerloop:
        for (ArrayList<Integer> palo : estado) {
            for (int i = 0; i < this.discos; i++) {
                int disco = palo.get(i);
                int numPalo = this.estado.indexOf(palo);
                if (disco != 0 && disco != this.discos && numPalo == 0) {
                    this.operador = "Operador: el Disco " + disco + " se fue al palo " + ((this.palo == 2) ? 2 : 3);
                    palo.set(i, 0);
                    this.estado.get((this.palo == 2) ? 1 : 2).set(this.discos - (i + 1), disco);
                    break outerloop;
                } else if (disco != 0 && disco == this.discos) {
                    this.operador = "Operador: el Disco " + disco + " se fue al palo " + ((this.palo == 2) ? 3 : 2);
                    palo.set(i, 0);
                    this.estado.get((this.palo == 2) ? 2 : 1).set(this.discos - 1, disco);
                    break outerloop;
                } else if (disco != 0 && disco != this.discos && numPalo != 0 && numPalo != 2) {
                    this.operador = "Operador: el Disco " + disco + " se fue al palo " + ((this.palo == 2) ? 3 : 2);
                    palo.set(i, 0);
                    this.estado.get((this.palo == 2) ? 2 : 1).set(this.discos - (i + 1), disco);
                    break outerloop;
                }
            }
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
    
    Funcion para saber si la torre llego a su estado final
    
     */
    public boolean checkTower() {
        int count = 0;
        for (int i = 0; i < this.discos; i++) {
            if (this.estado.get(this.palo).get(i) != 0) {
                count++;
            }
        }
        if (count == this.discos) {
            return true;
        } 
        return false;
    }

    /*
    funcion que va a regresar un string con todo el resumen de la torre de hanoi
     */
    public String nextStep() {
        String data = "";
        data += "Paso No. " + this.paso + "\n\n";
        changeDisc();
        data += this.operador;
        data += showState();
        return data;
    }

}
