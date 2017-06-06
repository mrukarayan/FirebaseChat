package co.syntax.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.syntax.firebasedemo.adapter.MessageAdapter;
import co.syntax.firebasedemo.model.Message;
import co.syntax.firebasedemo.model.User;
import co.syntax.firebasedemo.prefs.UserPrefs;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageButton imbtnSend;
    private EditText etMessage;
    private RecyclerView rvMessage;
    private LinearLayoutManager llm;
    private MessageAdapter msgAdapter;
    private List<Message> msgList;
    private FirebaseAuth rAuth;
    private DatabaseReference rRef;
    private UserPrefs prefs;
    private String senderId;
    private String reciepientId;
    private String message;
    private String timeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Chats");

        Intent intent = getIntent();
        if (intent != null) {
            reciepientId = intent.getStringExtra("rpId");
        }
        prefs = new UserPrefs(this);
        rvMessage = (RecyclerView) findViewById(R.id.rvMessage);
        imbtnSend = (ImageButton) findViewById(R.id.imbtnSend);
        etMessage = (EditText) findViewById(R.id.etMessage);
        imbtnSend.setOnClickListener(this);
        llm = new LinearLayoutManager(this);
        rvMessage.setLayoutManager(llm);
        //llm.setReverseLayout(true);
        msgList = new ArrayList<Message>();
        msgAdapter = new MessageAdapter(this, msgList);
        rvMessage.setAdapter(msgAdapter);
        rRef = FirebaseDatabase.getInstance().getReference().child("messages");

        rRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    Message msg = dataSnapshot.getValue(Message.class);
                    msgList.add(msg);
                    rvMessage.scrollToPosition(msgList.size() - 1);
                    msgAdapter.notifyItemInserted(msgList.size() - 1);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void sendMessage() {
        User user = prefs.getObject(UserPrefs.USER_TOKEN_KEY, User.class);
        senderId = user.getUserId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        timeStamp = simpleDateFormat.format(new Date());
        message = etMessage.getText().toString();
        Message msg = new Message(senderId, reciepientId, message, timeStamp);
        rRef.child(timeStamp).setValue(msg);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imbtnSend) {
            if (!etMessage.getText().toString().equalsIgnoreCase("")) {
                sendMessage();
            } else {
                etMessage.setError("required");
            }

        }
    }
}
