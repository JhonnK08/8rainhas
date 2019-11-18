/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AG;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 *
 * @author Jhonatan
 */
public class Main {
    static File arquivo;
    static FileOutputStream fos;
    static PrintStream ps;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Tabuleiro tabuleiro = new Tabuleiro();
        Rainha[][] rainhas = new Rainha[8][8];
        int tamanhopopulacao = 8; // Número de indivíduos
        int numerogeracoes = 1;
        float taxaCruzamento = (float) 0.5;
        ArrayList<Rainha> arrayRainhas = new ArrayList(tamanhopopulacao); //ArrayList de Rainhas
        Rainha melhorRainha = new Rainha();
        tabuleiro.setTabuleiro(rainhas); // Criação da Matriz Tabuleiro
        

// Criação de arquivo de log
        
        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataFormat = formatador.format(data);
        dataFormat = dataFormat.replace(" ", "");
        dataFormat = dataFormat.replace("-", "");
        dataFormat = dataFormat.replace(":", "");
        System.out.println(dataFormat);
        File arquivo;
        
        try {
            arquivo = new File("log-" + dataFormat + ".txt");
            //arquivo = new File("log.txt");              
            //System.setOut(new PrintStream(arquivo));
            arquivo.createNewFile();
            fos = new FileOutputStream(arquivo);
            ps = new PrintStream(fos);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }    
        
        
        
        
        
        geraPopulacao(tamanhopopulacao, arrayRainhas);
        
        populaTabuleiro(arrayRainhas, tabuleiro);

        verificaPosicoes(arrayRainhas, tabuleiro, tamanhopopulacao);
        
        calculaFitness(arrayRainhas);
  
        //selecionaRanking(arrayRainhas);
        
        //selecionaRoleta(arrayRainhas);
        
