package com.mahdy.codes.thenotesapp.Persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mahdy.codes.thenotesapp.Models.Notes;

@Database(entities = {Notes.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase
{
 public static final String DATABASENAME = "Note_db" ;
 private static NoteDatabase instance ;
 static NoteDatabase getInstance(final Context context) {
     if (instance == null){
         instance = Room.databaseBuilder(context.getApplicationContext()
                 , NoteDatabase.class
                 ,DATABASENAME).build() ;
     }
     return instance ;
 }
 public abstract NoteDao getnoteDao();
}