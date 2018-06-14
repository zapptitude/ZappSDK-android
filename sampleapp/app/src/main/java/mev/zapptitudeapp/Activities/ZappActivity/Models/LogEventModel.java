package mev.zapptitudeapp.Activities.ZappActivity.Models;

import com.google.gson.Gson;

import java.util.Date;

/**
 * Created by andrew on 11.04.16.
 */
public class LogEventModel {

    String event = "";
    Date timeStamp = new Date();
    String type = "Event";

    public LogEventModel(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
