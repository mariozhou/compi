/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DEIMOS
 */
public class TableFija extends javax.swing.JFrame {

    /**
     * Creates new form TableFija
     */
    public TableFija() {
        try {    //LookAndFeel nativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            System.err.println(ex);
        }
        initComponents();
        seticon();
        model = (DefaultTableModel) tablaFija.getModel();   
        cambiarTabla(jComboBox1.getSelectedIndex());
    }
    
    DefaultTableModel model;
    
    public void cambiarTabla(int index){
        
        int col = 0;
        int row = 0;
        
        String[] pal_res = new String[]{"si","sino","mientras","switch","Edato","Sdato","ini","ent","func","bool","cad","lista",
                                        "verdadero","falso","car","para","ir","show","mes","año","dia","fec","privado","publico"};
        String[] compl_pal_res = new String[]{"pal_res_si","pal_res_sino","pal_res_mientras","pal_res_switch","pal_res_Edato",
                                              "pal_res_Sdato","pal_res_ini","pal_res_ent","pal_res_func",
                                              "pal_res_bool","pal_res_cad","pal_res_lista","pal_res_verdadero","pal_res_falso",
                                              "pal_res_car","pal_res_para","pal_res_ir","pal_res_show","pal_res_mes",
                                              "pal_res_año","pal_res_dia","pal_res_fec","pal_res_privado","pal_res_publico"};
        String[] op = new String[]{"+","-","*","/","!","!=","<",">","<=",">=","==","[","]","(",")","{","}"};
        String[] compl_op = new String[]{"op_suma","op_resta","op_mult","op_div","op_negacion","op_diferente","op_menor","op_mayor","op_menor_igual","op_mayor_igual","op_comparacion", "op_agrup_corchete_a","op_agrup_corchete_c","op_agrup_parentesis_a","op_agrup_parentesis_c","op_agrup_llave_a","op_agrup_llave_c"};
        
        String[] sign = new String[]{";",":",",","."};
        String[] compl_sign = new String[]{"sig_punt_punto_y_coma","sig_punt_dos_puntos",
                                            "sig_punt_coma","sig_punt_punto"};
        switch (index) {
            case -1:
                    cleanTable();
                    for (int i = 0; i < pal_res.length; i++) {
                        model.addRow(new Object[]{"", ""});
                        tablaFija.setValueAt(pal_res[i],row,col);
                        col++;
                        tablaFija.setValueAt(compl_pal_res[i],row,col);
                        row++;
                        col--;
                    }     
                break;
            case 0:
                    cleanTable();
                    for (int i = 0; i < pal_res.length; i++) {
                        model.addRow(new Object[]{"", ""});
                        tablaFija.setValueAt(pal_res[i],row,col);
                        col++;
                        tablaFija.setValueAt(compl_pal_res[i],row,col);
                        row++;
                        col--;
                    }     
                break;
            case 1:
                    cleanTable();
                    for (int i = 0; i < op.length; i++) {
                        model.addRow(new Object[]{"", ""});
                        tablaFija.setValueAt(op[i],row,col);
                        col++;
                        tablaFija.setValueAt(compl_op[i],row,col);
                        row++;
                        col--;
                    }     
                break;
            case 2:
                    cleanTable();
                    for (int i = 0; i < sign.length; i++) {
                        model.addRow(new Object[]{"", ""});
                        tablaFija.setValueAt(sign[i],row,col);
                        col++;
                        tablaFija.setValueAt(compl_sign[i],row,col);
                        row++;
                        col--;
                    }     
                break;
            default:
                System.out.println("hello");
        }
    }
    
    public void cleanTable(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        revalidate();
    }
    
    private void seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("table.png")));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        tablaFija = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tabla Fija");
        setResizable(false);

        tablaFija.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tablaFija.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaFija.setRowHeight(22);
        tablaFija.setRowSelectionAllowed(false);
        tablaFija.getTableHeader().setResizingAllowed(false);
        tablaFija.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tablaFija);

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Palabras reservadas", "Operadores", "Signos" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        cambiarTabla(jComboBox1.getSelectedIndex());
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(TableFija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TableFija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TableFija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TableFija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TableFija().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable tablaFija;
    // End of variables declaration//GEN-END:variables
}
