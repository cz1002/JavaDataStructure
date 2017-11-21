package DataStructure;

/**
 * Created by Administrator on 2017/11/1 0001.
 */
public class AvlTree<T extends Comparable<? super T>> {
    private static class AvlNode<T> {
        T element;
        AvlNode<T> left;
        AvlNode<T> right;
        //静态情况下默认为零
        int height;

        AvlNode(T theElement) {
            this(theElement, null, null);
        }

        AvlNode(T theElement, AvlNode<T> lt, AvlNode<T> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }
    }

    private AvlNode<T> root;

    public AvlTree() {
        root = null;
    }

    public void insert(T x) {
        root = insert(x, root);
    }

    public void remove(T x) {
        root = remove(x, root);
    }

    private int height(AvlNode<T> t) {
        return t == null ? -1 : t.height;
    }

    //右单旋转
    private AvlNode<T> rotateWithLeftChild(AvlNode<T> k2) {
        AvlNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    //左单旋转
    private AvlNode<T> rotateWithRightChild(AvlNode<T> k2) {
        AvlNode<T> k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.height = Math.max(height(k2.right), height(k2.left)) + 1;
        k1.height = Math.max(height(k1.right), k2.height) + 1;
        return k1;
    }

    //左右双旋
    private AvlNode<T> doubleWithLeftChild(AvlNode<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    //右左双旋
    private AvlNode<T> doubleWithRightChild(AvlNode<T> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    private AvlNode<T> insert(T x, AvlNode<T> t) {
        if (t == null) {
            return new AvlNode<T>(x, null, null);
        }

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = insert(x, t.left);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right);
        } else
            ;//Duplicate;do nothing
        System.out.println("又调我：" + t.element + "；高度：" + t.height);
        return balance(t);
    }

    private AvlNode<T> findMin(AvlNode<T> t) {
        if (t == null) return null;
        else if (t.left == null) return t;
        return findMin(t);
    }

    private static final int ALLOWED_IMBALANCE = 1;

    private AvlNode<T> balance(AvlNode<T> t) {
        if (t == null) return t;

        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE)
            //如果孙子节点不在节点与儿子节点之间，右单旋
            if (height(t.left.left) >= height(t.left.right))
                t = rotateWithLeftChild(t);
            else
                //孙子节点在节点与儿子节点之间，左右双旋
                t = doubleWithLeftChild(t);
        else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE)
            //如果孙子节点不在节点与儿子节点之间，左单旋
            if (height(t.right.right) >= height(t.right.left))
                t = rotateWithRightChild(t);
            else
                //孙子节点在节点与儿子节点之间，右左双旋
                t = doubleWithRightChild(t);

        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    private AvlNode<T> remove(T x, AvlNode<T> t) {
        if (t == null) return t;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) t.left = remove(x, t.left);
        else if (compareResult > 0) t.right = remove(x, t.right);
        //用右子树的最小的数据代替该节点的数据并递归地删除那个节点
        else if (t.left != null && t.right != null) {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else
            //用其左节点或右节点来代替该取代节点
            t = (t.left != null) ? t.left : t.right;
        return balance(t);
    }
}
