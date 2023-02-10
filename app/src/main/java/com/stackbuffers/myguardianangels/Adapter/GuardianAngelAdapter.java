package com.stackbuffers.myguardianangels.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stackbuffers.myguardianangels.Models.AngelData;
import com.stackbuffers.myguardianangels.Models.DeleteListener;
import com.stackbuffers.myguardianangels.Models.UpdateListener;
import com.stackbuffers.myguardianangels.R;

import java.util.List;

public class GuardianAngelAdapter extends RecyclerView.Adapter<GuardianAngelAdapter.ViewHolder> {


    List<AngelData> list;
    Context context;
    DeleteListener deleteListener;
    UpdateListener updateListener;
    public GuardianAngelAdapter(List<AngelData> list, Context context, DeleteListener deleteListener, UpdateListener updateListener) {
        this.list = list;
        this.context = context;
        this.deleteListener = deleteListener;
        this.updateListener = updateListener;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guardian_angel_layout, parent, false);
        return new GuardianAngelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name_tv.setText(list.get(position).getName());
        holder.email_tv.setText(list.get(position).getEmail());
        holder.relation_tv.setText(list.get(position).getAngel_relation());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_tv,email_tv,relation_tv;
        ImageView delete,update;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.name_tv);
            email_tv = itemView.findViewById(R.id.email_tv);
            relation_tv = itemView.findViewById(R.id.relation_tv);
            update = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(v -> {

                deleteListener.deleteRequest(getAdapterPosition());

            });
            update.setOnClickListener(v -> {
                updateListener.acceptRequest(getAdapterPosition());

            });
        }
    }
}
