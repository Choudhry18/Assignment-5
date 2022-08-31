import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.LinkedList;
public class PointSET {
	private final SET<Point2D> tree;
	
	public PointSET(){
		tree = new SET<Point2D>();
	}
	
	public boolean isEmpty() {
		return tree.isEmpty();
	}
	
	public int size() {
		return tree.size();
	}
	
	public void insert(Point2D p) {
		if(p==null) throw new IllegalArgumentException();
		tree.add(p);
	}
	
	public boolean contains(Point2D p) {
		if(p==null) throw new IllegalArgumentException();
		return tree.contains(p);
	}
	
	public void draw() {
		for(Point2D i: tree) StdDraw.point(i.x(), i.y());
	}
	
	public Iterable<Point2D> range(RectHV rect){
		if(rect == null) throw new IllegalArgumentException();
		LinkedList<Point2D> in = new LinkedList<Point2D>();
		for(Point2D i: tree) {
			if(rect.contains(i)) in.add(i);
		}
		return in;
	}
	
	public Point2D nearest(Point2D p) {
		if(p==null) throw new IllegalArgumentException();
		if(isEmpty()) return null;
		TreeSet<Point2D> order = new TreeSet<Point2D>(p.distanceToOrder());
		for(Point2D i: tree) order.add(i);
		return order.first();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
