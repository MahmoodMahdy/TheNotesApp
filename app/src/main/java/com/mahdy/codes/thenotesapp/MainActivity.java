package com.mahdy.codes.thenotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mahdy.codes.thenotesapp.Adapter.NoteAdapter;
import com.mahdy.codes.thenotesapp.Models.Notes;
import com.mahdy.codes.thenotesapp.Persistance.NoteRepository;
import com.mahdy.codes.thenotesapp.Util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        NoteAdapter.OnNoteLestener
        , FloatingActionButton.OnClickListener {
    private RecyclerView mRecyclerview ;
    private RecyclerView.Adapter mNoteRecyclerAdapter ;
    private ArrayList<Notes> mNotes = new ArrayList<>() ;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerview = findViewById(R.id.mRecyclerview) ;
        findViewById(R.id.fab).setOnClickListener(this);
        mNoteRepository =new NoteRepository(this) ;
        retrievetNotes();
   //     insertFakeNotes();
        initRecyclerview();
       setSupportActionBar((Toolbar) findViewById(R.id.notes_toolBar) );
        setTitle("Notes");
    }

    private void retrievetNotes() {

        mNoteRepository.retrieveNoteTask().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                if (mNotes.size() > 0 ){
                    mNotes.clear();
                }
                if (notes != null ){
                    mNotes.addAll(notes) ;
                }
                mNoteRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }


    private void insertFakeNotes(){
        for(int i = 0; i < 1000; i++){
           Notes note = new Notes();
            note.setTitle("title #" + i);
            note.setContent("content #: " + i);
            note.setTimestamp("Jan 2019");
            mNotes.add(note);
        }
      //  mNoteRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerview() {
        LinearLayoutManager manager =new LinearLayoutManager(this) ;
        mRecyclerview.setLayoutManager(manager) ;
      VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerview.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mRecyclerview);
        //new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mNoteRecyclerAdapter =new NoteAdapter(mNotes,this) ;
        mRecyclerview.setAdapter(mNoteRecyclerAdapter) ;
    }

    @Override
    public void onNoteClicked(int position) {
        Intent intent =new Intent(this, com.mahdy.codes.thenotesapp.NotesActivity.class) ;
        intent.putExtra("selected_note" ,mNotes.get(position)) ;
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent =new Intent(this, com.mahdy.codes.thenotesapp.NotesActivity.class);
        startActivity(intent);
    }
    private void  deleteNoteItem(Notes notes){
        mNotes.remove(notes);
        mNoteRecyclerAdapter.notifyDataSetChanged();
        mNoteRepository.deleteNoteTask(notes);
     }

//    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT)
//    {
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//            deleteNoteItem(mNotes.get(viewHolder.getAdapterPosition()));
//        }
//    } ;
    ItemTouchHelper.SimpleCallback itemTouchHelper =new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.RIGHT) {
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        deleteNoteItem(mNotes.get(viewHolder.getAdapterPosition()));
    }
};
}
