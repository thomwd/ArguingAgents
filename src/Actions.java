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

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxCellHandler;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
	private  mxIGraphLayout layout;
    private static int selectedCellId = 0;
    private static mxCell selectedCell = null;
    private static ArrayList<Argument> argArrayCopy;
	private static ArrayList<Relation> relArrayCopy;
	
	

	public static HashMap getM() {
		return m;
	}

	public static mxGraph getGraph() {
		return graph;
	}
	
	

	public Actions(ArrayList<Argument> argArray,ArrayList<Relation> relArray,Framework framework){
		super("Argumentation Framework");
		initGUI(argArray, relArray,framework);
	}
	

	
	

	public void initGUI(ArrayList<Argument> argArray,ArrayList<Relation> relArray,Framework framework) {
		setSize(1600, 1000);
		setLocationRelativeTo(null);
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setBounds(5, 5, 1319, 1000);
		layout = new mxCircleLayout(graph);
		getContentPane().setLayout(null);
		graphComponent.setPreferredSize(new Dimension(1200, 1000));
		getContentPane().add(graphComponent);
		graph.setCellsDisconnectable(false);
        graph.setCellsEditable(false);
        graph.setCellsResizable(false);
        graph.setDropEnabled(false);
		
		for(int i = 0; i<argArray.size();i++){
			Argument argument = argArray.get(i);
			String summary = argument.getSummary();
			String activation = String.valueOf(argument.getActivity());
			String nodeInfo = summary+"\r\n"+activation;
			String argId = String.valueOf(argument.getArgId());
			AddNode.addNode(nodeInfo,i,i,argId,argument.getSummary());
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

 		graph.setCellsMovable(false);
        graph.setEdgeLabelsMovable(false);
        graph.setAllowDanglingEdges(false);
        graph.setSplitEnabled(false);
        graphComponent.setConnectable(false);
        
        argArrayCopy = copyArgArrayList(argArray);
		relArrayCopy = copyRelArrayList(relArray);
        
        
        evaluation = new JButton("Evaluate");
        evaluation.setBounds(1369, 307, 168, 50);
        evaluation.setFont(new Font("Arial", Font.PLAIN, 20));
        evaluation.setPreferredSize(new Dimension(168, 50));
        evaluation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<Argument> argArrayCopyNew = copyArgArrayList(argArrayCopy);
				ArrayList<Relation> relArrayCopyNew =  copyRelArrayList(relArrayCopy);
					
				ArrayList<Relation> relArrayForEvl = new ArrayList<Relation>();
				for(int i=0; i<relArrayCopyNew.size();i++){
					Relation temp = relArrayCopyNew.get(i);
					relArrayForEvl.add(temp);
				}
				
				
				ArrayList<Argument> soList = copyArgArrayList(argArrayCopy);
				 
				String newSummary = null;
				mxCell result = null;
				
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
				
				
				
				ArrayList<Argument> solution = Framework.evaluate("POE", "none", argArrayCopyNew, relArrayForEvl,soList);			   
				
				
				
				for (int i = 0; i < solution.size(); i++) {
					result= (mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(solution.get(i).getArgId()+1));
					newSummary = solution.get(i).getSummary()+"\r\n"+round(solution.get(i).getActivity(),2);
					graph.getModel().setValue(result, newSummary);
					Hashtable<String, Object> styleNodeNew = getNodeColor(argArrayCopy.get(i).getActivity(), solution.get(i).getActivity());
					mxStylesheet stylesheetNodeNew = graph.getStylesheet();
					stylesheetNodeNew.putCellStyle("updatedNodeStyle", styleNodeNew);
					graph.getModel().setStyle(result, "updatedNodeStyle");
				}
				//mxCell conclusion = null;
				ArrayList<mxCell> conclusion = new ArrayList<mxCell>();
				if (solution.get(0).getActivity()>solution.get(1).getActivity()){
					conclusion.add((mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(solution.get(0).getArgId()+1)));
				}else if (solution.get(0).getActivity()<solution.get(1).getActivity()) {
					conclusion.add((mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(solution.get(1).getArgId()+1)));
				}else {
					conclusion.add((mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(solution.get(0).getArgId()+1)));
					conclusion.add((mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(solution.get(1).getArgId()+1)));
				}
				for(int i = 0;i<conclusion.size();i++){
					graph.getModel().setStyle(conclusion.get(i), "winnerStyle");
				}
				
				for(int i = 0;i<relArrayCopy.size();i++){
					result= (mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(relArrayCopyNew.get(i).getRelId()+1000));
					newSummary = String.valueOf(Math.abs(round(relArrayCopy.get(i).getWeight(),2)))+" >> "+String.valueOf(Math.abs(round(relArrayCopyNew.get(i).getWeight(),2)));
					graph.getModel().setValue(result, newSummary);
					Hashtable<String, Object> styleEdgeNew = getEdgeColor(relArrayCopyNew.get(i).getWeight());
					mxStylesheet stylesheetEdgeNew = graph.getStylesheet();
					stylesheetEdgeNew.putCellStyle("updateEdgeStyle", styleEdgeNew);
					graph.getModel().setStyle(result, "updateEdgeStyle");
				}
				
			    evaluation.setEnabled(false);	
			    undoButton.setEnabled(true);
			    changeAT.setEnabled(false);
			}
		});
        
      
        
        undoButton.setFont(new Font("Arial", Font.PLAIN, 20));
        undoButton.setPreferredSize(new Dimension(168, 50));
        undoButton.setBounds(1369, 433, 168, 50);
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
				Actions newAction = new Actions(argArray,relArray,framework);
				Actions.this.dispose();
				newAction.setVisible(true);
				evaluation.setEnabled(true);
				undoButton.setEnabled(false);
			}
		});
        
        
        
        restart = new JButton("Restart");
        restart.setBounds(1369, 496, 168, 50);
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
				Import imports = new Import();	
				imports.setVisible(true);
			}
		});
        
        
        
      
        changeAT = new JButton("New value");
        changeAT.setBounds(1369, 370, 168, 50);
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
						graph.getModel().setValue(selectedCell, newSummary);
						selectedArg.setActivity(Double.parseDouble(v1));
					}
					
					
				}else{
					String v2 = JOptionPane.showInputDialog("Set the weight");
					if (v2 != null && v2.length() != 0) {
						int selectedRelId = selectedCellId-1000+1;
						Relation selectedRel = Framework.getRel(selectedRelId, relArrayCopy);
						String newSummary = v2;
						graph.getModel().setValue(selectedCell, newSummary);
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
						String text = "Argument ID: "+tooltipCellId+"\r\n"+tooltipArg.getText();
						textArea.setText(text);
					}else{
						tooltipCellId = Integer.parseInt(tooltipcell.getId())-1000;
						String text = "Relation ID: "+tooltipCellId;
						textArea.setText(text);
					}
					
				}else{
					textArea.setText("");
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
					changeAT.setEnabled(false);
				}else{
					changeAT.setEnabled(true);
				}
			}
		});       
         
        getContentPane().add(evaluation);
        getContentPane().add(changeAT);
        getContentPane().add(restart);
        getContentPane().add(undoButton);
                
        textArea = new JTextArea();
        textArea.setBounds(1338, 594, 230, 160);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setEditable(false);
        
        getContentPane().add(textArea);
        
        JTextArea lblNewLabel = new JTextArea("New label");
        lblNewLabel.setBounds(1338, 96, 230, 134);
        lblNewLabel.setText(framework.getTopicDescription());
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel.setLineWrap(true);
        lblNewLabel.setWrapStyleWord(true);
        lblNewLabel.setBackground(getBackground());
        lblNewLabel.setEditable(false);
        
        getContentPane().add(lblNewLabel);
	}

	

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static ArrayList<Argument> copyArgArrayList(ArrayList<Argument> origin) {
		ArrayList<Argument> copy = new ArrayList<Argument>();
		for(int i = 0; i<origin.size();i++){
			Argument temp = new Argument(origin.get(i).getArgId(), origin.get(i).getAgentId(), origin.get(i).getText(), origin.get(i).getSummary(), origin.get(i).getActivity());
			copy.add(temp);
		}
		return copy;
	}
	
	public static ArrayList<Relation> copyRelArrayList(ArrayList<Relation> origin) {
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
		biggerAT.put(mxConstants.STYLE_FONTSIZE, 16);
		

		Hashtable<String, Object> smallerAT = new Hashtable<String,Object>();
		smallerAT.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
		smallerAT.put(mxConstants.STYLE_OPACITY, 50);
		smallerAT.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
		smallerAT.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
		smallerAT.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
		smallerAT.put(mxConstants.STYLE_FONTCOLOR, "#D63E3E");
		smallerAT.put(mxConstants.STYLE_FONTSIZE, 16);
			
		if(originalAT>evlAT){
			return smallerAT;
		}else{
			return biggerAT;
		}
		
	}
}

