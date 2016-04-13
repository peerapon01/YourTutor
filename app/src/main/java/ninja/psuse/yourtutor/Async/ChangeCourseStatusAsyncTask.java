package ninja.psuse.yourtutor.Async;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import ninja.psuse.yourtutor.other.RegisterInfo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by peerapon01 on 4/13/16 AD.
 */
public class ChangeCourseStatusAsyncTask extends AsyncTask<ArrayList<String>,Void,Void> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    @Override
    protected Void doInBackground(ArrayList<String>... params) {

        try {
            ArrayList<String> things = params[0];
            QueryBuilder qb = new QueryBuilder();
            String json = qb.changeStatusInfo(things.get(0));
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url("https://api.mlab.com/api/1/databases/yourtutor/collections/courses/"+things.get(1)+"?apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR")
                    .put(body)
                    .build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            //e.getCause();
            String val = e.getMessage();
            String val2 = val;
            return null;
        }


    }
}
