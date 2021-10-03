package khan.solution.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import khan.solution.Model.Cart;
import khan.solution.Model.PayBill;
import khan.solution.MyFirebaseInstanceIDService;
import khan.solution.databinding.NotificationLayoutBinding;
import khan.solution.databinding.RecylerLayoutBinding;

public class AdapterPayBill extends RecyclerView.Adapter<AdapterPayBill.ViewHolder> {

    private Context context;
    private List<PayBill> payBills;

    public AdapterPayBill(Context context, List<PayBill> payBills) {
        this.context = context;
        this.payBills = payBills;
    }

    @NonNull
    @Override
    public AdapterPayBill.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationLayoutBinding binding=NotificationLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new AdapterPayBill.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPayBill.ViewHolder holder, int position) {

        PayBill payBill=payBills.get(position);
        holder.binding.nameordernot.setText(payBill.getName());
        holder.binding.emailOrdernot.setText(payBill.getEmail());
        holder.binding.phnordernot.setText(payBill.getPhone());
        holder.binding.addressOrdernot.setText(payBill.getAddress());
        holder.binding.pricetotalOrdernot.setText(payBill.getTotal_Bill());


    }

    @Override
    public int getItemCount() {
        return payBills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private NotificationLayoutBinding binding;

        public ViewHolder(@NonNull NotificationLayoutBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}

