package khan.solution.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import khan.solution.Model.DishPost;
import khan.solution.databinding.RecylerLayoutBinding;

public class AdapterAdminHome extends RecyclerView.Adapter<AdapterAdminHome.ViewHolder> {

    private Context context;
    private List<DishPost>dishPostList;
    private String item;

    public AdapterAdminHome(Context context, List<DishPost> dishPostList, String item) {
        this.context = context;
        this.dishPostList = dishPostList;
        this.item=item;
    }

    @NonNull
    @Override
    public AdapterAdminHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecylerLayoutBinding binding=RecylerLayoutBinding.
                inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdminHome.ViewHolder holder, int position) {

        DishPost dishPost=dishPostList.get(position);
        Glide.with(context).load(dishPost.getDish_Image_Uri()).into(holder.binding.imageViewRecycler);
        holder.binding.priceTv.setText(dishPost.getDish_Price());
        holder.binding.detailsTvs.setText(dishPost.getDish_Description());
        holder.binding.quantityTv.setText(dishPost.getDish_Quantity());

        holder.itemView.setOnClickListener(v ->{

            final FirebaseDatabase database=FirebaseDatabase.getInstance();
            final DatabaseReference ref;

            if (item.equals("chicken")){

                ref = database.getReference("Dish_Post").child("Chicken");
                ref.child(dishPost.getPost_Id()).removeValue();
                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
            }
            else {

                ref = database.getReference("Dish_Post").child("Mutton");
                ref.child(dishPost.getPost_Id()).removeValue();
                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
            }


//            Intent intent=new Intent(context,EditActivity.class);
//            intent.putExtra("image",dishPost.getDish_Image_Uri());
//            intent.putExtra("price",dishPost.getDish_Price());
//            intent.putExtra("details",dishPost.getDish_Description());
//            intent.putExtra("quantity",dishPost.getDish_Quantity());
//            context.startActivity(intent);

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
}
