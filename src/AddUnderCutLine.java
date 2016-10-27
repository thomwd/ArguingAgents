
import java.util.ArrayList;
import java.util.Hashtable;
import java.lang.Math;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;
public class AddUnderCutLine extends Actions {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void  addUnderCutLine(ArrayList<Argument> argArray, ArrayList<Relation> relArray) {
		mxStylesheet stylesheet = graph.getStylesheet();
		Hashtable<String, Object> style = new Hashtable<String,Object>();//style for the support relation
		style.put(mxConstants.STYLE_FONTCOLOR, "#7A93C1");
		style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		style.put(mxConstants.STYLE_FONTSIZE, 18);
		stylesheet.putCellStyle("lineStyle", style);
		
		
		Hashtable<String, Object> styleNeg = new Hashtable<String,Object>();//style for the attack relation
		styleNeg.put(mxConstants.STYLE_FONTCOLOR, "#E50B0B");
		styleNeg.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		styleNeg.put(mxConstants.STYLE_FONTSIZE, 18);
		stylesheet.putCellStyle("lineStyleNeg", styleNeg);
		
		
		
		
		Object parent = getGraph().getDefaultParent();
		for(int i = 0; i<relArray.size();i++){ 
			Object originArg = null;
			Object targetRelCell = null;
			Relation relation = relArray.get(i);
			int originArgId = relation.getOriginId();
			String relId = String.valueOf(relation.getRelId()+1000);
			int targetRelId = relation.getTargetRelId();
			String weight = String.valueOf(relation.getWeight());
			for(int j = 0;j<argArray.size();j++){ 
				if (argArray.get(j).getArgId() == originArgId) {
				originArg = getM().get("ArgId: "+argArray.get(j).getArgId()+"\r\n"+argArray.get(j).getActivity());
				//search for the original vertex of the undercut
				}
			}
			//JGraph doesn't directly support the edge between the another edge and vertex 
			//In order to draw the undercut line, first create a invisible node between two vertex and link to that node from original vertex														
			for(int q = 0;q<relArray.size();q++){ 
				if (relArray.get(q).getRelId() == targetRelId && targetRelId != 0){
					if (Framework.getRel(targetRelId, relArray).getTargetRelId() != 0) {// this is the case for undercuting another undercutting
						Relation targetRel = Framework.getRel(targetRelId, relArray);
						Relation targetRelOfTargetRel = Framework.getRel(targetRel.getTargetRelId(),relArray);
						int front = targetRelOfTargetRel.getOriginId();
						int end = targetRelOfTargetRel.getTargetArgId();
						mxCell frontCell = (mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(front+1));
						mxCell endCell = (mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(end+1));
						double xRelOriginCell = frontCell.getGeometry().getCenterX();
			            double yRelOriginCell = frontCell.getGeometry().getCenterY();
			            double xRelTargetCell = endCell.getGeometry().getCenterX();
			            double yRelTargetCell = endCell.getGeometry().getCenterY();
			            double xMiddlePointRel = Math.min(xRelOriginCell,xRelTargetCell)+Math.abs(xRelOriginCell-xRelTargetCell)/2;
			            double yMiddlePointRel = Math.min(yRelOriginCell,yRelTargetCell)+Math.abs(yRelOriginCell-yRelTargetCell)/2;
			            int targetRelOriginId = targetRel.getOriginId();
						mxCell targetRelOriginCell = (mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(targetRelOriginId+1));
						double xRelOriginCellFinal = targetRelOriginCell.getGeometry().getCenterX();
			            double yRelOriginCellFinal = targetRelOriginCell.getGeometry().getCenterY();
			            double xMiddleCell = Math.min(xRelOriginCellFinal,xMiddlePointRel)+Math.abs(xRelOriginCellFinal-xMiddlePointRel)/2;
			            double yMiddleCell = Math.min(yRelOriginCellFinal,yMiddlePointRel)+Math.abs(yRelOriginCellFinal-yMiddlePointRel)/2;
			            //get the middle point of the targeting undercut edge then set the invisible node and insert the edge
			            targetRelCell = getGraph().insertVertex(parent, "999", "", xMiddleCell, yMiddleCell, 0, 0,"nodeStyle");
					}else{// this is the case for normal undercut
					int targetRelOriginId = relArray.get(q).getOriginId();
					int targetRelTargetId = relArray.get(q).getTargetArgId();
					mxCell targetRelOriginCell = (mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(targetRelOriginId+1));//calculate the coordinate of the invisible middle node
					mxCell targetRelTargetCell = (mxCell) ((mxGraphModel)graph.getModel()).getCell(String.valueOf(targetRelTargetId+1));
					targetRelCell = getMiddleVertex(targetRelOriginCell, targetRelTargetCell);//insert the middle node to the graph
					}
				}
			
			}
			if(originArg !=null && targetRelCell !=null){
				
				
				if (relArray.get(i).getFlag()) {
					mxCell mxCell = (com.mxgraph.model.mxCell) getGraph().insertEdge(parent, relId, weight, originArg, targetRelCell,"lineStyle");
				}else{
					mxCell mxCell = (com.mxgraph.model.mxCell) getGraph().insertEdge(parent, relId, weight, originArg, targetRelCell,"lineStyleNeg");

				}

			}
		}
	}

	public AddUnderCutLine(ArrayList<Argument> argArray, ArrayList<Relation> relArray,Framework framework) {
		super(argArray, relArray,framework);        
	}

	//the auxiliary function to calculate the coordinate of the invisible middle node
	public static Object getMiddleVertex(mxCell front,mxCell end) {	
		double xOriginCell = front.getGeometry().getCenterX();
        double yOriginCell = front.getGeometry().getCenterY();
        double xTargetCell = end.getGeometry().getCenterX();
        double yTargetCell = end.getGeometry().getCenterY();
        double xMiddleCell = Math.min(xOriginCell,xTargetCell)+Math.abs(xOriginCell-xTargetCell)/2;
        double yMiddleCell = Math.min(yOriginCell,yTargetCell)+Math.abs(yOriginCell-yTargetCell)/2;
        Object middleCell = getGraph().insertVertex(getGraph().getDefaultParent(), "100000", "", xMiddleCell, yMiddleCell, 0, 0,"nodeStyle");
		return middleCell;
	}
	
	
}
