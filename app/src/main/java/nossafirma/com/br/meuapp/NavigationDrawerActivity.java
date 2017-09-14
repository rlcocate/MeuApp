package nossafirma.com.br.meuapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import nossafirma.com.br.meuapp.fragment.AddStoreFragment;
import nossafirma.com.br.meuapp.fragment.ListStoresFragment;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openFragment(MapsActivity.class);
                startActivity(new Intent(NavigationDrawerActivity.this, MapsActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
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

        Intent intent = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_stores) {
            openFragment(ListStoresFragment.class);
        } else if (id == R.id.nav_newStore) {
            openFragment(AddStoreFragment.class);
        } else if (id == R.id.nav_about) {
            try {
                startActivity(new Intent(NavigationDrawerActivity.this, AboutActivity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_share) {
            intent = new Intent(Intent.ACTION_SEND);
            String texto = getString(R.string.best_beer_app) +
                           "#cademinhacerveja #whereismybeer";
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, texto);
            startActivity(intent);

        } else if (id == R.id.nav_send) {
//            Uri uri = Uri.parse("tel:999999999");
//            intent = new Intent(Intent.ACTION_CALL, uri);
//            checkCallingPermission("Ligação");
//            startActivity(intent);
            Uri uri = Uri.parse("tel:32584994");
            Intent intencao = new Intent(Intent.ACTION_CALL, uri);
            checkPermission("ACTION_CALL",0,0);
            startActivity(intencao);
        } else if (id == R.id.nav_exit) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openFragment(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }
}
