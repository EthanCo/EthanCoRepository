
public class Test {
	public static void main(String[] args){
		int[] arr = {55,62,32,2,99,136};
		
		int max = arr[0];
		
		for (int i = 1; i < arr.length; i++) {
			if(max<arr[i]){
				max = arr[i];
			}
		}
		
		System.out.println(max);
	}
}
