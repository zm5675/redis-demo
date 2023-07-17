package easy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TowCountSum {
    public static void main(String[] args) {

        int[] ints = twoSum(new int[]{2, 7, 11, 15}, 9);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    public static int[] twoSum(int[] nums, int target) {
        // 创建数组
        int[] res = new int[2];
        // 判断是否为null
        if(nums == null || nums.length == 0){
            return res;
        }
        // 创建map
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            // 创建零时变量temp，判断这个值是否在map中
            int temp = target - nums[i];
            // 如果在map中的话
            if(map.containsKey(temp)){
                res[1] = i;
                res[0] = map.get(temp);
            }
            map.put(nums[i], i);
        }
        return res;
    }

}
