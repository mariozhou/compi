/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;

/**
 *
 * @author DEIMOS
 */
public class AnalizadorLexico {
    public static void main(String[] args) {
        String ruta = "C:/Users/arcin/Documents/TEC/LYA2/Projectos_Java/Compilador/src/Main/Lexer.flex";   
        generarLexer(ruta);
    }
    public static void generarLexer(String ruta){
        File archivo = new File(ruta);
        JFlex.Main.generate(archivo);
    }
}
