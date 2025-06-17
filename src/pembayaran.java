

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import komponen.TableActionCellEditor;
import komponen.TableActionCellRender;
import komponen.TableActionEvent;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class pembayaran extends javax.swing.JInternalFrame {

    /**
     * Creates new form dashboardu
     */
    
    Double bHeight=0.0;
    ArrayList<String> nokamar= new ArrayList<>();
    
    public PageFormat getPageFormat(PrinterJob pj){
    
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();    

        double bodyHeight = bHeight;  
        double headerHeight = 5.0;                  
        double footerHeight = 5.0;        
        double width = cm_to_pp(8); 
        double height = cm_to_pp(headerHeight+bodyHeight+footerHeight); 
        paper.setSize(width, height);
        paper.setImageableArea(0,10,width,height - cm_to_pp(1));  
            
        pf.setOrientation(PageFormat.PORTRAIT);  
        pf.setPaper(paper);    

        return pf;
    }
    
    public class BillPrintable implements Printable {
        public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) 
        throws PrinterException {    
      
            int r= nokamar.size();
            int result = NO_SUCH_PAGE;    
            if (pageIndex == 0) {                    
        
                Graphics2D g2d = (Graphics2D) graphics;                    
                double width = pageFormat.getImageableWidth();                               
                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 



          //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
        
        try{
            int y=20;
            int yShift = 10;
            int headerRectHeight=15;
           // int headerRectHeighta=40;
            
                
            g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
            g2d.drawString("-------------------------------------",12,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("             SYIFA HAURA             ",12,y);y+=yShift;
            g2d.drawString("             JALAN TIDAR             ",12,y);y+=yShift;
            g2d.drawString("             082244866126            ",12,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",12,y);y+=headerRectHeight;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString(" Nama : "+txtnamadetail.getText()+"   ",10,y);y+=yShift;
            g2d.drawString(" Penghuni Kamar No."+txtnokamardetail.getText()+"    ",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString(" Bulan:                    "+txtbulandetail.getText()+"   ",10,y);y+=yShift;
            g2d.drawString(" Tahun:                    "+txttahundetail.getText()+"   ",10,y);y+=yShift;
            g2d.drawString(" Nominal:               RP."+txtnominaldetail.getText()+"   ",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString("Tgl Pembayaran :  "+txttanggaldetail.getText()+"   ",10,y);y+=yShift;
            g2d.drawString("*************************************",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("            TERIMA KASIH             ",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("*************************************",10,y);y+=yShift;
      
           

    }
    catch(Exception e){
    e.printStackTrace();
    }

              result = PAGE_EXISTS;    
          }    
          return result;    
      }
   }
    
    protected static double cm_to_pp(double cm)
    {            
	        return toPPI(cm * 0.393600787);            
    }
 
    protected static double toPPI(double inch)
    {            
	        return inch * 72d;            
    }
    
    public pembayaran() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        load_tabletransaksilunas();
        load_tagihan();
        reset();
    }
    
    private void reset(){
        txtNoKamar1.setText(null);
        txtNIK1.setText(null);
        txtNominal1.setText(null);
        cbBulan.setSelectedItem(this);
        cbTahun.setSelectedItem("2023");
    }
    
    private void detailkosong(){
        txtnamadetail.setText(null);
        txtnokamardetail.setText(null);
        txtbulandetail.setText(null);
        txttahundetail.setText(null);
        txtnominaldetail.setText(null);
        txttanggaldetail.setText(null);
    }
    
    private void load_tabletransaksilunas(){
        //membuat tampilan model tabel
        
        DefaultTableModel model = new DefaultTableModel(){
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        
        model.addColumn("Tanggal");
        model.addColumn("No Kamar");
        model.addColumn("Nama Penghuni");
        model.addColumn("Bulan");
        model.addColumn("Tahun");
        model.addColumn("Nominal");
        model.addColumn("Aksi");
        //menampilkan data database kedalam table
        try {
            String sql = "SELECT * FROM pembayaran JOIN penghuni ON pembayaran.NIK=penghuni.NIK";
            java.sql.Connection conn=(Connection) Config.configDB();
            java.sql.PreparedStatement stm = conn.prepareStatement(sql);
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{ 
                res.getString("tgl_pembayaran"),
                res.getString("no_kamar"), 
                res.getString("Nama"),
                res.getString("bulan"),
                res.getString("tahun"),
                res.getString("harga")});
            }
            tbl_transaksi.setModel(model);
        } catch (Exception e) {
        }
        tbl_transaksi.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onDetail(int row) {
                txttanggaldetail.setText(tbl_transaksi.getValueAt(row, 0).toString());
                txtnokamardetail.setText(tbl_transaksi.getValueAt(row, 1).toString());
                txtnamadetail.setText(tbl_transaksi.getValueAt(row, 2).toString());
                txtbulandetail.setText(tbl_transaksi.getValueAt(row, 3).toString());
                txttahundetail.setText(tbl_transaksi.getValueAt(row, 4).toString());
                txtnominaldetail.setText(tbl_transaksi.getValueAt(row, 5).toString());
                jTabbedPane1.setSelectedIndex(2);
            }
        };
        tbl_transaksi.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
    }
    

    private void load_tagihan(){
        //membuat tampilan model tabel
        DefaultTableModel model = new DefaultTableModel(){
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        model.addColumn("No Kamar");
        model.addColumn("Harga");
        model.addColumn("NIK");
        model.addColumn("Nama");
        model.addColumn("No HP");
        
        //menampilkan data database kedalam table
        try {
            String sql = "SELECT * FROM penghuni JOIN kamar ON penghuni.no_kamar=kamar.no_kamar JOIN tagihan ON tagihan.no_kamar=penghuni.no_kamar";
            java.sql.Connection conn=(Connection) Config.configDB();
            java.sql.PreparedStatement stm = conn.prepareStatement(sql);
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{ 
                res.getString("no_kamar"),
                res.getString("harga"), 
                res.getString("NIK"),
                res.getString("Nama"),
                res.getString("No_Handphone")});
            }
            tbltagihan.setModel(model);
        } catch (Exception e) {
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

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Tagihan = new javax.swing.JPanel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel25 = new javax.swing.JLabel();
        txtcaritagihan = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbltagihan = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNoKamar1 = new javax.swing.JTextField();
        txtNominal1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        cbTahun = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        cbBulan = new javax.swing.JComboBox<>();
        txtNIK1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        btnbayar = new javax.swing.JButton();
        Lunas = new javax.swing.JPanel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel17 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtcarilunas = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_transaksi = new javax.swing.JTable();
        Detail = new javax.swing.JPanel();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txttanggaldetail = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtnominaldetail = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtnokamardetail = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtbulandetail = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtnamadetail = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txttahundetail = new javax.swing.JTextField();
        btn_cancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btn_cetak = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable2);

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(780, 450));

        Tagihan.setBackground(new java.awt.Color(174, 226, 255));

        kGradientPanel1.setkEndColor(new java.awt.Color(174, 226, 255));
        kGradientPanel1.setkStartColor(new java.awt.Color(255, 204, 204));

        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel25.setText("Data Tagihan");

        txtcaritagihan.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtcaritagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcaritagihanActionPerformed(evt);
            }
        });
        txtcaritagihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcaritagihanKeyReleased(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-search-25.png"))); // NOI18N

        tbltagihan.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tbltagihan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbltagihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbltagihanMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbltagihan);

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel11.setText("Nominal");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel8.setText("No Kamar");

        txtNoKamar1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txtNominal1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel28.setText("Tagihan Tahun");

        cbTahun.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbTahun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043", "2044", "2045", "2046", "2047", "2048", "2049", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099", "2100", "kosong" }));
        cbTahun.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTahunItemStateChanged(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel27.setText("Tagihan Bulan");

        cbBulan.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbBulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember", "kosong" }));
        cbBulan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbBulanItemStateChanged(evt);
            }
        });

        txtNIK1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel26.setText("NIK");

        jButton2.setBackground(new java.awt.Color(102, 153, 255));
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-reset-25.png"))); // NOI18N
        jButton2.setText("RESET");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnbayar.setBackground(new java.awt.Color(0, 204, 204));
        btnbayar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnbayar.setForeground(new java.awt.Color(255, 255, 255));
        btnbayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-cash-25.png"))); // NOI18N
        btnbayar.setText("BAYAR");
        btnbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbayarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtNoKamar1)
                        .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtNIK1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addGap(46, 46, 46)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addContainerGap(474, Short.MAX_VALUE))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(cbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53)
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(txtNominal1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addComponent(cbTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)))
                        .addGap(18, 18, 18)
                        .addComponent(btnbayar)
                        .addGap(28, 28, 28))))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(156, Short.MAX_VALUE))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcaritagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(460, Short.MAX_VALUE))))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel25)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtcaritagihan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel27))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(txtNoKamar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel28)
                                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtNIK1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                        .addComponent(txtNominal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(45, 45, 45)))))
                        .addContainerGap(57, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnbayar)
                            .addComponent(jButton2))
                        .addGap(36, 36, 36))))
        );

        javax.swing.GroupLayout TagihanLayout = new javax.swing.GroupLayout(Tagihan);
        Tagihan.setLayout(TagihanLayout);
        TagihanLayout.setHorizontalGroup(
            TagihanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        TagihanLayout.setVerticalGroup(
            TagihanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TagihanLayout.createSequentialGroup()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tagihan", Tagihan);

        Lunas.setBackground(new java.awt.Color(174, 226, 255));

        kGradientPanel2.setkEndColor(new java.awt.Color(174, 226, 255));
        kGradientPanel2.setkStartColor(new java.awt.Color(255, 204, 204));

        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel17.setText("Lunas");

        jButton1.setBackground(new java.awt.Color(153, 153, 255));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-reset-25.png"))); // NOI18N
        jButton1.setText("REFRESH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtcarilunas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcarilunasActionPerformed(evt);
            }
        });
        txtcarilunas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcarilunasKeyReleased(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-search-25.png"))); // NOI18N

        tbl_transaksi.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tbl_transaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tanggal", "No Kamar", "Nama", "Tagihan", "Bulan", "Tahun", "Aksi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_transaksi.setRowHeight(30);
        jScrollPane2.setViewportView(tbl_transaksi);

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(txtcarilunas, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel17)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtcarilunas))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout LunasLayout = new javax.swing.GroupLayout(Lunas);
        Lunas.setLayout(LunasLayout);
        LunasLayout.setHorizontalGroup(
            LunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        LunasLayout.setVerticalGroup(
            LunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Lunas", Lunas);

        Detail.setBackground(new java.awt.Color(174, 226, 255));

        kGradientPanel3.setkEndColor(new java.awt.Color(174, 226, 255));
        kGradientPanel3.setkStartColor(new java.awt.Color(255, 204, 204));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel18.setText("Detail Pembayaran");

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel19.setText("Tanggal");

        txttanggaldetail.setEditable(false);
        txttanggaldetail.setBackground(new java.awt.Color(255, 255, 255));
        txttanggaldetail.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txttanggaldetail.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txttanggaldetail.setEnabled(false);

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel22.setText("Nominal");

        txtnominaldetail.setEditable(false);
        txtnominaldetail.setBackground(new java.awt.Color(255, 255, 255));
        txtnominaldetail.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtnominaldetail.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtnominaldetail.setEnabled(false);

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel20.setText("No Kamar");

        txtnokamardetail.setEditable(false);
        txtnokamardetail.setBackground(new java.awt.Color(255, 255, 255));
        txtnokamardetail.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtnokamardetail.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtnokamardetail.setEnabled(false);

        jLabel23.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel23.setText("Tagihan Bulan");

        txtbulandetail.setEditable(false);
        txtbulandetail.setBackground(new java.awt.Color(255, 255, 255));
        txtbulandetail.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtbulandetail.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtbulandetail.setEnabled(false);

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel21.setText("Nama");

        txtnamadetail.setEditable(false);
        txtnamadetail.setBackground(new java.awt.Color(255, 255, 255));
        txtnamadetail.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtnamadetail.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtnamadetail.setEnabled(false);

        jLabel24.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel24.setText("Tagihan Tahun");

        txttahundetail.setEditable(false);
        txttahundetail.setBackground(new java.awt.Color(255, 255, 255));
        txttahundetail.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txttahundetail.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txttahundetail.setEnabled(false);

        btn_cancel.setBackground(new java.awt.Color(51, 102, 255));
        btn_cancel.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_cancel.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancel.setText("CANCEL");
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/undraw_No_data_re_kwbl-removebg-preview (1).png"))); // NOI18N

        btn_cetak.setBackground(new java.awt.Color(51, 102, 255));
        btn_cetak.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_cetak.setForeground(new java.awt.Color(255, 255, 255));
        btn_cetak.setText("CETAK");
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel3Layout.createSequentialGroup()
                        .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtnokamardetail, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(kGradientPanel3Layout.createSequentialGroup()
                                    .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txttanggaldetail, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel20)
                                        .addComponent(jLabel21)
                                        .addComponent(txtnamadetail, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel19))
                                    .addGap(109, 109, 109)
                                    .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel22)
                                        .addComponent(txttahundetail, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel23)
                                        .addComponent(txtnominaldetail, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtbulandetail, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel24))))
                            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel18)
                                .addGap(299, 299, 299)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel3Layout.createSequentialGroup()
                        .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133)))
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel18)
                .addGap(46, 46, 46)
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttanggaldetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnominaldetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel23))
                .addGap(22, 22, 22)
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnokamardetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbulandetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnamadetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttahundetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout DetailLayout = new javax.swing.GroupLayout(Detail);
        Detail.setLayout(DetailLayout);
        DetailLayout.setHorizontalGroup(
            DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DetailLayout.setVerticalGroup(
            DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Detail", Detail);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane1)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcaritagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcaritagihanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcaritagihanActionPerformed

    private void txtcarilunasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcarilunasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcarilunasActionPerformed

    private void btnbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbayarActionPerformed
        // TODO add your handling code here:
        String no_kamar = txtNoKamar1.getText();
        String nik = txtNIK1.getText();
        String nominal = txtNominal1.getText();
        String bln_bayar = cbBulan.getSelectedItem().toString();
        String thn_bayar = cbTahun.getSelectedItem().toString();

        try{
            String sql = "SELECT * FROM pembayaran WHERE no_kamar='"+no_kamar+"' AND bulan='"+bln_bayar+"' AND tahun='"+thn_bayar+"'";
            java.sql.Connection conn=(Connection) Config.configDB();
            java.sql.PreparedStatement stm = conn.prepareStatement(sql);
            java.sql.ResultSet rs = stm.executeQuery(sql);
            String sqll = "SELECT * FROM pembayaran JOIN penghuni ON pembayaran.NIK=penghuni.NIK";
            java.sql.PreparedStatement stmm = conn.prepareStatement(sqll);
            java.sql.ResultSet res = stmm.executeQuery(sqll);


            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Gagal : Transaksi ini sudah dilakukan!");
                
            } else {
                conn.prepareStatement(sql).executeUpdate("INSERT INTO pembayaran "
                        + "(NIK, no_kamar, harga, bulan, tahun) VALUES "
                        + "('"+nik+"','"+no_kamar+"','"+nominal+"','"+bln_bayar+"','"+thn_bayar+"')");
                int yes_no=JOptionPane.showConfirmDialog(this, "Pembayaran Berhasil Dilakukan Apakah Anda Ingin Mencetak Struk ?", 
                        "Berhasil", JOptionPane.YES_NO_OPTION);
                if (yes_no==JOptionPane.YES_OPTION){
                    res.next();
                    txtnokamardetail.setText(no_kamar);
                    txtnamadetail.setText(res.getString("Nama"));
                    txttanggaldetail.setText(res.getString("tgl_pembayaran"));
                    txtnominaldetail.setText(nominal);
                    txtbulandetail.setText(bln_bayar);
                    txttahundetail.setText(thn_bayar);
                    btn_cetakActionPerformed(evt);
                    detailkosong();
                    
                }; 
                jTabbedPane1.setSelectedIndex(1);
                reset();    
            }
        }catch (Exception ex) {
            System.out.println(""+ex);
            JOptionPane.showMessageDialog(null, "Gagal!");
        }
        load_tabletransaksilunas();
        load_tagihan();
        
    }//GEN-LAST:event_btnbayarActionPerformed

    private void tbltagihanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltagihanMouseClicked
        // TODO add your handling code here:
        int baris = tbltagihan.rowAtPoint(evt.getPoint());
        String nokamar = tbltagihan.getValueAt(baris,0).toString();
        txtNoKamar1.setText(nokamar);
        String nik = tbltagihan.getValueAt(baris,2).toString();
        txtNIK1.setText(nik);
        String nominal = tbltagihan.getValueAt(baris,1).toString();
        txtNominal1.setText(nominal);
    }//GEN-LAST:event_tbltagihanMouseClicked

    private void txtcaritagihanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcaritagihanKeyReleased
        // TODO add your handling code here:
        DefaultTableModel objj = (DefaultTableModel) tbltagihan.getModel();
        TableRowSorter<DefaultTableModel> objl1 =new TableRowSorter<>(objj);
        tbltagihan.setRowSorter(objl1);
        objl1.setRowFilter(RowFilter.regexFilter(txtcaritagihan.getText()));
    }//GEN-LAST:event_txtcaritagihanKeyReleased

    private void txtcarilunasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcarilunasKeyReleased
        // TODO add your handling code here:
        DefaultTableModel obj = (DefaultTableModel) tbl_transaksi.getModel();
        TableRowSorter<DefaultTableModel> obj1 =new TableRowSorter<>(obj);
        tbl_transaksi.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(txtcarilunas.getText()));
    }//GEN-LAST:event_txtcarilunasKeyReleased

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        // TODO add your handling code here:
        reset();
        detailkosong();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        load_tabletransaksilunas();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbTahunItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTahunItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cbTahunItemStateChanged

    private void cbBulanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbBulanItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBulanItemStateChanged

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
        // TODO add your handling code here:
        bHeight = Double.valueOf(nokamar.size());
        //JOptionPane.showMessageDialog(rootPane, bHeight);
        
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try {
             pj.print();
          
        }
         catch (PrinterException ex) {
                 ex.printStackTrace();
        }
    }//GEN-LAST:event_btn_cetakActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Detail;
    private javax.swing.JPanel Lunas;
    private javax.swing.JPanel Tagihan;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btnbayar;
    private javax.swing.JComboBox<String> cbBulan;
    private javax.swing.JComboBox<String> cbTahun;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private javax.swing.JTable tbl_transaksi;
    private javax.swing.JTable tbltagihan;
    private javax.swing.JTextField txtNIK1;
    private javax.swing.JTextField txtNoKamar1;
    private javax.swing.JTextField txtNominal1;
    private javax.swing.JTextField txtbulandetail;
    private javax.swing.JTextField txtcarilunas;
    private javax.swing.JTextField txtcaritagihan;
    private javax.swing.JTextField txtnamadetail;
    private javax.swing.JTextField txtnokamardetail;
    private javax.swing.JTextField txtnominaldetail;
    private javax.swing.JTextField txttahundetail;
    private javax.swing.JTextField txttanggaldetail;
    // End of variables declaration//GEN-END:variables
}