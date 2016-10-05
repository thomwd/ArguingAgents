
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.mxgraph.view.mxGraph;

public class AddNode extends Actions{
	
	public AddNode(ArrayList<Argument> argArray, ArrayList<Relation> relArray) {
		super(argArray, relArray);
		// TODO Auto-generated constructor stub
	}

	public static void addNode(String nome,int x ,int y){
		getGraph().getModel().beginUpdate();
		Object parent = getGraph().getDefaultParent(); 
		Object v1 = getGraph().insertVertex(parent, null, nome, x, y, 150, 50);
		getM().put(nome, v1);
		getGraph().getModel().endUpdate();
	}
}
