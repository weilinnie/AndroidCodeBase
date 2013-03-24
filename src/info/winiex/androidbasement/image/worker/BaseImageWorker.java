package info.winiex.androidbasement.image.worker;

import android.os.AsyncTask;

public abstract class BaseImageWorker<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

	@Override
	protected Result doInBackground(Params... params) {
		return null;
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
	}

}
