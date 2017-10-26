package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.yoric.projet.R;

/**
 * Created by yoric on 26-10-17.
 */

public class FragmentInscription extends Fragment {

    private EditText etPseudo;
    private EditText etMDP;
    private EditText etMDP2;
    private Button btConfirmer;

    private static FragmentInscription fragmentInscription = null;
    public static FragmentInscription getInstance() {
        if (fragmentInscription == null) {
            fragmentInscription = new FragmentInscription();
        }
        return fragmentInscription;
    }

    private InscriptionCallBack ICallBack;
    public void setCallBack(InscriptionCallBack ICallBack){
        this.ICallBack = ICallBack;
    }
    public interface InscriptionCallBack{}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_inscription,container,false);

        etPseudo = (EditText) v.findViewById(R.id.et_login_username);
        etMDP = (EditText) v.findViewById(R.id.et_password);
        etMDP2 = (EditText) v.findViewById(R.id.et_password2);
        btConfirmer = (Button) v.findViewById(R.id.btn_login_submit);
        return v;
    }
}
