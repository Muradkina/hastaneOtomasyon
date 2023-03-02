package helper;

import javax.swing.*;

public class Helper {
    public static void optionlPaneChangeButtonText(){
        UIManager.put("OptionPane.cancelButtonText","İptal");
        UIManager.put("OptionPane.noButtonText","Hayır");
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");

    }

    public static void showMesajGoster(String s) {
        String mesajVer;
        optionlPaneChangeButtonText();
        switch (s) {
            case "fiil":
                mesajVer = "Tüm Alanları Doldurunuz.";
                break;
            case "succes":
                mesajVer = "İşlem Başarılı !";
                break;
            default:
                mesajVer = s;

        }
        JOptionPane.showMessageDialog(null, mesajVer, "Mesaj", JOptionPane.INFORMATION_MESSAGE);

    }

    public static boolean confirm(String str) {
        String mesaj;
        optionlPaneChangeButtonText();
        switch (str) {
            case "sure":
                mesaj = "Bu işlemi gerçekleştirmek istiyor musunuz !";
                break;
            default:
            mesaj = str;
            break;
        }
        int sonuc = JOptionPane.showConfirmDialog(null, mesaj, "Dikkat !", JOptionPane.YES_NO_OPTION);
        if (sonuc == 0) {
            return true;
        } else
            return false;
    }
}
