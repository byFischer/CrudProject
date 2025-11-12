package crudproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;



public class Update {
	private final String tableName;
	public Update(String tableName){ this.tableName = tableName; }
	public int execute(Adresses a) throws SQLException {
        String sql ="UPDATE "+tableName+" SET " +
            "Kod=?, Adi=?, Adresi=?, Semt=?, Sehir=?, Telefon=?, " +
            "BorcTutar=?, AlacakTutar=?, Bakiye=?, Notu=?, OzelAlanGrup=? " +
            "WHERE ID=?";

        try (Connection con = DbHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1,  a.getAdresKodu());
            ps.setString(2,  a.getAdi());
            ps.setString(3,  a.getAdresi());
            ps.setString(4,  a.getSemt());
            ps.setString(5,  a.getSehir());
            ps.setString(6,  a.getTelefon());
            ps.setBigDecimal(7,  a.getBorcTutar());         
            ps.setBigDecimal(8,  a.getAlacakTutar());
            ps.setBigDecimal(9,  a.getBakiye());
            ps.setString(10,  a.getNotu());
            ps.setString(11, a.getOzelAlanGrup());
            ps.setInt(12, a.getID()); 

            int row = ps.executeUpdate(); 
            if (row != 1) {
                throw new SQLException("Update başarısız (etkilenen satır: " + row + ")");
            }
            return row; 
        }
    }

	
}
