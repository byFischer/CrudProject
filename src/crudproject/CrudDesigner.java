package crudproject;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import java.awt.CardLayout;



public class CrudDesigner {
	private static final String TABLE_NAME = "Rehber";
	private JTable table1 = new JTable();
    private JFrame tableWindow;
	private JFrame frame;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_10;
	private int selectedID = -1;
	private JComboBox combobox = new JComboBox();
	private JComboBox comboBoxSehir = new JComboBox();
	JTextArea textArea = new JTextArea();
	JTextArea textArea_1 = new JTextArea();
	private int currentID = 1;
	private int currentRecordID = 0;

	private JTextField textField;
	private JLabel lblbakiye;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_9;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
private JPanel panel_1;
private JPanel panel_6;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_21;
	private JTextField textField_22;
	private JTextField textField_23;
	private JTextField textField_24;
	private JTextField textField_25;
	private JTextField textField_26;
	private JTextField textField_27;
	private JTextField textField_28;
	private JTextField textField_29;
	private JTextField textField_30;
	private JTextField textField_31;
	private JTextField textField_32;
	private JTextField textField_33;
	private JTextField textField_34;
	private JTextField textField_35;
	private JTextField textField_36;
	private JTextField textField_37;

    private java.math.BigDecimal parseBigDecimal(String text) {
        if (text == null) return java.math.BigDecimal.ZERO;
        String s = text.trim();
        if (s.isEmpty()) return java.math.BigDecimal.ZERO;
        
        s = s.replaceAll("\\s", "");
        if (s.contains(",") && s.contains(".")) {
            s = s.replace(".", ""); 
            s = s.replace(",", "."); 
        } else if (s.contains(",") && !s.contains(".")) {
            s = s.replace(",", "."); 
        }
        try {
            return new java.math.BigDecimal(s);
        } catch (NumberFormatException ex) {
            return java.math.BigDecimal.ZERO;
        }
    }

    private java.math.BigDecimal parseBigDecimalOrNull(String text) {
        if (text == null) return null;
        String s = text.trim();
        if (s.isEmpty()) return null;
        java.math.BigDecimal val = parseBigDecimal(s);
        return val;
    }

    private String formatCurrency(java.math.BigDecimal value) {
        java.text.DecimalFormatSymbols symbols = new java.text.DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00", symbols);
        df.setGroupingUsed(true);
        if (value == null) return df.format(java.math.BigDecimal.ZERO);
        return df.format(value);
    }

