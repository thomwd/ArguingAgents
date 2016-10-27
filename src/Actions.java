import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxEdgeLabelLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxLayoutManager;
import com.mxgraph.view.mxStylesheet;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JLabel;

public class Actions extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static mxGraph graph = new mxGraph();
	protected static HashMap m = new HashMap();
	private static mxGraphComponent graphComponent;
	private JButton evaluation;
	private JButton restart;
	private JButton changeAT;
	private JButton undoButton = new JButton("Restore");
	private JTextArea textArea;
	private JTextArea textContri;
	private  mxCircleLayout layout;
    private static int selectedCellId = 0;
    private static mxCell selectedCell = null;
    private static ArrayList<Argument> argArrayCopy;
	private static ArrayList<Relation> relArrayCopy;
	private JRadioButton POE;
	private JRadioButton BRD;
	private JRadioButton SOE;
	private JRadioButton None;
	private JRadioButton Binary;
	private JRadioButton Sigmoid;
	private static String mode = "POE";
	private static String threshold = "sigmoid";
	private static boolean evaluated = false;
	
	

	public static HashMap getM() {
		return m;
	}

	public static mxGraph getGraph() {
		return graph;
	}
	
	

	public Actions(ArrayList<Argument> argArray,ArrayList<Relation> relArray,Framework framework){
		super("TEA");
		initGUI(argArray, relArray,framework);
	}
	

	
	

	public void initGUI(ArrayList<Argument> argArray,ArrayList<Relation> relArray,Framework framework) {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		setLocationRelativeTo(null);
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setBounds(5, 5, 1550, 1070);
		layout = new mxCircleLayout(graph);
		//some graph settings
		getContentPane().setLayout(null);
		graphComponent.setPreferredSize(new Dimension(1550, 1070));
		getContentPane().add(graphComponent);
		graph.setCellsDisconnectable(false);
        graph.setCellsEditable(false);
        graph.setCellsResizable(false);
        graph.setDropEnabled(false);
        
		
		for(int i = 0; i<argArray.size();i++){
			String nodeInfo= null;
			Argument argument = argArray.get(i);
			String summary = argument.getSummary();
			String activation = String.valueOf(argument.getActivity());
			String argId = String.valueOf(argument.getArgId());
			nodeInfo = "ArgId: "+argId+"\r\n"+activation; 
			//set the content of a vertex, in this case, each vertex is an argument
			AddNode.addNode(nodeInfo,0,0,argId,argument.getSummary());
		}
		
			
		AddAttackLine.addLine(argArray, relArray);//add the edges(relations) between nodes
				
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
         
         // Implement the layout 
         graph.getModel().beginUpdate();
         try
			{
        	 layout.setRadius(500);
        	 layout.setX0(225);
        	 layout.setY0(3);
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
         
           
 		AddUnderCutLine.addUnderCutLine(argArray, relArray); //add the edges for undercut relations

 		//some settings to the graph
 		graph.setCellsMovable(false);
        graph.setEdgeLabelsMovable(false);
        graph.setAllowDanglingEdges(false);
        graph.setSplitEnabled(false);
        graphComponent.setConnectable(false);
        
        //make the copy of the input for comparing the outcome of the evaluation and original file
        argArrayCopy = copyArgArrayList(argArray);
		relArrayCopy = copyRelArrayList(relArray);
        
        
        evaluation = new JButton("Evaluate");
        evaluation.setBounds(1575, 356, 288, 50);
        evaluation.setFont(new Font("Arial", Font.PLAIN, 20));
        evaluation.setPreferredSize(new Dimension(168, 50));
        evaluation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				//proof methods and threshold selection
				if (POE.isSelected()) {
					mode = "POE";
				}else if (SOE.isSelected()) {
					mode = "SOE";
				}else if (BRD.isSelected()) {
					mode = "BRD";
				}
				
				if (Sigmoid.isSelected()) {
					threshold = "sigmoid";
				}else if (Binary.isSelected()) {
					threshold = "binary";
				}else if (None.isSelected()) {
					threshold = "none";
				}
				
				
				//another copy for evaluation
				ArrayList<Argument> argArrayCopyNew = copyArgArrayList(argArrayCopy);
				ArrayList<Relation> relArrayCopyNew =  copyRelArrayList(relArrayCopy);
					
				
				ArrayList<Relation> relArrayForEvl = new ArrayList<Relation>();
				for(int i=0; i<relArrayCopyNew.size();i++){
					Relation temp = relArrayCopyNew.get(i);
					relArrayForEvl.add(temp);
				}
				
				
				ArrayList<Argument> soList = copyArgArrayList(argArrayCopy);				
				ArrayList<Argument> solution = Framework.evaluate(mode, threshold, argArrayCopyNew, relArrayForEvl,soList); //implement the evaluation.
				
				
				
				String newSummary = null;
				mxCell result = null;
				for (int i = 0; i < solution.size(); i++) {
					result= (mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(solution.get(i).getArgId()+1));
					newSummary = "ArgId: "+solution.get(i).getArgId()+"\r\n"+round(argArrayCopy.get(i).getActivity(), 2)+">>"+round(solution.get(i).getActivity(),4);
					//update the activation
					graph.getModel().setValue(result, newSummary);
					Hashtable<String, Object> styleNodeNew = getNodeColor(argArrayCopy.get(i).getActivity(), solution.get(i).getActivity());
					mxStylesheet stylesheetNodeNew = graph.getStylesheet();
					stylesheetNodeNew.putCellStyle("updatedNodeStyle", styleNodeNew);
					graph.getModel().setStyle(result, "updatedNodeStyle"); // adapt the style for the updated vertex
				}

				ArrayList<mxCell> conclusion = new ArrayList<mxCell>();//create a arrayList storing winning vertexes 
				if (solution.get(0).getActivity()>solution.get(1).getActivity()){
					conclusion.add((mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(solution.get(0).getArgId()+1)));
				}else if (solution.get(0).getActivity()<solution.get(1).getActivity()) {
					conclusion.add((mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(solution.get(1).getArgId()+1)));
				}else {
					conclusion.add((mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(solution.get(0).getArgId()+1)));
					conclusion.add((mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(solution.get(1).getArgId()+1)));
				}
				
				
				// set the styles for the winning vertex
				mxStylesheet stylesheet = graph.getStylesheet();
				Hashtable<String, Object> style = new Hashtable<String,Object>();
				style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
				style.put(mxConstants.STYLE_OPACITY, 50);
				style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
				style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
				style.put(mxConstants.STYLE_FILLCOLOR, "#ADF1D2");
				style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
				style.put(mxConstants.STYLE_FONTSIZE, 17);
				style.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
				stylesheet.putCellStyle("winnerStyle", style);
				
				
				
				for(int i = 0;i<conclusion.size();i++){
					graph.getModel().setStyle(conclusion.get(i), "winnerStyle");// adapt the style for winners
				}
				
				if(conclusion.size() == 1){
					textArea.setText("Winner is:\n"+Framework.getArg(Integer.parseInt(conclusion.get(0).getId())-1,argArrayCopy).getText());
				}
				
				for(int i = 0;i<relArrayCopy.size();i++){
					result= (mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(relArrayCopyNew.get(i).getRelId()+1000));
					newSummary = String.valueOf(Math.abs(round(relArrayCopy.get(i).getWeight(),2)))+" >> "+String.valueOf(Math.abs(round(relArrayCopyNew.get(i).getWeight(),2)));
					graph.getModel().setValue(result, newSummary);
					Hashtable<String, Object> styleEdgeNew = getEdgeColor(relArrayCopyNew.get(i).getWeight());
					mxStylesheet stylesheetEdgeNew = graph.getStylesheet();
					stylesheetEdgeNew.putCellStyle("updateEdgeStyle", styleEdgeNew);
					graph.getModel().setStyle(result, "updateEdgeStyle"); //update the styles for the edges.
				}
				
				evaluated = true;
			    evaluation.setEnabled(false);	
			    undoButton.setEnabled(true);
			    changeAT.setEnabled(false);
			}
		});
        
      
        
        undoButton.setFont(new Font("Arial", Font.PLAIN, 20));
        undoButton.setPreferredSize(new Dimension(168, 50));
        undoButton.setBounds(1575, 482, 288, 50);
        undoButton.setEnabled(false);
        undoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((mxGraphModel)graph.getModel()).clear();
				graph.setCellsMovable(true);
		        graph.setEdgeLabelsMovable(true);
		        graph.setAllowDanglingEdges(true);
		        graph.setSplitEnabled(true);
		        graphComponent.setConnectable(true);
				Actions newAction = new Actions(argArray,relArray,framework); //when user press the restore, current graph closes, a graph with all the default component popup
				Actions.this.dispose();
				newAction.setVisible(true);
				evaluation.setEnabled(true);
				undoButton.setEnabled(false);
				evaluated = false;
			}
		});
        
        
        
        restart = new JButton("Restart");
        restart.setBounds(1575, 545, 288, 50);
        restart.setFont(new Font("Arial", Font.PLAIN, 20));
        restart.setPreferredSize(new Dimension(168, 50));
        restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((mxGraphModel)graph.getModel()).clear();
				graph.setCellsMovable(true);
		        graph.setEdgeLabelsMovable(true);
		        graph.setAllowDanglingEdges(true);
		        graph.setSplitEnabled(true);
		        graphComponent.setConnectable(true);
				Actions.this.dispose();
				Import imports = new Import();	//restart the program from the import window
				imports.setVisible(true);
			}
		});
        
        
        
      
        changeAT = new JButton("New value");
        changeAT.setBounds(1575, 419, 288, 50);
        changeAT.setFont(new Font("Arial", Font.PLAIN, 20));
        changeAT.setPreferredSize(new Dimension(168, 50));
        changeAT.setEnabled(false);
        graph.getSelectionModel().addListener(mxEvent.CHANGE, new mxIEventListener() {
			
			@Override
			public void invoke(Object arg0, mxEventObject arg1) {
				if (graph.getSelectionCell() != null) {
					selectedCellId = Integer.parseInt(((mxCell)graph.getSelectionCell()).getId())-1;
					selectedCell = (mxCell)graph.getSelectionCell();
				}
				
			}
		});
        changeAT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Argument selectedArg = Framework.getArg(selectedCellId, argArrayCopy);
				if(selectedArg != null){
					String v1 = JOptionPane.showInputDialog("Set the activation");
					
					if (v1 != null &&v1.length() >0) {
						String newSummary = selectedArg.getSummary()+"\r\n"+round(Double.parseDouble(v1),2);
						graph.getModel().setValue(selectedCell, newSummary);// set the new activation to the selected vertex
						selectedArg.setActivity(Double.parseDouble(v1));
					}
					
					
				}else{
					String v2 = JOptionPane.showInputDialog("Set the weight");
					if (v2 != null && v2.length() != 0) {
						int selectedRelId = selectedCellId-1000+1;
						Relation selectedRel = Framework.getRel(selectedRelId, relArrayCopy);
						String newSummary = v2;
						graph.getModel().setValue(selectedCell, newSummary);// set the new weight to the selected edge
						selectedRel.setWeight(Double.parseDouble(v2));
					}
										
				}
			}
		});
        
         
        
        graphComponent.getGraphControl().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				mxCell tooltipcell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
				if (tooltipcell != null) {
					int tooltipCellId = Integer.parseInt(tooltipcell.getId())-1;
					Argument tooltipArg = Framework.getArg(tooltipCellId, argArray);
					if (tooltipArg != null) {
						if (tooltipCellId == 1 || tooltipCellId == 2) {
							String text = "Position: "+tooltipCellId+"\r\n"+tooltipArg.getText();
							textArea.setText(text);
						}else{
						String text = "Argument ID: "+tooltipCellId+"\r\n"+tooltipArg.getText();
						textArea.setText(text); //set the full text of the argument in the textArea
						}
						if (evaluated) {
							ArrayList<Argument> updatedPos = framework.argContribution(tooltipCellId, mode, threshold,argArrayCopy,relArrayCopy);//get the vertex contribution
							textContri.setText("Without ArgID: "+tooltipCellId+"\nAct pos1: " + round(updatedPos.get(0).getActivity(),4)+"\nAct pos2: "+round(updatedPos.get(1).getActivity(),4));
							//set the contribution in the textArea.
						}
						
						
					}else{
						tooltipCellId = Integer.parseInt(tooltipcell.getId())-1000;
						String text = "Relation ID: "+tooltipCellId;
						textArea.setText(text);
						textContri.setText("");
					}
					
				}else{
					textArea.setText("");
					textContri.setText("");
				}
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
				if (cell == null) {
					changeAT.setEnabled(false); // some button disable/enable strategies
				}else{
					changeAT.setEnabled(true);
				}
			}
		});       
         
        
        
        
        //some control panel elements initialization
        getContentPane().add(evaluation);
        getContentPane().add(changeAT);
        getContentPane().add(restart);
        getContentPane().add(undoButton);
                
        textArea = new JTextArea();
        textArea.setBounds(1575, 687, 288, 160);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setEditable(false);
        
        getContentPane().add(textArea);
        
        JTextArea lblNewLabel = new JTextArea("New label");
        lblNewLabel.setBounds(1575, 37, 288, 166);
        lblNewLabel.setText("Issue: \n\n"+framework.getTopicDescription());
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel.setLineWrap(true);
        lblNewLabel.setWrapStyleWord(true);
        lblNewLabel.setBackground(getBackground());
        lblNewLabel.setEditable(false);
        
        getContentPane().add(lblNewLabel);
        
        
        textContri = new JTextArea();
        textContri.setBounds(1573, 897, 288, 83);
        textContri.setFont(new Font("Arial", Font.PLAIN, 20));
        textContri.setEditable(false);
        
        getContentPane().add(textContri);
        
        POE = new JRadioButton("POE");
        POE.setSelected(true);
        POE.setBounds(1575, 291, 74, 40);
        POE.setFont(new Font("Arial", Font.PLAIN, 20));
        getContentPane().add(POE);
        
        BRD = new JRadioButton("BRD");
        BRD.setBounds(1681, 291, 74, 40);
        BRD.setFont(new Font("Arial", Font.PLAIN, 20));
        getContentPane().add(BRD);
        
        SOE = new JRadioButton("SOE");
        SOE.setBounds(1789, 291, 74, 40);
        SOE.setFont(new Font("Arial", Font.PLAIN, 20));
        getContentPane().add(SOE);
        
        
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(BRD);
        buttonGroup.add(SOE);
        buttonGroup.add(POE);
        
        
        
        Sigmoid = new JRadioButton("Sigmoid");
        Sigmoid.setSelected(true);
        Sigmoid.setBounds(1575, 233, 100, 40);
        Sigmoid.setFont(new Font("Arial", Font.PLAIN, 20));
        getContentPane().add(Sigmoid);
        
        None = new JRadioButton("None");
        None.setBounds(1789, 233, 74, 40);
        None.setFont(new Font("Arial", Font.PLAIN, 20));
        getContentPane().add(None);
        
        Binary = new JRadioButton("Binary");
        Binary.setBounds(1681, 233, 93, 40);
        Binary.setFont(new Font("Arial", Font.PLAIN, 20));
        getContentPane().add(Binary);
        
        
        ButtonGroup buttonGroupTh = new ButtonGroup();
        buttonGroupTh.add(Sigmoid);
        buttonGroupTh.add(Binary);
        buttonGroupTh.add(None);
        
        JLabel lblContribution = new JLabel("Arg Contribution");
        lblContribution.setBounds(1575, 860, 147, 24);
        lblContribution.setFont(new Font("Arial", Font.PLAIN, 20));
        getContentPane().add(lblContribution);
        
        JLabel lblArgInfobox = new JLabel("Arg Infobox");
        lblArgInfobox.setBounds(1575, 642, 135, 32);
        lblArgInfobox.setFont(new Font("Arial", Font.PLAIN, 20));
        getContentPane().add(lblArgInfobox);
	}

	
	//the auxiliary functions
	
	public static double round(double value, int places) { //round a double by the input digits 
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static ArrayList<Argument> copyArgArrayList(ArrayList<Argument> origin) {//a full copy to the argument arrayList
		ArrayList<Argument> copy = new ArrayList<Argument>();
		for(int i = 0; i<origin.size();i++){
			Argument temp = new Argument(origin.get(i).getArgId(), origin.get(i).getAgentId(), origin.get(i).getText(), origin.get(i).getSummary(), origin.get(i).getActivity());
			copy.add(temp);
		}
		return copy;
	}
	
	public static ArrayList<Relation> copyRelArrayList(ArrayList<Relation> origin) {//a full copy to the relation arrayList
		ArrayList<Relation> copy = new ArrayList<Relation>();
		for(int i = 0; i<origin.size();i++){
			Relation temp = new Relation(origin.get(i).getRelId(), origin.get(i).getOriginId(), origin.get(i).getTargetArgId(), origin.get(i).getTargetRelId()
					, origin.get(i).getWeight(), origin.get(i).getFlag());			
			copy.add(temp);
		}
		return copy;
	}
	
	
	public Hashtable<String, Object> getEdgeColor(double weight) {
		
		
		Hashtable<String, Object> style = new Hashtable<String,Object>();
		style.put(mxConstants.STYLE_FONTCOLOR, "#7A93C1");
		style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		style.put(mxConstants.STYLE_FONTSIZE, 18);
		
		
		
		Hashtable<String, Object> styleNeg = new Hashtable<String,Object>();
		styleNeg.put(mxConstants.STYLE_FONTCOLOR, "#E50B0B");
		styleNeg.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		styleNeg.put(mxConstants.STYLE_FONTSIZE, 18);
		
		
		if(weight>=0){
			return style;
		}else{
			return styleNeg;
		}
		
	}
	
	public Hashtable<String, Object> getNodeColor(double originalAT, double evlAT) {
		

		Hashtable<String, Object> biggerAT = new Hashtable<String,Object>();
		biggerAT.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
		biggerAT.put(mxConstants.STYLE_OPACITY, 50);
		biggerAT.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
		biggerAT.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
		biggerAT.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
		biggerAT.put(mxConstants.STYLE_FONTCOLOR, "#078E6F");
		biggerAT.put(mxConstants.STYLE_FONTSIZE, 15);
		

		Hashtable<String, Object> smallerAT = new Hashtable<String,Object>();
		smallerAT.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
		smallerAT.put(mxConstants.STYLE_OPACITY, 50);
		smallerAT.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
		smallerAT.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
		smallerAT.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
		smallerAT.put(mxConstants.STYLE_FONTCOLOR, "#D63E3E");
		smallerAT.put(mxConstants.STYLE_FONTSIZE, 15);
			
		if(originalAT>evlAT){
			return smallerAT;
		}else{
			return biggerAT;
		}
		
	}
}

