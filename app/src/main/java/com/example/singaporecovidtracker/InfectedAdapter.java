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
        TextView idTextView, importedTextView, deathTextView, ageTextView, genderTextView, nationalityTextView, statusTextView, docTextView, dodTextView;

        ViewHolder(View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.infected_id);
            importedTextView = itemView.findViewById(R.id.infected_imported);
            deathTextView = itemView.findViewById(R.id.infected_death);
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
        TextView importedtv = viewHolder.importedTextView;
        importedtv.setText(context.getResources().getString(R.string.Type) + Infected.getImported());
        TextView deathtv = viewHolder.deathTextView;
        deathtv.setText(context.getResources().getString(R.string.Dodeath) + System.getProperty("line.separator") + Infected.getDeath());
        TextView agetv = viewHolder.ageTextView;
        agetv.setText(context.getResources().getString(R.string.Age) + Infected.getAge());
        TextView gendertv = viewHolder.genderTextView;
        gendertv.setText(context.getResources().getString(R.string.Gender) + Infected.getGender());
        TextView nationalitytv = viewHolder.nationalityTextView;
        nationalitytv.setText(context.getResources().getString(R.string.Nationality) + Infected.getNationality());
        TextView statustv = viewHolder.statusTextView;
        if(!Infected.getDeath().equals(""))
            statustv.setText("Dead");
        else if (!Infected.getDateDischarged().equals(""))
            statustv.setText("Discharged");
        else
            statustv.setText("Active");
        TextView doctv = viewHolder.docTextView;
        doctv.setText(context.getResources().getString(R.string.Doc) + System.getProperty("line.separator") + Infected.getDateContaminated());
        TextView dodtv = viewHolder.dodTextView;
        dodtv.setText(context.getResources().getString(R.string.Dod) + System.getProperty("line.separator") + Infected.getDateDischarged());
    }

    @Override
    public int getItemCount() {
        return mInfected.size();
    }
}
