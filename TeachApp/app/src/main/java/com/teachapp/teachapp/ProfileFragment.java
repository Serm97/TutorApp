package com.teachapp.teachapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private User user;
    private TextView nameView,emailView,cityView,universityView,numberTView,areasView,scoreView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private OnFragmentInteractionListener mListener;
    private EditText editFecha;
    private ImageView imageProfile;
    private Spinner spinnerAO;
    private Spinner spinnerAS;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((BaseActivity)getActivity()).hideKeyboard(view);

        nameView =(TextView) view.findViewById(R.id.nomProfile);
        emailView =(TextView) view.findViewById(R.id.emailProfile);
        universityView=(TextView) view.findViewById(R.id.universityProfile);
        cityView =(TextView) view.findViewById(R.id.cityProfile);
        areasView =(TextView) view.findViewById(R.id.areasProfile);
        numberTView=(TextView) view.findViewById(R.id.numberTutProfile);
        scoreView=(TextView) view.findViewById(R.id.scoreProfile);
        imageProfile = view.findViewById(R.id.imageProfile);

        Button btnRequest = (Button) view.findViewById(R.id.btnRequest);

        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);
        imageProfile.startAnimation(animation);
        scoreView.startAnimation(animation);



        if (getArguments() != null){
            if (getArguments().getSerializable("mainUser") != null){
                user = (User)getArguments().getSerializable("mainUser");
                loadInfoUser(user);
                btnRequest.setVisibility(View.GONE);
            } else if (getArguments().getSerializable("userToView") != null){
                user = (User)getArguments().getSerializable("userToView");
                loadInfoUser(user);
                btnRequest.setVisibility(View.VISIBLE);
            }
        }
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogRequest();
            }
        });
        return view;
    }

    private void openDialogRequest() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_seeker);
        dialog.setTitle("Solicitar");
        editFecha = (EditText) dialog.findViewById(R.id.editDateLimit);
        spinnerAO = (Spinner) dialog.findViewById(R.id.spinnerAreaO);
        spinnerAS = (Spinner) dialog.findViewById(R.id.spinnerAreaS);

        //Area Solicitada
        loadAreasRequest();
        //Area Ofrecida
        loadAreasOffered(dialog);

        //Open Calendar
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
        dialog.show();
    }

    private void loadAreasOffered(final Dialog dialog) {
        final ArrayList<String> comboO = new ArrayList<>();
        final ArrayList<User> users = new ArrayList<>();
        Button btnFind = (Button) dialog.findViewById(R.id.btn_find_tutorial);

        Bundle mybundle = getActivity().getIntent().getExtras();
        String email = mybundle.getString("nombreUsuario");
        comboO.add("-Seleccione-");
        Query qq = FireDatabase.getInstance().child("User").orderByChild("email").equalTo(email).limitToFirst(1);
        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                        User infoUser = dataSnap.getValue(User.class);
                        users.add(infoUser);
                        for (Area a : infoUser.getAreas()){
                            comboO.add(a.getName());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled",""+databaseError.getDetails());
            }
        });
        ArrayAdapter<String> adapterO = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,comboO);
        spinnerAO.setAdapter(adapterO);
        spinnerAO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("onNothingSelected","No hay nada seleccionado");
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request(
                        users.get(0),
                        new Area(spinnerAO.getSelectedItem().toString()),
                        new Area(spinnerAS.getSelectedItem().toString()),
                        new Date(editFecha.getText().toString())
                );
                Notification not = new Notification(
                        user,
                        new Date(),
                        request.getApplicant().getName()+" solicita una tutoria de "+request.getAreaS().getName(),
                        request);

                FireDatabase.getInstance().child("Request").push().setValue(request);
                FireDatabase.getInstance().child("Notifications").push().setValue(not);
                Toast.makeText(getActivity(),"Petición realizada",Toast.LENGTH_LONG).show();
                dialog.hide();
            }
        });
    }

    private void loadAreasRequest() {
        final ArrayList<String> comboA = new ArrayList<>();
        comboA.add("-Seleccione-");
        Query q = FireDatabase.getInstance().child("User").orderByChild("email").equalTo(user.getEmail()).limitToFirst(1);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                        User infoUser = dataSnap.getValue(User.class);
                        for (Area a : infoUser.getAreas()){
                            comboA.add(a.getName());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled",""+databaseError.getDetails());
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,comboA);
        spinnerAS.setAdapter(adapter);
        spinnerAS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemSelected","Item seleccionado");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("onNothingSelected","No hay nada seleccionado");
            }
        });
    }

    private void loadInfoUser(User user) {
        String name = user.getName() != null ? user.getName() : "";
        name += user.getLastName() != null ? " "+user.getLastName() : "";
        String email = "Correo: "+(user.getEmail() != null ? user.getEmail() : " - ");
        String university = "Universidad: "+ (user.getUniversity() != null ? user.getUniversity() : " - ");
        String city = "Ciudad: "+ (user.getCity() != null ? user.getCity() : " - ");
        String numeroT = "Numero de tutorias: "+String.valueOf(user.getNtutorials());
        String score = String.valueOf(user.getScore());

        nameView.setText(name.toUpperCase());
        emailView.setText(email.toUpperCase());
        universityView.setText(university.toUpperCase());
        cityView.setText(city.toUpperCase());
        numberTView.setText(numeroT.toUpperCase());
        scoreView.setText(score.toUpperCase());
        String areas = "Conocimientos: \n";
        for(Area area : user.getAreas()){
            areas += "•"+area.getName().toUpperCase()+"\n";
        }
        areasView.setText(areas);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
