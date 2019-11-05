/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AG;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jhonatan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setTabuleiro(new Rainha[8][8]); // Criação da Matriz Tabuleiro
        ArrayList<Rainha> arrayRainhas = new ArrayList(8);
        Random aleatorio = new Random();

        for (int i = 0; i < 8; i++) {
            Rainha rainha = new Rainha();
            rainha.setId(i);
            rainha.setX(aleatorio.nextInt(8));
            rainha.setY(aleatorio.nextInt(8));
            rainha.setOcupado(true);
            arrayRainhas.add(i, rainha);
            System.out.println("Rainha " + arrayRainhas.get(i).getId() + " - Coordenadas: (" + arrayRainhas.get(i).getX() + "," + arrayRainhas.get(i).getY() + ")");
        }
        
        populaTabuleiro(arrayRainhas, tabuleiro);
        
        verificaPosicoes(tabuleiro, arrayRainhas);
        

    }

    public static void populaTabuleiro(ArrayList<Rainha> rainhas, Tabuleiro tabuleiro) {
        Rainha tabu2[][] = new Rainha[8][8]; //Tabuleiro de Rainhas
        for (int i = 0; i < rainhas.size(); i++) {
            tabu2[rainhas.get(i).getX()][rainhas.get(i).getY()] = (Rainha) rainhas.get(i); //Preenche a posição da Rainha no tabuleiro
        }
        tabuleiro.setTabuleiro(tabu2);
    }

    public static void verificaPosicoes(Tabuleiro tabuleiro, ArrayList<Rainha> rainhas) {
        Rainha[][] tabu = tabuleiro.getTabuleiro();
        for (int i = 0; i < rainhas.size(); i++) { //Loop de cada rainha

            int aux = 0; //Auxiliar dos encontros

            //Verifica o eixo X da rainha i
            for (int j = 0; j < 8; j++) {
                if (tabu[j][rainhas.get(i).getY()].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha

                } else {
                    if (tabu[j][rainhas.get(i).getY()].isOcupado()) {
                        rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                    }
                }
            }

            //Verifica o eixo Y da rainha i
            for (int j = 0; j < 8; j++) {
                if (tabu[rainhas.get(i).getX()][j].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha

                } else {
                    if (tabu[rainhas.get(i).getY()][j].isOcupado()) {
                        rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                    }
                }
            }

            //Verifica diagonais
            int x = rainhas.get(i).getX();
            int y = rainhas.get(i).getY();
            int k = y; // auxiliar de Y
            if (x == 0) {
                if (y == 0) {
                    for (int j = x; j < 8; j++) {
                        if (tabu[j][j].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[j][j].isOcupado()) {
                                rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                    }
                } else if (y == 7) {
                    k = y;
                    for (int j = x; j < 8; j++) {
                        if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[j][k].isOcupado()) {
                                rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        k--;
                    }
                } else { //O x continua sendo zero
                    k = y;
                    for (int j = x; j < 8; j++) {
                        if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[j][k].isOcupado()) {
                                rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        k++;
                    }
                    k = y;
                    for (int j = x; k >= 0; j++) {
                        if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[j][k].isOcupado()) {
                                rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        k--;
                    }
                }
            } else if (y == 0) {
                if (x == 7) {
                    k = y;
                    for (int j = x; k < 8; k++) {
                        if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[j][k].isOcupado()) {
                                rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        j--;
                    }
                } else {
                    k = x;
                    for (int j = y; j < 8; j++) {
                        if (tabu[k][j].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[k][j].isOcupado()) {
                                rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        k++;
                    }
                    k = x;
                    for (int j = y; k >= 0; j++) {
                        if (tabu[k][j].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[k][j].isOcupado()) {
                                rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        k--;
                    }
                }

            } else if (x == 7 & y == 7) {
                for (int j = x; j >= 0; j--) {
                    if (tabu[j][j].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                        //Não faz nada
                    } else {
                        if (tabu[j][j].isOcupado()) {
                            rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                        }
                    }
                }
            } else {
                k = y;
                for (int j = x; j < 8; j++) { //Diagonal inferior direita
                    if (k >= 0) {
                        if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[j][k].isOcupado()) {
                                rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        k--;
                    }
                }

                for (int j = x; k < 8; k++) { //Diagonal superior esquerda
                    k = y;
                    if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                        //Não faz nada
                    } else {
                        if (tabu[j][k].isOcupado()) {
                            rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                        }
                    }
                    j--;
                }

                k = y;
                for (int j = x; j < 8; j++) {
                    if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                        //Não faz nada
                    } else {
                        if (tabu[j][k].isOcupado()) {
                            rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                        }
                    }
                    k++;
                }

                k = y;
                for (int j = x; j >= 0; j++) {
                    if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                        //Não faz nada
                    } else {
                        if (tabu[j][k].isOcupado()) {
                            rainhas.get(i).setEncontros(aux + 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                        }
                    }
                    k--;
                }

            }
            System.out.println("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ") possui " + rainhas.get(i).getEncontros() + " conflitos.");
            tabuleiro.setTabuleiro(tabu);
        }

    }
}