        //crossoverUmPonto(arrayRainhas, taxaCruzamento);
        
    }

    public static void geraPopulacao(int populacao, ArrayList<Rainha> rainhas) throws IOException {
        Random aleatorio = new Random(); //Utilizado para obter coordenadas aleatórias
        System.out.println("Gerando População...\n");
        ps.printf("Gerando População...\n");
        

        for (int i = 0; i < populacao; i++) {
            Rainha rainha = new Rainha();
            rainha.setId(i);
            rainha.setX(aleatorio.nextInt(8));
            rainha.setY(aleatorio.nextInt(8));
            rainha.setOcupado(true);
            rainhas.add(i, rainha);
            System.out.println("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ")");
            ps.printf("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ")\n");
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
            if(tabu2[rainhas.get(i).getX()][rainhas.get(i).getY()].isOcupado()) {
            
            }
            tabu2[rainhas.get(i).getX()][rainhas.get(i).getY()] = (Rainha) rainhas.get(i); //Preenche a posição da Rainha no tabuleiro
        }
        tabuleiro.setTabuleiro(tabu2);
        System.out.println("Tabuleiro populado!\n");
        ps.printf("Tabuleiro populado!\n");
    }

    public static void verificaPosicoes(ArrayList<Rainha> rainhas, Tabuleiro tabuleiro, int populacao) {
        Rainha[][] tabu = new Rainha[8][8];
        tabu = tabuleiro.getTabuleiro();
        System.out.println("Verificando posições do tabuleiro...");
        ps.printf("Verificando posições do tabuleiro...\n");
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
            ps.printf("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ") possui " + rainhas.get(i).getEncontros() + " conflitos no eixo X\n");
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
            ps.printf("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ") possui " + rainhas.get(i).getEncontros() + " conflitos  no eixo Y.\n");
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
         ps.printf("Rainha " + rainhas.get(i).getId() + " - Coordenadas: (" + rainhas.get(i).getX() + "," + rainhas.get(i).getY() + ") possui " + rainhas.get(i).getEncontros() + " conflitos.\n");
        }
        
        tabuleiro.setTabuleiro(tabu);
    }
        
    //realizar fitness
    public static void calculaFitness(ArrayList<Rainha> Rainhas) {
        System.out.println("\nGerando valor de Fitness...");
        ps.printf("\nGerando valor de Fitness...\n");
        for (int i = 0; i < Rainhas.size(); i++) {
            Rainhas.get(i).setFitness((double)1 - ((double)Rainhas.get(i).getEncontros()/Rainhas.size()));
            System.out.println("Rainha " + Rainhas.get(i).getId() + " - Encontros: " + Rainhas.get(i).getEncontros() + " Fitness: " + Rainhas.get(i).getFitness());
            ps.printf("Rainha " + Rainhas.get(i).getId() + " - Encontros: " + Rainhas.get(i).getEncontros() + " Fitness: " + Rainhas.get(i).getFitness() + "\n");
        }    
    }

    //realiza a seleção por Ranking
    public static ArrayList<Rainha> selecionaRanking(ArrayList<Rainha> Rainhas) {
        System.out.println("\n\n---------------------------Ranking---------------------------");
        ps.printf("\n\n---------------------------Ranking---------------------------");
        Rainha melhorRainha = new Rainha();
        ArrayList<Rainha> Pais = new ArrayList<>(Rainhas.size()); //Vetor que guarda as Rainhas selecionadas
        melhorRainha = Rainhas.get(0); //Rainha selecionada
        for (int i = 0; i < Rainhas.size(); i++) {
            if (melhorRainha.getFitness() < Rainhas.get(i).getFitness()) {
                melhorRainha = Rainhas.get(i);
            }
        }
        System.out.println("A rainha selecionada é: " + melhorRainha.getId() + " com " + melhorRainha.getEncontros() + " encontros.");
        ps.printf("A rainha selecionada é: " + melhorRainha.getId() + " com " + melhorRainha.getEncontros() + " encontros.\n");
        Pais.add(melhorRainha);
        return Pais;
    }
    
    //realiza a seleção por Roleta
    public static ArrayList<Rainha> selecionaRoleta(ArrayList<Rainha> Rainhas) {
        float somaFitness = 0; // Soma de valores utilizados
        float[] valoresRoleta = new float[Rainhas.size()]; //Vetor de valores em Porcentagem de cada Rainha
        float[] grausRoleta = new float[Rainhas.size()]; //Vetor de graus na Roleta de cada rainha
        ArrayList<Rainha> Pais = new ArrayList<>(Rainhas.size()); //Vetor que guarda as Rainhas selecionadas
        int quantRodadas = 0; //Quantidade de giros da roleta
        for (int i = 0; i < Rainhas.size(); i++) {
            somaFitness += Rainhas.get(i).getFitness(); //Soma os valores
        }
        //valorFitness = ((float)360/somaFitness);
        System.out.println("\n\n---------------------------Roleta---------------------------");
        ps.printf("\n\n---------------------------Roleta---------------------------");
        //System.out.println(valorFitness);
        for (int i = 0; i < Rainhas.size(); i++) { 
            valoresRoleta[i] =  ((float)(Rainhas.get(i).getFitness()/somaFitness)*100);
            //System.out.println("Rainha "+Rainhas.get(i).getId()+" - "+ valoresRoleta[i]);
            if (i != 0) {
                grausRoleta[i] = ((float)(valoresRoleta[i]*360)/100) + grausRoleta[i-1]; // Preenche vetor com os graus de cada rainha
            }else
                grausRoleta[i] = ((float)(valoresRoleta[i]*360)/100);
            
            System.out.println("Rainha "+Rainhas.get(i).getId()+" - "+ "Porcentagem na Roleta: "+ valoresRoleta[i]+" - Graus na Roleta:" +grausRoleta[i]+ " - Encontros: "+ Rainhas.get(i).getEncontros());
            ps.printf("Rainha "+Rainhas.get(i).getId()+" - "+ "Porcentagem na Roleta: "+ valoresRoleta[i]+" - Graus na Roleta:" +grausRoleta[i]+ " - Encontros: "+ Rainhas.get(i).getEncontros());
        }
        System.out.println("\nGirando a Roleta...\n");
        ps.printf("\nGirando a Roleta...\n");
        Random grau = new Random();
        float grauAleatorio = grau.nextInt(360);
        System.out.println("Número gerado: " + grauAleatorio);
        System.out.println("\n");
        ps.printf("Número gerado: " + grauAleatorio + "\n");
        for (int i = 0; i < grausRoleta.length; i++) { 
            if (grauAleatorio > grausRoleta[i]) {
                Pais.add(quantRodadas, Rainhas.get(i+1));
            }
        }
        System.out.println("Rainha selecionada: " + Pais.get(quantRodadas).getId());
        System.out.println("\n");
        ps.printf("Rainha selecionada: " + Pais.get(quantRodadas).getId()+ "\n");
        
        return Pais;
        

        
        
    }
    
    public static ArrayList<Rainha> crossoverUmPonto(ArrayList<Rainha> RainhasSelecionadas, float taxaCruzamento) {
        ArrayList<Rainha> Filhos = new ArrayList<>(RainhasSelecionadas.size());
        
        return Filhos;
    }


}
