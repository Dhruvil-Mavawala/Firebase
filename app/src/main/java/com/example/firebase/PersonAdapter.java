package com.example.firebase;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonAdapter extends FirebaseRecyclerAdapter<PersonModel, PersonAdapter.personViewHolder> {
    Context context;

    public PersonAdapter(@NonNull FirebaseRecyclerOptions<PersonModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull personViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull PersonModel model) {

//        holder.tvname.setText("Name : "+model.getName());
//        holder.tvemail.setText("Email : "+model.getEmail());
//        holder.tvcourse.setText("Course : "+model.getCourse());
//        holder.tvmobile.setText("Mobile No : "+model.getMobileno());
//        holder.imgprofile.setImageResource(R.drawable.boy);
        holder.tvname.setText(model.getName());
        holder.tvemail.setText(model.getEmail());
        holder.tvcourse.setText(model.getCourse());
        holder.tvmobile.setText(" "+model.getMobileno());

        Glide.with(context).load(model.getPic()).into(holder.imgprofile);

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle(context.getString(R.string.delete_title));
                builder.setMessage("Delete can't be undo!!!");

                builder.setPositiveButton("Delete", (dialogInterface, i) -> {
                    FirebaseDatabase.getInstance().getReference().child("Person")
                            .child(getRef(position).getKey()).removeValue();
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Canceled!!!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPlus dialog= DialogPlus.newDialog(context)
                        .setExpanded(true,1630)
                        .setContentHolder(new ViewHolder(R.layout.dialog_update))
                        .create();

                View myview=dialog.getHolderView();
                EditText etname=myview.findViewById(R.id.etname);
                EditText etcourse=myview.findViewById(R.id.etcourse);
                EditText etemail=myview.findViewById(R.id.etemail);
                EditText etmob=myview.findViewById(R.id.etmob);
                EditText etpic=myview.findViewById(R.id.etpic);



                Button btnupdatedetails=myview.findViewById(R.id.btnupdatedetails);
                etname.setText(model.getName());
                etcourse.setText(model.getCourse());
                etemail.setText(model.getEmail());
                etmob.setText(String.valueOf(model.getMobileno()));
                etpic.setText(model.getPic());
                btnupdatedetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("name",etname.getText().toString());
                        map.put("course",etcourse.getText().toString());
                        map.put("email",etemail.getText().toString());
                        map.put("mobileno",etmob.getText().toString());
                        map.put("pic",etpic.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Person")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
    }

    @NonNull
    @Override
    public personViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_person,parent,false);
        return new personViewHolder(view);
    }

    class personViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgprofile;
        TextView tvname,tvemail,tvmobile,tvcourse;
        Button btndelete;
        Button btnupdate;

        public personViewHolder(@NonNull View itemView) {
            super(itemView);
            imgprofile= itemView.findViewById(R.id.imgprofile);
            tvname= itemView.findViewById(R.id.tvname);
            tvemail= itemView.findViewById(R.id.tvemail);
            tvmobile=itemView.findViewById(R.id.tvmobile);
            tvcourse=itemView.findViewById(R.id.tvcourse);
            btnupdate=itemView.findViewById(R.id.btnupdate);
            btndelete=itemView.findViewById(R.id.btndelete);
        }
    }
}
