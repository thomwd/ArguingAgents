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
	private JTable table;
	private JScrollPane scrollPane;
	ArrayList<Argument> argArray;
	String subject;
	String[] conclusion;

	
	public Mapping(ArrayList<Argument> argArray,String subject,String[] conclusion) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.argArray = argArray;
		this.subject = subject;
		this.conclusion = conclusion;
		String[] columns = {"ArgText","ArgId","Origin","Weight","HypothType"};
		String[][] data = new String[argArray.size()][];
		for(int i = 0; i<argArray.size();i++){
			Argument row = argArray.get(i);
			data[i] = new String[]{row.getText(),row.getArgId(),row.getOrigin(),row.getTextWt(),row.getHypothType()};
		}
		table = new JTable(data,columns){
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
		table.setPreferredScrollableViewportSize(new Dimension(450,63));
		table.setFillsViewportHeight(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(300);
		table.setRowHeight(30);
		table.setFont(new Font("Serif", Font.PLAIN, 15));
		table.getTableHeader().getPreferredSize().height = 40;
		table.getTableHeader().setFont(new Font("Serif",Font.BOLD,20));
		table.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 100, 1000, 500);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		
		
		
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
 