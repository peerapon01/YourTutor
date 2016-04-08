package ninja.psuse.yourtutor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ninja.psuse.yourtutor.Async.ResultedCourseAsyncTask;
import ninja.psuse.yourtutor.other.CourseInfo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainActivityFragment extends Fragment {
    android.support.v7.widget.AppCompatEditText priceperHr;
    Button searchButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainActivityFragment newInstance(String param1, String param2) {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private static final String[] COUNTRIES = new String[]{
            "ป1", "ป2", "ป3", "ป4", "มัธยม", "มหาลัย"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        priceperHr = (android.support.v7.widget.AppCompatEditText) view.findViewById(R.id.findpriceper_hr);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, LEVEL);
        final MaterialBetterSpinner level = (MaterialBetterSpinner)
                view.findViewById(R.id.spinnerFindLevel);
        level.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, CATEGORY);
        final MaterialBetterSpinner category = (MaterialBetterSpinner)
                view.findViewById(R.id.spinnerFindCategory);
        category.setAdapter(adapter2);

        searchButton = (Button) view.findViewById(R.id.searchCourse);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseInfo courseInfo = new CourseInfo();
                courseInfo.category = category.getText().toString();
                courseInfo.level = level.getText().toString();
                courseInfo.priceperHr = priceperHr.getText().toString();
                ResultedCourseAsyncTask resultedCourseAsyncTask = new ResultedCourseAsyncTask();
                resultedCourseAsyncTask.execute(courseInfo);
            }
        });
        return view;
    }

    private static final String[] LEVEL = new String[]{
            "ไม่ระบุ", "ป1", "ป2", "ป3", "ป4", "ป5", "ป6", "ม1", "ม2", "ม3", "ม4", "ม5", "ม6", "มหาวิทยาลัย"
    };
    private static final String[] CATEGORY = new String[]{
            "ไม่ระบุ", "คณิตศาสตร์", "วิทยาศาสตร์ทั่วไป", "ฟิสิกส์", "เคมี", "ชีวะ", "ภาษาอังกฤษ", "ภาษาไทย", "สังคม", "อื่นๆ"
    };
    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
}*/

    @Override
    /*public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/


    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class ResultedCourseAsyncTask extends AsyncTask<CourseInfo, Void, JSONArray> {
        String category;
        String author;
        String subject;
        String level;
        String location;
        String school;
        String priceperHr;
        String description;
        String status;

        public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override

        protected JSONArray doInBackground(CourseInfo... params) {
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
                return jsonInfo;
            } catch (JSONException e) {
                Log.e("ErrorJSON", e.getMessage());
            } catch (IOException e) {
                Log.e("ErrorIO", e.toString());
            }
            return null;
        }

        @Override

        protected void onPostExecute(JSONArray jsonInfo) {
            super.onPostExecute(jsonInfo);
            getFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer, ResultCourseFragment.newInstance(jsonInfo, mParam2))
                    .commit();

        }
    }

}
