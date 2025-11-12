package crudproject;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;

public final class TablePrinter {

    private TablePrinter() {} 

    public static void print(JTable table) {
        print(table,  true,  true);
    }

    public static void print(JTable table, boolean landscape, boolean showDialog) {
        if (table == null) {
            JOptionPane.showMessageDialog(null, "Tablo bulunamadı.");
            return;
        }
        TableModel model = table.getModel();
        if (model == null || model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(table, "Tablo boş. Yazdırılacak kayıt yok.");
            return;
        }
        table.doLayout();

        
        RepaintManager rm = RepaintManager.currentManager(table);
        boolean wasDb = rm.isDoubleBufferingEnabled();
        rm.setDoubleBufferingEnabled(false);

        try {
            
            MessageFormat header = new MessageFormat("");
            MessageFormat footer = new MessageFormat("");

            
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(table.getPrintable(JTable.PrintMode.FIT_WIDTH, header, footer));

            PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
            if (landscape) {
                attrs.add(OrientationRequested.LANDSCAPE);
            }

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
