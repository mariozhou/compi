/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author DEIMOS
 */

import javax.swing.*; 
import javax.swing.text.*; 
import java.io.*; 
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

//-----------------------------------------------
import javax.swing.text.html.HTMLEditorKit;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.undo.CannotUndoException;

//------------------------------------------------

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;import java_cup.parser;
;

import java_cup.runtime.Symbol;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;

/*--- NOTAS -----

- Mejorar la gramatica y el léxico

-----------------*/

public class Main extends javax.swing.JFrame{

    /**
     * Creates new form Main
     */
    public Main() {
        
    //-------------------------------- Cambiar IU ------------------------------------------------------

    try {    //LookAndFeel nativo
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
        System.err.println(ex);
    }        
    this.getContentPane().setBackground(new Color(240,240,240));

    //--------------------------------- Poner el icono --------------------------------------------------

    seticon();
        
        //--------------------------------- Pintar palabras --------------------------------------------------
        
        final StyleContext cont = StyleContext.getDefaultStyleContext();
        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        final AttributeSet attrBlue = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
        final AttributeSet attrOrange = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.ORANGE);
        
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = txtPane1.getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before; 
                int contador = 0;
                int contador2 = 0;              
                
                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("(\\W)")) {     
                        if((text.substring(wordL, wordR).matches("((\\n)|(\\s))* (False|Retroceder|Import|Detener|Avanzar|Receive|DeviceType|Else|True|Int|Text|Float|Bool|Display|For|While|If|Else|When|Default|PortA|PortB|PortC|PortD|New)"))){
                            setCharacterAttributes(wordL, wordR - wordL,attrBlue, false); 
                            contador++;                       
//                        }else if((text.substring(wordL, wordR).matches("((\\n)|(\\s))*(alex|asin|asem)"))){
//                            setCharacterAttributes(wordL, wordR - wordL,attrBlue, false); 
//                            contador++;
                        }else if (text.substring(wordL, wordR).matches("(\\W)+ (Receive|DeviceType|Else|True|Int|Text|Float|Bool|Display|For|While|If|Else|When|Default|PortA|PortB|PortC|PortD|New)")&&(contador==0)){
                            setCharacterAttributes(wordL+1, wordR - wordL,attrBlue, false);
//                        }else if (text.substring(wordL, wordR).matches("(\\W)+(alex|asin|asem)")&&(contador==0)){
//                            setCharacterAttributes(wordL+1, wordR - wordL,attrBlue, false);
                        }else if(text.substring(wordL, wordR).matches("(\\W)+ (Receive|DeviceType|Else|True|Int|Text|Float|Bool|Display|For|While|If|Else|When|Default|PortA|PortB|PortC|PortD|New)")&&(contador==0)){
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        }else if(contador2==0){
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        } 
                        wordL = wordR;
                    }
                    wordR++;
                    contador=0;
                    contador2=0;                 
                }               
            }
                               
            public void remove (int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("(\\W)+(Receive|DeviceType|Else|True|Int|Text|Float|Bool|Display|For|While|If|Else|When|Default|PortA|PortB|PortC|PortD|New)")) {
                    setCharacterAttributes(before+1, after - before, attrBlue, false);  
//                }else if (text.substring(before, after).matches("(\\W)+(alex|asin|asem)")) {
//                    setCharacterAttributes(before+1, after - before, attrBlue, false);  
                }else if (text.substring(before, after).matches("(\\W)* (Receive|DeviceType|Else|True|Int|Text|Float|Bool|Display|For|While|If|Else|When|Default|PortA|PortB|PortC|PortD|New)")) {
                    setCharacterAttributes(before, after - before, attrBlue, false);  
//                }else if (text.substring(before, after).matches("(\\W)*(alex|asin|asem)")) {
//                    setCharacterAttributes(before, after - before, attrBlue, false);  
//                }else{
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }
                
                if (text.substring(before, after).matches("(\\W)+ (Receive|DeviceType|Else|True|Int|Text|Float|Bool|Display|For|While|If|Else|When|Default|PortA|PortB|PortC|PortD|New)")) {
                    setCharacterAttributes(before+1, after - before, attrBlue, false);
//                }else if (text.substring(before, after).matches("(\\W)+(alex|asin|asem)")) {
//                    setCharacterAttributes(before+1, after - before, attrBlue, false);
                }else if (text.substring(before, after).matches("(\\W)* (Receive|DeviceType|Else|True|Int|Text|Float|Bool|Display|For|While|If|Else|When|Default|PortA|PortB|PortC|PortD|New)")) {
                    setCharacterAttributes(before, after - before, attrBlue, false);
//                }else if (text.substring(before, after).matches("(\\W)*(alex|asin|asem)")) {
//                    setCharacterAttributes(before, after - before, attrBlue, false);
                } else {
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }

                
            }
        };
        
        //---------------------- Inicializar componentes ----------------------------------------------------
        
        initComponents();   
        txtPane1.setEditorKit(new HTMLEditorKit());
        txtPane1.setEditorKit(new TabSizeEditorKit());
//        model = (DefaultTableModel) tablaElementos.getModel();
//        tablaElementos.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 15));
//        tablaElementos.getTableHeader().setResizingAllowed(true);
//        tablaElementos.getColumnModel().getColumn(0).setPreferredWidth(4);

        //---------------------- Pintar palabras reservadas -------------------------------------------------
        
        txtPane1.setStyledDocument(doc);   
         
        //----------------------- Centrar Pantalla ----------------------------------------------------------
        
        this.setLocationRelativeTo(null);
        
        //----------------------- Lineas del textPane y el sombreado gris -----------------------------------
        
        TextLineNumber tln = new TextLineNumber(txtPane1);
        jScrollPane1.setRowHeaderView(tln);       
        //txtPane1.setStyledDocument(doc);
        
        Color c = new Color(233, 239, 248);
        
        LinePainter painter = new LinePainter(txtPane1, c);
        
        //-----------------------------------------------------------------------------------------------
        
        undoManager = new UndoManager();                //construye una instancia de UndoManager
       
        txtPane1.getDocument().addUndoableEditListener(new UndoableEditListener() {
                public void undoableEditHappened(UndoableEditEvent e) {
                        undoManager.addEdit(e.getEdit());
                }
        });
        
        // Tabla dinamica
        
        model1 = (DefaultTableModel) dynamicTable.getModel();
        dynamicTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 15));       
        dynamicTable.getTableHeader().setResizingAllowed(true);

        dynamicTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        dynamicTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        
        // Tabla estática
        
        model2 = (DefaultTableModel) staticTable.getModel();
        staticTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 15));       
        staticTable.getTableHeader().setResizingAllowed(true);

        staticTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        staticTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        
        // Tabla de lexemas
        
        model3 = (DefaultTableModel) lexemeTable.getModel();
        lexemeTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 15));       
        lexemeTable.getTableHeader().setResizingAllowed(true);

        lexemeTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        lexemeTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        
        cambiarTabla(jComboBox1.getSelectedIndex());
        
        jMenu3.setVisible(false);
    }
    //---------------------------------------------------------------------------------------------------------
    
    String [] lineas;
    
    private boolean hasChanged = false;    //el estado del documento actual, no modificado por defecto
    private File currentFile = null;       //el archivo actual, ninguno por defecto
    private final UndoManager undoManager;            //instancia de UndoManager (administrador de edición)
    private String lastSearch = "";
    TablaVar tV = new TablaVar();
    TableFija tF = new TableFija();
  //  InfoErrores e = new InfoErrores();
  //  Contacto c = new Contacto();
    public static boolean error = false;
    public static int countLineas = 0;
    public static int variableSize = 0;
    static ArrayList<ArryL> Arr = new ArrayList<ArryL>();
    public static ArrayList<String> mostrarCod = new ArrayList<String>();  // guardar codigo intermedio para mostrar
    public static ArrayList<String> mostrarExpAr = new ArrayList<String>();  // guardar codigo arduiExp para mostrar
    public static ArrayList<Integer> listaLineas = new ArrayList<Integer>();
    public static ArrayList<String> listaErrores = new ArrayList<String>();
    public static ArrayList<String> listaCodigo = new ArrayList<String>();
    public static ArrayList<String> listaIdValor = new ArrayList<String>();
    public static ArrayList<String> listaIdTipo = new ArrayList<String>();
    public static ArrayList<Integer> listaIdLinea = new ArrayList<Integer>();
    public static ArrayList<String> listavalor1 = new ArrayList<String>();  // guarda tipos
    public static ArrayList<String> listaValoresId = new ArrayList<String>();
    public static ArrayList<String> listaLexemas = new ArrayList<String>();
    public static ArrayList<Integer> listaLineaLexemas = new ArrayList<Integer>();
    public static ArrayList<String> listaCompLexico= new ArrayList<String>();
    public static ArrayList<String> listaIdFuncValor = new ArrayList<String>();
    public static ArrayList<Integer> listaIdFuncLinea = new ArrayList<Integer>();
    public static ArrayList<String> listaValoresIdFunc = new ArrayList<String>();
    public static ArrayList<String> listaarduino = new ArrayList<String>();
    public static String codArInclude2="";
    public static String codArInclude="#include <Wire.h>     // libreria para bus I2C\n" +
