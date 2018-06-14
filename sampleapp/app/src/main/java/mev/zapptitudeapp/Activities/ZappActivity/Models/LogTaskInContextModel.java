package mev.zapptitudeapp.Activities.ZappActivity.Models;

import com.google.gson.Gson;

import java.util.Date;

/**
 * Created by andrew on 11.04.16.
 */
public class LogTaskInContextModel {

    String task = "";
    String context = "";
    Date timeStamp = new Date();
    String type = "Begin task";

    public LogTaskInContextModel(String task, String context) {
        this.task = task;
        this.context = context;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
