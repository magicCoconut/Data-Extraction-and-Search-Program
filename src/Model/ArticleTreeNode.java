package CSE210_Assignment.src.Model;
/*************************************************************************
 *
 *  DefaultMutableTreeNode with it own reference when jtree click
 *
 *************************************************************************/


import javax.swing.tree.DefaultMutableTreeNode;

/**
 *  The <tt>ArticleTreeNode</tt> class is to impliment a clickable tree node with reference
 *  It supports the <em>setReference</em> to set the original type of this node.
 *
 *  @author Yudi An
 */
public class ArticleTreeNode extends DefaultMutableTreeNode {

    public Object Reference; // reference the object

    /**
     * set reference object.
     * @param Reference Object its original object type 
     */
    public void setReference(Object Reference) {
        this.Reference = Reference;
    }

    /**
     * Initializes a new ArticleTreeNode.
     * @param title String for node title
     */
    public ArticleTreeNode(String title) {
        setUserObject(title);//override class
    }
}