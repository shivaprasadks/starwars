package com.think.starwars.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.think.starwars.R;
import com.think.starwars.model.StarShipData;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

/**
 * Created by Super on 06-10-2016.
 */

public class StarShipAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<StarShipData> data = Collections.emptyList();
    private StarShipData current;
    int currentPos = 0;

    //constructor
    public StarShipAdapter(Context context, List<StarShipData> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_shipdata, parent, false);
        StarShipAdapter.MyHolder holder = new StarShipAdapter.MyHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Get current position of item in recyclerview to bind data and assign values from list
        StarShipAdapter.MyHolder myHolder = (StarShipAdapter.MyHolder) holder;
        current = data.get(position);
        myHolder.ship_name.setText(current.ship_name);
        myHolder.film_name.setText("Film: " + current.movie_name);
        if (current.ship_cost.equals("0")) {
            myHolder.ship_cost.setText("Cost: " + "unknown");
        } else {
            myHolder.ship_cost.setText("Cost: " + current.ship_cost);
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ship_name, film_name, ship_cost;

        private AdapterView.OnItemClickListener listener;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            ship_name = (TextView) itemView.findViewById(R.id.Ship_name);
            film_name = (TextView) itemView.findViewById(R.id.Film_name);
            ship_cost = (TextView) itemView.findViewById(R.id.Ship_cost);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int a = getAdapterPosition();
            StarShipData current = data.get(a);

        }
    }


}
