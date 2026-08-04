package com.payflow.paymentgateway.shared.test;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {


    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        Result.miniMaxSum(arr);

        bufferedReader.close();
    }

    /*
     * Complete the 'miniMaxSum' function below.
     *
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static void miniMaxSum(List<Integer> arr) {



        long max = 0;
        long min = 0;

        long sumAll = 0;

        for (Integer ar : arr){
            sumAll += ar;
        }


        for(Integer ar : arr){

            long summation = sumAll - ar;


            if(summation>max){
                max = summation;
            }

            if(min == 0 || summation<min){
                min = summation;
            }
        }

        System.out.print(min + " " + max);
    }

}

