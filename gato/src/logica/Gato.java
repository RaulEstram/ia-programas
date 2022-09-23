/*

Codigo por Estrada Zermeño Raúl de Jesús

 */
package logica;

import java.io.IOException;
import java.util.Scanner;

public class Gato {

    /*
    Propiedades necesarias para la clase
     */
    private int tablero[][];
    private int produndidad = 0;
    public int tamaño = 3;
    private boolean cambio;
    private boolean thereIsWinner = false;
    public int theWinner = 0;
    private boolean first = true;
    private int simboloPlayer = 1;

    /*
    Constructor de la clase.
    
    Dentro de la misma clase se empieza a ejecutar toda la logica del 
    juego del gato, para simplemente crear la clase y que empiece el juego
     */
    public Gato(int[][] matris, boolean first, int simbolo) throws IOException {
        this.tablero = matris;
        this.first = first;
        this.simboloPlayer = simbolo;
        this.cambio = true;

        while (true) {
            // comprobar si hay un ganador
            if (this.thereIsWinner) {
                break;
            }
            // llamamos a nuestro metodo readNumbers()
            getCordenadasJugadaDelJugador();
            // obtenemos si hay un ganador
            int winner = this.getWinner(this.tablero);
            // comprobamos diferentes posibilidades para seguir o detener el juego
            if ((winner != 1 && winner != 2) && this.cambio && !this.isFull()) {
                getMiniMax(matris);
            } else if (winner == 1 || winner == 2) {
                this.thereIsWinner = true;
            } else if (this.isFull()) {
                System.out.println("Fin del juego No hay Ganador");
                break;
            }
            // imprimimos el estado de la tabla
            imprimirTablero(this.tablero);
        }
    }

