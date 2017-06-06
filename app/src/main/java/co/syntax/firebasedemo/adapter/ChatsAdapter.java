package co.syntax.firebasedemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import co.syntax.firebasedemo.MessageActivity;
import co.syntax.firebasedemo.R;
import co.syntax.firebasedemo.model.User;

/**
 * Created by rukarayan on 22-Jan-17.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {
    private LayoutInflater inflater;
    private List<User> userList;
    private Context context;

    public ChatsAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public ChatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(context).inflate(R.layout.list_item_chats, parent, false);
        ChatsViewHolder holder = new ChatsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChatsViewHolder holder, final int position) {

        holder.tvName.setText(userList.get(position).getUserName());
        holder.tvEmail.setText(userList.get(position).getUserEmail());
       /* Picasso.with(context)
                .load(userList.get(position).getImage())
                .into(holder.imgProfile);*/
        holder.rlChatsListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMessage = new Intent(context, MessageActivity.class);
                String reciepientId = userList.get(position).getUserId();
                intentMessage.putExtra("rpId", reciepientId);
                context.startActivity(intentMessage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ChatsViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvEmail;
        ImageView imgProfile;
        RelativeLayout rlChatsListItem;

        public ChatsViewHolder(View itemView) {
            super(itemView);

            rlChatsListItem = (RelativeLayout)itemView.findViewById(R.id.rlChatsListItem);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvEmail = (TextView)itemView.findViewById(R.id.tvEmail);
            imgProfile = (ImageView)itemView.findViewById(R.id.imgProfile);
        }
    }
}
