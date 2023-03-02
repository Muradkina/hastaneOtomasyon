package model;

import java.sql.*;
import java.util.ArrayList;

public class BasHekim extends User {


    Connection connection = conn.baglanDB();
    Statement stmn = null;
    ResultSet sonuc = null;
    PreparedStatement preparedStatement = null;


    public BasHekim(int id, String tcno, String name, String password, String type, int dogumTarih) {
        super(id, tcno, name, password, type);
    }

    public BasHekim() {
    }

    public ArrayList<User> getDoctorList() throws SQLException {
        ArrayList<User> kullaniciListe = new ArrayList<>();
        User obj;

        try {
            stmn = connection.createStatement();
            sonuc = stmn.executeQuery("SELECT * FROM kullanici WHERE tipi='doktor' ");
            while (sonuc.next()) {
                obj = new User(sonuc.getInt("id"),
                        sonuc.getString("tckn"),
                        sonuc.getString("name"),
                        sonuc.getString("password"),
                        sonuc.getString("tipi"));
                kullaniciListe.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();


        }
        return kullaniciListe;

    }

    public ArrayList<User> getKlinikDoktorList(int klinik_id) throws SQLException {
        ArrayList<User> kullaniciListe = new ArrayList<>();
        User obj;

        try {
            stmn = connection.createStatement();
            sonuc = stmn.executeQuery("SELECT k.id ,k.tckn,k.name,k.password,k.tipi FROM calisanlar c " +
                    "LEFT JOIN kullanici k " +
                    "ON k.id=c.kullanici_id WHERE klinik_id="+klinik_id);
            while (sonuc.next()) {
                obj = new User(sonuc.getInt("id"),
                        sonuc.getString("tckn"),
                        sonuc.getString("name"),
                        sonuc.getString("password"),
                        sonuc.getString("tipi"));
                kullaniciListe.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();


        }
        return kullaniciListe;

    }

    public boolean addDoctor(String tckn, String name, String password) throws SQLException {
        String query = "INSERT INTO kullanici" + "(tckn,name,tipi,password) VALUES"
                + "(?,?,?,?)";
        boolean key = false;

        try {
            stmn = connection.createStatement();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, tckn);
            preparedStatement.setString(3, "doktor");
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();
            key = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (key)
            return true;
        else
            return false;


    }

    public boolean deleteDoctor(int id) throws SQLException {
        String query = "DELETE FROM  kullanici WHERE id=?";
        boolean key = false;
        try {
            stmn = connection.createStatement();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            key = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (key)
            return true;
        else
            return false;


    }

    public boolean updateDoctor(int id, String tckn, String password, String name) throws SQLException {
        String query = "UPDATE  kullanici SET name = ?, tckn =? ,password =? WHERE id=?";
        boolean key = false;
        try {
            stmn = connection.createStatement();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,tckn);
            preparedStatement.setString(3,password);
            preparedStatement.setInt(4,id);
            preparedStatement.executeUpdate();
            key = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (key)
            return true;
        else
            return false;


    }
    public boolean addCalisan(int kullanici_id,int klinik_id) throws SQLException {
        String query = "INSERT INTO calisanlar" + "(kullanici_id,klinik_id) VALUES"
                + "(?,?)";
        boolean key = false;
        int count=0;
       try {
           stmn = connection.createStatement();
           //Başhekım kliniğe çalışan  eklerse daha önce kelenirse  bu çalışan eklemesini engellemek için
           sonuc = stmn.executeQuery("SELECT *FROM calisanlar WHERE klinik_id="
           +klinik_id
           +"AND kullanici_id="+kullanici_id);
           while (sonuc.next()) {
               count++;
           }
           if (count == 0) {
               preparedStatement = connection.prepareStatement(query);
               preparedStatement.setInt(1, kullanici_id);
               preparedStatement.setInt(2, klinik_id);
               preparedStatement.executeUpdate();
               key = true;
           }
       }catch (Exception e) {
            e.printStackTrace();
        }
        if (key)
            return true;
        else
            return false;


    }


}
