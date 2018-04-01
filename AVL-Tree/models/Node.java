public class Node implements NodeInterface {
  private int key;
  private Object element;
  private Node parent, leftChild, rightChild;
  
  public Node(Node parent, Node leftChild, Node rightChild, int key, Object element) {
  	this.key = key;
    this.element = element;
    this.parent = parent;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }
  public int key() {
    return key;
  }
  public void setKey(int key) {
    this.key = key;
  }
  public Object element() {
    return element;
  }
  public void setElement(Object element) {
    this.element = element;
  }
  public Node parent() {
    return parent;
  }
  public void setParent(Node parent) {
    this.parent = parent;
  }
  public Node leftChild() {
    return leftChild;
  }
  public void setLeftChild(Node leftChild) {
    this.leftChild = leftChild;
  }
  public Node rightChild() {
    return rightChild;
  }
  public void setRightChild(Node rightChild) {
    this.rightChild = rightChild;
  }
}