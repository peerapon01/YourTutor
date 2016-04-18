package ninja.psuse.yourtutor;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ninja.psuse.yourtutor.Async.ChangeCourseStatusAsyncTask;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultCourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultCourseFragment extends Fragment {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String category;
    String author;
    String subject;
    String level;
    String location;
    String school;
    String priceperHr;
    String description;
    String status;
    String authorfacebookID;
    String facebookName;
    String facebookId;
    String firstname;
    String lastname;
    String mobilenum;
    String email;
    String lineid;
    ArrayList<String> courseID=new ArrayList<String>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ResultCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultCourseFragment newInstance(JSONArray param1, String param2) {
        ResultCourseFragment fragment = new ResultCourseFragment();
        Bundle args = new Bundle();
        String json1 = param1.toString();
        args.putString(ARG_PARAM1, json1);
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_course, container, false);

        final MaterialListView mListView = (MaterialListView) view.findViewById(R.id.material_listview);
        try {
            JSONArray jsonInfo = new JSONArray(mParam1);
            for (int i = 0; i < jsonInfo.length(); i++) {
                JSONObject jsonObject = jsonInfo.getJSONObject(i);
                JSONObject courseIDOuter = jsonObject.getJSONObject("_id");
                courseID.add(courseIDOuter.getString("$oid"));
                category = jsonObject.getString("category");
                author = jsonObject.getString("author");
                authorfacebookID = jsonObject.getString("facebookID");
                subject = jsonObject.getString("subject");
                level = jsonObject.getString("level");
                location = jsonObject.getString("location");
                school = jsonObject.getString("school");
                description = jsonObject.getString("description");
                status = jsonObject.getString("status");
                priceperHr = jsonObject.getString("priceperHr");
                ArrayList<String> idAndauthorID = new ArrayList<String>();
                idAndauthorID.add(courseIDOuter.getString("$oid"));
                idAndauthorID.add(authorfacebookID);

                Log.v("subject", subject);
                if(status.equals("Not Yet")) {
                    Card card = new Card.Builder(getActivity())
                            .withProvider(new CardProvider())
                            .setLayout(R.layout.material_basic_image_buttons_card_layout)
                            .setTitle(subject)
                            .setTitleGravity(Gravity.END)
                            .setSubtitle("แตะเพื่อดูข้อมูลผู้เรียน")
                            .setSubtitleGravity(Gravity.END)
                            .setSubtitleColor(getResources().getColor(R.color.black_button))

                            .setDescription("ประกาศโดย : " + author + "\n " + "รายละเอียด : " + description + " \n" + "ระดับชั้น : "+ level + "\n "  + "สถานที่สอน : "+ location + "\n " + "สถานศึกษา : " +  school + "\n" + "สถานะ : "+status + "\n " + "ราคาต่อชั่วโมง : " +
                                    priceperHr )
                            .setDescriptionGravity(Gravity.START)
                            .setDrawable(R.drawable.ic_account_circle_black_24dp)
                            .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                                @Override
                                public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                                    requestCreator.fit();
                                }
                            })
                            .addAction(R.id.left_text_button, new TextViewAction(getActivity())

                                    .setText("ยกเลิก")
                                    .setTextResourceColor(R.color.black_button)
                                    .setListener(new OnActionClickListener() {
                                        @Override
                                        public void onActionClicked(View view, Card card) {
                                            card.setDismissible(true);
                                            Toast.makeText(getActivity(), "You have pressed the left button", Toast.LENGTH_SHORT).show();
                                            mListView.getAdapter().clear();
                                            //card.dismiss();


                                        }
                                    }))
                            .addAction(R.id.right_text_button, new TextViewAction(getActivity())
                                    .setText("รับงาน")
                                    .setTextResourceColor(R.color.orange_button)
                                    .setListener(new OnActionClickListener() {
                                        @Override
                                        public void onActionClicked(View view, Card card) {
                                            card.setDismissible(true);
                                            Toast.makeText(getActivity(), "You have pressed the right button on card " + courseID.get(mListView.getAdapter().getPosition(card)), Toast.LENGTH_SHORT).show();
                                            ArrayList<String> things = new ArrayList<String>();
                                            things.add(mParam2);
                                            ArrayList<String> forGet = (ArrayList<String>) card.getTag();

                                            things.add(forGet.get(0));
                                            ChangeCourseStatusAsyncTask changeCourseStatusAsyncTask = new ChangeCourseStatusAsyncTask();
                                            changeCourseStatusAsyncTask.execute(things);
                                            mListView.getAdapter().clear();
                                            // card.dismiss();
                                        }
                                    }))

                            .endConfig()
                            .setTag(idAndauthorID)
                            .build();

                    mListView.getAdapter().addAtStart(card);
                    mListView.getAdapter().notifyDataSetChanged();

                }


            }
            mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {

                @Override
                public void onItemClick(Card card, int position) {
                    Log.d("CARD_TYPE", "test");
                    ArrayList<String> forGet = (ArrayList<String>) card.getTag();
                    String authorfbID = forGet.get(1);
                    getAccountInfoDialog getAccountInfoDialog = new getAccountInfoDialog();
                    getAccountInfoDialog.execute(authorfbID);

                }

                @Override
                public void onItemLongClick(Card card, int position) {
                    ArrayList<String> forGet = (ArrayList<String>) card.getTag();
                    Log.d("LONG_CLICK", card.getTag().toString());
                    String authorfbID = forGet.get(1);
                }
            });



        } catch (JSONException e) {
            Log.e("ErrorJSon", e.getMessage());
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
  /*  public void onButtonPressed(Uri uri) {
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
    }*/
    public class getAccountInfoDialog extends AsyncTask<String,Void,Void> {



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
            Toast.makeText(getContext(),"Touch outside 2 time to dismiss this",Toast.LENGTH_SHORT).show();
            final MaterialSimpleListAdapter adapter2 = new MaterialSimpleListAdapter(getContext());
            adapter2.add(new MaterialSimpleListItem.Builder(getContext())
                    .content(email)
                    .icon(R.drawable.ic_email_black_24dp)
                    .backgroundColor(Color.WHITE)
                    .build());
            adapter2.add(new MaterialSimpleListItem.Builder(getContext())
                    .content(mobilenum)
                    .icon(R.drawable.ic_account_circle_black_24dp)
                    .backgroundColor(Color.WHITE)
                    .build());

            adapter2.add(new MaterialSimpleListItem.Builder(getContext())
                    .content(lineid)
                    .icon(R.drawable.line)
                    .backgroundColor(Color.WHITE)
                    .build());

            adapter2.add(new MaterialSimpleListItem.Builder(getContext())
                    .content(facebookName)
                    .icon(R.drawable.facebook)
                    .backgroundColor(Color.WHITE)
                    .build());
                          /*  adapter.add(new MaterialSimpleListItem.Builder(getActivity())
                                    .content(R.string.add_account)
                                    .icon(R.drawable.ic_content_add)
                                    .iconPaddingDp(8)
                                    .build());*/

            new MaterialDialog.Builder(getContext())
                    .title("ข้อมูลที่ติดต่อได้ของผู้เรียน")
                    .adapter(adapter2, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            MaterialSimpleListItem item = adapter2.getItem(which);

                            // TODO
                        }
                    })
                    .show();

        }
    }
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

}
