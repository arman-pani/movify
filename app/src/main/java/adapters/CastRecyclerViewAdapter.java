package adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.movieapp.R;

import java.util.List;

import javax.sql.DataSource;

import models.CastModel;

public class CastRecyclerViewAdapter extends  RecyclerView.Adapter<CastRecyclerViewAdapter.CastViewHolder>{

    private Context context;

    private List<CastModel> castList;

    public CastRecyclerViewAdapter(List<CastModel> castList, Context context){
        this.castList = castList;
        this.context = context;
    }

    public  static class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView castNameTextView;
        TextView characterNameTextView;

        TextView initialsPlaceholder;

        public CastViewHolder(View itemView){
            super(itemView);
            profileImageView =  itemView.findViewById(R.id.profileImageView);
            castNameTextView = itemView.findViewById(R.id.castNameTextView);
            characterNameTextView = itemView.findViewById(R.id.characterNameTextView);
            initialsPlaceholder = itemView.findViewById(R.id.initialsPlaceholder);
        }

    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cast_card, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        CastModel castModel = castList.get(position);
        holder.castNameTextView.setText(castModel.getName());
        holder.characterNameTextView.setText(castModel.getCharacter());
        holder.initialsPlaceholder.setText(castModel.getName());
        TextView initials = holder.initialsPlaceholder;

        initials.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(castModel.getProfilePath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model,
                                                   Target<Drawable> target, @NonNull com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        initials.setVisibility(View.GONE);
                        return false;
                    }


                })
                .into(holder.profileImageView);

    }

    @Override
    public int getItemCount() {
        return castList.size();
    }
}
