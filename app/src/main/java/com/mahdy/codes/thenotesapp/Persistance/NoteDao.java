package com.mahdy.codes.thenotesapp.Persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mahdy.codes.thenotesapp.Models.Notes;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertNotes(Notes... notes) ;
    @Query("SELECT * FROM Notes")
    LiveData<List<Notes>> getNotes() ;
    @Update
    void uddateNotes(Notes... notes) ;
    @Delete
    void deleteNote(Notes... notes) ;
}
