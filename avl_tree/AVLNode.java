public class AVLNode extends Node implements AVLNodeInterface {
	private int bf;
	
	public AVLNode(AVLNode parent, AVLNode leftChild, AVLNode rightChild, int key, Object element) {
  	super(parent, leftChild, rightChild, key, element);
  	bf = 0;
  }
  public void setBF(int bf) {
  	this.bf = bf;
  }
  public int getBF() {
  	return bf;
  }
}