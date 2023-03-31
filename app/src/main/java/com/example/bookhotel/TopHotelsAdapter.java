package com.example.bookhotel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TopHotelsAdapter extends RecyclerView.Adapter<TopHotelsAdapter.ViewHolder> {

    Context mycontext;
    ArrayList<TopHotelsModel> topHotelsModels;

    public TopHotelsAdapter(Context mycontext, ArrayList<TopHotelsModel> topHotelsModels) {
        this.mycontext = mycontext;
        this.topHotelsModels = topHotelsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tophotelsview = LayoutInflater.from(mycontext.getApplicationContext()).inflate(R.layout.top_hotels_pager_layout, null);
        return new ViewHolder(tophotelsview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopHotelsModel model = topHotelsModels.get(position);

        holder.name.setText(model.name);
        holder.location.setText(model.location);
        holder.price.setText(model.price);
        holder.image.setImageResource(model.image);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mycontext, DetailsActivity.class);
                intent.putExtra("tophotelsname", model.getName());
                intent.putExtra("tophotelslocation", model.getLocation());
                intent.putExtra("tophotelsprice", model.getPrice());
                intent.putExtra("tophotelsimage", model.getImage());
                mycontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return topHotelsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, location, price;
        ImageView image, location_icon, fav_icon;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.top_hotels_name);
            location = itemView.findViewById(R.id.top_hotels_location);
            price = itemView.findViewById(R.id.top_hotels_price);
            image = itemView.findViewById(R.id.top_hotels_image);
            card = itemView.findViewById(R.id.top_hotels_card);

            Typeface lato_black = ResourcesCompat.getFont(mycontext, R.font.lato_black);
            Typeface lato_bold = ResourcesCompat.getFont(mycontext, R.font.lato_bold);

            name.setTypeface(lato_black);
            price.setTypeface(lato_black);
            location.setTypeface(lato_bold);

        }
    }

}
