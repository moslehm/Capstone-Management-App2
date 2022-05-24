package ca.macewan.capstone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import ca.macewan.capstone.adapter.RecyclerAdapter;

public class HomeFragment extends Fragment {
    private TextView textViewRole, textViewName;
    private RecyclerView recyclerViewProject;
    private RecyclerAdapter recyclerAdapter;
    private String role;
    private String name;

    // Required empty public constructor
    public HomeFragment(String givenRole, String givenName) {
        role = givenRole;
        name = givenName;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // This one was missing in your earlier code
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    public void setUp() {
        textViewRole = (TextView) getView().findViewById(R.id.textView_Role);
        textViewName = (TextView) getView().findViewById(R.id.textView_Name);
        recyclerViewProject = (RecyclerView) getView().findViewById(R.id.recyclerView_Project);
        textViewName.setText(name);
        textViewRole.setText(role);

        // This will display all the current projects in our database
        Query query = FirebaseFirestore.getInstance().collection("Projects");
        FirestoreRecyclerOptions<Project> options = new FirestoreRecyclerOptions.Builder<Project>()
                .setQuery(query, Project.class)
                .build();
        recyclerAdapter = new RecyclerAdapter(options);
        recyclerViewProject.setItemAnimator(null);
        recyclerViewProject.setAdapter(recyclerAdapter);
        recyclerViewProject.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }
}