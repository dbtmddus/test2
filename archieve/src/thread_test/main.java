package thread_test;

public class main {

	public static void main(String[] args) {
		thread_extends t_extends1 = new thread_extends(1);
		t_extends1.start();
		
		thread_extends t_extends2 = new thread_extends(2);
		t_extends2.start();
		
		
		///
		
		thread_implements t_imp1emants1 = new thread_implements(11);
		thread_implements t_implements2 = new thread_implements(12);
		
		Thread thread1=new Thread(t_imp1emants1);
		thread1.start();
		
		Thread thread2=new Thread(t_implements2);
		thread2.start();
		
	}

}
