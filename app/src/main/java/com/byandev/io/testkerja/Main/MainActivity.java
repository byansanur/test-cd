package com.byandev.io.testkerja.Main;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import com.byandev.io.testkerja.Adapter.DataAdapterList;
import com.byandev.io.testkerja.Helper.SqliteDatabase;
import com.byandev.io.testkerja.Model.RespData;
import com.byandev.io.testkerja.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SqliteDatabase mDatabase;
    private ArrayList<RespData> allContacts=new ArrayList<>();
    private DataAdapterList mAdapter;

    private TextView tvNull;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FrameLayout fLayout = findViewById(R.id.activity_to_do);

        RecyclerView contactView = findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactView.setLayoutManager(linearLayoutManager);
        contactView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(this);
        allContacts = mDatabase.listContacts();

        tvNull = findViewById(R.id.tvNull);

        if(allContacts.size() > 0){
            contactView.setVisibility(View.VISIBLE);
            tvNull.setVisibility(View.INVISIBLE);
            mAdapter = new DataAdapterList(this, allContacts);
            contactView.setAdapter(mAdapter);

        }else {
            contactView.setVisibility(View.GONE);
            tvNull.setVisibility(View.VISIBLE);
            Toast.makeText(this, "There is no data in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog();
            }
        });
    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_contact_layout, null);

        final EditText conte = subView.findViewById(R.id.etNoConte);
        final EditText size = subView.findViewById(R.id.etSize);
        final EditText type = subView.findViewById(R.id.etType);
        final EditText slot = subView.findViewById(R.id.etSlot);
        final EditText row = subView.findViewById(R.id.etRow);
        final EditText tier = subView.findViewById(R.id.etTier);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Data");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String contes = conte.getText().toString();
                final String sizes = size.getText().toString();
                final String types = type.getText().toString();
                final String slots = slot.getText().toString();
                final String rows = row.getText().toString();
                final String tiers = tier.getText().toString();

                if(TextUtils.isEmpty(contes)){
                    Toast.makeText(MainActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    RespData newContact = new RespData(contes, sizes, types, slots, rows, tiers);
                    mDatabase.addContacts(newContact);

                    finish();
                    startActivity(getIntent());
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

//        MenuItem about = menu.findItem(R.id.about);
//        about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                alertProfile();
//                return false;
//            }
//        });


        return true;
    }

    private void alertProfile() {
        Toast.makeText(this, "hahahahha", Toast.LENGTH_SHORT).show();
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter!=null)
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
