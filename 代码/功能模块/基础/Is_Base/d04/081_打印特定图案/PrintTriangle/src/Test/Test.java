package Test;

/*
 需求:打印一个倒立的直角三角形

 * * * * *
 * * * *
 * * *
 * *
 * 

 5行

 * */
public class Test {
	public static void main(String[] args) {
		for (int line = 5; line> 0; line--) {
			for (int row = line; row > 0; row--) {
				System.out.print(" *");
			}
			System.out.println("");
		}
	}
}
