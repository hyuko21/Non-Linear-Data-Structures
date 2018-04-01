public class RBNode extends Node implements RBNodeInterface {
	private int color; // 0 - RED, 1 - BLACK
	
	public RBNode(RBNode parent, RBNode leftChild, RBNode rightChild, int key, Object element) {
		super(parent, leftChild, rightChild, key, element);
		this.color = 0;
	}
	public int getColor() {
		return this.color;
	}
	public void setColor(int c) {
		this.color = c;
	}
}