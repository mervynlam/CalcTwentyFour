package com.mervynlam.calctwentyfour;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {

	private EditText operandEdit1;
	private EditText operandEdit2;
	private EditText operandEdit3;
	private EditText operandEdit4;
	private FloatingActionButton fab;

	private List<String> resultList = new ArrayList<>();
	private ResultAdapter adapter;

	private long firstTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		setListener();

		Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
		setSupportActionBar(toolbar);

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new ResultAdapter(resultList);
		recyclerView.setAdapter(adapter);
	}

	private void init() {
		operandEdit1 = (EditText) findViewById(R.id.operand1);
		operandEdit2 = (EditText) findViewById(R.id.operand2);
		operandEdit3 = (EditText) findViewById(R.id.operand3);
		operandEdit4 = (EditText) findViewById(R.id.operand4);
		fab = (FloatingActionButton) findViewById(R.id.fab);
	}

	private void setListener() {
		operandEdit1.setOnFocusChangeListener(this);
		operandEdit2.setOnFocusChangeListener(this);
		operandEdit3.setOnFocusChangeListener(this);
		operandEdit4.setOnFocusChangeListener(this);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				lostFocus();
				//判断edittext是否为空，空则清空结果且提示
				if (!checkNull()) {
					resultList.clear();
					adapter.notifyDataSetChanged();
					Toast.makeText(MainActivity.this, getResources().getString(R.string.please_fill_blank), Toast.LENGTH_SHORT).show();
				} else {
					int[] num = new int[4];
					num[0] = getNum(operandEdit1.getText().toString().trim());
					num[1] = getNum(operandEdit2.getText().toString().trim());
					num[2] = getNum(operandEdit3.getText().toString().trim());
					num[3] = getNum(operandEdit4.getText().toString().trim());
					//获取4个数后计算
					Calculator.Calculate(num, resultList);
					if (resultList.size() == 0) {
						resultList.add("No solution");
					}
					adapter.notifyDataSetChanged();
				}
				getFocus();
			}
		});
	}

	private void lostFocus() {
		operandEdit1.setFocusable(false);
		operandEdit2.setFocusable(false);
		operandEdit3.setFocusable(false);
		operandEdit4.setFocusable(false);
	}

	private void getFocus() {
		operandEdit1.setFocusableInTouchMode(false | true);
		operandEdit2.setFocusableInTouchMode(false | true);
		operandEdit3.setFocusableInTouchMode(false | true);
		operandEdit4.setFocusableInTouchMode(false | true);
	}

	private boolean checkNull() {
		String op1 = operandEdit1.getText().toString().trim();
		String op2 = operandEdit2.getText().toString().trim();
		String op3 = operandEdit3.getText().toString().trim();
		String op4 = operandEdit4.getText().toString().trim();
		if (op1.equals("") || op2.equals("") || op3.equals("") || op4.equals(""))
			return false;
		return true;
	}

	private void clearAll() {
		clearOne(operandEdit1);
		clearOne(operandEdit2);
		clearOne(operandEdit3);
		clearOne(operandEdit4);
	}

	private void clearOne(EditText et) {
		et.setText("");
	}

	private int getNum(String s) {
		if (s.equals("J"))
			return 11;
		else if (s.equals("Q"))
			return 12;
		else if (s.equals("K"))
			return 13;
		else if (s.equals("A"))
			return 1;
		else
			return Integer.parseInt(s);
	}

	/**
	* this listener use for adjusting the digital which lacks of standardization after losing focus
	* @param
	* @return
	*/
	public void onFocusChange(View view, boolean b) {
		EditText editText = (EditText) view;
		String nowText = editText.getText().toString().trim();
		if (b) {
		} else {
			if (nowText.equals("11") || nowText.equals("j"))
				editText.setText("J");
			else if (nowText.equals("12") || nowText.equals("q"))
				editText.setText("Q");
			else if (nowText.equals("13") || nowText.equals("k"))
				editText.setText("K");
			else if (nowText.equals("1") || nowText.equals("a"))
				editText.setText("A");
			else if (nowText.equals("J") || nowText.equals("Q") || nowText.equals("K") || nowText.equals("A") || nowText.equals("") ||
					nowText.equals("2") || nowText.equals("3") || nowText.equals("4") || nowText.equals("5") || nowText.equals("6") ||
					nowText.equals("7") || nowText.equals("8") || nowText.equals("9") || nowText.equals("10")) {
			} else {
				clearOne(editText);
				Toast.makeText(MainActivity.this, getResources().getString(R.string.illegal_num), Toast.LENGTH_SHORT).show();
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.toolbar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.about:
				(new AboutDialog()).show(getFragmentManager(), "aboutDialog");
				break;
			case R.id.clear:
				clearAll();
				resultList.clear();
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
		}
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				long secondTime = System.currentTimeMillis();
				if (secondTime - firstTime > 2000) {
					Toast.makeText(MainActivity.this, getResources().getString(R.string.back_to_finish), Toast.LENGTH_SHORT).show();
					firstTime = secondTime;
					return true;
				} else {
					this.finish();
				}
				break;
		}
		return super.onKeyUp(keyCode, event);
	}
}
