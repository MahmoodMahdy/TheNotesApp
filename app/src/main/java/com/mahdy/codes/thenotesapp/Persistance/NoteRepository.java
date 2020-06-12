package com.mahdy.codes.thenotesapp.Persistance;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mahdy.codes.thenotesapp.Async.DeleteAsyncTask;
import com.mahdy.codes.thenotesapp.Async.InsertAsyncTask;
import com.mahdy.codes.thenotesapp.Async.UpdateAsyncTask;
import com.mahdy.codes.thenotesapp.Models.Notes;

import java.util.List;

public class NoteRepository
{
    private NoteDatabase mNoteDatabase ;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context) ;
    }
    public void insertNoteTask(Notes notes)
    {
        new InsertAsyncTask(mNoteDatabase.getnoteDao()).execute(notes) ;
    }
    public void updateNoteTask(Notes notes) {
        new UpdateAsyncTask(mNoteDatabase.getnoteDao()).execute(notes) ;
    }
    public void deleteNoteTask(Notes notes) {
        new DeleteAsyncTask(mNoteDatabase.getnoteDao()).execute(notes) ;
    }
    public LiveData<List<Notes>> retrieveNoteTask() {

        return mNoteDatabase.getnoteDao().getNotes() ;
    }

}
