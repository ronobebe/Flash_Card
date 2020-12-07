package com.example.flashcard;

public enum CategoryData {



      Algorithm("Algorithm"),
      Syntax("Syntax"),
      General("General");


     private String data;
      CategoryData cd;

    CategoryData(String categoryType){
        this.data=categoryType;
    }

    public String getData() {
        return data;
    }


}
