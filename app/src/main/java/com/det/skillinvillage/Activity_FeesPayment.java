package com.det.skillinvillage;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import static com.det.skillinvillage.Activity_Student_List.key_studentid_pay;
import static com.det.skillinvillage.Activity_Student_List.sharedpreferenc_studentid_pay;
import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;

public class Activity_FeesPayment extends AppCompatActivity {

    String str_studentID_myprefs, str_receive_studentid, str_loginuserID;
    EditText amount_et, remarks_et;
    TextView sandbox_tv;
    TextView academic_tv;
    TextView cluster_tv;
    TextView institute_tv;
    TextView level_tv;
    TextView applno_tv;
    TextView stuName_tv;
    TextView receivable_tv;
    TextView balance_tv;
    static TextView receiveddate_tv;
    TextView received_tv;
    Spinner paymentType_SP, paymentMode_SP;
    Button Submit_Feessubmit_bt;
    String[] paymentModeArray = {"Cash","Online"};
    String selected_paymentMode, selected_paymentType;
    String[] paymentTypeArray = {"Payment", "Concession"};
    LinearLayout paymentmode_LL;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    static String str_receiveddate;
    String str_receive_sandbox;
    String str_receive_academic;
    String str_receive_cluster;
    String str_receive_institute;
    String str_receive_level;
    String str_receive_applno;
    String str_receive_studentname;
    String str_receiveAble;
    String str_received;
    String str_receive_balance, str_receive_studentstatus,save_receive_studentstatus;
    static String yyyyMMdd_receiveddate = "";
    static String str_receiveddate_display;
    LinearLayout fees_submit_ll;
    EditText receipt_feepayment_et;
    TextView receiptlabel_feepayment_TV;

    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpref_stuid_pay_Obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feespayment);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Fee Submission");

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

//        SharedPreferences myprefs_userid = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_loginuserID = myprefs_userid.getString("login_userid", "nothing");

        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

