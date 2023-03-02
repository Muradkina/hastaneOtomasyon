package model;

import helper.DBConnetion;

import java.sql.*;
import java.util.ArrayList;

public class Klinik {
    private int id;
    private String name;


    DBConnetion conn = new DBConnetion();
    Statement stmn = null;
    ResultSet sonuc = null;
    PreparedStatement preparedStatement = null;

    public Klinik(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Klinik() {
    }


    public ArrayList<Klinik> getKlinikList() throws SQLException {
        ArrayList<Klinik> kullaniciListe = new ArrayList<>();
        Klinik obj;
        Connection connection = conn.baglanDB();
        try {
            stmn = connection.createStatement();
            sonuc = stmn.executeQuery("SELECT * FROM klinik  ");
            while (sonuc.next()) {
                obj = new Klinik();
                obj.setId(sonuc.getInt("id"));
                obj.setName(sonuc.getString("name"));
                kullaniciListe.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kullaniciListe;

    }

    public Klinik getFetch(int id) {
        Connection connection = conn.baglanDB();
        Klinik k = new Klinik();
        try {
            stmn = connection.createStatement();
            sonuc = stmn.executeQuery("SELECT * FROM klinik  WHERE id=" + id);

            while (sonuc.next()) {
                k.setName(sonuc.getString("name"));
                k.setId(sonuc.getInt("id"));
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return k;
    }

    public boolean addKlink(String name) throws SQLException {
        String query = "INSERT INTO klinik" + "(name) VALUES"
                + "(?)";
        boolean key = false;
        Connection connection = conn.baglanDB();

        try {
            stmn = connection.createStatement();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
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

    public boolean deleteKlink(int id) throws SQLException {
        String query = "DELETE FROM  klinik WHERE id=?";
        boolean key = false;
        Connection connection = conn.baglanDB();

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

    public boolean updateKlinik(int id, String name) throws SQLException {
        String query = "UPDATE  klinik SET name = ? WHERE id=?";
        boolean key = false;
        Connection connection = conn.baglanDB();

        try {
            stmn = connection.createStatement();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
