package com.example.flashcard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {
ArrayList<SubCategoryDetails> subCategoryList;

    RecyclerOnClick recyclerOnClick;
    public SubCategoryAdapter(ArrayList<SubCategoryDetails> subCategoryList,  RecyclerOnClick recyclerOnClick) {
        this.subCategoryList = subCategoryList;
        this.recyclerOnClick=recyclerOnClick;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_sub_category,parent,false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {

        if(subCategoryList.size()>0){
            holder.sub_Category.setText(subCategoryList.get(position).getSubCategory());
        }
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public  class  SubCategoryViewHolder extends RecyclerView.ViewHolder{

        TextView sub_Category;
        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            sub_Category=itemView.findViewById(R.id.sub_Category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerOnClick.onClickRecycler(getAdapterPosition(),subCategoryList.get(getAdapterPosition()));
                }
            });

        }
    }


    public  interface  RecyclerOnClick{
        public  void onClickRecycler(int pos, SubCategoryDetails o);
    }
}
