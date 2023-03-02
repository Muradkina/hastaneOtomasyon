package wiev;


import helper.Helper;
import helper.Item;
import model.BasHekim;
import model.Klinik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class BasHekimGUI extends JFrame {

    private JPanel bHelimForm;
    private JLabel hosgeldinizYazi;
    private JButton cikis_btn;
    private JTabbedPane tabbePane;
    private JLabel drAdSoyad;
    private JPanel doktorPanal;
    private JTextField adSoyadInput;
    private JLabel tcLabel;
    private JTextField tcInput;
    private JLabel sifreLabel;
    private JPasswordField sifreInput;
    private JButton ekleButton;
    private JLabel kullaniciID;
    private JTextField kullaniciIDInput;
    private JButton silBtn;
    private JScrollPane doktorPanelScroll;
    private JTable doktorTable;
    private JPanel klinikPanel;
    private JLabel pAdLabel;
    private JTextField pAdInput;
    private JButton ekleBtn;

    private JScrollPane solKlinikScroll;
    private JScrollPane sagKlinikScroll;

    private JTable solTableKlinik;
    private JTable calisanlarKlinikTable;

    private JButton secBtn;
    private JLabel poloAdLabel;
    private JTextField poloInput;
    private JComboBox comboBox1;
    private JButton ekleButtonCombo;

    private JPopupMenu klinikMenu;

    private BasHekim basHekim = new BasHekim();
    private Klinik klinik = new Klinik();


    private DefaultTableModel doctorModel = null;
    private Object[] doctorData = null;

    private DefaultTableModel klinikModel = null;
    private Object[] klinikData = null;


    public BasHekimGUI(BasHekim basHekim) throws SQLException {

        //Doktor Model
        this.doctorModel = new DefaultTableModel();
        Object[] colDoctorName = new Object[4];
        colDoctorName[0] = "ID";
        colDoctorName[1] = "Ad Soyad";
        colDoctorName[2] = "TC NO";
        colDoctorName[3] = "Şifre";
        // colDoctorName[4]="tipi";
        doctorModel.setColumnIdentifiers(colDoctorName);

        doctorData = new Object[4];
        for (int i = 0; i < basHekim.getDoctorList().size(); i++) {
            doctorData[0] = basHekim.getDoctorList().get(i).getId();
            doctorData[2] = basHekim.getDoctorList().get(i).getTckn();
            doctorData[1] = basHekim.getDoctorList().get(i).getName();
            doctorData[3] = basHekim.getDoctorList().get(i).getPassword();
            //doctorData[4]=basHekim.getDoctorList().get(i).getTipi();
            //modelın içine herdefasında girecek şekilde doctordatayı doctor modele attım
            doctorModel.addRow(doctorData);


        }

        //Klinik Model
        this.klinikModel = new DefaultTableModel();
        Object[] colKlinikName = new Object[2];
        colKlinikName[0] = "ID";
        colKlinikName[1] = "Poloklinik Adı";
        this.klinikModel.setColumnIdentifiers(colKlinikName);

        klinikData = new Object[2];
        for (int i = 0; i < this.klinik.getKlinikList().size(); i++) {
            klinikData[0] = this.klinik.getKlinikList().get(i).getId();
            klinikData[1] = this.klinik.getKlinikList().get(i).getName();
            klinikModel.addRow(klinikData);
        }

        //Calisanlar Model
        DefaultTableModel calisanlarModel = new DefaultTableModel();
        Object[] colCalisanlar = new Object[2];
        colCalisanlar[0] = "ID";
        colCalisanlar[1] = "AD SOYAD";
        calisanlarModel.setColumnIdentifiers(colCalisanlar);

        Object[] calisanlarData = new Object[2];
        // sol taraftaki Poloklinik bölümlerine tıklanıldığında Poloklinik alanında ki
        // seç butonuna basıldığında verilerin sağ tafaftaki calisanlar table sine aktarılmasını sağlayan kod
        secBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = solTableKlinik.getSelectedRow();
                if (selRow > 0) {
                    String selKlinik = solTableKlinik.getModel().getValueAt(selRow, 0).toString();
                    int selKlinikID = Integer.parseInt(selKlinik);
                    calisanTmizle();
                    try {
                        for (int i = 0; i < basHekim.getKlinikDoktorList(selKlinikID).size(); i++) {
                            calisanlarData[0] = basHekim.getKlinikDoktorList(selKlinikID).get(i).getId();
                            calisanlarData[1] = basHekim.getKlinikDoktorList(selKlinikID).get(i).getName();
                            calisanlarModel.addRow(calisanlarData);


                        }
                    } catch (SQLException es) {
                        es.printStackTrace();
                    }
                    calisanlarKlinikTable.setModel(calisanlarModel);

                } else {
                    Helper.showMesajGoster("Lütfen bir poloklinik seçiniz ");
                }
            }
        });


        add(bHelimForm);
        setResizable(false);
        setTitle("Hastane Yönetim Sistemi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 750, 500);
        bHelimForm.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(bHelimForm);
        bHelimForm.setLayout(null);

        //Hoşgeldiniz-Label
        JLabel hosgeldinizYazi = new JLabel("Hoşgeldizin Sayın" + " " + basHekim.getName());
        hosgeldinizYazi.setBounds(25, 10, 400, 25);
        hosgeldinizYazi.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        bHelimForm.add(hosgeldinizYazi);

        //Çıkış Yap Btn

        cikis_btn.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        cikis_btn.setBounds(615, 10, 100, 25);
        bHelimForm.add(cikis_btn);

        tabbePane.setBounds(20, 70, 700, 382);
        bHelimForm.add(tabbePane);

        //Doktor Yönetimi
        doktorPanal.setBackground(Color.WHITE);
        tabbePane.addTab("Doktor Yönetimi", null, doktorPanal, null);
        doktorPanal.setLayout(null);

        //Poloklinikler
        klinikPanel.setBackground(Color.white);
        tabbePane.addTab("Poloklinikler", null, klinikPanel, null);
        klinikPanel.setLayout(null);

        //Poloklinik Adı Label
        pAdLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        pAdLabel.setBounds(260, 10, 180, 55);
        klinikPanel.add(pAdInput);

        //Poloklinik Adı Input
        pAdInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
        pAdInput.setBounds(260, 60, 180, 30);
        klinikPanel.add(pAdInput);
        pAdInput.setColumns(10);

        //Ekle Button
        ekleBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
        ekleBtn.setBounds(260, 100, 180, 30);
        klinikPanel.add(ekleBtn);
        ekleBtn.setBackground(Color.cyan);

        ekleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pAdInput.getText().length() == 0) {
                    Helper.showMesajGoster("fiil");
                } else {
                    try {
                        if (BasHekimGUI.this.klinik.addKlink(pAdInput.getText())) {
                            Helper.showMesajGoster("succes");
                            pAdInput.setText(null);
                            updateKlinikModel();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //Poloklinik Adı Label
        poloAdLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        poloAdLabel.setBounds(260, 140, 180, 55);
        klinikPanel.add(poloAdLabel);

        //Poloklinik Seç Button
        secBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        secBtn.setBounds(260, 180, 180, 30);
        klinikPanel.add(secBtn);
        secBtn.setBackground(Color.cyan);


        //ComboBox
        comboBox1.setBounds(260, 260, 180, 30);
        for (int i = 0; i < basHekim.getDoctorList().size(); i++) {
            comboBox1.addItem(new Item(basHekim.getDoctorList().get(i).getId(), basHekim.getDoctorList().get(i).getName()));
        }
        comboBox1.addActionListener(e -> {
            JComboBox com = (JComboBox) e.getSource();
            Item item = (Item) com.getSelectedItem();
            System.out.println(item.getKey() + " : " + item.getValue());
        });
        klinikPanel.add(comboBox1);


        //Combo Ekle Button
        ekleButtonCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
        ekleButtonCombo.setBounds(260, 300, 180, 30);

        ekleButtonCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //tablodan değer semme varmı
                int selectRow = solTableKlinik.getSelectedRow();
                if (selectRow >= 0) {
                    String selectKlinik = solTableKlinik.getModel().getValueAt(selectRow, 0).toString();
                    int selectKlinikID = Integer.parseInt(selectKlinik);
                    Item doctorItem = (Item) comboBox1.getSelectedItem();
                    try {
                        boolean kntl = basHekim.addCalisan(doctorItem.getKey(), selectKlinikID);
                        if (kntl) {
                            calisanTmizle();
                            for (int i = 0; i < basHekim.getKlinikDoktorList(selectKlinikID).size(); i++) {
                                calisanlarData[0] = basHekim.getKlinikDoktorList(selectKlinikID).get(i).getId();
                                calisanlarData[1] = basHekim.getKlinikDoktorList(selectKlinikID).get(i).getName();
                                calisanlarModel.addRow(calisanlarData);
                            }
                            calisanlarKlinikTable.setModel(calisanlarModel);

                            Helper.showMesajGoster("succes");
                        } else {
                            Helper.showMesajGoster("error");
                        }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    Helper.showMesajGoster("Lütfen bir poloklinik seçiniz !");
                }
            }
        });
        klinikPanel.add(ekleBtn);
        ekleButtonCombo.setBackground(Color.cyan);


        //Klinik Sol ScrolPane
        solKlinikScroll.setBounds(5, 11, 248, 330);
        klinikPanel.add(solKlinikScroll);

        //Popup Menü klinik
        klinikMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        klinikMenu.add(updateMenu);
        klinikMenu.add(deleteMenu);

        //Popup Menu Guncelleme
        updateMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int setID = Integer.parseInt(solTableKlinik.getValueAt(solTableKlinik.getSelectedRow(), 0).toString());
                Klinik selectKlinik = klinik.getFetch(setID);
                UpdateKlinikGUI klinikGUI = new UpdateKlinikGUI(selectKlinik);
                klinikGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                klinikGUI.setVisible(true);
                klinikGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try {
                            updateKlinikModel();
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                    }
                });
            }
        });

        //Popup Menu Silme
        deleteMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.confirm("sure")) {
                    int setID = Integer.parseInt(solTableKlinik.getValueAt(solTableKlinik.getSelectedRow(), 0).toString());
                    try {
                        if (klinik.deleteKlink(setID)) {
                            updateKlinikModel();
                            Helper.showMesajGoster("succes");
                        } else {
                            Helper.showMesajGoster("error");
                        }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }

            }
        });


        //Poloklinik Sol Table
        solTableKlinik = new JTable(klinikModel);
        solTableKlinik.setComponentPopupMenu(klinikMenu);
        //mause tıklanma
        solTableKlinik.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = solTableKlinik.rowAtPoint(point);

                solTableKlinik.setRowSelectionInterval(selectedRow, selectedRow);
            }


        });
        solKlinikScroll.setViewportView(solTableKlinik);


        //Klinik Sag ScrolPane
        sagKlinikScroll.setBounds(444, 11, 248, 330);
        klinikPanel.add(sagKlinikScroll);
        sagKlinikScroll.setBackground(Color.pink);

        calisanlarKlinikTable = new JTable();
        sagKlinikScroll.setViewportView(calisanlarKlinikTable);

        //Doktor Ad Soyad
        drAdSoyad.setFont(new Font("Tahoma", Font.PLAIN, 13));
        drAdSoyad.setBounds(500, 10, 180, 45);
        doktorPanal.add(drAdSoyad);

        //Doktor Ad Soyad Input
        adSoyadInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
        adSoyadInput.setBounds(500, 40, 170, 20);
        doktorPanal.add(adSoyadInput);
        adSoyadInput.setColumns(100);

        //Tc No Label
        tcLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tcLabel.setBounds(500, 60, 180, 45);
        doktorPanal.add(tcLabel);

        //Tc No Input
        tcInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tcInput.setBounds(500, 90, 170, 20);
        doktorPanal.add(tcInput);
        tcInput.setColumns(100);

        //Şifre Label
        sifreLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        sifreLabel.setBounds(500, 110, 180, 45);
        doktorPanal.add(sifreLabel);

        //Şifre İnput
        sifreInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
        sifreInput.setBounds(500, 140, 170, 20);
        doktorPanal.add(sifreInput);
        sifreInput.setColumns(100);

        //Ekle Button
        ekleButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        ekleButton.setBounds(590, 170, 80, 30);
        doktorPanal.add(ekleButton);

        ekleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (adSoyadInput.getText().length() == 0 || sifreInput.getText().length() == 0 || tcInput.getText().length() == 0) {
                    Helper.showMesajGoster("fiil");
                } else {
                    try {
                        boolean kontrol = basHekim.addDoctor(adSoyadInput.getText(), sifreInput.getText(), tcInput.getText());
                        if (kontrol)
                            Helper.showMesajGoster("succes");
                        adSoyadInput.setText(null);
                        sifreInput.setText(null);
                        tcInput.setText(null);
                        updateDoctorModel();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        //Kullanıcı ID

        kullaniciID.setFont(new Font("Tahoma", Font.PLAIN, 13));
        kullaniciID.setBounds(500, 180, 180, 45);
        doktorPanal.add(kullaniciID);

        //Kullanıcı ID Input

        kullaniciIDInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
        kullaniciIDInput.setBounds(500, 210, 170, 20);
        doktorPanal.add(kullaniciIDInput);
        kullaniciIDInput.setColumns(100);

        //Sil Button
        silBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
        silBtn.setBounds(590, 240, 80, 30);
        doktorPanal.add(silBtn);

        silBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (kullaniciIDInput.getText().length() == 0) {
                    Helper.showMesajGoster("Lütfen geçerli bir doktor seçiniz  !");
                } else {
                    if (Helper.confirm("sure")) {
                        int selectID = Integer.parseInt(kullaniciIDInput.getText());
                        try {
                            boolean kontrol = basHekim.deleteDoctor(selectID);
                            if (kontrol) {
                                Helper.showMesajGoster("succes");
                                updateDoctorModel();
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

            }
        });

        //ScrolPane
        doktorPanelScroll.setBounds(10, 11, 480, 310);
        doktorPanal.add(doktorPanelScroll);

        doktorTable = new JTable(doctorModel);
        doktorPanelScroll.setViewportView(doktorTable);
        //bir nesne seçildiyse bunun değerini bana getirmesini sağlar
        doktorTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                try {
                    kullaniciIDInput.setText(doktorTable.getValueAt(doktorTable.getSelectedRow(), 0).toString());

                } catch (Exception ex) {

                }
            }
        });
        doktorTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int selectID = Integer.parseInt(doktorTable.getValueAt(doktorTable.getSelectedRow(), 0).toString());
                    String selectName = doktorTable.getValueAt(doktorTable.getSelectedRow(), 1).toString();
                    String selectTckn = doktorTable.getValueAt(doktorTable.getSelectedRow(), 2).toString();
                    String selectSifre = doktorTable.getValueAt(doktorTable.getSelectedRow(), 3).toString();
                    try {
                        boolean kontrol = basHekim.updateDoctor(selectID, selectTckn, selectSifre, selectName);

                    } catch (SQLException ex) {

                    }


                }
            }
        });


    }

    public void updateDoctorModel() throws SQLException {
        DefaultTableModel temizleModel = (DefaultTableModel) doktorTable.getModel();
        //bütün rowlar silinir 0 verirsem
        temizleModel.setRowCount(0);
        for (int i = 0; i < basHekim.getDoctorList().size(); i++) {
            doctorData[0] = basHekim.getDoctorList().get(i).getId();
            doctorData[2] = basHekim.getDoctorList().get(i).getTckn();
            doctorData[1] = basHekim.getDoctorList().get(i).getName();
            doctorData[3] = basHekim.getDoctorList().get(i).getPassword();
            //modelın içine herdefasında girecek şekilde doctordatayı doctor modele attım
            doctorModel.addRow(doctorData);
        }


    }

    public void updateKlinikModel() throws SQLException {
        DefaultTableModel temizleKlinik = (DefaultTableModel) solTableKlinik.getModel();
        //bütün rowlar silinir 0 verirsem
        temizleKlinik.setRowCount(0);
        for (int i = 0; i < klinik.getKlinikList().size(); i++) {
            klinikData[0] = klinik.getKlinikList().get(i).getId();
            klinikData[1] = klinik.getKlinikList().get(i).getName();
            //modelın içine herdefasında girecek şekilde doctordatayı doctor modele attım
            klinikModel.addRow(klinikData);
        }
    }

    public void calisanTmizle() {
        DefaultTableModel temizleCalisan = (DefaultTableModel) calisanlarKlinikTable.getModel();
        temizleCalisan.setRowCount(0);
    }


}