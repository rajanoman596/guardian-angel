package com.stackbuffers.myguardianangels.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stackbuffers.myguardianangels.Models.myEvidence.MyEvidence;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.interfaces.EvdClickListener;

import java.util.ArrayList;

public class MyEvedenceAdapter extends RecyclerView.Adapter<MyEvedenceAdapter.ViewHolder> {

    ArrayList<MyEvidence> myEvidenceArrayList;
    EvdClickListener evdClickListener;

    public MyEvedenceAdapter(ArrayList<MyEvidence> myEvidenceArrayList, EvdClickListener evdClickListener) {
        this.myEvidenceArrayList = myEvidenceArrayList;
        this.evdClickListener = evdClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View binding = LayoutInflater.from(parent.getContext()).inflate(R.layout.evedence_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyEvidence evidence = myEvidenceArrayList.get(position);
        holder.title.setText(evidence.file);
        holder.type.setText(evidence.format.equals("mp3") ? "Audio" : "Video");

        holder.delete.setOnClickListener(v -> evdClickListener.onDeleteClick(evidence,position));

        holder.play.setOnClickListener(v -> evdClickListener.onPlayClick(evidence));
    }

    @Override
    public int getItemCount() {
        return myEvidenceArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, type;
        ImageView play, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            type = itemView.findViewById(R.id.type);
            play = itemView.findViewById(R.id.playBtn);
            delete = itemView.findViewById(R.id.deleteBtn);


        }
    }
}
