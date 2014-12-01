package com.gzfgeh.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PhoneBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String number = getResultData();
		number = "12593" + number;
		setResultData(number);
	}

}
