package com.events.uninorte.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.events.uninorte.Models.LoginModel;
import com.events.uninorte.Models.UserModel;
import com.events.uninorte.R;
import com.events.uninorte.Services.Service;
import com.events.uninorte.Utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends Activity {

    private EditText name;
    private Spinner gender;
    private EditText tel;
    private EditText address;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private EditText birthday;
    private Activity _this;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.name);
        tel = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        birthday = (EditText) findViewById(R.id.birthday);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        gender = (Spinner) findViewById(R.id.gender);
        gender.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item, getResources().getStringArray(R.array.gender)));
        _this = this;
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cargando....");
    }

    public void back(View v) {
        finish();
    }

    public void register(View v) {
        String _email = email.getText().toString();
        String _password = password.getText().toString();
        String _name = name.getText().toString();
        String _tel = tel.getText().toString();
        String _address = address.getText().toString();
        String _confirmPassword = confirmPassword.getText().toString();
        String _gender = gender.getSelectedItem().toString();
        String _birthday = birthday.getText().toString();
        if (!TextUtils.isEmpty(_email) && !TextUtils.isEmpty(_password)
                && !TextUtils.isEmpty(_name) && !TextUtils.isEmpty(_tel)
                && !TextUtils.isEmpty(_address) && !TextUtils.isEmpty(_confirmPassword)
                && !TextUtils.isEmpty(_gender)) {
            if (_password.contentEquals(_confirmPassword)) {
                progressDialog.show();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL).addConverterFactory(GsonConverterFactory.create()).build();
                Service service = retrofit.create(Service.class);
                UserModel userModel = new UserModel();
                userModel.setAddress(_address);
                userModel.setEmail(_email);
                userModel.setGender(_gender);
                userModel.setName(_name);
                userModel.setTel(_tel);
                userModel.setPassword(_password);
                userModel.setBirthday(_birthday);
                service.register(userModel).enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.code() == 200) {
                            getSharedPreferences(Constants.PACKAGE, MODE_PRIVATE).edit().putBoolean("login", true).commit();
                            startActivity(new Intent(_this, MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "No se pudo realizar registro", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.hide();
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        t.fillInStackTrace();
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "No se pudo realizar registro", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Debe llenar todo los campos", Toast.LENGTH_LONG).show();
        }
    }
}
