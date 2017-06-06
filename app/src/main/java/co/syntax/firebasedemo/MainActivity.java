package co.syntax.firebasedemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.syntax.firebasedemo.adapter.TabLayoutPageAdapter;
import co.syntax.firebasedemo.base.BaseActivity;
import co.syntax.firebasedemo.fragment.ChatsFragment;
import co.syntax.firebasedemo.fragment.GroupsFragment;
import co.syntax.firebasedemo.prefs.UserPrefs;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private EditText etName, etPassword;
    private Button btnSend;
    private DatabaseReference rRef;
    private FirebaseAuth rAuth;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayoutPageAdapter pagerAdapter;
    public static final String LOG_CAT = MainActivity.class.getSimpleName();
    private UserPrefs prefs;
    private String userDeviceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rRef = FirebaseDatabase.getInstance().getReference();
        rAuth = FirebaseAuth.getInstance();
        prefs = new UserPrefs(this);
        userDeviceId = prefs.getUserDeviceId(UserPrefs.USER_DEVICE_ID_KEY);
        Log.d("MainActivity", userDeviceId);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        //Firebase crush report test
        FirebaseCrash.report(new Exception("Firebase crash report send"));
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(getResources().getString(R.string.tab_chats));
        //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_launcher, 0, 0);
        tabOne.setSelected(true);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getResources().getString(R.string.tab_groups));
        //tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_launcher, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(getResources().getString(R.string.tab_contacts));
        //tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_launcher, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

    }

    private void setupViewPager(ViewPager viewPager) {
        pagerAdapter = new TabLayoutPageAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new ChatsFragment(), getResources().getString(R.string.tab_chats));
        pagerAdapter.addFragment(new GroupsFragment(), getResources().getString(R.string.tab_groups));
        pagerAdapter.addFragment(new GroupsFragment(), getResources().getString(R.string.tab_contacts));
        viewPager.setAdapter(pagerAdapter);
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.btnSend) {

        }
    }

}
