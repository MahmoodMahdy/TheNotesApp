package com.mahdy.codes.thenotesapp.Async;

import android.os.AsyncTask;

import com.mahdy.codes.thenotesapp.Models.Notes;
import com.mahdy.codes.thenotesapp.Persistance.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Notes,Void,Void> {
    private NoteDao mNoteDao ;

    public UpdateAsyncTask(NoteDao mNoteDao) {
        this.mNoteDao = mNoteDao;
    }

    @Override
    protected Void doInBackground(Notes... notes) {
        mNoteDao.uddateNotes(notes);
        return null;
    }
}
