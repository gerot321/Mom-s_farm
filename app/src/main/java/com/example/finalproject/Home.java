package com.example.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Database.Database;
import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.Model.Category;
import com.example.finalproject.Model.Order;
import com.example.finalproject.Model.Product;
import com.example.finalproject.Model.User;
import com.example.finalproject.Model.Voucher;
import com.example.finalproject.holder.MenuViewHolder;
import com.example.finalproject.page.Cart;
import com.example.finalproject.page.SignIn;
import com.example.finalproject.util.PreferenceUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category,user,prod,voucher;

    Spinner spin;
    ImageView iamge;
    EditText edit;
    Dialog QRMyDialog;
    String addr = " ";
    TextView txtFullName,saldo,productname;
    String ID;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    String option ;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;
    CardView topup,addpr,confirms;
    private Dialog MyDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Menu Utama");
        ID= PreferenceUtil.getUser().getPhone();
        setSupportActionBar(toolbar);

        // Init firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");
        prod = database.getReference("Product");
        user = database.getReference("User");
        voucher = database.getReference("Voucher");
;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Cart.class);
                intent.putExtra("userID", ID );
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set who is logged in
        View headerView = navigationView.getHeaderView(0);

        txtFullName = headerView.findViewById(R.id.txtFullName);
        txtFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, UserProfile.class);
                i.putExtra("phoneId",ID);
                startActivity(i);
            }
        });

        saldo = headerView.findViewById(R.id.txtSaldo);
        user.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User users = dataSnapshot.getValue(User.class);
