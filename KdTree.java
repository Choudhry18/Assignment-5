import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;
import java.util.LinkedList;

public class KdTree {
	private double distance;
	private Point2D neighbour;
	private int size;
	private final Node root;
	private class Node{
		Node left;
		Node right;
		Point2D key;
		boolean axis; //false means x-axis
		
		public Node(Point2D k) {
			key = k;
		}
	}
	public KdTree() {
		root = new Node(null);
		root.axis = false;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public int size() {
		return size;
	}
	
	public void insert(Point2D p) {
		if(p==null) throw new IllegalArgumentException();
		if(root.key==null) root.key = p;
		else {
			Node insert = new Node(p);
			Node current = root;
			boolean position = false;
			if(p.x()>root.key.x()) position = true;
			while((current.left!=null && !position)||(current.right!=null && position)) {
				if(p.equals(current.key)) return;
				if(position) current = current.right;
				else current = current.left;
				if(current.axis) {
					if(p.y()>current.key.y()) position = true;
					else position = false;
				}
				else {
					if(p.x()>current.key.x()) position = true;
					else position = false;
				}
			}
			if(p.equals(current.key)) return;
			if(position) {
				current.right = insert;
				insert.axis = !current.axis;
			}
			else{
				current.left = insert;
				insert.axis = !current.axis;
			}	
		}
		size++;
	}
	
	public boolean contains(Point2D p) {
		if(p==null) throw new IllegalArgumentException();
		if(isEmpty()) return false;
		Node current = root;
		boolean position = false;
		if(p.x()>current.key.x()) position = true;
		while((current.left!=null && !position)||(current.right!=null && position)) {
			if(p.equals(current.key)) return true;
			if(position) current = current.right;
			else current = current.left;
			if(current.axis) {
				if(p.y()>current.key.y()) position = true;
				else position = false;
			}
			else {
				if(p.x()>current.key.x()) position = true;
				else position = false;
			}
		}
		if(p.equals(current.key)) return true;
		return false;
	}
	
	public void draw() {
		toOrder(root);
	}
	
	private void toOrder(Node a) {
		if(a==null) return;
		StdDraw.setPenColor();
		StdDraw.point(a.key.x(), a.key.y());
		if(a.axis) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(a.key.x(), 0,a.key.x(), 1);
		}
		else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(0, a.key.y(), 1, a.key.y());
		}
		toOrder(a.left);
		toOrder(a.right);
		
	}
	public Iterable<Point2D> range(RectHV rect){
		if(rect==null) throw new IllegalArgumentException();
		LinkedList<Point2D>  in = new LinkedList<Point2D>();
		check(rect,root,in);
		return in;
	}
	
	private void check(RectHV rect, Node a,LinkedList<Point2D> in) {
		if(a==null) return;
		if(a.key==null) return;
		if(rect.contains(a.key)) {
			in.add(a.key);
			check(rect,a.left,in);
			check(rect,a.right,in);
		}
		else {
			if(a.axis) {
				if(a.key.y()<rect.ymax()&&a.key.y()>rect.ymin()) {
					check(rect,a.left,in);
					check(rect,a.right,in);
				}
				else if(a.key.y()>rect.ymax()) check(rect,a.left,in);
				else check(rect,a.right,in);
			}
			else {
				if(a.key.x()<rect.xmax()&&a.key.x()>rect.xmin()) {
					check(rect,a.left,in);
					check(rect,a.right,in);
				}
				else if(a.key.x()>rect.xmax()) check(rect,a.left,in);
				else check(rect,a.right,in);
			}
		}
	}
	
	public Point2D nearest(Point2D p) {
		if(p==null) throw new IllegalArgumentException();
		if(isEmpty()) return null;
		distance = p.distanceSquaredTo(root.key);
		search(p,root);
		return neighbour;
	}
	
	private void search(Point2D p, Node a) {
		if(a==null) return;
		if(p.distanceSquaredTo(a.key)<distance) {
			distance = p.distanceSquaredTo(a.key);
			neighbour = a.key;
		}
		search(p,a.left);
		search(p,a.right);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}


