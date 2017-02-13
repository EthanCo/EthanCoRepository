import java.util.Arrays;


public class Test {
	public static void main(String[] args){
		int[] arr = {55,62,32,2,99,136};
		
		//排序 升序
		Arrays.sort(arr);
		
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+" ");
		}
		
		//二分法排序 - 只能用于有序的数组(所有要先sort) 如果没有怎返回负数
		int index = Arrays.binarySearch(arr,99);
		System.out.println("找到索引值:"+index);
	}
}
