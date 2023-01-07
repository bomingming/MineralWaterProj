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
    public String select_result;
    private HomeFragment homeFragment;

    public LocDialogFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.selec_loc_dialog, container, false);

        Bundle args = getArguments();
        String value = args.getString("key");

        loc_group = (RadioGroup) view.findViewById(R.id.loc_group);
        //radioButton = (RadioButton) view.findViewById(loc_group.getCheckedRadioButtonId());
        //Log.e("변수값 확인", radioButton.toString());
        //int rb = ((RadioGroup)loc_group.findViewById(R.id.loc_group)).getCheckedRadioButtonId();
        //Log.e("변수값 확인", Integer.toString(rb)); // 2131296801 ????? ID 인가?

        select_result = "";

        Button select_loc_btn = (Button)view.findViewById(R.id.select_loc_btn);
        select_loc_btn.setOnClickListener(this);

        /*Button apply_btn = (Button) view.findViewById(R.id.apply_btn);
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismiss();
            }
        });
*/
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

        // 선택값 전달 시도
        //String select_result =

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
