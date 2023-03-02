package wiev;

import com.toedter.calendar.JDateChooser;
import helper.Helper;
import model.Doktor;
import model.Saat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class DoctorGUI extends JFrame {
    private JPanel doctorForm;
    private JLabel hosgeldinizYazi;
    private JButton cikis_btn;
    private JTabbedPane paneCalismaSaat;
    private JPanel calismaSaatPanel;
    private JComboBox select_time;
    private JButton saatEkleBtn;
    private JScrollPane saatScrolPane;
    private JTable table_saat;
    private JButton silBtn;
    private JDateChooser dateChooser = new JDateChooser();
    private Doktor doktor = new Doktor();
    private Saat saat;

    private DefaultTableModel saatModel = null;
    private Object[] saatData = null;


    public DoctorGUI(Doktor doktor) throws SQLException {

        saatModel = new DefaultTableModel();
        Object[] colmnSaatName = new Object[2];
        colmnSaatName[0] = "ID";
        colmnSaatName[1] = "TARİH";
        saatModel.setColumnIdentifiers(colmnSaatName);
        saatData = new Object[2];
        try {
            for (int i = 0; i < doktor.getWhourList(doktor.getId()).size(); i++) {
                saatData[0] = doktor.getWhourList(doktor.getId()).get(i).getId();
                saatData[1] = doktor.getWhourList(doktor.getId()).get(i).getWdate();
                saatModel.addRow(saatData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(doctorForm);
        setResizable(false);
        setTitle("Hastane Yönetim Sistemi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 750, 500);
        doctorForm.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(doctorForm);
        doctorForm.setLayout(null);

        //Hoşgeldiniz-Label
        JLabel hosgeldinizYazi = new JLabel("Hoşgeldizin Sayın" + " " + doktor.getName());
        hosgeldinizYazi.setBounds(25, 10, 400, 25);
        hosgeldinizYazi.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        doctorForm.add(hosgeldinizYazi);

        //Çıkış Yap Btn

        cikis_btn.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        cikis_btn.setBounds(615, 10, 100, 25);
        doctorForm.add(cikis_btn);

        //Pane
        paneCalismaSaat.setBounds(20, 70, 700, 382);
        doctorForm.add(paneCalismaSaat);


        calismaSaatPanel.setBackground(Color.WHITE);
        paneCalismaSaat.addTab("Çalışma Saatleri", null, calismaSaatPanel, null);
        calismaSaatPanel.setLayout(null);

        dateChooser.setBounds(25, 10, 150, 25);
        calismaSaatPanel.add(dateChooser);

        //Saat Combo
        select_time.setBounds(215, 10, 100, 25);
        select_time.setBackground(Color.white);
        calismaSaatPanel.add(select_time);

        //Ekle button
        saatEkleBtn.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        saatEkleBtn.setBounds(335, 10, 100, 25);
        calismaSaatPanel.add(saatEkleBtn);
        saatEkleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = "";
                try {
                    date = sdf.format(dateChooser.getDate());
                } catch (Exception e1) {

                }
                if (date.length() == 0) {
                    Helper.showMesajGoster("Lütfen geçerli bir tarih giriniz !");
                } else {
                    String time = " " + select_time.getSelectedItem().toString() + ":00";
                    String selectDate = date + time;

                    try {
                        boolean cntrl = doktor.addWhour(doktor.getId(), doktor.getName(), selectDate);
                        if (cntrl) {
                            Helper.showMesajGoster("succes");
                            updateSaatModel(doktor);
                        } else {
                            Helper.showMesajGoster("error");
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                }


            }
        });

        saatScrolPane.setBounds(5, 60, 680, 290);
        calismaSaatPanel.add(saatScrolPane);


        table_saat = new JTable(saatModel);
        saatScrolPane.setViewportView(table_saat);

        //Sil Button
        silBtn.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        silBtn.setBounds(470, 10, 100, 25);
        calismaSaatPanel.add(silBtn);
        silBtn.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         int selRow = table_saat.getSelectedRow();
                                         if (selRow >= 0) {
                                             String selectRow = table_saat.getModel().getValueAt(selRow, 0).toString();
                                             int selID =Integer.parseInt(selectRow);
                                             try {
                                                 boolean kontrol=doktor.deleteSaat(selID);
                                                 if (kontrol){
                                                     Helper.showMesajGoster("succes");
                                                     updateSaatModel(doktor);
                                                 }
                                             } catch (SQLException ex) {
                                                 throw new RuntimeException(ex);
                                             }
                                         } else {
                                             Helper.showMesajGoster("Lütfen bir tarih seçiniz !");
                                         }
                                     }

                                 }
        );
    }

    public void updateSaatModel(Doktor doktor) throws SQLException {
        DefaultTableModel temizleModel = (DefaultTableModel) table_saat.getModel();
        //bütün rowlar silinir 0 verirsem
        temizleModel.setRowCount(0);
        for (int i = 0; i < doktor.getWhourList(doktor.getId()).size(); i++) {
            saatData[0] = doktor.getWhourList(doktor.getId()).get(i).getId();
            saatData[1] = doktor.getWhourList(doktor.getId()).get(i).getWdate();
            saatModel.addRow(saatData);

        }


    }
}