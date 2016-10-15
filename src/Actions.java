import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
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
		mxIGraphLayout layout = new mxCircleLayout(graph);
		graphComponent.setPreferredSize(new Dimension(1100, 1000));
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
			AddNode.addNode(nodeInfo,i,i,argId);
		}
		
		
		
		AddAttackLine.addLine(argArray, relArray);
				
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
		
			public void mouseReleased(MouseEvent e)
			{
				graphComponent.getCellAt(e.getX(), e.getY());		
			}
		});
        
        
         Object cell = graph.getSelectionCell();
         if (cell == null
					|| graph.getModel().getChildCount(cell) == 0)
			{
				cell = graph.getDefaultParent();
			}
         graph.getModel().beginUpdate();
         try
			{
				layout.execute(cell);
			}
         finally
			{
				mxMorphing morph = new mxMorphing(graphComponent, 1,
						1.2, 40);

				morph.addListener(mxEvent.DONE, new mxIEventListener()
				{

					public void invoke(Object sender, mxEventObject evt)
					{
						graph.getModel().endUpdate();
					}

				});

				morph.startAnimation();
			}
         
           
 		AddUnderCutLine.addUnderCutLine(argArray, relArray);

        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        evaluation = new JButton("Evaluate");
        evaluation.setFont(new Font("Arial", Font.PLAIN, 20));
        getContentPane().add(evaluation);
        evaluation.setPreferredSize(new Dimension(168, 50));
        evaluation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//framework.showLeaf(argArray, relArray);
				ArrayList<Argument> soList = new ArrayList<Argument>();
				for(int i = 0; i<argArray.size();i++){
					Argument temp = argArray.get(i);
					soList.add(temp);
				}
				System.out.println("size of solist: "+soList.size());
				ArrayList<Argument> solution = framework.evaluate("POE", 0, argArray, relArray,soList);
				for (int i = 0; i < solution.size(); i++) {
					int argId =  solution.get(i).getArgId();
					String summary = solution.get(i).getSummary();
					System.out.println("Conclusion: argID: "+argId+" summary: "+summary+" activation: "+solution.get(i).getActivity());
				}
			}
		});
        
//        for(int i = 1;i<=argArray.size();i++){
//        	mxCell myCell = (mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(i+1));// vertex id for each arg is argId +1
//            String id = (String) myCell.getValue();
//            double x = myCell.getGeometry().getCenterX();
//            double y = myCell.getGeometry().getCenterY();
//    		System.out.println("value is : "+id);
//    		System.out.println("x: "+x+"y: "+y);
//        }
        
		
        
	}

}