package com.think.starwars.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.think.starwars.MainActivity;
import com.think.starwars.R;
import com.think.starwars.model.StarShipData;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

/**
 * Created by Super on 06-10-2016.
 */

public class StarShipAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
        myHolder.film_name.setText(current.movie_name);
        if(current.ship_cost.equals("0")){
            myHolder.ship_cost.setText("unknown");
        }else{
            myHolder.ship_cost.setText(current.ship_cost);
        }



        //  myHolder.textSize.setText("Size: " + current.sizeName);
        //  myHolder.textType.setText("Category: " + current.catName);
        //  myHolder.p_price.setText("₹ " + current.price + "/-");
        //  myHolder.p_price.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        // load image into imageview using glide
       /* Glide.with(context).load(current.imaage)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(myHolder.p_image); */
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ship_name,film_name,ship_cost;

        private AdapterView.OnItemClickListener listener;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            ship_name = (TextView) itemView.findViewById(R.id.Ship_name);
            film_name = (TextView) itemView.findViewById(R.id.Film_name);
            ship_cost = (TextView) itemView.findViewById(R.id.Ship_cost);
            itemView.setOnClickListener(this);
            //  p_price.setSelected(true);
        }

        @Override
        public void onClick(View view) {
            int a = getAdapterPosition();
            StarShipData current = data.get(a);
         //   String mID = current.Ship_name;

        /*    Intent i = new Intent(context, MainActivity.class);
            i.putExtra("CATEGORY_ID", mID);
            context.startActivity(i); */
          //  Log.d("Click", "onClick " + mID + " " + mItem);

        }
    }


}
