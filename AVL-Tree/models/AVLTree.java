public class AVLTree extends BSTree implements AVLTreeInterface {
	public AVLTree() {
		super();
	}
	public AVLNode root() {
		return (AVLNode) root;
	}
	public boolean isEmpty() {
  	return size == 0;
  }
  private void rotbal(AVLNode v, int dir) { // balancear quando rotação
  		AVLNode child;
  		int childBF;
	  	if (dir > 0) { // à direita
	  		child = (AVLNode) v.leftChild();
	  		childBF = child == null ? 0 : child.getBF();
	  		v.setBF(v.getBF() - 1 - Math.max(childBF, 0));
	  		if (child != null) child.setBF(child.getBF() - 1 + Math.min(v.getBF(), 0));
	  	} else { // à esquerda
	  		child = (AVLNode) v.rightChild();
	  		childBF = child == null ? 0 : child.getBF();
	  		v.setBF(v.getBF() + 1 - Math.min(childBF, 0));
	  		if (child != null) child.setBF(child.getBF() + 1 + Math.max(v.getBF(), 0));
	  	}
  }
  private void rotCheck(AVLNode v) { // verifica necessidade de rotacionar
  	int bf = v.getBF();
  	if (Math.abs(bf) > 1) {
  		if (bf == 2) { // rotação à direita
  			AVLNode leftChild = (AVLNode) v.leftChild();
  			// filho esquerda fb < 0 ? rotação dupla : rotação simples
  			if (leftChild != null && leftChild.getBF() < 0) {
  				doubleRightRotation(v);
  			} else {
  				rightRotation(v);
  			}
  		} else { // rotação à esquerda
  			AVLNode rightChild = (AVLNode) v.rightChild();
  			// filho direito fb > 0 ? rotação dupla : rotação simples
  			if (rightChild != null && rightChild.getBF() > 0) {
  				doubleLeftRotation(v);
  			} else {
  				leftRotation(v);
  			}
  		}
  	}
  }
  private void inbal(AVLNode v, int k) { // balancear quando inserção
  	if (v != null) {
  		int pk = key(v); // pk = parent key
	  	v.setBF(v.getBF() + (k < pk ? 1 : -1)); // fb = veio do filho esquerdo ? fb + 1 : fb - 1
	  	rotCheck(v);
	  	if (v.getBF() != 0) inbal((AVLNode) parent(v), key(v)); // repetir enquanto fb != 0	
  	}
  }
  private void rembal(AVLNode v, int k) { // balancear quando remoção
  	if (v != null) {
	  	int pk = key(v); // pk = parent key
	  	v.setBF(v.getBF() + (k < pk ? -1 : 1)); // fb = veio do filho esquerdo ? fb - 1 : fb + 1
	  	rotCheck(v);
	  	if (v.getBF() == 0) rembal((AVLNode) parent(v), key(v)); // repetir enquanto fb == 0
  	}
  }
  private void leftRotation(AVLNode v) {
  	// atualizar fb, quando rotação
  	rotbal(v, -1);
  	
  	// pai (nó desbalanceado)
  	AVLNode parent = (AVLNode) v.parent();
  	
  	// filho direito (nó desbalanceado)
  	AVLNode rightChild = (AVLNode) v.rightChild();
  	
		// filho esquerdo (filho direito)
		AVLNode leftChild = (AVLNode) rightChild.leftChild();
		
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
  private void rightRotation(AVLNode v) {
  	// atualizar fb, quando rotação
  	rotbal(v, 1);
  	
  	// pai (nó desbalanceado)
  	AVLNode parent = (AVLNode) v.parent();
  	
  	// filho esquerdo (nó desbalanceado)
  	AVLNode leftChild = (AVLNode) v.leftChild();
  	
  	// filho direito (filho esquerdo)
  	AVLNode rightChild = (AVLNode) leftChild.rightChild();
  	
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
  private void doubleLeftRotation(AVLNode v) {
  	// rebalancear à direita
  	rightRotation((AVLNode) v.rightChild());
  	
  	// rebalancear à esquerda
  	leftRotation((AVLNode) v);
  }
  private void doubleRightRotation(AVLNode v) {
  	// rebalancear à esquerda
  	leftRotation((AVLNode) v.leftChild());
  	
  	// rebalancear à direita
  	rightRotation((AVLNode) v);
  }
  @Override
	public void insert(int k, Object o) {
		if (size > 0) {
			AVLNode v = (AVLNode) find(k);
	    int nk = key(v);
	    if (k < nk){
	      AVLNode w = new AVLNode(v, null, null, k, o);
	      v.setLeftChild(w);
	      size++;
	      inbal(v, k);
	    } else if (k > nk) {
	      AVLNode w = new AVLNode(v, null, null, k, o);
	      v.setRightChild(w);
	      size++;
	      inbal(v, k);
	    } else {
			  v.setElement(o);	
	    }	
		} else {
			root = new AVLNode(null, null, null, k, o);
			size = 1;
		}
	}
	@Override
  protected Object removeNode(int k) {
    AVLNode v = (AVLNode) find(k);
    AVLNode parent;
    int nk = key(v); // chave do nó
    Object o = null;
    
    // Encontrou a chave
    if (k == nk) {
      parent = (AVLNode) v.parent();
      
      // Não tem filhos
      if (isExternal(v)) {
        if (nk < key(parent))
          parent.setLeftChild(null);
        else
          parent.setRightChild(null);
        
        o = v.element();
        rembal(parent, nk); // rebalancear pai
        
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
        rembal(parent, nk); // rebalancear pai
        
      // Tem dois filhos
      } else {
        AVLNode w = (AVLNode) successor(v.rightChild()); // busca o nó sucessor
        removeNode(key(w));
        o = v.element();
        swap(v, w);
      }
      v = null;
    }
    return o;
  }
}