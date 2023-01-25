package kr.co.company.mineralwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar; // 액션바 다루기 위함
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; /*하단 바*/
    private FragmentManager fragmentManager = getSupportFragmentManager(); /*각 화면들 구성*/
    private HomeFragment homeFragment = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GPS 기능 추가
        //LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); // 객체 참조
        //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); // 가장 최근의 위치 정보 가지고 오기

        // 로딩창
        Intent intent = new Intent(this, LodingActivity.class);
        startActivity(intent);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, homeFragment).commit();

        bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()){
                    case R.id.first_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        break;
                    case R.id.second_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
                        break;
                    case R.id.third_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RankFragment()).commit();
                        break;
                    case R.id.fourth_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InfoFragment()).commit();
                        break;
                }
                return true;
            }
        });

        // 기본적으로 액션바 보이지 않게 하기기
       ActionBar actionBar = getSupportActionBar();
       actionBar.hide();

    }
}