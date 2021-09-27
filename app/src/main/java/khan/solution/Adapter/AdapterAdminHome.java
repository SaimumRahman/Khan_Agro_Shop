package khan.solution.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import khan.solution.Model.DishPost;
import khan.solution.databinding.RecylerLayoutBinding;

public class AdapterAdminHome extends RecyclerView.Adapter<AdapterAdminHome.ViewHolder> {

    private Context context;
    private List<DishPost>dishPostList;

    public AdapterAdminHome(Context context, List<DishPost> dishPostList) {
        this.context = context;
        this.dishPostList = dishPostList;
    }

    @NonNull
    @Override
    public AdapterAdminHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecylerLayoutBinding binding=RecylerLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdminHome.ViewHolder holder, int position) {

        DishPost dishPost=dishPostList.get(position);
        Glide.with(context).load(dishPost.getDish_Image_Uri()).into(holder.binding.imageViewRecycler);
        holder.binding.priceTv.setText(dishPost.getDish_Price());
        holder.binding.detailsTvs.setText(dishPost.getDish_Description());
        holder.binding.quantityTv.setText(dishPost.getDish_Quantity());

    }

    @Override
    public int getItemCount() {
        return dishPostList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private RecylerLayoutBinding binding;

        public ViewHolder(@NonNull RecylerLayoutBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
