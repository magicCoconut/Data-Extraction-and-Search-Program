package CSE210_Assignment.src.Model;
/*************************************************************************
 *
 *  A comparable article class used to creat article instance in articales
 *
 *************************************************************************/
import java.util.Vector;
import java.util.Comparator;

/**
 *
 *  The <tt>Article</tt> class represents an article instance in articales.
 *  
 * @author Yudi An
 */
public class Article implements Comparable<Article>{
	private String titleHash;  //hash code for artcle
	private LinkedList<Annotation> annotations;//all annotation of article
	private Annotation annotation; //a annotation instance of article
	private String title; //article title
	private int users; //article users
    private static Tag defult; //defult tag used for comparable.

    /**
     * Initializes a new Article.
     */
	public Article(){
    }
    
    /**
     * Initializes a new Article.
     * @param titleHash title hash code 
     * @param annotations of annotations 
     */
	public Article(String titleHash, LinkedList annotations){
		this.titleHash = titleHash;
		this.annotations = annotations;

	}

    /**
     * Peek the hash code title of article.
     * @return the hash code title of article.
     */
	public String getTitleHash(){
        return titleHash;
    }

    /**
     * Peek the title of article.
     * @return the title of article.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Peek the users of article.
     * @return the users of article.
     */
    public int getusers(){
        return users;
    }

    /**
     * Peek the ordered list of annotations of article.
     * @return the ordered list of annotations of article.
     */
    public LinkedList getAnnotations(){
        return annotations;
    }

    /**
     * Set a defult tag for article to compare for.
     * @param s to the defult tag for article to compare for.
     */
    public static void setDefultCompareTag(Tag s){
        defult = s;
    }

    /**
     * Peek a defult tag for article to compare for.
     * @return the defult tag for article to compare for.
     */
    public static Tag getDefultCompareTag(){
        return defult;
    }

    /**
     * Compare this Article to that Article.
     * @return { a negative integer, zero, or a positive integer }, depending
     *    on whether this Article is { before, equal to, after } that Article
     */
    public int compareTo(Article other) {
        return this.findTag(defult).getcount() - other.findTag(defult).getcount();
    }

    /**
     * Peek the number of annotations of article.
     * @return the number of annotations of article.
     */    
    public int getTagNum(){
    	return annotations.size();
    }

    /**
     * check wether the tag is in the annotation of article.
     * @param tag for article to find.
     * @return {true or false}, depending on whether this tag {can or cannot} be found.
     */
    public boolean isFindTag(Tag tag){    	
    	for(Annotation item : annotations){
    		if(item.getTag().toString().equals(tag.toString()))
    			return true;
        }
        return false;
    }

    /**
     * check wether the tag is in the annotation of article.
     * @param tag for article to find.
     * @return {the annotation or null}, depending on whether this tag {can or cannot} be found.
     */
    public Annotation findTag(Tag tag){        
        for(Annotation item : annotations){
                if(item.getTag().toString().equals(tag.toString()))
                    return item;
        }
        return null;
    }

    /**
     * Set the hash code title of article.
     * @param titleHash String the hash code title of article.
     */
	public void setTitleHash(String titleHash){
        this.titleHash = titleHash;
    }

    /**
     * Set the title of article.
     * @param title String the title of article.
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Set the users of article.
     * @param users String the users of article.
     */
    public void setUsers(String users){
        this.users = Integer.parseInt(users);
    }

    /**
     * Set the ordered list of annotations of article.
     * @param annotations LinkedList the ordered list of annotations of article.
     */
    public void setAnnotations(LinkedList annotations){
        this.annotations = annotations;
    }

    /**
     * Set a annotation for article.
     * @param annotation Annotation the annotation for article.
     */
    public void setAnnotation(Annotation annotation){
        annotations.add(annotation);
    }
    
    // public static void main(String[] args) {
    //     Annotation a = new Annotation(Tag.createTag("history"),4);
    //     Annotation another = new Annotation(Tag.createTag("chemical"),2);
    //     LinkedList annotations = new LinkedList();
    //     annotations.add(a);
    //     annotations.add(another);
    //     Article article = new Article("5619983c9b25f98d04bbe93cf7525b16",annotations);
    //     System.out.println(article.isFindTag(Tag.createTag("history")));
    //     System.out.println(article.getTagNum());
    //     System.out.println(article.getTitleHash());

    // }
    

	
}