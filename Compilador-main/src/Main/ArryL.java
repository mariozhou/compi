/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author TheTitan
 */
class ArryL {
       private String id;
    private String tipo;
    private String val;
    private String linea;
 
    public ArryL(String id, String tipo, String val,String linea){
        this.id = id;
        this.tipo  = tipo;   
        this.val =val;
        this.linea =linea;
    }
 
    public String getId() {
        return id;
    }
 
    public String getTipo() {
        return tipo;
    }
 
    public void setTipo(String d) {
        this.tipo = d;        
    }
    public String getVal() {
        return val;
    }
    
     public String getLinea() {
        return linea;
    }
     
     public void setLinea(String d) {
        this.linea = d;        
    }   
    
    public void setVal(String d) {
        this.val = d;        
    }   
}
