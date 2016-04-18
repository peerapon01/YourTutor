package ninja.psuse.yourtutor;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ninja.psuse.yourtutor.other.RegisterInfo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String facebookName;
    String facebookId;
    String firstname;
    String lastname;
    String mobilenum;
    String email;
    String lineid;
    TextView nameTxt;
    TextView facebookNameTxt;
    TextView mobileNumberTxt;
    TextView lineidTxt;
    TextView emailTxt;
    com.pnikosis.materialishprogress.ProgressWheel progressWheel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView userPic;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1,String param2) {
        AccountFragment fragment = new AccountFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_account, container, false);
        userPic = (ImageView) view.findViewById(R.id.user_pic_account);
        Glide.with(this).load(mParam1).asBitmap().centerCrop().into(new BitmapImageViewTarget(userPic) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                userPic.setImageDrawable(circularBitmapDrawable);

            }
        });

        nameTxt = (TextView) view.findViewById(R.id.info_fist_last);
        lineidTxt = (TextView) view.findViewById(R.id.info_lineid);
        emailTxt = (TextView) view.findViewById(R.id.info_email);
        mobileNumberTxt = (TextView) view.findViewById(R.id.info_mobilenum);
        facebookNameTxt = (TextView) view.findViewById(R.id.info_fbName);
        progressWheel = (com.pnikosis.materialishprogress.ProgressWheel) view.findViewById(R.id.progress_wheelAccount);

        getAccountInfo getAccountInfo = new getAccountInfo();
        getAccountInfo.execute(mParam2);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
    @Override
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
    public class getAccountInfo extends AsyncTask<String,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressWheel.setVisibility(View.VISIBLE);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {
                String facebookId = params[0];
                Request request = new Request.Builder()
                        .url("https://api.mlab.com/api/1/databases/yourtutor/collections/users?q={\"facebookid\":\"" + facebookId + "\"}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR")
                        .build();
                Log.v("urltest", "https://api.mlab.com/api/1/databases/yourtutor/collections/users?q={\"facebookid\":\"" + facebookId + "\"}&apiKey=HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR");
                Response response = client.newCall(request).execute();
                String info = response.body().string();
                Log.v("accountinfo",info);
                JSONArray jsonInfo = new JSONArray(info);


for(int i=0;i<jsonInfo.length();i++) {
    JSONObject jsonObject = jsonInfo.getJSONObject(i);
    firstname = jsonObject.getString("first_name");
    lastname = jsonObject.getString("last_name");
    email = jsonObject.getString("email");
    mobilenum = jsonObject.getString("phone");
    lineid = jsonObject.getString("lineid");
    facebookName = jsonObject.getString("facebookname");
}


               } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();

            }


            catch (Exception e){
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressWheel.setVisibility(View.GONE);
           nameTxt.setText("Name : "+firstname+" "+lastname);
            emailTxt.setText("Email : "+email);
            lineidTxt.setText("Line ID : "+lineid);
            facebookNameTxt.setText("Facebook Name : "+facebookName);
            mobileNumberTxt.setText("Mobile No. : "+mobilenum);

        }
    }

}
