package thread_test;

public class obj extends Thread{

	public static int[] arr = new int[10];
	public int iden_n=0;
	public int a=0;
	
	public obj(){
		a=0;
	}
	public obj(int _iden_n){
		a=0;
		iden_n= _iden_n;
	}	
	public void run(){
		while(true){
			if (a<100000){
				try {
					Thread.sleep(1000);
					a++;
					System.out.println(iden_n +" - "+a);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public int get_a(){
		return a;
	}
}
