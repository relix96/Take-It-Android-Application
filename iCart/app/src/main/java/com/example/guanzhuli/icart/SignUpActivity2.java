package com.example.guanzhuli.icart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.data.ClientData;
import com.example.guanzhuli.icart.data.SPManipulation;
import com.example.guanzhuli.icart.service.ClientService;
import com.example.guanzhuli.icart.utils.API;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class SignUpActivity2 extends AppCompatActivity {
    private TextView mTextAddress, mTextpostCode, mTextCity, mTextPwd, mTextRePwd, mTextSignIn;
    private Button mButtonSignUp;
    private RequestQueue mRequestQueue;
    private SPManipulation mSPManipulation;
    private ClientData client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        mSPManipulation = SPManipulation.getInstance(this);
        mRequestQueue =  Volley.newRequestQueue(SignUpActivity2.this);
        mTextAddress = (TextView) findViewById(R.id.sign_up_Address);
        mTextpostCode = (TextView) findViewById(R.id.sign_up_postCode);
        mTextCity = (TextView) findViewById(R.id.sign_up_City);
        mButtonSignUp = (Button) findViewById(R.id.button_signIn);

        client = (ClientData) getIntent().getSerializableExtra("client");

        ImageButton back1 = (ImageButton) findViewById(R.id.backToMain);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.getMorada().setMorada_1(mTextAddress.getText().toString());
                client.getMorada().setCod_postal(mTextCity.getText().toString());
                client.getMorada().setLocalidade(mTextCity.getText().toString());

                Intent intent = new Intent();
                intent.putExtra("client", client);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String address = mTextAddress.getText().toString();
                // add method to check illegal character
                final String postCode = mTextpostCode.getText().toString();
                // add method to check all are numbers
                final String City = mTextCity.getText().toString();
                // add method to check email format

                client.getMorada().setMorada_1(mTextAddress.getText().toString());
                client.getMorada().setCod_postal(mTextpostCode.getText().toString());
                client.getMorada().setLocalidade(mTextCity.getText().toString());
                try{
                    register(client);
                }
                catch (Exception e)
                {
                    Log.e("Error: ", e.toString());
                }


            }
        });

    }

    public void register(final ClientData client){
        final String[] hashcode = {null};
        ClientService clientService;
        clientService = API.getClientService();
        Call<ResponseBody> call = clientService.createClient(client);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        hashcode[0] = response.body().string();
                        Toast.makeText(SignUpActivity2.this, hashcode[0], Toast.LENGTH_LONG).show();
                        mSPManipulation.saveName(client.getPrimeiro_nome() + " " + client.getApelido());
                        mSPManipulation.saveEmail(client.getMail());
                        mSPManipulation.savePwd(client.getPassword());
                        mSPManipulation.saveMobile(client.getContacto());
                        mSPManipulation.saveHashCode(hashcode[0]);
                        Intent i = new Intent(SignUpActivity2.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(SignUpActivity2.this,response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        mTextAddress.setText( client.getMorada().getMorada_1());
        mTextpostCode.setText( client.getMorada().getCod_postal());
        mTextCity.setText(  client.getMorada().getLocalidade());
    }

}
