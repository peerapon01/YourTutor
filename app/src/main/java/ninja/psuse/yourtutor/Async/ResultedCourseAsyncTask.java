package ninja.psuse.yourtutor.Async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class ResultedCourseAsyncTask extends AsyncTask<CourseInfo, Void, Void> {
    String category;
    String author;
    String subject;
    String level;
    String location;
    String school;
    String priceperHr;
    String description;
    String status;
    String courseID;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override

    protected Void doInBackground(CourseInfo... params) {
        try {
            CourseInfo courseInfo = params[0];
            String url = "https://api.mlab.com/api/1/databases/yourtutor/collections/courses?apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            //nnn
            if (courseInfo.level.equals("ไม่ระบุ") && courseInfo.category.equals("ไม่ระบุ") && courseInfo.priceperHr.equals("")) {
                url = "https://api.mlab.com/api/1/databases/yourtutor/collections/courses?apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            }
            ///nny
            else if (courseInfo.level.equals("ไม่ระบุ") && courseInfo.category.equals("ไม่ระบุ") && !courseInfo.priceperHr.equals("")) {
                url = "https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={\"priceperHr\":{$gte:\"" + courseInfo.priceperHr + "\"}}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";

            }
            //nyn
            else if (courseInfo.level.equals("ไม่ระบุ") && !courseInfo.category.equals("ไม่ระบุ") && courseInfo.priceperHr.equals("")) {
                url = "https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={\"category\":\"" + courseInfo.category + "\"}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            }
            //nyy
            else if (courseInfo.level.equals("ไม่ระบุ") && !courseInfo.category.equals("ไม่ระบุ") && !courseInfo.priceperHr.equals("")) {
                url = "https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={$and:[{\"category\":\"" + courseInfo.category + "\"},{\"priceperHr\":{$gte:\"" + courseInfo.priceperHr + "\"}}]}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";

            } else if (!courseInfo.level.equals("ไม่ระบุ") && courseInfo.category.equals("ไม่ระบุ") && courseInfo.priceperHr.equals("")) {
                url = "https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={\"level\":\"" + courseInfo.level + "\"}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";

            }
            //https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={$and:[{"level":"ม3"},{"category":"คณิตศาสตร์"}]}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR
            //yny
            else if (!courseInfo.level.equals("ไม่ระบุ") && courseInfo.category.equals("ไม่ระบุ") && !courseInfo.priceperHr.equals("")) {
                url = "https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={$and:[{\"level\":\"" + courseInfo.level + "\"},{\"priceperHr\":{$gte:\"" + courseInfo.priceperHr + "\"}}]}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            }
            //yyn
            else if (!courseInfo.level.equals("ไม่ระบุ") && !courseInfo.category.equals("ไม่ระบุ") && courseInfo.priceperHr.equals("")) {
                url = "https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={$and:[{\"level\":\"" + courseInfo.level + "\"},{\"category\":\"" + courseInfo.category + "\"}]}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            }
            //yyy
            else if (!courseInfo.level.equals("ไม่ระบุ") && !courseInfo.category.equals("ไม่ระบุ") && !courseInfo.priceperHr.equals("")) {
                url = "https://api.mlab.com/api/1/databases/yourtutor/collections/courses?q={$and:[{\"level\":\"" + courseInfo.level + "\"},{\"priceperHr\":{$gte:\"" + courseInfo.priceperHr + "\"}}{\"category\":\"\"" + courseInfo.category + "}]}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
            }


            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Log.v("urltestcourse", url);
            Response response = client.newCall(request).execute();
            String info = response.body().string();
            Log.v("accountinfo", info);
            JSONArray jsonInfo = new JSONArray(info);


            for (int i = 0; i < jsonInfo.length(); i++) {
                JSONObject jsonObject = jsonInfo.getJSONObject(i);
                JSONObject courseIDOuter = jsonObject.getJSONObject("_id");
                courseID = courseIDOuter.getString("$oid");

                category = jsonObject.getString("category");
                author = jsonObject.getString("author");
                subject = jsonObject.getString("subject");
                level = jsonObject.getString("level");
                location = jsonObject.getString("location");
                school = jsonObject.getString("school");
                description = jsonObject.getString("description");
                status = jsonObject.getString("status");
                priceperHr = jsonObject.getString("priceperHr");
                Log.v("subject", subject);
            }
            return null;
        } catch (JSONException e) {
            Log.e("ErrorJSON", e.getMessage());
        } catch (IOException e) {
            Log.e("ErrorIO", e.toString());
        }
        return null;
    }

    @Override

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}
