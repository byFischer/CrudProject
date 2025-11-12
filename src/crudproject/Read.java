package crudproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class Read {
    private final String tableName;
    public Read(String tableName){ this.tableName = tableName; }

    public DefaultTableModel execute() throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        try (Connection con = DbHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData md = rs.getMetaData();
            int colCount = md.getColumnCount();

            String[] headers = new String[colCount];
            for (int i = 1; i <= colCount; i++) {
                headers[i-1] = md.getColumnLabel(i);
            }

            DefaultTableModel m = new DefaultTableModel(headers, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };

            while (rs.next()) {
                Object[] row = new Object[colCount];
                for (int i = 1; i <= colCount; i++) {
                    row[i-1] = rs.getObject(i);
                }
                m.addRow(row);
            }
            return m;
        }
    }
}
