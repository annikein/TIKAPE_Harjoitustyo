
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Lanka;
import tikape.runko.domain.Tarvike;

public class LankaDao implements Dao<Lanka, Integer> {

    private Database database;

    public LankaDao(Database database) {
        this.database = database;
    }

    @Override
    public Lanka findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        String vari = rs.getString("vari");
        String varikoodi = rs.getString("varikoodi");
        String valmistaja = rs.getString("valmistaja");
        String materiaali = rs.getString("materiaali");
        String paksuus = rs.getString("paksuus");
        
        Lanka o = new Lanka(id, nimi, vari, varikoodi, valmistaja, materiaali, paksuus);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }
    
   


    @Override
    public List<Lanka> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka "
                + "ORDER BY nimi");

        ResultSet rs = stmt.executeQuery();
        List<Lanka> langat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            String vari = rs.getString("vari");
            String varikoodi = rs.getString("varikoodi");
            String valmistaja = rs.getString("valmistaja");
            String materiaali = rs.getString("materiaali");
            String paksuus = rs.getString("paksuus");

            langat.add(new Lanka(id, nimi, vari, varikoodi, valmistaja, materiaali, paksuus));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }
    
  
    public List<Lanka> findAllForMalli(Integer malli_id) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka, MalliLanka "
                + "WHERE Lanka.id = MalliLanka.lanka_id "
                + "AND MalliLanka.malli_id =? ORDER BY lanka_id ASC");
        stmt.setInt(1, malli_id);
        
        ResultSet rs = stmt.executeQuery();
        List<Lanka> langat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            String vari = rs.getString("vari");
            String varikoodi = rs.getString("varikoodi");
            String valmistaja = rs.getString("valmistaja");
            String materiaali = rs.getString("materiaali");
            String paksuus = rs.getString("paksuus");

            langat.add(new Lanka(id, nimi, vari, varikoodi, valmistaja, materiaali, paksuus));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }
    
    
    @Override
    public Lanka save(Lanka object) throws SQLException {
        if (lankaExists(object) == true) {
            return null;
        }
        String nimi = object.getNimi();
        String vari = object.getVari();
        String varikoodi = object.getVarikoodi();
        String valmistaja = object.getValmistaja();
        String materiaali = object.getMateriaali();
        String paksuus = object.getPaksuus();
     
        
        if (nimi.isEmpty()) {
            return null;
        }
        
        if (varikoodi.isEmpty()) {
            varikoodi = "-";
        }
        
        if (valmistaja.isEmpty()) {
            valmistaja = "-";
        }
     
        if (materiaali.isEmpty()) {
            materiaali = "-";
        }
        
        if (paksuus.isEmpty()) {
            paksuus = "-";
        }
        

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Lanka "
                    + "(nimi, vari, varikoodi, valmistaja, materiaali, paksuus) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, nimi);
            stmt.setString(2, vari);
            stmt.setString(3, varikoodi);
            stmt.setString(4, valmistaja);
            stmt.setString(5, materiaali);
            stmt.setString(6, paksuus);
            stmt.executeUpdate();
        }
        return object;
        
    }
    
    //tarkistetaan onko tietty lanka jo olemassa
    public boolean lankaExists(Lanka object) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka WHERE nimi =? "
                + "AND vari =? AND varikoodi =? AND valmistaja =? AND materiaali =? AND paksuus =?");
        stmt.setObject(1, object.getNimi());
        stmt.setObject(2, object.getVari());
        stmt.setObject(3, object.getVarikoodi());
        stmt.setObject(3, object.getValmistaja());
        stmt.setObject(3, object.getMateriaali());
        stmt.setObject(3, object.getPaksuus());

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
