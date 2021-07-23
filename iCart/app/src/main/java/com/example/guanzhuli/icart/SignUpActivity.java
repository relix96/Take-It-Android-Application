package com.example.guanzhuli.icart;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.data.ClientData;
import com.example.guanzhuli.icart.data.MoradaData;
import com.example.guanzhuli.icart.data.SPManipulation;


public class SignUpActivity extends AppCompatActivity {
    private TextView mTextfirstName, mTextlastName, mTextEmail, mTextPwd, mTextRePwd, mTextSignIn, mTextPhone;
    private Button mButtonContinue;
    private RequestQueue mRequestQueue;
    private SPManipulation mSPManipulation;
    private ClientData clientData;
    private MoradaData morada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        clientData = new ClientData();
        morada = new MoradaData();
        clientData.setMorada(morada);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mSPManipulation = SPManipulation.getInstance(this);
        mRequestQueue =  Volley.newRequestQueue(SignUpActivity.this);
        mTextfirstName = (TextView) findViewById(R.id.sign_up_Address);
        mTextlastName = (TextView) findViewById(R.id.sign_up_postCode);
        mTextPhone = (TextView) findViewById(R.id.sign_up_phone);
        mTextEmail = (TextView) findViewById(R.id.sign_up_City);
        mTextPwd = (TextView) findViewById(R.id.sign_up_password);
        mTextRePwd = (TextView) findViewById(R.id.sign_up_password2);
        mButtonContinue = (Button) findViewById(R.id.button_signIn);
        mButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = mTextfirstName.getText().toString();
                // add method to check illegal character
                String lastName = mTextlastName.getText().toString();
                // add method to check all are numbers
                String email = mTextEmail.getText().toString();
                String phone = mTextPhone.getText().toString();
                // add method to check email format
                String pwd = mTextPwd.getText().toString();
                String pwd2 = mTextRePwd.getText().toString();
                if (!pwdMatch(pwd, pwd2)) {
                    Toast.makeText(SignUpActivity.this, "Password does not match!", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    clientData.setPrimeiro_nome(firstName);
                    clientData.setApelido(lastName);
                    clientData.setMail(email);
                    clientData.setPassword(pwd);
                    clientData.setContacto(phone);
                    Intent i = new Intent(SignUpActivity.this, SignUpActivity2.class);
                    i.putExtra("client", clientData);
                    startActivityForResult(i,1);
                }
            }
        });
        mTextSignIn = (TextView) findViewById(R.id.to_sign_in);
        mTextSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ClientData c = (ClientData) data.getSerializableExtra("client");
            this.clientData = c;
        }
    }

    private boolean pwdMatch(String pwd, String pwd2) {
        if(pwd.equals(pwd2) && pwd.length()>= 6){
            return true;
        }

        else
            return false;
    }

    private boolean checkUsername(String username) {
        return true;
    }

    private boolean checkMobile(String mobile) {
        return true;
    }

    private boolean checkEmail(String email) {
        return true;
    }
}
