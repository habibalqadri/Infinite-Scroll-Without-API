package com.codepolitan.infinitescrollandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.codepolitan.infinitescrollandroid.adapter.ContactAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> dataContacts = new ArrayList<>();
    private ContactAdapter contactAdapter = new ContactAdapter();
    private boolean isScroll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvContact = findViewById(R.id.rvContact);

        fetchData(0);

        //layoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvContact.setLayoutManager(linearLayoutManager);
        rvContact.setAdapter(contactAdapter);

        //ketika recycler view di scroll
        rvContact.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final int countItems = linearLayoutManager.getItemCount();
                int currentItems = linearLayoutManager.getChildCount();
                int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
                int totalScrollItem = currentItems + firstVisiblePosition;

                if (isScroll && totalScrollItem >= countItems){
                    isScroll = false;
                    contactAdapter.addDataLoading();

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            contactAdapter.removeDataLoading();
                            fetchData(countItems);
                            isScroll = true;
                        }
                    }, 2000);

                }


            }
        });

    }

    private void fetchData(int countItems) {
        if (dataContacts.size() > 0){
            dataContacts.clear();
        }

        for (int i = countItems; i < countItems + 15; i++){
            dataContacts.add(i + 1 + ". Jhon Doe");
        }
        contactAdapter.addDataContact(dataContacts);
    }


}