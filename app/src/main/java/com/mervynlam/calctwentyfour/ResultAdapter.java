package com.mervynlam.calctwentyfour;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mervynlam on 2017/8/4.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

	private Context mContext;
	private List<String> mResultList;

	static class ViewHolder extends RecyclerView.ViewHolder {

		CardView cardView;
		TextView resultText;

		public ViewHolder(View view) {
			super(view);
			cardView = (CardView) view;
			resultText = view.findViewById(R.id.result_text);
		}
	}

	public ResultAdapter(List<String> resultList) {
		this.mResultList = resultList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (mContext == null) {
			mContext = parent.getContext();
		}
		View view = LayoutInflater.from(mContext).inflate(R.layout.result_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		String result = mResultList.get(position);
		holder.resultText.setText(result);
	}

	@Override
	public int getItemCount() {
		return mResultList.size();
	}
}
