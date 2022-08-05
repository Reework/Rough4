package com.shamrock.reework.payment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.api.response.Language;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.MyHolder>
{

    Context context;
    List<paymentDetails> languages;


    public PaymentHistoryAdapter(Context context, List<paymentDetails> languages)
    {
        this.context = context;
        this.languages = languages;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView paymentmethod,orderid,date,refrenceid,amount,payment_status;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            paymentmethod = itemView.findViewById(R.id.paymentmethod);
            orderid = itemView.findViewById(R.id.orderid);
            date = itemView.findViewById(R.id.date);
            refrenceid = itemView.findViewById(R.id.refrenceid);
            amount = itemView.findViewById(R.id.amount);
            payment_status = itemView.findViewById(R.id.payment_status);
        }

        @Override
        public void onClick(View v)
        {
        }
    }

    @NonNull
    @Override
    public PaymentHistoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_payment, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHistoryAdapter.MyHolder myHolder, int i)
    {
        try {
            float amount = Float.parseFloat(languages.get(i).getAmount());

            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

            String moneyString = formatter.format(amount);

            System.out.println(moneyString);

            int amountnew=Integer.parseInt(languages.get(i).getAmount())/100;

            myHolder.date.setText(languages.get(i).getTransactionDate());

            if (languages.get(i).getOrderId()!=null){
                myHolder.orderid.setText(languages.get(i).getOrderId());

            }else {
                myHolder.orderid.setText("-");

            }
            if (languages.get(i).getPaymentMethod()!=null){
                myHolder.paymentmethod.setText(languages.get(i).getPaymentMethod());

            }else {
                myHolder.paymentmethod.setText("-");

            }
            myHolder.refrenceid.setText(languages.get(i).getReferenceId());
            myHolder.amount.setText("Rs."+languages.get(i).getAmount());
            myHolder.payment_status.setText(languages.get(i).getStatus());
        }catch (Exception e){
            e.printStackTrace();
        }








    }

    @Override
    public int getItemCount()
    {
        return languages.size();
    }
}
