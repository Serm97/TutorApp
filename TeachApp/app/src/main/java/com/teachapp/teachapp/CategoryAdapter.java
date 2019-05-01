package com.teachapp.teachapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<Category> categoryList;

    public CategoryAdapter(Context mContext,List<Category> categoryList) {
        this.mContext = mContext;
        this.categoryList = LoadIcons(categoryList);
    }

    private List<Category> LoadIcons(List<Category> categoryList) {
        for(Category cat : categoryList){
         switch (cat.getName().toLowerCase()){
             case "algebra":
                 cat.setIcon(R.drawable.algebra);
                 break;
             case "fisica":
                 cat.setIcon(R.drawable.fisica);
                 break;
             case "calculo":
                 cat.setIcon(R.drawable.calculo);
                 break;
             case "idiomas":
                 cat.setIcon(R.drawable.idiomas);
                 break;
             case "psicologia":
                 cat.setIcon(R.drawable.psicologia);
                 break;
             case "artes":
                 cat.setIcon(R.drawable.arte);
                 break;
             case "otro":
                 cat.setIcon(R.drawable.otro);
                 break;
             default:
                 break;
         }

        }
        return categoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_category,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.titleCategory.setText(categoryList.get(position).getName());
        int nareas = categoryList.get(position).getAreas() != null
                ? categoryList.get(position).getAreas().size():0;
        myViewHolder.contAreas.setText(String.valueOf(nareas));
        myViewHolder.iconView.setImageResource(this.categoryList.get(position).getIcon());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleCategory;
        TextView contAreas;
        ImageView iconView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleCategory = (TextView) itemView.findViewById(R.id.title_category);
            contAreas = (TextView) itemView.findViewById(R.id.count_areas);
            iconView = (ImageView) itemView.findViewById(R.id.icon_category);
        }
    }
}
