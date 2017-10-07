package CSE210_Assignment.src.Thread;
/*************************************************************************
 *
 * A thread class for main action processes in the hash panel.
 *
 *************************************************************************/
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;              //for layout managers and more
import java.net.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Vector;
import java.util.Map;
import java.util.Set;			//for data process of co-occurrence tags

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener; // for JTree display

import CSE210_Assignment.src.Model.Article;
import CSE210_Assignment.src.Model.Annotation;
import CSE210_Assignment.src.Model.ArticleTreeNode;
import CSE210_Assignment.src.Model.SearchArticles;
import CSE210_Assignment.src.Model.LinkedList;

/**
 *
 *  The <tt>HashSearchActionThread</tt> class represents thread for main 
 *  action processes in the hash panel.
 *  
 * @author Yudi An
 */
public class HashSearchActionThread implements Runnable {

	private JScrollPane	panel; //the pane to display JTree
	private LinkedList<Annotation> result; //the search result of annotations
	private SearchArticles searchsrticles; // SearchArticles instance to provide algorithm for complexity questions	
	private String hash; //hash code for article
	private boolean casesensitive; //whether it is case sensitive
	private JLabel actionLabel; //the label place to show the info of the article


	/**
     * Initializes a new HashSearchActionThread.
     * @param actionLabel JLabel the label place to show the info of the article
     * @param searchsrticles  instance to provide algorithm for complexity questions	
     * @param panel JScrollPane the pane to display JTree
     * @param hash String hash code for article
     * @param casesensitive boolean whether it is case sensitive
     */
	public HashSearchActionThread(JLabel actionLabel,SearchArticles searchsrticles,JScrollPane panel, String hash,boolean casesensitive) {
		this.panel = panel;
		this.casesensitive = casesensitive;
		this.hash = hash;
		this.searchsrticles = searchsrticles;
		this.actionLabel = actionLabel;

	}


	/**
     * override function from impliment runable. 
     */
	@Override
	public void run() {
		if (!hash.equals("")) {
			result = searchAction(hash,casesensitive);
			// System.out.println(result.size());			
			showInfo(); //show the details of rearched article
			refeshTree(result,hash); //show JTree of annotation
			// for command line display
			System.out.println("Task 3: count the number of tags used to annotate a particular article.");
			System.out.println(">> " + result.size());
			System.out.println("Task 4: display all the tags and their count for one particular	article.");
			articleDisplay(hash);


			
		}
	}

	//helper function for article display on command line
	private void articleDisplay (String titleHash) {
		if(result.size()>=0){
			for (Annotation a: result) {
            System.out.printf("tag: %-20s count: %2d\n",  a.getTag().toString(), a.getcount());	
        	}
		}else{
			System.out.printf("Not Found");	
		}
		
		
	}

	//helper function to get linked list of annotation by hash code s
	private LinkedList<Annotation> searchAction(String s, boolean b){
		return searchsrticles.getTags(s,b);
	}

	//helper function to set the tree
	private void refeshTree (LinkedList<Annotation> nodes,String s) {
        final JTree tree;

        if(nodes.size() > 0) {
        	// root node of the tree
            DefaultMutableTreeNode top = new DefaultMutableTreeNode("Tags(" + nodes.size() + ")");
            tree = new JTree(top);
            ArticleTreeNode item;
            //build node for each article and restore the reference
            for (Annotation node : nodes) {
                item = new ArticleTreeNode(node.getTag()+"("+node.getcount()+")");
                item.setReference( node );
                top.add(item);
            }
        } else {
            tree = new JTree(new DefaultTreeModel(new DefaultMutableTreeNode("No match found")));
        }

        //display tree on panel
        tree.setVisibleRowCount(25);
        tree.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        panel.getViewport().add(tree);
    } 

    //helper function to show the details of article on jlebal
    private void showInfo(){
    	Article a = searchsrticles.getInfo(hash);
    	actionLabel.setText( "<html>Title: " + a.getTitle() +
                "<br> Hash:" + a.getTitleHash() +
                "<br> Users:" + a.getusers() + 
                "<br> Annotation:" + a.getAnnotations().size() +"</html>");
	}

}