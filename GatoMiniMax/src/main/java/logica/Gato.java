/*

Codigo por Estrada Zermeño Raúl de Jesús

Usaremos un algoritmo llamado minimax que utiliza funciones recursivas.

 */
package logica;

import java.io.IOException;
import java.util.Scanner;

public class Gato {

    /*
    Propiedades necesarias para la clase
    */
    private int tablero[][];
    private int deepth = 0;
    public int endBoard = 3;
    private boolean change;
    private boolean winner = false;
    public int theWinner = 0;
    private boolean first = true;

    /*
    Constructor de la clase.
    
    Dentro de la misma clase se empieza a ejecutar toda la logica del 
    juego del gato, para simplemente crear la clase y que empiece el juego
    */
    public Gato(int[][] matris, boolean first) throws IOException {
        this.tablero = matris;
        this.first = first;
        this.change = true;

        while (true) {
            // comprobar si hay un ganador
            if (this.winner) {
                break;
            }
            // llamamos a nuestro metodo readNumbers()
            readNumbers();
            // obtenemos si hay un ganador
            int winner = this.isWin(this.tablero);
            // comprobamos diferentes posibilidades para seguir o detener el juego
            if ((winner != 1 && winner != 2) && this.change && !this.isFull()) {
                miniMax(matris);
            } else if (winner == 1 || winner == 2) {
                this.winner = true;
            } else if (this.isFull()) {
                System.out.println("Fin del juego No hay Ganador");
                break;
            }
            // imprimimos el estado de la tabla
            printBoard(this.tablero);
        }
    }

    // METODOS
    
    /*
    Este metodo nos sirve para que el jugador realize su movimiento.
    para que realize su movimiento se tienen que hacer varias comprobaciones.
    */
    private void readNumbers() {
        if (isFull()) {
            this.winner = true;
            return;
        }
        this.change = false;
        Scanner input = new Scanner(System.in);
        int i = 0, j = 0;
        boolean var = true;
        if (this.first) {
            var = true;
            
        }else if (!this.first) {
            var = false;
            this.first = true;
        }
        
         while (var) {
            System.out.println("Introduce las cordenadas a jugar:");
            System.out.print("X: ");
            j = input.nextInt();
            System.out.print("Y: ");
            i = input.nextInt();

            if (i < 3 && j < 3) {
                if (this.tablero[j][i] == 0) {
                    this.tablero[j][i] = 1;
                    var = false;
                }
            }
        };
        this.change = true;
    }

    /*
    Imprimimos la tabla de nuestro juego, tambien imprime si hay un ganador
    en caso de que asi sea.
    */
    private void printBoard(int[][] matriz) {
        int ganador = this.isWin(matriz);

        if (ganador == 1) {
            System.out.println("Ha ganado el Jugador");
            this.theWinner = 1;
            this.winner = true;
        } else if (ganador == 2) {
            System.out.println("Ha ganado la maquina");
            this.theWinner = 2;
            this.winner = true;
        }

        for (int i = 0; i < this.endBoard; i++) {
            for (int j = 0; j < this.endBoard; j++) {
                System.out.print("" + matriz[j][i] + "\t");
            }
            System.out.println("");
        }
    }
    
    /*
    Este metodo comprueba si hay una wictoria, para esto comprueba todas las posibles
    jugadas donde se ganan.
    */
    private int isWin(int matriz[][]) {
        for (int i = 0; i < this.endBoard; i++) {
            for (int j = 0; j < this.endBoard; j++) {
                if (j + 2 < this.endBoard) {
                    if (matriz[j][i] == matriz[j + 1][i] && matriz[j][i] == matriz[j + 2][i]) {
                        return matriz[j][i];
                    }
                }
                if (i + 2 < this.endBoard) {
                    if (matriz[j][i] == matriz[j][i + 1] && matriz[j][i] == matriz[j][i + 2]) {
                        return matriz[j][i];
                    }
                }
                if (j + 2 < this.endBoard && i + 2 < this.endBoard) {
                    if (matriz[j][i] == matriz[j + 1][i + 1] && matriz[j][i] == matriz[j + 2][i + 2]) {
                        return matriz[j][i];
                    }
                }
                if (i - 3 > -1 && j + 3 < this.endBoard) {
                    if (matriz[j][i] == matriz[j + 1][i - 1] && matriz[j][i] == matriz[j + 2][i - 2]) {
                        return matriz[j][i];
                    }
                }
                if (j - 2 > -1 && i + 2 < this.endBoard) {
                    if (matriz[j][i] == matriz[j - 1][i + 1] && matriz[j][i] == matriz[j - 2][i + 2]) {
                        return matriz[j][i];
                    }
                }
            }
        }
        return 0;
    }

