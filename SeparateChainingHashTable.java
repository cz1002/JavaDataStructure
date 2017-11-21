package DataStructure;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/9 0009.
 */
public class SeparateChainingHashTable<T> {
    private static final int DEAFAULT_TABLE_SIZE = 101;

    private List<T>[] theLists;
    private int currentSize;

    public SeparateChainingHashTable(){
        this(DEAFAULT_TABLE_SIZE);
    }

    public SeparateChainingHashTable(int size){
        theLists = new LinkedList[nextPrime(size)];
        for(int i = 0;i < theLists.length;i++)theLists[i] = new LinkedList<T>();
    }

    public void insert(T x){
        List<T> whichList = theLists[myhash(x)];
        if(!whichList.contains(x)){
            whichList.add(x);
            if(++currentSize > theLists.length)rehash();
        }
    }

    public void remove(T x){
        List<T> whichList = theLists[myhash(x)];
        if(whichList.contains(x)){
            whichList.remove(x);
            currentSize--;
        }
    }

    public boolean contains(T x){
        List<T> whichList = theLists[myhash(x)];
        return whichList.contains(x);
    }

    public void makeEmpty(){
        for(int i = 0;i < theLists.length ; i++)theLists[i].clear();
        currentSize = 0;
    }

    private void rehash(){
        List<T>[] oldLists = theLists;

        //Create new double-sized,empty table
        theLists = new List[nextPrime(2*theLists.length)];
        for (int j = 0;j < theLists.length; j++)
            theLists[j] = new LinkedList<T>();

            //Copy table over
            currentSize = 0;
            for (int i = 0;i < oldLists.length;i++){
                for (T item : oldLists[i])insert(item);
            }
    }

    private int myhash(T x){
        int hashVal = x.hashCode();

        hashVal %= theLists.length;
        if(hashVal < 0)hashVal += theLists.length;

        return hashVal;
    }

    /**
     * 返回不小于某个整数的素数*/
    private static int nextPrime(int n){
        if (n == 0 || n == 1 || n == 2) {
            return 2;
        }
        if (n % 2 == 0) {
            n++;
        }
        while (!isPrime(n)) {
            n += 2;
        }
        return n;
    }

    /**
     * 检查某整数是否为素数*/
    private static boolean isPrime(int n){
        if (n == 2 || n == 3) {
            return true;
        }
        if (n == 1 || n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
