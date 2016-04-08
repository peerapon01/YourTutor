package ninja.psuse.yourtutor.Async;

import android.os.AsyncTask;
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
public class CheckAlreadyHaveThisID extends AsyncTask<RegisterInfo, Void, Void> {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    protected Void doInBackground(RegisterInfo... arg0) {
        try {
            QueryBuilder qb = new QueryBuilder();
            RegisterInfo contact = arg0[0];
            String facebookId = contact.facebookID;
            Request request = new Request.Builder()
                    .url("https://api.mlab.com/api/1/databases/yourtutor/collections/users?q={\"facebookid\":\"" + facebookId + "\"}&c=true&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR")
                    .build();
            Log.v("urltest", "https://api.mlab.com/api/1/databases/yourtutor/collections/users?q={\"facebookid\":\"" + facebookId + "\"}&c=true&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR");
            Response response = client.newCall(request).execute();
            Log.v("response", response.body().string());
            int check = Integer.parseInt(response.body().string());
            Log.v("check", check + "eiei");
            if (check == 0) {
                Log.v("test", "eiei");
                String json = qb.createRegisterInfo(contact);
                RequestBody body = RequestBody.create(JSON, json);
                Request request2 = new Request.Builder()
                        .url("https://api.mlab.com/api/1/databases/yourtutor/collections/users?apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR")
                        .post(body)
                        .build();
                Response response2 = client.newCall(request2).execute();
            }

           /* if(response.body().string().equals(0)){
                Log.v("test","eiei");
                String json = qb.createRegisterInfo(contact);
                RequestBody body = RequestBody.create(JSON, json);
                Request request2 = new Request.Builder()
                        .url("https://api.mlab.com/api/1/databases/yourtutor/collections/users?apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR")
                        .post(body)
                        .build();
                Response response2 = client.newCall(request2).execute();

                if(response2.isSuccessful())
                {
                    Log.v("Successsend","body");
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else{
                return false;
            }*/
        } catch (Exception e) {
            String val = e.getMessage();
            String val2 = val;
            return null;
        }
        return null;
    }


}
