import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;


public class Actions extends JFrame {
	protected static mxGraph graph = new mxGraph();
	protected static HashMap m = new HashMap();
	private mxGraphComponent graphComponent;
	private JTextField texto;
	private JButton botaoAdd;
	private JButton botaoDel;
	private JButton botaoLigar;
	private Object cell;	
	ArrayList<Argument> argArray;
	ArrayList<Conclusion> conArray;
	ArrayList<Relation> relArray;
	
	public static HashMap getM() {
		return m;
	}

	public static mxGraph getGraph() {
		return graph;
	}

	public Actions(ArrayList<Argument> argArray,ArrayList<Relation> relArray){
		super("Argumentation Framework");
		initGUI(argArray,relArray);
	}

	private void initGUI(ArrayList<Argument> argArray,ArrayList<Relation> relArray) {
		setSize(700, 500);
		setLocationRelativeTo(null);
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setPreferredSize(new Dimension(670, 380));
		getContentPane().add(graphComponent);
		
		
		for(int i = 0; i<argArray.size();i++){
			Argument argument = argArray.get(i);
			String summary = argument.getSummary();
			String activition = String.valueOf(argument.getActivity());
			String nodeInfo = summary + "\r\n"+activition;
			AddNode.addNode(nodeInfo,i*50,i*60);
		}
		
		AddLine.addLine(argArray, relArray);		
		
		
//		texto = new JTextField();
//		getContentPane().add(texto);
//        texto.setPreferredSize(new Dimension(420, 21));
//        setLayout(new FlowLayout(FlowLayout.LEFT));
//		
//        botaoAdd = new JButton("Add");
//        getContentPane().add(botaoAdd);
//        botaoAdd.addActionListener(new ActionListener() {
//                        
//            public void actionPerformed(ActionEvent e) {
//            	AddNode add = new AddNode(texto.getText());
//                texto.setText("");
//            }
//        });
//        
//        botaoDel = new JButton("Delete");
//        getContentPane().add(botaoDel);
//        botaoDel.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				graph.getModel().remove(cell);
//				
//			}
//		});
//        
//        botaoLigar = new JButton("Relation");
//        getContentPane().add(botaoLigar);
//        botaoLigar.addActionListener(new ActionListener() {
//            
//            public void actionPerformed(ActionEvent e) {
//               AddLine linha = new AddLine();
//            	
//            }
//        });
        
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
		
			public void mouseReleased(MouseEvent e)
			{
				cell = graphComponent.getCellAt(e.getX(), e.getY());		
			}
		});
	}

}