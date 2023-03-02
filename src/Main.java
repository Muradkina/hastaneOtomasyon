import model.BasHekim;
import model.Klinik;
import wiev.BasHekimGUI;
import wiev.DoctorGUI;
import wiev.HastaneLoginGUI;
import wiev.UpdateKlinikGUI;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        BasHekim basHekim = new BasHekim();
        Klinik klinik = new Klinik();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {
                    //Doktor ve Hasta giriş ekranına  BasHekım Nesnesinin içine aktarımı
                    HastaneLoginGUI loginGUI = new HastaneLoginGUI(new BasHekimGUI(basHekim));
                    loginGUI.setVisible(true);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}