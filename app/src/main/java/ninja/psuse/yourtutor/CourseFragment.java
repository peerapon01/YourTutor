package ninja.psuse.yourtutor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import ninja.psuse.yourtutor.Async.ClassifyCourseAsyncTask;
import ninja.psuse.yourtutor.Async.RegisterAsyncTask;
import ninja.psuse.yourtutor.other.CourseInfo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {
    android.support.v7.widget.AppCompatEditText subject;
    android.support.v7.widget.AppCompatEditText location;
    android.support.v7.widget.AppCompatEditText priceperHr;
    android.support.v7.widget.AppCompatEditText description;
    android.support.v7.widget.AppCompatEditText school;
    Button confirmClassify;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1,String param2) {
        CourseFragment fragment = new CourseFragment();
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
        View view=inflater.inflate(R.layout.fragment_course, container, false);
        subject = (android.support.v7.widget.AppCompatEditText) view.findViewById(R.id.subject);
        location = (android.support.v7.widget.AppCompatEditText) view.findViewById(R.id.location);
        priceperHr = (android.support.v7.widget.AppCompatEditText) view.findViewById(R.id.priceper_hr);
        description = (android.support.v7.widget.AppCompatEditText) view.findViewById(R.id.description);
        confirmClassify = (Button) view.findViewById(R.id.confirmClassify);
        school = (android.support.v7.widget.AppCompatEditText) view.findViewById(R.id.school);
        final String facebookName = mParam2;
        final String facebookId = mParam1;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, LEVEL);
        final MaterialBetterSpinner textView = (MaterialBetterSpinner)
                view.findViewById(R.id.spinnerLevel);
        textView.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, CATEGORY);
        final MaterialBetterSpinner textView2 = (MaterialBetterSpinner)
                view.findViewById(R.id.spinnerCategory);
        textView2.setAdapter(adapter2);

        confirmClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CourseInfo courseInfo = new CourseInfo();
                courseInfo.author=facebookName;
                courseInfo.authorfacebookID=facebookId;
                courseInfo.category=textView2.getText().toString();
                courseInfo.description=description.getText().toString();
                courseInfo.level=textView.getText().toString();
                courseInfo.location=location.getText().toString();
                courseInfo.description=description.getText().toString();
                courseInfo.school=school.getText().toString();
                courseInfo.subject=subject.getText().toString();
                courseInfo.priceperHr=priceperHr.getText().toString();
                courseInfo.status="Not Yet";
                courseInfo.reservedBy="Not Yet";
                ClassifyCourseAsyncTask classifyCourseAsyncTask = new ClassifyCourseAsyncTask();
                classifyCourseAsyncTask.execute(courseInfo);

            }
        });





        return view;
    }
    private static final String[] LEVEL = new String[] {
            "ป1","ป2","ป3","ป4","ป5","ป6","ม1","ม2","ม3","ม4","ม5","ม6","มหาวิทยาลัย"
    };
    private static final String[] CATEGORY = new String[] {
           "คณิตศาสตร์","วิทยาศาสตร์ทั่วไป","ฟิสิกส์","เคมี","ชีวะ","ภาษาอังกฤษ","ภาษาไทย","สังคม","อื่นๆ"
    };


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
