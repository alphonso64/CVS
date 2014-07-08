package com.example.cvs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SampleListFragment extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e("sliding ", "onCreateView");
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		
		adapter.add(new SampleItem("¿ÕÑ¹»ú 5001", android.R.drawable.ic_media_play));
		adapter.add(new SampleItem("¿ÕÑ¹»ú 5002", android.R.drawable.ic_media_play));
		
		setListAdapter(adapter);
		Log.e("sliding ", "onActivityCreated");
	}

	private class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.e("test", "hit: "+position);
		switch (position) {
		case 0:
			switchFragment(5001);
			break;
		case 1:
			switchFragment(5002);
		default:
			break;
		}
	}
	
	private void switchFragment(int cv_id) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof InfoCheck) {
			InfoCheck ra = (InfoCheck) getActivity();
			ra.switchContent(cv_id);
		}
	}
	
	
}
