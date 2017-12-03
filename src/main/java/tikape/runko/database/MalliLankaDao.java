
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.MalliLanka;


public class MalliLankaDao implements Dao<MalliLanka, Integer> {

    private Database database;

    public MalliLankaDao(Database database) {
        this.database = database;
    }

    @Override
    public MalliLanka findOne(Integer key) throws SQLException {
        //ei toteutettu
        return null;
    }
    
    @Override
    public List<MalliLanka> findAll() throws SQLException {
   
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MalliLanka");

        ResultSet rs = stmt.executeQuery();
        List<MalliLanka> ml = new ArrayList<>();
        while (rs.next()) {
            Integer malliid = rs.getInt("malli_id");
            Integer lankaid = rs.getInt("lanka_id");
            String maara = rs.getString("maara");

            ml.add(new MalliLanka(malliid, lankaid, maara));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ml;
    }
    
    

    public List<MalliLanka> findAllForMalli(Integer malli_id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MalliLanka "
                + "WHERE malli_id=? ORDER BY lanka_id ASC");
     
        stmt.setInt(1, malli_id);
        
        ResultSet rs = stmt.executeQuery();
        List<MalliLanka> ml = new ArrayList<>();
        while (rs.next()) {
            Integer malliId = rs.getInt("malli_id");
            Integer lankaId = rs.getInt("lanka_id");
            String maara = rs.getString("maara");
            

            ml.add(new MalliLanka(malliId, lankaId, maara));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ml;
    }
    

    @Override
    public MalliLanka save(MalliLanka object) throws SQLException {
        if (malliLankaExists(object) == true) {
            return null;
        }
        
        String maara = object.getMaara();
        
        if (maara.isEmpty()) {
                return null;
            }      
       
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO MalliLanka "
                    + "(malli_id, lanka_id, maara) VALUES (?, ?, ?)");
            stmt.setInt(1, object.getMalliId());
            stmt.setInt(2, object.getLankaId());
            stmt.setString(3, object.getMaara());
            stmt.executeUpdate();        
        }
        return object;
        
    }

    
    public boolean malliLankaExists(MalliLanka object) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MalliLanka WHERE malli_id =? "
                + "AND lanka_id =? AND maara =?");
        stmt.setObject(1, object.getMalliId());
        stmt.setObject(2, object.getLankaId());
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