package com.example.itinventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Inventory;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{

    List<Inventory> inventoryList;
    private final AdapterButtonListner listener;

    public ListAdapter(List<Inventory> inventoryList, AdapterButtonListner listener)
    {
        this.inventoryList = inventoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.MyViewHolder holder, int position)
    {
        Inventory inventory = inventoryList.get(position);
        holder.name.setText(inventory.getName());
        holder.amount.setText(inventory.getAmount().toString());
        holder.desc.setText(inventory.getDescription());
        holder.location.setText("Location: "+inventory.getLocation());

        boolean isExpanded = inventoryList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount()
    {
        return inventoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ConstraintLayout expandableLayout;
        TextView name, amount, desc, location;
        Button plus_btn, minus_btn, delete_btn, update_btn;
        private WeakReference<AdapterButtonListner> listenerRef;

        public MyViewHolder(@NonNull View itemView, AdapterButtonListner listener)
        {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            amount = itemView.findViewById(R.id.item_amount);
            desc = itemView.findViewById(R.id.item_desc);
            location = itemView.findViewById(R.id.item_location);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            plus_btn = itemView.findViewById(R.id.plus_btn);
            minus_btn = itemView.findViewById(R.id.minus_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            update_btn = itemView.findViewById(R.id.update_btn);
            listenerRef = new WeakReference<>(listener);


            name.setOnClickListener(view -> {
                Inventory inventory = inventoryList.get(getAdapterPosition());
                inventory.setExpanded(!inventory.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
            plus_btn.setOnClickListener(view->{
                plus_btn.setFocusableInTouchMode(true);
                listenerRef.get().plusBtnClicked(getAdapterPosition());
            });
            minus_btn.setOnClickListener(view->{
                minus_btn.setFocusableInTouchMode(true);
                listenerRef.get().minusBtnClicked(getAdapterPosition());
            });
            delete_btn.setOnClickListener(view->{
                delete_btn.setFocusableInTouchMode(true);
                listenerRef.get().deleteBtnClicked(getAdapterPosition());
            });
            update_btn.setOnClickListener(view->{
                update_btn.setFocusableInTouchMode(true);
                listenerRef.get().updateBtnClicked(getAdapterPosition());
            });
        }
    }
}
