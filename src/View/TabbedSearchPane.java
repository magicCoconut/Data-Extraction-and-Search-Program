package CSE210_Assignment.src.View;
/*************************************************************************
 *
 * A tabbled search frame class.
 *
 *************************************************************************/
import java.awt.*;
import javax.swing.*;

import CSE210_Assignment.src.Model.SearchArticles;
/**
 *
 * The <tt>TabbedSearchPane</tt> class build JFrame for GUI
 *  
 * @author Yudi An
 */
public class TabbedSearchPane extends 	JFrame {
	private	JTabbedPane tabbedPane; //the tabble panel to hold two panel
	private	TagPanel		tagSearch; //page 1 for tag search
	private	HashPanel		hashSearch; // page 2 for hash code search
	private static SearchArticles s; //SearchArticles instance

	/**
     * Initializes a new TabbedSearchPane.
     * @param s a SearchArticles instance 
     */
	public TabbedSearchPane(SearchArticles s){
		this.s =s;
		
		//set the frame
		setTitle( "Article Search Application" );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize( 1000, 700 );

		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		// Create the tab pages
		tagSearch = new TagPanel(s); //using tag panel class
		hashSearch = new HashPanel(s); //using hash panel class

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "Search by Tag", tagSearch );
		tabbedPane.addTab( "Search by Hash", hashSearch );
		topPanel.add( tabbedPane, BorderLayout.CENTER );
	}


    // Main method to get things started
	// public static void main( String args[] )
	// {
	// 	// Create an instance of the test application
	// 	TabbedSearchPane mainFrame	= new TabbedSearchPane();
	// 	mainFrame.setVisible( true );
	// }
}