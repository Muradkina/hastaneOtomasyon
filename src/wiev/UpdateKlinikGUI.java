package wiev;

import helper.Helper;
import model.Klinik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateKlinikGUI extends JFrame {
    private JPanel popupForm;
    private JLabel ploklinikLabel;
    private JTextField PlokinikInput;
    private JButton btnUpdateKlinik;


    private Klinik klinik = new Klinik();


    public UpdateKlinikGUI(Klinik klinik) {
        add(popupForm);
        setResizable(false);
        setTitle("Popup Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 300, 180);
        popupForm.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(popupForm);
        popupForm.setLayout(null);

        //Poloklinil Label
        ploklinikLabel.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 11));
        ploklinikLabel.setBounds(10, 11, 100, 20);
        popupForm.add(ploklinikLabel);

        PlokinikInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
        PlokinikInput.setBounds(10, 34, 160, 30);
        popupForm.add(PlokinikInput);
        PlokinikInput.setColumns(10);
        PlokinikInput.setText(klinik.getName());

        btnUpdateKlinik.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 11));
        btnUpdateKlinik.setBounds(10, 74, 80, 30);
        popupForm.add(btnUpdateKlinik);
        btnUpdateKlinik.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.confirm("sure")) {
                    try {
                        klinik.updateKlinik(klinik.getId(), PlokinikInput.getText());
                        Helper.showMesajGoster("succes");
                        dispose();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

}
