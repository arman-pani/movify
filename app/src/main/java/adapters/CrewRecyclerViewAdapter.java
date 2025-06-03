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

        public CrewViewHolder(View itemView){
            super(itemView);
            profileImageView =  itemView.findViewById(R.id.profileImageView);
            nameTextView = itemView.findViewById(R.id.castNameTextView);
            jobTextView = itemView.findViewById(R.id.characterNameTextView);
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

        String fullUrl = "https://image.tmdb.org/t/p/w500" + CrewModel.getProfilePath();

        Glide.with(context)
                .load(fullUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.profileImageView);

    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }
}
