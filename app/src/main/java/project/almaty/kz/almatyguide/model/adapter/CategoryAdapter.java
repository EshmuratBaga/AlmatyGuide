package project.almaty.kz.almatyguide.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.almaty.kz.almatyguide.R;
import project.almaty.kz.almatyguide.model.models.CategoryModel;
import project.almaty.kz.almatyguide.model.screen.around_me.AroundMeActivity;
import project.almaty.kz.almatyguide.model.utils.Constants;

/**
 * Created by Andrey on 3/20/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<CategoryModel> models;

    public CategoryAdapter(Context context, List<CategoryModel> models) {
        this.context = context;
        this.models = models;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView cardView;
        private ImageView imageView;
        private TextView title;
        private String typePlace;
        public CategoryViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cv);
            imageView = (ImageView) itemView.findViewById(R.id.img_category);
            title = (TextView) itemView.findViewById(R.id.txt_title_category);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, AroundMeActivity.class);
            intent.putExtra(Constants.TYPE_PLACE,typePlace);
            context.startActivity(intent);
        }
    }

    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, int position) {
        Picasso.with(context).load(models.get(position).getImg()).centerCrop().fit().into(holder.imageView);
        holder.title.setText(models.get(position).getIconName());
        holder.typePlace = models.get(position).getTypePlaces();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
