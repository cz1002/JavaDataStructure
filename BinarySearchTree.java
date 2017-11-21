package DataStructure;

import java.nio.BufferUnderflowException;

/**
 * Created by Administrator on 2017/10/31 0031.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {
    //? super T：接收T类型或者T的父类型，T extends Comparable:接收Comparable类型或其子类型
    private static class BinaryNode<T> {
        T element;
        BinaryNode<T> left;
        BinaryNode<T> right;

        BinaryNode(T theElement) {
            this(theElement, null, null);
        }

        BinaryNode(T theElement, BinaryNode<T> lt, BinaryNode<T> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }
    }

    private BinaryNode<T> root;

    public BinarySearchTree() {
        root = null;
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(T x) {
        return contains(x, root);
    }

    public T findMin() {
        if (isEmpty()) throw new BufferUnderflowException();
        return findMin(root).element;
    }

    public T findMax() {
        if (isEmpty()) throw new BufferUnderflowException();
        return findMax(root).element;
    }

    public void insert(T x) {
        root = insert(x, root);
    }

    public void remove(T x) {
        root = remove(x, root);
    }

    public void printTree() {
        if(isEmpty())
            System.out.println("Empty tree");
        else printTree(root);
    }

    public boolean contains(T x, BinaryNode<T> t) {
        if (t == null) return false;
        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            return contains(x, t.left);
        } else if (compareResult > 0) {
            return contains(x, t.right);
        } else
            return true;
    }

    private BinaryNode<T> findMin(BinaryNode<T> t) {
        if (t == null) return null;
        else if (t.left == null) return t;
        return findMin(t);
    }

    private BinaryNode<T> findMax(BinaryNode<T> t) {
        if (t == null) return null;
        while (t.right != null) {
            t = t.right;
        }
        return t;
    }

    private BinaryNode<T> insert(T x, BinaryNode<T> t) {
        if (t == null) {
            return new BinaryNode<T>(x, null, null);
        }
        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) t.left = insert(x, t.left);
        else if (compareResult > 0) t.right = insert(x, t.right);
        return t;
    }

    private BinaryNode<T> remove(T x, BinaryNode<T> t) {
        if (t == null) return null;
        int compareResult = x.compareTo(t.element);

        //叶子节点的情况
        if(compareResult < 0)t.left = remove(x,t.left);
        else if(compareResult > 0)t.right = remove(x,t.right);
        //有两个儿子节点的情况，递归删除
        else if(t.left != null && t.right != null){
            t.element = findMin(t.right).element;
            t.right = remove(t.element,t.right);
        }
        //一个儿子的情况，绕过该节点被删除
        else t = (t.left != null)?t.left : t.right;
        return t;
    }

    private void printTree(BinaryNode<T> t) {
        //中序遍历
        if(t != null){
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

}
