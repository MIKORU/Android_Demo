package com.example.sxs10540.uiwigettest;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.sxs10540.uifragment.ChatFragment;
import com.example.sxs10540.uifragment.DownloadFragment;
import com.example.sxs10540.uifragment.FruitFragment;
import com.example.sxs10540.uifragment.MainFragment;
import com.example.sxs10540.uifragment.PersonFragment;
import com.example.sxs10540.uifragment.WebFragment;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;

    private MainFragment mainFragment = new MainFragment();
    private DownloadFragment downloadFragment = new DownloadFragment();
    private PersonFragment personFragment = new PersonFragment();
    private WebFragment webFragment = new WebFragment();
    private ChatFragment chatFragment = new ChatFragment();
    private FruitFragment fruitFragment = new FruitFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replacesFragment(mainFragment);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ImageView imageView = (ImageView) findViewById(R.id.other);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getOrder()) {
                    case 0:
                        replacesFragment(mainFragment);
                        break;
                    case 1:
                        replacesFragment(downloadFragment);
                        break;
                    case 2:
                        replacesFragment(personFragment);
                        break;
                    case 3:
                        replacesFragment(webFragment);
                        break;
                    case 4:
                        replacesFragment(chatFragment);
                        break;
                    case 5:
                        replacesFragment(fruitFragment);
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });


    }

    public void replacesFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
