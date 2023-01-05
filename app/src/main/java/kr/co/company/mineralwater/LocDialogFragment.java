package kr.co.company.mineralwater;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

public class LocDialogFragment extends DialogFragment {
    /*@NonNull
    @Override
    public Dialog onCreateDilaog(@Nullable Bundle savedInstanceSate){
        CharSequence[] items = {"선택 안 함","경기도","강원도","경상도"};
        boolean[] checkedItem = {true, false, false, false};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton("선택",null).setCancelable(false);
    }*/

    private Fragment fragment;

    public LocDialogFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.selec_loc_dialog, container, false);

        Bundle args = getArguments();
        String value = args.getString("key");



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);*/
    }

}
