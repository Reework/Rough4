package com.shamrock.reework.payment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.razerpaypsyment.CreatePaymentResonse;
import com.shamrock.reework.util.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class paymentHistoryActivity extends AppCompatActivity {
    RecyclerView recylerview_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        recylerview_payment=findViewById(R.id.recylerview_payment);
//        setContentView(R.layout.row_payment);
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        imgLeft.setImageResource(R.drawable.back_arrow);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("Payment History");
        createPayment();
    }
    private void createPayment() {

        final Utils  utils = new Utils();
        RegistrationService    regService = Client.getClient().create(RegistrationService.class);

        final SessionManager sessionManager=new SessionManager(paymentHistoryActivity.this);
        int  userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        utils.showProgressbar(paymentHistoryActivity.this);
//        Call<Ar> call = regService.getPaymentHistory(userID);
        Call <List<paymentDetails>> call = regService.getPaymentHistory(userID);

        call.enqueue(new Callback<List<paymentDetails>>() {
            @Override
            public void onResponse(Call<List<paymentDetails>> call, Response<List<paymentDetails>> response) {
                utils.hideProgressbar();
                List<paymentDetails> rs = response.body();
                if (rs!=null&&!rs.isEmpty()){
                    recylerview_payment.setAdapter(new PaymentHistoryAdapter(paymentHistoryActivity.this,rs));

                }


            }

            @Override
            public void onFailure(Call<List<paymentDetails>> call, Throwable t) {
                utils.hideProgressbar();
                Toast.makeText(paymentHistoryActivity.this, ""+t.getMessage().toString(), Toast.LENGTH_SHORT).show();


            }
        });



    }

}
