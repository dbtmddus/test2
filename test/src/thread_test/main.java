package thread_test;

public class main {

	public static void main(String[] args) {
		thread_extends t_extends1 = new thread_extends(1);
		t_extends1.f();
		//t_extends1.start();
		
		thread_extends t_extends2 = new thread_extends(2);
		t_extends2.f();
		//t_extends2.start();
		
		
		
	}

}