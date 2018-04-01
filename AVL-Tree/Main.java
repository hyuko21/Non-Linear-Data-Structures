public class Main {
	public static void main (String[] args) {
		AVLTree Arv = new AVLTree();
		for (int i = 0; i < 40; ++i) {
			Arv.insert((int) (Math.random() * 100), i);
		}
		Arv.showTree();
		
		Arv = new AVLTree();
		for (int i = 0; i < 10; ++i) {
			Arv.insert((int) (Math.random() * 100), i);
		}
		Arv.showTree();
		
		Arv = new AVLTree();
		for (int i = 0; i < 20; ++i) {
			Arv.insert((int) (Math.random() * 100), i);
		}
		Arv.showTree();
		
		Arv = new AVLTree();
		for (int i = 0; i < 30; ++i) {
			Arv.insert((int) (Math.random() * 100), i);
		}
		Arv.showTree();
	}
}