    /*
    Este metodo comprueba si la tabla esta totalmente llena
    */
    private boolean isFull() {
        for (int i = 0; i < this.endBoard; i++) {
            for (int j = 0; j < this.endBoard; j++) {
                if (this.tablero[j][i] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
    
    Todos los emtodos para el funcionamiento de la inteligencia del gato usando
    el algoritmo minimax.
     */
    private void miniMax(int[][] matriz) {
        int mejorFila = -1, mejorColumna = -1;
        int max, maxActual;
        max = Integer.MIN_VALUE;
        for (int i = 0; i < this.endBoard; i++) {
            for (int j = 0; j < this.endBoard; j++) {
                if (matriz[j][i] == 0) {
                    int tempFila = i, tempColumna = j;
                    matriz[j][i] = 2;
                    maxActual = this.valorMin(matriz, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    matriz[tempColumna][tempFila] = 0;
                    if (max < maxActual) {
                        max = maxActual;
                        mejorFila = tempFila;
                        mejorColumna = tempColumna;
                    }
                }
            }
        }
        matriz[mejorColumna][mejorFila] = 2;
        this.change = false;
    }

    private int valorMin(int[][] matriz, int depth, int alfa, int beta) {
        if (this.isWin(matriz) == 1 || this.isWin(matriz) == 2) {
            return this.heuristica(matriz);
        } else if (this.isFull()) {
            return this.heuristica(matriz);
        } else if (this.deepth < depth) {
            return this.heuristica(matriz);
        }
        for (int i = 0; i < this.endBoard; i++) {
            for (int j = 0; j < this.endBoard; j++) {
                if (matriz[j][i] == 0) {
                    int tempFila = i, tempColumna = j;
                    matriz[j][i] = 1;
                    beta = Integer.min(beta, this.valorMax(matriz, depth + 1, alfa, beta));
                    matriz[tempColumna][tempFila] = 0;
                    if (alfa >= beta) {
                        return alfa;
                    }
                }
            }
        }
        return beta;
    }

    private int valorMax(int[][] matriz, int depth, int alfa, int beta) {
        if (this.isWin(matriz) == 1 || this.isWin(matriz) == 2) {
            return this.heuristica(matriz);
        } else if (this.isFull()) {
            return this.heuristica(matriz);
        }
        for (int i = 0; i < this.endBoard; i++) {
            for (int j = 0; j < this.endBoard; j++) {
                if (matriz[j][i] == 0) {
                    int tempFila = i, tempColumna = j;
                    matriz[j][i] = 2;
                    alfa = Integer.max(alfa, this.valorMin(matriz, depth + 1, alfa, beta));
                    matriz[tempColumna][tempFila] = 0;
                    if (alfa >= beta) {
                        return beta;
                    }
                }
            }
        }
        return alfa;
    }

    private int heuristica(int m[][]) {
        int costo;
        costo = this.costo(m, 2) - this.costo(m, 1);
        return costo;
    }

    private int costo(int m[][], int jugador) {
        int value = 0;
        for (int i = 0; i < this.endBoard; i++) {
            for (int j = 0; j < this.endBoard; j++) {
                if (j + 2 < this.endBoard) {
                    if (m[j][i] == jugador && m[j + 1][i] == jugador && m[j + 2][i] == jugador) {
                        return 500;
                    }
                }
                if (i + 2 < this.endBoard) {
                    if (m[j][i] == jugador && m[j][i + 1] == jugador && m[j][i + 2] == jugador) {
                        return 500;
                    }
                }
                if (j + 2 < this.endBoard && i + 2 < this.endBoard) {
                    if (m[j][i] == jugador && m[j + 1][i + 1] == jugador && m[j + 2][i + 2] == jugador) {
                        return 500;
                    }
                }
                // vertical
                if (i - 2 > -1 && j + 2 < this.endBoard) {
                    if (m[j][i] == jugador && m[j + 1][i - 1] == jugador && m[j + 2][i - 2] == jugador) {
                        return 500;
                    }
                }
                if (j - 2 > -1 && i + 2 < this.endBoard) {
                    if (m[j][i] == jugador && m[j - 1][i + 1] == jugador && m[j - 2][i + 2] == jugador) {
                        return 500;
                    }
                }
                if (j + 1 < this.endBoard) {
                    if (m[j][i] == jugador && m[j + 1][i] == jugador) {
                        value = 300;
                    }
                }
                if (i + 1 < this.endBoard) {
                    if (m[j][i] == jugador && m[j][i + 1] == jugador) {
                        value = 300;
                    }
                }
                if (i + 1 < this.endBoard && j + 1 < this.endBoard) {
                    if (m[j][i] == jugador && m[j + 1][i + 1] == jugador) {
                        value = 300;
                    }
                }
                if (i - 1 > -1 && j + 1 < this.endBoard) {
                    if (m[j][i] == jugador && m[j + 1][i - 1] == jugador) {
                        value = 300;
                    }
                }
                if (i + 1 > this.endBoard && j - 1 > -1) {
                    if (m[j][i] == jugador && m[j - 1][i + 1] == jugador) {
                        value = 300;
                    }
                }
            }
        }
        return value;
    }

}
