import java.util.Vector;

public class test {

	public static void main(String[] args) {
		
		/*Vector<String> v = new Vector<String>();
		v.addElement(5);
		System.out.println(v[1].elementAt(0));
		*/
		Vector<Vector> v = Vector<Vector>(0);
		
		v.addElement(new Vector<Integer>(0));
		v.addElement(new Vector<String>(0));
		
		v.elementAt(0).addElement(5);
		v.elementAt(1).addElement(5);
		System.out.println(v.elementAt(0).elementAt(0)+1);
		
//		System.out.println(str.substring(0, str.length()-1));
	}
}
