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
        ArrayList<Rainha> arrayRainhas;
        Random aleatorio = new Random();
        
        for (int i = 0; i < 8; i++) {
            Rainha rainha = new Rainha();
            rainha.setId(i);
            rainha.setX(aleatorio.nextInt(8));
            rainha.setY(aleatorio.nextInt(8));
            System.out.println("Rainha " + rainha.getId() + " - Coordenadas: (" + rainha.getX() + "," + rainha.getY() + ")");
        }
        

        
        
    }
    
            
        public void populaTabuleiro(Rainha rainha, Tabuleiro tabuleiro) {
            //Preenche X 
            Rainha tabu2[][] = null;
            rainha.setOcupado(1);
            tabu2[rainha.getX()][rainha.getY()] = rainha;
            for (int i = 0; i < 8; i++) { // Preenchendo eixo y
                tabu2[rainha.getX()][i] = rainha;
            }
            
        
        }
    
}
