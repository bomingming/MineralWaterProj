package kr.co.company.mineralwater;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

public class LocDialogFragment extends DialogFragment implements View.OnClickListener{
    OnMyDialogResult myDialogResult;

    private Fragment fragment;
    private RadioGroup loc_group;
    public String select_result;

    public LocDialogFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.selec_loc_dialog, container, false);

        Bundle args = getArguments();
        String value = args.getString("key");

        loc_group = (RadioGroup) view.findViewById(R.id.loc_group);

        select_result = "";

        Button select_loc_btn = (Button)view.findViewById(R.id.select_loc_btn);
        select_loc_btn.setOnClickListener(this);

        // 화면 터치 시 꺼짐 막기
        setCancelable(false);

        return view;
    }

    @Override
    public void onClick(View view) {
        int rb = ((RadioGroup)loc_group.findViewById(R.id.loc_group)).getCheckedRadioButtonId();
        switch (rb){
            case R.id.loc_radio1:
                select_result = "선택 안 함";
                break;
            case R.id.loc_radio2:
                select_result = "경기도";
                break;
            case R.id.loc_radio3:
                select_result = "강원도";
                break;
            case R.id.loc_radio4:
                select_result = "경상도";
                break;
            case R.id.loc_radio5:
                select_result = "충청도";
                break;
            case R.id.loc_radio6:
                select_result = "전라도";
                break;
            case R.id.loc_radio7:
                select_result = "제주도";
                break;
        }

        if(myDialogResult != null){
            // 지역 선택 시 토스트 메시지 출력
            Toast.makeText(getActivity(), "지역이 선택되었습니다", Toast.LENGTH_SHORT).show();
            myDialogResult.finish(select_result);
        }

    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        myDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(String result);
    }

}
