package huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Huffman {
	private Map<Character, String> codes = new TreeMap<>();
	private PriorityQueue<Node> nodes = new PriorityQueue<>((x,y)->(x.weight-y.weight));
	private int[] characters = new int[256];
	
	public void clearAll() {
		codes.clear();
		nodes.clear();
		characters = new int[256];
	}
	
	public void createCode(String value) {
		for (Character c : value.toCharArray()) {
			characters[c]++;
		}
		for (int i = 0; i <256; i++) {
			if (characters[i] > 0) {
				nodes.add(new Node(characters[i], (char) i));
			} 
		}
		Node p = null;
		while (nodes.size() > 1) {
			Node x = nodes.poll();
			System.out.println("'"+x.character+"' :"+x.weight);
			p = new Node();
			p.weight = x.weight;
			p.left = x;
			if (nodes.isEmpty()) break;
			x = nodes.poll();
			p.weight += x.weight;
			p.right = x;
			nodes.add(p);
			System.out.println("'"+x.character+"' :"+x.weight);
		}
		System.out.println("------------Nodes Start---------------");
		getChild(p, "");
		System.out.println("------------Nodes End-----------------");
		System.out.println("------------Codes Start---------------");
		codes.forEach((x, y)->System.out.println("'"+x + "' : " + y));
		System.out.println("------------Codes End-----------------");
	}
	
	public void getChild(Node p, String s) {
		if (null == p) return;
		getChild(p.left, s+"0");
		getChild(p.right, s+"1");
		System.out.println(s+"'"+p.character+"' : "+p.weight);
		if (p.character != null) {
			codes.put(p.character, s);
		}
	}
	
	public static void main(String[] args) {
		Huffman huffman = new Huffman();
		File fileIn = new File("./source/huffman/input.txt");
		File fileOut = new File("./source/huffman/output.txt");
		String value = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(fileIn));
				PrintWriter writer = new PrintWriter(new FileWriter(fileOut));){
			while ((value = reader.readLine()) != null) {
				huffman.createCode(value);
				huffman.clearAll();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}

class Node{
	Node left,right;
	int weight;
	Character character;
	
	public Node() {
		super();
	}

	public Node(int weight, Character character) {
		super();
		this.weight = weight;
		this.character = character;
	}



	public boolean isLeaf() {
		return left == null && right == null;
	}
}
