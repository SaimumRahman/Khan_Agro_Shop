package khan.solution.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.paperdb.Paper;
import khan.solution.Model.Cart;
import khan.solution.Model.DishPost;
import khan.solution.Model.Prevelent;
import khan.solution.databinding.RecylerLayoutBinding;

public class AdapterCustomerCart extends RecyclerView.Adapter<AdapterCustomerCart.ViewHolder> {

    private Context context;
    private List<Cart> cartList;

    public AdapterCustomerCart(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public AdapterCustomerCart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecylerLayoutBinding binding=RecylerLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new AdapterCustomerCart.ViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull AdapterCustomerCart.ViewHolder holder, int position) {

        Cart cart=cartList.get(position);
        Glide.with(context).load(cart.getImage_Uri()).into(holder.binding.imageViewRecycler);
        holder.binding.priceTv.setText(cart.getPrice());
        holder.binding.detailsTvs.setText(cart.getDetails());
        holder.binding.quantityTv.setText(cart.getQuantity());

        holder.itemView.setOnClickListener(v ->{

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            final FirebaseAuth auth=FirebaseAuth.getInstance();
                            String user= auth.getCurrentUser().getUid();
                            final FirebaseDatabase database=FirebaseDatabase.getInstance();
                            final DatabaseReference databaseReference=database.getReference("Cart");

                            databaseReference.child(user).child(cart.getCart_id()).removeValue().
                                    addOnCompleteListener(task ->
                                            Toast.makeText(context, "Product removed", Toast.LENGTH_SHORT).show());
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you Want to remove this from CART?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        });


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private RecylerLayoutBinding binding;

        public ViewHolder(@NonNull RecylerLayoutBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
