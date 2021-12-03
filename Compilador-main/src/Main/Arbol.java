/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.Stack;
import java.util.StringTokenizer;
import javax.swing.JPanel;

/**
 *
 * @author Negrillo
 */
public class Arbol {
    Stack <Nodo> Anodo=new Stack<Nodo>();
    Stack <String> Caracter=new Stack<String>();
    final String espacios ="\t";
    final String aritmeticos = "=!+-*/%^()><|@";
    private Nodo raiz;
    
    private void guardar(){
        Nodo derecho=(Nodo)Anodo.pop();
     Nodo izquierdo=(Nodo)Anodo.pop();
     
     Anodo.push(new Nodo(derecho,izquierdo,Caracter.pop()));
     
    } 
    
    public  Nodo Crear(String expresion) {
       	StringTokenizer tokenizer;
	String token;
	//Nodo1 raiz = null;
        String cadToken="";
      
	tokenizer = new StringTokenizer(expresion,aritmeticos,true);
	while (tokenizer.hasMoreTokens()) {
	    token = tokenizer.nextToken();
            //Imprime datos de la pila
           
           cadToken=cadToken+"\n"+token;
          //  System.out.println(token);
	    if (espacios.indexOf(token) >= 0) 
		;               // Es un espacio en blanco, se ignora
	    else if (aritmeticos.indexOf(token) < 0) {
                System.out.println(token);
		                // Es operando y lo guarda como nodo del arbol
                                Nodo a;
		Anodo.push( new Nodo(token));
                System.out.println(new Nodo(token));
	    } else if(token.equals(")")) { // Saca elementos hasta encontrar (
		while (!Caracter.empty() && !Caracter.peek().equals("(")) {
		    guardar();
		}
		Caracter.pop(); 
            //    System.out.println(pOperadores.lastElement());
                    // Saca el parentesis izquierdo
	    } else {
		if (!token.equals("(") && !Caracter.empty()) {
		           //operador diferente de cualquier parentesis
		    String op = (String) Caracter.peek();
		    while (!op.equals("(") && !Caracter.empty()
			   && aritmeticos.indexOf(op) >= aritmeticos.indexOf(token)) {
			guardar();
			if (!Caracter.empty()) 
			    op = (String)Caracter.peek();
		    }
		}
		Caracter.push(token);  //Guarda el operador
	    }
            
	}
             
	//Sacar todo lo que queda
	raiz = (Nodo)Anodo.peek();
	while (!Caracter.empty()) {
	    if (Caracter.peek().equals("(")) {
		Caracter.pop();
	    } else {
	    guardar();
	    raiz = (Nodo) Anodo.peek() ;
	    }
	}
	return raiz;
    }
    public boolean Construir (String con){
        Crear(con);
        return true;
    }
    public JPanel getdibujo() {
       // return new ArbolExpresionGrafico(this);
       return new ArbolExpresionGrafico(this);
       
    }

    public Stack<Nodo> getAnodo() {
        return Anodo;
    }

    public void setAnodo(Stack<Nodo> Anodo) {
        this.Anodo = Anodo;
    }

    public Stack<String> getCaracter() {
        return Caracter;
    }

    public void setCaracter(Stack<String> Caracter) {
        this.Caracter = Caracter;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    public String getEspacios() {
        return espacios;
    }

    public String getAritmeticos() {
        return aritmeticos;
    }
    
    
}
