package wiev;

import helper.DBConnetion;
import helper.Helper;
import model.BasHekim;
import model.Doktor;
import model.Klinik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class HastaneLoginGUI extends JFrame {
    private JLabel logo;
    private JPanel hastane_Form;
    private JLabel label2;
    private JTabbedPane tabbedPane1;
    private JPanel hstGiris;
    private JLabel tckn;
    private JLabel sifre;
    private JTextField hastaTc;
    private JButton kayıtOlButton;
    private JButton girişYapButton;
    private JPanel dktgiris;
    private JLabel dktrTckn;
    private JButton doktorLoginBtn;
    private JButton dktrKayitBtn;
    private JTextField doktorTc;
    private JPasswordField hastPassword;
    private JPasswordField doktorPassword;
    private JLabel dktorSifre;
    private DBConnetion cDB = new DBConnetion();


    public HastaneLoginGUI(BasHekimGUI hekimGUI) {
        add(hastane_Form);
        setResizable(false);
        setTitle("Hastane Otomasyonu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        hastane_Form.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(hastane_Form);
        hastane_Form.setLayout(null);

        //LOGO
        logo.setBounds(196, 5, 200, 140);
        hastane_Form.add(logo);


        //HASTANE YÖNETİM SİSTEMİ YAZI
        label2.setBounds(99, 40, 450, 230);
        label2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
        hastane_Form.add(label2);


        //JTabbedPanel Bölümü
        tabbedPane1.setBounds(10, 220, 470, 222);
        hastane_Form.add(tabbedPane1);

        //Hasta Giriş
        hstGiris.setBackground(Color.WHITE);
        tabbedPane1.addTab("Hasta Girişi", null, hstGiris, null);
        hstGiris.setLayout(null);

        //T.C NO
        tckn.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
        tckn.setBounds(35, 10, 180, 45);
        hstGiris.add(tckn);

        //ŞİFRE
        sifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
        sifre.setBounds(35, 90, 180, 45);
        hstGiris.add(sifre);


        //HASTA TC INPUT
        hastaTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
        hastaTc.setBounds(170, 20, 220, 35);
        hstGiris.add(hastaTc);
        hastaTc.setColumns(10);

        //HASTA  ŞİFRE INPUT
        hastPassword.setFont(new Font("Yu Gothic UI Ligh", Font.PLAIN, 15));
        hastPassword.setBounds(170, 100, 220, 35);
        hstGiris.add(hastPassword);
        hastPassword.setColumns(10);

        //Kayıt BTN
        kayıtOlButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        kayıtOlButton.setBounds(170, 150, 100, 30);
        hstGiris.add(kayıtOlButton);

        //Giriş BTN
        girişYapButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        girişYapButton.setBounds(290, 150, 100, 30);
        hstGiris.add(girişYapButton);

        //Doktor Giriş
        dktgiris.setBackground(Color.WHITE);
        tabbedPane1.addTab("Doktor Girişi", null, dktgiris, null);
        dktgiris.setLayout(null);

        //T.C DOKTOR
        dktrTckn.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
        dktrTckn.setBounds(35, 10, 180, 45);
        dktgiris.add(dktrTckn);

        //ŞİFRE DOKTOR
        dktorSifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
        dktorSifre.setBounds(35, 90, 180, 45);
        dktgiris.add(dktorSifre);

        //Doktor TC INPUT
        doktorTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
        doktorTc.setBounds(170, 20, 220, 35);
        dktgiris.add(doktorTc);
        doktorTc.setColumns(10);

        //DOKTOR  ŞİFRE INPUT
        doktorPassword.setFont(new Font("Yu Gothic UI Ligh", Font.PLAIN, 15));
        doktorPassword.setBounds(170, 100, 220, 35);
        dktgiris.add(doktorPassword);
        doktorPassword.setColumns(10);


     /*   //Kayıt BTN
        dktrKayitBtn.setFont(new Font("Yu Gothic UI Semibold",Font.PLAIN,12));
        dktrKayitBtn.setBounds(170,150,100,30);
        dktgiris.add(dktrKayitBtn);*/

        //Giriş BTN
        doktorLoginBtn.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        doktorLoginBtn.setBounds(270, 150, 120, 30);
        dktgiris.add(doktorLoginBtn);
        doktorLoginBtn.addActionListener(new ActionListener() {
            private Klinik klinik;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (doktorTc.getText().length() == 0 || doktorPassword.getText().length() == 0) {
                    Helper.showMesajGoster("fiil");
                } else {

                    try {

                        Connection con = cDB.baglanDB();
                        Statement st = con.createStatement();
                        ResultSet rs =st.executeQuery("SELECT * FROM kullanici");
                        while (rs.next()) {
                            if (doktorTc.getText().equals(rs.getString("tckn"))
                                    && doktorPassword.getText().equals(rs.getString("password"))) {
                                if (rs.getString("tipi").equals("bashekim")){
                                    BasHekim bHekim = new BasHekim();
                                    bHekim.setId(rs.getInt("id"));
                                    bHekim.setPassword("password");
                                    bHekim.setTckn(rs.getString("tckn"));
                                    bHekim.setName(rs.getString("name"));
                                    bHekim.setTipi(rs.getString("tipi"));
                                    BasHekimGUI hekimGUI1=new BasHekimGUI(bHekim);
                                    hekimGUI1.setVisible(true);

                                    dispose();//Bashekimgui sayfasının kapatımını sağlar
                                }
                                if (rs.getString("tipi").equals("doktor")){
                                    Doktor doktor=new Doktor();
                                    doktor.setId(rs.getInt("id"));
                                    doktor.setPassword("password");
                                    doktor.setTckn(rs.getString("tckn"));
                                    doktor.setName(rs.getString("name"));
                                    doktor.setTipi(rs.getString("tipi"));
                                    DoctorGUI doctorGUI=new DoctorGUI(doktor);
                                    doctorGUI.setVisible(true);
                                    dispose();
                                }
                            }
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();

                    }
                }
            }
        });
    }


}