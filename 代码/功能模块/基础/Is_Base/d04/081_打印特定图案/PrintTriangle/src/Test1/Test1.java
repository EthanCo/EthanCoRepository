package Test1;

/*
 * 需求:打印一个九九乘法表
 * */

public class Test1 {
	public static void main(String[] args){
		for(int i=1;i<=9;i++){
			for(int j=1;j<=i;j++){
				System.out.print(" "+j*i);
			}
			System.out.println("");
		}
	}
}