"#include <Adafruit_GFX.h>   // libreria para pantallas graficas\n" +
"#include <Adafruit_SSD1306.h>   // libreria para controlador SSD1306\n" +
"#include <Servo.h> \n" +
"\n" +
"#define ANCHO 128     // reemplaza ocurrencia de ANCHO por 128\n" +
"#define ALTO 64       // reemplaza ocurrencia de ALTO por 64\n" +
"\n" +
"#define OLED_RESET 4      // necesario por la libreria pero no usado\n" +
"Adafruit_SSD1306 oled(ANCHO, ALTO, &Wire, OLED_RESET);  // crea objeto\n" +
"\n" +
"//llanta\n" +
"int IN1 = 9;\n" +
"int IN2 = 10;\n" +
"int ENA = 8;\n" +
"int Mic=A0;"+  //sonido
"//-------------------\n" +
"//servo motor\n" +
"int dir=0,menu=1,opc=0;\n" +
"char c='\\0';\n" +
"String words;       \n" +
"Servo servo1; \n" +
"//---------------\n" +
"\n" +
"//PIR\n" +
"int sensor=2;\n" +
"int pBuzzer = 3; // pin del buzzer pasivo \n" +
"int cuarto = 1000 / 4; // tiempo de 1/4 de la nota\n" +
"int octavo = 1000 / 8; // tiempo de 1/8 de la nota\n" +
"double pausa = 1.30; // pausar el 30% de la nota"+
"// La frecuencia min que puede producir el Arduino Uno es 31Hz.\n" +
"// La frecuencia max que puede producir el Arduino Uno es 65,525Hz.\n" +
"int NOTA_C4 = 500; // Frecuencia de la nota C4 es 262Hz\n" +
"int NOTA_A4 = 600; // Frecuencia de la nota A4 es 440Hz\n" +
"int NOTA_E5 = 150; // Frecuencia de la nota E5 es 659Hz";

    public static String codArSetup="void setup() { pinMode(IN1, OUTPUT);\n" +
" pinMode(IN2, OUTPUT);\n" +
" pinMode(ENA,OUTPUT); \n //LSD\n" +
"  Wire.begin();         // inicializa bus I2C\n" +
"  oled.begin(SSD1306_SWITCHCAPVCC, 0x3C); // inicializa pantalla con direccion 0x3C\n" +
"\n" +
"//PIR\n" +
"    pinMode(sensor,INPUT);\n" +
" Serial.begin(115200);"+
"  pinMode(pBuzzer,OUTPUT);\n" +
"  Serial.begin(9600); \n";
    public static String confSetup ="void setup() { pinMode(IN1, OUTPUT);\n" +