    // METODOS
    /*
    Este metodo nos sirve para que el jugador realize su movimiento.
    para que realize su movimiento se tienen que hacer varias comprobaciones.
     */
    private void getCordenadasJugadaDelJugador() {
        if (isFull()) {
            this.thereIsWinner = true;
            return;
        }
        this.cambio = false;
        Scanner input = new Scanner(System.in);
        int i = 0, j = 0;
        boolean var = true;
        if (this.first) {
            var = true;

        } else if (!this.first) {
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
                    this.tablero[j][i] = simboloPlayer;
                    var = false;
                }
            }
        };
        this.cambio = true;
    }

    /*
    Imprimimos la tabla de nuestro juego, tambien imprime si hay un ganador
    en caso de que asi sea.
     */
    private void imprimirTablero(int[][] matriz) {
        int ganador = this.getWinner(matriz);

        if (ganador == simboloPlayer) {
            System.out.println("Ha ganado el Jugador");
            this.theWinner = 1;
            this.thereIsWinner = true;
        } else if (ganador == (simboloPlayer == 2 ? 1 : 2)) {
            System.out.println("Ha ganado la maquina");
            this.theWinner = 2;
            this.thereIsWinner = true;
        }

        for (int i = 0; i < this.tamaño; i++) {
            for (int j = 0; j < this.tamaño; j++) {
                System.out.print("" + matriz[j][i] + "\t");
            }
            System.out.println("");
        }
    }

    /*
    Este metodo comprueba si hay una wictoria, para esto comprueba todas las posibles
    jugadas donde se ganan.
     */
    private int getWinner(int matriz[][]) {
        for (int i = 0; i < this.tamaño; i++) {
            for (int j = 0; j < this.tamaño; j++) {
                if (j + 2 < this.tamaño) {
                    if (matriz[j][i] == matriz[j + 1][i] && matriz[j][i] == matriz[j + 2][i]) {
                        return matriz[j][i];
                    }
                }
                if (i + 2 < this.tamaño) {
                    if (matriz[j][i] == matriz[j][i + 1] && matriz[j][i] == matriz[j][i + 2]) {
                        return matriz[j][i];
                    }
                }
                if (j + 2 < this.tamaño && i + 2 < this.tamaño) {
                    if (matriz[j][i] == matriz[j + 1][i + 1] && matriz[j][i] == matriz[j + 2][i + 2]) {
                        return matriz[j][i];
                    }
                }
                if (i - 3 > -1 && j + 3 < this.tamaño) {
                    if (matriz[j][i] == matriz[j + 1][i - 1] && matriz[j][i] == matriz[j + 2][i - 2]) {
                        return matriz[j][i];
                    }
                }
                if (j - 2 > -1 && i + 2 < this.tamaño) {
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
        for (int i = 0; i < this.tamaño; i++) {
            for (int j = 0; j < this.tamaño; j++) {
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
    private void getMiniMax(int[][] matriz) {
        int mejorFila = -1, mejorColumna = -1;
        int max, maxActual;
        max = Integer.MIN_VALUE;
        for (int i = 0; i < this.tamaño; i++) {
            for (int j = 0; j < this.tamaño; j++) {
                if (matriz[j][i] == 0) {
                    int tempFila = i, tempColumna = j;
                    matriz[j][i] = simboloPlayer == 2 ? 1 : 2;
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
        matriz[mejorColumna][mejorFila] = simboloPlayer == 2 ? 1 : 2;
        this.cambio = false;
    }

    private int valorMin(int[][] matriz, int depth, int alfa, int beta) {
        if (this.getWinner(matriz) == 1 || this.getWinner(matriz) == 2) {
            return this.controlador(matriz);
        } else if (this.isFull()) {
            return this.controlador(matriz);
        } else if (this.produndidad < depth) {
            return this.controlador(matriz);
        }
        for (int i = 0; i < this.tamaño; i++) {
            for (int j = 0; j < this.tamaño; j++) {
                if (matriz[j][i] == 0) {
                    int tempFila = i, tempColumna = j;
                    matriz[j][i] = simboloPlayer;
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
        if (this.getWinner(matriz) == 1 || this.getWinner(matriz) == 2) {
            return this.controlador(matriz);
        } else if (this.isFull()) {
            return this.controlador(matriz);
        }
        for (int i = 0; i < this.tamaño; i++) {
            for (int j = 0; j < this.tamaño; j++) {
                if (matriz[j][i] == 0) {
                    int tempFila = i, tempColumna = j;
                    matriz[j][i] = simboloPlayer == 2 ? 1 : 2;
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

    private int controlador(int m[][]) {
        int costo;
        costo = this.costo(m, simboloPlayer == 2 ? 1 : 2) - this.costo(m, simboloPlayer);
        return costo;
    }

    private int costo(int m[][], int jugador) {
        int value = 0;
        for (int i = 0; i < this.tamaño; i++) {
            for (int j = 0; j < this.tamaño; j++) {
                if (j + 2 < this.tamaño) {
                    if (m[j][i] == jugador && m[j + 1][i] == jugador && m[j + 2][i] == jugador) {
                        return 500;
                    }
                }
                if (i + 2 < this.tamaño) {
                    if (m[j][i] == jugador && m[j][i + 1] == jugador && m[j][i + 2] == jugador) {
                        return 500;
                    }
                }
                if (j + 2 < this.tamaño && i + 2 < this.tamaño) {
                    if (m[j][i] == jugador && m[j + 1][i + 1] == jugador && m[j + 2][i + 2] == jugador) {
                        return 500;
                    }
                }
                // vertical
                if (i - 2 > -1 && j + 2 < this.tamaño) {
                    if (m[j][i] == jugador && m[j + 1][i - 1] == jugador && m[j + 2][i - 2] == jugador) {
                        return 500;
                    }
                }
                if (j - 2 > -1 && i + 2 < this.tamaño) {
                    if (m[j][i] == jugador && m[j - 1][i + 1] == jugador && m[j - 2][i + 2] == jugador) {
                        return 500;
                    }
                }
                if (j + 1 < this.tamaño) {
                    if (m[j][i] == jugador && m[j + 1][i] == jugador) {
                        return 300;
                    }
                }
                if (i + 1 < this.tamaño) {
                    if (m[j][i] == jugador && m[j][i + 1] == jugador) {
                        return 300;
                    }
                }
                if (i + 1 < this.tamaño && j + 1 < this.tamaño) {
                    if (m[j][i] == jugador && m[j + 1][i + 1] == jugador) {
                        return 300;
                    }
                }
                if (i - 1 > -1 && j + 1 < this.tamaño) {
                    if (m[j][i] == jugador && m[j + 1][i - 1] == jugador) {
                        return 300;
                    }
                }
                if (i + 1 > this.tamaño && j - 1 > -1) {
                    if (m[j][i] == jugador && m[j - 1][i + 1] == jugador) {
                        return 300;
                    }
                }
            }
        }
        return value;
    }

}
