package crudproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class Delete {
	private final String tableName;
	public Delete(String tableName){ this.tableName = tableName; }
	
	public int execute(String kod) throws SQLException{
		String sql = "DELETE FROM "+ tableName+ " WHERE Kod=?"; 
	
		try (Connection con = DbHelper.getConnection();
			PreparedStatement ps = con.prepareStatement(sql))
		{ps.setString(1, kod);
		int row = ps.executeUpdate();
		if (row != 1) {
			throw new SQLException("Delete başarısız");
            }
            return row;
		} 			
	}

}