//        SharedPreferences myprefs = this.getSharedPreferences("studentid", Context.MODE_PRIVATE);
//        str_studentID_myprefs = myprefs.getString("str_studentID", "nothing");

        sharedpref_stuid_pay_Obj=getSharedPreferences(sharedpreferenc_studentid_pay, Context.MODE_PRIVATE);
        str_studentID_myprefs = sharedpref_stuid_pay_Obj.getString(key_studentid_pay, "").trim();



        amount_et = findViewById(R.id.amount_ET);
        remarks_et = findViewById(R.id.remarks_ET);
        sandbox_tv = findViewById(R.id.sandbox_TV);
        academic_tv = findViewById(R.id.academic_TV);
        cluster_tv = findViewById(R.id.cluster_TV);
        institute_tv = findViewById(R.id.Institute_TV);
        level_tv = findViewById(R.id.level_TV);
        applno_tv = findViewById(R.id.applicationo_TV);
        stuName_tv = findViewById(R.id.studentname_TV);
        receivable_tv = findViewById(R.id.receviable_TV);
        receiveddate_tv = findViewById(R.id.ReceivedDate_TV);
        received_tv = findViewById(R.id.received_TV);
        balance_tv = findViewById(R.id.balance_TV);
        paymentType_SP = findViewById(R.id.paymenttype_Spinner);
        paymentMode_SP = findViewById(R.id.paymentMode_Spinner);
        paymentmode_LL = findViewById(R.id.paymentmode_ll);
        Submit_Feessubmit_bt = findViewById(R.id.Submit_Feessubmit_BT);
        receipt_feepayment_et= findViewById(R.id.receipt_feepayment_ET);
        receiptlabel_feepayment_TV= findViewById(R.id.receiptlabel_feepayment_TV);

        fees_submit_ll= findViewById(R.id.fees_submit_LL);
        ArrayAdapter dataAdapter_paymentmode = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, paymentModeArray);
        dataAdapter_paymentmode.setDropDownViewResource(R.layout.spinnercenterstyle);
        paymentMode_SP.setAdapter(dataAdapter_paymentmode);


        @SuppressLint("ResourceType")
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_in);
        animation1.setDuration(1000);
        fees_submit_ll.setAnimation(animation1);
        fees_submit_ll.animate();
        animation1.start();

        paymentMode_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_paymentMode = paymentMode_SP.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter dataAdapter_paymentType = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, paymentTypeArray);
        dataAdapter_paymentType.setDropDownViewResource(R.layout.spinnercenterstyle);
        paymentType_SP.setAdapter(dataAdapter_paymentType);

        paymentType_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_paymentType = paymentType_SP.getSelectedItem().toString();
                if (selected_paymentType.equals("Payment")) {
                    paymentMode_SP.setVisibility(View.VISIBLE);
                    paymentmode_LL.setVisibility(View.VISIBLE);
                } else {
                    paymentMode_SP.setVisibility(View.GONE);
                    paymentmode_LL.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setcurrentdate();
        receiveddate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment fromdateFragment = new DatePickerFragmentReceivedDate();
//                fromdateFragment.show(getFragmentManager(), "Date Picker");

//            }
//        });
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                final DatePickerDialog datePickerDialog_receiveddate = new DatePickerDialog(Activity_FeesPayment.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar cal = new GregorianCalendar(i, i1, i2);


                        DatePickerDialog dialog = new DatePickerDialog(Activity_FeesPayment.this, this, i, i1, i2);
                        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                        setReceivedDate(cal);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog_receiveddate.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog_receiveddate.show();

            }
        });


        Submit_Feessubmit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Validation()){
                    SaveStudentpayment();
                }else{
                    Toast.makeText(Activity_FeesPayment.this, "Please enter all the valid data", Toast.LENGTH_LONG).show();
                }
            }
        });

        GetLoadStudentpayment();


    }//oncreate

    public void setcurrentdate() {
        //// Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        //str_receiveddate = mDay + "-" + (mMonth + 1) + "-" + mYear;
        str_receiveddate = mYear + "-" + (mMonth + 1) + "-" + mDay;
        Log.e("str_receiveddate..", str_receiveddate);
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        str_receiveddate_display=dateFormat.format(c.getTime());
        receiveddate_tv.setText(str_receiveddate_display);
    }

    public void setReceivedDate (Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        receiveddate_tv.setText(dateFormat.format(calendar.getTime()));
        str_receiveddate_display = dateFormat.format(calendar.getTime());
        Log.e("receiveddate_tv...", dateFormat.format(calendar.getTime()));


        SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy");

        str_receiveddate = mdyFormat.format(calendar.getTime());
        Log.e("str_receiveddate..", str_receiveddate);
        Calendar c = Calendar.getInstance();
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Log.e("outputFormat..", String.valueOf(outputFormat));



    }

    public static class DatePickerFragmentReceivedDate extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                    this, year, month, day);

            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return dialog;


        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
            setDate(cal);

        }

        public void setDate(final Calendar calendar) {
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

            receiveddate_tv.setText(dateFormat.format(calendar.getTime()));
            str_receiveddate_display = dateFormat.format(calendar.getTime());
            Log.e("receiveddate_tv...", dateFormat.format(calendar.getTime()));


            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy");

            str_receiveddate = mdyFormat.format(calendar.getTime());
            Log.e("str_receiveddate..", str_receiveddate);
            Calendar c = Calendar.getInstance();
            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Log.e("outputFormat..", String.valueOf(outputFormat));


        }

    }

    public void GetLoadStudentpayment() {

        if (isInternetPresent) {

            GetstudentpaymentTask task = new GetstudentpaymentTask(Activity_FeesPayment.this);
            task.execute();

        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }
    }


    private class GetstudentpaymentTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {

            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("list", "doInBackground");

            getpaymentdata();  // call of details
            return null;
        }

        public GetstudentpaymentTask(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1,R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPostExecute(Void result) {

            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();

            }
            if (str_receive_studentstatus.equals("Admission")) {
                SetValues();
            } else {
                Toast.makeText(Activity_FeesPayment.this, "No Data Found", Toast.LENGTH_LONG).show();
                finish();
                //  alert();

            }

            Log.e("tag", "Reached the onPostExecute");

        }//end of onPostExecute
    }// end Async task


    public void getpaymentdata() {
        Vector<SoapObject> result1 = null;

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadStudentPayment";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/LoadStudentPayment";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("Student_ID", str_studentID_myprefs);

            Log.i("request", request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("value at response", response.getProperty(0).toString());
                SoapObject obj2 = (SoapObject) response.getProperty(0);
                Log.e("obj2", obj2.toString());

                SoapPrimitive receive_studentstatus = (SoapPrimitive) obj2.getProperty("Student_Status");
                str_receive_studentstatus = receive_studentstatus.toString();
                Log.e("receive_studentstatus", receive_studentstatus.toString());
                if (str_receive_studentstatus.equals("Admission")) {
                    SoapPrimitive receive_studentid = (SoapPrimitive) obj2.getProperty("Student_ID");
                    str_receive_studentid = receive_studentid.toString();
                    SoapPrimitive receive_sandbox = (SoapPrimitive) obj2.getProperty("Sandbox_Name");
                    str_receive_sandbox = receive_sandbox.toString();
                    SoapPrimitive receive_academic = (SoapPrimitive) obj2.getProperty("Academic_Name");
                    str_receive_academic = receive_academic.toString();
                    SoapPrimitive receive_cluster = (SoapPrimitive) obj2.getProperty("Cluster_Name");
                    str_receive_cluster = receive_cluster.toString();
                    SoapPrimitive receive_institute = (SoapPrimitive) obj2.getProperty("Institute_Name");
                    str_receive_institute = receive_institute.toString();
                    SoapPrimitive receive_level = (SoapPrimitive) obj2.getProperty("Lavel_Name");
                    str_receive_level = receive_level.toString();
                    SoapPrimitive receive_applno = (SoapPrimitive) obj2.getProperty("Application_No");
                    str_receive_applno = receive_applno.toString();
                    SoapPrimitive receive_studentname = (SoapPrimitive) obj2.getProperty("Student_Name");
                    str_receive_studentname = receive_studentname.toString();
                    SoapPrimitive receiveAble = (SoapPrimitive) obj2.getProperty("Payment_Receivable");
                    str_receiveAble = receiveAble.toString();
                    SoapPrimitive received = (SoapPrimitive) obj2.getProperty("Payment_Received");
                    str_received = received.toString();
                    SoapPrimitive receive_balance = (SoapPrimitive) obj2.getProperty("Payment_Balance");
                    str_receive_balance = receive_balance.toString();

                    Log.e("str_receive_studentid", str_receive_studentid);
                } else if (str_receive_studentstatus.equals("No Result")) {
                    Log.e("recstudentstatus else", receive_studentstatus.toString());

                }

            } catch (Throwable t) {
                Log.e("request fail", "> " + t.getMessage());
                str_receive_studentstatus = "slow internet";

            }
        } catch (Throwable t) {
            Log.e("UnRegister Receiver ", "> " + t.getMessage());

        }

    }


    private void SetValues() {

        amount_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", str_receive_balance)});
        sandbox_tv.setText(str_receive_sandbox);
        academic_tv.setText(str_receive_academic);
        cluster_tv.setText(str_receive_cluster);
        institute_tv.setText(str_receive_institute);
        level_tv.setText(str_receive_level);
        applno_tv.setText(str_receive_applno);
        stuName_tv.setText(str_receive_studentname);
        receivable_tv.setText(str_receiveAble);
        received_tv.setText(str_received);
        balance_tv.setText(str_receive_balance);
        if(selected_paymentType.equals(paymentTypeArray[1])){
            receipt_feepayment_et.setVisibility(View.GONE);
            receiptlabel_feepayment_TV.setVisibility(View.GONE);
        }else
        {
            receipt_feepayment_et.setVisibility(View.VISIBLE);
            receiptlabel_feepayment_TV.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),Activity_Student_List.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu_register, menu);
        menu.findItem(R.id.Sync)
                .setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {


            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public boolean Validation() {
        Boolean bamount = true,bremarks=true,breciptno=true;
        if (amount_et.getText().toString().length() == 0) {
            amount_et.setError("Enter Amount");

            Toast.makeText(getApplicationContext(), "Please Enter Amount", Toast.LENGTH_SHORT).show();
            bamount = false;

        }

        if (remarks_et.getText().toString().length() == 0 || remarks_et.getText().toString().length() < 2) {
            remarks_et.setError("Enter Remarks");
            Toast.makeText(getApplicationContext(), "Please Enter Remarks", Toast.LENGTH_SHORT).show();
            bremarks = false;

        }
        try {
            int amt_intval = Integer.parseInt(amount_et.getText().toString());
            int balance_intval = Integer.parseInt(str_receive_balance);
            if (amt_intval > balance_intval) {
                Toast.makeText(getApplicationContext(), "Please Enter Valid Amount", Toast.LENGTH_SHORT).show();
                bamount = false;


            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (receipt_feepayment_et.getVisibility() == View.VISIBLE)

         {
            if (receipt_feepayment_et.getText().toString().length() == 0) {
                receipt_feepayment_et.setError("Enter Receipt No.");

                Toast.makeText(getApplicationContext(), "Please Enter Receipt No.", Toast.LENGTH_SHORT).show();
                breciptno = false;

            }
        }
        if (receipt_feepayment_et.getVisibility() == View.VISIBLE)
        {
            return bamount && bremarks && breciptno;
        }else{
            return bamount && bremarks;
        }

    }


    public void SaveStudentpayment() {

        if (isInternetPresent) {

            SaveStudentpaymentTask task = new SaveStudentpaymentTask(Activity_FeesPayment.this);
            task.execute();

        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private class SaveStudentpaymentTask extends AsyncTask<String, Void, Void> {

        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {

            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("list", "doInBackground");

            savepaymentdata();  // call of details
            return null;
        }

        public SaveStudentpaymentTask(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1,R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPostExecute(Void result) {

            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();

            }

            if(save_receive_studentstatus.equals("Sucess")){
                ClearData();
                Toast.makeText(Activity_FeesPayment.this, "Successfully submitted", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),Activity_Student_List.class);
                startActivity(i);
                finish();
            }else  if(save_receive_studentstatus.equals("Error")){
                Toast.makeText(Activity_FeesPayment.this, "Error", Toast.LENGTH_SHORT).show();
                finish();
            }

            Log.e("tag", "Reached the onPostExecute");

        }//end of onPostExecute
    }

    public void ClearData() {
        amount_et.getText().clear();
        remarks_et.getText().clear();
    }

    public  void savepaymentdata(){
        Vector<SoapObject> result1 = null;

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "SaveStudentPayment";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/SaveStudentPayment";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("Student_ID", str_studentID_myprefs);
            request.addProperty("Payment_Type", selected_paymentType);
            request.addProperty("Payment_Mode", selected_paymentMode);
            request.addProperty("Payment_Amount", amount_et.getText().toString());
            request.addProperty("Payment_Date", str_receiveddate);
            request.addProperty("Payment_Remarks", remarks_et.getText().toString());
            request.addProperty("Created_By", str_loginuserID);
            request.addProperty("Receipt_Manual", receipt_feepayment_et.getText().toString());

            Log.i("request", request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("value at response", response.getProperty(0).toString());

                SoapObject obj2 = (SoapObject) response.getProperty(0);
                Log.e("obj2", obj2.toString());

                SoapPrimitive receive_studentstatus_save = (SoapPrimitive) obj2.getProperty("Student_Status");
                save_receive_studentstatus = receive_studentstatus_save.toString();

            } catch (Throwable t) {
                Log.e("request fail", "> " + t.getMessage());
                //str_receive_studentstatus = "slow internet";

            }
        } catch (Throwable t) {
            Log.e("UnRegister Receiver ", "> " + t.getMessage());

        }

    }
}
