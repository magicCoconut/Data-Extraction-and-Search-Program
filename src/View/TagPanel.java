package CSE210_Assignment.src.View;
/*************************************************************************
 *
 * A JPanel class for searching by tags.
 *
 *************************************************************************/
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;              //for layout managers and more
import java.awt.event.*;        //for action events
import java.net.*;
import java.io.*;
import java.util.Vector;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import CSE210_Assignment.src.Model.SearchArticles;
import CSE210_Assignment.src.Model.LinkedList;
import CSE210_Assignment.src.Model.Article;
import CSE210_Assignment.src.Model.ArticleTreeNode;
import CSE210_Assignment.src.Model.Tag;
import CSE210_Assignment.src.Thread.displayhtmlThread;
import CSE210_Assignment.src.Thread.SearchActionThread;
/**
 *
 *  The <tt>HashPanel</tt> build a Jpanel for page 1 in Jframe
 *  
 * @author Yudi An
 */
public class TagPanel extends JPanel {
	private static final String textFieldString = "JTextField";
	private SearchArticles searchsrticles;//SearchArticles instance
	private LinkedList<Article> result; // the linked list of result articles
	private JTextField findTextField,findText2Field;
	private JCheckBox caseCheckBox;//check whether it is case sensitive
	private JEditorPane htmltextPane; // pane to display webpage
	private JPanel textPane,tagScrollPane; //jpanel to hold the tags.
	private JScrollPane paneScrollPane; //pane to display jtree
	private JButton[] topTags; //top-10 tags buttons

	/**
     * Initializes a new TagPanel.
     * @param sa a SearchArticles instance 
     */
	public TagPanel(SearchArticles sa){
		searchsrticles = sa;
		setLayout(new BorderLayout());

		setTextPane();
		setTagScrollPane();
				
		add(tagScrollPane, BorderLayout.WEST);
		add(textPane, BorderLayout.CENTER);
		System.out.println("Task 1: count the total number of articles in the dataset.");
		System.out.println(">> "+searchsrticles.getArticles().size());
	}


	private void setTextPane(){
		JPanel searchPanel = createSearchPanel();
		JScrollPane textScrollPane = createTextScrollPane();

		
		textPane = new JPanel();
		textPane.setLayout(new BorderLayout());
		
		textPane.add(searchPanel, BorderLayout.NORTH);
		textPane.add(textScrollPane, BorderLayout.CENTER);
	}

	private void setTagScrollPane(){
		tagScrollPane  = new JPanel(new GridLayout(1, 0));

        JTree tree = new JTree(new DefaultTreeModel(new DefaultMutableTreeNode("Article")));
		tree.setVisibleRowCount(25);
		tree.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		paneScrollPane = new JScrollPane(tree);
		paneScrollPane.getViewport().setPreferredSize(new Dimension(200, 300));
		paneScrollPane.setMinimumSize(new Dimension(10, 10));

		tagScrollPane.add(paneScrollPane);
        tagScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Relevant Book( total: "+searchsrticles.getArticles().size()+" )"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

	}

	private JPanel createSearchPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		
		JLabel findLabel = new JLabel("Tag 1:");
		panel.add(findLabel);
		
		panel.add(Box.createRigidArea(new Dimension(6, 0)));
		
		findTextField = new JTextField(14);
		findTextField.setActionCommand(textFieldString);
		panel.add(findTextField);

		panel.add(Box.createRigidArea(new Dimension(6, 0)));
		JLabel findLabel2 = new JLabel("Tag 2(Optional):");
		panel.add(findLabel2);
		
		panel.add(Box.createRigidArea(new Dimension(6, 0)));

		findText2Field = new JTextField(14);
		findText2Field.setActionCommand(textFieldString);
		panel.add(findText2Field);
		
		panel.add(Box.createRigidArea(new Dimension(6, 0)));
		
		caseCheckBox = new JCheckBox("Case sensitive?");
		panel.add(caseCheckBox);
		
		panel.add(Box.createRigidArea(new Dimension(6, 0)));
		
		JButton findButton = new JButton("Search");
		findButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String s = findTextField.getText().trim();
				String optional = findText2Field.getText().trim();
				boolean caseSensitive = caseCheckBox.getModel().isSelected();
				Thread thread = new Thread(new SearchActionThread(htmltextPane,searchsrticles,findTextField,paneScrollPane, s,optional, caseSensitive,topTags));
				thread.start();
			}
		});
		panel.add(findButton);
		
		panel.add(Box.createRigidArea(new Dimension(6, 0)));
		
		JButton restoreButton = new JButton("Reset");
		restoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				findTextField.setText("");
				findText2Field.setText("");
			}

		});
		panel.add(restoreButton);


		JPanel tpanel = new JPanel();
		tpanel.setLayout(new BorderLayout());

		JPanel lpanel = new JPanel();
		topTags = new JButton[10];


		JPanel actionLabel = new JPanel();
        actionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        actionLabel.setLayout(new BorderLayout(0,8));;

        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));
        
        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.X_AXIS));
        

        for(int i = 0;i<5;i++){
        	topTags[i] = new JButton();
        	topTags[i].setVisible(false);					
        	up.add(topTags[i]);
        	up.add(Box.createRigidArea(new Dimension(10, 0)));
        }
        for(int i = 5;i<10;i++){
        	topTags[i] = new JButton();
        	topTags[i].setVisible(false);
        	down.add(topTags[i]);
        	down.add(Box.createRigidArea(new Dimension(10, 0)));
        }
        actionLabel.add(up, BorderLayout.NORTH);

		actionLabel.add(down, BorderLayout.CENTER);
        
        lpanel.add(actionLabel);
        lpanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Top-10 Coocurrence Tags for Tag1"),
                        BorderFactory.createEmptyBorder(2, 2, 2, 2)));

		
		tpanel.add(panel, BorderLayout.NORTH);
		tpanel.add(lpanel, BorderLayout.CENTER);



		return tpanel;
	}

	private JScrollPane createTextScrollPane(){
		htmltextPane = new JEditorPane();
		htmltextPane.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		Font font = new Font("Courier New", Font.PLAIN, 12);
		htmltextPane.setFont(font);
		htmltextPane.setPreferredSize(new Dimension(710, 540));
		htmltextPane.setMinimumSize(new Dimension(10, 10));
		
		DefaultCaret caret = (DefaultCaret) htmltextPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
        JScrollPane scrollPane = new JScrollPane(htmltextPane);

        return scrollPane;
	}

	
}