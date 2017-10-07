package CSE210_Assignment.src;
/*************************************************************************
 *  Compilation:  javac CSE210_Assignment.src.TagSearch.java
 *  Execution:    java CSE210_Assignment/src/TagSearch
 *
 *************************************************************************/
import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.SwingUtilities;
import java.util.Vector;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import CSE210_Assignment.src.Model.SearchArticles;
import CSE210_Assignment.src.View.TagPanel;
import CSE210_Assignment.src.View.TabbedSearchPane;
/**
 *
 *  The <tt>TagSearch</tt> whole GUI for task1-8.
 *  It contains two tab page, one for search by tag and another is for search by hash code
 *  
 * @author Yudi An
 */
public class TagSearch  {
  private static SearchArticles s;

  /**
  * main function for display task1-5 in command line
  * @throws Exception.
  */
	public static void main(String[] args) throws Exception {
        s = new SearchArticles("CSE210_Assignment/src/data/tag-data.xml");
        //Search info from vector of articles:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              TabbedSearchPane mainFrame  = new TabbedSearchPane(s);
              mainFrame.setVisible( true );
            }
        });
    }
}