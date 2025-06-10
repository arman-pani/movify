package activities;

import static com.example.movieapp.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.AppCheckProviderFactory;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import adapters.TabViewPagerAdapter;
import helpers.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private NavController navController;
    private FirebaseHelper authHelper = new FirebaseHelper();

    private DrawerLayout drawerLayout;
    private NavigationView drawerNavigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onStart() {
        super.onStart();

        if (!authHelper.isUserLoggedIn()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search){
            Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance()
        );


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager2 = findViewById(R.id.tab_view_pager);
        fab = findViewById(R.id.movieBotFAB);

        fab.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, ChatbotActivity.class);
            startActivity(intent);
        });

        TabViewPagerAdapter adapter = new TabViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0){
                tab.setText("Movies");
            } else if (position == 1){
                tab.setText("TV Series");
            } else if (position == 2){
                tab.setText("People");
            }
        }).attach();



        Toolbar appbar = findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        drawerNavigationView = findViewById(id.drawer_navigation_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout,appbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);

        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.menu);
        toggle.syncState();


        appbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        drawerNavigationView.setNavigationItemSelectedListener(
                item -> {
                    int id = item.getItemId();

                    if(id == R.id.nav_home) {
                        navController.navigate(R.id.moviesFragment);

                    } else if (id == R.id.nav_search){
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);

                    } else if (id == R.id.nav_bookmark){
                        Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
                        startActivity(intent);
                    } else if (id == R.id.nav_logout){
                        authHelper.signOut();
                        Intent intent = new Intent(this, RegisterActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    drawerLayout.closeDrawers();
                    return true;
                }
        );

    }
}
