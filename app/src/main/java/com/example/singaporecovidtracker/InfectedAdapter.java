package com.example.singaporecovidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InfectedAdapter extends RecyclerView.Adapter<InfectedAdapter.ViewHolder> {
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView, hospitalTextView, importedTextView, placeTextView, ageTextView, genderTextView, nationalityTextView, statusTextView, docTextView, dodTextView;

        ViewHolder(View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.infected_id);
            hospitalTextView = itemView.findViewById(R.id.infected_hospital);
            importedTextView = itemView.findViewById(R.id.infected_imported);
            placeTextView = itemView.findViewById(R.id.infected_place);
            ageTextView = itemView.findViewById(R.id.infected_age);
            genderTextView = itemView.findViewById(R.id.infected_gender);
            nationalityTextView = itemView.findViewById(R.id.infected_nationality);
            statusTextView = itemView.findViewById(R.id.infected_status);
            docTextView = itemView.findViewById(R.id.infected_doc);
            dodTextView = itemView.findViewById(R.id.infected_dod);
        }
    }

    private List<Infected> mInfected;

    InfectedAdapter(List<Infected> Infected, Context context) {
        mInfected = Infected;
        this.context=context;
    }

    @Override
    public InfectedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View InfectedView = inflater.inflate(R.layout.item_infected, parent, false);

        // Return a new holder instance
        return new ViewHolder(InfectedView);
    }

    @Override
    public void onBindViewHolder(InfectedAdapter.ViewHolder viewHolder, int position) {
        Infected Infected = mInfected.get(position);

        TextView idtv = viewHolder.idTextView;
        idtv.setText(Infected.getId());
        TextView hospitaltv = viewHolder.hospitalTextView;
        hospitaltv.setText(context.getResources().getString(R.string.Hospital) + Infected.getHospital());
        TextView importedtv = viewHolder.importedTextView;
        importedtv.setText(context.getResources().getString(R.string.Type) + Infected.getImported());
        TextView placetv = viewHolder.placeTextView;
        placetv.setText(context.getResources().getString(R.string.Placesvisited) + Infected.getPlace());
        TextView agetv = viewHolder.ageTextView;
        agetv.setText(context.getResources().getString(R.string.Age) + Infected.getAge());
        TextView gendertv = viewHolder.genderTextView;
        gendertv.setText(context.getResources().getString(R.string.Gender) + Infected.getGender());
        TextView nationalitytv = viewHolder.nationalityTextView;
        nationalitytv.setText(context.getResources().getString(R.string.Nationality) + Infected.getNationality());
        TextView statustv = viewHolder.statusTextView;
        statustv.setText(Infected.getStatus());
        TextView doctv = viewHolder.docTextView;
        doctv.setText(context.getResources().getString(R.string.Doc) + Infected.getDateContaminated());
        TextView dodtv = viewHolder.dodTextView;
        dodtv.setText(context.getResources().getString(R.string.Dod) + Infected.getDateDischarged());
    }

    @Override
    public int getItemCount() {
        return mInfected.size();
    }
}
