package com.mervynlam.calctwentyfour;

import java.util.ArrayList;

import static java.util.Arrays.sort;

/**
 * Created by Mervynlam on 2017/8/4.
 */

public class Permutation {

	private static ArrayList<int[]> permutationList = new ArrayList<int[]>();

	public static ArrayList<int[]> permute(int[] num){
		permutationList.clear();
		sort(num);
		perm(num, 0, num.length);
		return permutationList;
	}

	/**
	* if i in [a,b) exist num[i] == num[b], don't swap, for avoiding repeat
	* @param num the array that needs to be swapped
	* @param a floor of the range
	* @param num ceiling of the range
	* @return
	*/
	private static boolean isSwap(int[] num, int a, int b) {
		for (int i = a; i < b; ++i) {
			if (num[i] == num[b])
				return false;
		}
		return true;
	}

	private static void swap(int[] num, int a, int b) {
		int tmp = num[a];
		num[a] = num[b];
		num[b] = tmp;
	}

	private static void addToList(int[] num, int n) {
		int[] tmp = new int[n];
		for (int i = 0; i < n; ++i) {
			tmp[i] = num[i];
		}
		permutationList.add(tmp);
	}

	private static void perm(int[] num, int pos, int n) {
		addToList(num, n);
		for (int i = pos+1; i < n; ++i) {
			if (isSwap(num, pos, i)) {
				swap(num, pos, i);
				perm(num, pos+1, n);
				swap(num, pos, i);
			}
		}
	}
}
