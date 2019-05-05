package huffman;

public class BTTree<T> {
	private final Integer DEFUALT_WIDTH = 2;
	private Integer width = DEFUALT_WIDTH;
	private Node<T> head = null;
	@SuppressWarnings("unchecked")
	private class Node<R> {
		private Node<R> parent;
		private Node<R>[] childs = null;
		private Integer weight;
		private R value;
		private int size = 0;
		public Node() {
			super();
			childs = new Node[width];
		}
		public Node(Integer weight) {
			this();
			this.weight = weight;
		}
		public Node(R value, Integer weight) {
			this();
			this.weight = weight;
			this.value = value;
		}
		public boolean isLeaf() {
			return childs == null || size == 0;
		}
		public Node<R> getParent() {
			return parent;
		}
		public void setParent(Node<R> parent) {
			this.parent = parent;
		}
		public int getWeight() {
			return weight;
		}
		public void setWeight(int weight) {
			this.weight = weight;
		}
		public R getValue() {
			return value;
		}
		public void setValue(R value) {
			this.value = value;
		}
		public Node<R>[] getChilds() {
			return childs;
		}
		public int getSize() {
			return size;
		}
		public void setChild(int index,Node<R> node) {
			if (index >= width || index < 0) {
				throw new IndexOutOfBoundsException("index: "+index+", width: "+width);
			}
			if (null == childs[index] && null != node) size++;
			if (null != childs[index] && null == node) size--;
			childs[index] = node;
		}
	}
	
	public void addNode() {
		
	}
	
	public static void main(String[] args) {
		BTTree<String> btTree = new BTTree<String>();
		BTTree<String>.Node<String> node  = btTree.new Node<>("KILLLAKILL", 19);
		node.setChild(1, node);
		System.out.println(node.getValue());
		System.out.println(node.getSize());
		node.setChild(0, node);
		System.out.println(node.getSize());
		node.setChild(0, null);
		System.out.println(node.getSize());
	}
}
