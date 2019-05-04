package com.teachapp.teachapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Request request;
    private RecyclerView recyclerUserMatch;
    private TextView areaSearched,numerResult;

    private OnFragmentInteractionListener mListener;

    public MatchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchFragment newInstance(String param1, String param2) {
        MatchFragment fragment = new MatchFragment();
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
        View vista = inflater.inflate(R.layout.fragment_match, container, false);
        recyclerUserMatch = (RecyclerView) vista.findViewById(R.id.recyclerMatch);
        areaSearched = (TextView) vista.findViewById(R.id.txtAreaSearched);
        numerResult = (TextView) vista.findViewById(R.id.text_number_result);

        if (getArguments() != null){
            if (getArguments().getSerializable("requestMatch") != null){
                request = (Request)getArguments().getSerializable("requestMatch");
                areaSearched.setText("Se Busca: "+request.getAreaS().getName());
                findMatchRequest(request);
            }else if(getArguments().getSerializable("searchMatch") != null){
                String search = getArguments().getString("searchMatch");
                areaSearched.setText("Se busca coincidencia con: "+search);
                searchUser(search);
            }else if(getArguments().getSerializable("categoryMatch") != null){
                Category cat = (Category) getArguments().getSerializable("categoryMatch");
                if (cat.getAreas()== null){
                    Toast.makeText(getActivity(),"No hay coincidencias",Toast.LENGTH_LONG).show();
                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_main,new CategoriesFragment())
                            .commit();
                }else if(cat.getAreas().size()>0){
                    areaSearched.setText("Se busca Categorias en: "+cat.getName());
                    searchCategory(cat);
                }

            }
        }


        return vista;
    }

    private void searchCategory(final Category cat) {
        final List<User> userList = new ArrayList<>();
        Bundle mybundle = getActivity().getIntent().getExtras();
        final String email = mybundle.getString("nombreUsuario");

        FireDatabase.getInstance().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User u = snapshot.getValue(User.class);
                        if (!u.getEmail().equals(email)){
                            for(Area area : cat.getAreas()){
                                for (Area a : u.getAreas()){
                                    if (a.getName().equals(area.getName())){
                                        userList.add(u);
                                        break;
                                    }
                                }

                            }
                        }

                    }
                    String conocimientos = "Areas: \n";
                    for(Area a : cat.getAreas()){
                        conocimientos += "\t•"+a.getName()+"\n";
                    }
                    conocimientos += "\n Resultados: "+userList.size();
                    numerResult.setText(conocimientos);
                    recyclerUserMatch.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recyclerUserMatch.setItemAnimator(new DefaultItemAnimator());
                    UserAdapter adapter = new UserAdapter(getContext(),userList);
                    recyclerUserMatch.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchUser(final String search) {
        final List<User> userList = new ArrayList<>();
        Bundle mybundle = getActivity().getIntent().getExtras();
        final String email = mybundle.getString("nombreUsuario");

        FireDatabase.getInstance().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User u = snapshot.getValue(User.class);
                    if (!u.getEmail().equals(email)){
                        if (u.getName().toLowerCase().contains(search.toLowerCase()) ||
                                u.getLastName().toLowerCase().contains(search.toLowerCase())){
                            userList.add(u);
                        }

                    }
                }
                numerResult.setText("Resultados: "+userList.size());
                recyclerUserMatch.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerUserMatch.setItemAnimator(new DefaultItemAnimator());
                UserAdapter adapter = new UserAdapter(getContext(),userList);
                recyclerUserMatch.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void findMatchRequest(final Request request) {
        final ArrayList<Request> lstRequestMatch = new ArrayList<>();
        final ArrayList<Request> lstRequest = new ArrayList<>();
        FireDatabase.getInstance().child("Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Request req = snapshot.getValue(Request.class);
                    if(!validateUser(req)){
                        if(validateMatch(req)){
                            lstRequestMatch.add(req);
                        }else if (req.getAreaO().getName().equals(request.getAreaS().getName())){
                            lstRequest.add(req);
                        }
                    }
                }
                for(Request r : lstRequest){
                    lstRequestMatch.add(r);
                }
                numerResult.setText("Resultados: "+lstRequestMatch.size());
                if(lstRequestMatch.size()>0){
                    recyclerUserMatch.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recyclerUserMatch.setItemAnimator(new DefaultItemAnimator());
                    UserMatchAdapter adapter = new UserMatchAdapter(getContext(),lstRequestMatch,request);
                    recyclerUserMatch.setAdapter(adapter);
                }else{
                    Toast.makeText(getActivity(),"No hay coincidencias por el momento",Toast.LENGTH_LONG).show();
                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_main,new HistoryFragment())
                            .commit();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean validateMatch(Request req) {
        return ((req.getAreaO().getName().equals(request.getAreaS().getName())) &&
                (req.getAreaS().getName().equals(request.getAreaO().getName())));
    }

    private boolean validateUser(Request req) {
        return req.getApplicant().getEmail().equals(request.getApplicant().getEmail());
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
