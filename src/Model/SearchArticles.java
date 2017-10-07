package CSE210_Assignment.src.Model;
/*************************************************************************
 *
 *  This class hold all result from sex reader
 *  Aims to impliment basic algorithm of find top-10 co-occurrence and so on
 *
 *************************************************************************/
import java.util.Vector;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *  The <tt>SearchArticles</tt> class represents result instance once finish xml read for GUI.
 *  It supports the <em>getArticles</em> and <em>getInfo</em>operations, along with methods
 *  for get result vector articles.
 *  <p> 
 *  The <em>getArticles</em> impliment basic algorithm for task search by tag,
 *  operations all take linear time in the worst case.
 *  The <em>getInfo</em> provide data for GUI of search by hash,
 *  operations all take linear time in the worst case.
 *  <p>
 *
 *  @author Yudi An
 */

public class SearchArticles{
	private Vector articles = new Vector();//use vector to store all articles

	/**
     * Initializes a new SearchArticles
     * @param url String URL to read
     * @throws exception from xmlreader
     */
	public SearchArticles(String url) throws Exception{
		SAXParserFactory spf = SAXParserFactory.newInstance();
	    spf.setNamespaceAware(true);
	    SAXParser saxParser = spf.newSAXParser();
	    XMLReader xmlReader = saxParser.getXMLReader();
	    SAXDataExtraction sax = new SAXDataExtraction();
	    xmlReader.setContentHandler(sax);
	    xmlReader.parse(url);
	    articles = sax.getArticles();
	}

	/**
     * Return the vector of articles.
     * @return Return the vector of articles.
     */
	public Vector getArticles(){
		return articles;
	}

	/**
	 * Getting a ordered list of articles by count of the tag.
	 * Using linked list for comparable articles on key of the tag.
     * linear time search.
     * @param t Tag search key tag used for search.
     * @return Return linked list of articles by order of count of tag.
     */
	public LinkedList getArticles(Tag t){
		LinkedList<Article> orderedResult = new LinkedList<Article>();
		Article.setDefultCompareTag(t); //set defult tag for comparable article
		for (int i = 0; i < articles.size(); i++) {
           Article a = ((Article) articles.elementAt(i));
           // System.out.println(articles.size());
           if(a.getAnnotations()!=null)
           		if(a.isFindTag(t))
           			orderedResult.add(a);			
    	}

    	return orderedResult;
	}

	/**
	 * Getting a ordered list of annotations of article depending on count of the tag.
	 * Using linked list for comparable annotation on key of the tag.
     * linear time search.
     * @param titleHash String unique hash code of article.
     * @param b boolean whether it is casesensitive.
     * @return Return linked list of articles by order of count of tag.
     */
	public LinkedList<Annotation> getTags(String titleHash,boolean b){
		for (int i = 0; i < articles.size(); i++) {
           Article a = ((Article) articles.elementAt(i));
           if(!b){
            	if(a.getTitleHash().equals(titleHash))//case sensitive
            		return a.getAnnotations();
           }else{
           		if(a.getTitleHash().equalsIgnoreCase(titleHash))//not case sensitive
            		return a.getAnnotations();
           }
           
           		
        }
        return new LinkedList<Annotation>();
	}

	/**
     * get the article instance by unique hash code
     * @param titleHash String HASH code for search.
     * @return Return the article.
     */
	public Article getInfo(String titleHash){
		for (int i = 0; i < articles.size(); i++) {
           Article a = ((Article) articles.elementAt(i));
           if(a.getTitleHash().equalsIgnoreCase(titleHash))
            	return a;
        }
        return null;
	}

	/**
	 * Getting a ordered list of co-occurrence of annotations depending on specific of the tag.
	 * This function is used for finding the top-10 co-occurrence tags.
	 * Using linked list of articles which is the search result for specific tag, 
	 * in this way we can reduce the time complexiy of this algorithm and make it looks faster in GUI.
     * Implement by hashmap to scan every unique tags for every result articles only once, 
     * therefore ingoring the hash time, this is a square time function
     * @param articles linkedlist of articles which is the search result for specific tag.
     * @return Return sorted map of tags.
     */
	public Map getTags(LinkedList<Article> articles){		
		HashMap<Tag,Integer> checkedTags = new HashMap<Tag,Integer>(); // no duplicate
		for(Article a:articles){
			for(Annotation an:(LinkedList<Annotation>)a.getAnnotations()){
				Integer i = checkedTags.get(an.getTag());
				if(i!=null){
					checkedTags.put(an.getTag(), i+1);//use value as counter
				}else{
					checkedTags.put(an.getTag(), 1);//if not exits set a new one
				}
			}
		}
		Map sortedMap = sortByValue(checkedTags);//sort the map by value
		return sortedMap;
	}


	
	 // Helper funcction for sorting a map by value.
     
	private static Map sortByValue(Map unsortedMap) {
		Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}


	
}

	
	 //Helper class for sorting a map by value.
  	
class ValueComparator implements Comparator {
 
	Map map;
 
	public ValueComparator(Map map) {
		this.map = map;
	}
 	
 	// sort the map by  value.
	public int compare(Object keyA, Object keyB) {
		Comparable valueA = (Comparable) map.get(keyA);
		Comparable valueB = (Comparable) map.get(keyB);
		return valueB.compareTo(valueA);
	}
}