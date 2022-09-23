/*

Programa hecho por:
Estrada Zermeño Raúl de Jesús

 */
package main;

import java.util.Scanner;
import logica.TorreDeHanoi;

public class main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Cuandos discos quieres?");
        int discos = input.nextInt();
        System.out.println("En que palo quieres el estado final? (2-3)");
        int palo = input.nextInt();
        TorreDeHanoi torre = new TorreDeHanoi(discos, palo);
        torre.getSteps(torre.discos, torre.inicio, torre.temp, torre.fin);
        System.out.println("Cantidad de pasos Realizados: " + torre.paso);
    }
}
