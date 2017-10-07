package CSE210_Assignment.src.Model;

/*
 * Helper class for xml handler
 */


import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
/**
 *  The <tt>SAXDataExtraction</tt> class extend defult handler to handle xml input streams
 *  It supports the <em>startElement</em> and <em>endElement</em>operations, along with methods
 *  for get result vector articles.
 *
 *  @author Wei Wang
 *  @author Yudi An
 */
public class SAXDataExtraction extends DefaultHandler {

    private Vector articles = new Vector(); //vector of articles
    private Article article; // instance of article
    private Annotation annotation; // instance of annotation
    private LinkedList annotations; // linked list of annotation
    private String temp = ""; // defult temp string

    /**
     * start document.
     * @throws SAXException 
     */
    public void startDocument() throws SAXException {
        System.out.println("Start processing \"wiki10+ tag-data.xml ...\"");
    }

    /**
     * end document.
     * @throws SAXException 
     */
    public void endDocument() throws SAXException {
        System.out.println("End processing \"wiki10+ tag-data.xml\"");
    }

    /**
     * the start tag xml to look for
     * @param  namespaceURI String url space name.
     * @param  localName String lacal name.
     * @param  qName String query name.
     * @param  atts Attributes attributes.
     * @throws SAXException 
     */
    public void startElement(String namespaceURI,
            String localName,
            String qName,
            Attributes atts)
            throws SAXException {
                if (qName.equalsIgnoreCase("article")) {
                    article = new Article();

                    // annotations = new LinkedList();
                } else if (qName.equalsIgnoreCase("tags")) {
                    annotations = new LinkedList();

                }
                else if (qName.equalsIgnoreCase("tag")){
                    annotation = new Annotation();

                }
    }

    /**
     * Process sub string.
     * @param  ch char[] original char array.
     * @param  start int start index.
     * @param  length int length.
     * @throws SAXException 
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        temp = new String(ch, start, length);
    }

    /**
     * the end tag xml to look for
     * @param  uri String space name.
     * @param  localName String lacal name.
     * @param  qName String query name.
     * @throws SAXException 
     */
    public void endElement(String uri, String localName,
            String qName) throws SAXException {

            if (qName.equalsIgnoreCase("Article")) {
                articles.add(article);
            } else if (qName.equalsIgnoreCase("hash")) {
                article.setTitleHash(temp);
            } else if (qName.equalsIgnoreCase("title")) {
                article.setTitle(temp);
            } else if (qName.equalsIgnoreCase("users")) {
                article.setUsers(temp);
            } else if (qName.equalsIgnoreCase("name")){
                annotation.setTag(Tag.createTag(temp));//use static foctory
            } else if (qName.equalsIgnoreCase("count")){
                annotation.setCount(temp);
            } else if (qName.equalsIgnoreCase("tag")) {
                 annotations.add(annotation);
            } else if (qName.equalsIgnoreCase("tags")) {
                article.setAnnotations(annotations);
            } 
    }

    /**
     * @return vector of articles 
     */
    public Vector getArticles(){
        return articles;
    }

    // public static void main(String[] args) throws Exception {

    //     SAXParserFactory spf = SAXParserFactory.newInstance();
    //     spf.setNamespaceAware(true);
    //     SAXParser saxParser = spf.newSAXParser();
    //     XMLReader xmlReader = saxParser.getXMLReader();
    //     SAXDataExtraction sax = new SAXDataExtraction();
    //     xmlReader.setContentHandler(sax);
    //     xmlReader.parse("data/tag-data.xml");

    //     Vector articles_b = sax.getArticles();
    //     // // count the number of articles
    //     // System.out.println(articles_b.size());
    //     // // print the hashes of all articles

    //     // System.out.println("Task 1: count the total number of articles in the dataset.");
    //     // System.out.println(">> " + articles.size());

    //     // System.out.println("Task 2: count the total number of unique tags in the dataset..");
    //     // System.out.println(">> " + item.getTag().size());
    //     for (int i = 0; i <2; i++) {
    //         System.out.println(((Article) articles_b.elementAt(i)).getTitleHash());
    //         System.out.println(((Article) articles_b.elementAt(i)).isFindTag(new Tag("usa")));
    //         LinkedList<Annotation> a = ((Article) articles_b.elementAt(i)).getAnnotations();
    //         for(Annotation item : a){
    //             System.out.println(item.getTag().toString());
    //         }
    //         // System.out.println(((Article) articles.elementAt(i)).getAnnotations().toString());
    //     }
    // }

}
