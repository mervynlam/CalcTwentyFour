package com.mervynlam.calctwentyfour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mervynlam on 2017/8/4.
 */

public class Calculator {

	private static ArrayList<int[]> permutationList = new ArrayList<>();
	private static final float EPS = 1e-4f;

	/**
	* Calculate 24 point
	* @param num list of operands
	* @param result data source of adapter
	* @return
	*/
	public static void Calculate(int[] num, List<String> result) {
		if (result == null)
			result = new ArrayList<>();
		else
			result.clear();

		permutationList = Permutation.permute(num);
		Calculate(permutationList, result);
	}

	/**
	* Compare the priority of two operator
	* @param a one of the operator that needs to be compared
	* @param b another operator that needs to be compared
	* @return -1 <, 0 =, 1 >
	*/
	private static int cmpOp(int a, int b) {
		if ((a == 0 || a == 1) && (b == 2 || b == 3))
			return -1;
		if ((a == 2 || a == 3) && (b == 0 || b == 1))
			return 1;
		return 0;
	}

	/*private static void Calc(ArrayList<int[]> permutationList, List<String> result) {
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
	}	*/

	/**
	* calculating
	* @param permutationList the full permutation of operand without repeat (like 122, 212, 211)
	* @param result the list of result
	* @return
	*/
	private static void Calculate(ArrayList<int[]> permutationList, List<String> result) {
		char[] ops = new char[] {'+','-','*','/'};

		for (int[] num : permutationList) {
			int[] op = new int[3];
			float r1;
			float r2;
			String s;
			for (op[0] = 0; op[0] < 4; op[0]++)
			for (op[1] = 0; op[1] < 4; op[1]++)
			for (op[2] = 0; op[2] < 4; op[2]++){
				//((a X b) X c) X d		123
				r1 = num[0];
				r1 = Calc(ops[op[0]], r1, num[1]);
				r1 = Calc(ops[op[1]], r1, num[2]);
				r1 = Calc(ops[op[2]], r1, num[3]);
				if (Math.abs(r1 - 24) < EPS) {
					s = "((" + formatOutput(num[0]) + formatOutput(ops[op[0]]) + formatOutput(num[1]) + ")" + formatOutput(ops[op[1]]) + formatOutput(num[2]) + ")" + formatOutput(ops[op[2]]) + formatOutput(num[3]);
					result.add(s);
				}
				//(a X b) X (c X d)		132
				r1 = num[0];
				r2 = num[2];
				r1 = Calc(ops[op[0]], r1, num[1]);
				r2 = Calc(ops[op[2]], r2, num[3]);
				r1 = Calc(ops[op[1]], r1, r2);
				if (Math.abs(r1 - 24) < EPS) {
					s = "(" + formatOutput(num[0]) + formatOutput(ops[op[0]]) + formatOutput(num[1]) + ")" + formatOutput(ops[op[1]]) + "(" + formatOutput(num[2]) + formatOutput(ops[op[2]]) + formatOutput(num[3]) + ")";
					result.add(s);
				}
				//(a X (b X c)) X d		213
				r1 = num[1];
				r1 = Calc(ops[op[1]], r1, num[2]);
				r1 = Calc(ops[op[0]], num[0], r1);
				r1 = Calc(ops[op[2]], r1, num[3]);
				if (Math.abs(r1 - 24) < EPS) {
					s = "(" + formatOutput(num[0]) + formatOutput(ops[op[0]]) + "(" + formatOutput(num[1]) + formatOutput(ops[op[1]]) + formatOutput(num[2]) + "))" + formatOutput(ops[op[2]]) + formatOutput(num[3]);
					result.add(s);
				}
				//a X ((b X c) X d)		312
				r1 = num[1];
				r1 = Calc(ops[op[1]], r1, num[2]);
				r1 = Calc(ops[op[2]], r1, num[3]);
				r1 = Calc(ops[op[0]], num[0], r1);
				if (Math.abs(r1 - 24) < EPS) {
					s = formatOutput(num[0]) + formatOutput(ops[op[0]]) + "((" + formatOutput(num[1]) + formatOutput(ops[op[1]]) + formatOutput(num[2]) + ")" + formatOutput(ops[op[2]]) + formatOutput(num[3]) + ")";
					result.add(s);
				}
				//a X (b X (c X d))		321
				r1 = num[2];
				r1 = Calc(ops[op[2]], r1, num[3]);
				r1 = Calc(ops[op[1]], num[1], r1);
				r1 = Calc(ops[op[0]], num[0], r1);
				if (Math.abs(r1 - 24) < EPS) {
					s = formatOutput(num[0]) + formatOutput(ops[op[0]]) + "(" + formatOutput(num[1]) + formatOutput(ops[op[1]]) + "(" + formatOutput(num[2]) + formatOutput(ops[op[2]]) + formatOutput(num[3]) + "))";
					result.add(s);
				}
			}
		}
	}

	private static float Calc(char op, float r, float num) {
		switch (op) {
			case '+' :
				return r + num;
			case '-' :
				return r - num;
			case '*' :
				return r * num;
			case '/' :
				return r / num;
			default:
				return 0;
		}
	}

	/**
	* format output
	* @param op operator needs to be formatted
	* @return Corresponding symbol
	*/
	private static String formatOutput(char op) {
		switch (op) {
			case '*':
				return "×";
			case '/':
				return "÷";
			default:
				return Character.toString(op);
		}
	}

	/**
	 * format output
	 * @param num operand needs to be formatted
	 * @return Corresponding alphabet
	 */
	private static String formatOutput(int num) {
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
