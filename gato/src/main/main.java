/*

Codigo por Estrada Zermeño Raúl de Jesús

 */
package main;

import java.io.IOException;
import java.util.Scanner;
import logica.Gato;

public class main {

    public static void main(String[] args) throws IOException {

        // variables necesarias para el menu
        int victoriasPlayer = 0, victoriasPC = 0, empates = 0;
        boolean continuar = true;
        Scanner input = new Scanner(System.in);
        boolean playerFirst;
        System.out.println("Con que numero quieres jugar? (1/2)");
        int simbolo = input.nextInt();
        do {
            System.out.println("Contador: \nVictorias jugador: " + victoriasPlayer + "\nVictorias PC: " + victoriasPC + "\nEmpates: " + empates);
            System.out.println("Quien va Primero? \n1) Jugador\n2) Maquina\n3) Salir del juego");
            int jugador = input.nextInt();
            if (jugador == 1) {
                playerFirst = true;
            } else if (jugador == 2) {
                playerFirst = false;
            } else if (jugador == 3) {
                break;
            } else {
                continue;
            }

            int matriz[][];
            matriz = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    matriz[i][j] = 0;
                }
            }

            Gato gato = new Gato(matriz, playerFirst, simbolo);
            int ganador = gato.theWinner;
            if (ganador == 0) {
                empates += 1;
            } else if (ganador == 1) {
                victoriasPlayer += 1;
            } else if (ganador == 2) {
                victoriasPC += 1;
            }

        } while (continuar);

    }
}
