package com.example.finalproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Common.Common;
import com.example.finalproject.Database.Database;
import com.example.finalproject.Model.Order;
import com.example.finalproject.Model.Request;
import com.example.finalproject.Model.User;
import com.example.finalproject.Model.productRequest;
import com.example.finalproject.adapter.CartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Checkout extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference requests,prodReq,prod,user;

    TextView txtTotalPrice;


    CardView Ovo,cod,saldo;
    String Status = " ";
    List<Order> cart = new ArrayList<>();
    String PriCode, orderId;
    int total = 0;
    String ID;
    CartAdapter adapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //Firebase
        ID = getIntent().getStringExtra("userID");
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        prodReq = database.getReference("productReq");
        prod = database.getReference("Product");
        user = database.getReference("User");
        Ovo = (CardView)findViewById(R.id.ovoPayment);
        cod = (CardView)findViewById(R.id.codPayment);
        saldo = (CardView)findViewById(R.id.saldoPayment);


        cart = new Database(this).getCarts();
        for (Order order:cart)
            total+=(Integer.parseInt(order.getPrice()));

        Ovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new request
                Status = "Waiting Payment";
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Checkout.this);
                alertDialog.setTitle("Checkout Confirmation");

                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);

                Context context = layoutInflater.getContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final TextView text = new TextView(Checkout.this);

                text.setGravity(Gravity.CENTER);
                layout.addView(text);
                text.setText("Total Belanja : " + String.valueOf(total));
                alertDialog.setView(layout);
                alertDialog.setIcon(R.drawable.ic_add_shopping_cart_black_24dp);
                final Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        String.valueOf(total),
                        cart,
                        Status,
                        "OVO"
                );


                Random r = new Random();
                int unic = r.nextInt(80 - 1) + 1;
                PriCode = Common.currentUser.getPhone();
                orderId = PriCode+Integer.toString(unic);
                final ProgressDialog mDialog = new ProgressDialog(Checkout.this);;

                // Add edit text to alert dialog
                alertDialog.setIcon(R.drawable.ic_add_shopping_cart_black_24dp);

                alertDialog.setPositiveButton("oke", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        requests.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Check if already user phone
                                if(dataSnapshot.child(orderId.toString()).exists()){


                                    Toast.makeText(Checkout.this, "Account already exist!", Toast.LENGTH_SHORT).show();
                                }else {

                                    requests.child(orderId).setValue(request);

                                    Toast.makeText(Checkout.this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        // Submit to Firebase
                        new Database(getBaseContext()).clearCart();
                        Toast.makeText(Checkout.this, "Thank you, your order has been placed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Checkout.this, CheckoutInfo.class);
                        intent.putExtra("userID",ID);
                        startActivity(intent);
                    }
                });

                alertDialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final User users = dataSnapshot.getValue(User.class);
                        if (Integer.valueOf(users.getSaldo())<total){
                            Toast.makeText(Checkout.this, "saldo tidak mencukupi", Toast.LENGTH_SHORT).show();

                        }
                        else if(Integer.valueOf(users.getSaldo())>total){
                            Status = "Confirmed Order";

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Checkout.this);
                            alertDialog.setTitle("Checkout Confirmation");

                            LayoutInflater layoutInflater =
                                    (LayoutInflater) getBaseContext()
                                            .getSystemService(LAYOUT_INFLATER_SERVICE);

                            Context context = layoutInflater.getContext();
                            LinearLayout layout = new LinearLayout(context);
                            layout.setOrientation(LinearLayout.VERTICAL);

                            final TextView text = new TextView(Checkout.this);

                            text.setGravity(Gravity.CENTER);
                            layout.addView(text);
                            text.setText("Total Belanja : " + String.valueOf(total));
                            alertDialog.setView(layout);
                            alertDialog.setIcon(R.drawable.ic_add_shopping_cart_black_24dp);
                            final Request request = new Request(
                                    Common.currentUser.getPhone(),
                                    Common.currentUser.getName(),
                                    String.valueOf(total),
                                    cart,
                                    Status,
                                    "chaperone saldo"
                            );


                            Random r = new Random();
                            int unic = r.nextInt(80 - 1) + 1;
                            PriCode = Common.currentUser.getPhone();
                            orderId = PriCode+Integer.toString(unic);

                            alertDialog.setPositiveButton("oke", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    //Clear cart
                                    requests.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // Check if already user phone
                                            if(dataSnapshot.child(orderId.toString()).exists()){
                                                Toast.makeText(Checkout.this, "Account already exist!", Toast.LENGTH_SHORT).show();
                                            }else {
                                                user.child(ID).child("saldo").setValue(String.valueOf(Integer.valueOf(users.getSaldo())- total));
                                                requests.child(orderId).setValue(request);
                                                requests.child(orderId.toString()).child("product").addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                                                            final Order orders = child.getValue(Order.class);
                                                            prod.child(orders.getProductId().toString()).child("MerchantId").addValueEventListener(new ValueEventListener() {
                                                                @Override

                                                                public void onDataChange(DataSnapshot dataSnapshot) {



//                                                                    productRequest req= new productRequest(orderId.toString(),dataSnapshot.getValue().toString(),orders.getProductId(),
//                                                                            orders.getProductName().toString(),orders.getQuantity().toString()
//                                                                            ,orders.getPrice().toString()) ;
//                                                                    prodReq.push().setValue(req);
//                                                                    finish();
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });

                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                                Toast.makeText(Checkout.this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    // Submit to Firebase
                                    new Database(getBaseContext()).clearCart();
                                    Toast.makeText(Checkout.this, "Thank you, your order has been placed", Toast.LENGTH_SHORT).show();

                                }
                            });

                            alertDialog.setNegativeButton("batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                }
                            });

                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Status = "Confirmed Order";

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Checkout.this);
                alertDialog.setTitle("Checkout Confirmation");

                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);

                Context context = layoutInflater.getContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final TextView text = new TextView(Checkout.this);

                text.setGravity(Gravity.CENTER);
                layout.addView(text);
                text.setText("Total Belanja : " + String.valueOf(total));
                alertDialog.setView(layout);
                alertDialog.setIcon(R.drawable.ic_add_shopping_cart_black_24dp);
                final Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        String.valueOf(total),
                        cart,
                        Status,
                        "cash on delivery"
                );


                Random r = new Random();
                int unic = r.nextInt(80 - 1) + 1;
                PriCode = Common.currentUser.getPhone();
                orderId = PriCode+Integer.toString(unic);





                alertDialog.setPositiveButton("oke", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //Clear cart
                        requests.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Check if already user phone
                                if(dataSnapshot.child(orderId.toString()).exists()){
                                    Toast.makeText(Checkout.this, "Account already exist!", Toast.LENGTH_SHORT).show();
                                }else {

                                    requests.child(orderId).setValue(request);
                                    requests.child(orderId.toString()).child("product").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot child: dataSnapshot.getChildren()) {
                                                final Order orders = child.getValue(Order.class);
                                                prod.child(orders.getProductId().toString()).child("MerchantId").addValueEventListener(new ValueEventListener() {
                                                    @Override

                                                    public void onDataChange(DataSnapshot dataSnapshot) {

//                                                        productRequest req= new productRequest(orderId.toString(),dataSnapshot.getValue().toString(),orders.getProductId(),
//                                                                orders.getProductName().toString(),orders.getQuantity().toString()
//                                                                ,orders.getPrice().toString(),orders.getAddress(),"diteruskan ke merchant",orders.getShippingPrice()) ;
//                                                        prodReq.push().setValue(req);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });

                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    Intent intent = new Intent(orderId).putExtra("cart", (Serializable) cart);
                                    LocalBroadcastManager.getInstance(Checkout.this).sendBroadcast(intent);
                                    Toast.makeText(Checkout.this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        // Submit to Firebase
                        new Database(getBaseContext()).clearCart();
                        Toast.makeText(Checkout.this, "Thank you, your order has been placed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                alertDialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });

                alertDialog.show();

            }
        });

    }





}
