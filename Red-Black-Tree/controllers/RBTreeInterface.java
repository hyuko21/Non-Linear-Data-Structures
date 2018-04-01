public interface RBTreeInterface {
    public abstract void insert(int k, Object o);
    public abstract boolean isEmpty();
    public abstract Object remove(int k);
    public abstract RBNode root();
    public abstract void showTree();
}