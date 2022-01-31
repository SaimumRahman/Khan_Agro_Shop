package khan.solution.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import khan.solution.Activities.AdminOrderDetailsActivity;
import khan.solution.Model.PayBill;
import khan.solution.Model.Products;
import khan.solution.databinding.NotificationLayoutBinding;

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

        holder.binding.ordershipped.setOnClickListener(v ->{
            shiipingOrder(payBill);
        });

        holder.itemView.setOnClickListener(v ->{
            Intent intent=new Intent(context, AdminOrderDetailsActivity.class);
            intent.putExtra("user",payBill.getCustomer_ID());
            context.startActivity(intent);
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

    private void shiipingOrder(PayBill payBill) {


        String user=payBill.getCustomer_ID();

        final FirebaseDatabase db=FirebaseDatabase.getInstance();
        final DatabaseReference ref=db.getReference("Products").child(user);
        final DatabaseReference addref=db.getReference("Shipped").child(user);
        final DatabaseReference removeRef=db.getReference("Orders").child(user);
        final DatabaseReference addShippedOrder=db.getReference("Shipped_Orders").child(user);
        final String shippingId= UUID.randomUUID().toString();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                       for (DataSnapshot snapshot1:snapshot.getChildren()){

                           Products p=snapshot1.getValue(Products.class);

                           final HashMap<String,Object> addShipHas=new HashMap<>();
                           addShipHas.put("cart_id",p.getCart_id());
                           addShipHas.put("details",p.getDetails());
                           addShipHas.put("imageuri",p.getImageuri());
                           addShipHas.put("price",p.getPrice());
                           addShipHas.put("quantity",p.getQuantity());

                           addref.child(shippingId).updateChildren(addShipHas)
                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   Toast.makeText(context, "Order Has been Shipped", Toast.LENGTH_SHORT).show();

                                   //Address,Customer_ID,Email,Name,Order_ID,Phone,Total_Bill;
                                   final HashMap<String,Object> addordership=new HashMap<>();
                                   addordership.put("Address",payBill.getAddress());
                                   addordership.put("Customer_ID",payBill.getCustomer_ID());
                                   addordership.put("Email",payBill.getEmail());
                                   addordership.put("Name",payBill.getName());
                                   addordership.put("Order_ID",payBill.getOrder_ID());
                                   addordership.put("Total_Bill",payBill.getTotal_Bill());

                                   addShippedOrder.child(payBill.getOrder_ID()).updateChildren(addordership).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {

                                       }
                                   });

                                   removeRef.removeValue();

                               }
                           });

                       }
                } else {
                    Toast.makeText(context, "No Data Exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}

