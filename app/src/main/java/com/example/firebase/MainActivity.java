package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    PersonAdapter adapter;
    FloatingActionButton btnfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recycler);
        btnfab=findViewById(R.id.fab);


        btnfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,insert.class);
                startActivity(intent);
            }
        });
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Person");

        FirebaseRecyclerOptions<PersonModel> options
                new FirebaseRecyclerOptions.Builder<PersonModel>()
                        .setQuery(query, PersonModel.class)
                        .build();

        adapter = new PersonAdapter(options, this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem=menu.findItem(R.id.search);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                setSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                setSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public void setSearch(String str)
    {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Person")
                .orderByChild("name")
                .startAt(str)
                .endAt(str+"~");
        FirebaseRecyclerOptions<PersonModel> options =
                new FirebaseRecyclerOptions.Builder<PersonModel>()
                        .setQuery(query, PersonModel.class)
                        .build();

        adapter=new PersonAdapter(options,MainActivity.this);
        adapter.startListening();
        recycler.setAdapter(adapter);
    }
}