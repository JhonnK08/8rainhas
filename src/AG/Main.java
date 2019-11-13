/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AG;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
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
    public static void main(String[] args) throws FileNotFoundException, IOException {
        final Tabuleiro tabuleiro = new Tabuleiro();
        final Rainha[][] rainhas = new Rainha[8][8];
        final int tamanhopopulacao = 8; // Número de indivíduos
        final int numerogeracoes = 1;
        final ArrayList<Rainha> arrayRainhas = new ArrayList(tamanhopopulacao); //ArrayList de Rainhas
        tabuleiro.setTabuleiro(rainhas); // Criação da Matriz Tabuleiro
        
        geraPopulacao(tamanhopopulacao, arrayRainhas);
        
        populaTabuleiro(arrayRainhas, tabuleiro);

        verificaPosicoes(arrayRainhas, tabuleiro, tamanhopopulacao);
        
        calculaFitness(arrayRainhas);

        selecionaRanking(arrayRainhas);
        
    }

    public static void geraPopulacao(int populacao, ArrayList<Rainha> rainhas) {
        Random aleatorio = new Random();
        for (int i = 0; i < populacao; i++) {
            Rainha rainha = new Rainha();
            rainha.setId(i);
            rainha.setX(aleatorio.nextInt(8));
            rainha.setY(aleatorio.nextInt(8));
            rainha.setOcupado(true);
            rainhas.add(i, rainha);
            System.out.println("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ")");
        }
    }
    
    public static void populaTabuleiro(ArrayList<Rainha> rainhas, Tabuleiro tabuleiro) {
        Rainha tabu2[][] = new Rainha[8][8]; //Tabuleiro de Rainhas
        Rainha rainhaNula = new Rainha();
        rainhaNula.setOcupado(false);
        rainhaNula.setId(999);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabu2[i][j] = rainhaNula; 
            }
        }
        for (int i = 0; i < rainhas.size(); i++) {
            tabu2[rainhas.get(i).getX()][rainhas.get(i).getY()] = (Rainha) rainhas.get(i); //Preenche a posição da Rainha no tabuleiro
        }
        tabuleiro.setTabuleiro(tabu2);
        System.out.println("Tabuleiro populado!");
    }

    public static void verificaPosicoes(ArrayList<Rainha> rainhas, Tabuleiro tabuleiro, int populacao) {
        Rainha[][] tabu = new Rainha[8][8];
        tabu = tabuleiro.getTabuleiro();
            for (int i = 0; i < populacao; i++) { //Loop de cada rainha
            Rainha rainha = rainhas.get(i);
            int aux = 0; //Auxiliar dos encontros

            //Verifica o eixo X da rainha i
            for (int j = 0; j < 8 ; j++) {
                if (tabu[j][rainhas.get(i).getY()].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha

                } else {
                    if (tabu[j][rainhas.get(i).getY()].isOcupado()) {
                        rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                    }
                }
            }
            System.out.println("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ") possui " + rainhas.get(i).getEncontros() + " conflitos no eixo X");

            //Verifica o eixo Y da rainha i
            for (int j = 0; j < 8; j++) {
                if (tabu[rainhas.get(i).getX()][j].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha

                } else {
                    if (tabu[rainhas.get(i).getX()][j].isOcupado()) {
                        rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                    }
                }
            }
            System.out.println("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ") possui " + rainhas.get(i).getEncontros() + " conflitos  no eixo Y.");

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
                                rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
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
                                rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        k--;
                    }
                } else { //O x continua sendo zero
                    k = y;
                    for (int j = x; j < 8; j++) {
                        if (k < 8) {
                        if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[j][k].isOcupado()) {
                                rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        k++;
                        }
                    }
                    k = y;
                    for (int j = x; k >= 0; j++) {
                        if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[j][k].isOcupado()) {
                                rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
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
                                rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        j--;
                    }
                } else {
                    k = x;
                    for (int j = y; j < 8; j++) {
                        if (k <8) {
                        if (tabu[k][j].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[k][j].isOcupado()) {
                                rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        k++;
                        }
                    }
                    k = x;
                    for (int j = y; k >= 0; j++) {
                        if (tabu[k][j].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                            //Não faz nada
                        } else {
                            if (tabu[k][j].isOcupado()) {
                                rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
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
                            rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
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
                                rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                            }
                        }
                        k--;
                    }
                }
                System.out.println("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ") possui " + rainhas.get(i).getEncontros() + " conflitos  na diagonal inferior direita.");
                k = y;
                for (int j = x; k < 8; k++) { //Diagonal superior esquerda
                    if (j >= 0) {
                    if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                        //Não faz nada
                    } else {
                        if (tabu[j][k].isOcupado()) {
                            rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                        }
                    }
                    j--;
                    }
                }
                System.out.println("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ") possui " + rainhas.get(i).getEncontros() + " conflitos  na diagonal superior esquerda.");
                k = y;
                for (int j = x; j < 8; j++) {  //Diagonal superior direita
                    if(k < 8) {
                    if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                        //Não faz nada
                    } else {
                        if (tabu[j][k].isOcupado()) {
                            rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                        }
                    }
                    k++;
                    }
                }
                System.out.println("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ") possui " + rainhas.get(i).getEncontros() + " conflitos  na diagonal superior direita.");
                k = y;
                for (int j = x; j >= 0; j--) { //Diagonal inferior esquerda
                    if (k >= 0) {
                    if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                        //Não faz nada
                    } else {
                        if (tabu[j][k].isOcupado()) {
                            rainhas.get(i).setEncontros(aux += 1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                        }
                    }
                    k--;
                    }
                }
                System.out.println("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ") possui " + rainhas.get(i).getEncontros() + " conflitos  na diagonal inferior esquerda.");

            }
            
         System.out.println("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ") possui " + rainhas.get(i).getEncontros() + " conflitos.");   
        }
        
        tabuleiro.setTabuleiro(tabu);
    }
        
    
    //realizar fitness
    public static void calculaFitness(ArrayList<Rainha> Rainhas) {
        for (int i = 0; i < Rainhas.size(); i++) {
            Rainhas.get(i).setFitness((double)Rainhas.get(i).getEncontros()/Rainhas.size());
            System.out.println("Rainha " + Rainhas.get(i).getId() + " - Encontros: " + Rainhas.get(i).getEncontros() + " Fitness: " + Rainhas.get(i).getFitness());
        }    
    }

    //realiza a seleção por Ranking
    public static void selecionaRanking(ArrayList<Rainha> Rainhas) {
        Rainha melhorRainha = new Rainha();
        melhorRainha = Rainhas.get(0);
        for (int i = 0; i < 10; i++) {
            if (melhorRainha.getFitness() > Rainhas.get(i).getFitness()) {
                melhorRainha = Rainhas.get(i);
            }
        }
    }
    
    public static void selecionaRoleta(ArrayList<Rainha> Rainhas) {
        int somaEncontros = 0;
        for (int i = 0; i < Rainhas.size(); i++) {
            somaEncontros += Rainhas.get(i).getEncontros();
        }
        
    }
    
    
    
    
    public static void criaArquivo() {
            try {

            String content = "This is the content to write into file";

            File file = new File("arquivo.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

}
