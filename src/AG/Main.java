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
            System.out.println("Rainha " +  arrayRainhas.get(i).getId() + " - Coordenadas: (" + arrayRainhas.get(i).getX() + "," + arrayRainhas.get(i).getY() + ")");
        }
        

        
        
    }
    
            
        public void populaTabuleiro(ArrayList<Rainha> rainhas, Tabuleiro tabuleiro) {
            Rainha tabu2[][] = null; //Tabuleiro de Rainhas
            for (int i = 0; i < rainhas.size(); i++) {
                tabu2[rainhas.get(i).getX()][rainhas.get(i).getY()] = (Rainha) rainhas.get(i); //Preenche a posição da Rainha no tabuleiro
            }
            tabuleiro.setTabuleiro(tabu2);
        }
        
        public void verificaPosicoes(Tabuleiro tabuleiro, ArrayList<Rainha> rainhas) {
            Rainha[][] tabu = tabuleiro.getTabuleiro();
            for (int i = 0; i < rainhas.size(); i++) { //Loop de cada rainha
                
                int aux = 0; //Auxiliar dos encontros
                
                //Verifica o eixo X da rainha i
                for (int j = 0; j < 8; j++) {
                    if (tabu[j][rainhas.get(i).getY()].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                    
                    }else{
                        if(tabu[j][rainhas.get(i).getY()].isOcupado()) {
                            rainhas.get(i).setEncontros(aux+1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                        } 
                    }
                }
                
                //Verifica o eixo Y da rainha i
                for (int j = 0; j < 8; j++) {
                    if (tabu[rainhas.get(i).getX()][j].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                    
                    }else{
                        if(tabu[rainhas.get(i).getY()][j].isOcupado()) {
                            rainhas.get(i).setEncontros(aux+1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                        } 
                    }
                }
                
                //Verifica diagonais
                int x = rainhas.get(i).getX();
                int y = rainhas.get(i).getY();
                int k = y; // auxiliar de Y
                if(x == 0){
                    if(y == 0) {
                        for (int j = x; j < 8; j++) {
                                if (tabu[j][j].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                                    //Não faz nada
                                }else{
                                    if(tabu[j][j].isOcupado()) {
                                       rainhas.get(i).setEncontros(aux+1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                                    } 
                                }
                        }
                    }else { //O x continua sendo zero
                        for (int j = x; j < 8; j++) {
                            k = y;
                                if (tabu[j][k].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                                    //Não faz nada
                                }else{
                                    if(tabu[j][k].isOcupado()) {
                                       rainhas.get(i).setEncontros(aux+1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                                    } 
                                }
                                k++;
                        }
                        k = y;
                        for (int j = x; k >=0; j++) {
                                if (tabu[x][y].getId() == rainhas.get(i).getId()) { //Verifica se a posição é a mesma da Rainha
                                    //Não faz nada
                                }else{
                                    if(tabu[x][y].isOcupado()) {
                                       rainhas.get(i).setEncontros(aux+1); //Se foi encontrado uma Rainha na mesma posição, adiciona 1 aos encontros
                                    } 
                                }
                            k--;
                        }
                    
                    }
                }                
            
            }
            
            
            }
        
}
    
