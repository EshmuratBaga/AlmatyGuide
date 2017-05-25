package project.almaty.kz.almatyguide.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import project.almaty.kz.almatyguide.R;
import project.almaty.kz.almatyguide.model.models.places.ResultPlaces;
import project.almaty.kz.almatyguide.model.screen.details_places.DetailsActivity;
import project.almaty.kz.almatyguide.model.utils.Constants;

/**
 * Created by Andrey on 4/1/2017.
 */

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.MyHolder>{
    private Context context;
    private List<ResultPlaces> places;
    private String imgUrl;

    public AllCategoryAdapter(Context context, List<ResultPlaces> places) {
        this.context = context;
        this.places = places;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView txtTitle;
        private TextView txtDistance;
        private CardView cardView;

        public MyHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cv_in);
            imageView = (ImageView) itemView.findViewById(R.id.img_category_in);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title_category_in);
            txtDistance = (TextView) itemView.findViewById(R.id.txt_distance_category_in);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cv_in:
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("result", places.get(getAdapterPosition()));
                    intent.putExtra("location",places.get(getAdapterPosition()).getGeometry().getLocation());
                    intent.putExtra("distance",txtDistance.getText());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (places.get(position).getPhotoPlaces() != null){
            Picasso.with(context).load(places.get(position).getPhotoPlaces().get(0).getPhotoReference()).centerCrop().fit().into(holder.imageView);
        }else {
            Picasso.with(context).load(R.drawable.bar).centerCrop().fit().into(holder.imageView);
        }
        holder.txtTitle.setText(places.get(position).getName());
        holder.txtDistance.setText(calculationByDistance(Constants.UPlat,Constants.UPlng,places.get(position).getGeometry().getLocation().getLat(),places.get(position).getGeometry().getLocation().getLng()) + " км");
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public String calculationByDistance(String startLng, String startLon, Double endLng, Double endLon) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = Double.parseDouble(startLng);
        double lat2 = endLng;
        double lon1 = Double.parseDouble(startLon);
        double lon2 = endLon;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        DecimalFormat newFormat = new DecimalFormat("0.0");
        String valueResult = newFormat.format(Radius * c);
        Log.i("ssss", "val:" + valueResult + " Radius * c " + Radius * c);

        return valueResult;
    }
}
