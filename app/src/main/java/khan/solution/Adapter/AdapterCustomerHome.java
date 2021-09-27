package khan.solution.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import khan.solution.Fragments.CustomerCartFragment;
import khan.solution.Model.DishPost;
import khan.solution.R;
import khan.solution.databinding.RecylerLayoutBinding;

public class AdapterCustomerHome extends RecyclerView.Adapter<AdapterCustomerHome.ViewHolder> {

    private Context context;
    private List<DishPost> dishPostList;

    public AdapterCustomerHome(Context context, List<DishPost> dishPostList) {
        this.context = context;
        this.dishPostList = dishPostList;
    }

    @NonNull
    @Override
    public AdapterCustomerHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecylerLayoutBinding binding=RecylerLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new AdapterCustomerHome.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCustomerHome.ViewHolder holder, int position) {

        DishPost dishPost=dishPostList.get(position);
        Glide.with(context).load(dishPost.getDish_Image_Uri()).into(holder.binding.imageViewRecycler);
        holder.binding.priceTv.setText(dishPost.getDish_Price());
        holder.binding.detailsTvs.setText(dishPost.getDish_Description());
        holder.binding.quantityTv.setText(dishPost.getDish_Quantity());
        holder.itemView.setOnClickListener(v ->{
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            saveData(dishPost);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you Want to add this to CART?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        });

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


    private void saveData(DishPost dishPost) {

        final FirebaseAuth auth=FirebaseAuth.getInstance();
        String user = auth.getCurrentUser().getUid();
        String uniqueId = UUID.randomUUID().toString();
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=database.getReference("Cart");

        HashMap<String,Object> cartHash=new HashMap<>();
        cartHash.put("Price", dishPost.getDish_Price());
        cartHash.put("Details", dishPost.getDish_Description());
        cartHash.put("Quantity", dishPost.getDish_Quantity());
        cartHash.put("Image_Uri",dishPost.getDish_Image_Uri());
        cartHash.put("cart_id",uniqueId);

        databaseReference.child(user).child(uniqueId).updateChildren(cartHash).addOnCompleteListener(task -> {
            Toast.makeText(context, "Added To CART", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        });



    }

}
