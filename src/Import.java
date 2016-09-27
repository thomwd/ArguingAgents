import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import org.w3c.dom.Document;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Import extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFileChooser chooser; 
	Document document = null;


	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Import frame = new Import();
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
	public Import() {
		chooser = new JFileChooser();
		setTitle("Group10-AA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 671, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnImportXml = new JButton("Browse");
		btnImportXml.setBounds(157, 109, 168, 39);
		btnImportXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnImportXml.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int value = chooser.showOpenDialog(Import.this);
				chooser.setMultiSelectionEnabled(true);
				if(value == JFileChooser.APPROVE_OPTION){
					File file = chooser.getSelectedFile();
					try {
						document = ProcessXML.parse(file.getAbsolutePath());
					} catch (DocumentException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnImportXml);
		
		JButton btnImport = new JButton("Next");
		btnImport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ArrayList<Argument> argArray= new ArrayList<Argument>();
				ArrayList<Relation> relArray= new ArrayList<Relation>();
				argArray = ProcessXML.getArgument(document);
				relArray = ProcessXML.getRelation(document);
				Mapping map = new Mapping(argArray,relArray);
				Import.this.dispose();
				map.setVisible(true);

			}
		});
		btnImport.setBounds(157, 212, 168, 39);
		contentPane.add(btnImport);
		
		JLabel lblNewLabel = new JLabel("Import XML");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(14, 114, 117, 29);
		contentPane.add(lblNewLabel);
	}
	
}
