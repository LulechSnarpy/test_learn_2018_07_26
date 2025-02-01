package club.iskyc.lulech.layout.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;


public class Tree<E> implements RandomAccess, Cloneable, java.io.Serializable
{
    private static final long serialVersionUID = 5683452581122892189L;
    /**
     * Default initial leaves number
     * */
    private static final int LEAVES_NUMBER = 2;

    private long limit;

    private TreeNode<E> head;

    private HashMap<E, TreeNode<E>> location = new HashMap<>();

    public Tree() {
        initLimit();
    }

    public Tree(long limit) {
        this.limit = limit;
    }

    public Tree(E data,long limit) {
        this.limit = limit;
        this.add(data);
    }

    public Tree(E data) {
        initLimit();
        this.add(data);
    }

    public Tree(E[] data, long limit) {
        this.limit = limit;
        this.addAll(data);
    }

    public Tree(E[] data) {
        initLimit();
        this.addAll(data);
    }

    public void initLimit() {
        limit = LEAVES_NUMBER;
    }

    public void addAll(E[] data, E p) {
        Arrays.stream(data).forEach(x -> this.add(x, p));
    }

    public void addAll(E[] data) {
        Arrays.stream(data).forEach(x -> this.add(x));
    }

    public void add(E data) {
        if (this.location.containsKey(data)) return;
        TreeNode<E> node = new TreeNode<>(data);
        this.location.put(data, node);
        if (null == head) { head = node; return; }
        TreeNode<E> parent = getDefaultParent();
        parent.addLeaf(node, limit);
    }

    public void add(E data,@NotNull E p) {
        if (this.location.containsKey(data)) return;
        TreeNode<E> parent = this.get(p);
        TreeNode<E> node = new TreeNode<>(data);
        this.location.put(data, node);
        if (null == parent.getLeaves()) parent.setLeaves(new ArrayList<>());
        if (!parent.addLeaf(node, limit)) {
            parent = getDefaultParent();
            parent.addLeaf(node, limit);
        }
    }

    @Override
    public String toString() {
        return "Tree{" +
                "limit=" + limit +
                ", head=" + head +
                '}' + System.lineSeparator()
                + toString(head, 0);
    }

    public static final int indent = 2;

    public String toString(TreeNode<E> node, int increment) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < increment; i++) {
            if (i > 0 && ((i & 1) == 0)) {
                buffer.append('|');
            } else {
                buffer.append("-");
            }
        }
        buffer.append('|').append(node).append(System.lineSeparator());
        if (null != node.getLeaves()) {
            for (TreeNode<E> leaf: node.getLeaves()) {
                buffer.append(toString(leaf, increment + indent));
            }
        }
        return buffer.toString();
    }

    public TreeNode<E> get(E data) {
        return this.location.get(data);
    }

    public E getHead() {
        return this.head.getData();
    }

    public TreeNode<E> getHeadNode() {
        return this.head;
    }

    public TreeNode<E> getDefaultParent() {
        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            TreeNode<E> node =  queue.poll();
            if (null == node.getLeaves())
                node.setLeaves(new ArrayList<>());
            if (node.getLeaves().size() < limit)
                return node;
            queue.addAll(node.getLeaves());
        }
        return null;
    }
}
