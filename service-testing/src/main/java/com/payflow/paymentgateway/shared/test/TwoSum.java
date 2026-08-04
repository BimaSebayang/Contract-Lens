package com.payflow.paymentgateway.shared.test;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    public static void main(String[] args){

        System.out.println(3/2);
       // System.out.println((int)Math.log10(12));
        String s = "SISIFA";

        System.out.println(pageCount(2,6));
//        int[] nums = new int[]{2, 11, 15,7};
//        int target = 9;
//
//        for (int i : twoSum(nums,target)) {
//            System.out.println(i);
//        }

    }

    public static int pageCount(int n, int p) {
        int starter = (n)/2;
        int finisher = (p)/2;


        int turnPageFromLastNumber = Math.abs(starter-finisher);
        int pageStarterCounter = Math.abs(0-starter);
        return Math.min(turnPageFromLastNumber, pageStarterCounter);

    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }

            map.put(nums[i], i);
        }

        return new int[] {};
    }

}
