import java.util.ArrayList;
import java.util.Hashtable;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;
public class AddAttackLine extends Actions {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void  addLine(ArrayList<Argument> argArray, ArrayList<Relation> relArray) {
		mxStylesheet stylesheet = graph.getStylesheet();
		Hashtable<String, Object> style = new Hashtable<String,Object>();
		
		style.put(mxConstants.STYLE_FONTCOLOR, "#7A93C1");
		style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		style.put(mxConstants.STYLE_FONTSIZE, 20);
		stylesheet.putCellStyle("lineStyle", style);
		
		Object parent = getGraph().getDefaultParent();
		for(int i = 0; i<relArray.size();i++){
			Object v1 = null;
			Object v2 = null;
			Relation relation = relArray.get(i);
			int orginId = relation.getOriginId();
			String relId = String.valueOf(relation.getRelId()+1000);
			int targetArgId = relation.getTargetArgId();
			String weight = String.valueOf(relArray.get(i).getWeight());
			
				for(int j = 0;j<argArray.size();j++){
					if (targetArgId != 0) {
						
						
						if (argArray.get(j).getArgId() == orginId) {
							v1 = getM().get(argArray.get(j).getSummary()+"\r\n"+argArray.get(j).getActivity());
						}
						if (argArray.get(j).getArgId() == targetArgId) {
							v2 = getM().get(argArray.get(j).getSummary()+"\r\n"+argArray.get(j).getActivity());

						}
						
					    if(v1 !=null && v2 !=null)
					    {
					    	mxCell cell = (mxCell) getGraph().insertEdge(parent, relId, weight, v1, v2,"lineStyle");
							break;

						}
						
					}
					
				    
				}
				
			
		}
	}

	public AddAttackLine(ArrayList<Argument> argArray, ArrayList<Relation> relArray,Framework framework) {
		super(argArray, relArray);        
	}

}