//                saldo.setText("Saldo " + users.getSaldo().toString());
//                txtFullName.setText(users.getName().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // Load menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();
        option = getIntent().getStringExtra("option");
        final String barcode = getIntent().getStringExtra("code");
        if(barcode != null){
            if(option.equals("addProd")){
                MyDialog = new Dialog(Home.this);
                MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                MyDialog.setContentView(R.layout.dialog);
                MyDialog.setTitle("My Custom Dialog");

                Button hello = (Button) MyDialog.findViewById(R.id.oke12);
                Button close = (Button) MyDialog.findViewById(R.id.cancan);
                iamge = (ImageView)MyDialog.findViewById(R.id.lol);

                spin = (Spinner) MyDialog.findViewById(R.id.spinner);
                edit = (EditText) MyDialog.findViewById(R.id.dit);
                String[] Des = {"GedungA", "GedungB", "GedungC", "GedungD", "GedungE", "GedungF", "FJ", "DP"};

                ArrayAdapter< String > adapter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_spinner_item, Des);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);
                prod.child(barcode).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final Product prods = dataSnapshot.getValue(Product.class);
                        productname = (TextView)MyDialog.findViewById(R.id.namepro);
                        productname.setText(prods.getName());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                hello.setEnabled(true);
                close.setEnabled(true);

                hello.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prod.child(barcode).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final Product prods = dataSnapshot.getValue(Product.class);

                                category.child(prods.getMerchantId()).child("origin").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String origin = dataSnapshot.getValue().toString();
                                        addr = spin.getSelectedItem().toString()+edit.getText().toString();
                                        String gedung = spin.getSelectedItem().toString();
                                        int total=0;
                                        String ShippingPrice = " ";

                                        if (origin.equals("FJ") && gedung.equals("GedungA")) {
                                            total = (Integer.parseInt(prods.getPrice()))+2000;
                                            ShippingPrice = "2000";
                                        }
                                        else if (origin.equals("FJ") && gedung.equals("GedungB")){
                                            total = (Integer.parseInt(prods.getPrice()))+2000;
                                            ShippingPrice = "2000";

                                        } else if (origin.equals("FJ") && gedung.equals("GedungC")){
                                            total = (Integer.parseInt(prods.getPrice()))+2000;
                                            ShippingPrice = "2000";

                                        } else if (origin.equals("FJ") && gedung.equals("GedungD")){
                                            total = (Integer.parseInt(prods.getPrice()))+4000;
                                            ShippingPrice = "4000";

                                        } else if (origin.equals("FJ") && gedung.equals("GedungE")){
                                            total = (Integer.parseInt(prods.getPrice()))+5000;
                                            ShippingPrice = "5000";

                                        } else if (origin.equals("FJ") && gedung.equals("GedungF")){
                                            total = (Integer.parseInt(prods.getPrice()))+5000;
                                            ShippingPrice = "5000";

                                        } else if (origin.equals("FJ") && gedung.equals("DP")){
                                            total = (Integer.parseInt(prods.getPrice()))+3000;
                                            ShippingPrice = "3000";

                                        } else if (origin.equals("FJ") && gedung.equals("FJ")){
                                            total = (Integer.parseInt(prods.getPrice()))+1000;
                                            ShippingPrice = "1000";

                                        }


                                        new Database(getBaseContext()).addToCart(new Order(
                                                barcode,
                                                prods.getName(),
                                                "1",
                                                String.valueOf(total)
                                                ,"",""
                                        ));

                                        //category.child("status").setValue("Waiting Admin Confirmation");
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        //handle databaseError
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        MyDialog.dismiss();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyDialog.cancel();
                    }
                });

                MyDialog.show();
            }
            else if(option.equals("topup")){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle("TopUp Saldo Confirmation");

                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);

                Context context = layoutInflater.getContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final TextView text = new TextView(Home.this);

                text.setGravity(Gravity.CENTER);
                layout.addView(text);
                alertDialog.setView(layout);

                voucher.child(barcode).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Voucher vocer = dataSnapshot.getValue(Voucher.class);
                        text.setText("Voucher Amount : " + vocer.getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                // Add edit text to alert dialog
                alertDialog.setIcon(R.drawable.ic_add_shopping_cart_black_24dp);

                alertDialog.setPositiveButton("Isi", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        voucher.child(barcode).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final Voucher vocer = dataSnapshot.getValue(Voucher.class);
                                if (vocer.getStatus().equals("valid")) {

                                    user.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (vocer.getStatus().equals("valid")) {
                                                User users = dataSnapshot.getValue(User.class);
                                                int total = 0;
                                                total = Integer.valueOf(users.getSaldo()) + Integer.valueOf(vocer.getValue().toString());
                                                user.child(ID).child("saldo").setValue(String.valueOf(total));
                                                voucher.child(barcode).child("status").setValue("invalid");

                                            }else{
                                                Toast.makeText(Home.this, "Kode voucher tidak sesuai atau sudah di topup", Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    Toast.makeText(Home.this, "saldo telah ditambahkan" + vocer.getValue().toString(), Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(Home.this, "Kode voucher tidak sesuai atau sudah di topup", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

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
            else if(option.equals("confirm")){
                Toast.makeText(Home.this, "Shipping order confirmed", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void loadMenu() {

         adapter = new FirebaseRecyclerAdapter<Category,
                 MenuViewHolder>(Category.class,
             R.layout.menu_item,
             MenuViewHolder.class,
             category) {
                 @Override
                 protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                     viewHolder.txtMenuName.setText(model.getName());
                     Picasso.with(getBaseContext()).load(model.getImage())
                             .into(viewHolder.imageView);

                     final Category clickItem = model;

                     viewHolder.setItemClickListener(new ItemClickListener() {
                         @Override
                         public void onClick(View view, int position, boolean isLongCLick) {
                             // Get categoryId and send to the new activity
                             Intent intent = new Intent(Home.this, ShoeList.class);
                             // get the key of this item
                             intent.putExtra("CategoryId", adapter.getRef(position).getKey());

                             startActivity(intent);
                         }
                     });
                 }
        };

        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter = new FirebaseRecyclerAdapter<Category,
                        MenuViewHolder>(Category.class,
                        R.layout.menu_item,
                        MenuViewHolder.class,
                        category) {
                    @Override
                    protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                        viewHolder.txtMenuName.setText(model.getName());
                        Picasso.with(getBaseContext()).load(model.getImage())
                                .into(viewHolder.imageView);

                        final Category clickItem = model;

                        viewHolder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongCLick) {
                                // Get categoryId and send to the new activity
                                Intent intent = new Intent(Home.this, ShoeList.class);
                                // get the key of this item
                                intent.putExtra("CategoryId", adapter.getRef(position).getKey());

                                startActivity(intent);
                            }
                        });
                    }
                };

                recycler_menu.setAdapter(adapter);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter = new FirebaseRecyclerAdapter<Category,
                        MenuViewHolder>(Category.class,
                        R.layout.menu_item,
                        MenuViewHolder.class,
                        category.orderByChild("name").startAt(query).endAt(query+"\uf8ff")) {
                    @Override
                    protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                        viewHolder.txtMenuName.setText(model.getName());
                        Picasso.with(getBaseContext()).load(model.getImage())
                                .into(viewHolder.imageView);

                        final Category clickItem = model;

                        viewHolder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongCLick) {
                                // Get categoryId and send to the new activity
                                Intent intent = new Intent(Home.this, ShoeList.class);
                                // get the key of this item
                                intent.putExtra("CategoryId", adapter.getRef(position).getKey());

                                startActivity(intent);
                            }
                        });
                    }
                };

                recycler_menu.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter = new FirebaseRecyclerAdapter<Category,
                        MenuViewHolder>(Category.class,
                        R.layout.menu_item,
                        MenuViewHolder.class,
                        category.orderByChild("name").startAt(newText).endAt(newText+"\uf8ff")) {
                    @Override
                    protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                        viewHolder.txtMenuName.setText(model.getName());
                        Picasso.with(getBaseContext()).load(model.getImage())
                                .into(viewHolder.imageView);

                        final Category clickItem = model;

                        viewHolder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongCLick) {
                                // Get categoryId and send to the new activity
                                Intent intent = new Intent(Home.this, ShoeList.class);
                                // get the key of this item
                                intent.putExtra("CategoryId", adapter.getRef(position).getKey());

                                startActivity(intent);
                            }
                        });
                    }
                };

                recycler_menu.setAdapter(adapter);
                return false;
            }
        });
       /* int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        int closeButtonId = getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButtonImage = (ImageView) searchView.findViewById(closeButtonId);
        closeButtonImage.setImageResource(R.drawable.ic_clear_black_24dp);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(Color.WHITE);
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.BLACK);
                searchText.setHintTextColor(Color.BLACK);
            }
        }*/
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {

        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(Home.this, Cart.class);
            intent.putExtra("userID", ID );
            startActivity(intent);
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(Home.this, OrderStatus.class);
            startActivity(intent);
        } else if (id == R.id.nav_log_out) {
            // Log off
            Intent intent = new Intent(Home.this, SignIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if (id == R.id.nav_Confirmation) {
            Intent intent = new Intent(Home.this, ConfirmationSection.class);
            intent.putExtra("userID", ID );
            startActivity(intent);
        }
        else if (id == R.id.nav_top_up) {
            Intent intent = new Intent(Home.this, TopUpActivity.class);
            intent.putExtra("userID", ID );
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.qr:
                QRMyDialog = new Dialog(Home.this);
                QRMyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                QRMyDialog.setContentView(R.layout.scan_option_popup);
                QRMyDialog.setTitle("Scan Option");

                topup = (CardView)  QRMyDialog.findViewById(R.id.topup);
                topup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Home.this, ScanActivity.class);
                        i.putExtra("phoneId", ID );
                        i.putExtra("activity","home");
                        i.putExtra("option","topup");
                        startActivity(i);
                    }
                });
                addpr = (CardView)  QRMyDialog.findViewById(R.id.addprod);
                addpr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Home.this, ScanActivity.class);
                        i.putExtra("phoneId", ID );
                        i.putExtra("activity","home");
                        i.putExtra("option","addProd");
                        startActivity(i);
                    }
                });
                confirms = (CardView)  QRMyDialog.findViewById(R.id.confirmshipping);
                confirms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Home.this, ScanActivity.class);
                        i.putExtra("phoneId", ID );
                        i.putExtra("activity","home");
                        i.putExtra("option","confirm");
                        startActivity(i);
                    }
                });
                QRMyDialog.show();
                return true;
            case R.id.cart:
                Intent ia = new Intent(this, Cart.class);
                ia.putExtra("userID", ID );
                this.startActivity(ia);
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void MyCustomAlertDialog(){

    }
}
