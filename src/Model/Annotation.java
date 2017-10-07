package CSE210_Assignment.src.Model;
/*************************************************************************
 *
 *  A comparable tag class used to creat annotation in articales
 *
 *************************************************************************/

/**
 *  The <tt>Annotation</tt> class represents the annotation of an articles.
 *  It supports the <em>getTag</em> and <em>getCount</em> operations, along with methods
 *  for setting the tag, setting the tag count and compare oprations.
 *  <p>
 *  The <em>getTag</em>, <em>getCount</em>, <em>setTag</em>,<em>setCount</em>, <em>compareTo</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *
 *  @author Yudi An
 */
public class Annotation implements Comparable<Annotation>{
	private Tag tag; //tag for annotaion
	private int count; //count number of tag

	/**
     * Initializes a new annotation.
     */
	public Annotation(){
	}
	
	/**
     * Initializes a new annotation.
     * @param tag name 
     * @param count count 
     */
	public Annotation(Tag tag, int count){
		this.tag = tag;
		this.count = count;
	}

	/**
     * Return the tag of this annotation.
     * @return Return the tag of this annotation.
     */
	public Tag getTag(){
		return tag;
	}

	/**
     * Return the tag count of this annotation.
     * @return Return the tag count of this annotation.
     */
	public int getcount(){
		return count;
	}

	/**
     * Set the tag for the annotation.
     * @param tag to be set in annotation.
     */
	public void setTag(Tag tag){
		this.tag = tag;
	}

	/**
     * Set the count for the annotation.
     * @param count to be set in annotation.
     */
	public void setCount(String count){
		this.count = Integer.parseInt(count);
	}

	/**
     * Compare this Annotation to that Annotation.
     * @return { a negative integer, zero, or a positive integer }, depending
     *    on whether this Annotation is { before, equal to, after } that Annotation
     */
	public int compareTo(Annotation other) {
    	return this.count - other.getcount();
	}

	//unit test 
	// public static void main(String[] args) {
	// 	Annotation a = new Annotation(Tag.createTag("history"),4);
	// 	Annotation another = new Annotation(Tag.createTag("chemical"),2);
	// 	System.out.println(a.getTag());
	// 	System.out.println(a.getcount());
	// 	System.out.println(a.compareTo(another));

	// }
	
}