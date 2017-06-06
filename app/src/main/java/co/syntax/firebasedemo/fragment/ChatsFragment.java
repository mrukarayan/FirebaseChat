package co.syntax.firebasedemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import co.syntax.firebasedemo.R;
import co.syntax.firebasedemo.adapter.ChatsAdapter;
import co.syntax.firebasedemo.model.User;
import co.syntax.firebasedemo.prefs.UserPrefs;

/**
 * Created by rukarayan on 22-Jan-17.
 */

public class ChatsFragment extends Fragment {
    private View view;
    private RecyclerView rvFriens;
    private LinearLayoutManager llm;
    private ChatsAdapter chatsAdapter;
    private List<User> userList;
    private FirebaseAuth rAuth;
    private DatabaseReference rRef;
    private UserPrefs prefs;
    private User user;
    public static final String LOG_TAG = "ChatsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chats, container, false);

        rAuth = FirebaseAuth.getInstance();
        rRef = FirebaseDatabase.getInstance().getReference().child("users");
        prefs = new UserPrefs(getActivity());
        rvFriens = (RecyclerView)view.findViewById(R.id.rvFriends);
        llm = new LinearLayoutManager(getActivity());
        rvFriens.setLayoutManager(llm);
        userList = new ArrayList<>();
        chatsAdapter = new ChatsAdapter(getActivity(), userList);
        user = prefs.getObject(UserPrefs.USER_TOKEN_KEY, User.class);
        Log.d(LOG_TAG, user.getUserEmail());

        rRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot != null && dataSnapshot.getValue() != null){
                    try {
                        User newUser = dataSnapshot.getValue(User.class);
                        if(!newUser.getUserId().equalsIgnoreCase(user.getUserId())){
                            userList.add(newUser);
                        }
                        rvFriens.scrollToPosition(userList.size() - 1);
                        chatsAdapter.notifyItemInserted(userList.size() - 1);

                    }
                    catch (Exception e){

                    }
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
        rvFriens.setAdapter(chatsAdapter);


        return view;
    }


}
