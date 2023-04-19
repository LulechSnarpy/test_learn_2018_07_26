package club.iskyc.lulech.layout.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TreeNode<E> implements Comparable<TreeNode<E>>, java.io.Serializable
{
    private E data;

    private TreeNode<E> parent;

    private List<TreeNode<E>> leaves = new ArrayList<>();

    public TreeNode() { super(); }

    public TreeNode(E data) {
        this.data = data;
    }

    public TreeNode(E data, TreeNode<E> parent) {
        this.data = data;
        this.parent = parent;
    }

    public TreeNode(E data, TreeNode<E> parent, List<TreeNode<E>> leaves) {
        this.data = data;
        this.parent = parent;
        this.leaves = leaves;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public TreeNode<E> getParent() {
        return parent;
    }

    public void setParent(TreeNode<E> parent) {
        this.parent = parent;
    }

    public List<TreeNode<E>> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<TreeNode<E>> leaves) {
        this.leaves = leaves;
    }

    public boolean addLeaf(TreeNode<E> leaf, long limit) {
        if (limit == this.getLeaves().size()) return false;
        leaf.setParent(this);
        this.getLeaves().add(leaf);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode<E> treeNode = (TreeNode<E>) o;
        return data.equals(treeNode.data) &&
                Objects.equals(parent, treeNode.parent) &&
                Objects.equals(leaves, treeNode.leaves);
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "data=" + data +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public int compareTo(TreeNode<E> o) {
        if (null == o) return 1;
        if (null == this.data && null == o.data) return 0;
        if (null == this.data) return -1;
        if (this.data.getClass().isInstance(Comparable.class)) {
            Comparable<E> v1 = (Comparable<E>) this.data;
            E v2 = (E) o.data;
            return v1.compareTo(v2);
        }
        return this.data.toString().compareTo(o.data.toString());
    }

    private static final long serialVersionUID = -5683452581122892189L;

}
