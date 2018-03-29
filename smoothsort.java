class Node implements Comparable<Node> {
	private int element, order;
	
	public Node(int element, int order) {
		this.element = element;
		this.order = order;
	}
	
	public int getElement() { return element; }
	public void setElement(int element) { this.element = element; }
	
	public int getOrder() { return order; }
	public void setOrder(int order) { this.order = order; }
	
	public int compareTo(Node n) {
		if (this.element < n.getElement()) return -1;
		if (this.element > n.getElement()) return 1;
		return 0;
	}
}


class LeonardoTree {
	static final int[] LN = {
		1, 1, 3, 5, 9, 15, 
		25, 41, 67, 109, 177, 
		287, 465, 753, 1219, 1973, 
		3193, 5167, 8361, 13529, 21891
	};
	
	private Node[] ltree;
	private int[] orderList;
	private int n_elem, n_ltree, order;
	
	public LeonardoTree() {
		ltree = new Node[64];
		orderList = new int[6];
		n_elem = 0;
		n_ltree = 0;
		order = 1;
	}
	
	private int getNextOrder() {
		return order == 1 ? order-- : order++;
	}
	
	private Node secondChild(int root) {
		return ltree[root - 1];
	}
	
	private Node firstChild(int root, int order) {
		int index = root - 1 - LN[order - 2];
		return ltree[index];
	}
	
	private void swap(Node n1, Node n2) {
		int aux = n1.getElement();
		n1.setElement(n2.getElement());
		n2.setElement(aux);
	}
	
	private void fixUp(int root, int order) {
		Node first = firstChild(root, order);
		Node second = secondChild(root);
		if (first.getOrder() + second.getOrder() > 1 && first.compareTo(second) >= 0) {
			swap(first, second);
			downHeap(root - 1 - LN[first.getOrder() - 2], first.getOrder());
		} else {
			rebalanceHeap(root - 1 - LN[order - 2]);
			rebalanceHeap(root - 1);
		}
	}
	
	private void rebalanceHeap(int i) {
		int index = i != -1 ? i : n_elem;
		while (index > 0 && ltree[index].getElement() < ltree[index - 1].getElement()) {
			swap(ltree[index], ltree[index - 1]);
			downHeap(index - 1, ltree[index - 1].getOrder());
			index--;
		}
	}
	
	private void downHeap(int root, int order) {
		Node first, second, greaterChild;
		int aux_root;
		while (order > 1) {
			first = firstChild(root, order);
			second = secondChild(root);
			if (first.compareTo(second) >= 0) { 
				greaterChild = first;
				aux_root = root - 1 - LN[order - 2];
			} else {
				greaterChild = second;
				aux_root = root - 1;
			}
			order = greaterChild.getOrder();
			if (ltree[root].getElement() > greaterChild.getElement())
				break;
			else
				swap(ltree[root], greaterChild);
			root = aux_root;
		}
	}
	
	private boolean mergable() {
		if (n_ltree > 1) {
			if (orderList[n_ltree - 2] - orderList[n_ltree - 1] == 1 ||
			orderList[n_ltree - 2] == orderList[n_ltree - 1]) {
				orderList[n_ltree - 2]++;
				orderList[n_ltree - 1] = 0;
				n_ltree--;
				return true;
			}
		}
		return false;
	}
	
	public void insert(int element) {
		if (mergable()) {
			ltree[n_elem] = new Node(element, orderList[n_ltree - 1]);
			downHeap(n_elem, ltree[n_elem].getOrder());
			n_elem++;
		} else {
			ltree[n_elem] = new Node(element, getNextOrder());
			rebalanceHeap(-1);
			n_elem++;
			orderList[n_ltree++] = 1;
		}
	}
	
	public int remove() {
		int element;
		if (ltree[n_elem - 1].getOrder() > 1) {
			fixUp(n_elem - 1, ltree[n_elem - 1].getOrder());
			element = ltree[n_elem - 1].getElement();
		} else {
			element = ltree[n_elem - 1].getElement();
		}
		ltree[--n_elem] = null;
		list();
		System.out.println("----");
		return element;
	}
	
	public void list() {
		for (int i = 0; i < n_elem; ++i) {
			System.out.println("["+i+"] "+ltree[i].getElement()+" "+ltree[i].getOrder());
		}
	}
	
	public void listOrders() {
		for (int i = 0; i < n_ltree; ++i) {
			System.out.print(orderList[i] + " ");
		}
	}
	
	public void smoothSort(int[] arr) {
		for (int i = 0; i < arr.length; ++i) insert(arr[i]);
		for (int i = n_elem - 1; i >= 0; --i) arr[i] = remove();
	}
}

class Main {
	public static void main(String[] args) {
		LeonardoTree lt = new LeonardoTree();
		int[] arr = {8, 2, 5, 6, 3, 7, 10, 9, 1, 4};
		//for (int i = 0; i < 10; ++i) lt.insert((int) (Math.random() * 50));
		//lt.list();
		//lt.listOrders();
		for (int i = 0; i < 10; ++i) arr[i] = (int) (Math.random() * 50);
		lt.smoothSort(arr);
		for (int i = 0; i < arr.length; ++i) System.out.print(arr[i] + " ");
	}
}
