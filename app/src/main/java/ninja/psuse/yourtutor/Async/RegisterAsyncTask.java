package ninja.psuse.yourtutor.Async;

import android.os.AsyncTask;

import ninja.psuse.yourtutor.other.RegisterInfo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by peerapon01 on 4/7/16 AD.
 */
public class RegisterAsyncTask extends AsyncTask <RegisterInfo,Void,Boolean> {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    protected Boolean doInBackground(RegisterInfo... arg0) {

        try
        {
            MyContact contact = arg0[0];

            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            if(response.)
            {
                return true;
            }
            else
            {
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
}
