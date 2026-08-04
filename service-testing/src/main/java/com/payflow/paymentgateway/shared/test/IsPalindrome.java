package com.payflow.paymentgateway.shared.test;

import java.util.*;



public class IsPalindrome extends BaseC implements BaseI{


    public static void main(String[] args){
        (new IsPalindrome()).Method();
    }

    public static int mySqrt(int x) {

        Map<String,Object> maps = new TreeMap<>();

        long xLong = x;
        long divider = 0;
        long temp = 0;
        for(long i = 0; i<=xLong;i++){
            if( (i*i) <= xLong){
                temp = i;
            }

            if(temp>=divider){
                divider = temp;
            }
        }

        return (int) divider;

    }
}
