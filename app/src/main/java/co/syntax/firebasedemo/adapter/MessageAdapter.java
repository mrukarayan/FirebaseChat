package co.syntax.firebasedemo.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.syntax.firebasedemo.HelperMethods;
import co.syntax.firebasedemo.R;
import co.syntax.firebasedemo.model.Message;
import co.syntax.firebasedemo.model.User;
import co.syntax.firebasedemo.prefs.UserPrefs;

/**
 * Created by rukarayan on 24-Jan-17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageItemViewHolder>{
    public List<Message> msgList;
    private Context context;
    private LayoutInflater inflater;
    public static final int MESSAGE_LEFT = 1;
    public static final int MESSAGE_RIGHT = 2;
    private UserPrefs prefs;
    private String userId;
    private String sourceFormate = "dd-MM-yyyy-hh-mm-ss";
    private String destinyFormate = "hh:mm a";

    public MessageAdapter(Context context, List<Message> msgList) {
        this.context = context;
        this.msgList = msgList;
        prefs = new UserPrefs(context);
    }

    @Override
    public MessageItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == MESSAGE_LEFT){
            view = inflater.from(context).inflate(R.layout.list_item_message_send, parent, false);
        } else {
            view = inflater.from(context).inflate(R.layout.list_item_message_receive, parent, false);
        }
        MessageItemViewHolder holder = new MessageItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MessageItemViewHolder holder, int position) {
        String date = HelperMethods.getFormatedDate(msgList.get(position).getTime().toString(), sourceFormate, destinyFormate);
        holder.tvDate.setText(date);
        holder.tvMessage.setText(msgList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    @Override
    public int getItemViewType(int position) {
        User user = prefs.getObject(UserPrefs.USER_TOKEN_KEY, User.class);
        if(user != null){
            userId = user.getUserId();
        }
        if( userId.equalsIgnoreCase(msgList.get(position).getSenderId())){
            return MESSAGE_LEFT;
        }
        else {
            return MESSAGE_RIGHT;
        }
    }

    public class MessageItemViewHolder extends RecyclerView.ViewHolder{
            TextView tvDate, tvMessage;
            public MessageItemViewHolder(View itemView) {
                super(itemView);
                tvDate = (TextView)itemView.findViewById(R.id.tvDate);
                tvMessage = (TextView)itemView.findViewById(R.id.tvMessage);
            }

}


}
