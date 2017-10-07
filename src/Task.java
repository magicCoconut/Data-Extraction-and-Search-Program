package CSE210_Assignment.src;
/*************************************************************************
 *  Compilation:  javac CSE210_Assignment/src/Task.java
 *  Execution:    java CSE210_Assignment.src.Task
 *
 *************************************************************************/
import java.util.Vector;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import CSE210_Assignment.src.Model.Article;
import CSE210_Assignment.src.Model.Tag;
import CSE210_Assignment.src.Model.LinkedList;
import CSE210_Assignment.src.Model.Annotation;
import CSE210_Assignment.src.Model.SAXDataExtraction;
/**
 *  The <tt>Task</tt> class is an main function class to display task1-5 in command line
 *
 *  @author Yudi An
 */
public class Task {
	private static Vector articles = new Vector();
	
  private static void dataExtraction(String url) throws Exception{
		SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setNamespaceAware(true);
    SAXParser saxParser = spf.newSAXParser();
    XMLReader xmlReader = saxParser.getXMLReader();
    SAXDataExtraction sax = new SAXDataExtraction();
    xmlReader.setContentHandler(sax);
    xmlReader.parse(url);
    articles = sax.getArticles();
	}
	private static int numOfArticle(){
		return articles.size();
	}

	private static int numOfUniqueTags(){
		return Tag.size();
	}

	private static int numOfTags(String titleHash){
		for (int i = 0; i < articles.size(); i++) {
           Article a = ((Article) articles.elementAt(i));
           if(a.getTitleHash().equals(titleHash))
           		return a.getAnnotations().size();
        }
        return -1;
	}

	private static int numOfCooccur(Tag tag1, Tag tag2){
		int n = 0;
		for (int i = 0; i < articles.size(); i++) {
           Article a = ((Article) articles.elementAt(i));
           // System.out.println(articles.size());
           if(a.getAnnotations()!=null)
           		if(a.isFindTag(tag1) && a.isFindTag(tag2))
           			n++;
           // System.out.println(a.isFindTag(tag2));   			
    }
    return n;
	}

	private static void articleDisplay (String titleHash) {
		for (int i = 0; i < articles.size(); i++) {
           Article art = ((Article) articles.elementAt(i));
           if(art.getTitleHash().equals(titleHash)){
           		LinkedList<Annotation> a = art.getAnnotations();
           		System.out.println(">> Book Title: "+art.getTitle());
	            for(Annotation item : a){
	                // System.out.println(""+item.getTag().toString()+"\t\t\tcount: "+item.getcount());
	                System.out.printf("tag: %-20s count: %2d\n",  item.getTag().toString(), item.getcount());
	            }
           }
           		
        }
	}

  /**
  * main function for display task1-5 in command line
  * @throws Exception.
  */
	public static void main(String[] args)  throws Exception {
		String url = "data/tag-data.xml";
		dataExtraction(url);
		System.out.println();
		System.out.println();

		System.out.println("Task 1: count the total number of articles in the dataset.");
        System.out.println(">> " + numOfArticle());
        System.out.println();

        System.out.println("Task 2: count the total number of unique tags in the dataset.");
        System.out.println(">> " + numOfUniqueTags());
        System.out.println();

        System.out.println("Task 3: count the number of tags used to annotate a particular article.");
        System.out.println("here we take \"5619983c9b25f98d04bbe93cf7525b16\"as example ");
        System.out.println(">> " + numOfTags("5619983c9b25f98d04bbe93cf7525b16"));
        System.out.println();

        System.out.println("Task 4: display all the tags and their count for one particular	article.");
        System.out.println("here we take \"5253e4edd95a559c3fd2488728a79393\"as example ");
        articleDisplay("5253e4edd95a559c3fd2488728a79393");
        System.out.println();

        System.out.println("Task 5: count the number of times two tags co-occur in the dataset.");
        System.out.println("here we take \"history\" and \"music\"as example ");
        System.out.println(">> " + numOfCooccur(Tag.createTag("history"), Tag.createTag("music")));
        System.out.println();




	}
}