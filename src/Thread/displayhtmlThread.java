package CSE210_Assignment.src.Thread;
/*************************************************************************
 *
 * A thread class for Html web page display on main GUI
 *
 *************************************************************************/
import javax.swing.*;
import javax.swing.text.*;
import java.net.*;

import CSE210_Assignment.src.Model.Article;

/**
 *
 *  The <tt>displayhtmlThread</tt> class represents thread for display web page.
 *  
 * @author Yudi An
 */
public class displayhtmlThread implements Runnable {

	private JEditorPane panel; //the pane to display web page
	
	private Object ref; //the object be actived

	/**
     * Initializes a new displayhtmlThread.
     * @param panel EditorPane to display web page 
     * @param ref Object to be actived 
     */
	public displayhtmlThread(JEditorPane panel, Object ref) {
		this.panel = panel; 
		this.ref = ref;
	}

	/**
     * override function from impliment runable. 
     */
	@Override
	public void run() {
		Article article = (Article) ref;
        java.net.URL helpURL = null;
        // try MalformedURLException and ioexception for set page operation
        try {
            helpURL = new URL("http://delicious.com/url/"+article.getTitleHash());
            panel.setPage(helpURL);
        } catch (MalformedURLException e) {
        	//error message for url transform
            System.err.println("Attempted to read a bad URL: " + helpURL);
        } catch (Exception e) {
        	//helper message for io exception
            System.err.println("IO: " + helpURL +"\n"+e);
        }
	}

}