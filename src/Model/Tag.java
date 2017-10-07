package CSE210_Assignment.src.Model;
/*************************************************************************
 *
 *  A tag class used to creat unique tag and avoid duplicate
 *  Each tag must has different tag name
 *
 *************************************************************************/
import java.util.HashMap;

/**
 *  The <tt>Tag</tt> class represents a tag item in the annotation of an articles.
 *  It supports the static factory <em>createTag</em> operations, along with methods
 *  for get the total number of tags, to string.
 *  <p>
 *  This implementation uses a HashMap with a static class for
 *  create unduplicate tag. 
 *  The <em>createTag</em>, <em>toString</em>, <em>size</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *
 *  @author Yudi An
 */
public class Tag{
	private String tag = "";//tag name
	private static int total = 0;//total tag number
	private static HashMap<String,Tag> availableTags = new HashMap<String,Tag>();//used for static factory
	
	/**
     * Initializes a new tag
     */
	public Tag(){
		total++;
	}

	 /**
     * Initializes a new tag
     * @param tag name for tag
     */
	public Tag(String tag){
		this.tag = tag;
		total++;
	}


	/**
     * Return the month.
     * @param s name for tag
     * @return if tag already exits, return old tag, else return new tag.
     */
	public static Tag createTag(String s){
		Tag t;
     	if(availableTags.containsKey(s)){
     		return availableTags.get(s);
     	} else {
	     	t = new Tag(s);
	     	availableTags.put(s, t);
     	}
     	return t;
   }

 	/**
     * Return a string representation of this tag.
     * @return the string representation in the foramt String
     */
	public String toString(){
		return tag;
	}

	/**
     * Returns the number of items in the tags.
     * @return the number of items in the tags
     */
	public static int size(){
		return total;
	}


	//unit test for Tag
	// public static void main(String[] args) {
	// 	Tag t = new Tag();
	// 	Tag r = new Tag("history");
	// 	System.out.println(t.size());
	// 	// System.out.println(r.getContent());		
	// }

	
}