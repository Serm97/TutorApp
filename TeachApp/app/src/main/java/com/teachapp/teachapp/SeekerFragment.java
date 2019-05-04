package com.teachapp.teachapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeekerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeekerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeekerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;
    EditText editFecha;
    Spinner spinnerAO;
    Spinner spinnerAS;
    Button btnFind;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    private OnFragmentInteractionListener mListener;

    public SeekerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeekerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeekerFragment newInstance(String param1, String param2) {
        SeekerFragment fragment = new SeekerFragment();
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
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_seeker, container, false);
        ((BaseActivity)getActivity()).hideKeyboard(vista);
        editFecha = (EditText) vista.findViewById(R.id.editDateLimit);
        spinnerAO = (Spinner) vista.findViewById(R.id.spinnerAreaO);
        spinnerAS = (Spinner) vista.findViewById(R.id.spinnerAreaS);
        btnFind = (Button) vista.findViewById(R.id.btn_find_tutorial);

        loadRequestArea();
        loadOfferedArea();
        loadCalendar();
        loadRequest();
        return vista;
    }

    private void loadRequest() {
        final ArrayList<User> users = new ArrayList<>();

        Bundle mybundle = getActivity().getIntent().getExtras();
        String email = mybundle.getString("nombreUsuario");
        Query q = FireDatabase.getInstance().child("User").orderByChild("email").equalTo(email).limitToFirst(1);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                        User infoUser = dataSnap.getValue(User.class);
                        users.add(infoUser);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Request request = new Request(
                        users.get(0),
                        new Area(spinnerAO.getSelectedItem().toString()),
                        new Area(spinnerAS.getSelectedItem().toString()),
                        new java.util.Date(editFecha.getText().toString())
                );

                FireDatabase.getInstance().child("Request").push().setValue(request);
                Toast.makeText(getActivity(),"Petición realizada",Toast.LENGTH_LONG).show();

                Fragment fragment = new MatchFragment();
                Bundle args = new Bundle();
                args.putSerializable("requestMatch",request);
                fragment.setArguments(args);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,fragment)
                        .commit();
            }
        });
    }

    private void loadOfferedArea() {
        final ArrayList<String> areasUser = new ArrayList<>();
        areasUser.add("-Seleccione-");
        Bundle mybundle = getActivity().getIntent().getExtras();
        String email = mybundle.getString("nombreUsuario");
        Query q = FireDatabase.getInstance().child("User").orderByChild("email").equalTo(email).limitToFirst(1);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                        User infoUser = dataSnap.getValue(User.class);
                        for (Area a : infoUser.getAreas()){
                            areasUser.add(a.getName());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,areasUser);
        spinnerAO.setAdapter(adapter);
        spinnerAO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadRequestArea() {
        final ArrayList<String> comboA = new ArrayList<>();
        comboA.add("-Seleccione-");
        FireDatabase.getInstance().child("Utilities").child("Areas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Utilities universitie = snapshot.getValue(Utilities.class);
                    comboA.add(universitie.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,comboA);
        spinnerAS.setAdapter(adapter);
        spinnerAS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadCalendar() {
        editFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_DeviceDefault_Light_Dialog,
                        mDateSetListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                String y = String.valueOf(year);
                String m = String.valueOf(month < 10 ? "0"+month : month);
                String d = String.valueOf(dayOfMonth < 10 ? "0"+dayOfMonth:dayOfMonth);
                editFecha.setText(d+"/"+m+"/"+y);
            }
        };
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
