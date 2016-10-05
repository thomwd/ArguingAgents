import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.mxgraph.view.mxGraph;


public class AddLine extends Actions {
	
	
	public static void  addLine(ArrayList<Argument> argArray, ArrayList<Relation> relArray) {
		Object parent = getGraph().getDefaultParent();
		for(int i = 0; i<relArray.size();i++){
			Object v1 = null;
			Object v2 = null;
			Relation relation = relArray.get(i);
			int orginId = relation.getOriginId();
			int targetArgId = relation.getTargetArgId();
			String weight = String.valueOf(relArray.get(i));
			for(int j = 0;j<argArray.size();j++){
				if (argArray.get(j).getArgId() == orginId) {
					v1 = getM().get(argArray.get(j).getSummary());
				}
				if (argArray.get(j).getArgId() == targetArgId) {
					v2 = getM().get(argArray.get(j).getSummary());
				}
				
			}
			
		}
		String mac = "Buy_mac";
		String windows = "Buy_windows";
		Object v11 = getM().get(mac);
		Object v22 = getM().get(windows);
		getGraph().insertEdge(parent, null, "1", v11, v22);
	}

	public AddLine(ArrayList<Argument> argArray, ArrayList<Relation> relArray) {
		super(argArray, relArray);
		
//		Object v1 = this.getM().get(JOptionPane.showInputDialog("Digite o grafo 1:"));
//        Object v2 = this.getM().get(JOptionPane.showInputDialog("Digite o grafo 2:"));
//        String nome = JOptionPane.showInputDialog("Digite o nome da linha:");
        
	}

}