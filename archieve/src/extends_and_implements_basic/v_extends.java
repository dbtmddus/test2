package extends_and_implements_basic;

class e_obj extends v_extends_class {
	
	public void f1(){
		System.out.println("extended f1");
		super.f1();
	}
	public void f2(){
		System.out.println("normal f2");	
	}
}


public class v_extends extends v_extends_class{
	public static void main(String[] args) {
		e_obj o1 = new e_obj();
		o1.f1();
		
		
	}
	
	@Override 
	public void f1(){
		System.out.println("extended f1");
	}
}


// extends �� ����ϱ� ����, �ش� �Լ�, class���� public������ static���� ��� ���ƾ���.