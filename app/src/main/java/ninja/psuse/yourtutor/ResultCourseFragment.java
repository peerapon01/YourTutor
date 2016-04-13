package ninja.psuse.yourtutor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.view.MaterialListView;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ninja.psuse.yourtutor.Async.ChangeCourseStatusAsyncTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultCourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultCourseFragment extends Fragment {
    String category;
    String author;
    String subject;
    String level;
    String location;
    String school;
    String priceperHr;
    String description;
    String status;
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
                subject = jsonObject.getString("subject");
                level = jsonObject.getString("level");
                location = jsonObject.getString("location");
                school = jsonObject.getString("school");
                description = jsonObject.getString("description");
                status = jsonObject.getString("status");
                priceperHr = jsonObject.getString("priceperHr");
                Log.v("subject", subject);
                Card card = new Card.Builder(getActivity())
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_basic_image_buttons_card_layout)
                        .setTitle(subject)
                        .setTitleGravity(Gravity.END)

                        .setDescription(author + " " + description + " " + level + " " + location + " " + school + " " + status + " " + priceperHr)
                        .setDescriptionGravity(Gravity.END)
                        .setDrawable(R.drawable.ic_account_circle_white_24dp)
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
                                        things.add((String) card.getTag());
                                        ChangeCourseStatusAsyncTask changeCourseStatusAsyncTask = new ChangeCourseStatusAsyncTask();
                                        changeCourseStatusAsyncTask.execute(things);
                                        mListView.getAdapter().clear();
                                       // card.dismiss();
                                    }
                                }))
                        .endConfig()
                        .setTag(courseIDOuter.getString("$oid"))
                        .build();

                mListView.getAdapter().addAtStart(card);


            }
            mListView.getAdapter().notifyDataSetChanged();


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
