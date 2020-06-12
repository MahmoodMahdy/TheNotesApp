package com.mahdy.codes.thenotesapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahdy.codes.thenotesapp.Models.Notes;
import com.mahdy.codes.thenotesapp.R;
import com.mahdy.codes.thenotesapp.Util.Utility;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.Viewholder> {
    private ArrayList<Notes> mNotes =new ArrayList<>() ;
    private OnNoteLestener onNoteLestener ;

    public NoteAdapter(ArrayList<Notes> mNotes,OnNoteLestener onNoteLestener)
    {
        this.mNotes = mNotes;
        this.onNoteLestener= onNoteLestener ;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notes_item,parent,false) ;
        return new Viewholder(view,onNoteLestener);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String month = mNotes.get(position).getTimestamp().substring(0,2) ;
        month = Utility.getMonthsFromNumbers(month) ;
        String year = mNotes.get(position).getTimestamp().substring(3) ;
        String timeStamp = month + " " + year ;
        holder.note_timestamp.setText(timeStamp);
        holder.note_title.setText(mNotes.get(position) .getTitle());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView note_title ,note_timestamp  ;
        OnNoteLestener onNoteLestener ;

        public Viewholder(@NonNull View itemView , OnNoteLestener onNoteLestener)
        {
            super(itemView);
            note_title = itemView.findViewById(R.id.note_title) ;
            note_timestamp = itemView.findViewById(R.id.note_timestamp) ;
            this.onNoteLestener = onNoteLestener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onNoteLestener.onNoteClicked(getAdapterPosition());

        }
    }
    public interface OnNoteLestener
    {
        void onNoteClicked(int position) ;
    }
}
