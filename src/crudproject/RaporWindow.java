package crudproject;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.RepaintManager;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import java.text.MessageFormat;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class RaporWindow extends JFrame {
	private int selectedID = -1;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JFrame tableWindow;
	private JTable table1 = new JTable();
	private JTable table2 = new JTable();
	private static final String TABLE_NAME = "Rehber";
	private JComboBox combobox = new JComboBox();
	private JComboBox comboBoxSehir = new JComboBox();
	/**
	 * Launch the application.
	 */
	private JTextField currentTarget;   
	private static final String KOD_COLUMN = "Kod";

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

        try { tbl.getColumnModel().getColumn(tbl.getColumnModel().getColumnIndex("BorcTutar")).setCellRenderer(currencyRenderer); } catch (IllegalArgumentException ignore) {}
        try { tbl.getColumnModel().getColumn(tbl.getColumnModel().getColumnIndex("AlacakTutar")).setCellRenderer(currencyRenderer); } catch (IllegalArgumentException ignore) {}
    }


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RaporWindow frame = new RaporWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RaporWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 445, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Kod:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 54, 44, 33);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(53, 54, 130, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(235, 54, 137, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Başlangıç");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(64, 31, 119, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Bitiş");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(235, 31, 137, 20);
		contentPane.add(lblNewLabel_2);
		
		JRadioButton rdbtnGrid = new JRadioButton("Liste");
		rdbtnGrid.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnGrid.setBounds(45, 148, 109, 23);
		contentPane.add(rdbtnGrid);
		
		JRadioButton rdbtnMizan = new JRadioButton("Mizan");
		rdbtnMizan.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnMizan.setBounds(45, 193, 109, 23);
		contentPane.add(rdbtnMizan);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnGrid);
		group.add(rdbtnMizan);
		
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    if (currentTarget == null) return;
                    int viewRow = table1.getSelectedRow();
                    if (viewRow < 0) return;
                    int modelRow = table1.convertRowIndexToModel(viewRow);
                    DefaultTableModel m = (DefaultTableModel) table1.getModel();
                    int colIndex = m.findColumn(KOD_COLUMN);
                    if (colIndex < 0) return;
                    Object val = m.getValueAt(modelRow, colIndex);
                    currentTarget.setText(val == null ? "" : val.toString());
                    currentTarget = null;
                    if (tableWindow != null) tableWindow.dispose();
                }
            }
        });
		


		
		JButton btnGrid = new JButton();
		btnGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String adresKodu = textField.getText();
				String adresKodu2 = textField_1.getText();
				if(rdbtnGrid.isSelected()) {
					if (tableWindow == null) {
						tableWindow = new JFrame("Mizan Table");
			            tableWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			            JLayeredPane layeredPane = new JLayeredPane();
			            layeredPane.setPreferredSize(new Dimension(1000, 700));
			            
			            
			            JScrollPane scrollPane = new JScrollPane(table1);
			            scrollPane.setBounds(0, 40, 1000, 660); 
			            layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);
			            
			            
			            JPanel buttonPanel = new JPanel();
			            buttonPanel.setLayout(null);
			            buttonPanel.setOpaque(false); 
			            buttonPanel.setBounds(850, 5, 140, 35); 
			            
			            JLabel lblTitle= new JLabel("MİZAN RAPORU");
			            lblTitle.setBounds(35,0,10,15);
			            JButton btnprint2 = new JButton();
			            btnprint2.setBounds(35, 0, 30, 30); 
			            btnprint2.setToolTipText("Print");
			            
			            ImageIcon printIcon = new ImageIcon(getClass().getResource("159287.png"));
			            Image img1 = printIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
			            btnprint2.setIcon(new ImageIcon(img1));
			            
			            btnprint2.addActionListener(new ActionListener() {
			                public void actionPerformed(ActionEvent evt) {
			                	String a1 = textField.getText().trim();
			    		        String a2 = textField_1.getText().trim();
			                	try {
			                		DefaultTableModel m = new GridList(TABLE_NAME, a1, a2).execute(); // veya MizanRapor
			                		table1.setModel(m);
			                		table1.setAutoCreateRowSorter(true);
			                		applyCurrencyRenderer(table1);
			                		PrintUtils.printTableSafe(table1, "Liste");
			    		        } catch (SQLException ex) {
			    		            JOptionPane.showMessageDialog(btnprint2, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
			    		        }
			                    
			                }
			            });
			            JButton btnRefresh = new JButton();
			            btnRefresh.setBounds(70, 0, 30, 30);
			            btnRefresh.setToolTipText("Refresh");
			            
			            ImageIcon exportIcon = new ImageIcon(getClass().getResource("Refresh_icon.svg.png"));
			            Image img2 = exportIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
			            btnRefresh.setIcon(new ImageIcon(img2));
			            
			            btnRefresh.addActionListener(new ActionListener() {
			                public void actionPerformed(ActionEvent evt) {
			                	try {
			                        DefaultTableModel m = new GridList(TABLE_NAME, adresKodu, adresKodu2).execute();
			                        table1.setModel(m);
			                        table1.setAutoCreateRowSorter(true);
			                        applyCurrencyRenderer(table1);
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(tableWindow, "Hata: " + ex.getMessage());
			                    }
			                }
			            });

			            buttonPanel.add(btnRefresh);
			            buttonPanel.add(btnprint2);
			            buttonPanel.add(lblTitle);
			            layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);
			            
			           
			            tableWindow.getContentPane().add(layeredPane);
			            tableWindow.setSize(1000, 700);
			            tableWindow.setLocation(1234, 200);
			        }
			        tableWindow.setVisible(true);
			        try {
			            DefaultTableModel m = new GridList(TABLE_NAME, adresKodu, adresKodu2).execute();
			            table1.setModel(m);
			            table1.setAutoCreateRowSorter(true);
			            applyCurrencyRenderer(table1);
			            table1.addMouseListener(new java.awt.event.MouseAdapter() {
			                
			            });
			        } catch (SQLException ex) {
			            JOptionPane.showMessageDialog(tableWindow, "Hata: " + ex.getMessage(), "hata: ", JOptionPane.ERROR_MESSAGE);
			        
					}
				}
				else if(rdbtnMizan.isSelected()) {
					if (tableWindow == null) {
						tableWindow = new JFrame("TABLE");
			            tableWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			            JLayeredPane layeredPane = new JLayeredPane();
			            layeredPane.setPreferredSize(new Dimension(1000, 1000));
			            
			            
			            JScrollPane scrollPane = new JScrollPane(table1);
			            scrollPane.setBounds(0, 40, 1000, 750); 
			            layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);
			            
			            
			            JPanel buttonPanel = new JPanel();
			            buttonPanel.setLayout(null);
			            buttonPanel.setOpaque(false); 
			            buttonPanel.setBounds(850, 5, 140, 35); 
			            
			            JButton btnprint1 = new JButton();
			            btnprint1.setBounds(35, 0, 30, 30); 
			            btnprint1.setToolTipText("Print");
			            
			            ImageIcon printIcon = new ImageIcon(getClass().getResource("159287.png"));
			            Image img1 = printIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
			            btnprint1.setIcon(new ImageIcon(img1));
			            
			            btnprint1.addActionListener(new ActionListener() {
			            	public void actionPerformed(ActionEvent evt) {
			                	String a1 = textField.getText().trim();
			    		        String a2 = textField_1.getText().trim();
			    		        try {
			    		            DefaultTableModel m;
			    		            if (rdbtnGrid.isSelected()) {
			    		                m = new GridList(TABLE_NAME, a1, a2).execute();
			    		            } else if (rdbtnMizan.isSelected()) {
			    		                m = new MizanRapor(TABLE_NAME, a1, a2).execute();
			    		            } else {
			    		                JOptionPane.showMessageDialog(RaporWindow.this, "Lütfen 'Liste' veya 'Mizan' seçin.");
			    		                return;
			    		            }
			    		            table1.setModel(m);
			    		            table1.setAutoCreateRowSorter(true);
			    		            applyCurrencyRenderer(table1);
			    		            PrintUtils.printTableSafe(table1, rdbtnMizan.isSelected() ? "Mizan Raporu" : "Liste");

			    		        } catch (SQLException ex) {
			    		            JOptionPane.showMessageDialog(RaporWindow.this, "Veritabanı hatası: " + ex.getMessage(),
			    		                                          "Hata", JOptionPane.ERROR_MESSAGE);
			    		        }
			                    
			                }
			            });
			            JButton btnRefresh = new JButton();
			            btnRefresh.setBounds(70, 0, 30, 30);
			            btnRefresh.setToolTipText("Refresh");
			            
			            ImageIcon exportIcon = new ImageIcon(getClass().getResource("Refresh_icon.svg.png"));
			            Image img2 = exportIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
			            btnRefresh.setIcon(new ImageIcon(img2));
			            
			            btnRefresh.addActionListener(new ActionListener() {
			                public void actionPerformed(ActionEvent evt) {
			                	try {
			                        DefaultTableModel m = new MizanRapor(TABLE_NAME, adresKodu, adresKodu2).execute();
			                        table1.setModel(m);
			                        table1.setAutoCreateRowSorter(true);
			                        applyCurrencyRenderer(table1);
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(tableWindow, "Hata: " + ex.getMessage());
			                    }
			                }
			            });

			            buttonPanel.add(btnRefresh);
			            buttonPanel.add(btnprint1);

			            layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);
			            
			           
			            tableWindow.getContentPane().add(layeredPane);
			            tableWindow.setSize(1000, 828);
			            tableWindow.setLocation(1234, 200);
			        }
					tableWindow.setVisible(true);
					try {
			            DefaultTableModel m = new MizanRapor(TABLE_NAME, adresKodu, adresKodu2).execute();
			            table1.setModel(m);
			            table1.setAutoCreateRowSorter(true);
			            applyCurrencyRenderer(table1);
			            table1.addMouseListener(new java.awt.event.MouseAdapter() {
			                
			            });
			        } catch (SQLException ex) {
			            JOptionPane.showMessageDialog(tableWindow, "Hata: " + ex.getMessage(), "hata: ", JOptionPane.ERROR_MESSAGE);
			        
					}
				}
				else {
					JOptionPane.showMessageDialog(btnGrid, "HATA!");
				}
			}
		});
		btnGrid.setToolTipText("Show Grid");
		btnGrid.setBounds(45, 271, 73, 71);
		contentPane.add(btnGrid);
		ImageIcon gridIcon = new ImageIcon(getClass().getResource("table (1).png"));
        Image img2 = gridIcon.getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        btnGrid.setIcon(new ImageIcon(img2));
		
		JButton btnPrint = new JButton();
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a1 = textField.getText().trim();
		        String a2 = textField_1.getText().trim();
		        try {
		            DefaultTableModel m;
		            if (rdbtnGrid.isSelected()) {
		                m = new GridList(TABLE_NAME, a1, a2).execute();
		            } else if (rdbtnMizan.isSelected()) {
		                m = new MizanRapor(TABLE_NAME, a1, a2).execute();
		            } else {
		                JOptionPane.showMessageDialog(RaporWindow.this, "Lütfen 'Liste' veya 'Mizan' seçin.");
		                return;
		            }
		            table1.setModel(m);
		            table1.setAutoCreateRowSorter(true);
		            applyCurrencyRenderer(table1);
		            PrintUtils.printTableSafe(table1, rdbtnMizan.isSelected() ? "Mizan Raporu" : "Liste");

		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(RaporWindow.this, "Veritabanı hatası: " + ex.getMessage(),
		                                          "Hata", JOptionPane.ERROR_MESSAGE);
		        }
				
			}
		});
		ImageIcon printIcon = new ImageIcon(getClass().getResource("159287.png"));
        Image img1 = printIcon.getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        btnPrint.setIcon(new ImageIcon(img1));
		btnPrint.setToolTipText("Print");
		btnPrint.setBounds(272, 271, 73, 71);
		contentPane.add(btnPrint);
		
		JButton btnNewButton = new JButton("");
		ImageIcon buyutecicon = new ImageIcon(getClass().getResource("buyutec.png"));
        Image img3 = buyutecicon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
        btnNewButton.setIcon(new ImageIcon(img3));
        btnNewButton.addActionListener(new ActionListener() {	
            public void actionPerformed(ActionEvent e) {
                currentTarget = textField; // başlangıç kodu hedefi
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
		            	    new String[]{"Hepsi","Kod","Adi","Adresi","Sehir","Semt","Telefon","BorcTutar","AlacakTutar","Notu","Ozel Alan Grup"}
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
									table1.setAutoCreateRowSorter(true);
									applyCurrencyRenderer(table1);
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
								table1.setAutoCreateRowSorter(true);
								applyCurrencyRenderer(table1);
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
								table1.setAutoCreateRowSorter(true);
								applyCurrencyRenderer(table1);
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
							table1.setAutoCreateRowSorter(true);
							applyCurrencyRenderer(table1);
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
		                        table1.setAutoCreateRowSorter(true);
		                        applyCurrencyRenderer(table1);
		                    
		                	
		                	} catch (SQLException ex) {
		                        JOptionPane.showMessageDialog(tableWindow, "Hata: " + ex.getMessage());
		                    }
		                	textField_1.setText("");
		        			
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
		            table1.setAutoCreateRowSorter(true);
		            applyCurrencyRenderer(table1);
		            table1.addMouseListener(new java.awt.event.MouseAdapter() {
		               
		            	
		            });
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(tableWindow, "Hata: " + ex.getMessage(), "hata: ", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnNewButton.setBounds(193, 54, 32, 28);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		ImageIcon buyutecicon2 = new ImageIcon(getClass().getResource("buyutec.png"));
        Image img4 = buyutecicon2.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
        btnNewButton_1.setIcon(new ImageIcon(img4));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentTarget = textField_1; // bitiş kodu hedefi
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
		            	    new String[]{"Hepsi","Kod","Adi","Adresi","Sehir","Semt","Telefon","BorcTutar","AlacakTutar","Notu","Ozel Alan Grup"}
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
									table1.setAutoCreateRowSorter(true);
									applyCurrencyRenderer(table1);
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
								table1.setAutoCreateRowSorter(true);
								applyCurrencyRenderer(table1);
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
								table1.setAutoCreateRowSorter(true);
								applyCurrencyRenderer(table1);
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
							table1.setAutoCreateRowSorter(true);
							applyCurrencyRenderer(table1);
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
		                        table1.setAutoCreateRowSorter(true);
		                        applyCurrencyRenderer(table1);
		                    
		                	
		                	} catch (SQLException ex) {
		                        JOptionPane.showMessageDialog(tableWindow, "Hata: " + ex.getMessage());
		                    }
		                	textField_1.setText("");
		        			
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
		            table1.setAutoCreateRowSorter(true);
		            applyCurrencyRenderer(table1);
		            table1.addMouseListener(new java.awt.event.MouseAdapter() {
		                
		            });
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(tableWindow, "Hata: " + ex.getMessage(), "hata: ", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnNewButton_1.setBounds(382, 54, 32, 28);
		contentPane.add(btnNewButton_1);
		btnNewButton.addActionListener(e ->{
			currentTarget=textField;
			
		});
	btnNewButton_1.addActionListener(e ->{
		currentTarget=textField_1;
		
	});
	}
}
