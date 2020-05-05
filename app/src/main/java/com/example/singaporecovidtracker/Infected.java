package com.example.singaporecovidtracker;

import org.json.JSONArray;
import org.json.JSONException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class Infected {
    private String mId;
    private String mHospital;
    private String mAge;
    private String mNationality;
    private String mGender;
    private String mDeath;
    private String mDateContaminated;
    private String mDateDischarged;
    private String mImported;
    private String mPlace;

    private Infected(String id, String hospital, String imported, String place, String age, String gender, String nationality, String death, String dateContaminated, String dateDischarged) {
        mId = id;
        mHospital = hospital;
        mAge = age;
        mNationality = nationality;
        mGender = gender;
        mDeath = death;
        mDateContaminated = dateContaminated;
        mDateDischarged = dateDischarged;
        mImported = imported;
        mPlace = place;
    }

    String getId() {
        return mId;
    }
    String getHospital() {
        return mHospital;
    }
    String getAge() {
        return mAge;
    }
    String getNationality() {
        return mNationality;
    }
    String getGender() {
        return mGender;
    }
    String getDeath() {
        return mDeath;
    }
    String getDateContaminated() {
        return mDateContaminated;
    }
    String getDateDischarged() {
        return mDateDischarged;
    }
    String getImported() {
        return mImported;
    }
    String getPlace() {
        return mPlace;
    }

    static ArrayList<Infected> createInfectedList(JSONArray cases) {
        ArrayList<Infected> Infected = new ArrayList<>();

        for (int i = 0; i < cases.length(); i++) {
            try {
                Infected.add(
                        new Infected(
                                cases.getJSONObject(i).getJSONObject("properties").get("id").toString(),
                                cases.getJSONObject(i).getJSONObject("properties").get("hospital").toString(),
                                cases.getJSONObject(i).getJSONObject("properties").get("transmissionSource").toString(),
                                cases.getJSONObject(i).getJSONObject("properties").get("placesVisited").toString(),
                                cases.getJSONObject(i).getJSONObject("properties").get("age").toString(),
                                cases.getJSONObject(i).getJSONObject("properties").get("gender").toString(),
                                cases.getJSONObject(i).getJSONObject("properties").get("nationality").toString(),
                                cases.getJSONObject(i).getJSONObject("properties").get("death").toString(),
                                cases.getJSONObject(i).getJSONObject("properties").get("confirmed").toString(),
                                cases.getJSONObject(i).getJSONObject("properties").get("discharged").toString()
                        )
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return Infected;
    }
}