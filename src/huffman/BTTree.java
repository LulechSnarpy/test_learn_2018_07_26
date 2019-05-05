package huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import com.google.common.cache.Weigher;

public class BTTree<T> {
	private final Integer DEFUALT_WIDTH = 2;
	private Integer width = DEFUALT_WIDTH;
	private Node<T> head = null;
	private PriorityQueue<Node<T>> nodes = new PriorityQueue<>((x,y)->(x.weight-y.weight));
	private HashMap<T, Integer> values = new HashMap<>();
	private TreeMap<T, String> codes = new TreeMap<>(); 
	private TreeMap<String, T> keys = new TreeMap<>();
	private String[] codeValue = {"0", "1"};
	public BTTree() {}

	public BTTree(Integer width) {
		this.width = null == width ? DEFUALT_WIDTH : width;
	}
	
	public void createCode(List<T> list) {
		Node<T> child = null;
		int l = 0;
		for (T t : list) {
			addMap(t);
		}
		for (Map.Entry<T, Integer> entry : values.entrySet()) {
			nodes.add(new Node<T>(entry.getKey(), entry.getValue()));
		}
		while(!nodes.isEmpty()) {
			if (nodes.size() == 1) {
				head = nodes.poll();
				break;
			}
			head = new Node<>();
			l = nodes.size();
			for (int i = 0; i<l && i < width; i++) {
				child = nodes.poll();
				head.setChild(i, child);
			}
			nodes.add(head);
		}
		createCode(head,"");
	}
	
	public void createCode(Node<T> p, String s) {
		int i = 0;
		if (null == p) return;
		for (Node<T> c : p.getChilds()) {
			createCode(c, s + codeValue[i]);
			i++;
		}
		if (p.isLeaf()) {
			codes.put(p.getValue(), s);
			keys.put(s, p.getValue());
			System.out.println("Value : " +p.getValue() +" ,Code : " +s);
		}
	}
	
	public String encode(List<T> list) {
		StringBuffer buffer = new StringBuffer();
		for (T t : list) {
			buffer.append(codes.get(t));
		}
		return buffer.toString();
	}
	
	public List<T> decode(String code) {
		List<T> list = new ArrayList<>();
		String s = null;
		T t = null;
		int i = 0;
		while(code.length() > 0 && i < code.length()) {
			i++;
			s = code.substring(0, i);
			t = keys.get(s);
			if (t == null) continue;
			list.add(t);
			code = code.substring(i);
			i = 0;
		}
		return list;
	}
	
	public static void main(String[] args) {
	  BTTree<Character> btTree = new BTTree<>(); 
	  List<Character> v = new ArrayList<>();
	  for (Character c : "Hello World!".toCharArray()) {
		  v.add(c);
	  }
	  System.out.println("---------Code Strat------------");
	  btTree.createCode(v);
	  System.out.println("---------Code End--------------");
	  System.out.println("---------Encode Strat--------------");
	  String s = btTree.encode(v);
	  System.out.println(s);
	  System.out.println("---------Encode End----------------");
	  System.out.println("---------Decode Strat--------------");
	  List<Character> k = btTree.decode(s);
	  k.forEach(System.out::print);
	  System.out.println();
	  System.out.println("---------Decode End----------------");
	  
	}	
	private void addMap (T t) {
		Integer value = values.get(t);
		value = null == value? 0 : value;
		value++;
		values.put(t, value);
	}
	
	@SuppressWarnings("unchecked")
	private class Node<R> {
		private Node<R> parent;
		private Node<R>[] childs = null;
		private Integer weight = 0;
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
		public R getValue() {
			return value;
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
			this.weight += node.weight;
		}
	}
}
