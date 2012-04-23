package me.shkschneider.dropbearserver.Tasks;

import me.shkschneider.dropbearserver.Utils.ServerUtils;
import me.shkschneider.dropbearserver.Utils.ShellUtils;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ServerStopper extends AsyncTask<Void, String, Boolean> {
	
	private static final String TAG = "ServerStopper";

	public Context mContext = null;
	public ProgressDialog mProgressDialog = null;

	private ServerStopperCallback<Boolean> mCallback;

	public ServerStopper(Context context, ServerStopperCallback<Boolean> callback) {
		mContext = context;
		mCallback = callback;
		if (mContext != null) {
			mProgressDialog = new ProgressDialog(mContext);
			mProgressDialog.setTitle("Stopping server");
			mProgressDialog.setMessage("Please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setMax(100);
			mProgressDialog.setIcon(0);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (mProgressDialog != null) {
			mProgressDialog.show();
		}
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		String pidFile = ServerUtils.getLocalDir(mContext) + "/pid";
		if (ShellUtils.echoToFile("0", pidFile) == false)
			Log.d(TAG, "echoToFile(0, " + pidFile + ")");

		// TODO: killall?
		Integer pid = ServerUtils.getServerPidFromPs();
		if (ShellUtils.kill(9, pid) == false) {
			Log.d(TAG, "kill(9, " + pid + ")");
			return false;
		}
		
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		mProgressDialog.dismiss();
		if (mCallback != null) {
			mCallback.onServerStopperComplete(result);
		}
	}
}