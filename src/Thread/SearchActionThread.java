package CSE210_Assignment.src.Thread;
/*************************************************************************
 *
 * A thread class for main action processes in the tag panel.
 *
 *************************************************************************/
import javax.swing.*;
import javax.swing.text.*;
import java.net.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Vector;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import CSE210_Assignment.src.Model.Article;
import CSE210_Assignment.src.Model.ArticleTreeNode;
import CSE210_Assignment.src.Thread.displayhtmlThread;
import CSE210_Assignment.src.Model.SearchArticles;
import CSE210_Assignment.src.Model.LinkedList;
import CSE210_Assignment.src.Model.Tag;
/**
 *
 *  The <tt>SearchActionThread</tt> class represents thread for main 
 *  action processes in the tag panel.
 *  
 * @author Yudi An
 */
public class SearchActionThread implements Runnable,ActionListener {
	private static final String buttonString = "JButton";//add action command

	private JScrollPane	panel; //the pane to display JTree
	private JTextField	textpanel; // the feild to display top-10 tags
	private LinkedList<Article> result;//the search result of articles
	//SearchArticles instance to provide algorithm for complexity questions	
	private SearchArticles searchsrticles;
	private JEditorPane htmltextPane; // the pane to display webpage
	private JButton[] bttags; // top-10 tags
	private String tag,optional;//tag to search
	private boolean casesensitive; //whether it is case sensitive

	/**
     * Initializes a new SearchActionThread.
     * @param htmltextPane JEditorPane the pane to display webpage
     * @param searchsrticles  instance to provide algorithm for complexity questions	
     * @param textpanel  the feild to display top-10 tags
     * @param panel  the pane to display JTree
     * @param tag tag to search
     * @param optional tag to search coocurrence
     * @param casesensitive boolean whether it is case sensitive
     * @param tags JButton[] top-10 tags button
     */
	public SearchActionThread(JEditorPane htmltextPane,SearchArticles searchsrticles,JTextField textpanel,JScrollPane panel, String tag,String optional,boolean casesensitive,JButton[] tags) {
		this.panel = panel;
		this.casesensitive = casesensitive;
		this.tag = tag;
		this.textpanel = textpanel;
		this.bttags = tags;
		this.searchsrticles = searchsrticles;
		this.htmltextPane = htmltextPane;
		this.optional = optional;
	}

	/**
     * override function from impliment actionlistener. 
     */
	@Override
	public void run() {
		if (!tag.equals("")) {			
			textpanel.setText(tag);
			result = searchAction(tag,casesensitive);
			// System.out.println("s");
			showCoocurrence(result);
			if(!optional.equals("")){
				LinkedList<Article> newTagArticles = cooFind(optional,result);
				refeshTree(newTagArticles,tag);
			}else{
				refeshTree(result,tag);
			}
		}
	}

	/**
     * override function from impliment actionlistener. 
     * handle the actions when button is being double clicked
     */
	@Override
	public void actionPerformed(ActionEvent e) {	
		if(buttonString.equals(e.getActionCommand())){
			JButton source = (JButton) e.getSource();
			String s = source.getText().trim();
			int index = s.indexOf('.')+1;
			s= s.substring(index);
			System.out.println(s);
			LinkedList<Article> newTagArticles = cooFind(s,result);
			refeshTree(newTagArticles,s);

		}								
    }

    //helper function to find the article both contaion two tags
    private LinkedList<Article> cooFind(String s,LinkedList<Article> articles){
		LinkedList<Article> orderedResult = new LinkedList<Article>();
		Article.setDefultCompareTag(Tag.createTag(tag));
		// System.out.println(Article.getDefultCompareTag().toString());
		for (Article a:articles){
			if(a.getAnnotations()!=null)
           		if(a.isFindTag(Tag.createTag(s)))
           			orderedResult.add(a);
		}
		System.out.println("Task 5: count the number of times two tags co-occur in the dataset.");
		System.out.println(">> for " +tag.toString()+" and "+s+": "+orderedResult.size());

		return orderedResult;
	}

	//helper function to find the article both contaion two tags
	private LinkedList<Article> searchAction(String s, boolean b){
		if(!b){
			s = s.toLowerCase();
		}
		return searchsrticles.getArticles(Tag.createTag(s));
	}

	//helper function to set the tree
	private void refeshTree (LinkedList<Article> nodes,String s) {
        final JTree tree;
        if(nodes.size() > 0) {
        	// root node of the tree
            DefaultMutableTreeNode top = new DefaultMutableTreeNode("Articles(" + nodes.size() + ")");
            tree = new JTree(top);
            ArticleTreeNode item;
            //build node for each article and restore the reference
            for (Article node : nodes) {
                item = new ArticleTreeNode(node.getTitle()+"( "+tag+" count:"+node.findTag(Tag.createTag(s)).getcount()+")");
                item.setReference( node );
                top.add(item);
            }
            // add tree node listener to display webpage when double click
            tree.addTreeSelectionListener(new TreeSelectionListener(){
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

                    /* if nothing is selected */ 
                    if (node == null ||  !node.isLeaf()) return;
                    Object nodeInfo = node.getUserObject();
                    if (nodeInfo instanceof String) {
                    	Thread thread = new Thread(new displayhtmlThread(htmltextPane,  ((ArticleTreeNode)node).Reference));
                    	thread.start();
                        // displayDetailInfo( ((ArticleTreeNode)node).Reference );
                    }
                }
            });

        } else {
            tree = new JTree(new DefaultTreeModel(new DefaultMutableTreeNode("No match found")));
        }
        //display tree on panel
        tree.setVisibleRowCount(25);
        tree.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        panel.getViewport().add(tree);
    } 

    //help class to show the top-10 occurrence from a map
    private void showCoocurrence(LinkedList<Article> articles){
		final LinkedList<Article> result = articles;
		Map tags = searchsrticles.getTags(articles);
		int n = 0;
		String s = "";
		for(Tag t: (Set<Tag>)tags.keySet()){
			if(n==6){
				s = s+"\n";
			}
			if(n==11) break;
			if(n>=1){
				s = s+n+". "+t+"\t"; 
				bttags[n-1].setText(n+"."+t.toString());
				bttags[n-1].setVisible(true);
				bttags[n-1].setActionCommand(buttonString);
        		bttags[n-1].addActionListener(this);

			}
			n++;		 	
		}
		//if there are no 10 tags here, unvisible the rest.
		while(n<11&&n>0){
			bttags[n-1].setVisible(false);
			n++;
		}
		// display in command line
		System.out.println("Task 8: suggest up to 10 most frequently cooccurring tags.");
		System.out.println(s);
	}

}