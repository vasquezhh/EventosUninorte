package com.events.uninorte.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.events.uninorte.Models.LoginModel;
import com.events.uninorte.R;
import com.events.uninorte.Services.Service;
import com.events.uninorte.Utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity {

    private EditText email;
    private EditText password;
    private ProgressDialog progressDialog;
    private Activity _this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cargando....");
        _this = this;
    }

    public void login(View v) {
        String _email = email.getText().toString();
        String _password = password.getText().toString();
        if (!TextUtils.isEmpty(_email) && !TextUtils.isEmpty(_password)) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
                progressDialog.show();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL).addConverterFactory(GsonConverterFactory.create()).build();
                Service service = retrofit.create(Service.class);
                service.login(_email, _password).enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.code() == 200 && response.body().isLogin()) {
                            getSharedPreferences(Constants.PACKAGE, MODE_PRIVATE).edit().putBoolean("login", true).commit();
                            startActivity(new Intent(_this, MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "El Correo y la Contraseña no son validos", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.hide();
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        t.fillInStackTrace();
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "El Correo y la Contraseña no son validos", Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "Correo no valido", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "El Correo y la Contraseña no pueden estar vacio", Toast.LENGTH_LONG).show();
        }
    }

    public void next(View v) {
        startActivity(new Intent(_this, MainActivity.class));
    }

    public void register(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
