package ninja.psuse.yourtutor.Async;

import android.os.AsyncTask;
import android.util.Log;

import ninja.psuse.yourtutor.other.CourseInfo;
import ninja.psuse.yourtutor.other.RegisterInfo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by peerapon01 on 4/8/16 AD.
 */
public class ClassifyCourseAsyncTask extends AsyncTask<CourseInfo,Void,Boolean> {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    protected Boolean doInBackground(CourseInfo... params) {

        try
        {
            QueryBuilder qb = new QueryBuilder();
            CourseInfo course = params[0];
            String json = qb.createCourseInfo(course);


            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url("https://api.mlab.com/api/1/databases/yourtutor/collections/courses?apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            if(response.isSuccessful())
            {
                Log.v("Successsend","body");
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
