import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Mapping extends JFrame {

	private JPanel contentPane;
	private JTable table_arg;
	private JScrollPane scrollPane;
	private JScrollPane scrollPaneCon;
	int iForTableCon = 0;
	int iForTableArg = 0;

	ArrayList<Argument> argArray;
	String subject;
	ArrayList<Conclusion> conArray;
	private JTable table_con;

	
	
	
	
	public Mapping(ArrayList<Argument> argArray,String subject,ArrayList<Conclusion> conArray) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.argArray = argArray;
		this.subject = subject;
		this.conArray = conArray;
		String[] columns = {"ArgText","ArgId","Origin","Weight","Type"};
		String[][] data = new String[argArray.size()][];
		String[] columnsTableCon = {"Conclusion","Type"};
		String[][] dataTableCon = new String[conArray.size()][];
		for(iForTableArg = 0; iForTableArg<argArray.size();iForTableArg++){
			Argument row = argArray.get(iForTableArg);
			data[iForTableArg] = new String[]{row.getText(),row.getArgId(),row.getOrigin(),row.getTextWt(),row.getHypothType()};
		}
		
		for(iForTableCon = 0; iForTableCon<conArray.size();iForTableCon++){
			Conclusion row = conArray.get(iForTableCon);
			dataTableCon[iForTableCon] = new String[]{row.getConclusionText(),row.getType()};
		}
		
		table_con = new JTable(dataTableCon,columnsTableCon){
			public boolean isCellEditable(int data, int columns) {
				return false;
			}
		};
		table_con.setPreferredScrollableViewportSize(new Dimension(450,63));
		table_con.setFillsViewportHeight(true);
		table_con.getColumnModel().getColumn(0).setPreferredWidth(300);
		table_con.setRowHeight(30);
		table_con.setFont(new Font("Serif", Font.PLAIN, 15));
		table_con.getTableHeader().setFont(new Font("Serif",Font.BOLD,20));
		table_con.getTableHeader().setDefaultRenderer(new HeaderRenderer(table_con));
		scrollPaneCon = new JScrollPane();
		scrollPaneCon.setBounds(20, 100, 700, table_con.getRowHeight()*(iForTableCon + 1));
		contentPane.add(scrollPaneCon);
		scrollPaneCon.setViewportView(table_con);
		
		
		
		
		table_arg = new JTable(data,columns){
			public boolean isCellEditable(int data, int columns) {
				return false;
			}
			
			public Component prepareRenderer(TableCellRenderer r,int data, int columns) {
				Component component = super.prepareRenderer(r, data, columns);
				if (data%2==0) {
					component.setBackground(Color.WHITE);
				}
				else{
					component.setBackground(Color.LIGHT_GRAY);
				}
				return component;
			}
		};
		table_arg.setPreferredScrollableViewportSize(new Dimension(450,63));
		table_arg.setFillsViewportHeight(true);
		table_arg.getColumnModel().getColumn(0).setPreferredWidth(300);
		table_arg.setRowHeight(30);
		table_arg.setFont(new Font("Serif", Font.PLAIN, 15));
		table_arg.getTableHeader().setFont(new Font("Serif",Font.BOLD,20));
		table_arg.getTableHeader().setDefaultRenderer(new HeaderRenderer(table_arg));
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 300, 1000, table_arg.getRowHeight()*(iForTableArg + 1));
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table_arg);
		
		
		
		
		JLabel lblNewLabel = new JLabel("Subject:");
		lblNewLabel.setFont(new Font("Serif", Font.PLAIN, 22));
		lblNewLabel.setBounds(20, 23, 82, 61);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(subject);
		lblNewLabel_1.setFont(new Font("Serif", Font.PLAIN, 22));
		lblNewLabel_1.setBounds(116, 23, 640, 61);
		contentPane.add(lblNewLabel_1);
			
		
	}

	private static class HeaderRenderer implements TableCellRenderer {

	    DefaultTableCellRenderer renderer;

	    public HeaderRenderer(JTable table) {
	        renderer = (DefaultTableCellRenderer)
	            table.getTableHeader().getDefaultRenderer();
	        renderer.setHorizontalAlignment(JLabel.LEFT);
	    }

	    @Override
	    public Component getTableCellRendererComponent(
	        JTable table, Object value, boolean isSelected,
	        boolean hasFocus, int row, int col) {
	        return renderer.getTableCellRendererComponent(
	            table, value, isSelected, hasFocus, row, col);
	    }
	}
}
 