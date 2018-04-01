class Main {
  public static void main(String[] args) {
    RBTree Arv = new RBTree();
		Arv.insert(30, 0);
		Arv.insert(10, 0);
		Arv.insert(40, 0);
		Arv.insert(50, 0);
		Arv.showTreeWithKeys();
		Arv.remove(50);
		Arv.showTreeWithKeys();
		Arv.remove(10);
		Arv.showTreeWithKeys();
		// for(int i=0;i<15;++i) Arv.insert((int) (Math.random() * 100), 0);
		Arv.showTree();
  }
}

