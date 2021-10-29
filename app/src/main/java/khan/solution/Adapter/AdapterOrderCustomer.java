package khan.solution.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import khan.solution.Activities.AdminOrderDetailsActivity;
import khan.solution.Model.PayBill;
import khan.solution.databinding.NotificationLayoutBinding;

public class AdapterOrderCustomer extends RecyclerView.Adapter<AdapterOrderCustomer.ViewHolder> {

    private Context context;
    private List<PayBill> payBills;

    public AdapterOrderCustomer(Context context, List<PayBill> payBills) {
        this.context = context;
        this.payBills = payBills;
    }

    @NonNull
    @Override
    public AdapterOrderCustomer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NotificationLayoutBinding binding=NotificationLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new AdapterOrderCustomer.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrderCustomer.ViewHolder holder, int position) {

        PayBill payBill=payBills.get(position);
        holder.binding.nameordernot.setText(payBill.getName());
        holder.binding.emailOrdernot.setText(payBill.getEmail());
        holder.binding.phnordernot.setText(payBill.getPhone());
        holder.binding.addressOrdernot.setText(payBill.getAddress());
        holder.binding.pricetotalOrdernot.setText(payBill.getTotal_Bill());

        holder.itemView.setOnClickListener(v ->{

           Intent passIntent=new Intent(context,AdminOrderDetailsActivity.class);
           passIntent.putExtra("user",payBill.getCustomer_ID());
           context.startActivity(passIntent);

        });


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


