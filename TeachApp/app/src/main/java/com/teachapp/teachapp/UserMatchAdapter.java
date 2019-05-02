package com.teachapp.teachapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class UserMatchAdapter extends RecyclerView.Adapter<UserMatchAdapter.ViewHolderUserMatch> {

    private Context mContext;
    private List<Request> requests;
    private Request mainRequest;

    public UserMatchAdapter(Context mContext, List<Request> requests,Request mainRequest) {
        this.mContext = mContext;
        this.requests = requests;
        this.mainRequest=mainRequest;
    }

    @Override
    public ViewHolderUserMatch onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_match,null,false);
        return new ViewHolderUserMatch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUserMatch viewHolderUserMatch, int position) {
        final Request request = this.requests.get(position);
        final User user = request.getApplicant();
        viewHolderUserMatch.nameUser.setText(user.getName());
        viewHolderUserMatch.universityUser.setText(user.getUniversity());
        viewHolderUserMatch.emailUser.setText(user.getEmail());
        viewHolderUserMatch.phoneUser.setText(user.getPhone());
        viewHolderUserMatch.areasUser.setText("Area solicitada: "+request.getAreaS().getName());
        viewHolderUserMatch.iconViewUser.setImageResource(R.mipmap.student);
        if (mainRequest.getAreaO().getName().equals(request.getAreaS().getName())&&
                mainRequest.getAreaS().getName().equals(request.getAreaO().getName())){
            viewHolderUserMatch.matchRequest.setVisibility(View.VISIBLE);
        }else{
            viewHolderUserMatch.matchRequest.setVisibility(View.GONE);
        }
        viewHolderUserMatch.iconViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfileFragment();
                Bundle args = new Bundle();
                args.putSerializable("userToView",user);
                fragment.setArguments(args);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,fragment)
                        .commit();

            }
        });
        viewHolderUserMatch.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification not = new Notification(
                        user,
                        new Date(),
                        request.getApplicant().getName()+" solicita una tutoria de "+request.getAreaS().getName(),
                        request);
                FireDatabase.getInstance().child("Notifications").push().setValue(not);
                Toast.makeText(mContext,"Petición realizada",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.requests.size();
    }

    public class ViewHolderUserMatch extends RecyclerView.ViewHolder {

        TextView nameUser,emailUser,universityUser,phoneUser,areasUser,matchRequest;
        ImageView iconViewUser;
        Button btnRequest;
        View elementView;

        public ViewHolderUserMatch(@NonNull View itemView) {
            super(itemView);
            nameUser = (TextView)itemView.findViewById(R.id.text_name_user_M);
            emailUser = (TextView)itemView.findViewById(R.id.text_email_user_M);
            universityUser = (TextView)itemView.findViewById(R.id.text_university_user_M);
            phoneUser = (TextView)itemView.findViewById(R.id.text_phone_user_M);
            areasUser = (TextView)itemView.findViewById(R.id.text_areas_user_M);
            matchRequest = (TextView)itemView.findViewById(R.id.match_score);
            btnRequest = (Button) itemView.findViewById(R.id.button_match_request);

            iconViewUser = (ImageView)itemView.findViewById(R.id.icon_user_M);
            elementView = itemView;
        }
    }
}
