package kr.co.company.mineralwater;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class LocDialogFragment extends DialogFragment {
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

}