    private void applyCurrencyRenderer(JTable tbl) {
        DefaultTableCellRenderer currencyRenderer = new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof java.math.BigDecimal) {
                    super.setText(formatCurrency((java.math.BigDecimal) value));
                } else if (value instanceof Number) {
                    java.math.BigDecimal bd = new java.math.BigDecimal(((Number) value).toString());
                    super.setText(formatCurrency(bd));
                } else if (value != null) {
                    java.math.BigDecimal bd = parseBigDecimal(value.toString());
                    super.setText(formatCurrency(bd));
                } else {
                    super.setText("");
                }
            }
        };
        currencyRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        try {
            tbl.getColumnModel().getColumn(tbl.getColumnModel().getColumnIndex("BorcTutar")).setCellRenderer(currencyRenderer);
        } catch (IllegalArgumentException ignore) {}
        try {
            tbl.getColumnModel().getColumn(tbl.getColumnModel().getColumnIndex("AlacakTutar")).setCellRenderer(currencyRenderer);
        } catch (IllegalArgumentException ignore) 
        {}
        try {
            tbl.getColumnModel().getColumn(tbl.getColumnModel().getColumnIndex("Bakiye")).setCellRenderer(currencyRenderer);
        } catch (IllegalArgumentException ignore) 
        
        {}
    }

	private void updateBakiye() {
		java.math.BigDecimal borc = parseBigDecimal(textField_7.getText());
		java.math.BigDecimal alacak = parseBigDecimal(textField_8.getText());
        java.math.BigDecimal diff = borc.subtract(alacak);
        textField.setText(formatCurrency(diff.abs()));
        if (diff.signum() > 0) {
            lblbakiye.setText("B");
        } else if (diff.signum() < 0) {
            lblbakiye.setText("A");
        } else {
            lblbakiye.setText("-");
        }
    }
	
	
	
	public void JTableMouseSelect(java.awt.event.MouseEvent evt) {
		
		
		String sql = "select * from "+ TABLE_NAME+" where ID=?"; 
		try {
			Connection con = DbHelper.getConnection();
			int row = table1.getSelectedRow();
			if(row ==-1) return;
			
			int modelRow = table1.convertRowIndexToModel(row);
			String tblclick = (table1.getModel().getValueAt(modelRow, 0).toString());
			selectedID = Integer.parseInt(tblclick);
			PreparedStatement ps =con.prepareStatement(sql);
			ps.setString(1, tblclick);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String AdresKodu =rs.getString("Kod"); textField_1.setText(AdresKodu);
				String Adi =rs.getString("Adi"); textField_2.setText(Adi);
				String Adresi=rs.getString("Adresi"); textArea_1.setText(Adresi);
				String Sehir =rs.getString("Sehir"); comboBoxSehir.setSelectedItem(Sehir);
				String Semt =rs.getString("Semt"); textField_5.setText(Semt);
				String Telefon =rs.getString("Telefon"); textField_6.setText(Telefon);
                java.math.BigDecimal borc = rs.getBigDecimal("BorcTutar"); textField_7.setText(formatCurrency(borc));
                java.math.BigDecimal AlacakTutar = rs.getBigDecimal("AlacakTutar"); textField_8.setText(formatCurrency(AlacakTutar));
                String Notu =rs.getString("Notu"); textArea.setText(Notu);
                String OzelAlanGrup =rs.getString("OzelAlanGrup"); textField_10.setText(OzelAlanGrup);
                java.math.BigDecimal bakiyeDb = null;
                try { bakiyeDb = rs.getBigDecimal("Bakiye"); } catch (Exception ignore) {}
                if (bakiyeDb != null) {
                    textField.setText(formatCurrency(bakiyeDb));
                    if (bakiyeDb.signum() > 0) {
                        lblbakiye.setText("B");
                    } else if (bakiyeDb.signum() < 0) {
                        lblbakiye.setText("A");
                    } else {
                        lblbakiye.setText("-");
                    }
                } else {
                    updateBakiye();
                }
			}
			if(evt.getClickCount() == 2 && !evt.isConsumed()) {
				evt.consume();
				tableWindow.dispose();
				
				
			}
			rs.close();
	        ps.close();
	        con.close();
		}catch(Exception e) {
			
		}
	}
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrudDesigner window = new CrudDesigner();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public CrudDesigner() {
		initialize();
	
	}

	
	private Image scaleImage(Image img, int w, int h) {
		return img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	}
	
	private Image mirrorHorizontally(Image img) {
		BufferedImage src = toBuffered(img);
		int w = src.getWidth();
		int h = src.getHeight();
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-w, 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		op.filter(src, dst);
		return dst;
	}
	
	private BufferedImage toBuffered(Image img) {
		ImageIcon ii = new ImageIcon(img);
		int w = ii.getIconWidth();
		int h = ii.getIconHeight();
		BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		java.awt.Graphics2D g = b.createGraphics();
		ii.paintIcon(null, g, 0, 0);
		g.dispose();
		return b;
	}
	

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.setBounds(100, 100, 977, 855);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	String[] sehirler={"Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir", "Bilecik", "Bingöl", 
			"Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", 
			"Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", "İçel (Mersin)", "İstanbul", 
			"İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", 
			"Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", 
			"Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", 
			"Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"
	};
		
		panel_6 = new JPanel();
		panel_6.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_6.setBounds(10, 109, 941, 534);
		frame.getContentPane().add(panel_6);
		panel_6.setLayout(null);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_7.setBounds(10, 11, 921, 91);
		panel_6.add(panel_7);
		panel_7.setLayout(null);
		
		JLabel lblNewLabel_23 = new JLabel("Terminal Bilgileri");
		lblNewLabel_23.setForeground(new Color(0, 0, 160));
		lblNewLabel_23.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblNewLabel_23.setBounds(10, 0, 102, 14);
		panel_7.add(lblNewLabel_23);
		
		JLabel lblNewLabel_27 = new JLabel("New label");
		lblNewLabel_27.setBounds(10, 25, 46, 14);
		panel_7.add(lblNewLabel_27);
		
		JLabel lblNewLabel_28 = new JLabel("New label");
		lblNewLabel_28.setBounds(10, 50, 46, 14);
		panel_7.add(lblNewLabel_28);
		
		JLabel lblNewLabel_29 = new JLabel("New label");
		lblNewLabel_29.setBounds(476, 25, 46, 14);
		panel_7.add(lblNewLabel_29);
		
		JLabel lblNewLabel_30 = new JLabel("New label");
		lblNewLabel_30.setBounds(476, 50, 46, 14);
		panel_7.add(lblNewLabel_30);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_8.setBounds(10, 113, 921, 76);
		panel_6.add(panel_8);
		panel_8.setLayout(null);
		
		JLabel lblNewLabel_24 = new JLabel("Web / E-Mail");
		lblNewLabel_24.setForeground(new Color(0, 0, 160));
		lblNewLabel_24.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblNewLabel_24.setBounds(10, 0, 89, 14);
		panel_8.add(lblNewLabel_24);
		
		JLabel lblNewLabel_26 = new JLabel("Stok Barkod Dizayn");
		lblNewLabel_26.setForeground(new Color(0, 0, 160));
		lblNewLabel_26.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblNewLabel_26.setBounds(625, 0, 113, 14);
		panel_8.add(lblNewLabel_26);
		
		JLabel lblNewLabel_31 = new JLabel("New label");
		lblNewLabel_31.setBounds(10, 24, 46, 14);
		panel_8.add(lblNewLabel_31);
		
		JLabel lblNewLabel_32 = new JLabel("New label");
		lblNewLabel_32.setBounds(10, 49, 46, 14);
		panel_8.add(lblNewLabel_32);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_9.setBounds(10, 197, 921, 161);
		panel_6.add(panel_9);
		panel_9.setLayout(null);
		
		JLabel lblNewLabel_25 = new JLabel("Özel Alanlar");
		lblNewLabel_25.setForeground(new Color(0, 0, 160));
		lblNewLabel_25.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblNewLabel_25.setBounds(10, 0, 91, 14);
		panel_9.add(lblNewLabel_25);
		
		JLabel lblNewLabel_33 = new JLabel("New label");
		lblNewLabel_33.setBounds(10, 25, 46, 14);
		panel_9.add(lblNewLabel_33);
		
		JLabel lblNewLabel_34 = new JLabel("New label");
		lblNewLabel_34.setBounds(10, 50, 46, 14);
		panel_9.add(lblNewLabel_34);
		
		JLabel lblNewLabel_35 = new JLabel("New label");
		lblNewLabel_35.setBounds(10, 75, 46, 14);
		panel_9.add(lblNewLabel_35);
		
		JLabel lblNewLabel_36 = new JLabel("New label");
		lblNewLabel_36.setBounds(10, 100, 46, 14);
		panel_9.add(lblNewLabel_36);
		
		JLabel lblNewLabel_37 = new JLabel("New label");
		lblNewLabel_37.setBounds(10, 125, 46, 14);
		panel_9.add(lblNewLabel_37);
		
		JLabel lblNewLabel_38 = new JLabel("New label");
		lblNewLabel_38.setBounds(10, 147, 46, 14);
		panel_9.add(lblNewLabel_38);
		
		JLabel lblNewLabel_39 = new JLabel("New label");
		lblNewLabel_39.setBounds(448, 25, 46, 14);
		panel_9.add(lblNewLabel_39);
		
		JLabel lblNewLabel_40 = new JLabel("New label");
		lblNewLabel_40.setBounds(448, 50, 46, 14);
		panel_9.add(lblNewLabel_40);
		
		JLabel lblNewLabel_41 = new JLabel("New label");
		lblNewLabel_41.setBounds(448, 75, 46, 14);
		panel_9.add(lblNewLabel_41);
		
		JLabel lblNewLabel_42 = new JLabel("New label");
		lblNewLabel_42.setBounds(448, 100, 46, 14);
		panel_9.add(lblNewLabel_42);
		
		JLabel lblNewLabel_43 = new JLabel("New label");
		lblNewLabel_43.setBounds(448, 125, 46, 14);
		panel_9.add(lblNewLabel_43);
		
		JLabel lblNewLabel_44 = new JLabel("New label");
		lblNewLabel_44.setBounds(448, 147, 46, 14);
		panel_9.add(lblNewLabel_44);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_10.setBounds(10, 369, 921, 25);
		panel_6.add(panel_10);
		panel_10.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnNewButton_12 = new JButton("e-Fatura");
		btnNewButton_12.setFont(new Font("Times New Roman", Font.BOLD, 14));
		panel_10.add(btnNewButton_12);
		
		JButton btnNewButton_13 = new JButton("e-İrsaliye");
		btnNewButton_13.setFont(new Font("Times New Roman", Font.BOLD, 14));
		panel_10.add(btnNewButton_13);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_11.setBounds(10, 405, 921, 129);
		panel_6.add(panel_11);
		panel_11.setLayout(null);
		
		JLabel lblNewLabel_45 = new JLabel("New label");
		lblNewLabel_45.setBounds(10, 11, 46, 14);
		panel_11.add(lblNewLabel_45);
		
		JLabel lblNewLabel_46 = new JLabel("New label");
		lblNewLabel_46.setBounds(10, 34, 46, 14);
		panel_11.add(lblNewLabel_46);
		
		JLabel lblNewLabel_47 = new JLabel("New label");
		lblNewLabel_47.setBounds(10, 59, 46, 14);
		panel_11.add(lblNewLabel_47);
		
		JLabel lblNewLabel_48 = new JLabel("New label");
		lblNewLabel_48.setBounds(140, 11, 46, 14);
		panel_11.add(lblNewLabel_48);
		
		JLabel lblNewLabel_49 = new JLabel("New label");
		lblNewLabel_49.setBounds(412, 11, 46, 14);
		panel_11.add(lblNewLabel_49);
		
		JLabel lblNewLabel_50 = new JLabel("New label");
		lblNewLabel_50.setBounds(140, 59, 46, 14);
		panel_11.add(lblNewLabel_50);
		
		JLabel lblNewLabel_51 = new JLabel("New label");
		lblNewLabel_51.setBounds(10, 84, 46, 14);
		panel_11.add(lblNewLabel_51);
		
		JLabel lblNewLabel_52 = new JLabel("New label");
		lblNewLabel_52.setBounds(10, 104, 46, 14);
		panel_11.add(lblNewLabel_52);
		
		JLabel lblNewLabel_53 = new JLabel("New label");
		lblNewLabel_53.setBounds(493, 84, 46, 14);
		panel_11.add(lblNewLabel_53);
		
		JLabel lblNewLabel_54 = new JLabel("New label");
		lblNewLabel_54.setBounds(493, 104, 46, 14);
		panel_11.add(lblNewLabel_54);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 11, 941, 49);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBounds(42, 5, 51, 42);
		panel.add(btnNewButton);
		{
			ImageIcon createIcon = new ImageIcon(getClass().getResource("create icon.png"));
			Image img = createIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
			btnNewButton.setIcon(new ImageIcon(img));
			btnNewButton.setToolTipText("Yeni Kayıt");
		}
		btnNewButton.addActionListener(new ActionListener() {
            
			public void actionPerformed(ActionEvent e) {
				
				String adresKodu = textField_1.getText();
		    	String adi = textField_2.getText();
		    	String adresi = textArea_1.getText();
		    	String sehir = "";
		    	if(comboBoxSehir.getSelectedItem()!=null) {
		    		sehir =comboBoxSehir.getSelectedItem().toString();
		    	}
		    	else {
		    		sehir = "";
		    	}
		    	String semt = textField_5.getText();
		    	String telefon = textField_6.getText();
            java.math.BigDecimal borcTutar = parseBigDecimalOrNull(textField_7.getText());
            java.math.BigDecimal alacakTutar = parseBigDecimalOrNull(textField_8.getText());
            java.math.BigDecimal b1 = (borcTutar == null ? java.math.BigDecimal.ZERO : borcTutar);
            java.math.BigDecimal a1 = (alacakTutar == null ? java.math.BigDecimal.ZERO : alacakTutar);
            java.math.BigDecimal bakiyeInput = parseBigDecimalOrNull(textField.getText());
            java.math.BigDecimal bakiye = (bakiyeInput != null ? bakiyeInput : b1.subtract(a1));
		    	String notu = textArea.getText();
		    	String ozelAlanGrup = textField_10.getText();
		    	
				if(adresKodu.isEmpty()) {
					JOptionPane.showMessageDialog( btnNewButton, "Kod boş olamaz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
		    	}
				Adresses a = new Adresses(adresKodu, adi,adresi,semt,sehir, telefon, borcTutar,alacakTutar,bakiye,notu,ozelAlanGrup);
		    
		    try {
		    	int newId = new Create(TABLE_NAME).execute(a);
		    	JOptionPane.showMessageDialog(btnNewButton,"Kayıt eklendi. Kod = " + adresKodu,"Bilgi",JOptionPane.INFORMATION_MESSAGE);
		    
		    	
		    }catch (java.sql.SQLException ex) {
		        	JOptionPane.showMessageDialog(btnNewButton,"Bu Kod içerisinde adres kayıtlı!","Hata",JOptionPane.ERROR_MESSAGE);
		        	
		    }	
			textField_1.setText("");
			textField_2.setText("");
			textArea_1.setText("");
			
			textField_5.setText("");
			textField_6.setText("");
			textField_7.setText("");
			textField_8.setText("");
			textArea.setText("");
			textField_10.setText("");
			
			textField_1.requestFocus();
			
			}
		});
		
		
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBounds(103, 5, 51, 42);
		panel.add(btnNewButton_1);
		{
			ImageIcon listIcon = new ImageIcon(getClass().getResource("table (1).png"));
			Image img = listIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
			btnNewButton_1.setIcon(new ImageIcon(img));
			btnNewButton_1.setHorizontalTextPosition(SwingConstants.CENTER);
			btnNewButton_1.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnNewButton_1.setToolTipText("Liste");
		}
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (tableWindow == null) {
		            tableWindow = new JFrame("TABLE");
		            tableWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		            JLayeredPane layeredPane = new JLayeredPane();
		            layeredPane.setPreferredSize(new Dimension(1000, 700));
		            
		            
		            JScrollPane scrollPane = new JScrollPane(table1);
		            scrollPane.setBounds(0, 40, 1000, 660); 
		            layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);
		            
		            
		            
		            JPanel buttonPanel = new JPanel();
		            buttonPanel.setLayout(null);
		            buttonPanel.setOpaque(false); 
		            buttonPanel.setBounds(0, 5, 1000, 35); 
		            
		            JButton btnprint = new JButton();
		            btnprint.setBounds(900, 0, 30, 30); 
		            btnprint.setToolTipText("Print");
		            
		            ImageIcon printIcon = new ImageIcon(getClass().getResource("159287.png"));
		            Image img1 = printIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
		            btnprint.setIcon(new ImageIcon(img1));
		            
		            JButton btnsearch = new JButton("ARA");
		            btnsearch.setBounds(380, 5, 60, 25);
		            
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(
	            	    new String[]{"Hepsi","Kod","Adi","Adresi","Sehir","Semt","Telefon","BorcTutar","AlacakTutar","Bakiye","Notu","Ozel Alan Grup"}
	            	);
		            combobox = new JComboBox(comboBoxModel);
		            combobox.setBounds(10, 5, 120, 25);
		            
		            
		            

		            JLabel lbl1 = new JLabel("Ara: ");
		            lbl1.setBounds(170, 5, 40, 25);
		            
		            JTextField txtsearch = new JTextField();
		            txtsearch.setBounds(210, 5, 150, 25);
		            txtsearch.addKeyListener(new KeyAdapter() {
		    			@Override
		    			public void keyPressed(KeyEvent e) {
		    				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
		    					DefaultTableModel m;
								try {
		            			m = new Read(TABLE_NAME).execute();
								table1.setModel(m);
								applyCurrencyRenderer(table1);
									table1.setAutoCreateRowSorter(true);
								} catch (SQLException e1) {
									
									e1.printStackTrace();
								}
		    					
		    					DefaultTableModel obj = (DefaultTableModel) table1.getModel();
								DefaultTableModel searchmodel =  new DefaultTableModel();
								
								String searchText = txtsearch.getText().trim();
								boolean found = false;
								
								for(int i=0;i<obj.getColumnCount();i++) {
									searchmodel.addColumn(obj.getColumnName(i));
								}
								String secilenKolon = (String) combobox.getSelectedItem();
								int colIndex = -1;
								if(secilenKolon != null && !secilenKolon.equalsIgnoreCase("Hepsi") && !secilenKolon.equalsIgnoreCase("Tümü")) {
									colIndex = obj.findColumn(secilenKolon);
								}
								String q = searchText.toLowerCase();
								for(int r =0; r<obj.getRowCount();r++) {
									boolean match = false;
									if(q.isEmpty()) {
										match = true;
									}
									else if (colIndex >=0) {
										Object val = obj.getValueAt(r, colIndex);
												if(val != null && val.toString().toLowerCase().contains(q)) {
													match= true;
													
												}
									}
									else {
										for(int c = 0;c<obj.getColumnCount();c++) {
											Object val = obj.getValueAt(r, c);
											if(val!=null && val.toString().toLowerCase().contains(q)) {
												match = true;
												break;
											}
										}
									}
									if (match) {
								        Object[] rowData = new Object[obj.getColumnCount()];
								        for (int c = 0; c < obj.getColumnCount(); c++) {
								            rowData[c] = obj.getValueAt(r, c);
								        }
								searchmodel.addRow(rowData);
								}
								}
								table1.setModel(searchmodel);
								applyCurrencyRenderer(table1);
								table1.setAutoCreateRowSorter(true);
							txtsearch.setText("");
		    				}
		    			}
		    		});
		            btnsearch.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							DefaultTableModel m;
							try {
								m = new Read(TABLE_NAME).execute();
								table1.setModel(m);
								applyCurrencyRenderer(table1);
								table1.setAutoCreateRowSorter(true);
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
							DefaultTableModel obj = (DefaultTableModel) table1.getModel();
							DefaultTableModel searchmodel =  new DefaultTableModel();
							
							String searchText = txtsearch.getText().trim();
							boolean found = false;
							
							for(int i=0;i<obj.getColumnCount();i++) {
								searchmodel.addColumn(obj.getColumnName(i));
							}
							String secilenKolon = (String) combobox.getSelectedItem();
							int colIndex = -1;
							if(secilenKolon != null && !secilenKolon.equalsIgnoreCase("Hepsi") && !secilenKolon.equalsIgnoreCase("Tümü")) {
								colIndex = obj.findColumn(secilenKolon);
							}
							String q = searchText.toLowerCase();
							for(int r =0; r<obj.getRowCount();r++) {
								boolean match = false;
								if(q.isEmpty()) {
									match = true;
								}
								else if (colIndex >=0) {
									Object val = obj.getValueAt(r, colIndex);
											if(val != null && val.toString().toLowerCase().contains(q)) {
												match= true;
												
											}
								}
								else {
									for(int c = 0;c<obj.getColumnCount();c++) {
										Object val = obj.getValueAt(r, c);
										if(val!=null && val.toString().toLowerCase().contains(q)) {
											match = true;
											break;
										}
									}
								}
								if (match) {
							        Object[] rowData = new Object[obj.getColumnCount()];
							        for (int c = 0; c < obj.getColumnCount(); c++) {
							            rowData[c] = obj.getValueAt(r, c);
							        }
							searchmodel.addRow(rowData);
							}
							}
							table1.setModel(searchmodel);
							applyCurrencyRenderer(table1);
							table1.setAutoCreateRowSorter(true);
						txtsearch.setText("");
						}
					});
		            

		            btnprint.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							TablePrinter.print(table1);
							
						}
		            });
		            JButton btnRefresh = new JButton();
		            btnRefresh.setBounds(935, 0, 30, 30);
		            btnRefresh.setToolTipText("Refresh");
		            
		            ImageIcon exportIcon = new ImageIcon(getClass().getResource("Refresh_icon.svg.png"));
		            Image img2 = exportIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
		            btnRefresh.setIcon(new ImageIcon(img2));
		            
		            btnRefresh.addActionListener(new ActionListener() {
		                public void actionPerformed(ActionEvent evt) {
		                	try {
		                        DefaultTableModel m = new Read(TABLE_NAME).execute();
		                        table1.setModel(m);
		                        applyCurrencyRenderer(table1);
		                        table1.setAutoCreateRowSorter(true);
		                    
		                	
		                	} catch (SQLException ex) {
		                        JOptionPane.showMessageDialog(tableWindow, "Hata: " + ex.getMessage());
		                    }
		                	textField_1.setText("");
		        			textField_2.setText("");
		        			textArea_1.setText("");
		        			combobox.setSelectedIndex(0);
		        			textField_5.setText("");
		        			textField_6.setText("");
		        			textField_7.setText("");
		        			textField_8.setText("");
		        			textArea.setText("");
		        			textField_10.setText("");
		        			textField_1.requestFocus();
		                }
		            });

		            layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);
		            buttonPanel.add(btnRefresh);
		            buttonPanel.add(btnprint);
		            buttonPanel.add(lbl1, JLayeredPane.PALETTE_LAYER);
		            buttonPanel.add(txtsearch, JLayeredPane.PALETTE_LAYER);
		            buttonPanel.add(btnsearch, JLayeredPane.PALETTE_LAYER);
		            buttonPanel.add(combobox);
		           
		            tableWindow.getContentPane().add(layeredPane);
		            tableWindow.setSize(1015, 735);
		            tableWindow.setLocation(1234, 200);
		        }
		        tableWindow.setVisible(true);
		        
		        try {
		            DefaultTableModel m = new Read(TABLE_NAME).execute();
		            table1.setModel(m);
		            applyCurrencyRenderer(table1);
		            table1.setAutoCreateRowSorter(true);
		            table1.addMouseListener(new java.awt.event.MouseAdapter() {
		                public void mouseClicked(java.awt.event.MouseEvent evt) {
		                    JTableMouseSelect(evt);
		                }
		            });
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(tableWindow, "Hata: " + ex.getMessage(), "hata: ", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setBounds(164, 5, 51, 42);
		panel.add(btnNewButton_2);
		{
			ImageIcon updateIcon = new ImageIcon(getClass().getResource("updateicon.png"));
			Image img = updateIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
			btnNewButton_2.setIcon(new ImageIcon(img));
			btnNewButton_2.setHorizontalTextPosition(SwingConstants.CENTER);
			btnNewButton_2.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnNewButton_2.setToolTipText("Güncelle");
		}
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String adresKodu = textField_1.getText();
		    	String Adi = textField_2.getText();
		    	String adresi = textArea_1.getText();
		    	String Semt = textField_5.getText();
		    	String sehir = "";
		    	if(comboBoxSehir.getSelectedItem()!=null) {
		    		sehir = comboBoxSehir.getSelectedItem().toString();
		    	} else {
		    		sehir = "";
		    	}
		    	String Telefon = textField_6.getText();
		    	java.math.BigDecimal borc = parseBigDecimalOrNull(textField_7.getText());
		    	java.math.BigDecimal alacak = parseBigDecimalOrNull(textField_8.getText());
		    	java.math.BigDecimal b1u = (borc == null ? java.math.BigDecimal.ZERO : borc);
		    	java.math.BigDecimal a1u = (alacak == null ? java.math.BigDecimal.ZERO : alacak);
		    	java.math.BigDecimal bakiyeInputU = parseBigDecimalOrNull(textField.getText());
		    	java.math.BigDecimal bakiyeU = (bakiyeInputU != null ? bakiyeInputU : b1u.subtract(a1u));
		    	String notu = textArea.getText();
		    	String ozel = textField_10.getText();
		    	
		    	Adresses a = new Adresses(adresKodu,Adi,adresi,Semt,sehir,Telefon,borc,alacak,bakiyeU,notu,ozel);
		    	a.setID(selectedID);
		    	int row = new Update(TABLE_NAME).execute(a);
		    	if (row == 1) {
		            JOptionPane.showMessageDialog(btnNewButton_2, "Güncellendi. ID = " + adresKodu);
		        }			    
		    	}catch (java.sql.SQLException ex) {
		                JOptionPane.showMessageDialog(btnNewButton_2, "AdresKodu benzersiz olmalı");
		            	JOptionPane.showMessageDialog(btnNewButton_2, "Hata: " + ex.getMessage());
		    			}
				textField_1.setText("");
    			textField_2.setText("");
    			textArea_1.setText("");
    			textField_5.setText("");
    			textField_6.setText("");
    			textField_7.setText("");
    			textField_8.setText("");
    			textArea.setText("");
    			textField_10.setText("");
    			
    			textField_1.requestFocus();	
				
				return;
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setBounds(225, 5, 51, 42);
		panel.add(btnNewButton_3);
		{
			ImageIcon deleteIcon = new ImageIcon(getClass().getResource("deleteicon.png"));
			Image img = deleteIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
			btnNewButton_3.setIcon(new ImageIcon(img));
			btnNewButton_3.setHorizontalTextPosition(SwingConstants.CENTER);
			btnNewButton_3.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnNewButton_3.setToolTipText("Sil");
		}
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String Kod = (textField_1.getText());
		    		
					int choice =JOptionPane.showConfirmDialog(btnNewButton_3," Kod numarası: "+Kod+ " olan satır Silinecektir Emin misiniz ?",null, JOptionPane.OK_CANCEL_OPTION);
		    		if(choice != JOptionPane.OK_OPTION) {
		    			return;
		    		}
		    		int row = new Delete(TABLE_NAME).execute(Kod);
		    	if(row ==1) {
		    		JOptionPane.showMessageDialog(btnNewButton_3, "Adres Silindi Kod: "+ Kod);
		    	}
		    	
		    	}catch (java.sql.SQLException ex) {
		    		JOptionPane.showMessageDialog(btnNewButton_3, "Hata: " + ex.getMessage());
		            }	
		        
				textField_1.setText("");
    			textField_2.setText("");
    			textArea_1.setText("");
    			
    			textField_5.setText("");
    			textField_6.setText("");
    			textField_7.setText("");
    			textField_8.setText("");
    			textArea.setText("");
    			textField_10.setText("");
    			
    			textField_1.requestFocus();
    			
		    	return;
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setBounds(286, 5, 51, 42);
		panel.add(btnNewButton_4);
		{
			ImageIcon pdfIcon = new ImageIcon(getClass().getResource("159287.png"));
			Image img = pdfIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
			btnNewButton_4.setIcon(new ImageIcon(img));
			btnNewButton_4.setHorizontalTextPosition(SwingConstants.CENTER);
			btnNewButton_4.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnNewButton_4.setToolTipText("PDF / Rapor");
		}
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RaporWindow rw = new RaporWindow();
				rw.setVisible(true);
				rw.setLocation(1200, 250);
				
		    }
		});
		
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton engeriBtn = new JButton("");
		engeriBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql = "SELECT TOP 1 * FROM " + TABLE_NAME + " WHERE ID < ? ORDER BY ID ASC";
				try {
					Connection con = DbHelper.getConnection();
					if (currentRecordID <= 0 && selectedID > 0) {
						currentRecordID = selectedID;
					}
					
					PreparedStatement ps =con.prepareStatement(sql);
					ps.setInt(1, currentRecordID);
					ResultSet rs = ps.executeQuery();
					
					if(rs.next()) {
						currentRecordID = rs.getInt("ID"); // ✓ Yeni ID'yi güncelle
					    textField_1.setText(rs.getString("Kod"));
						selectedID = currentRecordID;
						String Adi =rs.getString("Adi"); textField_2.setText(Adi);
						String Adresi=rs.getString("Adresi"); textArea_1.setText(Adresi);
						String Sehir =rs.getString("Sehir"); comboBoxSehir.setSelectedItem(Sehir);
						String Semt =rs.getString("Semt"); textField_5.setText(Semt);
						String Telefon =rs.getString("Telefon"); textField_6.setText(Telefon);
		                java.math.BigDecimal borc = rs.getBigDecimal("BorcTutar"); textField_7.setText(formatCurrency(borc));
		                java.math.BigDecimal AlacakTutar = rs.getBigDecimal("AlacakTutar"); textField_8.setText(formatCurrency(AlacakTutar));
		                String Notu =rs.getString("Notu"); textArea.setText(Notu);
		                String OzelAlanGrup =rs.getString("OzelAlanGrup"); textField_10.setText(OzelAlanGrup);
		                java.math.BigDecimal bakiyeDb = null;
		                try { bakiyeDb = rs.getBigDecimal("Bakiye"); } catch (Exception ignore) {}
		                if (bakiyeDb != null) {
		                    textField.setText(formatCurrency(bakiyeDb));
		                    if (bakiyeDb.signum() > 0) {
		                        lblbakiye.setText("B");
		                    } else if (bakiyeDb.signum() < 0) {
		                        lblbakiye.setText("A");
		                    } else {
		                        lblbakiye.setText("-");
		                    }
		                } else {
		                    updateBakiye();
		                }
						
					}
					
				}catch(SQLException ex) {JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
			    ex.printStackTrace();
					
				}
				catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Geçerli bir ID girin!");
				    ex.printStackTrace();
				}
			}
		});
		engeriBtn.setBounds(390, 5, 51, 41);
		panel.add(engeriBtn);
		{
			ImageIcon rightMost = new ImageIcon(getClass().getResource("right.png"));
			Image img = scaleImage(rightMost.getImage(), 24, 24);
			Image mirrored = mirrorHorizontally(img);
			engeriBtn.setIcon(new ImageIcon(mirrored));
			engeriBtn.setHorizontalTextPosition(SwingConstants.CENTER);
			engeriBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			engeriBtn.setToolTipText("En Geri");
		}
		
		JButton geriBtn = new JButton("");
		geriBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT TOP 1 * FROM " + TABLE_NAME + " WHERE ID < ? ORDER BY ID DESC";
				try {
					Connection con = DbHelper.getConnection();
					if (currentRecordID <= 0 && selectedID > 0) {
						currentRecordID = selectedID;
					}
					
					PreparedStatement ps =con.prepareStatement(sql);
					ps.setInt(1, currentRecordID);
					ResultSet rs = ps.executeQuery();
					
					if(rs.next()) {
						currentRecordID = rs.getInt("ID"); // ✓ Yeni ID'yi güncelle
					    textField_1.setText(rs.getString("Kod"));
						selectedID = currentRecordID;
						String Adi =rs.getString("Adi"); textField_2.setText(Adi);
						String Adresi=rs.getString("Adresi"); textArea_1.setText(Adresi);
						String Sehir =rs.getString("Sehir"); comboBoxSehir.setSelectedItem(Sehir);
						String Semt =rs.getString("Semt"); textField_5.setText(Semt);
						String Telefon =rs.getString("Telefon"); textField_6.setText(Telefon);
		                java.math.BigDecimal borc = rs.getBigDecimal("BorcTutar"); textField_7.setText(formatCurrency(borc));
		                java.math.BigDecimal AlacakTutar = rs.getBigDecimal("AlacakTutar"); textField_8.setText(formatCurrency(AlacakTutar));
		                String Notu =rs.getString("Notu"); textArea.setText(Notu);
		                String OzelAlanGrup =rs.getString("OzelAlanGrup"); textField_10.setText(OzelAlanGrup);
		                java.math.BigDecimal bakiyeDb = null;
		                try { bakiyeDb = rs.getBigDecimal("Bakiye"); } catch (Exception ignore) {}
		                if (bakiyeDb != null) {
		                    textField.setText(formatCurrency(bakiyeDb));
		                    if (bakiyeDb.signum() > 0) {
		                        lblbakiye.setText("B");
		                    } else if (bakiyeDb.signum() < 0) {
		                        lblbakiye.setText("A");
		                    } else {
		                        lblbakiye.setText("-");
		                    }
		                } else {
		                    updateBakiye();
		                }
						
					}
					
				}catch(SQLException ex) {JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
			    ex.printStackTrace();
					
				}
				catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Geçerli bir ID girin!");
				    ex.printStackTrace();
				}
				
			}
		});
		geriBtn.setBounds(451, 5, 51, 42);
		panel.add(geriBtn);
		{
			ImageIcon rightOne = new ImageIcon(getClass().getResource("right one.png"));
			Image img = scaleImage(rightOne.getImage(), 24, 24);
			Image mirrored = mirrorHorizontally(img);
			geriBtn.setIcon(new ImageIcon(mirrored));
			geriBtn.setHorizontalTextPosition(SwingConstants.CENTER);
			geriBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			geriBtn.setToolTipText("Geri");
		}
		
		JButton ierliBtn = new JButton("");
		ierliBtn.setBounds(512, 5, 51, 42);
		panel.add(ierliBtn);
		{
			ImageIcon rightOne = new ImageIcon(getClass().getResource("right one.png"));
			Image img = scaleImage(rightOne.getImage(), 24, 24);
			ierliBtn.setIcon(new ImageIcon(img));
			ierliBtn.setHorizontalTextPosition(SwingConstants.CENTER);
			ierliBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			ierliBtn.setToolTipText("İleri");
		}
		
		JButton enileriBtn = new JButton("");
		enileriBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT TOP 1 * FROM " + TABLE_NAME + " WHERE ID > ? ORDER BY ID DESC";
				try {
					Connection con = DbHelper.getConnection();
					if (currentRecordID <= 0 && selectedID > 0) {
						currentRecordID = selectedID;
					}
					
					PreparedStatement ps =con.prepareStatement(sql);
					ps.setInt(1, currentRecordID);
					ResultSet rs = ps.executeQuery();
					
					if(rs.next()) {
						currentRecordID = rs.getInt("ID"); // ✓ Yeni ID'yi güncelle
					    textField_1.setText(rs.getString("Kod"));
						selectedID = currentRecordID;
						String Adi =rs.getString("Adi"); textField_2.setText(Adi);
						String Adresi=rs.getString("Adresi"); textArea_1.setText(Adresi);
						String Sehir =rs.getString("Sehir"); comboBoxSehir.setSelectedItem(Sehir);
						String Semt =rs.getString("Semt"); textField_5.setText(Semt);
						String Telefon =rs.getString("Telefon"); textField_6.setText(Telefon);
		                java.math.BigDecimal borc = rs.getBigDecimal("BorcTutar"); textField_7.setText(formatCurrency(borc));
		                java.math.BigDecimal AlacakTutar = rs.getBigDecimal("AlacakTutar"); textField_8.setText(formatCurrency(AlacakTutar));
		                String Notu =rs.getString("Notu"); textArea.setText(Notu);
		                String OzelAlanGrup =rs.getString("OzelAlanGrup"); textField_10.setText(OzelAlanGrup);
		                java.math.BigDecimal bakiyeDb = null;
		                try { bakiyeDb = rs.getBigDecimal("Bakiye"); } catch (Exception ignore) {}
		                if (bakiyeDb != null) {
		                    textField.setText(formatCurrency(bakiyeDb));
		                    if (bakiyeDb.signum() > 0) {
		                        lblbakiye.setText("B");
		                    } else if (bakiyeDb.signum() < 0) {
		                        lblbakiye.setText("A");
		                    } else {
		                        lblbakiye.setText("-");
		                    }
		                } else {
		                    updateBakiye();
		                }
						
					}
					
				}catch(SQLException ex) {JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
			    ex.printStackTrace();
					
				}
				catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Geçerli bir ID girin!");
				    ex.printStackTrace();
				}
				
			}
		});
		enileriBtn.setBounds(571, 5, 51, 42);
		panel.add(enileriBtn);
		{
			ImageIcon rightMost = new ImageIcon(getClass().getResource("right.png"));
			Image img = scaleImage(rightMost.getImage(), 24, 24);
			enileriBtn.setIcon(new ImageIcon(img));
			enileriBtn.setHorizontalTextPosition(SwingConstants.CENTER);
			enileriBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			enileriBtn.setToolTipText("En İleri");
		}
		
		panel_1 = new JPanel();
		panel_1.setVisible(false);
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(10, 109, 941, 534);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(10, 11, 429, 512);
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Kod:");
		lblNewLabel_1.setBounds(10, 11, 89, 14);
		panel_4.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		textField_1 = new JTextField();
		textField_1.setBounds(109, 10, 237, 20);
		panel_4.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Adi:");
		lblNewLabel_2.setBounds(10, 36, 89, 14);
		panel_4.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		textField_2 = new JTextField();
		textField_2.setBounds(109, 35, 237, 20);
		panel_4.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Adresi:");
		lblNewLabel_3.setBounds(10, 61, 89, 14);
		panel_4.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		textArea_1.setBounds(109, 63, 237, 75);
		panel_4.add(textArea_1);
		
		textArea_1.setLineWrap(true);
		
		JLabel lblNewLabel_5 = new JLabel("Semt:");
		lblNewLabel_5.setBounds(10, 146, 67, 14);
		panel_4.add(lblNewLabel_5);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		textField_5 = new JTextField();
		textField_5.setBounds(109, 145, 237, 20);
		panel_4.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Sehir:");
		lblNewLabel_4.setBounds(10, 171, 67, 14);
		panel_4.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		comboBoxSehir = new JComboBox(sehirler);
		comboBoxSehir.setBounds(109, 169, 157, 22);
		panel_4.add(comboBoxSehir);
		
		JLabel lblNewLabel_12 = new JLabel("Ülke");
		lblNewLabel_12.setBounds(10, 196, 46, 14);
		panel_4.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Ülke kodu");
		lblNewLabel_13.setBounds(10, 221, 46, 14);
		panel_4.add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("Kapı No");
		lblNewLabel_14.setBounds(10, 246, 46, 14);
		panel_4.add(lblNewLabel_14);
		
		JLabel lblNewLabel_18 = new JLabel("TC Kimlik No");
		lblNewLabel_18.setBounds(10, 271, 46, 14);
		panel_4.add(lblNewLabel_18);
		
		JLabel lblNewLabel_19 = new JLabel("IBAN No");
		lblNewLabel_19.setBounds(10, 296, 46, 14);
		panel_4.add(lblNewLabel_19);
		
		JLabel lblNewLabel_20 = new JLabel("Banka Kodu");
		lblNewLabel_20.setBounds(10, 321, 46, 14);
		panel_4.add(lblNewLabel_20);
		
		JLabel lblNewLabel_21 = new JLabel("Şube Kodu");
		lblNewLabel_21.setBounds(10, 346, 46, 14);
		panel_4.add(lblNewLabel_21);
		
		JLabel lblNewLabel_22 = new JLabel("Hesap No");
		lblNewLabel_22.setBounds(10, 371, 46, 14);
		panel_4.add(lblNewLabel_22);
		
		textField_3 = new JTextField();
		textField_3.setBounds(109, 193, 237, 20);
		panel_4.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(109, 218, 237, 20);
		panel_4.add(textField_4);
		textField_4.setColumns(10);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(109, 243, 237, 20);
		panel_4.add(textField_9);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(109, 268, 237, 20);
		panel_4.add(textField_11);
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBounds(109, 293, 237, 20);
		panel_4.add(textField_12);
		
		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBounds(109, 318, 237, 20);
		panel_4.add(textField_13);
		
		textField_14 = new JTextField();
		textField_14.setColumns(10);
		textField_14.setBounds(109, 343, 237, 20);
		panel_4.add(textField_14);
		
		textField_15 = new JTextField();
		textField_15.setColumns(10);
		textField_15.setBounds(109, 368, 237, 20);
		panel_4.add(textField_15);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_5.setBounds(449, 11, 482, 512);
		panel_1.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblNewLabel_8 = new JLabel("Telefon:");
		lblNewLabel_8.setBounds(10, 11, 100, 14);
		panel_5.add(lblNewLabel_8);
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		textField_6 = new JTextField();
		textField_6.setBounds(120, 10, 237, 20);
		panel_5.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("BorcTutar:");
		lblNewLabel_7.setBounds(10, 36, 100, 14);
		panel_5.add(lblNewLabel_7);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		textField_7 = new JTextField();
		textField_7.setBounds(120, 35, 237, 20);
		panel_5.add(textField_7);
		textField_7.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("AlacakTutar:");
		lblNewLabel_6.setBounds(10, 61, 100, 14);
		panel_5.add(lblNewLabel_6);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		textField_8 = new JTextField();
		textField_8.setBounds(120, 60, 237, 20);
		panel_5.add(textField_8);
		textField_8.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("BAKIYE:");
		lblNewLabel.setBounds(10, 86, 87, 21);
		panel_5.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		textField = new JTextField();
		textField.setBounds(120, 88, 237, 20);
		panel_5.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("OzelAlanGrup:");
		lblNewLabel_10.setBounds(10, 118, 100, 14);
		panel_5.add(lblNewLabel_10);
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		textField_10 = new JTextField();
		textField_10.setBounds(120, 117, 237, 20);
		panel_5.add(textField_10);
		textField_10.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Notu:");
		lblNewLabel_9.setBounds(10, 155, 72, 14);
		panel_5.add(lblNewLabel_9);
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 14));
		textArea.setBounds(120, 145, 237, 75);
		panel_5.add(textArea);
		textArea.setLineWrap(true);
		
		JLabel lblNewLabel_15 = new JLabel("Cep tel");
		lblNewLabel_15.setBounds(10, 241, 46, 14);
		panel_5.add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("Vergi Dairesi");
		lblNewLabel_16.setBounds(10, 266, 46, 14);
		panel_5.add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("Vergi No");
		lblNewLabel_17.setBounds(10, 291, 46, 14);
		panel_5.add(lblNewLabel_17);
		
		textField_16 = new JTextField();
		textField_16.setColumns(10);
		textField_16.setBounds(120, 238, 237, 20);
		panel_5.add(textField_16);
		
		textField_17 = new JTextField();
		textField_17.setColumns(10);
		textField_17.setBounds(120, 263, 237, 20);
		panel_5.add(textField_17);
		
		textField_18 = new JTextField();
		textField_18.setColumns(10);
		textField_18.setBounds(120, 288, 237, 20);
		panel_5.add(textField_18);
		
		lblbakiye = new JLabel("");
		lblbakiye.setBounds(367, 88, 32, 19);
		panel_5.add(lblbakiye);
		lblbakiye.setFont(new Font("Tahoma", Font.BOLD, 15));
		textField_8.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			public void insertUpdate(javax.swing.event.DocumentEvent e) { updateBakiye(); }
			public void removeUpdate(javax.swing.event.DocumentEvent e) { updateBakiye(); }
			public void changedUpdate(javax.swing.event.DocumentEvent e) { updateBakiye(); }
		});
		
				textField_7.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
					public void insertUpdate(javax.swing.event.DocumentEvent e) { updateBakiye(); }
					public void removeUpdate(javax.swing.event.DocumentEvent e) { updateBakiye(); }
					public void changedUpdate(javax.swing.event.DocumentEvent e) { updateBakiye(); }
				});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(10, 62, 941, 49);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnNewButton_5 = new JButton("Sayfa-1");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.setVisible(true);
				panel_6.setVisible(false);
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
			}
		});
		btnNewButton_5.setFont(new Font("Poppins", Font.BOLD, 16));
		panel_2.add(btnNewButton_5);
		ierliBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql = "SELECT TOP 1 * FROM " + TABLE_NAME + " WHERE ID > ? ORDER BY ID ASC";
				try {
					Connection con = DbHelper.getConnection();
					if (currentRecordID <= 0 && selectedID > 0) {
						currentRecordID = selectedID;
					}
					
					PreparedStatement ps =con.prepareStatement(sql);
					ps.setInt(1, currentRecordID);
					ResultSet rs = ps.executeQuery();
					
					if(rs.next()) {
						currentRecordID = rs.getInt("ID"); // ✓ Yeni ID'yi güncelle
					    textField_1.setText(rs.getString("Kod"));
						selectedID = currentRecordID;
						String Adi =rs.getString("Adi"); textField_2.setText(Adi);
						String Adresi=rs.getString("Adresi"); textArea_1.setText(Adresi);
						String Sehir =rs.getString("Sehir"); comboBoxSehir.setSelectedItem(Sehir);
						String Semt =rs.getString("Semt"); textField_5.setText(Semt);
						String Telefon =rs.getString("Telefon"); textField_6.setText(Telefon);
		                java.math.BigDecimal borc = rs.getBigDecimal("BorcTutar"); textField_7.setText(formatCurrency(borc));
		                java.math.BigDecimal AlacakTutar = rs.getBigDecimal("AlacakTutar"); textField_8.setText(formatCurrency(AlacakTutar));
		                String Notu =rs.getString("Notu"); textArea.setText(Notu);
		                String OzelAlanGrup =rs.getString("OzelAlanGrup"); textField_10.setText(OzelAlanGrup);
		                java.math.BigDecimal bakiyeDb = null;
		                try { bakiyeDb = rs.getBigDecimal("Bakiye"); } catch (Exception ignore) {}
		                if (bakiyeDb != null) {
		                    textField.setText(formatCurrency(bakiyeDb));
		                    if (bakiyeDb.signum() > 0) {
		                        lblbakiye.setText("B");
		                    } else if (bakiyeDb.signum() < 0) {
		                        lblbakiye.setText("A");
		                    } else {
		                        lblbakiye.setText("-");
		                    }
		                } else {
		                    updateBakiye();
		                }
						
					}
					
				}catch(SQLException ex) {JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
			    ex.printStackTrace();
					
				}
				catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Geçerli bir ID girin!");
				    ex.printStackTrace();
				}
				
			}
		});
		JButton btnNewButton_9 = new JButton("Sayfa-2");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.setVisible(false);
				panel_6.setVisible(true);
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
			}
		});
		btnNewButton_9.setFont(new Font("Poppins", Font.BOLD, 16));
		panel_2.add(btnNewButton_9);
		
		JButton btnNewButton_10 = new JButton("Hesap Özet");
		btnNewButton_10.setFont(new Font("Poppins", Font.BOLD, 16));
		panel_2.add(btnNewButton_10);
		
		JButton btnNewButton_8 = new JButton("Dağılım");
		btnNewButton_8.setFont(new Font("Poppins", Font.BOLD, 16));
		panel_2.add(btnNewButton_8);
		
		JButton btnNewButton_7 = new JButton("Hareketler");
		btnNewButton_7.setFont(new Font("Poppins", Font.BOLD, 16));
		panel_2.add(btnNewButton_7);
		
		JButton btnNewButton_6 = new JButton("Dokümanlar");
		btnNewButton_6.setFont(new Font("Poppins", Font.BOLD, 15));
		panel_2.add(btnNewButton_6);
		
		JButton btnNewButton_11 = new JButton("Kişisel Bilgiler");
		btnNewButton_11.setFont(new Font("Poppins", Font.BOLD, 15));
		panel_2.add(btnNewButton_11);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_3.setBounds(10, 654, 941, 151);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_11 = new JLabel("Son Durum");
		lblNewLabel_11.setFont(new Font("Cambria", Font.PLAIN, 15));
		lblNewLabel_11.setBounds(10, 11, 79, 16);
		panel_3.add(lblNewLabel_11);
		
		updateBakiye();
		
		
		
		
	}
}
