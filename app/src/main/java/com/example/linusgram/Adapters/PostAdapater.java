package com.example.linusgram.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.linusgram.Models.Post;
import com.example.linusgram.R;
import com.parse.ParseFile;

import org.w3c.dom.Text;

import java.util.List;

public class PostAdapater extends RecyclerView.Adapter<PostAdapater.ViewHolder> {

    private final int CLASS_CODE;
    private Context context;
    private List<Post> posts;
    onClickListener clickListener;
    public static final int DETAILS_CODE = 200;
    public static final int PROFILE_CODE = 300;
    public static final int LIKE_CODE = 400;

    public static final int HOME_FRAGMENT_CODE = 21;
    public static final int PROFILE_FRAGMENT_CODE = 22;

    public interface onClickListener{
        void onItemClicked(int position, int replyCode);
    }

    public PostAdapater(Context context, List<Post> posts, onClickListener clickListener, int CLASS_CODE) {
        this.context = context;
        this.posts = posts;
        this.clickListener = clickListener;
        this.CLASS_CODE = CLASS_CODE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        if (CLASS_CODE == HOME_FRAGMENT_CODE){
            view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        } else if (CLASS_CODE == PROFILE_FRAGMENT_CODE){
            view = LayoutInflater.from(context).inflate(R.layout.item_user_post, parent, false);
        }

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private TextView tvUserName;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvUserNameDescription;
        private TextView tvDate;
        private TextView tvNumberofLikes;
        private ImageView profilePic;
        private ImageView likeIcon;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivPostImage);
            tvUserNameDescription = itemView.findViewById(R.id.tvUserNameDescription);
            tvDate = itemView.findViewById(R.id.tvCreatedAt);
            tvNumberofLikes = itemView.findViewById(R.id.NumberofActualLikes);
            profilePic = itemView.findViewById(R.id.ivProfileImage);
            likeIcon = itemView.findViewById(R.id.ivLike);



        }

        public void bind(final Post post) {

            tvDescription.setText(post.getDescription());
            tvUserName.setText(post.getUser().getUsername());
            tvUserNameDescription.setText(post.getUser().getUsername());
            tvDate.setText(post.getTime());
            tvNumberofLikes.setText(post.getLikes().toString());

            profilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClicked(getAdapterPosition(), PROFILE_CODE);
                }
            });

            likeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClicked(getAdapterPosition(), LIKE_CODE);
                    likeIcon.setImageResource(R.drawable.ufi_heart_active);
                    tvNumberofLikes.setText(post.getLikes().toString());
                }
            });



           String profilepic = post.getUser().getParseFile("ProfilePic").getUrl();

            Glide.with(context)
                    .load(profilepic)
                    .into(profilePic);

            ParseFile image = post.getImage();

            if (image != null){
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivImage);
            }else{
                Log.i("pOSN CB ", "IMAGE NOT EXISTING" );
            }

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClicked(getAdapterPosition(), DETAILS_CODE);
                }
            });

        }
    }
}
