package extends_and_implements_basic;

interface v_implements_interface {
	
	int a =5;
	public abstract void f_abstract();	//abstract형은 함수 body생성 불가능. 당연히 아래도 마찬가지.
	public void f_basic();	//abstract
	
	public static void f_static() {		//얘들 2개는 함수 body선언 가능
		System.out.println("f_static_origin");
	}
	public default void f_default(){
		System.out.println("f_default origin");
	}
	
}
