package adapters;

import android.content.Context;
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

import models.CrewModel;

public class CrewRecyclerViewAdapter extends  RecyclerView.Adapter<CrewRecyclerViewAdapter.CrewViewHolder>{


    private Context context;

    private List<CrewModel> crewList;

    public CrewRecyclerViewAdapter(List<CrewModel> crewList, Context context){
        this.crewList = crewList;
        this.context = context;
    }

    public  static class CrewViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView nameTextView;
        TextView jobTextView;

        TextView initialsPlaceholder;

        public CrewViewHolder(View itemView){
            super(itemView);
            profileImageView =  itemView.findViewById(R.id.profileImageView);
            nameTextView = itemView.findViewById(R.id.castNameTextView);
            jobTextView = itemView.findViewById(R.id.characterNameTextView);
            initialsPlaceholder = itemView.findViewById(R.id.initialsPlaceholder);

        }

    }

    @NonNull
    @Override
    public CrewRecyclerViewAdapter.CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cast_card, parent, false);
        return new CrewRecyclerViewAdapter.CrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewRecyclerViewAdapter.CrewViewHolder holder, int position) {
        CrewModel CrewModel = crewList.get(position);
        holder.nameTextView.setText(CrewModel.getName());
        holder.jobTextView.setText(CrewModel.getJob());
        holder.initialsPlaceholder.setText(CrewModel.getName());
        TextView initials = holder.initialsPlaceholder;
        initials.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(CrewModel.getProfilePath())
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
        return crewList.size();
    }
}
