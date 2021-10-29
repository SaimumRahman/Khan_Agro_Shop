package khan.solution.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import khan.solution.Activities.AdminOrderDetailsActivity;
import khan.solution.Model.PayBill;
import khan.solution.Model.Products;
import khan.solution.databinding.NotificationLayoutBinding;
import khan.solution.databinding.RecylerLayoutBinding;

public class AdapterOrderShippedAdmin extends RecyclerView.Adapter<AdapterOrderShippedAdmin.ViewHolder> {

    private Context context;
    private List<Products> products;

    public AdapterOrderShippedAdmin(Context context, List<Products> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public AdapterOrderShippedAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecylerLayoutBinding binding=RecylerLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new AdapterOrderShippedAdmin.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrderShippedAdmin.ViewHolder holder, int position) {

        Products product=products.get(position);
        holder.binding.priceTv.setText(product.getPrice());
        holder.binding.quantityTv.setText(product.getQuantity());
        holder.binding.detailsTvs.setText(product.getDetails());
        Glide.with(context).load(product.getImageuri()).into(holder.binding.imageViewRecycler);

        holder.itemView.setOnClickListener(v ->{
            Intent intent=new Intent(context, AdminOrderDetailsActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private RecylerLayoutBinding binding;

        public ViewHolder(@NonNull RecylerLayoutBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}

