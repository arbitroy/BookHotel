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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopHotelsAdapter extends RecyclerView.Adapter<TopHotelsAdapter.ViewHolder> {

    Context mycontext;
    ArrayList<Hotel> topHotelsModels;

    public TopHotelsAdapter(Context mycontext, ArrayList<Hotel> topHotelsModels) {
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
        Hotel model = topHotelsModels.get(position);

        holder.name.setText(model.name);
        holder.location.setText(model.location);
        holder.price.setText(" Ksh "+model.price);
        // Load the image using Glide
        if (model.imageURL != null && !model.imageURL.isEmpty()) {
            Picasso.get().load(model.imageURL.get(0)).error(R.drawable.error_image).placeholder(R.drawable.placeholder_image).into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.default_image); // Set a default image if there is no image URL
        }
        holder.card.setOnClickListener(view -> {
            Intent intent = new Intent(mycontext, DetailsActivity.class);
            intent.putExtra("tophotelsname", model.getName());
            intent.putExtra("tophoteldesc",model.getDescription());
            intent.putExtra("tophotelslocation", model.getLocation());
            intent.putExtra("tophotelsprice", model.getPrice());
            intent.putExtra("tophotelsimage", model.imageURL.get(0));
            mycontext.startActivity(intent);
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
