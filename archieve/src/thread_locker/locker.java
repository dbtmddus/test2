package thread_locker;

class thread_extends extends Thread{

	private int iden_n=0;
	private static int n=0;

	public thread_extends(int _iden_n){
		iden_n= _iden_n;
	}	

	@Override
	public void run(){
		f();
	}

	public void f(){	//�� ���, class ��ü�� ȥ�� �����ϹǷ� �̷� �ù���� ����õ �Ǵµ�(locker�� ����)
		synchronized(thread_extends.class)
		{
			for(int i=0; i<100; i++){
				System.out.print(iden_n+"aaaaaaaaaaaaaaaaaaaaaaaaaa");			
				System.out.println(iden_n+"b");
				System.out.println(i);	
			}
		}
	}
	//public synchronized void f(){} //�̷��� ���Ÿ� ��ü��ü�� ������ �����. 
}


public class locker {

	public static void main(String[] args) throws InterruptedException {
		thread_extends t_extends1 = new thread_extends(1);	//�� ��ü�� �̹� thread ������.
		thread_extends t_extends2 = new thread_extends(2);
		
		t_extends1.start();		
		t_extends2.start();		
	}
}