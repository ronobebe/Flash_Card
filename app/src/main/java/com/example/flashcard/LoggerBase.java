package com.example.flashcard;

import android.util.Log;

public class LoggerBase  {

    LoggerBase.Logs log;



    LoggerBase(LoggerBase.Logs log){
        this.log=log;
    }



    public  interface Logs{

        public  void CreateLog(String tag, String message);
    }



    public  void printLog(){


    }

}
