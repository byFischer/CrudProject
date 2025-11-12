package crudproject;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;
import javax.swing.JOptionPane;

public final class PrintUtils {
	private PrintUtils() {}
		public static void printTableSafe(JTable table, String title) {
	        printTableSafe(table, title, true, true);
	}
		public static void printTableSafe(JTable table, String title, boolean landscape, boolean showDialog) {
	        if (table == null) {
	            JOptionPane.showMessageDialog(null, "Tablo bulunamadı.");
	            return;
	        }
	        TableModel model = table.getModel();
	        if (model == null || model.getRowCount() == 0) {
	            JOptionPane.showMessageDialog(table, "Tablo boş. Yazdırılacak kayıt yok.");
	            return;
	        }

	        
	        if (!table.isShowing()) {
	            table.setSize(table.getPreferredSize());
	            table.doLayout();
	        }

	        RepaintManager rm = RepaintManager.currentManager(table);
	        boolean wasDb = rm.isDoubleBufferingEnabled();
	        rm.setDoubleBufferingEnabled(false);
	        try {
	            MessageFormat header = new MessageFormat(title != null ? title : "Rapor");
	            MessageFormat footer = new MessageFormat("Sayfa {0}");

	            
	            PrinterJob job = PrinterJob.getPrinterJob();
	            job.setPrintable(table.getPrintable(JTable.PrintMode.FIT_WIDTH, header, footer));

	            PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
	            if (landscape) attrs.add(OrientationRequested.LANDSCAPE);

	            if (!showDialog || job.printDialog(attrs)) {
	                job.print(attrs);
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(table, "Yazdırma hatası: " + ex.getMessage(),
	                    "Hata", JOptionPane.ERROR_MESSAGE);
	        } finally {
	            rm.setDoubleBufferingEnabled(wasDb);
	        }
	        }
}
