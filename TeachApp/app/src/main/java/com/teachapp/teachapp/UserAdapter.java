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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolderUser>{

    private Context mContext;
    private List<User> userList;

    public UserAdapter(Context mContext, List<User> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @Override
    public ViewHolderUser onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_tutor,null,false);
        return new ViewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUser viewHolderUser, int position) {
        final User user = userList.get(position);
        viewHolderUser.nameUser.setText(user.getName());
        viewHolderUser.universityUser.setText(user.getUniversity());
        viewHolderUser.emailUser.setText(user.getEmail());
        viewHolderUser.phoneUser.setText(user.getPhone());
        String areas = "";
        for (Area area : user.getAreas()){
            areas += area.getName();
        }
        viewHolderUser.areasUser.setText(areas);
        viewHolderUser.iconViewUser.setImageResource(R.drawable.studnt);
        viewHolderUser.elementView.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }

    public class ViewHolderUser extends RecyclerView.ViewHolder {

        TextView nameUser,emailUser,universityUser,phoneUser,areasUser;
        ImageView iconViewUser;
        View elementView;

        public ViewHolderUser(@NonNull View itemView) {
            super(itemView);
            nameUser = (TextView)itemView.findViewById(R.id.text_name_user);
            emailUser = (TextView)itemView.findViewById(R.id.text_email_user);
            universityUser = (TextView)itemView.findViewById(R.id.text_university_user);
            phoneUser = (TextView)itemView.findViewById(R.id.text_phone_user);
            areasUser = (TextView)itemView.findViewById(R.id.text_areas_user);
            iconViewUser = (ImageView)itemView.findViewById(R.id.icon_user);
            elementView = itemView;
        }
    }
}
