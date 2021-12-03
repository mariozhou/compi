/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Principal {

    public static void main(String[] args) throws Exception {
        
        File path=new File ("src/Main/LexerCup.flex");
        String lexer=path.getAbsolutePath()+"";
        String cup="src/Main/Sintax.cup";
        generarLexer(lexer);
        generarCup(cup);
        
        
        //C:\Users/TheTitan/Desktop/AUTO2/Compilador-Base-master/src\Main\Sintax.cup
    }
 public static void generarLexer(String lexer) throws Exception{    
        File file=new File(lexer);
        jflex.Main.generate(file);
        
    }
     public static void generarCup(String cup) throws IOException, Exception{        
        String[] archivoCup={"-parser","Sintax",cup};
        java_cup.Main.main(archivoCup);
        boolean RedirigidoCup = redirigir("Sintax.java");
        boolean RedirigidoSym= redirigir("sym.java");
        if(RedirigidoCup && RedirigidoSym){
            System.exit(0);
        }else System.err.println("Falló generar Cup");
     }
     
     public static boolean redirigir(String file) {
        boolean exito = false;
        File archivo = new File(file);
        if (archivo.exists()) {
            Path currentRelativePath = Paths.get("");
            String miLocacion = currentRelativePath.toAbsolutePath().toString()
                    + File.separator + "src" + File.separator
                    + "Main" + File.separator + archivo.getName();
            File archivoAnterior = new File(miLocacion);
            archivoAnterior.delete();
            
            if (archivo.renameTo(new File(miLocacion))) {
                exito = true;
            }else System.err.println("Fallo renombrar");
        }else System.err.println("Fallo redirigir");
        return exito;
    }
}