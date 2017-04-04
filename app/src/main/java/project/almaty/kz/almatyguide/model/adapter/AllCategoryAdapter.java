package project.almaty.kz.almatyguide.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import project.almaty.kz.almatyguide.R;
import project.almaty.kz.almatyguide.model.models.places.ResultPlaces;
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

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txtTitle;

        public MyHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.img_category_in);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title_category_in);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (places.get(position).getPhotoPlaces().size() != 0){
            Log.d("dddd","" + 123);
            Picasso.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=190&photoreference="
                    + places.get(position).getPhotoPlaces().get(0).getPhotoReference() + "&sensor=true&key=" + Constants.apiKey).centerCrop().fit().into(holder.imageView);
        }else {
            Log.d("dddd","" + 123456);
            Picasso.with(context).load(R.drawable.bar).centerCrop().fit().into(holder.imageView);
        }
        holder.txtTitle.setText(places.get(position).getName());
        Log.i("ssss", "" + 123);
        calculationByDistance(Constants.UPlat,Constants.UPlng,places.get(position).getGeometry().getLocation().getLat(),places.get(position).getGeometry().getLocation().getLng());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public double calculationByDistance(String startLng, String startLon, Double endLng, Double endLon) {
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
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("ssss", "val:" + valueResult + "   KM:" + kmInDec
                + " Meter:" + meterInDec + " Radius * c " + Radius * c);

        return Radius * c;
    }
}
