
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.MalliTarvike;


public class MalliTarvikeDao implements Dao<MalliTarvike, Integer> {

    private Database database;

    public MalliTarvikeDao(Database database) {
        this.database = database;
    }

    @Override
    public MalliTarvike findOne(Integer key) throws SQLException {
        //ei toteutettu
        return null;
    }

    @Override
    public List<MalliTarvike> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MalliTarvike");

        ResultSet rs = stmt.executeQuery();
        List<MalliTarvike> mt = new ArrayList<>();
        while (rs.next()) {
            Integer malliid = rs.getInt("malli_id");
            Integer tarvikeid = rs.getInt("tarvike_id");
            String maara = rs.getString("maara");

            mt.add(new MalliTarvike(malliid, tarvikeid, maara));
        }

        rs.close();
        stmt.close();
        connection.close();

        return mt;
    }
    
    public List<MalliTarvike> findAllForMalli(Integer malli_id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MalliTarvike "
                + "WHERE malli_id=? ORDER BY tarvike_id ASC");
     
        stmt.setInt(1, malli_id);
        
        ResultSet rs = stmt.executeQuery();
        List<MalliTarvike> mt = new ArrayList<>();
        while (rs.next()) {
            Integer malliId = rs.getInt("malli_id");
            Integer tarvikeId = rs.getInt("tarvike_id");
            String maara = rs.getString("maara");
            

            mt.add(new MalliTarvike(malliId, tarvikeId, maara));
        }

        rs.close();
        stmt.close();
        connection.close();

        return mt;
    }
    
    

    @Override
    public MalliTarvike save(MalliTarvike object) throws SQLException {
        if (malliTarvikeExists(object) == true) {
            return null;
        }
        
        String maara = object.getMaara();
        
        if (maara.isEmpty()) {
                return null;
            } 
       
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO MalliTarvike "
                    + "(malli_id, tarvike_id, maara) VALUES (?, ?, ?)");
            stmt.setInt(1, object.getMalliId());
            stmt.setInt(2, object.getTarvikeId());
            stmt.setString(3, object.getMaara());
            stmt.executeUpdate();
        }
        return object;
        
    }
    
    public boolean malliTarvikeExists(MalliTarvike object) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MalliTarvike WHERE malli_id =? "
                + "AND tarvike_id =? AND maara =?");
        stmt.setObject(1, object.getMalliId());
        stmt.setObject(2, object.getTarvikeId());
        stmt.setObject(3, object.getMaara());


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
