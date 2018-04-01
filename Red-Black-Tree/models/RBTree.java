import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class RBTree extends BSTree implements RBTreeInterface {
	private final int RED = 0, BLACK = 1;
	
	public RBTree() {
		super();
		size = 0;
	}
	public RBNode root() {
		return (RBNode) root;
	}
	public boolean isEmpty() {
		return size > 0;
	}
	private void leftRotation(RBNode v) {
  	// pai (nó desbalanceado)
  	RBNode parent = (RBNode) v.parent();
  	
  	// filho direito (nó desbalanceado)
  	RBNode rightChild = (RBNode) v.rightChild();
  	
		// filho esquerdo (filho direito)
		RBNode leftChild = (RBNode) rightChild.leftChild();
		
		// pai (filho direito) = pai (nó desbalanceado)
		rightChild.setParent(parent);
		
		// filho esquerdo (filho direito) = nó desbalanceado
		rightChild.setLeftChild(v);
  	
  	// se existe um pai (do nó desbalanceado), filho = filho direito
  	if (parent != null) {
  		if (parent.key() > v.key())
  			parent.setLeftChild(rightChild);
  		else
  			parent.setRightChild(rightChild);
  	}
  	
  	// filho direito (nó desbalanceado) = filho esquerdo (filho direito)
  	v.setRightChild(leftChild);
  	
  	// se existe um filho esquerdo (filho direito), pai = nó desbalanceado
  	if (leftChild != null) leftChild.setParent(v);
  	
  	// pai (nó desbalanceado) = filho direito
  	v.setParent(rightChild);
  	
  	// root = filho direito, se nó desbalanceado é root
  	if (v == root) root = rightChild;
  }
  private void rightRotation(RBNode v) {
  	// pai (nó desbalanceado)
  	RBNode parent = (RBNode) v.parent();
  	
  	// filho esquerdo (nó desbalanceado)
  	RBNode leftChild = (RBNode) v.leftChild();
  	
  	// filho direito (filho esquerdo)
  	RBNode rightChild = (RBNode) leftChild.rightChild();
  	
  	// pai (filho esquerdo) = pai (nó desbalanceado)
  	leftChild.setParent(parent);
  	
  	// filho esquerdo (filho esquerdo) = nó desbalanceado
		leftChild.setRightChild(v);
  	
  	// se existe um pai (do nó desbalanceado), filho = filho esquerdo
  	if (parent != null) {
  		if (parent.key() > v.key())
  			parent.setLeftChild(leftChild);
  		else
  			parent.setRightChild(leftChild);
  	}
  	
  	// filho esquerdo (nó desbalanceado) = filho direito (filho esquerdo)
  	v.setLeftChild(rightChild);
  	
  	// se existe um filho direito (filho esquerdo), pai = nó desbalanceado
  	if (rightChild != null) rightChild.setParent(v);
  	
  	// pai (nó desbalanceado) = filho esquerdo
  	v.setParent(leftChild);
  	
  	// root = filho esquerdo, se nó desbalanceado é root
  	if (v == root) root = leftChild;
  }
  private void doubleLeftRotation(RBNode v) {
  	// rebalancear à direita
  	rightRotation((RBNode) v.rightChild());
  	
  	// trocar as cores entre pai e filho
  	swapColor(v, (RBNode) v.rightChild());
  	
  	// rebalancear à esquerda
  	leftRotation((RBNode) v);
  }
  private void doubleRightRotation(RBNode v) {
  	// rebalancear à esquerda
  	leftRotation((RBNode) v.leftChild());
  	
  	// trocar as cores entre pai e filho
  	swapColor(v, (RBNode) v.leftChild());
  	
  	// rebalancear à direita
  	rightRotation((RBNode) v);
  }
	private RBNode sibling(RBNode v) { // retorna o nó irmão de v
		RBNode parent = (RBNode) v.parent();
		return (RBNode) (v == parent.leftChild() ? parent.rightChild() : parent.leftChild());
	}
	private RBNode uncle(RBNode v) { // retorna o nó tio de v
		return sibling((RBNode) v.parent());
	}
	private boolean hasBlackChilds(RBNode v) {
		RBNode leftChild = (RBNode) v.leftChild();
		RBNode rightChild = (RBNode) v.rightChild();
		return ((leftChild == null || leftChild.getColor() == BLACK) &&
						(rightChild == null || rightChild.getColor() == BLACK));
	}
	private void swapColor(RBNode v, RBNode w) {
		int aux = v.getColor();
		v.setColor(w.getColor());
		w.setColor(aux);
	}
	private void recolor(RBNode v) { // recolorir quando inserção
		if (v == root) {
			v.setColor(BLACK);
		} else {
			RBNode parent = (RBNode) v.parent();
			if (parent.getColor() == BLACK) { // caso 1
				return;
			} else {
				RBNode uncle = uncle(v);
				RBNode grandParent = (RBNode) parent.parent();
				if (uncle != null && uncle.getColor() == RED) { // caso 2 (tio é rubro)
					uncle.setColor(BLACK); // tio vira negro
					parent.setColor(BLACK); // pai vira negro
					grandParent.setColor(RED); // avó vira rubro
					recolor(grandParent);
				} else { // caso 3 (tio é negro)
					int pkey = key(parent), nkey = key(v); // chaves do pai e do filho (nó inserido)
					if (pkey > key(grandParent)) { // pai está à direita do avó
						if (nkey > pkey) { // nó inserido está a direita do pai
							swapColor(grandParent, parent);
							leftRotation(grandParent);
						} else { // nó inserido está á esquerda do pai
							doubleLeftRotation(grandParent);
						}
					} else { // pai está à esquerda do avó
						if (nkey > pkey) { // nó inserido está à direita do pai
							doubleRightRotation(grandParent);
						} else { // nó inserido está à esquerda do pai
							swapColor(grandParent, parent);
							rightRotation(grandParent);
						}
					}
				}
			}
		}
	}
	private void rebalance(RBNode v, int dir) { // rebalancear quando remoção
		if (v != null) {
			// passando o pai como parâmetro e recuperando os dois filhos
			RBNode leftChild = (RBNode) v.leftChild(); // filho esquerdo
			RBNode rightChild = (RBNode) v.rightChild(); // filho direito
			RBNode dblack = dir < 0 ? leftChild : rightChild; // filho duplo negro
			RBNode sibling = sibling(dblack); // irmão do duplo negro
			RBNode closer, farther;
			
			if (dir < 0) { // duplo negro está à esquerda
				closer = (RBNode) sibling.leftChild(); // sobrinho próximo
				farther = (RBNode) sibling.rightChild(); // sobrinho distante
			} else { // duplo negro esta está à direita
				closer = (RBNode) sibling.rightChild(); // sobrinho próximo
				farther = (RBNode) sibling.leftChild(); // sobrinho distante
			}
			
			if (v.getColor() == RED) { // situação 1 ou 4
				if (sibling.getColor() == BLACK) {
					if (hasBlackChilds(sibling)) { // caso 2b
						swapColor(v, sibling);
					} else if (farther == null || farther.getColor() == BLACK) { // caso 3
						swapColor(sibling, closer); // troca cores entre irmão e sobrinho próximo
						if (closer == sibling.leftChild()) {
							rightRotation(sibling);
							rebalance(v, -1); // continua no caso 4
						} else { 
							leftRotation(sibling);
							rebalance(v, 1); // continua no caso 4
						}
					} else { // caso 4
						swapColor(v, sibling); // troca cores entre pai e irmão
						farther.setColor(BLACK); // troca cor do sobrino distante
						if (farther == sibling.rightChild()) leftRotation(v);
						else rightRotation(v);
					}
				}
			} else { // situação 3 (pai negro)
				if (v.getColor() == BLACK && sibling.getColor() == RED) { // caso 1
					swapColor(v, sibling); // troca cores entre pai e irmão
					if (dir < 0) {// duplo negro está à esquerda
						leftRotation(v);
						rebalance(v, -1); // continua no caso 2b
					} else {// duplo negro está à esquerda
						rightRotation(v);
						rebalance(v, 1); // continua no caso 2b
					}
				} else if (v.getColor() == BLACK && sibling.getColor() == BLACK && hasBlackChilds(sibling)) { // caso 2a
					sibling.setColor(RED);
					RBNode parent = (RBNode) v.parent();
					if (parent != null && parent != root) {
						if (parent == parent.parent().leftChild())
							rebalance(parent, -1);
						else
							rebalance(parent, 1);
					}
				} else if (v.getColor() == RED && sibling.getColor() == BLACK && hasBlackChilds(sibling)) { // caso 2b
					swapColor(v, sibling);
				} else if (farther == null || farther.getColor() == BLACK) { // caso 3
					swapColor(sibling, closer); // troca cores entre irmão e sobrinho próximo
					if (closer == sibling.leftChild()) {
						rightRotation(sibling);
						rebalance(v, -1); // continua no caso 4
					} else { 
						leftRotation(sibling);
						rebalance(v, 1); // continua no caso 4
					}
				} else { // caso 4
					swapColor(v, sibling); // troca cores entre pai e irmão
					farther.setColor(BLACK); // troca cor do sobrino distante
					if (farther == sibling.rightChild()) leftRotation(v);
					else rightRotation(v);
				}
			}
		}
	}
	private RBNode setRefsAndRecolor(RBNode v) {
		RBNode parent = (RBNode) v.parent();
		RBNode leftChild = (RBNode) v.leftChild();
		RBNode rightChild = (RBNode) v.rightChild();
		
		if (leftChild != null) {
			leftChild.setParent(parent);
			leftChild.setColor(v.getColor());
			return leftChild;
		} else {
			rightChild.setParent(parent);
			rightChild.setColor(v.getColor());
			return rightChild;
		}
	}
	@Override
	public void insert(int k, Object o) {
		if (size > 0) {
			RBNode v = (RBNode) find(k);
	    int nk = key(v);
	    if (k < nk){
	      RBNode w = new RBNode(v, null, null, k, o);
	      v.setLeftChild(w);
	      size++;
	      recolor(w);
	    } else if (k > nk) {
	      RBNode w = new RBNode(v, null, null, k, o);
	      v.setRightChild(w);
	      size++;
	      recolor(w);
	    } else {
			  v.setElement(o);
	    }
		} else {
			root = new RBNode(null, null, null, k, o);
			root().setColor(BLACK);
			size = 1;
		}
	}
	@Override
  public Object removeNode(int k) {
  	RBNode v = (RBNode) find(k);
    RBNode parent;
    int nk = key(v); // chave do nó
    Object o = null;
    
    // Encontrou a chave
    if (k == nk) {
      parent = (RBNode) v.parent();
      
      // Não tem filhos
      if (isExternal(v)) {
        if (nk < key(parent)) {
          if (v.getColor() == BLACK) rebalance(parent, -1);
          parent.setLeftChild(null);
        } else {
        	if (v.getColor() == BLACK) rebalance(parent, 1);
          parent.setRightChild(null);
        }
        
        o = v.element();
          
      // Tem apenas um filho (esquerdo ou direito)
      } else if (v.leftChild() == null && v.rightChild() != null || 
      						v.leftChild() != null && v.rightChild() == null) {
				
				// atualiza referências/cor retornando o sucessor
      	RBNode w = setRefsAndRecolor(v);
      	
        if (nk < key(parent)) {// filho à remoção está na esquerda
        	parent.setLeftChild(w);
        	if (w.getColor() == BLACK) rebalance(v, -1);
        } else { // filho à remoção está na direita
        	parent.setRightChild(w);
        	if (w.getColor() == BLACK) rebalance(v, 1);
        }
      	
        v.setLeftChild(null);
        v.setRightChild(null);
        
        o = v.element();
        
      // Tem dois filhos
      } else {
        RBNode w = (RBNode) successor(v.rightChild()); // busca o nó sucessor
        removeNode(key(w));
        o = v.element();
        swap(v, w);
      }
      v = null;
    }
    return o;
  }
  private void setPositions(RBNode[][] t) {
    int i = 0;
    RBNode v;
    for (Iterator<RBNode> w = nodes(); w.hasNext(); ++i) {
      v = w.next();
      t[depth(v)][i] = v;
    }
  }
  public void showTree() {
    RBNode[][] t = new RBNode[height(root) + 1][size];
    setPositions(t);
    for (int j = 0; j < t.length; ++j) {
      for (int i = 0; i < t[j].length; ++i) {
        System.out.print(" ");
        System.out.print(t[j][i] != null ? (t[j][i].getColor() == RED ? "R" : "B") : " ");
      }
      System.out.println();
    }
    System.out.println();
  }
  
  /* only for debug purpose */
  public void showTreeWithKeys() {
    RBNode[][] t = new RBNode[height(root) + 1][size];
    setPositions(t);
    for (int j = 0; j < t.length; ++j) {
      for (int i = 0; i < t[j].length; ++i) {
        System.out.print(" ");
        System.out.print(t[j][i] != null ? (t[j][i].getColor() == RED ? t[j][i].key()+"-R" : t[j][i].key()+"-B") : " ");
      }
      System.out.println();
    }
    System.out.println();
  }
}