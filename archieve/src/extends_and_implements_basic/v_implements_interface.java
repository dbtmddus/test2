package extends_and_implements_basic;

interface v_implements_interface {
	
	int a =5;
	public abstract void f_abstract();	//abstract���� �Լ� body���� �Ұ���. �翬�� �Ʒ��� ��������.
	public void f_basic();	//abstract
	
	public static void f_static() {		//��� 2���� �Լ� body���� ����
		System.out.println("f_static_origin");
	}
	public default void f_default(){
		System.out.println("f_default origin");
	}
	
}