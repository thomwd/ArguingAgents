
import java.util.ArrayList;
import java.util.Hashtable;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

public class AddNode extends Actions{
	
	public AddNode(ArrayList<Argument> argArray, ArrayList<Relation> relArray,Framework framework) {
		super(argArray, relArray,framework);
	}

	public static void addNode(String nodeInfo,int x ,int y,String argId){
		mxStylesheet stylesheet = graph.getStylesheet();
		Hashtable<String, Object> style = new Hashtable<String,Object>();
		style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
		style.put(mxConstants.STYLE_OPACITY, 50);
		style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
		style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
		style.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
		style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
		style.put(mxConstants.STYLE_FONTSIZE, 20);
		stylesheet.putCellStyle("nodeStyle", style);
		
		getGraph().getModel().beginUpdate();
		Object parent = getGraph().getDefaultParent(); 
		Object v1 = getGraph().insertVertex(parent, argId, nodeInfo, x, y, 300, 80,"nodeStyle");
		getM().put(nodeInfo, v1);
		getGraph().getModel().endUpdate();
	}
}
