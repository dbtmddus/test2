package thread_test;

public class main {

	public static void main(String[] args) {
		obj o1 = new obj(1);
		o1.start();
		
		obj o2 = new obj(2);
		o2.start();
		
		obj o3 = new obj(3);
		o3.start();
		
	}

}
