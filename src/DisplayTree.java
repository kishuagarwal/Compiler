import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import parser.ParseTree;


public class DisplayTree extends JPanel{
	
	private static JTree tree;
		
	public DisplayTree(ParseTree pt){
		super(new BorderLayout());
		tree = new JTree(new ParseTreeModel(pt));
		add(tree,BorderLayout.CENTER);
		
	}
	
	
	class ParseTreeModel implements TreeModel{

		ParseTree tree;
		ParseTreeModel(ParseTree pt){
			tree = pt;
		}
		
		@Override
		public void addTreeModelListener(TreeModelListener arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Object getChild(Object parseTree, int index) {
			// TODO Auto-generated method stub
			ParseTree p = (ParseTree)parseTree;
			return p.getChildren().get(index);
		}

		@Override
		public int getChildCount(Object parseTree) {
			// TODO Auto-generated method stub
			ParseTree p = (ParseTree)parseTree;
			return p.getChildren().size();
		}

		@Override
		public int getIndexOfChild(Object parent, Object child) {
			// TODO Auto-generated method stub
			ParseTree p = (ParseTree)parent;
			ParseTree c = (ParseTree)child;
			int count = 0;
			for(ParseTree t : p)
				if(t == c)
					return count;
				else
					count++;
			return -1;
		}

		@Override
		public Object getRoot() {
			// TODO Auto-generated method stub
			return tree;
		}

		@Override
		public boolean isLeaf(Object parseTree) {
			// TODO Auto-generated method stub
			ParseTree p = (ParseTree)parseTree;
			return (p.getChildren().size() == 0) ? true : false;
		}

		@Override
		public void removeTreeModelListener(TreeModelListener arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void valueForPathChanged(TreePath arg0, Object arg1) {
			// TODO Auto-generated method stub
			
		}
		
	}


	public static void createAndShowTree(ParseTree output) {
		// TODO Auto-generated method stub
		 
		  JFrame frame = new JFrame("ParseTree");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //Create and set up the content pane.
	        DisplayTree newContentPane = new DisplayTree(output);
            newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	}

}
