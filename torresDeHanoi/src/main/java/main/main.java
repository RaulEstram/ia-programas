/*

Programa hecho por:
Estrada Zermeño Raúl de Jesús

 */
package main;

import java.util.Scanner;
import logica.TorreDeHanoi;

public class main {

    public static void main(String[] args) {
        // variables necesarias
        boolean continuar = true;
        int cont = 0;
        Scanner input = new Scanner(System.in);

        // pedimos cantidad de discos
        System.out.println("Cuantos Discos quiere? (3-9)");
        int discos = input.nextInt();

        // pedimos en que palo es el estado final
        System.out.println("En que palo quiere el estado Final?\n1) Palo 2\n2) Palo 3");
        int palo = input.nextInt();

        // creamos nuestro objeto de la torre
        TorreDeHanoi torre = new TorreDeHanoi(discos, palo);

        String next = "";
        while (continuar) {
            if (cont == 0) {
                System.out.println("Introduccion del programa");
                next = input.nextLine();
                cont++;
            } else if (cont == 1) {
                System.out.println(torre.nextStep());
                cont++;
            } else if (cont == 2) {
                if (torre.checkTower()) {
                    continuar = false;
                    System.out.println("Se llego al estado final");
                    continue;
                }
                System.out.println("Quieres ver el siguiente paso? (Y/N)");
                next = input.nextLine().toUpperCase();
                if (next.equals("Y")) {
                    System.out.println(torre.nextStep());
                } else {
                    continuar = false;
                }//if
            } // if
        }; // while
        System.out.println("Exit...");
        input.nextLine();
    }
}
