/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.awt.Color;
import java.sql.*;
import java.util.Random;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.awt.Graphics;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


public class Perfil extends javax.swing.JFrame {
    private String usuario;
    void setUser(String u){
        this.usuario = u;
    }
    public String getUser(){
        return this.usuario;
    }
    public int getRandomNumberUsingNextInt(int min, int max) {
    Random random = new Random();
    return random.nextInt(max - min) + min;
}
    /**
     * Creates new form Perfil
     */
    public void checarAdmin(){
        try{
            Connection c = DBCConnection.getConnection();
            PreparedStatement pst = c.prepareStatement("select * from cachorro_clientes where nome = ? and admin = true");
            pst.setString(1, usuario);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                adminFeedBut.setVisible(true);
                manterUsuariosBut.setVisible(true);
            }else{
                adminFeedBut.setVisible(false);
                manterUsuariosBut.setVisible(false);
            }
                
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Perfil() {
        initComponents();
        compraTabelaPanel.setVisible(false);
        contraPanel.setVisible(false);
        this.setLocationRelativeTo(null);
        pieChartPanel.setVisible(false);
        
    }
    public void criarGrafico(){
        try{
        DefaultPieDataset pie = new DefaultPieDataset();
        Connection c = DBCConnection.getConnection();
        PreparedStatement ps1 = c.prepareStatement("select SUM(quantLGTV) from cachorro_compras where nome = ?");
        ps1.setString(1, usuario);
        ResultSet rs1 = ps1.executeQuery();
        if(rs1.next()){
             pie.setValue("LGTV?", rs1.getInt(1));
        }
        PreparedStatement ps2 = c.prepareStatement("select SUM(quantOiCadela) from cachorro_compras where nome = ?");
        ps2.setString(1, usuario);
        ResultSet rs2 = ps2.executeQuery();
        if(rs2.next()){
            pie.setValue("Oi Cadela", rs2.getInt(1));
        }
        PreparedStatement ps3 = c.prepareStatement("select SUM(quantMedo) from cachorro_compras where nome = ?");
        ps3.setString(1, usuario);
        ResultSet rs3 = ps3.executeQuery();
        if(rs3.next()){
            pie.setValue("Ele Tem Medo De Curitiba", rs3.getInt(1));
        }
        PreparedStatement ps4 = c.prepareStatement("select SUM(quantSMT) from cachorro_compras where nome = ?");
        ps4.setString(1, usuario);
        ResultSet rs4 = ps4.executeQuery();
        if(rs4.next()){
        pie.setValue("Se Mata", rs4.getInt(1));
        }
        PreparedStatement ps5 = c.prepareStatement("select SUM(quantNsei) from cachorro_compras where nome = ?");
        ps5.setString(1, usuario);
        ResultSet rs5 = ps5.executeQuery();
        if(rs5.next()){
            pie.setValue("N sei se comento", rs5.getInt(1));
        }
        PreparedStatement ps6 = c.prepareStatement("select SUM(quantPiorqe) from cachorro_compras where nome = ?");
        ps6.setString(1, usuario);
        ResultSet rs6 = ps6.executeQuery();
        if(rs6.next()){
            pie.setValue("Pior que e", rs6.getInt(1));
        }
        
        JFreeChart graf = ChartFactory.createPieChart("Cachorros mais comprados", pie,true,true,false);
        ChartPanel pan = new ChartPanel(graf);
        JFrame jr = new JFrame();
        jr.add(pan);
        jr.setVisible(true);
        jr.setSize(500,700);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void atualizarPerfilInfo(){
        if(usuario.equals("Beatriz")){
            nomePanel.setBackground(Color.pink);
        }else{
            nomePanel.setBackground(Color.green);
        }
        try{
            Connection c = DBCConnection.getConnection();
            PreparedStatement pst = c.prepareStatement("select * from cachorro_clientes where nome = ?");
            pst.setString(1, usuario);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
            labelComONome.setText(rs.getString("nome"));
            senhaTextField.setText(rs.getString("senha"));
            signoTextField.setText(rs.getString("signo"));
            deusaTextField.setText(rs.getString("deusa"));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    void atualizarTabelaCompras(){
        try{
        Connection con = DBCConnection.getConnection();
        PreparedStatement pst = con.prepareStatement("select * from cachorro_compras where nome = ?");
        pst.setString(1, usuario);
        ResultSet rs = pst.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int n = rsmd.getColumnCount();
        DefaultTableModel dtm = (DefaultTableModel) comprasTable.getModel();
        dtm.setRowCount(0);
        while(rs.next()){
            Vector v = new Vector();
            for(int i = 1; i<=n;i++ ){
                v.add(rs.getString("nome"));
                v.add(rs.getInt("quantLGTV"));
                v.add(rs.getInt("quantOiCadela"));
                v.add(rs.getInt("quantMedo"));
                v.add(rs.getInt("quantSMT"));
                v.add(rs.getInt("quantNsei"));
                v.add(rs.getInt("quantPiorqe"));
            }
            dtm.addRow(v);
        }
        
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    void atualizarTabelaContra(){
        try{
        Connection con = DBCConnection.getConnection();
        PreparedStatement pst = con.prepareStatement("select * from cachorro_contrabandos where nome = ?");
        pst.setString(1, usuario);
        ResultSet rs = pst.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int n = rsmd.getColumnCount();
        DefaultTableModel dtm = (DefaultTableModel) contraTable.getModel();
        dtm.setRowCount(0);
        while(rs.next()){
            Vector v = new Vector();
            for(int i = 1; i<=n;i++ ){
                v.add(rs.getString("nome"));
                v.add(rs.getString("cachorroContrabandeado"));
            }
            dtm.addRow(v);
        }
        
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        verGraficoBut = new javax.swing.JButton();
        adminFeedBut = new javax.swing.JButton();
        reclamacaoBut = new javax.swing.JButton();
        pieChartPanel = new javax.swing.JPanel();
        contraPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        contraTable = new javax.swing.JTable();
        compraTabelaPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        comprasTable = new javax.swing.JTable();
        closeLabel = new javax.swing.JLabel();
        voltarPanel = new javax.swing.JPanel();
        voltarLabel = new javax.swing.JLabel();
        histContrabandos = new javax.swing.JButton();
        histCompras = new javax.swing.JButton();
        atualizarBut = new javax.swing.JButton();
        signoTextField = new javax.swing.JTextField();
        signoLabel = new javax.swing.JLabel();
        deusaTextField = new javax.swing.JTextField();
        deusaLabel = new javax.swing.JLabel();
        senhaTextField = new javax.swing.JTextField();
        senhaLabel = new javax.swing.JLabel();
        nomePanel = new javax.swing.JPanel();
        labelComONome = new javax.swing.JLabel();
        labelNome = new javax.swing.JLabel();
        manterUsuariosBut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(950, 700));
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(950, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        background.setBackground(new java.awt.Color(204, 204, 255));
        background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        verGraficoBut.setText("Ver grafico");
        verGraficoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verGraficoButActionPerformed(evt);
            }
        });
        background.add(verGraficoBut, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        adminFeedBut.setText("Ver feedback");
        adminFeedBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminFeedButActionPerformed(evt);
            }
        });
        background.add(adminFeedBut, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 550, -1, -1));

        reclamacaoBut.setText("Dar feedback (SAC)");
        reclamacaoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reclamacaoButActionPerformed(evt);
            }
        });
        background.add(reclamacaoBut, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 520, -1, -1));

        pieChartPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        background.add(pieChartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 570, 510));

        contraPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contraTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "CachorroContrabandeado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(contraTable);

        contraPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 490));

        background.add(contraPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 570, 500));

        compraTabelaPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        comprasTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "LGTV", "OiCadela", "MedoDeCuritiba", "SeMata", "NSeiSeComento", "PiorQueE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(comprasTable);

        compraTabelaPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 7, 550, 470));

        background.add(compraTabelaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 560, 500));

        closeLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        closeLabel.setText(" X");
        closeLabel.setMaximumSize(new java.awt.Dimension(30, 33));
        closeLabel.setMinimumSize(new java.awt.Dimension(30, 33));
        closeLabel.setPreferredSize(new java.awt.Dimension(30, 33));
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
        });
        background.add(closeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 0, -1, -1));

        voltarLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        voltarLabel.setText("          VOLTAR");
        voltarLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                voltarLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                voltarLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                voltarLabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout voltarPanelLayout = new javax.swing.GroupLayout(voltarPanel);
        voltarPanel.setLayout(voltarPanelLayout);
        voltarPanelLayout.setHorizontalGroup(
            voltarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(voltarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        voltarPanelLayout.setVerticalGroup(
            voltarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(voltarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        background.add(voltarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 50));

        histContrabandos.setText("Ver historico de contrabandos");
        histContrabandos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                histContrabandosActionPerformed(evt);
            }
        });
        background.add(histContrabandos, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 480, -1, -1));

        histCompras.setText("Ver historico de compras");
        histCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                histComprasActionPerformed(evt);
            }
        });
        background.add(histCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 450, -1, -1));

        atualizarBut.setText("Atualizar informacoes");
        atualizarBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarButActionPerformed(evt);
            }
        });
        background.add(atualizarBut, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, -1, -1));
        background.add(signoTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 150, -1));

        signoLabel.setText("Signo");
        background.add(signoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, -1));
        background.add(deusaTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 160, -1));

        deusaLabel.setText("Deusa");
        background.add(deusaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 50, -1));
        background.add(senhaTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 150, -1));

        senhaLabel.setText("Senha");
        background.add(senhaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 146, 50, 20));

        nomePanel.setBackground(new java.awt.Color(255, 255, 255));

        labelComONome.setFont(new java.awt.Font("Sitka Heading", 1, 14)); // NOI18N

        javax.swing.GroupLayout nomePanelLayout = new javax.swing.GroupLayout(nomePanel);
        nomePanel.setLayout(nomePanelLayout);
        nomePanelLayout.setHorizontalGroup(
            nomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nomePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelComONome, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        nomePanelLayout.setVerticalGroup(
            nomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nomePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelComONome, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                .addContainerGap())
        );

        background.add(nomePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 180, 30));

        labelNome.setText("Nome");
        background.add(labelNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 78, 70, 20));

        manterUsuariosBut.setText("Manter Usuarios");
        manterUsuariosBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manterUsuariosButActionPerformed(evt);
            }
        });
        background.add(manterUsuariosBut, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 550, -1, -1));

        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 800));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void atualizarButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarButActionPerformed
        try{
            Connection c = DBCConnection.getConnection();
            PreparedStatement pst = c.prepareStatement("update cachorro_clientes set senha = ?, signo = ?, deusa = ? where nome = ?");
            pst.setString(1, senhaTextField.getText());
            pst.setString(2, signoTextField.getText());
            pst.setString(3, deusaTextField.getText());
            pst.setString(4, labelComONome.getText());
            pst.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_atualizarButActionPerformed

    private void voltarLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voltarLabelMouseEntered
        int n1, n2, n3;
        n1 = getRandomNumberUsingNextInt(0,255);
        n2 = getRandomNumberUsingNextInt(0,255);
        n3 = getRandomNumberUsingNextInt(0,255);
        Color roberto = new Color(n1,n2,n3);
        voltarPanel.setBackground(roberto);
    }//GEN-LAST:event_voltarLabelMouseEntered

    private void voltarLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voltarLabelMouseExited
        int n1,n2,n3;
        n1 = 242;
        n2 = 242;
        n3 = 242;
        Color cor = new Color(n1,n2,n3);
        
        voltarPanel.setBackground(cor);
    }//GEN-LAST:event_voltarLabelMouseExited

    private void voltarLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voltarLabelMouseClicked
        PaginaInicial pag = new PaginaInicial();
        pag.setUsuario(usuario);
        pag.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_voltarLabelMouseClicked

    private void histComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_histComprasActionPerformed
        contraTable.setVisible(false);
        comprasTable.setVisible(true);
        compraTabelaPanel.setVisible(true);
        contraPanel.setVisible(false);
        atualizarTabelaCompras();
    }//GEN-LAST:event_histComprasActionPerformed

    private void histContrabandosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_histContrabandosActionPerformed
        comprasTable.setVisible(false);
        contraTable.setVisible(true);
        compraTabelaPanel.setVisible(false);
        contraPanel.setVisible(true);
        atualizarTabelaContra();
    }//GEN-LAST:event_histContrabandosActionPerformed

    private void adminFeedButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminFeedButActionPerformed
       SACAdmin adm = new SACAdmin();
       adm.setVisible(true);
       adm.setUser(usuario);
       adm.atualizarTabelaFeedbacks();
    }//GEN-LAST:event_adminFeedButActionPerformed

    private void reclamacaoButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reclamacaoButActionPerformed
        SACCliente sc = new SACCliente();
        sc.setUser(this.getUser());
        sc.setVisible(true);
        
    }//GEN-LAST:event_reclamacaoButActionPerformed

    private void verGraficoButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verGraficoButActionPerformed
        compraTabelaPanel.setVisible(false);
        contraPanel.setVisible(false);
        pieChartPanel.setVisible(false);
        
        criarGrafico();
                
    }//GEN-LAST:event_verGraficoButActionPerformed

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeLabelMouseClicked

    private void manterUsuariosButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manterUsuariosButActionPerformed
        Manutencao m = new Manutencao();
        m.setUser(usuario);
        m.setVisible(true);
        m.atualizarTabela();
        this.dispose();
    }//GEN-LAST:event_manterUsuariosButActionPerformed
    
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
            java.util.logging.Logger.getLogger(Perfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Perfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Perfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Perfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Perfil().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminFeedBut;
    private javax.swing.JButton atualizarBut;
    private javax.swing.JPanel background;
    private javax.swing.JLabel closeLabel;
    private javax.swing.JPanel compraTabelaPanel;
    private javax.swing.JTable comprasTable;
    private javax.swing.JPanel contraPanel;
    private javax.swing.JTable contraTable;
    private javax.swing.JLabel deusaLabel;
    private javax.swing.JTextField deusaTextField;
    private javax.swing.JButton histCompras;
    private javax.swing.JButton histContrabandos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelComONome;
    private javax.swing.JLabel labelNome;
    private javax.swing.JButton manterUsuariosBut;
    private javax.swing.JPanel nomePanel;
    private javax.swing.JPanel pieChartPanel;
    private javax.swing.JButton reclamacaoBut;
    private javax.swing.JLabel senhaLabel;
    private javax.swing.JTextField senhaTextField;
    private javax.swing.JLabel signoLabel;
    private javax.swing.JTextField signoTextField;
    private javax.swing.JButton verGraficoBut;
    private javax.swing.JLabel voltarLabel;
    private javax.swing.JPanel voltarPanel;
    // End of variables declaration//GEN-END:variables
}
