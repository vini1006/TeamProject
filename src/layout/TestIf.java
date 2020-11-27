package layout;

public class TestIf {
	
	
	public TestIf() {
		boolean a = true;
		
		while(true) {
			
			if(a) {
				//1번선택
				System.out.println("if문안에서 이걸 실행함");
				a = false;
			}else if(a == false) {
				//2번선택
				System.out.println("else if 문 안에서 이걸 실행함");
				a = true;
			}
		}
	}
	
	public static void main(String[] args) {
		new TestIf();
	}

}
