package com.bitgymup.gymup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.bitgymup.gymup.users.UserHome;

import java.util.ArrayList;
import java.util.List;

public class GymList extends AppCompatActivity {

    List<Gym> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_list);

        loadGym();

    }

    public void Siguiente (View view){
        Intent goHome = new Intent(getApplicationContext(), UserHome.class);
        startActivity(goHome);
    }

    public void loadGym(){
        elements = new ArrayList<>();
        elements.add(new Gym(1, "Juventus", "hola@gmail.com", "123456789","096456132",  123456789));
        elements.add(new Gym(2,"ACJ","adios@gmail.com", "123456789","096456132",   123456789));
        elements.add(new Gym(3, "Neptuno","Neptuno@gmail.com", "123456789","096456132",   123456789));
        elements.add(new Gym(4,"Malvin", "Malvin@gmail.com", "123456789","096456132",  123456789));
        elements.add(new Gym(5, "Bigua","Bigua@gmail.com", "096456132", "123456789",  123456789));
        elements.add(new Gym(6, "Aguada", "Aguada@gmail.com", "096456132","123456789", 123456789));
        elements.add(new Gym(7, "Rentistas", "Rentistas@gmail.com", "123456789", "096456132", 123456789));

        GymListAdapter listAdapter = new GymListAdapter(elements, this);
        RecyclerView reciclerView = findViewById(R.id.recicler);
        reciclerView.setHasFixedSize(true);
        reciclerView.setLayoutManager(new LinearLayoutManager(this));
        reciclerView.setAdapter(listAdapter);
    }
}