/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author Negrillo
 */
public class Nodo {
    private String dato;
    private Nodo Padre;
    private Nodo nodoIzq;
    private Nodo nodoDer;
    
    public Nodo(String info){this.dato=info;}
    
    public Nodo(Nodo derecho, Nodo izquierdo,String info){
        this.nodoIzq=izquierdo;
        this.nodoDer=derecho;
        this.dato=info;
        this.Padre=null;
    }

    public String getDato() {
        return dato;
    }

    public Nodo getPadre() {
        return Padre;
    }

    public Nodo getNodoIzq() {
        return nodoIzq;
    }

    public Nodo getNodoDer() {
        return nodoDer;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public void setPadre(Nodo Padre) {
        this.Padre = Padre;
    }

    public void setNodoIzq(Nodo nodoIzq) {
        this.nodoIzq = nodoIzq;
    }

    public void setNodoDer(Nodo nodoDer) {
        this.nodoDer = nodoDer;
    }
    
}
