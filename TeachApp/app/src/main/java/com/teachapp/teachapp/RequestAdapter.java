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
import android.widget.TextView;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolderRequest> {

    public ArrayList<Request> lstRequest;
    public Context vContext;

    public RequestAdapter(ArrayList<Request> lstRequest, Context vContext) {
        this.lstRequest = lstRequest;
        this.vContext = vContext;
    }

    @Override
    public ViewHolderRequest onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_request,null,false);
        return new ViewHolderRequest(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRequest viewHolderRequest, int i) {
        final Request req = lstRequest.get(i);
        viewHolderRequest.areaO.setText("Conocimiento ofrecido: "+req.getAreaO().getName());
        viewHolderRequest.areaR.setText("Conocimiento Solicitado: "+req.getAreaS().getName());
        if (req.getState() == 0){
            viewHolderRequest.btnSearch.setVisibility(View.VISIBLE);
        }else{
            viewHolderRequest.btnSearch.setVisibility(View.GONE);
        }
        viewHolderRequest.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MatchFragment();
                Bundle args = new Bundle();
                args.putSerializable("requestMatch",req);
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
        return lstRequest.size();
    }

    public class ViewHolderRequest extends RecyclerView.ViewHolder {

        public TextView areaO,areaR;
        public Button btnSearch;

        public ViewHolderRequest(@NonNull View itemView) {
            super(itemView);
            areaO= (TextView) itemView.findViewById(R.id.text_area_offered);
            areaR= (TextView) itemView.findViewById(R.id.text_area_request);
            btnSearch= (Button) itemView.findViewById(R.id.button_view_request);
        }
    }
}
