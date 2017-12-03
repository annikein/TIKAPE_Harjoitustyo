/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Lanka;
import tikape.runko.domain.Tarvike;

public class TarvikeDao implements Dao<Tarvike, Integer> {

    private Database database;

    public TarvikeDao(Database database) {
        this.database = database;
    }

    @Override
    public Tarvike findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tarvike WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String tyyppi = rs.getString("tyyppi");
        String pituus = rs.getString("pituus");
        String koko = rs.getString("koko");


        Tarvike o = new Tarvike(id, tyyppi, pituus, koko);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    public boolean tarvikeExists(Tarvike object) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tarvike WHERE tyyppi =? AND pituus =?"
                + " AND koko = ?");
        stmt.setObject(1, object.getTyyppi());
        stmt.setObject(2, object.getPituus());
        stmt.setObject(3, object.getKoko());

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public List<Tarvike> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tarvike ORDER BY tyyppi, koko");

        ResultSet rs = stmt.executeQuery();
        List<Tarvike> tarvikkeet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String tyyppi = rs.getString("tyyppi");
            String pituus = rs.getString("pituus");
            String koko = rs.getString("koko");
           

            tarvikkeet.add(new Tarvike(id, tyyppi, pituus, koko));
        }

        rs.close();
        stmt.close();
        connection.close();

        return tarvikkeet;
    }
    
    public List<Tarvike> findAllForMalli(Integer malli_id) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tarvike, MalliTarvike "
                + "WHERE Tarvike.id = MalliTarvike.tarvike_id "
                + "AND MalliTarvike.malli_id =?");
        stmt.setInt(1, malli_id);
        
        ResultSet rs = stmt.executeQuery();
        List<Tarvike> tarvikkeet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String tyyppi = rs.getString("tyyppi");
            String pituus = rs.getString("pituus");
            String koko = rs.getString("koko");


            tarvikkeet.add(new Tarvike(id, tyyppi, pituus, koko));
        }

        rs.close();
        stmt.close();
        connection.close();

        return tarvikkeet;
    }
    
    public Tarvike save(Tarvike object) throws SQLException {
        // simply support saving -- disallow saving if tarvike with same attributes exist
        if (tarvikeExists(object) == true) {
            return null;
        }
        String tyyppi = object.getTyyppi();
        String pituus = object.getPituus();
        String koko = object.getKoko();
        
        
        if (pituus.isEmpty()) {
            pituus = "-";
        }
        
        if (koko.isEmpty()) {
            koko = "-";
        }
        
        

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tarvike "
                    + "(tyyppi, pituus, koko) VALUES (?, ?, ?)");
            stmt.setString(1, tyyppi);
            stmt.setString(2, pituus);
            stmt.setString(3, koko);
            stmt.executeUpdate();
        }
        return object;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}