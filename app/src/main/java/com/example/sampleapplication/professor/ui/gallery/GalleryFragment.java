package com.example.sampleapplication.professor.ui.gallery;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleapplication.CommonUtils;
import com.example.sampleapplication.R;
import com.example.sampleapplication.RegistrationModel;
import com.example.sampleapplication.databinding.FragmentGalleryBinding;
import com.example.sampleapplication.student.models.StudentRegistrationModel;
import com.example.sampleapplication.student.ui.gallery.StudentPeopleAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GalleryFragment extends Fragment {

    RecyclerView studentPeople_recyclerview;
    StudentPeopleAdapter studentPeopleAdapter;
    ProgressDialog progressDialog;
    SearchView id_searchview;
    ArrayList<StudentRegistrationModel> registrationModelArrayList;

    private void getStudentDate(ArrayList<StudentRegistrationModel> registrationModelArrayList) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails").child("Student");
        //  String studentID = FirebaseAuth.getInstance().getUid();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.cancel();
                Log.d("Student Course", snapshot.toString());
                registrationModelArrayList.clear();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    StudentRegistrationModel post = postSnapshot.getValue(StudentRegistrationModel.class);
                    registrationModelArrayList.add(post);
                }
                studentPeopleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_people_instructor, container, false);
        studentPeople_recyclerview=view.findViewById(R.id.studentPeople_recyclerview);
        studentPeople_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(studentPeople_recyclerview.getContext(),
                new LinearLayoutManager(getActivity()).getOrientation());
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.line_decoration));
        studentPeople_recyclerview.addItemDecoration(dividerItemDecoration);
        id_searchview=view.findViewById(R.id.id_searchview);
        // id_searchview.setQueryHint(Html.fromHtml("<font color = #000000>" + getResources().getString(R.string.search_hint) + "</font>"));
        int id = id_searchview.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) id_searchview.findViewById(id);
        textView.setTextColor(Color.BLACK);
        id_searchview.clearFocus();
        id_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        registrationModelArrayList = new ArrayList<>();
        if (CommonUtils.isConnectedToInternet(getActivity())) {
            try {
                getStudentDate(registrationModelArrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "No Internet Connection....", Toast.LENGTH_SHORT).show();
        }
        studentPeopleAdapter = new  StudentPeopleAdapter(registrationModelArrayList, getActivity());;
        studentPeople_recyclerview.setAdapter(studentPeopleAdapter);
        return view;
    }

    private void filterList(String newText) {
        ArrayList<StudentRegistrationModel> list=new ArrayList<>();
        for (StudentRegistrationModel model:registrationModelArrayList){
            String name=model.getFname().toLowerCase() + model.getLname().toLowerCase();
            if(name.contains(newText.toLowerCase())){
                list.add(model);
            }
        }

        if(list.isEmpty()){
            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
        }else{
            studentPeopleAdapter.setFilterList(list);
        }

    }

}