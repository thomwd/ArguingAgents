import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;


import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;


public class Actions extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static mxGraph graph = new mxGraph();
	protected static HashMap m = new HashMap();
	private mxGraphComponent graphComponent;
	ArrayList<Argument> argArray;
	ArrayList<Relation> relArray;
	private JButton evaluation;
	
	public static HashMap getM() {
		return m;
	}

	public static mxGraph getGraph() {
		return graph;
	}

	public Actions(ArrayList<Argument> argArray,ArrayList<Relation> relArray,Framework framework){
		super("Argumentation Framework");
		initGUI(argArray,relArray,framework);
	}

	private void initGUI(ArrayList<Argument> argArray,ArrayList<Relation> relArray,Framework framework) {
		setSize(1400, 1000);
		setLocationRelativeTo(null);
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setPreferredSize(new Dimension(1400, 800));
		getContentPane().add(graphComponent);
		graph.setCellsDisconnectable(false);
        graph.setCellsEditable(false);
        graph.setCellsResizable(false);
		
		
		for(int i = 0; i<argArray.size();i++){
			Argument argument = argArray.get(i);
			String summary = argument.getSummary();
			String activation = String.valueOf(argument.getActivity());
			String nodeInfo = summary+"\r\n"+activation;
			String argId = String.valueOf(argument.getArgId());
			AddNode.addNode(nodeInfo,i*50,i*60,argId);
		}
		
		
		AddLine.addLine(argArray, relArray);		
		
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
		
			public void mouseReleased(MouseEvent e)
			{
				graphComponent.getCellAt(e.getX(), e.getY());		
			}
		});
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        evaluation = new JButton("Evaluation");
        getContentPane().add(evaluation);
        evaluation.setPreferredSize(new Dimension(100, 50));
        evaluation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//framework.showLeaf(argArray, relArray);
				ArrayList<Argument> solution = framework.evaluate("POE", 0, argArray, relArray,argArray);
				for (int i = 0; i < solution.size(); i++) {
					int argId =  solution.get(i).getArgId();
					String summary = solution.get(i).getSummary();
					System.out.println("Conclusion: argID: "+argId+" summary: "+summary+" activation: "+solution.get(i).getActivity());
				}
			}
		});
        
        
        
	}

}