package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;

import java.util.List;

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

        public CastViewHolder(View itemView){
            super(itemView);
            profileImageView =  itemView.findViewById(R.id.profileImageView);
            castNameTextView = itemView.findViewById(R.id.castNameTextView);
            characterNameTextView = itemView.findViewById(R.id.characterNameTextView);
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

        String fullUrl = "https://image.tmdb.org/t/p/w500" + castModel.getProfilePath();

        Glide.with(context)
                .load(fullUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.profileImageView);

    }

    @Override
    public int getItemCount() {
        return castList.size();
    }
}
