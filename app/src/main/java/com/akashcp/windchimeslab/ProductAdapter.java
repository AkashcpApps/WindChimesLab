package com.akashcp.windchimeslab;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.akashcp.windchimeslab.model.Model;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;
import com.shreyaspatil.MaterialDialog.AbstractDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.net.FileNameMap;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import io.blackbox_vision.datetimepickeredittext.view.DatePickerEditText;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context mCtx;
    List<Model> productList;

    public ProductAdapter(Context mCtx, List<Model> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.card_view,
                parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final Model product = productList.get(position);

        holder.tv_name.setText(" "+product.getName());
        holder.tv_phone.setText(" "+product.getPhonenumber());

        holder.tv_sex.setText(" "+product.getSex());
        holder.tv_date.setText(" "+product.getDate());
        holder.tv_cost.setText(" "+product.getCost());
        holder.tv_college.setText(" "+product.getCollege());
        holder.Tv_domain.setText(" "+product.getDomain());
        holder.tv_registernumber.setText(" "+product.getRegisternumber());
        holder.tv_email.setText(" "+product.getEmail());
        String tv_color=product.getMcolor();
        if(tv_color.equals("#79C257")) {holder.circleImageView.setBackgroundResource(R.drawable.android); }
        if(tv_color.equals("#00AEEF")) {holder.circleImageView.setBackgroundResource(R.drawable.wifi); }
        if(tv_color.equals("#FE0200")) {holder.circleImageView.setBackgroundResource(R.drawable.java); }
        if(tv_color.equals("#5586A4")) {holder.circleImageView.setBackgroundResource(R.drawable.opengl); }
        if(tv_color.equals("#D04424")) {holder.circleImageView.setBackgroundResource(R.drawable.ppt); }
        if(tv_color.equals("#376D9C")) {holder.circleImageView.setBackgroundResource(R.drawable.python); }
        if(tv_color.equals("#F8B100")) {holder.circleImageView.setBackgroundResource(R.drawable.database); }
        if(tv_color.equals("#FA704B")) { holder.circleImageView.setBackgroundResource(R.drawable.settings);}

        holder.cardView.setCardBackgroundColor(Color.parseColor(tv_color));
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final Dialog dialog = new Dialog(mCtx);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.main_dailog);
                Button main_call = (Button) dialog.findViewById(R.id.main_call);
                main_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                /*final Dialog dialog123 = new Dialog(mCtx);
                dialog123.setCancelable(false);
                dialog123.setContentView(R.layout.update_dailog);
                final EditText name = (EditText) dialog123.findViewById(R.id.update_name);
                final EditText phone = (EditText) dialog123.findViewById(R.id.update_phonenumber);
                final EditText cost = (EditText) dialog123.findViewById(R.id.update_cost);
                final EditText email = (EditText) dialog123.findViewById(R.id.update_email);
                final EditText college = (EditText) dialog123.findViewById(R.id.update_college);
                final DatePickerEditText date = (DatePickerEditText) dialog123.findViewById(R.id.update_time);
                Button update = (Button) dialog123.findViewById(R.id.button);
                Button cancel = (Button) dialog123.findViewById(R.id.update_cancel);
                dialog123.show();
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String name1 = name.getText().toString();
                        final String phone1 = phone.getText().toString();
                        final String cost1 = cost.getText().toString();
                        final String email1 = email.getText().toString();
                        final String college1 = college.getText().toString();

                       final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        final DatabaseReference databaseReference = firebaseDatabase.getReference("Project_list");
                        final Query userQuery = databaseReference.orderByChild("registernumber").equalTo(product.getRegisternumber());
                        ValueEventListener valueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String key = ds.getKey();
                                    Log.i("tags", key);
                                    databaseReference.child(key).child("name").setValue(name1);
                                    databaseReference.child(key).child("phonenumber").setValue(phone1);
                                    databaseReference.child(key).child("cost").setValue(cost1);
                                    databaseReference.child(key).child("email").setValue(email1);
                                    databaseReference.child(key).child("college").setValue(college1);

                                    //showNotification(mCtx,"Wind Chimes Lab",product.getName()+" Payment Cancelled...!");
                                    // Toast.makeText(mCtx, product.getName()+" Payment Succesful...!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Log.d(TAG, databaseError.getMessage());
                            }
                        };
                        userQuery.addListenerForSingleValueEvent(valueEventListener);

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog123.dismiss();
                    }
                });*/


                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", product.getPhonenumber(), null));
                        mCtx.startActivity(intent);
                        dialog.dismiss();

            }
                });
                Button main_email = (Button) dialog.findViewById(R.id.main_email);
                main_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog1 = new Dialog(mCtx);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.setCancelable(true);
                        dialog1.setContentView(R.layout.mail_to);
                      final   EditText title=(EditText)dialog1.findViewById(R.id.dailor_edittext_title);
                        final EditText subject=(EditText)dialog1.findViewById(R.id.dailor_edittext_body);
                        Button dailor_edittext_submit=(Button)dialog1.findViewById(R.id.dailor_edittext_submit) ;
                        Button dailor_edittext_cancel=(Button)dialog1.findViewById(R.id.dailor_edittext_cancel) ;
                        TextView textView=(TextView)dialog1.findViewById(R.id.mailto);
                        textView.setText("Email to: "+product.getEmail());
                        dialog1.show();

                        dailor_edittext_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                                intent.putExtra(Intent.EXTRA_TEXT, title.getText().toString());
                                intent.setData(Uri.parse("mailto:"+product.getEmail()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mCtx.startActivity(intent);

                            }
                        });
                        dailor_edittext_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog1.dismiss();

                            }
                        });


                    }
                });
                Button main_delete = (Button) dialog.findViewById(R.id.main_delete);
                main_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(mCtx).setTitle("Do you want to delete "+product.getName()).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Project_list");
                                Query applesQuery = ref.orderByChild("registernumber").equalTo(product.getRegisternumber());
                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                            Toast.makeText(mCtx, product.getName()+" record deleted", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e("onCancelled", databaseError.toException().toString());
                                        dialog.dismiss();
                                    }
                                });

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        }).show();




                    }
                });

                dialog.show();




               /* new SmartDialogBuilder(mCtx)
                        .setTitle("Name: "+product.getName()+"\nRNo: "+product.getRegisternumber())
                        .setSubTitle("you can call or Delete the record of "+product.getName()+" once you Delete you Can't ROLL BACK")
                        .setCancalable(true)//set title font//set sub title font
                        .setNegativeButtonHide(false) //hide cancel button
                        .setPositiveButton("Delete", new SmartDialogClickListener() {
                            @Override
                            public void onClick(SmartDialog smartDialog) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Project_list");
                                Query applesQuery = ref.orderByChild("registernumber").equalTo(product.getRegisternumber());

                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                            Toast.makeText(mCtx, product.getName()+" record deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e("onCancelled", databaseError.toException().toString());
                                    }
                                });
                                Toast.makeText(mCtx,"Ok button Click",Toast.LENGTH_SHORT).show();
                                smartDialog.dismiss();
                            }
                        }).setNegativeButton("Call", new SmartDialogClickListener() {
                    @Override
                    public void onClick(SmartDialog smartDialog) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", product.getPhonenumber(), null));
                       mCtx.startActivity(intent);
                        Toast.makeText(mCtx,"Cancel button Click",Toast.LENGTH_SHORT).show();
                        smartDialog.dismiss();

                    }
                }).build().show();
*/

                return true;
            }
        });
        holder.checkBox.setChecked(Boolean.parseBoolean(product.getPaidcheckbox()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                    final DatabaseReference databaseReference=firebaseDatabase.getReference("Project_list");
                    final Query userQuery = databaseReference.orderByChild("registernumber").equalTo(product.getRegisternumber());
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                String key = ds.getKey();
                               Log.i("tags", key);
                                databaseReference.child(key).child("paidcheckbox").setValue("true");
                                showNotification(mCtx,"Wind Chimes Lab",product.getName()+" Payment Cancelled...!");
                                Toast.makeText(mCtx, product.getName()+" Payment Succesful...!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Log.d(TAG, databaseError.getMessage());
                        }
                    };
                    userQuery.addListenerForSingleValueEvent(valueEventListener);



                }
                else
                    {
                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        final DatabaseReference databaseReference=firebaseDatabase.getReference("Project_list");
                        final Query userQuery = databaseReference.orderByChild("registernumber").equalTo(product.getRegisternumber());
                        ValueEventListener valueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String key = ds.getKey();
                                   // Log.d(TAG, key);
                                    databaseReference.child(key).child("paidcheckbox").setValue("false");
                                    showNotification(mCtx,"Wind Chimes Lab",product.getName()+" Payment Cancelled...!");
                                    Toast.makeText(mCtx, product.getName()+" Payment Cancelled...!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                               // Log.d(TAG, databaseError.getMessage());
                            }
                        };
                        userQuery.addListenerForSingleValueEvent(valueEventListener);


                        //mDatabaseRef.child("TABLE_NAME").child("orderStatus").setValue(2);

                       // Toast.makeText(mCtx, product.getName()+" Paied Cancelled...!", Toast.LENGTH_LONG).show();


                    }

            }
        }
        );

    }

    public int getRandomColorCode(){

        Random random = new Random();

        return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));

    }

    @Override
    public int getItemCount() {


            return productList.size();


    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        //  CircleImageView imageView;
        TextView tv_name, tv_phone,tv_sex, tv_date,tv_cost,tv_college,Tv_domain,tv_registernumber,tv_email;

        CardView cardView;
        CircleImageView circleImageView;
        CheckBox checkBox;

        public ProductViewHolder(View itemView) {
            super(itemView);

            //   imageView = itemView.findViewById(R.id.domain);
            tv_name = itemView.findViewById(R.id.cname);
            tv_phone = itemView.findViewById(R.id.cphone);
            tv_sex = itemView.findViewById(R.id.csex);
            tv_date = itemView.findViewById(R.id.cdate);
            tv_cost = itemView.findViewById(R.id.ccost);
            tv_college = itemView.findViewById(R.id.ccollege);
            cardView=itemView.findViewById(R.id.cv);
            Tv_domain=itemView.findViewById(R.id.cdomain);
            circleImageView=itemView.findViewById(R.id.circel_image);
            checkBox=itemView.findViewById(R.id.paidcheckbox);
            tv_registernumber=itemView.findViewById(R.id.cregister);
            tv_email=itemView.findViewById(R.id.cemail);

        }
    }
    public String[] mColors = {
            "FFEBEE", "FFCDD2", "EF9A9A", "E57373", "EF5350", "F44336", "E53935",        //reds
            "D32F2F", "C62828", "B71C1C", "FF8A80", "FF5252", "FF1744", "D50000",
            "FCE4EC", "F8BBD0", "F48FB1", "F06292", "EC407A", "E91E63", "D81B60",        //pinks
            "C2185B", "AD1457", "880E4F", "FF80AB", "FF4081", "F50057", "C51162",
            "F3E5F5", "E1BEE7", "CE93D8", "BA68C8", "AB47BC", "9C27B0", "8E24AA",        //purples
            "7B1FA2", "6A1B9A", "4A148C", "EA80FC", "E040FB", "D500F9", "AA00FF",
            "EDE7F6", "D1C4E9", "B39DDB", "9575CD", "7E57C2", "673AB7", "5E35B1",        //deep purples
            "512DA8", "4527A0", "311B92", "B388FF", "7C4DFF", "651FFF", "6200EA",
            "E8EAF6", "C5CAE9", "9FA8DA", "7986CB", "5C6BC0", "3F51B5", "3949AB",        //indigo
            "303F9F", "283593", "1A237E", "8C9EFF", "536DFE", "3D5AFE", "304FFE",
            "E3F2FD", "BBDEFB", "90CAF9", "64B5F6", "42A5F5", "2196F3", "1E88E5",        //blue
            "1976D2", "1565C0", "0D47A1", "82B1FF", "448AFF", "2979FF", "2962FF",
            "E1F5FE", "B3E5FC", "81D4fA", "4fC3F7", "29B6FC", "03A9F4", "039BE5",        //light blue
            "0288D1", "0277BD", "01579B", "80D8FF", "40C4FF", "00B0FF", "0091EA",
            "E0F7FA", "B2EBF2", "80DEEA", "4DD0E1", "26C6DA", "00BCD4", "00ACC1",        //cyan
            "0097A7", "00838F", "006064", "84FFFF", "18FFFF", "00E5FF", "00B8D4",
            "E0F2F1", "B2DFDB", "80CBC4", "4DB6AC", "26A69A", "009688", "00897B",        //teal
            "00796B", "00695C", "004D40", "A7FFEB", "64FFDA", "1DE9B6", "00BFA5",
            "E8F5E9", "C8E6C9", "A5D6A7", "81C784", "66BB6A", "4CAF50", "43A047",        //green
            "388E3C", "2E7D32", "1B5E20", "B9F6CA", "69F0AE", "00E676", "00C853",
            "F1F8E9", "DCEDC8", "C5E1A5", "AED581", "9CCC65", "8BC34A", "7CB342",        //light green
            "689F38", "558B2F", "33691E", "CCFF90", "B2FF59", "76FF03", "64DD17",
            "F9FBE7", "F0F4C3", "E6EE9C", "DCE775", "D4E157", "CDDC39", "C0CA33",        //lime
            "A4B42B", "9E9D24", "827717", "F4FF81", "EEFF41", "C6FF00", "AEEA00",
            "FFFDE7", "FFF9C4", "FFF590", "FFF176", "FFEE58", "FFEB3B", "FDD835",        //yellow
            "FBC02D", "F9A825", "F57F17", "FFFF82", "FFFF00", "FFEA00", "FFD600",
            "FFF8E1", "FFECB3", "FFE082", "FFD54F", "FFCA28", "FFC107", "FFB300",        //amber
            "FFA000", "FF8F00", "FF6F00", "FFE57F", "FFD740", "FFC400", "FFAB00",
            "FFF3E0", "FFE0B2", "FFCC80", "FFB74D", "FFA726", "FF9800", "FB8C00",        //orange
            "F57C00", "EF6C00", "E65100", "FFD180", "FFAB40", "FF9100", "FF6D00",
            "FBE9A7", "FFCCBC", "FFAB91", "FF8A65", "FF7043", "FF5722", "F4511E",        //deep orange
            "E64A19", "D84315", "BF360C", "FF9E80", "FF6E40", "FF3D00", "DD2600",
            "EFEBE9", "D7CCC8", "BCAAA4", "A1887F", "8D6E63", "795548", "6D4C41",        //brown
            "5D4037", "4E342E", "3E2723",
            "FAFAFA", "F5F5F5", "EEEEEE", "E0E0E0", "BDBDBD", "9E9E9E", "757575",        //grey
            "616161", "424242", "212121",
            "ECEFF1", "CFD8DC", "B0BBC5", "90A4AE", "78909C", "607D8B", "546E7A",        //blue grey
            "455A64", "37474F", "263238"
    };
    public void showNotification(Context context, String title, String body) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);
        notificationManager.notify(notificationId, mBuilder.build());
    }


}
