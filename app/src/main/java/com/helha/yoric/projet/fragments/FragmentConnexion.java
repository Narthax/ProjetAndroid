package com.helha.yoric.projet.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.helha.yoric.projet.MainActivity;
import com.helha.yoric.projet.R;
import com.helha.yoric.projet.model.User;
import com.helha.yoric.projet.model.UserManagement;

/**
 * Created by yoric on 26-10-17.
 */

public class FragmentConnexion extends Fragment {

    private EditText etPseudo;
    private EditText etMDP;
    private Button btConnexion;
    private CheckBox cbRemember;
    private SharedPreferences preferences;

    public CheckBox getCbRemember(){
        if(cbRemember==null){
            cbRemember = (CheckBox) getView().findViewById(R.id.cb_login_remember);
        }
        return cbRemember;
    }

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

        etPseudo = (EditText) v.findViewById(R.id.et_login_username);
        etMDP = (EditText) v.findViewById(R.id.et_password);
        cbRemember = (CheckBox) v.findViewById(R.id.cb_login_remember);
        btConnexion = (Button) v.findViewById(R.id.btn_login_submit);


        preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        boolean isRemembered = preferences.getBoolean("remember_me", false);
        if (isRemembered) {
            etPseudo.setText(preferences.getString("username", ""));
            etMDP.setText(preferences.getString("password", ""));
            cbRemember.setChecked(isRemembered);
        }

        btConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).hideSoftKeyboard(getActivity(), v);
                if (!etPseudo.getText().toString().isEmpty()&&!etMDP.getText().toString().isEmpty()) {
                    User user = new User(etPseudo.getText().toString(), etMDP.getText().toString());
                    int index = UserManagement.getInstance().getListUser().indexOf(user);
                    if (index > -1 && UserManagement.getInstance().getListUser().get(index).getPassword().equals(user.getPassword()) &&
                            UserManagement.getInstance().getListUser().get(index).getName().equals(user.getName())) {
                            SharedPreferences.Editor editor = preferences.edit();
                            if (cbRemember.isChecked()) {
                                editor.putString("username", etPseudo.getText().toString());
                                editor.putString("password", etMDP.getText().toString());
                                editor.putBoolean("remember_me", true);
                            } else {
                                editor.putString("username", "");
                                editor.putString("password", "");
                                editor.putBoolean("remember_me", false);
                            }
                            editor.apply();

                            ((MainActivity) getActivity()).setUser(new User(etPseudo.getText().toString(), etMDP.getText().toString()));
                            ((MainActivity) getActivity()).getMenu().getItem(4).setVisible(true);
                            ((MainActivity) getActivity()).getMenu().getItem(3).setVisible(true);
                            ((MainActivity) getActivity()).getMenu().getItem(3).setVisible(false);
                            ((MainActivity) getActivity()).getMenu().getItem(1).setVisible(true);
                            ((MainActivity) getActivity()).getMenu().getItem(2).setVisible(false);
                            ((MainActivity) getActivity()).getMenu().getItem(5).setVisible(true);
                            ((MainActivity) getActivity()).getMenu().getItem(1).setTitle(((MainActivity) getActivity()).getUser().getName());
                            ((MainActivity) getActivity()).goHome();
                    }
                    else {
                        Toast.makeText(getActivity(), "Pseudo ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Champs vides", Toast.LENGTH_LONG).show();
                }

            }
        });
        return v;
    }

    public void setFragmentConnexion(){
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        boolean isRemembered = preferences.getBoolean("remember_me", false);
        if (isRemembered) {
            etPseudo.setText(preferences.getString("username", ""));
            etMDP.setText(preferences.getString("password", ""));
            cbRemember.setChecked(preferences.getBoolean("remember_me", false));
        }
        else{
            etPseudo.setText("");
            etMDP.setText("");
            cbRemember.setChecked(false);
        }
    }

    public void resetFormConnexion(){
        etPseudo.setText("");
        etMDP.setText("");
        cbRemember.setChecked(false);
    }
}