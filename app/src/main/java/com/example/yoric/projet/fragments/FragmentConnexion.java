package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.yoric.projet.R;

/**
 * Created by yoric on 26-10-17.
 */

public class FragmentConnexion extends Fragment {

    private EditText etPseudo;
    private EditText etMDP;
    private Button btConnexion;
    private CheckBox cbRemember;

    private static FragmentConnexion fragmentConnexion = null;
    public static FragmentConnexion getInstance() {
        if (fragmentConnexion == null) {
            fragmentConnexion = new FragmentConnexion();
        }
        return fragmentConnexion;
    }

    private ConnexionCallBack ICallBack;
    public void setCallBack(ConnexionCallBack ICallBack){
        this.ICallBack = ICallBack;
    }
    public interface ConnexionCallBack{}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_connexion,container,false);

        etPseudo = (EditText) v.findViewById(R.id.et_password);
        btConnexion = (Button) v.findViewById(R.id.btn_login_submit);
        cbRemember = (CheckBox) v.findViewById(R.id.cb_login_remember);
        return v;
    }

}
