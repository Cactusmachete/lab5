import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int num_trees = scan.nextInt();
		int num_students = scan.nextInt();
		
		BSTFilesBuilder builder = new BSTFilesBuilder();
		builder.createBSTFiles(num_students, num_trees);
		
		ArrayList<String> given_ans = new ArrayList<String>(num_trees);
		ArrayList<Object> list = new ArrayList<Object>(num_trees);
		ArrayList<Integer> given_stud = new ArrayList<Integer>();
		
		for(int i=1; i<num_trees+1; i++){
			try {
				String str ="./src/"+ String.valueOf(i) + ".txt";
				List<String> lines = Files.readAllLines(Paths.get(str));
				if (lines.get(0).equals("String")){
					BST<String> b = new BST<String>();
					int a = Integer.valueOf(lines.get(1));
					String[] words=lines.get(2).split("\\s");
					for (int j=0; j<a; j++){
						b.Insert(words[j]);
					}
					list.add(b);
				}
				
				else if (lines.get(0).equals("Integer")){
					BST<Integer> b = new BST<Integer>();
					int a = Integer.valueOf(lines.get(1));
					String[] words=lines.get(2).split("\\s");
					for (int j=0; j<a; j++){
						b.Insert(Integer.valueOf(words[j]));
					}
					list.add(b);
				}
				
				else{
					BST<Float> b = new BST<Float>();
					int a = Integer.valueOf(lines.get(1));
					String[] words=lines.get(2).split("\\s");
					for (int j=0; j<a; j++){
						b.Insert(Float.valueOf(words[j]));
					}
					list.add(b);
				}

				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		for (int i=0; i<num_trees; i++){
			BST<?> n =(BST<?>) list.get(i);
			if (n.getType().equals("String")){
				BST<String> a  = (BST<String>) n;
				a.newList();
				a.inOrder(a.getRoot());
				String s = a.getPos()+" "+a.getVal();
				given_ans.add(s);
			}
			else if(n.getType().equals("Integer")){
				BST<Integer> a = (BST<Integer>) n;
				a.newList();
				a.inOrder(a.getRoot());
				String s = a.getPos()+" "+a.getVal();
				given_ans.add(s);
			}
			
			else{
				BST<Float> a = (BST<Float>) n;
				a.newList();
				a.inOrder(a.getRoot());
				String s = a.getPos()+" "+a.getVal();
				given_ans.add(s);
			}
			
			
		}
		int i=0;
		while(i<given_ans.size()-1){
			String[] words=given_ans.get(i).split("\\s");
			int a= Integer.valueOf(words[0]);
			int j=i+1;
			while(j<given_ans.size()){
				words=given_ans.get(j).split("\\s");
				int b= Integer.valueOf(words[0]);
				if (a==b){
					words = given_ans.get(j).split("\\s");
					given_ans.set(i, given_ans.get(i)+" "+words[1]);
					given_ans.remove(j);
				}
				else{
					j++;
				}
				
			}
			i++;
		}
		
		for(i=0; i<given_ans.size()-1; i++){
			String curr = given_ans.get(i);
			String[] words=given_ans.get(i).split("\\s");
			int a= Integer.valueOf(words[0]);
			int min=i;
			for(int j=i+1; j<given_ans.size(); j++){
				words=given_ans.get(j).split("\\s");
				int b= Integer.valueOf(words[0]);
				if(a>b){
					a=b;
					min=j;
				}
			}
			given_ans.set(i, given_ans.get(min));
			given_ans.set(min, curr);
		}
		
		try{
		    PrintWriter writer = new PrintWriter("./src/output.txt", "UTF-8");
		    for(i=0; i<given_ans.size(); i++){
		    	 writer.println(given_ans.get(i));
		    	 String[] words = given_ans.get(i).split("\\s");
		    	 if (given_stud.contains(Integer.valueOf(words[0]))){}
					else{
						given_stud.add(Integer.valueOf(words[0]));
						
					}
		    }
		   
		    writer.println(num_students - given_stud.size());
		    writer.close();
		} catch (IOException e) {
		  
		}
				
		
		
		
	}

}




class BST<T>{
	
	private Node<T> root;
	private int size=0;
	private ArrayList<T> list;
	private T val;
	
	public void Insert(T val){
		Node<T> n= new Node<T>(val);
		if (size==0){
			this.root = n;
		}
		
		else{
			add(this.root, n);
		}
		size++;
	}
	
	public void add(Node<T> root, Node<T> n){
		if(n.compareTo(root)<1){
			if(root.getLeft()==null) root.setLeft(n);
			else add(root.getLeft(), n);
			
		}
		else{
			if (root.getRight()==null) root.setRight(n);
			else add(root.getRight(), n);
			
		}
	}
	
	public void inOrder(Node<T> root){
		if(root.getLeft()!=null)inOrder(root.getLeft());
		
		this.list.add(root.getData());
		if (root.getRight()!=null)inOrder(root.getRight());
	}
	
	public void newList(){
		ArrayList<T> ls = new ArrayList<T>(this.size);
		this.list = ls;
	}
	
	public Node<T> getRoot(){
		return this.root;
	}
	
	public ArrayList<T> getList(){
		return this.list;
	}
	
	public T getVal(){
		if (root.getData() instanceof String){
			String str = "";
			for (int i=0; i<this.size; i++){
				str = str+(String) this.list.get(i);
			}
			this.val = (T) str;
		}
		
		else if(root.getData() instanceof Integer){
			Integer sum= new Integer(0);
			for(int i=0; i<this.size; i++){
				sum = sum + (Integer) this.list.get(i);
			}
			this.val = (T) sum;
		}
		
		else{
			Float sum= new Float(0);
			for(int i=0; i<this.size; i++){
				sum = sum + (Float) this.list.get(i);
			}
			
			this.val = (T) sum;
		}
		
		return this.val;
		
	}

	public int getPos(){
		int s=0;
		if(this.root.getData() instanceof String){
			for(int i=this.size-1; i>-1; i--){
				if(this.list.get(i).equals(this.root.getData())){
					s=i;
					break;
				}
			}
		}
		else{
			for(int i=this.size-1; i>-1; i--){
				if(this.list.get(i)==this.root.getData()){
					s=i;
					break;
				}
			}
		}
		return s+1;	
	}
	
	
	public String getType(){
		if(this.root.getData() instanceof String) return "String";
		else if (this.root.getData() instanceof Integer) return "Integer";
		else return "Float";
	}
}



class Node<T> implements Comparable<Node<T>>{
	private Node<T> left, right;
	private T data;
	
	public Node(T d){
		this.data = d;
		this.left = null;
		this.right = null;
	}
	
	public Node<T> getLeft(){
		return this.left;
	}
	
	public Node<T> getRight(){
		return this.right;
	}
	
	public T getData(){
		return this.data;
	}
	
	public void setLeft(Node<T> n){
		this.left = n;
	}
	
	public void setRight(Node<T> n){
		this.right = n;
	}

	@Override
	public int compareTo(Node<T> o) {
		if(this.data instanceof String){
			String a = (String) this.data;
			String b = (String) o.getData();
			return a.compareTo(b);
			
		}
		
		else if (this.data instanceof Integer){
			Integer a = (Integer) this.data;
			Integer b = (Integer) o.getData();
			if (a<b) return -1;
			else if(a>b) return 1;
			else return 0;
			
		}
		
		else{
			Float a = (Float) this.data;
			Float b = (Float) o.getData();
			if (a<b) return -1;
			else if (a>b) return 1;
			else return 0;
		}
		
	}
}