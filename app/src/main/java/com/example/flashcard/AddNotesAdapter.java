package com.example.flashcard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.ArrayList;

public class AddNotesAdapter extends RecyclerView.Adapter<AddNotesAdapter.SubCategoryViewHolder> {
    ArrayList<String> subCategoryList;

    public AddNotesAdapter(ArrayList<String> subCategoryList ) {
        this.subCategoryList = subCategoryList;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_sub_category,parent,false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {

        if(subCategoryList.size()>0){
            holder.sub_Category.setText(""+subCategoryList.get(position));
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


        }
    }


    public  interface  RecyclerOnClick{
        public  void onClickRecycler(int pos, SubCategoryDetails o);
    }
}
