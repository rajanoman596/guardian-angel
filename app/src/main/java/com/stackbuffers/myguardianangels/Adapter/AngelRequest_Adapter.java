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

public class AngelRequest_Adapter extends RecyclerView.Adapter<AngelRequest_Adapter.ViewHolder> {
    List<AngelData> list;
    Context context;
    UpdateListener updateListener;
    DeleteListener deleteListener;

    public AngelRequest_Adapter(List<AngelData> list, Context context, UpdateListener updateListener, DeleteListener deleteListener) {
        this.list = list;
        this.context = context;
        this.updateListener = updateListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_request_layout, parent, false);
        return new AngelRequest_Adapter.ViewHolder(view);
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
        ImageView icon,delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.name_tv);
            email_tv = itemView.findViewById(R.id.email_tv);
            relation_tv = itemView.findViewById(R.id.relation_tv);
            icon = itemView.findViewById(R.id.icon);
            delete = itemView.findViewById(R.id.delete);

            icon.setOnClickListener(v -> {

          updateListener.acceptRequest(getAdapterPosition());

            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListener.deleteRequest(getAdapterPosition());
                }
            });




        }
    }
}
