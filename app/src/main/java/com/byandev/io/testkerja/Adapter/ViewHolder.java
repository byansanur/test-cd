package com.byandev.io.testkerja.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.byandev.io.testkerja.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView tvNoconte, tvSize, tvType, tvSlot, tvRow, tvTier;
    public ImageView deleteContact;
    public  ImageView editContact;

    public ViewHolder(View itemView) {
        super(itemView);
        tvNoconte = itemView.findViewById(R.id.tvNocontainer);
        tvSize = itemView.findViewById(R.id.tvSize);
        tvType = itemView.findViewById(R.id.tvType);
        tvSlot = itemView.findViewById(R.id.tvSlot);
        tvRow = itemView.findViewById(R.id.tvRow);
        tvTier = itemView.findViewById(R.id.tvTier);

        deleteContact = itemView.findViewById(R.id.delete_contact);
        editContact = itemView.findViewById(R.id.edit_contact);
    }
}
