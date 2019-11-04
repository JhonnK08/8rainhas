/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AG;

/**
 *
 * @author Jhonatan
 */
public class Rainha {
    private int id;
    private int x;
    private int y;
    private int encontros;
    private boolean ocupado = false;
    

    public Rainha() {
    }

    public Rainha(int x, int y, int encontros) {
        this.x = x;
        this.y = y;
        this.encontros = encontros;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getEncontros() {
        return encontros;
    }

    public void setEncontros(int encontros) {
        this.encontros = encontros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
    
    
    
    
    
    
    
    
}
