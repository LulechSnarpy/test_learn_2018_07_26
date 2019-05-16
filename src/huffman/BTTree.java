package huffman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class BTTree<T> {
	private final Integer DEFUALT_WIDTH = 2;
	private Integer width = DEFUALT_WIDTH;
	private Node<T> head = null;
	private PriorityQueue<Node<T>> nodes = new PriorityQueue<>((x,y)->(x.weight-y.weight));
	private HashMap<T, Integer> values = new HashMap<>();
	private Map<T, String> codes = new LinkedHashMap<>(); 
	private Map<String, T> keys = new LinkedHashMap<>();
	private String[] codeValue = {"0", "1"};
	public BTTree() {}

	public BTTree(String[] codeValue) {
		if (null == codeValue) return;
		int width = codeValue.length;
		if (width < 2) return;
		this.codeValue = codeValue;
		this.width = width;
	}
	
	public void addMap(List<T> list) {
		for (T t : list) {
			addMap(t);
		}
	}
	
	public void addMap (T t) {
		Integer value = values.get(t);
		value = null == value? 0 : value;
		value++;
		values.put(t, value);
	}
	
	public void createCode() {
		Node<T> child = null;
		int l = 0;
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
				child.setParent(head);
				head.setChild(i, child);
			}
			nodes.add(head);
		}
		createCode(head);
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
	
	public void createCode(Node<T> p) {
		if (null == p || p.isLeaf()) return;
		int psize = p.getSize();
		Node<T> cmax = null;
		Node<T> pmax = null;
		while(psize < width){
			cmax = findMax(p, 0);
			if (null == cmax) break;
			pmax = cmax.getParent();
			pmax.removeChild(cmax);
			p.setChild(psize, cmax);
			cmax.setParent(p);
			p.sort();
			psize = p.getSize();
		}
		for (Node<T> c : p.getChilds()) {
			createCode(c);
		}
	}
	
	public Node<T> findMax(Node<T> p, int step) {
		Node<T> node = null;
		Node<T> cmax = null;
		if (step == 2) return p;
		if (null == p || p.isLeaf()) return cmax;
		for (Node<T> c : p.getChilds()) {
			node = findMax(c, step + 1);
			if (null == node) continue;
			cmax = cmax == null || cmax.getWeight() < node.getWeight() ? node : cmax;
		}
		return cmax;
	}
	
	public String encode(T t) {
		return codes.get(t);
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
	
	public List<T> decode(StringBuffer code) {
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
			code.delete(0, i);
			i = 0;
		}
		return list;
	}
	
	public static void main(String[] args) {
	  BTTree<Character> btTree = new BTTree<>("1,2,3,4".split(",")); 
	  List<Character> v = new ArrayList<>();
	  for (Character c : "在那遥远的地方，有位老流氓，他带着那神秘的面罩，调戏所有美丽的姑娘。".toCharArray()) {
		  v.add(c);
	  }
	  System.out.println("---------Code Strat------------");
	  btTree.addMap(v);
	  btTree.createCode();
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
	
	public Map<T, String> getCodes() {
		return codes;
	}

	public void setCodes(Map<T, String> codes) {
		this.codes = codes;
	}

	public Map<String, T> getKeys() {
		return keys;
	}

	public void setKeys(Map<String, T> keys) {
		this.keys = keys;
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
		
		public void removeChild(Node<R> node) {
			int i;
			for ( i = 0; i < size; i++) {
				if (node.equals(childs[i])) {
					break;
				}
			}
			if (i < size-1) {
				System.arraycopy(childs, i+1, childs, i, size-i-1);
			} 
			childs[size-1] = null;
			size--;
			this.weight -= node.weight;
		}
		
		public void sort() {
			Arrays.sort(childs, (x, y)->(x.weight - y.weight));
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
