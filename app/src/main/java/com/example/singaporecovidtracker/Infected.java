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
    private String mStatus;
    private String mDateContaminated;
    private String mDateDischarged;
    private String mImported;
    private String mPlace;

    private Infected(String id, String hospital, String imported, String place, String age, String gender, String nationality, String status, String dateContaminated, String dateDischarged) {
        mId = id;
        mHospital = hospital;
        mAge = age;
        mNationality = nationality;
        mGender = gender;
        mStatus = status;
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
    String getStatus() {
        return mStatus;
    }
    String getDateContaminated() {
        Date df = new java.util.Date(Long.parseLong(mDateContaminated));
        String sd = new SimpleDateFormat("dd-MM-yyyy").format(df);
        return sd;
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
                Infected.add(new Infected(cases.getJSONObject(i).getJSONObject("attributes").get("Case_ID").toString(),
                        cases.getJSONObject(i).getJSONObject("attributes").get("Current_Lo").toString(),
                        cases.getJSONObject(i).getJSONObject("attributes").get("Imported_o").toString(),
                        cases.getJSONObject(i).getJSONObject("attributes").get("Place").toString(),
                        cases.getJSONObject(i).getJSONObject("attributes").get("Age").toString(),
                        cases.getJSONObject(i).getJSONObject("attributes").get("Gender").toString(),
                        cases.getJSONObject(i).getJSONObject("attributes").get("Nationalit").toString(),
                        cases.getJSONObject(i).getJSONObject("attributes").get("Status").toString(),
                        cases.getJSONObject(i).getJSONObject("attributes").get("Date_of_Co").toString(),
                        cases.getJSONObject(i).getJSONObject("attributes").get("Date_of_Di").toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return Infected;
    }
}