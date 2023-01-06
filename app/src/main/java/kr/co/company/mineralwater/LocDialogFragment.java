package kr.co.company.mineralwater;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

public class LocDialogFragment extends DialogFragment implements View.OnClickListener{

    private Fragment fragment;
    private RadioGroup loc_group;
    private RadioButton radioButton;
    public String select_loc;

    public LocDialogFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.selec_loc_dialog, container, false);

        Bundle args = getArguments();
        String value = args.getString("key");

        /*rep_group = (RadioGroup) view.findViewById(R.id.rep_group);
        loc_group1 = (RadioGroup) view.findViewById(R.id.loc_group1);
        loc_group2 = (RadioGroup) view.findViewById(R.id.loc_group2);

        int rb = ((RadioGroup)rep_group.findViewById(R.id.rep_group)).getCheckedRadioButtonId();

        switch (rb){
            case R.id.loc_group1:
                loc_group2.clearCheck();

                *//*if(checked){
                    loc_group2.clearCheck();
                }*//*
            case R.id.loc_group2:
                loc_group1.clearCheck();
        }*/
        loc_group = (RadioGroup) view.findViewById(R.id.loc_group);
        //radioButton = (RadioButton) view.findViewById(loc_group.getCheckedRadioButtonId());
        //Log.e("변수값 확인", radioButton.toString());
        //int rb = ((RadioGroup)loc_group.findViewById(R.id.loc_group)).getCheckedRadioButtonId();
        //Log.e("변수값 확인", Integer.toString(rb)); // 2131296801 ????? ID 인가?

        select_loc = "초기값";

        //Log.e("R.id 값 확인", String.valueOf(R.id.loc_radio1));

        Button select_loc_btn = (Button)view.findViewById(R.id.select_loc_btn);
        select_loc_btn.setOnClickListener(this);
        /*select_loc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("선택 버튼 눌렸는지 확인", "눌러졌음");
                switch (rb){
                    case R.id.loc_radio1:
                        select_loc = "선택 안 함";
                        break;
                    case R.id.loc_radio2:
                        select_loc = "경기도";
                        break;
                    case R.id.loc_radio3:
                        select_loc = "강원도";
                        break;

                }
                Log.e("switch 값 변하는지 확인", select_loc);
            }
        });*/


        //String select_loc = radioButton.getText().toString();
        //Log.e("라디오 버튼 값", select_loc);

        return view;
    }

    @Override
    public void onClick(View view) {
        int rb = ((RadioGroup)loc_group.findViewById(R.id.loc_group)).getCheckedRadioButtonId();
        switch (rb){
            case R.id.loc_radio1:
                select_loc = "선택 안 함";
                break;
            case R.id.loc_radio2:
                select_loc = "경기도";
                break;
            case R.id.loc_radio3:
                select_loc = "강원도";
                break;
            case R.id.loc_radio4:
                select_loc = "경상도";
                break;
            case R.id.loc_radio5:
                select_loc = "충청도";
                break;
            case R.id.loc_radio6:
                select_loc = "전라도";
                break;
            case R.id.loc_radio7:
                select_loc = "제주도";
                break;
        }
    Log.e("선택값 확인", select_loc);

    }

    // DialogFragment
    /*@Override
    public void onDismiss(DialogInterface dialog){
        super.onDismiss(dialog);

        final Activity activity = getActivity();
        if(activity instanceof DialogInterface.OnDismissListener){
            ((DialogInterface.OnDismissListener)activity).onDismiss(dialog);
        }
    }*/

}
