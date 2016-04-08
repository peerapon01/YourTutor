package ninja.psuse.yourtutor.Async;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import ninja.psuse.yourtutor.other.RegisterInfo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by peerapon01 on 4/7/16 AD.
 */
public class RegisterAsyncTask extends AsyncTask<RegisterInfo, Void, Boolean> {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    protected Boolean doInBackground(RegisterInfo... arg0) {

        try {
            QueryBuilder qb = new QueryBuilder();
            RegisterInfo contact = arg0[0];
            String json = qb.createRegisterInfo(contact);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url("https://api.mlab.com/api/1/databases/yourtutor/collections/users?apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                Log.v("Successsend", "body");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            //e.getCause();
            String val = e.getMessage();
            String val2 = val;
            return false;
        }
    }

}

