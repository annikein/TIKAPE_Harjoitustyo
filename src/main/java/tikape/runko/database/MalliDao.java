
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Lanka;
import tikape.runko.domain.Malli;

public class MalliDao implements Dao<Malli, Integer> {

    private Database database;

    public MalliDao(Database database) {
        this.database = database;
    }

    @Override
    public Malli findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Malli WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        String tyyppi = rs.getString("tyyppi");
        String koko = rs.getString("koko");
        String ohje = rs.getString("ohje");


        Malli m = new Malli(id, nimi, tyyppi, koko, ohje);

        rs.close();
        stmt.close();
        connection.close();

        return m;
    }

    @Override
    public List<Malli> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Malli");

        ResultSet rs = stmt.executeQuery();
        List<Malli> mallit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            String tyyppi = rs.getString("tyyppi");
            String koko = rs.getString("koko");
            String ohje = rs.getString("ohje");
 

            mallit.add(new Malli(id, nimi, tyyppi, koko, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        return mallit;
    }
    
    
    public void createNew(String nimi, String tyyppi) throws SQLException {
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Malli "
                    + "(nimi, tyyppi) VALUES (?, ?)");
            stmt.setString(1, nimi);
            stmt.setString(2, tyyppi);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public Malli save(Malli object) throws SQLException {
        // ei toteutettu
          return null;
    }
    
   
    public void addKoko(Integer malli_id, String koko) throws SQLException {
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Malli "
                    + " SET koko =? WHERE id=?");
            stmt.setString(1, koko);
            stmt.setInt(2, malli_id);
            stmt.executeUpdate();
        }
    }
    
    public void addOhje(Integer malli_id, String ohje) throws SQLException {
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Malli "
                    + " SET ohje =? WHERE id=?");
            stmt.setString(1, ohje);
            stmt.setInt(2, malli_id);
            stmt.executeUpdate();
        }
    }
    
    

    @Override
    public void delete(Integer key) throws SQLException {
 
        Connection connection = database.getConnection();
        PreparedStatement stmt1 = connection.prepareStatement("DELETE FROM Malli WHERE id =?");
        stmt1.setObject(1, key);
        stmt1.executeUpdate();
        
        PreparedStatement stmt2 = connection.prepareStatement("DELETE FROM MalliLanka WHERE malli_id =?");
        stmt2.setObject(1, key);
        stmt2.executeUpdate();
        
        PreparedStatement stmt3 = connection.prepareStatement("DELETE FROM MalliTarvike WHERE malli_id =?");
        stmt3.setObject(1, key);
        stmt3.executeUpdate();
        
        stmt1.close();
        stmt2.close();
        stmt3.close();
        connection.close();
    }

}

