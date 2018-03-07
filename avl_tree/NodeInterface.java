public interface NodeInterface {
  public abstract Node rightChild();
  public abstract Node leftChild();
	public abstract Node parent();
  public abstract int key();
  public abstract Object element();
  public abstract void setRightChild(Node rightChild);
  public abstract void setLeftChild(Node leftChild);
  public abstract void setParent(Node parent);
  public abstract void setKey(int k);
  public abstract void setElement(Object o);
}