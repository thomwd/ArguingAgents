import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
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
			String nodeInfo = summary+"\r\n"+activition;
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
        graph.setCellsDisconnectable(false);
        graph.setCellsEditable(false);
        graph.setCellsResizable(false);
	}

}