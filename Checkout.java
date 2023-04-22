package com.example.mcdonalds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Checkout extends AppCompatActivity {
    ImageView fullImageView;
    TextView name1, pay_email1;
    EditText pay_email, fullname, address, cardHolderName, pay_name, pay_card_number, pay_cvv, pay_cvv2;
    TextView pr;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        fullImageView = findViewById(R.id.fullImageView);
        name1 = findViewById(R.id.name);
        pr = findViewById(R.id.price);
        pay_email1 = findViewById(R.id.pay_email);
        pay_email = findViewById(R.id.pay_email);
        fullname = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        cardHolderName = findViewById(R.id.cardHolderName);
        pay_name = findViewById(R.id.pay_name);
        pay_card_number = findViewById(R.id.pay_card_number);
        pay_cvv = findViewById(R.id.pay_cvv);
        pay_cvv2 = findViewById(R.id.pay_cvv2);

        Glide.with(this).load(getIntent().getStringExtra("imageurl")).into(fullImageView);

        String name = getIntent().getStringExtra("name");
        name1.setText(name);

        String price = getIntent().getStringExtra("finalprice").toString();
        pr.setText(price);

        button = findViewById(R.id.finalpaybtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullname.getText().toString().isEmpty()){
                    Toast.makeText(Checkout.this, "Please enter fullname",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(pay_email.getText().toString().isEmpty()){
                    Toast.makeText(Checkout.this, "Please enter email id",Toast.LENGTH_SHORT).show();
                }
                else if(!(Patterns.EMAIL_ADDRESS.matcher(pay_email.getText().toString()).matches())){
                    Toast.makeText(Checkout.this, "Please check your email id",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(address.getText().toString().isEmpty()){
                    Toast.makeText(Checkout.this, "Please enter address",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(pay_name.getText().toString().isEmpty()){
                    Toast.makeText(Checkout.this, "Please enter phone number",Toast.LENGTH_SHORT).show();
                    return;
                }

                   else if(!(Patterns.PHONE.matcher(pay_name.getText().toString()).matches()) || (pay_name.getText().toString().length()!=10)){
                        Toast.makeText(Checkout.this, "Please check your phone number",Toast.LENGTH_SHORT).show();
                        return;
                    }

                else if(pay_card_number.getText().toString().isEmpty()){
                    Toast.makeText(Checkout.this, "Please enter card number",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!(Patterns.PHONE.matcher(pay_card_number.getText().toString()).matches()) || (pay_card_number.getText().toString().length()!=16)){
                    Toast.makeText(Checkout.this, "Please check your card number",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(cardHolderName.getText().toString().isEmpty()){
                    Toast.makeText(Checkout.this, "Please enter card holder name",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(pay_cvv.getText().toString().isEmpty()){
                    Toast.makeText(Checkout.this, "Please enter CVV number",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!(Patterns.PHONE.matcher(pay_cvv.getText().toString()).matches()) || (pay_cvv.getText().toString().length()!=3)){
                    Toast.makeText(Checkout.this, "Please check your three digit CVV number",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(pay_cvv2.getText().toString().isEmpty()){
                    Toast.makeText(Checkout.this, "Please enter Expiry Date",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!(Patterns.PHONE.matcher(pay_cvv2.getText().toString()).matches()) || (pay_cvv2.getText().toString().length()!=4)){
                    Toast.makeText(Checkout.this, "Please check expiry date",Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    Intent intent;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                    String currentDateandTime = sdf.format(new Date());
                    final String username = "np2622000@gmail.com";
                    final String password = "hmigskxrshctpgfg";
                    String messageToSend = pay_email1.getText().toString();
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");
                    Session session = Session.getInstance(props,
                            new Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }
                            });
                    try {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(username));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pay_email1.getText().toString()));
                        message.setSubject("Invoice for your reference");
                        message.setText("------------------------------------------------------------------------------ "+"Your purchase is "+name+" and the subtotal is "+ price +" on "+currentDateandTime+"                                   ------------------------------------------------------------------------------");
                        Transport.send(message);
                        Toast.makeText(getApplicationContext(),"Payment Successfully",Toast.LENGTH_LONG).show();
                        intent = new Intent(Checkout.this, menu.class);
                        startActivity(intent);
                    }catch (MessagingException e){
                        throw new RuntimeException(e);
                    }

                }
                }

        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }
}