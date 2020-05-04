package com.akashcp.windchimeslab;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akashcp.windchimeslab.model.Model;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import io.blackbox_vision.datetimepickeredittext.view.DatePickerEditText;
import io.blackbox_vision.datetimepickeredittext.view.DatePickerInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment  {
    TextInputLayout name, phonenumber, cost, college,email;
   String sname, sphonenumber, sdate, scost, scollege, sdomain,semail;
    Button button;
    MaterialSpinner spinner;
    String sex;
    EditText date;
    String tdate;
    String finalsex;
    private DatePickerEditText datePickerInputEditText;
    final Calendar myCalendar = Calendar.getInstance();
    String finalcolor;
    Integer registernumber;
    String finaldomain;


    public RegisterFragment() {
        // Required empty public constructor
    }

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setHasOptionsMenu(true);



        name = (TextInputLayout) view.findViewById(R.id.name);
        phonenumber = (TextInputLayout) view.findViewById(R.id.phone);
        datePickerInputEditText=(DatePickerEditText)view.findViewById(R.id.datePickerEditText);
        cost = (TextInputLayout) view.findViewById(R.id.cost);
        college = (TextInputLayout) view.findViewById(R.id.college);
        email = (TextInputLayout) view.findViewById(R.id.email);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:
                        sex = "Male";
                        Toast.makeText(getContext(), "Male", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio2:
                        sex = "Female";
                        Toast.makeText(getContext(), "Female", Toast.LENGTH_SHORT).show();
                        break;

                   default:
                        sex = "Male";
                        Toast.makeText(getContext(), "Male", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        });

        spinner = (MaterialSpinner) view.findViewById(R.id.spinner);
        button = (Button) view.findViewById(R.id.button);
        spinner.setItems("<--Domain-->","Android","Internet of things","JAVA","OpenGL","Power Point Presentation","Python","SQL","Web Application");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                sdomain = item.toLowerCase();

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // codeRef.setValue("100");

                sname = name.getEditText().getText().toString().trim().toLowerCase();
                sphonenumber = phonenumber.getEditText().getText().toString().trim();
                sdate = datePickerInputEditText.getText().toString().trim();
                scost = cost.getEditText().getText().toString().trim();
                scollege = college.getEditText().getText().toString().trim().toLowerCase();
                semail = email.getEditText().getText().toString().trim();


                if (sname.equals("") || sex==null || sphonenumber.equals("")||sdate.equals("")||scost.equals("")||scollege.equals("")||semail.equals(""))
                {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getContext())
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                            .setTitle("Please enter all detiles.")
                            .setMessage("Every field must be filled for further processes.");
                    builder.show();
                }


                else {
                     finaldomain = sdomain.toLowerCase();
                    finalsex =  sex.toLowerCase();
                    if(finaldomain.equals("android")) {finalcolor="#79C257"; }
                    else if(finaldomain.equals("internet of things")){finalcolor="#00AEEF";}
                    else if(finaldomain.equals("java")){finalcolor="#FE0200";}
                    else if(finaldomain.equals("opengl")){finalcolor="#5586A4";}
                    else if(finaldomain.equals("power point presentation")){finalcolor="#D04424";}
                    else if(finaldomain.equals("python")){finalcolor="#376D9C";}
                    else if(finaldomain.equals("sql")){finalcolor="#F8B100";}
                    else if(finaldomain.equals("web application")){finalcolor="#FA704B";}
                    String bool="false";
                    Model model = new Model(sphonenumber,sname,finalsex,scost,sdate,scollege,sdomain,finalcolor,bool,registernumber,semail);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Project_list").push();
                    final ProgressDialog progressDialog=new ProgressDialog(getContext());
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    myRef.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Data Inserted", Toast.LENGTH_SHORT).show();
                                name.getEditText().setText("");
                                phonenumber.getEditText().setText("");
                                datePickerInputEditText.setText("");
                                cost.getEditText().setText("");
                                college.getEditText().setText("");
                                email.getEditText().setText("");
                                    FirebaseDatabase updateregister = FirebaseDatabase.getInstance();
                    DatabaseReference kupdateregister = updateregister.getReference("code");
                                registernumber+=1;
                    kupdateregister.setValue(registernumber);

                            } else {
                                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getContext())
                                        .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                                        .setTitle("Insertion Failed...!")
                                        .setMessage(task.getException().toString());
                                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });

                    Log.i("Date", sname + "" + sphonenumber + "" + sdate + "" + scost + "" + scollege + "" + finalsex);

                }
            }



        });

                return view;
            }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase codevalue = FirebaseDatabase.getInstance();
        DatabaseReference codevalueRef = codevalue.getReference("code");
        codevalueRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                registernumber=value;
                // Toast.makeText(getContext(), " "+value, Toast.LENGTH_SHORT).show();
                Log.i("valuesss ",value.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("Failed to read value.", error.toException().toString());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu2, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.stat:
                name.getEditText().setText("");
                 phonenumber.getEditText().setText("");
               datePickerInputEditText.setText("");
               cost.getEditText().setText("");
                 college.getEditText().setText("");
                 email.getEditText().setText("");
                Toast.makeText(getContext(), "Clear", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

