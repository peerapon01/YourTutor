package ninja.psuse.yourtutor.Async;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import ninja.psuse.yourtutor.R;
import ninja.psuse.yourtutor.ResultCourseFragment;
import ninja.psuse.yourtutor.other.CourseInfo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by peerapon01 on 4/8/16 AD.
 */
public class ResultedCourseAsyncTask extends AsyncTask<CourseInfo,Void,Void> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override

    protected Void doInBackground(CourseInfo... params) {
        try {
            CourseInfo courseInfo = params[0];
            String url="https://api.mlab.com/api/1/databases/yourtutor/collections/courses?apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            //nnn
            if(courseInfo.level.equals("ไม่ระบุ")&&courseInfo.category.equals("ไม่ระบุ")&&courseInfo.priceperHr==null){
                url="https://api.mlab.com/api/1/databases/yourtutor/collections/courses?apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            }
            ///nny
            else if(!courseInfo.level.equals("ไม่ระบุ")&&courseInfo.category.equals("ไม่ระบุ")&&courseInfo.priceperHr==null){
                url="https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={\"priceperHr\":{$gt:\""+courseInfo.priceperHr+"\"}}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";

            }
            //nyn
            else if(courseInfo.level.equals("ไม่ระบุ")&&!courseInfo.category.equals("ไม่ระบุ")&&courseInfo.priceperHr==null){
                url="https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={\"level\":\""+courseInfo.level+"\"}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            }
            //nyy
            else if(courseInfo.level.equals("ไม่ระบุ")&&!courseInfo.category.equals("ไม่ระบุ")&&courseInfo.priceperHr!=null){
                url="https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={$and:[{\"category\":\""+courseInfo.category+"\"},{\"priceperHr\":{$gte:"+courseInfo.priceperHr+"\"}]}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";

            }
            //https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={$and:[{"level":"ม3"},{"category":"คณิตศาสตร์"}]}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR
            //yny
            else if(!courseInfo.level.equals("ไม่ระบุ")&&courseInfo.category.equals("ไม่ระบุ")&&courseInfo.priceperHr!=null){
                url="https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={$and:[{\"level\":\""+courseInfo.level+"\"},{\"priceperHr\":{$gte:"+courseInfo.priceperHr+"\"}]}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            }
            //yyn
            else if(!courseInfo.level.equals("ไม่ระบุ")&&!courseInfo.category.equals("ไม่ระบุ")&&courseInfo.priceperHr==null){
                url="https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={$and:[{\"level\":\""+courseInfo.level+"\"},{\"category\""+courseInfo.category+"\"}]}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            }
            //yyy
            else if(!courseInfo.level.equals("ไม่ระบุ")&&!courseInfo.category.equals("ไม่ระบุ")&&courseInfo.priceperHr!=null){
                url="https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={$and:[{\"level\":\""+courseInfo.level+"\"},{\"priceperHr\":{$gte:"+courseInfo.priceperHr+"\"}{\"category\":\"\""+courseInfo.category+"}]}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            }


            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Log.v("urltestcourse",url);
            Response response = client.newCall(request).execute();
            return null;
        }
        catch (IOException e) {
            Log.e("ErrorIO", e.toString());
        }
        return null;
    }

    @Override

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}
