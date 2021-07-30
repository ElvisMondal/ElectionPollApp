package com.assignment4;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewHolder extends RecyclerView.ViewHolder {
    TextView Position,Name,subpos;


    viewHolder( View view) {
        super(view);

       Position=view.findViewById(R.id.position);
       Name=view.findViewById(R.id.name);
        subpos=view.findViewById(R.id.sPos);

    }
}
