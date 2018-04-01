import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class BSTree {
  protected Node root;
  protected int size;
  
  public BSTree() {
  	size = 0;
  }
  public BSTree(int k, Object o) {
    root = new Node(null, null, null, k, o);
    size = 1;
  }
  public Node root() {
    return root;
  }
  public Node parent(Node v) {
    return v.parent();
  }
  public Node left(Node v) {
    return v.leftChild();
  }
  public Node right(Node v) {
    return v.rightChild();
  }
  public int key(Node v) {
    return v.key();
  }
  public Object element(Node v) {
    return v.element();
  }
  public int size() {
    return size;
  }
  public int height(Node v) {
    if (v != null) {
      if (isExternal(v)) {
        return 0;
      } else {
        int h = 0;
        h = Math.max(height(v.rightChild()), height(v.leftChild()));
        return 1 + h;
      }
    }
    return 0;
  }
  public int depth(Node v) {
    return v == root ? 0 : 1 + depth(v.parent());
  }
  public boolean isExternal(Node v) {
    return v.leftChild() == null && v.rightChild() == null;
  }
  public boolean isInternal(Node v) {
    return v.leftChild() != null || v.rightChild() != null;
  }
  public void swap(Node v, Node w) {
    v.setElement(w.element());
    v.setKey(w.key());
  }
  public Node find(int k) {
      return treeSearch(k, root);
  }
  public Node treeSearch(int k, Node v) {
    if (isExternal(v) || k == key(v)) {
      return v;
    } else if (k < key(v)) {
      if (left(v) != null)
        return treeSearch(k, left(v));
      return v;
    } else {
      if (right(v) != null)
        return treeSearch(k, right(v));
      return v;
    }
  }
  public void insert(int k, Object o) {
    Node v = find(k);
    int nk = key(v);
    if (k < nk){
      Node w = new Node(v, null, null, k, o);
      v.setLeftChild(w);
      size++;
    } else if (k > nk) {
      Node w = new Node(v, null, null, k, o);
      v.setRightChild(w);
      size++;
    } else {
		  v.setElement(o);	
    }
  }
  public Iterator nodes() {
    List<Node> n = new ArrayList();
    inOrder(root, n);
    return n.iterator();
  }
  public void inOrder(Node v, List<Node> nodes) {
    if (isInternal(v) && v.leftChild() != null)
      inOrder(v.leftChild(), nodes);
      
    nodes.add(v);
      
    if (isInternal(v) && v.rightChild() != null)
      inOrder(v.rightChild(), nodes);
  }
  protected Node successor(Node v) {
  	Node leftChild = v.leftChild();
  	if (leftChild != null) return successor(leftChild);
  	return v;
  }
  public Object remove(int k) {
  	Object o = removeNode(k);
    if (o != null) size--;
    return o;
  } 
  protected Object removeNode(int k) {
    Node v = find(k);
    Node parent;
    int nk = key(v); // chave do nó
    Object o = null;
    
    // Encontrou a chave
    if (k == nk) {
      parent = v.parent();
      
      // Não tem filhos
      if (isExternal(v)) {
        if (nk < key(parent))
          parent.setLeftChild(null);
        else
          parent.setRightChild(null);
          
          o = v.element();
          
      // Tem apenas um filho (esquerdo ou direito)
      } else if (v.leftChild() == null && v.rightChild() != null || 
      						v.leftChild() != null && v.rightChild() == null) {
        if (nk < key(parent)) 
        	if (v.leftChild() != null) {
        		parent.setLeftChild(v.leftChild());
        		v.leftChild().setParent(parent);
        	} else {
        		parent.setLeftChild(v.rightChild());
        		v.rightChild().setParent(parent);
        	}
        else
          if (v.leftChild() != null) {
        		parent.setRightChild(v.leftChild());
        		v.leftChild().setParent(parent);
        	} else {
        		parent.setRightChild(v.rightChild());
        		v.rightChild().setParent(parent);
        	}
        
        v.setLeftChild(null);
        v.setRightChild(null);
        
        o = v.element();
        
      // Tem dois filhos
      } else {
        Node w = successor(v.rightChild()); // busca o nó sucessor
        removeNode(key(w));
        o = v.element();
        swap(v, w);
      }
      v = null;
    }
    return o;
  }
  private void setPositions(Node[][] t) {
    int i = 0;
    Node v;
    for (Iterator<Node> w = nodes(); w.hasNext(); ++i) {
      v = w.next();
      t[depth(v)][i] = v;
    }
  }
  public void showTree() {
    Node[][] t = new Node[height(root) + 1][size];
    setPositions(t);
    for (int j = 0; j < t.length; ++j) {
      for (int i = 0; i < t[j].length; ++i) {
        System.out.print(" ");
        System.out.print(t[j][i] != null ? t[j][i].key() : " ");
      }
      System.out.println();
    }
    System.out.println();
  }
}