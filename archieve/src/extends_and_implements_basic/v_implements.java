package extends_and_implements_basic;

import java.util.Arrays;

interface�� super�� �޾ƿü� �ִ��� Ȯ���Ұ�, ���txt�����Ұ�.
class implemented_obj implements v_implements_interface{

	public implemented_obj(){
		super();
	}
	@Override
	public void f_abstract() {
		System.out.println("f_abstract_implemented");
		//v_implements_interface.f_static();
	}

	@Override
	public void f_basic() {
		System.out.println("f_basic_implemented");		
	}

	@Override
	public void f_default() {
		//super.		//implements�� �ƿ� super�� �ȵǴµ�
		System.out.println("f_default_implemented");		
	}

	/*@Override	//��� ���� �Ұ���
	public void f_static() {
		System.out.println("f_default_implemented");		
	}*/

}
public class v_implements{
	public static void main(String[] args) {
		implemented_obj o1 =new implemented_obj();
		o1.f_basic();
		o1.f_abstract();
		o1.f_default();
		o1.f_default();
		
	}//end main	
}