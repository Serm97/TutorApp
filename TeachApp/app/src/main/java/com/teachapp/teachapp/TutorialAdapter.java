package com.teachapp.teachapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.ViewHolderTutorials> {

    public Context mContext;
    private List<Tutorial> tutorialList;

    public TutorialAdapter(Context mContext, List<Tutorial> tutorialList) {
        this.mContext = mContext;
        this.tutorialList = tutorialList;
    }

    @Override
    public TutorialAdapter.ViewHolderTutorials onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_tutorial,null,false);
        return new ViewHolderTutorials(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialAdapter.ViewHolderTutorials viewHolderTutorials, int i) {
        final Tutorial t = this.tutorialList.get(i);
        viewHolderTutorials.date.setText("Fecha Tutoria: "+new SimpleDateFormat("dd-MM-yyyy").format(t.getDate()));
        viewHolderTutorials.areaO.setText("Conocimiento Ofrecido: "+ t.getArea().getName());
        viewHolderTutorials.areaR.setText("Conocimiento Recibido: "+t.getRequestedArea().getName());
        viewHolderTutorials.userView.setText("Estudiante: "+t.getApplicant().getName()+" "+t.getApplicant().getLastName());
        viewHolderTutorials.tutoView.setText("Tutor: "+t.getTutor().getName()+" "+t.getTutor().getLastName());

    }

    @Override
    public int getItemCount() {
        return this.tutorialList.size();
    }

    public class ViewHolderTutorials extends RecyclerView.ViewHolder {

        public TextView userView,tutoView,areaO,areaR,date;

        public ViewHolderTutorials(@NonNull View itemView) {
            super(itemView);
            userView= (TextView) itemView.findViewById(R.id.text_user_tutorial);
            tutoView= (TextView) itemView.findViewById(R.id.text_tutor_tutorial);
            areaO= (TextView) itemView.findViewById(R.id.text_area_uno_tutorial);
            areaR= (TextView) itemView.findViewById(R.id.text_area_dos_tutorial);
            date = (TextView) itemView.findViewById(R.id.text_date_tutorial);
        }
    }
}
