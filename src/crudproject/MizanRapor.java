package crudproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class MizanRapor {
	private final String tableName;
    public String adresKodu;
    public String adresKodu2;
    
    public MizanRapor(String tableName, String adresKodu, String adresKodu2) {
        this.tableName = tableName; 
        this.adresKodu = adresKodu; 
        this.adresKodu2 = adresKodu2;
    }
public DefaultTableModel execute() throws SQLException {
        
	String sql = "(SELECT Kod, Adi, BorcTutar, AlacakTutar, " +
            "CASE WHEN BorcTutar > AlacakTutar THEN BorcTutar - AlacakTutar ELSE 0 END AS BorcBakiye, " +
            "CASE WHEN AlacakTutar > BorcTutar THEN AlacakTutar - BorcTutar ELSE 0 END AS AlacakBakiye, " +
            "0 as Sıra " +
            "FROM " + tableName + " WHERE Kod BETWEEN ? AND ?) " +
            "UNION ALL " + "(SELECT 'TOPLAM', '', " + "SUM(BorcTutar), SUM(AlacakTutar), " +
            "SUM(CASE WHEN BorcTutar > AlacakTutar THEN BorcTutar - AlacakTutar ELSE 0 END), " +
            "SUM(CASE WHEN AlacakTutar > BorcTutar THEN AlacakTutar - BorcTutar ELSE 0 END), " +
            "1 as Sıra " + "FROM " + tableName + " WHERE Kod BETWEEN ? AND ?) " + "ORDER BY Sıra, Kod";
	
	
        
        try (Connection con = DbHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        	{
            
           
            ps.setString(1, adresKodu);
            ps.setString(2, adresKodu2);
            ps.setString(3, adresKodu);
            ps.setString(4, adresKodu2);
            
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                int colcount = md.getColumnCount();
                
                String[] headers = new String[colcount];
                for (int i = 1; i <= colcount; i++) {
                    headers[i-1] = md.getColumnLabel(i);
                }
                
                DefaultTableModel m = new DefaultTableModel(headers, 0) {
                    @Override 
                    public boolean isCellEditable(int r, int c) { 
                        return false; 
                    }
                };
                
                while (rs.next()) {
                    Object[] row = new Object[colcount];
                    for (int i = 1; i <= colcount; i++) {
                        row[i-1] = rs.getObject(i);
                    }
                    m.addRow(row);
                }
                return m;
            }
        }
        
}
}
