import java.util.Arrays;


public class Test {
	public static void main(String[] args){
		int[] arr = {55,62,32,2,99,136};
		
		//���� ����
		Arrays.sort(arr);
		
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+" ");
		}
		
		//���ַ����� - ֻ���������������(����Ҫ��sort) ���û�������ظ���
		int index = Arrays.binarySearch(arr,99);
		System.out.println("�ҵ�����ֵ:"+index);
	}
}
