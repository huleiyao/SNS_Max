package com.bytegem.snsmax;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testTwoSum() {
        twoSum(new int[]{1, 2, 3, 4, 5, 6, 7, 81, 9}, 15);
    }

    public int[] twoSum(int[] nums, int target) {
        int indexArrayMax = 2047;
        System.out.println("ssss->"+Integer.toBinaryString(1));
        System.out.println("ssss->"+Integer.toBinaryString(14));
        System.out.println("ssss->"+Integer.toBinaryString(14 & 2047));
        int[] indexArrays = new int[indexArrayMax + 1];
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            int index = diff & indexArrayMax;
            if (indexArrays[index] != 0) {
                return new int[]{indexArrays[index] - 1, i};
            }
            indexArrays[nums[i] & indexArrayMax] = i + 1;
        }
        throw new IllegalArgumentException("No two sum value");
    }

//    public int[] twoSum2(int[] nums, int target) {
//        List<Integer> list = Arrays.asList("123", "123", "`123");
//        list.parallelStream().map(i -> {
//            list.parallelStream().map(j -> {
//
//
//            }).collect(Collectors.toList());
//        });
//
//    }
}