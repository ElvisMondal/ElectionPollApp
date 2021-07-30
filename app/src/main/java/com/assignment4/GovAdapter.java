package com.assignment4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class GovAdapter extends RecyclerView.Adapter<viewHolder> {

    private List<OfficialClass> rankList;
    private MainActivity mainActiv;

    public GovAdapter(List<OfficialClass> rankList, MainActivity mainActiv) {
        this.rankList = rankList;
        this.mainActiv = mainActiv;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.political_official_view, parent, false);

        itemView.setOnClickListener(mainActiv);
        itemView.setOnLongClickListener(mainActiv);

        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        OfficialClass edit =rankList.get(position);

         holder.Position.setText(edit.getPosition());
         holder.Name.setText(edit.getNames());
         holder.subpos.setText(mainActiv.getString(R.string.Position, "(",edit.getParty() , ")"));
    }

    @Override
    public int getItemCount() {
        return  rankList.size();
    }
}
