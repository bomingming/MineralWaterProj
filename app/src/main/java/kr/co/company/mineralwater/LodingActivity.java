package kr.co.company.mineralwater;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

public class LodingActivity extends Activity {
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loaing_main);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isNetwork = false;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Network network = connectivityManager.getActiveNetwork();
            if(network != null){
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                if(capabilities != null){
                    isNetwork = true;
                }
            }
        }
        if(isNetwork == false){ // 인터넷에 연결되지 않은 경우
            // 다이얼로그 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle("종료");
            builder.setMessage("인터넷 연결이 원활하지 않아 어플이 종료됩니다.");
            builder.setPositiveButton("확인", dialogListener);
            alertDialog = builder.create();
            builder.show();
        }else if(isNetwork == true){ // 인터넷에 연결된 경우
            startLoading(); // 로딩 후 화면 전환
        }
    }

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            finishAffinity(); // 어플 종료
        }
    };

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }
}
