package CSE210_Assignment.src.View;
/*************************************************************************
 *
 * A JPanel class for searching by hash code.
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
import CSE210_Assignment.src.Model.Tag;
import CSE210_Assignment.src.Thread.HashSearchActionThread;
/**
 *
 *  The <tt>HashPanel</tt> build a Jpanel for page 2 in Jframe
 *  
 * @author Yudi An
 */
public class HashPanel extends JPanel {
  private static final String textFieldString = "JTextField"; 
  private SearchArticles searchsrticles; //SearchArticles instance
  private JTextField findTextField; //textfeild to set text
  private JCheckBox caseCheckBox; //check whether it is case sensitive
  private JPanel textPane,tagScrollPane; //panel to diaplay tags and info
  private JScrollPane paneScrollPane; // pane to display jtree
  private JLabel actionLabel; // label to display info

  /**
     * Initializes a new HashPanel.
     * @param sa  a SearchArticles instance 
     */
  public HashPanel(SearchArticles sa){
    searchsrticles = sa;
    setLayout(new BorderLayout());

    //set layout panel
    setTextPane();
    setTagScrollPane();
        
    add(tagScrollPane, BorderLayout.WEST);
    add(textPane, BorderLayout.CENTER);
    System.out.println("Task 2: count the total number of unique tags in the dataset.");
    System.out.println(">> "+Tag.size());
  }

  //
  private void setTextPane(){
    JPanel searchPanel = createSearchPanel();
    textPane = new JPanel();
    textPane.setLayout(new BorderLayout());    
    textPane.add(searchPanel, BorderLayout.NORTH);

  }

  private void setTagScrollPane(){
    tagScrollPane  = new JPanel(new GridLayout(1, 0));

        JTree tree = new JTree(new DefaultTreeModel(new DefaultMutableTreeNode("Total Tags ("+Tag.size()+")")));
    tree.setVisibleRowCount(25);
    tree.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    paneScrollPane = new JScrollPane(tree);
    paneScrollPane.getViewport().setPreferredSize(new Dimension(200, 300));
    paneScrollPane.setMinimumSize(new Dimension(10, 10));

    tagScrollPane.add(paneScrollPane);
        tagScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Relevant Tags( total: "+Tag.size()+" )"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

  }

  private JPanel createSearchPanel(){
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    
    JLabel findLabel = new JLabel("Hash Value:");
    panel.add(findLabel);
    
    panel.add(Box.createRigidArea(new Dimension(6, 0)));
    
    findTextField = new JTextField(30);
    findTextField.setActionCommand(textFieldString);
    panel.add(findTextField);
    
    panel.add(Box.createRigidArea(new Dimension(6, 0)));
    
    caseCheckBox = new JCheckBox("Case sensitive?");
    panel.add(caseCheckBox);
    
    panel.add(Box.createRigidArea(new Dimension(6, 0)));
    
    JButton findButton = new JButton("Search");
    findButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        String s = findTextField.getText().trim();
        findTextField.setText(s);
        boolean caseSensitive = caseCheckBox.getModel().isSelected();
        Thread thread = new Thread(new HashSearchActionThread(actionLabel,searchsrticles,paneScrollPane, s, caseSensitive));
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
      }

    });
    panel.add(restoreButton);

    actionLabel = new JLabel();
    actionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    actionLabel.setLayout(new BorderLayout(0,8));;

    JPanel tpanel = new JPanel();
    tpanel.setLayout(new BorderLayout());
    JPanel lpanel = new JPanel();
    lpanel.add(actionLabel);
    lpanel.setBorder(
            BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("All Info of the Article"),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)));
    
    tpanel.add(panel, BorderLayout.NORTH);
    tpanel.add(lpanel, BorderLayout.CENTER);



    return tpanel;
  }
  
}