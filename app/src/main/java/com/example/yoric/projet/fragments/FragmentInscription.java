package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.yoric.projet.MainActivity;
import com.example.yoric.projet.R;
import com.example.yoric.projet.model.User;
import com.example.yoric.projet.model.UserManagement;
import com.example.yoric.projet.utils.Serialisation;

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

        btConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etPseudo.getText().toString().isEmpty()&&!etMDP.getText().toString().isEmpty()&&!etMDP2.getText().toString().isEmpty()){
                    if (etMDP.getText().toString().equals(etMDP2.getText().toString())){

                        User user = new User(etPseudo.getText().toString(),etMDP.getText().toString());
                        if (!UserManagement.getInstance().getListUser().contains(user)){
                            UserManagement.getInstance().addUser(user);
                            Serialisation.writeJson("User.json", Serialisation.writeUser(), getActivity());
                            Log.i("OUHOUHOUH",Serialisation.writeUser());
                            ((MainActivity)getActivity()).goConnexion();
                        }
                    }
                }
            }
        });
        return v;
    }

    public void setFragmentInscription(){
        etMDP.setText("");
        etMDP2.setText("");
        etPseudo.setText("");
    }
}
