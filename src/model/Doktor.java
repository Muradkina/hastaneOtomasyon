package model;

import java.sql.*;
import java.util.ArrayList;

public class Doktor extends User {
    Connection connection = conn.baglanDB();
    Statement stmn = null;
    ResultSet sonuc = null;
    PreparedStatement preparedStatement = null;

    public Doktor() {
    }


    public ArrayList<Saat> getWhourList(int doctor_id) throws SQLException {
        ArrayList<Saat> list = new ArrayList<>();

        Saat obj;
        try {
            stmn = connection.createStatement();
            sonuc = stmn.executeQuery("SELECT * FROM saat WHERE status ='aktif' AND doctor_id=" + doctor_id);

            while (sonuc.next()) {
                obj = new Saat();
                obj.setId(sonuc.getInt("id"));
                obj.setDoktor_id(sonuc.getInt("doctor_id"));
                obj.setDoktor_name(sonuc.getString("doctor_name"));
                obj.setStatus(sonuc.getString("status"));
                obj.setWdate(sonuc.getString("wdate"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Doktor(int id, String tcno, String name, String password, String type) {
        super(id, tcno, name, password, type);
    }
    public boolean addWhour(int doctor_id, String doctor_name, String wdate) {
        int key = 0;
        int count = 0;
        String query = "INSERT INTO saat" + "(doctor_id,doctor_name,wdate) VALUES" + " (?,?,?)";
        try {
            stmn = connection.createStatement();
            sonuc = stmn.executeQuery(
                    "SELECT * FROM saat WHERE status='aktif' AND doctor_id=" + doctor_id + " AND wdate='" + wdate + "'");
            while (sonuc.next()) {
                count++;
                break;
            }
            if (count == 0) {

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, doctor_id);
                preparedStatement.setString(2, doctor_name);
                preparedStatement.setString(3, wdate);
                preparedStatement.executeUpdate();
            }
            key = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (key == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteSaat(int id) throws SQLException {
        String query = "DELETE FROM  saat WHERE id=?";
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


}
