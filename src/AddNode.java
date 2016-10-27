
import java.util.ArrayList;
import java.util.Hashtable;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

public class AddNode extends Actions{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddNode(ArrayList<Argument> argArray, ArrayList<Relation> relArray,Framework framework) {
		super(argArray, relArray,framework);
	}

	@SuppressWarnings("unchecked")
	public static void addNode(String nodeInfo,int x ,int y,String argId,String text){
		mxStylesheet stylesheet = graph.getStylesheet();
		
		if (Integer.valueOf(argId)<=2) {//define the style of the vertex of positions
			Hashtable<String, Object> stylePos = new Hashtable<String,Object>();
			stylePos.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
			stylePos.put(mxConstants.STYLE_OPACITY, 50);
			stylePos.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
			stylePos.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
			if (Integer.valueOf(argId)==1) {
				stylePos.put(mxConstants.STYLE_FILLCOLOR, "#F8CECC");
			}else{
				stylePos.put(mxConstants.STYLE_FILLCOLOR, "#DAE8FC");
			}
			stylePos.put(mxConstants.STYLE_FONTCOLOR, "#774400");
			stylePos.put(mxConstants.STYLE_FONTSIZE, 15);
			stylesheet.putCellStyle("nodeStyle", stylePos);
			
		}else{ //define the style of the vertex of the normal arguments
			Hashtable<String, Object> style = new Hashtable<String,Object>();
			style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
			style.put(mxConstants.STYLE_OPACITY, 50);
			style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
			style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
			style.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
			style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
			style.put(mxConstants.STYLE_FONTSIZE, 15);
			stylesheet.putCellStyle("nodeStyle", style);
		}
		
		getGraph().getModel().beginUpdate();
		Object parent = getGraph().getDefaultParent(); 
		Object v1 = getGraph().insertVertex(parent, argId, nodeInfo, x, y, 80, 60,"nodeStyle");// insert the vertex to with specified id, text,size and style
		getM().put(nodeInfo, v1);		
		getGraph().getModel().endUpdate();
	}
}
