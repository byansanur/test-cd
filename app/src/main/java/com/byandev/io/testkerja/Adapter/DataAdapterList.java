package com.byandev.io.testkerja.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;


import java.util.ArrayList;

import com.byandev.io.testkerja.Model.RespData;
import com.byandev.io.testkerja.R;
import com.byandev.io.testkerja.Helper.SqliteDatabase;

public class DataAdapterList extends RecyclerView.Adapter<ViewHolder> implements Filterable{

    private Context context;
    private ArrayList<RespData> listContacts;
    private ArrayList<RespData> mArrayList;

    private SqliteDatabase mDatabase;

    public DataAdapterList(Context context, ArrayList<RespData> listContacts) {
        this.context = context;
        this.listContacts = listContacts;
        this.mArrayList=listContacts;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RespData contacts = listContacts.get(position);


        holder.tvNoconte.setText(contacts.getNo_container());
        holder.tvSize.setText(contacts.getSize());
        holder.tvType.setText(contacts.getType());
        holder.tvSlot.setText(contacts.getSlots());
        holder.tvRow.setText(contacts.getRows());
        holder.tvTier.setText(contacts.getTier());

        holder.editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(contacts);
            }
        });

        holder.deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setTitle("Hapus data ?");
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.deleteContact(contacts.getId());

                        //refresh the activity page.
                        ((Activity)context).finish();
                        context.startActivity(((Activity) context).getIntent());
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    listContacts = mArrayList;
                } else {

                    ArrayList<RespData> filteredList = new ArrayList<>();

                    for (RespData contacts : mArrayList) {

                        if (contacts.getNo_container().toLowerCase().contains(charString)) {

                            filteredList.add(contacts);
                        }
                    }

                    listContacts = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listContacts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listContacts = (ArrayList<RespData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return listContacts.size();
    }


    private void editTaskDialog(final RespData contacts){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_contact_layout, null);

        final EditText conte = (EditText)subView.findViewById(R.id.etNoConte);
        final EditText size = (EditText)subView.findViewById(R.id.etSize);
        final EditText type = (EditText)subView.findViewById(R.id.etType);
        final EditText slot = (EditText)subView.findViewById(R.id.etSlot);
        final EditText row = (EditText)subView.findViewById(R.id.etRow);
        final EditText tier = (EditText)subView.findViewById(R.id.etTier);

        if(contacts != null){
            conte.setText(contacts.getNo_container());
            size.setText(String.valueOf(contacts.getSize()));
            type.setText(String.valueOf(contacts.getType()));
            slot.setText(String.valueOf(contacts.getSlots()));
            row.setText(String.valueOf(contacts.getRows()));
            tier.setText(String.valueOf(contacts.getTier()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit contact");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String contes = conte.getText().toString();
                final String sizes = size.getText().toString();
                final String types = type.getText().toString();
                final String slots = slot.getText().toString();
                final String rows = row.getText().toString();
                final String tiers = tier.getText().toString();

                if(TextUtils.isEmpty(contes)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateContacts(new RespData(contacts.getId(), contes, sizes, types, slots, rows, tiers));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
