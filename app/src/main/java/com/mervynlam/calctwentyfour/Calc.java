package com.mervynlam.calctwentyfour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mervynlam on 2017/8/4.
 */

public class Calc {

	private static ArrayList<int[]> permutationList = new ArrayList<>();
	private static final float EPS = 1e-4f;

	/*
	* Calculate 24 point
	* @param num operand
	* @param result data source of adapter
	* @return
	*/
	public static void Calculate(int[] num, List<String> result) {
		if (result == null)
			result = new ArrayList<>();
		else
			result.clear();

		permutationList = Permutation.permute(num);
		Calc(permutationList, result);
	}

	/*
	* Compare the priority of two operator
	* @param
	* @return -1 <, 0 =, 1 >
	*/
	private static int cmpOp(int a, int b) {
		if ((a == 0 || a == 1) && (b == 2 || b == 3))
			return -1;
		if ((a == 2 || a == 3) && (b == 0 || b == 1))
			return 1;
		return 0;
	}

	/*
	* process of calculating
	* @param permutationList the full permutation of operand without repeat (like 122, 212, 211)
	* @return
	*/
	private static void Calc(ArrayList<int[]> permutationList, List<String> result) {
		char[] ops = new char[] {'+','-','*','/'};

		for (int[] num : permutationList) {
			int[] op = new int[3];
			for (op[0] = 0; op[0] < 4; op[0]++)
			for (op[1] = 0; op[1] < 4; op[1]++)
			for (op[2] = 0; op[2] < 4; op[2]++){
				float r = num[0];
				for (int i = 0; i < 3; ++i) {
					switch (ops[op[i]]) {
						case '+' :
							r = r + num[i+1];
							break;
						case '-' :
							r = r - num[i+1];
							break;
						case '*' :
							r = r * num[i+1];
							break;
						case '/' :
							r = r / num[i+1];
							break;
					}
				}
				if (Math.abs(r-24) < EPS) {
					String s;
					if (cmpOp(op[0], op[1]) == 0 && cmpOp(op[0], op[2]) == -1)
						s = "(" + formatNumIntoAlpha(num[0]) + formatOperator(ops[op[0]]) + formatNumIntoAlpha(num[1]) + formatOperator(ops[op[1]]) + formatNumIntoAlpha(num[2]) + ")" + formatOperator(ops[op[2]]) + formatNumIntoAlpha(num[3]);
					else if (cmpOp(op[0], op[1]) == -1)
						s = "(" + formatNumIntoAlpha(num[0]) + formatOperator(ops[op[0]]) + formatNumIntoAlpha(num[1]) + ")" + formatOperator(ops[op[1]]) + formatNumIntoAlpha(num[2]) + formatOperator(ops[op[2]]) + formatNumIntoAlpha(num[3]);
					else if (cmpOp(op[0], op[2]) == 0 && cmpOp(op[0], op[1]) == 1)
						s = "(" + formatNumIntoAlpha(num[0]) + formatOperator(ops[op[0]]) + formatNumIntoAlpha(num[1]) + formatOperator(ops[op[1]]) + formatNumIntoAlpha(num[2]) + ")" + formatOperator(ops[op[2]]) + formatNumIntoAlpha(num[3]);
					else
						s = "" + formatNumIntoAlpha(num[0]) + formatOperator(ops[op[0]]) + formatNumIntoAlpha(num[1]) + formatOperator(ops[op[1]]) + formatNumIntoAlpha(num[2]) + formatOperator(ops[op[2]]) + formatNumIntoAlpha(num[3]);
					result.add(s);
				}
			}
		}
	}

	/*
	* format output
	* @param
	* @return
	*/
	private static String formatOperator(char op) {
		switch (op) {
			case '*':
				return "×";
			case '/':
				return "÷";
			default:
				return Character.toString(op);
		}
	}

	private static String formatNumIntoAlpha(int num) {
		switch (num) {
			case 1:
				return "A";
			case 11:
				return "J";
			case 12:
				return "Q";
			case 13:
				return "K";
			default:
				return Integer.toString(num);
		}
	}
}
