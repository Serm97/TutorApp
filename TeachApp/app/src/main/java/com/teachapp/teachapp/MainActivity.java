package com.teachapp.teachapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener,
        TutorialsFragment.OnFragmentInteractionListener,
        NotificationsFragment.OnFragmentInteractionListener,
        CategoriesFragment.OnFragmentInteractionListener,
        HistoryFragment.OnFragmentInteractionListener,
        SeekerFragment.OnFragmentInteractionListener,
        MatchFragment.OnFragmentInteractionListener{

    private FirebaseAuth mAuth;
    private TextView userT;
    private TextView emailT;
    private User userGeneric;
    SharedPreferences sharedPreference;
    Integer theme = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        userT = (TextView) headerView.findViewById(R.id.txt_name_user);
        emailT = (TextView) headerView.findViewById(R.id.txt_email_user);

        Bundle mybundle = this.getIntent().getExtras();

        if(mybundle!=null)
        {
            String name = mybundle.getString("nombreUsuario");
            findUserFirebase(name);

        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_main,new CategoriesFragment())
                .commit();
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        mAuth = FirebaseAuth.getInstance();
    }

    private void findUserFirebase(String name) {

        Query q = FireDatabase.getInstance().child("User").orderByChild("email").equalTo(name).limitToFirst(1);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                        userGeneric = dataSnap.getValue(User.class);
                    }
                }
                userT.setText(userGeneric.getName().toUpperCase());
                emailT.setText(userGeneric.getEmail().toLowerCase());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled",databaseError.getDetails());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.e("User",currentUser.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFragment = null;
        boolean fragmentSeleccionado = false;
        if (id == R.id.nav_seeker) {
            miFragment = new SeekerFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_profile) {
            miFragment = new ProfileFragment();

            Bundle args = new Bundle();
            args.putSerializable("mainUser",userGeneric);
            miFragment.setArguments(args);

            fragmentSeleccionado = true;
        } else if (id == R.id.nav_tutorials) {
            miFragment = new TutorialsFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_notifications) {
            miFragment = new NotificationsFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_categories) {
            miFragment = new CategoriesFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_history) {
            miFragment = new HistoryFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_exit) {
            mAuth.signOut();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
        if(fragmentSeleccionado){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main,miFragment)
                    .commit();

        }
        this.overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void changeTheme(MenuItem item) {
        String tema = "";
        Log.e("DEBUGGGG", String.valueOf(theme));

        switch (theme){
            case 0:
                setTheme(R.style.DarTheme_Theme);
                theme = 1;
                tema = "Dark Theme";
                break;
            case 1:
                theme = 0;
                setTheme(R.style.AppTheme);
                tema = "Light Theme";
                break;
            default:
                theme = 0;
                setTheme(R.style.AppTheme);
                tema = "Light Theme";

        }


        Toast.makeText(this, tema,
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
