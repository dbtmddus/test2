import java.util.Arrays;
import java.util.Scanner;

public class Solution2 {
	static int[] home;
	static int[] des;

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int total_cycle = in.nextInt();

		for (int cycle=0; cycle< total_cycle; cycle++){

			int n_home = in.nextInt();
			int n_des=in.nextInt();

			home = new int[n_home];
			for (int i=0; i< n_home; i++){
				home[i] = in.nextInt();
			}	
			Arrays.sort(home);

			int[] distance = new int[n_home-1];
			for (int i=0; i<n_home-1; i++){
				distance[i] = home[i+1]-home[i];
			}

			int[] distance_temp =distance.clone();
			Arrays.sort(distance);
			int[] sorted_distance = distance.clone();
			distance = distance_temp;


			int sum_of_longest=0;
			boolean[] b_trash = new boolean[n_home];
			for (int i=0; i<n_des; i++){
				sum_of_longest = sorted_distance[n_home-2-i];
				b_trash[Arrays.binarySearch(distance, sorted_distance[n_home-2-i])]=true;
			}

			System.out.println();
			System.out.println(Arrays.toString(home));
			System.out.println(Arrays.toString(distance));
			System.out.println(Arrays.toString(sorted_distance));

			int total_distance=0;
			for (int i=0; i<n_home; i++){

				if (b_trash[i]){
					total_distance += 0;
					System.out.println("+0 / "+total_distance);
					//System.out.println();
					
				}
				else{
					final int local_i = i;

					//left
					int left_v=0;
					boolean b_left_is_valid = false;
					while (i>=1){
						left_v += distance[i-1];
						//System.out.println("left : " + left_v);
						i--;
						if (b_trash[i]){
							b_left_is_valid = true;
							break;
						}
					}
					i=local_i;

					//right
					int right_v=0;
					boolean b_right_is_valid=false;
					while (i<n_home-1){
						right_v += distance[i];
						//System.out.println("right : "+ right_v);
						i++;
						if (b_trash[i]){
							b_right_is_valid = true;
							break;
						}
					}
					i=local_i;

					//더 짧은 값 선택
				//	System.out.println("left " + left_v +" / right : "+ right_v);
					int shorten_v=0;
					if (b_right_is_valid && b_left_is_valid){
						shorten_v = Math.min(right_v, left_v);
					//	System.out.println("shorten is "+ Math.min(right_v, left_v));
					}else if (b_right_is_valid && !b_left_is_valid){
						shorten_v = right_v;
					//	System.out.println("case 2 , "+ right_v);
					}else if (!b_right_is_valid && b_left_is_valid){
						shorten_v = left_v;
					//	System.out.println("case3 , "+ left_v);
					}else{
						System.out.println("쓰레기통 없음 ---------------------");
					}
					total_distance += shorten_v;
					System.out.println("+"+shorten_v +" / "+total_distance);
				//	System.out.println();
				}
			}

			System.out.println("총 거리 : " + total_distance );
			System.out.println("제외 거리  : " + sum_of_longest);
			System.out.println(total_distance - sum_of_longest);
		}
	}
}
