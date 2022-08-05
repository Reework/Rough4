package com.shamrock.reework.activity.wellcomepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.shamrock.R;
import com.shamrock.reework.activity.LoginModule.LoginActivity;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.NewRegisterActivity;

public class WellcomePageActivity extends AppCompatActivity {
    Button btn_login,btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome_page);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WellcomePageActivity.this, LoginActivity.class));
                finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WellcomePageActivity.this, NewRegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}