" pinMode(IN2, OUTPUT);\n" +
" pinMode(ENA,OUTPUT); \n //LSD\n" +
"  Wire.begin();         // inicializa bus I2C\n" +
"  oled.begin(SSD1306_SWITCHCAPVCC, 0x3C); // inicializa pantalla con direccion 0x3C\n" +
"\n" +
"//PIR\n" +
"    pinMode(sensor,INPUT);\n" +
"  pinMode(pBuzzer,OUTPUT);\n" +
"  Serial.begin(9600); \n ";;
    public static String codarduinoS="";
    public static String codArLoop="void loop() {int value= digitalRead(sensor); \n";
     public static String confLoop="void loop() {int value= digitalRead(sensor); \n";
    public static String codarduinoL="";
    public static String codArExp="";  // para las expresiones

    public static ArrayList<Integer> listaLineasSin = new ArrayList<Integer>();
    public static ArrayList<String> listaErroresSin = new ArrayList<String>();
    public static ArrayList<String> arbolSin = new ArrayList<String>();
    DefaultTableModel model1; 
    DefaultTableModel model2; 
    DefaultTableModel model3; 
    public static ArrayList<Object> ArbolConPilas = new ArrayList<Object>();
    public static ArrayList<String> posiblesArboles = new ArrayList<String>();
    public static String ArbolesCad="";
    public Arbol prueba = new Arbol();
    
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        popmenuUndo = new javax.swing.JMenuItem();
        popmenuRedo = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        popmenuCortar = new javax.swing.JMenuItem();
        popmenuCopiar = new javax.swing.JMenuItem();
        popmenuPegar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        popmenuGoto = new javax.swing.JMenuItem();
        popmenuBuscar = new javax.swing.JMenuItem();
        popmenuBuscarN = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        popmenuSel = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jToolBar1 = new BackGroundMenuBar();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 32767));
        btnAbrir = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnGuardar = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        jSeparator11 = new javax.swing.JToolBar.Separator();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        btnCortar = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnCopiar = new javax.swing.JButton();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnPegar = new javax.swing.JButton();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        jSeparator10 = new javax.swing.JToolBar.Separator();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        btnUndo = new javax.swing.JButton();
        btnRedo = new javax.swing.JButton();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        jSeparator12 = new javax.swing.JToolBar.Separator();
        filler13 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        btnCompilar = new javax.swing.JButton();
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblCol = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblLinea = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtResultado = new JTextPane() {

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getUI().getPreferredSize(this).width
                <= getParent().getSize().width;
            }

        };
        jSeparator9 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPane1 = new JTextPane() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getUI().getPreferredSize(this).width
                <= getParent().getSize().width;
            }

        };
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lexemeTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        staticTable = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        dynamicTable = new javax.swing.JTable();
        comboVariable = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        MostrarCodInt = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuNuevo = new javax.swing.JMenuItem();
        menuAbrir = new javax.swing.JMenuItem();
        menuGuardar = new javax.swing.JMenuItem();
        menuGuardarComo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuSalir = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenu();
        menubtnUndo = new javax.swing.JMenuItem();
        menubtnRedo = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        menuCortar = new javax.swing.JMenuItem();
        menuCopiar = new javax.swing.JMenuItem();
        menuPegar = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menubtnBuscar = new javax.swing.JMenuItem();
        menubtnBuscarS = new javax.swing.JMenuItem();
        menubtnGoto = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menubtnSel = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menubtnCompilar = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuTFija = new javax.swing.JMenuItem();
        menuTVar = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menubtnVerTablas = new javax.swing.JCheckBoxMenuItem();
        menubtnVerBH = new javax.swing.JCheckBoxMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuTFija2 = new javax.swing.JMenuItem();
        menuTFija5 = new javax.swing.JMenuItem();

        popmenuUndo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        popmenuUndo.setText("Deshacer");
        popmenuUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmenuUndoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popmenuUndo);

        popmenuRedo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        popmenuRedo.setText("Rehacer");
        popmenuRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmenuRedoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popmenuRedo);
        jPopupMenu1.add(jSeparator6);

        popmenuCortar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        popmenuCortar.setText("Cortar");
        popmenuCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmenuCortarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popmenuCortar);

        popmenuCopiar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        popmenuCopiar.setText("Copiar");
        popmenuCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmenuCopiarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popmenuCopiar);

        popmenuPegar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        popmenuPegar.setText("Pegar");
        popmenuPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmenuPegarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popmenuPegar);
        jPopupMenu1.add(jSeparator2);

        popmenuGoto.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        popmenuGoto.setText("Ir a la línea...");
        popmenuGoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmenuGotoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popmenuGoto);

        popmenuBuscar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        popmenuBuscar.setText("Buscar");
        popmenuBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmenuBuscarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popmenuBuscar);

        popmenuBuscarN.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        popmenuBuscarN.setText("Buscar siguiente");
        popmenuBuscarN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmenuBuscarNActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popmenuBuscarN);
        jPopupMenu1.add(jSeparator8);

        popmenuSel.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        popmenuSel.setText("Seleccionar todo");
        popmenuSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmenuSelActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popmenuSel);

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SHL - Sin Título");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jToolBar1.add(filler8);

        btnAbrir.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        btnAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/Imagenes/carpeta.png"))); // NOI18N
        btnAbrir.setToolTipText("Abrir");
        btnAbrir.setFocusable(false);
        btnAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbrir.setMaximumSize(new java.awt.Dimension(39, 39));
        btnAbrir.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAbrir);
        jToolBar1.add(filler1);

        btnGuardar.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/Imagenes/guardar.png"))); // NOI18N
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.setFocusable(false);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setMaximumSize(new java.awt.Dimension(39, 39));
        btnGuardar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGuardar);
        jToolBar1.add(filler2);
        jToolBar1.add(jSeparator11);
        jToolBar1.add(filler10);

        btnCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/Imagenes/tijeras.png"))); // NOI18N
        btnCortar.setToolTipText("Cortar");
        btnCortar.setFocusable(false);
        btnCortar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCortar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCortar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCortarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCortar);
        jToolBar1.add(filler6);

        btnCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/Imagenes/Copiar.png"))); // NOI18N
        btnCopiar.setToolTipText("Copiar");
        btnCopiar.setFocusable(false);
        btnCopiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopiar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCopiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopiarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCopiar);
        jToolBar1.add(filler11);

        btnPegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/Imagenes/clipboard.png"))); // NOI18N
        btnPegar.setToolTipText("Pegar");
        btnPegar.setFocusable(false);
        btnPegar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPegar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnPegar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPegarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPegar);
        jToolBar1.add(filler7);
        jToolBar1.add(jSeparator10);
        jToolBar1.add(filler5);

        btnUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/Imagenes/undo.png"))); // NOI18N
        btnUndo.setToolTipText("Deshacer");
        btnUndo.setFocusable(false);
        btnUndo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUndo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnUndo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnUndo);

        btnRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/Imagenes/redo.png"))); // NOI18N
        btnRedo.setToolTipText("Rehacer");
        btnRedo.setFocusable(false);
        btnRedo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRedo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnRedo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRedoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRedo);
        jToolBar1.add(filler9);
        jToolBar1.add(jSeparator12);
        jToolBar1.add(filler13);

        btnCompilar.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        btnCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/Imagenes/jugar.png"))); // NOI18N
        btnCompilar.setToolTipText("Compilar");
        btnCompilar.setFocusable(false);
        btnCompilar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompilar.setMaximumSize(new java.awt.Dimension(39, 39));
        btnCompilar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCompilar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCompilar);
        jToolBar1.add(filler12);

        jButton1.setText("Arboles");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel2.setText("Columna:");

        lblCol.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lblCol.setText("1");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel4.setText("Linea:");

        lblLinea.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lblLinea.setText("1");

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(480);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(1);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(1230, 650));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setFocusable(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1230, 150));

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txtResultado.setEditable(false);
        txtResultado.setBorder(null);
        txtResultado.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtResultado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtResultado.setInheritsPopupMenu(true);
        txtResultado.setMaximumSize(new java.awt.Dimension(2147483647, 6));
        txtResultado.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtResultadoCaretUpdate(evt);
            }
        });
        txtResultado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtResultadoMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(txtResultado);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel1.setText("Consola");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator9)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1158, 1158, 1158))
                    .addComponent(jScrollPane2)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel1);

        jSplitPane2.setDividerLocation(700);
        jSplitPane2.setResizeWeight(1);
        jSplitPane2.setMinimumSize(new java.awt.Dimension(113, 120));
        jSplitPane2.setPreferredSize(new java.awt.Dimension(1025, 300));

        jScrollPane1.setAutoscrolls(true);

        txtPane1.setBorder(null);
        txtPane1.setFont(new java.awt.Font("Monospaced", 0, 15)); // NOI18N
        txtPane1.setInheritsPopupMenu(true);
        txtPane1.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtPane1.setSelectionColor(new java.awt.Color(176, 197, 227));
        txtPane1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPane1CaretUpdate(evt);
            }
        });
        txtPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtPane1MouseReleased(evt);
            }
        });
        txtPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPane1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPane1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtPane1);

        jSplitPane2.setLeftComponent(jScrollPane1);

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbedPane1MousePressed(evt);
            }
        });

        lexemeTable.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lexemeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lexema", "Comp. léxico", "Linea"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lexemeTable.setRowHeight(22);
        lexemeTable.setRowSelectionAllowed(false);
        lexemeTable.getTableHeader().setResizingAllowed(false);
        lexemeTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(lexemeTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tokens", jPanel3);

        staticTable.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        staticTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lexema", "Comp. léxico"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        staticTable.setRowHeight(22);
        staticTable.setRowSelectionAllowed(false);
        staticTable.getTableHeader().setResizingAllowed(false);
        staticTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(staticTable);

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Palabras reservadas", "Operadores", "Signos" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, 0, 1175, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tabla Estática", jPanel2);

        dynamicTable.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        dynamicTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Tipo", "Valor", "Linea"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dynamicTable.setRowHeight(22);
        dynamicTable.setRowSelectionAllowed(false);
        dynamicTable.getTableHeader().setResizingAllowed(false);
        dynamicTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(dynamicTable);

        comboVariable.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        comboVariable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Variables", "Funciones" }));
        comboVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVariableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE)
                    .addComponent(comboVariable, javax.swing.GroupLayout.Alignment.TRAILING, 0, 1175, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tabla Variable", jPanel4);

        MostrarCodInt.setEditable(false);
        MostrarCodInt.setColumns(20);
        MostrarCodInt.setRows(5);
        jScrollPane7.setViewportView(MostrarCodInt);

        jTabbedPane1.addTab("Codigo Intermedio", jScrollPane7);

        jSplitPane2.setRightComponent(jTabbedPane1);

        jSplitPane1.setTopComponent(jSplitPane2);

        jMenuBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        menuArchivo.setBorder(null);
        menuArchivo.setText("Archivo");
        menuArchivo.setToolTipText("");
        menuArchivo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        menuNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuNuevo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuNuevo.setText("Nuevo");
        menuNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(menuNuevo);

        menuAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuAbrir.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuAbrir.setText("Abrir");
        menuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirActionPerformed(evt);
            }
        });
        menuArchivo.add(menuAbrir);

        menuGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuGuardar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuGuardar.setText("Guardar");
        menuGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarActionPerformed(evt);
            }
        });
        menuArchivo.add(menuGuardar);

        menuGuardarComo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuGuardarComo.setText("Guardar como...");
        menuGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarComoActionPerformed(evt);
            }
        });
        menuArchivo.add(menuGuardarComo);
        menuArchivo.add(jSeparator1);

        menuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuSalir.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuSalir.setText("Salir");
        menuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirActionPerformed(evt);
            }
        });
        menuArchivo.add(menuSalir);

        jMenuBar1.add(menuArchivo);

        menuEditar.setBorder(null);
        menuEditar.setText("Editar");
        menuEditar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        menubtnUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menubtnUndo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menubtnUndo.setText("Deshacer");
        menubtnUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menubtnUndoActionPerformed(evt);
            }
        });
        menuEditar.add(menubtnUndo);

        menubtnRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menubtnRedo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menubtnRedo.setText("Rehacer");
        menubtnRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menubtnRedoActionPerformed(evt);
            }
        });
        menuEditar.add(menubtnRedo);
        menuEditar.add(jSeparator5);

        menuCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuCortar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuCortar.setText("Cortar");
        menuCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCortarActionPerformed(evt);
            }
        });
        menuEditar.add(menuCortar);

        menuCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuCopiar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuCopiar.setText("Copiar");
        menuCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCopiarActionPerformed(evt);
            }
        });
        menuEditar.add(menuCopiar);

        menuPegar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuPegar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuPegar.setText("Pegar");
        menuPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPegarActionPerformed(evt);
            }
        });
        menuEditar.add(menuPegar);
        menuEditar.add(jSeparator4);

        menubtnBuscar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menubtnBuscar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menubtnBuscar.setText("Buscar");
        menubtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menubtnBuscarActionPerformed(evt);
            }
        });
        menuEditar.add(menubtnBuscar);

        menubtnBuscarS.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        menubtnBuscarS.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menubtnBuscarS.setText("Buscar siguiente");
        menubtnBuscarS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menubtnBuscarSActionPerformed(evt);
            }
        });
        menuEditar.add(menubtnBuscarS);

        menubtnGoto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menubtnGoto.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menubtnGoto.setText("Ir a la línea...");
        menubtnGoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menubtnGotoActionPerformed(evt);
            }
        });
        menuEditar.add(menubtnGoto);
        menuEditar.add(jSeparator3);

        menubtnSel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menubtnSel.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menubtnSel.setText("Seleccionar todo");
        menubtnSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menubtnSelActionPerformed(evt);
            }
        });
        menuEditar.add(menubtnSel);

        jMenuBar1.add(menuEditar);

        jMenu2.setText("Correr");
        jMenu2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        menubtnCompilar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        menubtnCompilar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menubtnCompilar.setText("Compilar");
        menubtnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menubtnCompilarActionPerformed(evt);
            }
        });
        jMenu2.add(menubtnCompilar);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Opciones");
        jMenu1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jMenu1.add(jSeparator7);

        jMenuItem1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jMenuItem1.setText("Fuente de letra");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Tablas");
        jMenu3.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        menuTFija.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuTFija.setText("Tabla Fija");
        menuTFija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTFijaActionPerformed(evt);
            }
        });
        jMenu3.add(menuTFija);

        menuTVar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuTVar.setText("Tabla Variable");
        menuTVar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTVarActionPerformed(evt);
            }
        });
        jMenu3.add(menuTVar);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Ver");
        jMenu4.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        menubtnVerTablas.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menubtnVerTablas.setSelected(true);
        menubtnVerTablas.setText("Tablas");
        menubtnVerTablas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menubtnVerTablasActionPerformed(evt);
            }
        });
        jMenu4.add(menubtnVerTablas);

        menubtnVerBH.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menubtnVerBH.setSelected(true);
        menubtnVerBH.setText("Barra de herramientas");
        menubtnVerBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menubtnVerBHActionPerformed(evt);
            }
        });
        jMenu4.add(menubtnVerBH);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Ayuda");
        jMenu5.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        menuTFija2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuTFija2.setText("Errores");
        menuTFija2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTFija2ActionPerformed(evt);
            }
        });
        jMenu5.add(menuTFija2);

        menuTFija5.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        menuTFija5.setText("Contacto");
        menuTFija5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTFija5ActionPerformed(evt);
            }
        });
        jMenu5.add(menuTFija5);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLinea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCol))
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblLinea)
                    .addComponent(jLabel2)
                    .addComponent(lblCol))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPegarActionPerformed
        txtPane1.paste();
    }//GEN-LAST:event_menuPegarActionPerformed

    private void menuCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCopiarActionPerformed
        txtPane1.copy();
    }//GEN-LAST:event_menuCopiarActionPerformed

    private void menuCortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCortarActionPerformed
        txtPane1.cut();
    }//GEN-LAST:event_menuCortarActionPerformed

    private void menuNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoActionPerformed
        actionNew();       
    }//GEN-LAST:event_menuNuevoActionPerformed

    private void menuGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarActionPerformed
        try {
            actionSave();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuGuardarActionPerformed

    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
        try {           
            actionOpen();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuAbrirActionPerformed

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        try {
            actionOpen();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            actionSave();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed
public void construirArbol(){
        String arbolprueba="";
        StringBuilder input1 = new StringBuilder();
        for(int i=0; i< Main.ArbolConPilas.size();i++){
            if(ArbolConPilas.get(i).equals("\t")){
                System.out.println(input1.toString());
                System.out.println(arbolprueba);
                posiblesArboles.add(arbolprueba);
                Nodo raiz=prueba.Crear(arbolprueba);
                
                arbolprueba="";
                input1.setLength(0);
            }else{
                input1.append(ArbolConPilas.get(i));
                arbolprueba+= ArbolConPilas.get(i);
            }       
        }
        
    }
    
    public static void mostrarInOrden(Nodo n){
        if(n!=null){
            mostrarInOrden(n.getNodoDer());
            if(n.getDato().equals("!")){System.out.print("!=");}
            else{System.out.print(n.getDato());}
            mostrarInOrden(n.getNodoIzq());
        }
    }
    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
    
        try {
            ArbolConPilas.clear();
            posiblesArboles.clear();
            cleanTables();
            actionSave();
            compilar(); //Reminder modifique el metodo compilar
            construirArbol();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        mostarCodInt();
    }//GEN-LAST:event_btnCompilarActionPerformed
    
    public static void codArduino(){

           //listaarduino 
            //codArInclude+=codArInclude2+"\n";
            codArSetup+=codarduinoS+"} \n";
            codArLoop+=codarduinoL+"} \n";
            String cod = codArInclude+codArInclude2+"\n"+codArSetup+codArLoop;
            codArSetup=confSetup;
            codArLoop=confLoop;
            codarduinoS="";
            codarduinoL="";
            codArInclude2="";
           // String id = "C:\\asd\\filename1\\filename1.ino";
           String id = "C:\\codarduino\\filename1\\filename1.ino";;
        try {
            String ruta = id+"";    
            String contenido = cod; //aqui va el cod arduino
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \" cd C:\\Users\\\\ZhouInput\\Desktop\\arduino && arduino-cli compile --fqbn arduino:avr:uno C:/codarduino/filename1/filename1.ino && exit");
            try
                {
                    Thread.sleep(12000);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \" cd C:\\Users\\\\ZhouInput\\Desktop\\arduino && arduino-cli upload -p COM4 --fqbn arduino:avr:uno C:/codarduino/filename1/filename1.ino && exit");
           
            //arduino-cli upload -p YourBoardPort --fqbn YourBoardFQBN YourSketchName arduino-cli upload -p COM3 --fqbn arduino:avr:uno C:/asd/filename1/filename1.ino
//arduino-cli compile --fqbn arduino:avr:uno C:/asd/filename1/filename1.ino
//C:/Users/usuarios/Desktop/arduino arduino-cli upload -p COM3 --fqbn arduino:avr:uno C:/asd/filename1/filename1.ino
            listaarduino.clear();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
    
    
    public static void mostarCodInt() {
        String txt="";
        for (int i = 0; i <mostrarCod.size(); i++) {
           txt += mostrarCod.get(i);
        }
        insertCodInt(txt);
        mostrarCod.clear();
    }
        
    private void btnPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPegarActionPerformed
        txtPane1.paste();
    }//GEN-LAST:event_btnPegarActionPerformed

    private void btnCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopiarActionPerformed
        txtPane1.copy();
    }//GEN-LAST:event_btnCopiarActionPerformed

    private void btnCortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCortarActionPerformed
        txtPane1.cut();        
    }//GEN-LAST:event_btnCortarActionPerformed

    private void btnUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoActionPerformed
        try {
                undoManager.undo();
                undoManager.undo();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_btnUndoActionPerformed

    private void btnRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedoActionPerformed
        try {
                undoManager.redo();
                undoManager.redo();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_btnRedoActionPerformed

    private void menuGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarComoActionPerformed
        actionSaveAs();
    }//GEN-LAST:event_menuGuardarComoActionPerformed

    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
        try {
            actionExit();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuSalirActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       actionSelectFont();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void popmenuCortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmenuCortarActionPerformed
        txtPane1.cut();
    }//GEN-LAST:event_popmenuCortarActionPerformed

    private void popmenuCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmenuCopiarActionPerformed
        txtPane1.copy();
    }//GEN-LAST:event_popmenuCopiarActionPerformed

    private void popmenuPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmenuPegarActionPerformed
        txtPane1.paste();
    }//GEN-LAST:event_popmenuPegarActionPerformed

    private void popmenuGotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmenuGotoActionPerformed
        actionGoToLine();
    }//GEN-LAST:event_popmenuGotoActionPerformed

    private void popmenuBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmenuBuscarActionPerformed
        actionSearch();
    }//GEN-LAST:event_popmenuBuscarActionPerformed

    private void popmenuBuscarNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmenuBuscarNActionPerformed
        actionSearchNext();
    }//GEN-LAST:event_popmenuBuscarNActionPerformed

    private void menubtnBuscarSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menubtnBuscarSActionPerformed
        actionSearchNext();
    }//GEN-LAST:event_menubtnBuscarSActionPerformed

    private void menubtnGotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menubtnGotoActionPerformed
        actionGoToLine();
    }//GEN-LAST:event_menubtnGotoActionPerformed

    private void menubtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menubtnBuscarActionPerformed
        actionSearch();;
    }//GEN-LAST:event_menubtnBuscarActionPerformed

    private void menubtnSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menubtnSelActionPerformed
        txtPane1.selectAll();
    }//GEN-LAST:event_menubtnSelActionPerformed

    private void popmenuSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmenuSelActionPerformed
        txtPane1.selectAll();
    }//GEN-LAST:event_popmenuSelActionPerformed

    private void menubtnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menubtnCompilarActionPerformed
        try {
            cleanTables();
            actionSave();
            compilar();
            System.out.println("\n-------------------------------------------------------------------------\n");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        mostarCodInt();
    }//GEN-LAST:event_menubtnCompilarActionPerformed

    private void menubtnVerTablasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menubtnVerTablasActionPerformed
        int div = 0;
        
        div = jSplitPane2.getDividerLocation();
        
        jTabbedPane1.setVisible(!jTabbedPane1.isVisible());
        jSplitPane2.setDividerLocation(700);
        revalidate();
    }//GEN-LAST:event_menubtnVerTablasActionPerformed

    private void menuTFijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTFijaActionPerformed
        tF.setLocationRelativeTo(null);
        tF.setVisible(true);
    }//GEN-LAST:event_menuTFijaActionPerformed

    private void menuTVarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTVarActionPerformed
        tV.setLocationRelativeTo(null);
        tV.setVisible(true);
    }//GEN-LAST:event_menuTVarActionPerformed

    private void menubtnUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menubtnUndoActionPerformed
        try {
                undoManager.undo();
                undoManager.undo();
        } catch (Exception ex) {
        }			      
    }//GEN-LAST:event_menubtnUndoActionPerformed

    private void menubtnRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menubtnRedoActionPerformed
         try {
                undoManager.redo();
                undoManager.redo();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_menubtnRedoActionPerformed

    private void popmenuUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmenuUndoActionPerformed
        try {
                undoManager.undo();
                undoManager.undo();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_popmenuUndoActionPerformed

    private void popmenuRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmenuRedoActionPerformed
        try {
                undoManager.redo();
                undoManager.redo();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_popmenuRedoActionPerformed

    private void txtPane1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPane1KeyTyped
        //        updateControls();    //actualiza el estado de las opciones "Deshacer" y "Rehacer"
        hasChanged = true;
    }//GEN-LAST:event_txtPane1KeyTyped

    private void txtPane1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPane1MouseReleased
        showPopupMenu(evt);
    }//GEN-LAST:event_txtPane1MouseReleased

    private void txtPane1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPane1CaretUpdate
        getLineAndColumn();
    }//GEN-LAST:event_txtPane1CaretUpdate

    private void txtResultadoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtResultadoCaretUpdate
//        if (txtResultado.getText().isEmpty()) {
//            btnLimpiar.setEnabled(false);
//        }else{
//            btnLimpiar.setEnabled(true);
//        }
    }//GEN-LAST:event_txtResultadoCaretUpdate

    private void menubtnVerBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menubtnVerBHActionPerformed
        jToolBar1.setVisible(!jToolBar1.isVisible());
    }//GEN-LAST:event_menubtnVerBHActionPerformed

    private void txtPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPane1KeyReleased
        // TODO add your handling code here:z
    }//GEN-LAST:event_txtPane1KeyReleased

    private void menuTFija2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTFija2ActionPerformed
      //  e.setLocationRelativeTo(null);
       // e.setVisible(!e.isVisible());
    }//GEN-LAST:event_menuTFija2ActionPerformed

    private void menuTFija5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTFija5ActionPerformed
       // c.setLocationRelativeTo(null);
      //  c.setVisible(!c.isVisible());
    }//GEN-LAST:event_menuTFija5ActionPerformed

    private void jTabbedPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MousePressed
        //        cambiarTabla(jComboBox1.getSelectedIndex());
        //        llenarTabla(comboVariable.getSelectedIndex());
    }//GEN-LAST:event_jTabbedPane1MousePressed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        //        cambiarTabla(jComboBox1.getSelectedIndex());
        //        llenarTabla(comboVariable.getSelectedIndex());
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged

    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void comboVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVariableActionPerformed
        llenarTabla(comboVariable.getSelectedIndex());
    }//GEN-LAST:event_comboVariableActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        cambiarTabla(jComboBox1.getSelectedIndex());
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Gui g = new Gui();
        for (int i =0;i<posiblesArboles.size();i++){
            g.ifbox.addItem(posiblesArboles.get(i));
        }
        g.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtResultadoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtResultadoMouseReleased
        lineas=txtResultado.getText().split("\n");
        new Errores(this,true,lineas).setVisible(true);
    }//GEN-LAST:event_txtResultadoMouseReleased

    /* ------------------------ Métodos secundarios---------------------------------------- */ 

    JFrame getJFrame() {
        return this;
    }
    
    boolean documentHasChanged() {
        return hasChanged;
    }
    
    private static FileFilter textFileFilter = new FileFilter() {
 
        @Override
        public boolean accept(File f) {
            //acepta directorios y archivos de extensión .txt
            return f.isDirectory() || f.getName().toLowerCase().endsWith("shl");
        }
 
        @Override
        public String getDescription() {
            //la descripción del tipo de archivo aceptado
            return "Archivos SHL";
        }
    };
    
    private static JFileChooser getJFileChooser() {
        JFileChooser fc = new JFileChooser();                     //construye un JFileChooser
        fc.setDialogTitle("SHL - Elige un archivo:");    //se le establece un título
        fc.setMultiSelectionEnabled(false);                       //desactiva la multi-selección
        fc.setFileFilter(textFileFilter);                         //aplica un filtro de extensiones
        return fc;    //retorna el JFileChooser
    }
    
    private static String shortPathName(String longPath) {
        //construye un arreglo de cadenas, donde cada una es un nombre de directorio
        String[] tokens = longPath.split(Pattern.quote(File.separator));
 
        //construye un StringBuilder donde se añadirá el resultado
        StringBuilder shortpath = new StringBuilder();
 
        //itera sobre el arreglo de cadenas
        for (int i = 0 ; i < tokens.length ; i++) {
            if (i == tokens.length - 1) {              //si la cadena actual es la última, es el nombre del archivo
                shortpath.append(tokens[i]);    //añade al resultado sin separador
                break;                          //termina el bucle
            } else if (tokens[i].length() >= 10) {     //si la cadena actual tiene 10 o más caracteres
                //se toman los primeros 3 caracteres y se añade al resultado con un separador
                shortpath.append(tokens[i].substring(0, 3)).append("...").append(File.separator);
            } else {                                   //si la cadena actual tiene menos de 10 caracteres
                //añade al resultado con un separador
                shortpath.append(tokens[i]).append(File.separator);
            }
        }
 
        return shortpath.toString();    //retorna la cadena resultante
    }
    
    private static String roundFileSize(long length) {
        //retorna el tamaño del archivo redondeado
        return (length < 1024) ? length + " bytes" : (length / 1024) + " Kbytes";
    }
    
    void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }
    
    void setDocumentChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }
    
    File getCurrentFile() {
        return currentFile;
    }
    
    UndoManager getUndoManager() {
        return undoManager;
    }

    public void undoableEditHappened(UndoableEditEvent uee) {
            /** el cambio realizado en el área de edición se guarda en el buffer
            del administrador de edición */
            undoManager.addEdit(uee.getEdit());
            //updateControls();    //actualiza el estado de las opciones "Deshacer" y "Rehacer"
            hasChanged = true;
        }
    
    /*--------------------------- Métodos para las funciones principales ----------------------- */
    
    // Guardar archivo como...
    
    public void actionSaveAs() {
        JFileChooser fc = getJFileChooser();    //obtiene un JFileChooser
 
        //presenta un dialogo modal para que el usuario seleccione un archivo
        int state = fc.showSaveDialog(getJFrame());
        if (state == JFileChooser.APPROVE_OPTION) {    //si elige guardar en el archivo
            File f = fc.getSelectedFile();    //obtiene el archivo seleccionado
 
            try {
                //abre un flujo de datos hacia el archivo asociado seleccionado
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                //escribe desde el flujo de datos hacia el archivo
                txtPane1.write(bw);
                bw.close();    //cierra el flujo
 
                //nuevo título de la ventana con el nombre del archivo guardado
                this.setTitle("SHL - " + f.getName());
 
                //muestra la ubicación del archivo guardado
                //lblFilePath.setText(shortPathName(f.getAbsolutePath()));
                //muestra el tamaño del archivo guardado
                // lblFileSize.setText(roundFileSize(f.length()));
 
                //establece el archivo guardado como el archivo actual
                this.setCurrentFile(f);
                //marca el estado del documento como no modificado
                this.setDocumentChanged(false);
            } catch (IOException ex) {    //en caso de que ocurra una excepción
                //presenta un dialogo modal con alguna información de la excepción
                JOptionPane.showMessageDialog(this.getJFrame(),
                                              ex.getMessage(),
                                              ex.toString(),
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Guardar archivo
    
    public void actionSave() throws IOException{       
         if (this.getCurrentFile() == null) {    //si no hay un archivo asociado al documento actual
            actionSaveAs();    //invoca el método actionSaveAs()
        } else if (this.documentHasChanged() == true) {    //si el documento esta marcado como modificado
            BufferedWriter bw;
            FileWriter fw = null;
            File file = currentFile;
            try {
                //abre un flujo de datos hacia el archivo asociado seleccionado
                bw = new BufferedWriter(new FileWriter(file));
                //escribe desde el flujo de datos hacia el archivo
                txtPane1.write(bw);
                bw.close();    //cierra el flujo

                //lblFileSize.setText(roundFileSize(f.length()));
                
                //marca el estado del documento como no modificado
                this.setDocumentChanged(false);
            } catch (IOException ex) {    //en caso de que ocurra una excepción
                //presenta un dialogo modal con alguna información de la excepción
                JOptionPane.showMessageDialog(this.getJFrame(),
                                            ex.getMessage(),
                                            ex.toString(),
                                            JOptionPane.ERROR_MESSAGE);
            }
        }       
    }
    
    // Abrir archivo
    
    public void actionOpen() throws IOException {
        if (documentHasChanged() == true) {    //si el documento esta marcado como modificado
            //le ofrece al usuario guardar los cambios
            int option = JOptionPane.showConfirmDialog(this.getJFrame(), "¿Desea guardar los cambios?");
 
            switch (option) {
                case JOptionPane.YES_OPTION:     //si elige que si
                    actionSave();               //guarda el archivo
                    break;
                case JOptionPane.CANCEL_OPTION:  //si elige cancelar
                    return;                      //cancela esta operación
                //en otro caso se continúa con la operación y no se guarda el documento actual
            }           
        }       
 
        JFileChooser fc = getJFileChooser();    //obtiene un JFileChooser
 
        //presenta un dialogo modal para que el usuario seleccione un archivo
        int state = fc.showOpenDialog(this.getJFrame());
        String s1, sl;
        
        if (state == JFileChooser.APPROVE_OPTION) {    //si elige abrir el archivo
            File f = fc.getSelectedFile();    //obtiene el archivo seleccionado
            if (!(f.getName().endsWith(".shl"))) {
                JOptionPane.showMessageDialog(null, "Solo los archivos con extensión .shl son admitidos");
                actionOpen();
                return;
            }
            try {
                cleanTables();
                //abre un flujo de datos desde el archivo seleccionado
                BufferedReader br = new BufferedReader(new FileReader(f));
                
                // Initilize sl
                sl = br.readLine();
                
                while ((s1 = br.readLine()) != null) {
                    sl = sl + "\n" + s1;
                }

                txtPane1.setText(sl);
                
                br.close();    //cierra el flujo
 
                //txtPane1.getDocument().addUndoableEditListener(this.getEventHandler());
 
                getUndoManager().die();    //se limpia el buffer del administrador de edición
                //updateControls();          //se actualiza el estado de las opciones "Deshacer" y "Rehacer"
 
                //nuevo título de la ventana con el nombre del archivo cargado
                this.setTitle("SHL - " + f.getName());
 
                //muestra la ubicación del archivo actual
                // lblFilePath.setText(shortPathName(f.getAbsolutePath()));
                //muestra el tamaño del archivo actual
                // lblFileSize.setText("| " + roundFileSize(f.length()));
 
                //establece el archivo cargado como el archivo actual
                setCurrentFile(f);
                //marca el estado del documento como no modificado
                setDocumentChanged(false);
            } catch (IOException ex) {    //en caso de que ocurra una excepción
                //presenta un dialogo modal con alguna información de la excepción
                JOptionPane.showMessageDialog(this.getJFrame(),
                                              ex.getMessage(),
                                              ex.toString(),
                                              JOptionPane.ERROR_MESSAGE);
            }
        }       
    }
    
    // Abrir nuevo archivo
    
    public void actionNew() {
        if (documentHasChanged() == true) {    //si el documento esta marcado como modificado
            //le ofrece al usuario guardar los cambios
            int option = JOptionPane.showConfirmDialog(this.getJFrame(), "¿Desea guardar los cambios?");
 
            switch (option) {
                case JOptionPane.YES_OPTION:       {
                try {
                    //si elige que si
                    actionSave();                  //guarda el archivo
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    break;
                case JOptionPane.CANCEL_OPTION:    //si elige cancelar
                    return;                        //cancela esta operación
                //en otro caso se continúa con la operación y no se guarda el documento actual
            }
        }
        cleanTables();
 
        this.getJFrame().setTitle("SHL - Sin Título");    //nuevo título de la ventana
 
        // limpia el contenido del area de edición
        txtPane1.setText("");
        txtResultado.setText("");
        // limpia el contenido de las etiquetas en la barra de estado
        // lblFilePath.setText("...");
        // lblFileSize.setText("");
 
        getUndoManager().die();    //limpia el buffer del administrador de edición
        //updateControls();          //actualiza el estado de las opciones "Deshacer" y "Rehacer"
 
        //el archivo asociado al documento actual se establece como null
        setCurrentFile(null);
        //marca el estado del documento como no modificado
        setDocumentChanged(false);
    }
    
    // Deshacer acción
    
    public void actionUndo() {
        try {
            //deshace el último cambio realizado sobre el documento en el área de edición
            getUndoManager().undo();
        } catch (CannotUndoException ex) {    //en caso de que ocurra una excepción
            System.err.println(ex);
        }
 
        //actualiza el estado de las opciones "Deshacer" y "Rehacer"
        //updateControls();
    }
    
    // Rehacer acción
    
    public void actionRedo() {
        try {
            //rehace el último cambio realizado sobre el documento en el área de edición
            getUndoManager().redo();
        } catch (CannotRedoException ex) {    //en caso de que ocurra una excepción
            System.err.println(ex);
        }
 
        //actualiza el estado de las opciones "Deshacer" y "Rehacer"
        //updateControls();
    }
   
    // Seleccionar tipo de letra
    
    public void actionSelectFont() {
        //presenta el dialogo de selección de fuentes
        Font font = JFontChooser.showDialog(this.getJFrame(),
                "SHL - Fuente de letra:",
                txtPane1.getFont());
        if (font != null) {    //si un fuente fue seleccionado
            //se establece como fuente del area de edición
            txtPane1.setFont(font);
            txtResultado.setFont(font);
            TextLineNumber tln = new TextLineNumber(txtPane1);
            tln.setUpdateFont(true);
            jScrollPane1.setRowHeaderView(tln);  
        }
    }
   
    // Ir a la línea...
    
    public void actionGoToLine() {
        //solicita al usuario que introduzca el número de línea
        String line = JOptionPane.showInputDialog(
                this.getJFrame(),
                "Número:",
                "Ir a la línea...",
                JOptionPane.QUESTION_MESSAGE);
 
        if (line != null && line.length() > 0) {    //si se introdujo un dato
            try {
                int pos = Integer.parseInt(line);    //el dato introducido se convierte en entero
 
                //si el número de línea esta dentro de los límites del área de texto
                if (pos >= 0 && pos <= getLines()) {
                    //posiciona el cursor en el inicio de la línea
                    txtPane1.setCaretPosition(getLineStartOffset(txtPane1,pos-1));
                }else{
                    JOptionPane.showMessageDialog(null, "La línea ingresada no existe");
                    actionGoToLine();
                }
            } catch (NumberFormatException  ex) {    //en caso de que ocurran excepciones
                JOptionPane.showMessageDialog(null, "Entrada no valída");
                actionGoToLine();
            } catch (BadLocationException ex) {    //en caso de que ocurran excepciones             
                actionGoToLine();
            }
        }
    }
    
    public void actionGoToLine(int linea) {
        //solicita al usuario que introduzca el número de línea
        String line = String.valueOf(linea);
 
        if (line != null && line.length() > 0) {    //si se introdujo un dato
            try {
                int pos = Integer.parseInt(line);    //el dato introducido se convierte en entero
 
                //si el número de línea esta dentro de los límites del área de texto
                if (pos >= 0 && pos <= getLines()) {
                    //posiciona el cursor en el inicio de la línea
                    txtPane1.setCaretPosition(getLineStartOffset(txtPane1,pos-1));
                }else{
                    actionGoToLine();
                }
            } catch (NumberFormatException  ex) {    //en caso de que ocurran excepciones
                System.err.println(ex);
                actionGoToLine();
            } catch (BadLocationException ex) {    //en caso de que ocurran excepciones
                System.err.println(ex);
                actionGoToLine();
            }
        }
    }
   
    // Salir
    
    public void actionExit() throws IOException {
        if (documentHasChanged() == true) {    //si el documento esta marcado como modificado
            //le ofrece al usuario guardar los cambios
            int option = JOptionPane.showConfirmDialog(this.getJFrame(), "¿Desea guardar los cambios?");
            switch (option) {
                case JOptionPane.YES_OPTION:     //si elige que si
                    actionSave();                //guarda el archivo
                    break;
                case JOptionPane.CANCEL_OPTION:  //si elige cancelar
                    return;                      //cancela esta operación
                //en otro caso se continúa con la operación y no se guarda el documento actual
            }
    }
 
 
        System.exit(0);    //finaliza el programa con el código 0 (sin errores)
    }
    
    // Buscar
    
    public void actionSearch() {
        //solicita al usuario que introduzca el texto a buscar
        String text = JOptionPane.showInputDialog(
                this.getJFrame(),
                "Texto:",
                "Buscar",
                JOptionPane.QUESTION_MESSAGE);
 
        if (text != null) {    //si se introdujo texto (puede ser una cadena vacía)
            String textPaneContent = txtPane1.getText();    //obtiene todo el contenido del área de edición
            int pos = textPaneContent.indexOf(text);    //obtiene la posición de la primera ocurrencia del texto
 
            if (pos > -1) {    //si la posición es mayor a -1 significa que la búsqueda fue positiva
                //selecciona el texto en el área de edición para resaltarlo
                txtPane1.select(pos, pos + text.length());
            }
 
            //establece el texto buscado como el texto de la última búsqueda realizada
            lastSearch = text;
        }
    }
    
    // Buscar siguiente...
    
    public void actionSearchNext() {
        if (lastSearch.length() > 0) {    //si la última búsqueda contiene texto
            String textPaneContent = txtPane1.getText();    //se obtiene todo el contenido del área de edición
            int pos = txtPane1.getCaretPosition();    //se obtiene la posición del cursor sobre el área de edición
            //buscando a partir desde la posición del cursor, se obtiene la posición de la primera ocurrencia del texto
            pos = textPaneContent.indexOf(lastSearch, pos);
 
            if (pos > -1) {    //si la posición es mayor a -1 significa que la búsqueda fue positiva
                //selecciona el texto en el área de edición para resaltarlo
                txtPane1.select(pos, pos + lastSearch.length());
            }
        } else {    //si la última búsqueda no contiene nada
            actionSearch();    //invoca el método actionSearch()
        }
    }
   
    /* ---------------------------- Otros metodos --------------------------- */
    
    //Obtener linea y columna del cursor
    
    public void getLineAndColumn(){
            int pos = txtPane1.getCaretPosition();
            Element map = txtPane1.getDocument().getDefaultRootElement();
            int row = map.getElementIndex(pos);
            Element lineElem = map.getElement(row);
            int col = pos - lineElem.getStartOffset();
            
            row = row+1;
            col = col+1;
            
            lblLinea.setText(""+row);
            lblCol.setText(""+col); 
    }
    
    // Limpiar las tablas variables
    
    public void cleanTables(){
//        tV.model.getDataVector().removeAllElements();
//        tV.model.fireTableDataChanged();
        model3.getDataVector().removeAllElements();
        model3.fireTableDataChanged();
        revalidate();
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        revalidate();
        model1.getDataVector().removeAllElements();
        model1.fireTableDataChanged();
        revalidate();
    }
    
    //Métodos necesario para pintar palabras
    
    public int getLineStartOffset(JTextComponent comp, int line) throws BadLocationException {
        Element map = comp.getDocument().getDefaultRootElement();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line >= map.getElementCount()) {
            throw new BadLocationException("No such line", comp.getDocument().getLength() + 1);
        } else {
            Element lineElem = map.getElement(line);
            return lineElem.getStartOffset();
        }
    }
    
    private int findLastNonWordChar (String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }
    
    //obtiener el número de líneas
   
    public int getLines(){
        String totalCharacters = txtPane1.getText(); 
        int lineCount =  totalCharacters.split("\n").length+1;
        return lineCount;
    }
    
    private void seticon() {
     setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icono2.png")));
    }
    
    // Obtiene las coordenadas para desplegar el shoPopMenu
   
    /**
     * Atiende y maneja los eventos sobre el ratón cuando este es liberado.
     *
     * @param me evento del ratón
     */
    
    private void showPopupMenu(MouseEvent me) {
        if (me.isPopupTrigger() == true) {    //si el evento es el desencadenador de menú emergente
            //hace visible el menú emergente en las coordenadas actuales del ratón
            jPopupMenu1.show(me.getComponent(), me.getX(), me.getY());
        }
    }

    // ----------------------------- Tablas ----------------------------------------   
    
    //---------------------- agregar id watch ------------------------------- //
    public static void agregarid(String ID, String tipo, String val,String linea) { 
       Arr.add(new ArryL(ID, tipo, val,linea));
    }
    
    //--------------------- actualizar valor y linea de id watch ------------------------------- //
     public static void actualizar(String ID, String tipo, String val, int v) {
         for (int i = 0; i < Arr.size(); i++) {
            if (Arr.get(i).getId().equals(ID)) {
        if ((Arr.get(i).getTipo().equals("Int")&& Main.isNumeric(val))||(Arr.get(i).getTipo().equals("Float")&& Main.isNumericFloat(val))||(Arr.get(i).getTipo().equals("Text")&&((val.charAt(0)==34)&& (val.charAt(val.length()-1)==34)))||((Arr.get(i).getTipo().equals("Bool"))&&(val.equals("True")||val.equals("False"))))
                Arr.get(i).setVal(val);
            else{
           //  setError("Error Semantico; el valor: "+val+" . Que se encuentra renglon: "+(v+1)+" no concuerda con el tipo de dato: "+Arr.get(i).getTipo());

             }
            }
        }
        
       }
    
    public static void insertCodInt(String Cadena){
       MostrarCodInt.setText("Código Intermedio\nOp|A1|A2|R| \n"+Cadena);
        
    }
    
      public static boolean isNumeric(String cadena) {
         
        boolean resultado;

        try {
            Integer.parseInt(cadena);
       
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
      public static boolean isNumericFloat(String cadena) {

        boolean resultado;

        try {
            //Integer.parseInt(cadena);
            Float.parseFloat(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    
    
    
    public void llenarTabla(int index){
        
        int col = 0;
        int row = 0;
      

        switch (index) {
            /*
            case -1:
                cleanTable2();
                    if(!listaIdValor.isEmpty()){
                        for (int j = 0; j < listaValoresId.size(); j++) {
                            if(!listaIdFuncValor.contains(listaIdValor.get(j))){
                                model1.addRow(new Object[]{"", ""});
                                dynamicTable.setValueAt(listaIdValor.get(j),row,col);
                                col++;
                                dynamicTable.setValueAt(listaIdTipo.get(j),row,col);  // tipo de id
                                col++;
                                // dynamicTable.setValueAt("",row,col);
                                dynamicTable.setValueAt(listaValoresId.get(j),row,col);
                                col++;
                                dynamicTable.setValueAt(listaIdLinea.get(j),row,col);
                                row++;
                                col = 0;
                            }
                        }
                    }
                break; 
                */
            case 0:
                cleanTable2();
                    if(!listaIdValor.isEmpty()){
                       
                        for (int j = 0; j < listaValoresId.size(); j++) {
                //            if(!listaIdFuncValor.contains(listaIdValor.get(j))){
                                model1.addRow(new Object[]{"",""});
                                dynamicTable.setValueAt(listaIdValor.get(j),row,col);
                                col++;
                                dynamicTable.setValueAt(listaIdTipo.get(j),row,col);  // tipo de id
                                col++;
                                // dynamicTable.setValueAt("",row,col);
                                dynamicTable.setValueAt(listaValoresId.get(j),row,col);
                                col++;
                                dynamicTable.setValueAt(listaIdLinea.get(j),row,col);
                                
                                row++;
                                col =0;
                   //         }
                        }
                    }
                break;
        /*    case 1:
                cleanTable2();
                for (int j = 0; j < listaIdFuncValor.size(); j++) {
                    model1.addRow(new Object[]{"", ""});
                    dynamicTable.setValueAt(listaIdFuncValor.get(j),row,col);
                    col++;
                    dynamicTable.setValueAt("Función",row,col);
                    col++;
                    dynamicTable.setValueAt(listaIdFuncLinea.get(j),row,col);
                    row++;
                    col = 0;
                }
                break;
                */
            default:
                System.out.println("hello llenar tabla");
        }
    }
    
    public static boolean buscarid(String a) {// comprobar id si existen en la tabla de simbolo
        for (int i = 0; i < listaIdValor.size(); i++) {
            if (listaIdValor.get(i).equals(a)) {
                return true;
            }
        }
        return false;
    }
    
    public static int returnid(String a) {// comprobar id si existen en la tabla de simbolo y retornar el index
      
        
        int in=0;
        for (int i = 0; i < listaIdValor.size(); i++) {
            in =i;
            if (listaIdValor.get(i).equals(a)) {
                return in;
            }
        }
    
        return in;
    }
    
   
     
     
    public static String returntipo(String a) {// comprobar id si existen en la tabla de simbolo y retornar el index
        String in="";
   
        for (int i = 0; i < listaIdValor.size(); i++) {
            
            if (listaIdValor.get(i).equals(a)) {
                in =listaIdTipo.get(i);   
                
            }
        }
        return in;
        
    
    }
    
    public static String agregarvalue(String txt){
        
        
        
        return txt;
    }
    
    public static String removeid(String a){//buscar id repetido y actualizarlo
 
        String x="";
         for(int i=0; i<listaIdValor.size(); i++){ 
          
           //  System.out.println(listaIdValor.get(i));
                if (listaIdValor.get(i).equals(a)){
                   // i++;
                   System.out.println(listaValoresId.get(i)+" "+listaIdValor.get(i));
                    x =listaIdTipo.get(i);
                    listaIdValor.remove(i);                                     
                    listaIdLinea.remove(i);
                    listaIdTipo.remove(i);
                    listaValoresId.remove(i);
                 
                  
                    System.out.println(listaIdValor.size()); 
                    System.out.println(listaIdLinea.size()); 
                    System.out.println(listaIdTipo.size()); 
                    System.out.println(listaValoresId.size()); 
                    
                //   model1.removeRow(listaIdValor.size());
                  
                //    dynamicTable.remove(listaIdValor.size());
                  
                                    }
                  
                                }
          return x;
    }
        

    
    public String[] acomodar(String[] lista){
        for(int i = 0; i<lista.length; i++){  
            for (int j = i+1; j<lista.length; j++){  
                //compares each elements of the array to all the remaining elements  
                if(lista[i].compareTo(lista[j])>0){  
                    //swapping array elements  
                    String temp = lista[i];  
                    lista[i] = lista[j];  
                    lista[j] = temp;  
                }  
            }  
        }
        return lista; 
        
    } 
    
    public void cambiarTabla(int index){
            
        int col = 0;
        int row = 0;
        
        String[] pal_res = new String[]{"Int", "Text" , "Float" , "Bool" , "For" , "While" , "If" , "Else" , "When" , "Default" ,"Display" ,"Home" , "Initialize" , "PortA" , "PortB" , "PortC" , "PortD" , "Enable" , "Room", "Program", "New" ,"Temp" , "GetTemp" , "Ac" , "Set" , "Start" , "Shutdown" , "Light" , "Off" , "On" , "LightRGB" , "LightMode" , "Color" , "Door" , "Open" , "Close" , "Lock" , "Unlock" , "Window" , "IsOpen" , "IsClose" , "IsLock" ,"IsUnlock" , "UnlockAt" , "LockAt" , "Camera" , "Record" , "StopRec" , "Move" , "IsMove", "Alarm" , "Device" , "IsIn" , "IsOut", "Doorbell","UnSync","FaceCheck","SaveFace","DeleteFace", "DeviceType","Sync","SmartCamera","Receive"}; // 
        pal_res = acomodar(pal_res);
        String[] compl_pal_res = new String[]{"Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada","Palabra reservada"};
        compl_pal_res = acomodar(compl_pal_res);
        String[] op = new String[]{"[","]","(",")","{","}","+","-","*","/","=>","=","--","++","&&","||","!","!=","<",">","<=",">=","=="};
        String[] compl_op = new String[]{"Op. Asignación","Op. Arrow","Op. Aritmetico","Op. Aritmetico","Op. Aritmetico","Op. Aritmetico","Op. Relacional","Op. Relacional","Op. Relacional","Op. Relacional","Op. Relacional","Op. Relacional","Op. Relacional", "Op. Agrupación","Op. Agrupación","Op. Agrupación","Op. Agrupación","Op. Agrupación","Op. Agrupación", "Op. Lógico", "Op. Lógico","Op. Incremental", "Op. Decremental"};
        compl_op = acomodar(compl_op);
        String[] sign = new String[]{"'",";",":",".",","};
        String[] compl_sign = new String[]{"Delimitador","Separador","Punto","Dos_puntos","Comilla_simple"};
        compl_sign = acomodar(compl_sign);
        
        switch (index) {
            case -1:
                    cleanTable();
                    for (int i = 0; i < pal_res.length; i++) {
                        model2.addRow(new Object[]{"", ""});
                        staticTable.setValueAt(pal_res[i],row,col);
                        col++;
                        staticTable.setValueAt(compl_pal_res[i],row,col);
                        row++;
                        col--;
                    }     
                break;
            case 0:
                    cleanTable();
                    for (int i = 0; i < pal_res.length; i++) {
                        model2.addRow(new Object[]{"", ""});
                        staticTable.setValueAt(pal_res[i],row,col);
                        col++;
                        staticTable.setValueAt(compl_pal_res[i],row,col);
                        row++;
                        col--;
                    }     
                break;
            case 1:
                    cleanTable();
                    for (int i = 0; i < op.length; i++) {
                        model2.addRow(new Object[]{"", ""});
                        staticTable.setValueAt(op[i],row,col);
                        col++;
                        staticTable.setValueAt(compl_op[i],row,col);
                        row++;
                        col--;
                    }     
                break;
            case 2:
                    cleanTable();
                    for (int i = 0; i < sign.length; i++) {
                        model2.addRow(new Object[]{"", ""});
                        staticTable.setValueAt(sign[i],row,col);
                        col++;
                        staticTable.setValueAt(compl_sign[i],row,col);
                        row++;
                        col--;
                    }     
                break;
            default:
                System.out.println("hello");
        }
    }
    
    public void cleanTable(){
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        revalidate();
    }
    
    public void cleanTable2(){
        model1.getDataVector().removeAllElements();
        model1.fireTableDataChanged();
        revalidate();
    }
    
    // ----------------------------- Analisis léxico  ----------------------------------------   
    
    // Método para obtener los valores de las variables
    
    private void getIdValue(ArrayList<Integer> lineasId){
        String temp;
        String []lineas = txtPane1.getText().split("\n");

        for (int i = 0; i < lineasId.size(); i++) {
            temp = lineas[lineasId.get(i)-1].trim();
            
            if(temp.contains("=") && temp.contains(";")){
                temp = temp.substring(temp.indexOf("=") + 1);
                temp = temp.substring(0, temp.indexOf(";"));
                listaValoresId.add(temp.trim());
            }else{
                listaIdValor.remove(i); 
                listaIdLinea.remove(i);
                listaIdTipo.remove(i);
                        
                System.out.println("error" + " -> " + temp);
            }       
        }

    }
    
    // Método principal para el analisis léxico
    
    private String analizadorLexico(String txt){
        File archivo = new File("archivo.txt");
        PrintWriter escribir;       
        int col = 0;
        int row = 0;    
        String linea = "";
        
        String []lineas = txtPane1.getText().split("\n");
        tV.model.getDataVector().removeAllElements(); 
        revalidate();
        
        try {
            escribir = new PrintWriter(archivo);
            escribir.print(txtPane1.getText());
            escribir.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Reader lector = new BufferedReader(new FileReader("archivo.txt"));
            Lexer lexer = new Lexer(lector);
            String resultado = txt;           
            lexer.yyreset(lector);       
            
            while (true) {
                Tokens tokens = lexer.yylex();
                if (tokens == null) {                 
                    
                    for (int i = 0; i < listaCompLexico.size(); i++) {
                        model3.addRow(new Object[]{"", ""});
                        lexemeTable.setValueAt(listaLexemas.get(i),row,col);
                        col++;
                        lexemeTable.setValueAt(listaCompLexico.get(i),row,col);
                        col++;
                        lexemeTable.setValueAt(listaLineaLexemas.get(i),row,col);
                        row++;
                        col = 0;
                    }
                    
                    getIdValue(listaIdLinea);
                    
                    if(!listaIdValor.isEmpty()){
                            row = 0;
                            col = 0;
                            for (int j = 0; j < listaValoresId.size(); j++) {
                                if(!listaIdFuncValor.contains(listaIdValor.get(j))){
                                    model1.addRow(new Object[]{"", ""});
                              //      dynamicTable.setValueAt(listaIdValor.get(j),row,col);
                                    col++;
                              //      dynamicTable.setValueAt(listaIdTipo.get(j),row,col);  // tipo de id
                              //      col++;
                                    // dynamicTable.setValueAt("",row,col);
                              //      dynamicTable.setValueAt(listaValoresId.get(j),row,col);
                                    col++;
                              //      dynamicTable.setValueAt(listaIdLinea.get(j),row,col);
                                    row++;
                                    col = 0;
                                }
                            }
                    }
                    return resultado;
                }
                switch (tokens) {
                    case Palabra_reservada:
                        break;
                    case Identificador:
                        lexer.linea += 1;                       
                        
                        linea = lineas[lexer.linea-1].trim();
                        
                        if(!linea.isEmpty()){
                            if(linea.contains("=") && !linea.contains("==")){
                                for(int i=0; i<listaIdValor.size(); i++){ 
                                    if (listaIdValor.get(i).equals(lexer.lexeme)){
                                        //listaIdValor.remove(i);                                     
                                      //  listaIdLinea.remove(i);
                                        
                                    }
                                }//agreagar codigo de validar solo declaracion sin asginacion: Tipo id;     
                                
                            }else{
                                for(int i=0; i<listaIdFuncValor.size(); i++){    
                                    if (listaIdFuncValor.get(i).equals(lexer.lexeme)){
                                        // System.out.println("Función -> " + linea);
                                        listaIdFuncValor.remove(i);
                                        listaIdFuncLinea.remove(i);
                                    }
                                }
                            }
                        }
                        
                        if(linea.contains("=") && linea.contains("Text") &&!linea.contains("==") && !linea.contains("<=") && !linea.contains(">=")&& !linea.contains(">")&& !linea.contains("++")){
                           // listaIdValor.add(lexer.lexeme);  
                            //listaIdLinea.add(lexer.linea);
                            break;
                        }else{
                           // listaIdFuncValor.add(lexer.lexeme); 
                          //  listaIdFuncLinea.add(lexer.linea); 
                            break;
                        }                                     
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return txt;
    }
    
    // ----------------------------- Analisis Sintactico ----------------------------------------   
    
    private String analizadorSintactico(String txt){
        String ST = txtPane1.getText();
        Sintax s = new Sintax(new LexerCup(new StringReader(ST)));
        
        if(!txtPane1.getText().isEmpty()){
            try {
                s.parse();
            } catch (Exception ex) {
                try{            
                    Symbol sym = s.getS();
                    
                    Main.error = true;
                    Main.arbolSin.add("( 0 ) ---> Error General\n"); 
                    Main.listaErroresSin.add("> Linea ( 0 ) - Error sintáctico - e016 - "
                      + "Error general, el código no coincidio con ninguna gramática.\n");
                    
                }catch (Exception exp){
                    System.err.print(exp);
                }           
            }
        }
        return txt;
    }
    
    
    
    // ----------------------------- Compilación  ----------------------------------------     
   
        private void compilar() throws IOException{
        
        listaIdLinea.clear();
        listaValoresId.clear();
        listaIdValor.clear();
        listaIdTipo.clear();
        
        listaErrores.clear();
        listaCodigo.clear();
        
        listaLexemas.clear();
        listaCompLexico.clear();
        listaLineaLexemas.clear();
        
        listaIdFuncLinea.clear();
        listaValoresIdFunc.clear();
        listaIdFuncValor.clear();
        
        variableSize = 0;
        
        cambiarTabla(jComboBox1.getSelectedIndex());
        
        error = false;
        
        /* ---- Variables para el analisis sintactico ------ */
        
        countLineas = 0;
        listaLineasSin.clear();
        listaErroresSin.clear();
        arbolSin.clear();
        
        listavalor1.clear();
        
        /* ------------------------------------------------- */
        
        String txt = "";
        // analizadorLexico(txt);
        analizadorSintactico(analizadorLexico(txt));

//        for (int i = 0; i < arbolSin.size(); i++) {
//            System.out.println(arbolSin.get(i));
//        }
         codArduino();
        if(error){
            txt += "> Análisis completado con errores\n";
            
            getErrorLines(listaErroresSin);

            listaErrores.addAll(listaErroresSin);         
            ordenarErrores(listaErrores);

            for (int i = 0; i < listaErrores.size() ; i++) {
                
                txt += listaErrores.get(i);
            }
            txtResultado.setText(txt);
            txtResultado.setForeground(Color.red);
        }else{
            txt += "> Análisis completado con éxito\n";
            txtResultado.setText(txt);
            txtResultado.setForeground(new Color(25, 111, 61));
        }
    }

    private void getErrorLines(ArrayList<String> lista){
        String temp;
        String []lineasErr = txtPane1.getText().split("\n");

        for (int j = 0; j < lineasErr.length; j++) {
            for (int l = 0; l < lista.size(); l++) {
                temp = lista.get(l).trim();
                temp = temp.substring(temp.indexOf("( ") + 2);
                temp = temp.substring(0, temp.indexOf(" )"));
                if(Integer.valueOf(temp) == (j+1)){
                   lista.set(l, lista.get(l)+lineasErr[j].trim()+"\n");
                }
            }
        }
    }
    
    private void ordenarErrores(ArrayList lista){
        Collections.sort(lista, (String s1, String s2) -> {
            s1 = s1.trim();
            
            s1 = s1.substring(s1.indexOf("( ") + 2);
            s1 = s1.substring(0, s1.indexOf(" )"));
            
            s2 = s2.trim();
            
            s2 = s2.substring(s2.indexOf("( ") + 2);
            s2 = s2.substring(0, s2.indexOf(" )"));
            
            return Integer.valueOf(s1).compareTo(Integer.valueOf(s2));
        });
    }
    
    // ----------------------------- Analisis Semantico y generación de código ----------------------------------------   
    
    private void errores(String cad){ 

        Collections.reverse(listaErrores);
        
        System.out.println(listaErrores);
    
        if(error){
            String hola = txtPane1.getText();
            boolean flag = true;
            try {
                for(int i=0;i<listaErrores.size();i++){
                    String mp = listaErrores.get(i);
                    String aux ="";
                    if(mp.indexOf("~")==-1){
                        mp = mp.substring(26,28);
                        String []arreglo = txtPane1.getText().split("\n");
                        try{
                            int k=Integer.parseInt(mp.replace(" ", ""));
                            //------> LE PUSE EL -1 y le puse +2 en el sintas, debe ser + 1<-------
                            aux = arreglo[k-1];
                            aux = aux.replaceAll("\t","");
                            // aux = aux.replaceAll(" ","");
                            aux = aux.replaceAll("\r","");
                        }catch(NumberFormatException a){}
                    }
                    if (flag==false && i==0) {
                        cad="Analizando...\n";
                    }else{
                        cad+="ERROR: "+(i+1)+" "+listaErrores.get(i).replace("~", "")+" "+aux+"\n";
                    }
                }
            } catch (Exception e) {
                System.err.print(e);
                cad += listaErrores.get(0);
            }
            txtResultado.setText(cad+"> Compilación completada con errores");
            txtResultado.setForeground(Color.red);
        }else{
            txtResultado.setText(cad+"> Compilación completada con éxito");
            txtResultado.setForeground(new Color(25, 111, 61));
        }     
    }
    
    private String generarCodigo(){
        String codigo = "";
        codigo += "//>>> Compilado con SHL <<<\n\n";
        codigo += "#include <Ultrasonic.h>\n"+
                  "#define pinLED 12\n\n" +
                  "Ultrasonic ultrasonic(10,8,24500);\n\n";
        
        if(error == false){      
            try {
                for(int i=listaCodigo.size()-1;i>=0;i--){
                    System.out.println(listaCodigo.get(i));
                    if(listaCodigo.get(i)=="void loop() {\n"){
                        codigo += "}\n" + listaCodigo.get(i);
                    }else{
                        codigo += listaCodigo.get(i);
                    }
                }
            } catch (Exception e) {
                System.err.print(e);
            }
        }  
        return codigo + "}";
    }
    
    private void createDoc(){
        try {
            File file = new File("C:\\Users\\arcin\\Documents\\TEC\\LYA2\\Projectos_Java\\Compilador\\codigo.ino");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    private void writeDoc(String txt){
        try {
            FileWriter myWriter = new FileWriter("C:\\Users\\arcin\\Documents\\TEC\\LYA2\\Projectos_Java\\Compilador\\codigo.ino");
            myWriter.write(txt);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
       
    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    } 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextArea MostrarCodInt;
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnCopiar;
    private javax.swing.JButton btnCortar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnPegar;
    private javax.swing.JButton btnRedo;
    private javax.swing.JButton btnUndo;
    private javax.swing.JComboBox<String> comboVariable;
    public static javax.swing.JTable dynamicTable;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler12;
    private javax.swing.Box.Filler filler13;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblCol;
    private javax.swing.JLabel lblLinea;
    static javax.swing.JTable lexemeTable;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuItem menuCopiar;
    private javax.swing.JMenuItem menuCortar;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenuItem menuGuardarComo;
    private javax.swing.JMenuItem menuNuevo;
    private javax.swing.JMenuItem menuPegar;
    private javax.swing.JMenuItem menuSalir;
    private javax.swing.JMenuItem menuTFija;
    private javax.swing.JMenuItem menuTFija2;
    private javax.swing.JMenuItem menuTFija5;
    private javax.swing.JMenuItem menuTVar;
    private javax.swing.JMenuItem menubtnBuscar;
    private javax.swing.JMenuItem menubtnBuscarS;
    private javax.swing.JMenuItem menubtnCompilar;
    private javax.swing.JMenuItem menubtnGoto;
    private javax.swing.JMenuItem menubtnRedo;
    private javax.swing.JMenuItem menubtnSel;
    private javax.swing.JMenuItem menubtnUndo;
    private javax.swing.JCheckBoxMenuItem menubtnVerBH;
    private javax.swing.JCheckBoxMenuItem menubtnVerTablas;
    private javax.swing.JMenuItem popmenuBuscar;
    private javax.swing.JMenuItem popmenuBuscarN;
    private javax.swing.JMenuItem popmenuCopiar;
    private javax.swing.JMenuItem popmenuCortar;
    private javax.swing.JMenuItem popmenuGoto;
    private javax.swing.JMenuItem popmenuPegar;
    private javax.swing.JMenuItem popmenuRedo;
    private javax.swing.JMenuItem popmenuSel;
    private javax.swing.JMenuItem popmenuUndo;
    public javax.swing.JTable staticTable;
    private javax.swing.JTextPane txtPane1;
    public javax.swing.JTextPane txtResultado;
    // End of variables declaration//GEN-END:variables
}
