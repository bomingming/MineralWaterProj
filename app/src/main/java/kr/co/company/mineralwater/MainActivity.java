package kr.co.company.mineralwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    SearchView searchView; /*검색창*/

    private BottomNavigationView bottomNavigationView; /*하단 바*/
    private FragmentManager fragmentManager = getSupportFragmentManager(); /*각 화면들 구성*/
    private HomeFragment homeFragment = new HomeFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private RankFragment rankFragment = new RankFragment();
    private InfoFragment infoFragment = new InfoFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, homeFragment).commit();

        bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()){
                    case R.id.first_tab:
                        fragmentTransaction.replace(R.id.fragment_container, homeFragment).commit();
                        break;
                    case R.id.second_tab:
                        fragmentTransaction.replace(R.id.fragment_container, searchFragment).commit();
                        break;
                    case R.id.third_tab:
                        fragmentTransaction.replace(R.id.fragment_container, rankFragment).commit();
                        break;
                    case R.id.fourth_tab:
                        fragmentTransaction.replace(R.id.fragment_container, infoFragment).commit();
                        break;

                }

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){ // ActionBar에서 Search 기능 구현
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            searchView.setQuery("", false);
            searchView.setIconified(true);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };



}