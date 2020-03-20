package com.example.demoapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

import static com.example.demoapp.Databasehelper.Id;

public class Adapterclass extends BaseAdapter {
   ArrayList<Modalclass> modalclasses=new ArrayList<Modalclass>();
    Context context;
    Databasehelper databasehelper;
    EditText modname, modemail, modnumber;
    Button modsubmit, modcancel;
//    SQLiteDatabase sqLiteDatabase=databasehelper.getWritableDatabase();

    public Adapterclass(ArrayList<Modalclass> modalcls, Context context) {
        this.modalclasses=modalcls;
        this.context = context;
        databasehelper = new Databasehelper(context);
    }

    @Override
    public int getCount() {
        return modalclasses.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder;
        LayoutInflater layoutInflater;
        final Modalclass modalclass=modalclasses.get(position);
        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_listviewrow, null);

            holder = new Holder();
            holder.textviewid = (TextView) convertView.findViewById(R.id.id);
            holder.textviewname = (TextView) convertView.findViewById(R.id.name);
            holder.textviewemail = (TextView) convertView.findViewById(R.id.email);
            holder.textviewphone_number = (TextView) convertView.findViewById(R.id.phonenumber);
            holder.delete = (Button) convertView.findViewById(R.id.delete);
            holder.edit = (Button) convertView.findViewById(R.id.edit);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.textviewid.setText(modalclass.getId());
        holder.textviewname.setText(modalclass.getName());
        holder.textviewemail.setText(modalclass.getEmail());
        holder.textviewphone_number.setText(modalclass.getNumber());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasehelper.deletedata(modalclass.getId());
                notifyDataSetChanged();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.activity_modify_data);
                modname =(EditText)dialog.findViewById(R.id.modname);
                modemail =(EditText)dialog.findViewById(R.id.modemail);
                modnumber =(EditText)dialog.findViewById(R.id.modnumber);
                modsubmit = (Button)dialog.findViewById(R.id.modsubmit);
                modcancel = (Button)dialog.findViewById(R.id.modcancel);
                dialog.setCancelable(false);

                modsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = modname.getText().toString();
                        String email = modemail.getText().toString();
                        String number = modnumber.getText().toString();

                        if (!isVallidName(name)) {
                            modname.setError("Enter Only Name");
                        } else if (!isValidNumber(number)) {
                            modnumber.setError("Enter Mobile Number");
                        } else if (!isValidEmail(email)) {
                            modemail.setError("Enter Valid Email");
                        } else {
                            boolean data = databasehelper.updatedata(modalclass.getId(),name,number,email);


                            if (data) {

                                dialog.dismiss();

                            } else {
                                Toast.makeText(context, "Enter Value", Toast.LENGTH_SHORT).show();
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
                modcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });
                dialog.show();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public class Holder {
        TextView textviewid;
        TextView textviewname;
        TextView textviewemail;
        TextView textviewphone_number;
        Button delete;
        Button edit;
    }

    private boolean isVallidName(String name) {
        String USER_NAME = "[a-zA-Z]+";
        if (name != null && name.matches(USER_NAME)) {
            return true;
        }
        return false;
    }

    private boolean isValidNumber(String num) {
        if (num != null && num.length() == 10) {
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$";
        if (email != null && email.matches(EMAIL_PATTERN)) {
            return true;
        }
        return false;
    }
}
