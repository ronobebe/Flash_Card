package com.example.flashcard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.DataBase.QuestionStatus;

import java.util.ArrayList;

public class MainListRecycler extends RecyclerView.Adapter <MainListRecycler.mainListViewHolder>implements QuestionStatus {


    ArrayList<FlashCardDetails> flashCardDetailsArrayList;

    RecyclerOnClick recyclerOnClick;

    public MainListRecycler(ArrayList<FlashCardDetails> flashCardDetailsArrayList,  RecyclerOnClick recyclerOnClick) {
        this.flashCardDetailsArrayList = flashCardDetailsArrayList;
        this.recyclerOnClick=recyclerOnClick;
    }

    @NonNull
    @Override
    public mainListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.main_page_list,parent,false);
        return new mainListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mainListViewHolder holder, int position) {

        if(flashCardDetailsArrayList.size()>0) {
            FlashCardDetails flashCardDetails=flashCardDetailsArrayList.get(position);

            holder.questions.setText(flashCardDetails.getQuestion());

            if(flashCardDetails.getLastVisitDate().equals(""))
                holder.lastVisitDate.setText("LV : "+"NO Visit");

            else

            holder.lastVisitDate.setText("LV : "+flashCardDetails.getLastVisitDate());


            holder.itemCount.setText("" + (position + 1));

            setQuestionStatus(holder.itemCount,flashCardDetails);


        }
    }

    @Override
    public int getItemCount() {
        return flashCardDetailsArrayList.size();
    }

    public  class mainListViewHolder extends RecyclerView.ViewHolder{


        TextView questions,itemCount,lastVisitDate;

        public mainListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemCount=itemView.findViewById(R.id.itemCount);

            questions=itemView.findViewById(R.id.questions);

            lastVisitDate=itemView.findViewById(R.id.lastVisitDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerOnClick.onClickRecycler(getAdapterPosition(),flashCardDetailsArrayList.get(getAdapterPosition()));
                }
            });
        }
    }

    void setQuestionStatus(View questionStatus, FlashCardDetails flashCardDetails){

        if(flashCardDetails.getQuestionStatus()=="" )
            questionStatus.setBackgroundResource(R.drawable.list_number_circle);
        else if(flashCardDetails.getQuestionStatus().equals(QuestionKnow ))
            questionStatus.setBackgroundResource(R.drawable.list_number_circle_known);
        else if(Integer.parseInt(flashCardDetails.getTotalVisit())>5 )
            questionStatus.setBackgroundResource(R.drawable.list_number_circle_tuf);
        else
            questionStatus.setBackgroundResource(R.drawable.list_number_circle);



    }



    public  interface RecyclerOnClick{
        public  void onClickRecycler(int pos ,FlashCardDetails flashCardDetails );
    }
}
