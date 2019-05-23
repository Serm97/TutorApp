package com.teachapp.teachapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolderNotifications> {

    private Context mContext;
    private List<Notification> notificationList;

    public NotificationsAdapter(Context mContext, List<Notification> notificationList) {
        this.mContext = mContext;
        this.notificationList = notificationList;
    }

    @Override
    public ViewHolderNotifications onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_notifications,null,false);
        return new ViewHolderNotifications(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNotifications viewHolderNotifications, int i) {
        final Notification not = this.notificationList.get(i);
        viewHolderNotifications.messageView.setText(not.getMessage());
        viewHolderNotifications.dateView.setText(new SimpleDateFormat("dd-MM-yyyy").format(not.getNotificationDate()));
        viewHolderNotifications.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Request req = not.getRequest();
                final Area areaO = req.getAreaO();
                final Area areaOR = req.getAreaS();
                Tutorial tutorial = new Tutorial(
                        req.getApplicant(),
                        not.getToUser(),
                        areaO,
                        areaOR,
                        req.getDateLimit(),
                        0);
                FireDatabase.getInstance().child("Tutorials").push().setValue(tutorial);
                Toast.makeText(mContext,"Tutoria Programada",Toast.LENGTH_LONG).show();
            }
        });


        viewHolderNotifications.btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Tutoria Rechazada",Toast.LENGTH_LONG).show();
            }
        });

        //setFadeAnimation(viewHolderNotifications.itemView);
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.5f, 1.0f);
        anim.setDuration(100);
        view.startAnimation(anim);
    }


    @Override
    public int getItemCount() {
        return this.notificationList.size();
    }

    public class ViewHolderNotifications extends RecyclerView.ViewHolder {

        public TextView dateView,messageView;
        public Button btnAccept,btnRefuse;

        public ViewHolderNotifications(@NonNull View itemView) {
            super(itemView);
            dateView=(TextView) itemView.findViewById(R.id.text_date_notificacion);
            messageView=(TextView) itemView.findViewById(R.id.text_msg_notification);
            btnAccept = (Button) itemView.findViewById(R.id.button_accept);
            btnRefuse = (Button) itemView.findViewById(R.id.button_refuse);
        }
    }
}
