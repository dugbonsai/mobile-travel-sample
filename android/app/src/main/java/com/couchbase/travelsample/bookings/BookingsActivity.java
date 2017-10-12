package com.couchbase.travelsample.bookings;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.couchbase.travelsample.R;
import com.couchbase.travelsample.hotels.HotelsActivity;
import com.couchbase.travelsample.searchflight.SearchFlightActivity;
import com.couchbase.travelsample.util.ResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookingsActivity extends AppCompatActivity implements BookingsContract.View {

    private RecyclerView mRecyclerView;
    private BookingsContract.UserActionsListener mActionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_search_flights);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchFlightActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView = findViewById(R.id.bookingsList);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);

        ResultAdapter mResultAdapter = new ResultAdapter(new ArrayList<String>(), android.R.layout.simple_selectable_list_item);
        mResultAdapter.setOnItemClickListener(new ResultAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Log.d("App", String.valueOf(position));
            }
        });
        mRecyclerView.setAdapter(mResultAdapter);

        mActionListener = new BookingsPresenter(this);
        mActionListener.fetchUserBookings();
    }

    public void onHotelsTapped(View view) {
        Intent intent = new Intent(getApplicationContext(), HotelsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showBookings(List<String> bookings) {
        ResultAdapter adapter = new ResultAdapter(bookings, android.R.layout.simple_selectable_list_item);
        adapter.setOnItemClickListener(new ResultAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.invalidate();
    }

    public void onFlightSearchTap(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchFlightActivity.class);
        startActivity(intent);
    }
}
