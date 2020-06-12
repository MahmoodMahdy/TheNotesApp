package com.mahdy.codes.thenotesapp;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mahdy.codes.thenotesapp.Models.Notes;
import com.mahdy.codes.thenotesapp.Persistance.NoteRepository;
import com.mahdy.codes.thenotesapp.Util.Utility;

public class NotesActivity extends AppCompatActivity implements View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher
{

    private static final String TAG = "NotesActivity";
    private static final int EDIT_MODE_ENABLED=1  ;
    private static final int EDIT_MODE_DISABLED=0  ;
    private com.mahdy.codes.thenotesapp.LinedEditText mLinedEditText ;
    private EditText mEditTitle ;
    private TextView mTextTitle ;
    private RelativeLayout back_arrow_container , check_container ;
    private ImageButton mBackArrow , mCheck ;
    private boolean mIsNewNote ;
    private Notes mNoteInitial ;
    private GestureDetector mGestureDetector;
    private int mMode ;
    private NoteRepository mNoteRepository;
    private Notes mFinalNote ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        mLinedEditText = (LinedEditText) findViewById(R.id.et_noteText) ;
        mEditTitle = (EditText) findViewById(R.id.note_edit_title) ;
        mTextTitle = (TextView) findViewById(R.id.note_text_title);
        back_arrow_container =(RelativeLayout) findViewById(R.id.back_arrow_container) ;
        check_container =(RelativeLayout) findViewById(R.id.check_container) ;
        mBackArrow = (ImageButton) findViewById(R.id.toolbar_back_arrow) ;
        mCheck = (ImageButton) findViewById(R.id.toolbar_check) ;
        mNoteRepository =new NoteRepository(this) ;

        setListeners() ;
        if (getInComingIntent()){
            //this is  a new note (Edit Mode)
            setNewNoteProprietes();
             enabledEditMode();
        }else {
            //this is Not a new note (View Mode)
            setNoteProprietes();
            disableContentInteraction();
        }
        }
        private void setListeners(){
        mLinedEditText.setOnTouchListener(this);
            mGestureDetector= new GestureDetector(this,this) ;
            mEditTitle.setOnClickListener(this) ;
            mCheck.setOnClickListener(this);
            mBackArrow.setOnClickListener(this);
            mEditTitle.addTextChangedListener(this);

        }

        private boolean getInComingIntent () {
            if (getIntent().hasExtra("selected_note")){
                mNoteInitial = getIntent().getParcelableExtra("selected_note") ;

                mFinalNote =new Notes() ;
                mFinalNote.setTitle(mNoteInitial.getTitle());
                mFinalNote.setContent(mNoteInitial.getContent());
                mFinalNote.setTimestamp(mNoteInitial.getTimestamp());
                mFinalNote.setId(mNoteInitial.getId());

                mMode = EDIT_MODE_DISABLED ;
                return mIsNewNote = false ;
            }
            mMode = EDIT_MODE_ENABLED ;
            // mIsNewNote = true ;
            return mIsNewNote = true ;

        }
        private void saveNewNote(){
        mNoteRepository.insertNoteTask(mFinalNote);
        }
        private void updateNote(){mNoteRepository.updateNoteTask(mFinalNote);}
        private void saveChanges() {
        if (mIsNewNote){
            saveNewNote();
            Log.d(TAG, "saveNewNote: called.");

        }else {

            updateNote();
        }
        }

        private void  disableContentInteraction() {
        mLinedEditText.setOnKeyListener(null);
        mLinedEditText.setFocusable(false);
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.clearFocus();
        }
    private void  enableContentInteraction() {
        mLinedEditText.setKeyListener(new EditText(this).getKeyListener());
        mLinedEditText.setFocusable(true);
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.requestFocus();
    }
        private  void enabledEditMode () {
        back_arrow_container.setVisibility(View.GONE);
        check_container.setVisibility(View.VISIBLE);

            mTextTitle.setVisibility(View.GONE);
            mEditTitle.setVisibility(View.VISIBLE);
        mMode = EDIT_MODE_ENABLED ;
        enableContentInteraction();
         }
        private  void disabledEditMode () {
        back_arrow_container.setVisibility(View.VISIBLE);
            check_container.setVisibility(View.GONE);
            mTextTitle.setVisibility(View.VISIBLE);
            mEditTitle.setVisibility(View.GONE);
            mMode = EDIT_MODE_DISABLED;
            disableContentInteraction();
//            String temp = mLinedEditText.getText().toString();
//            temp = temp.replace("\n", "");
//            temp = temp.replace(" ", "");
//            if(temp.length() > 0) {
//                mNoteInitial.setTitle(mEditTitle.getText().toString());
//                mNoteInitial.setContent(mLinedEditText.getText().toString());
//                String timestamp = Utility.getCurrentTimeStamp() ;
//               // String timestamp = Utility.getCurrentTimeStamp();
//                mNoteInitial.setTimestamp(timestamp);
//
////                Log.d(TAG, "disableEditMode: initial: " + mNoteInitial.toString());
////                Log.d(TAG, "disableEditMode: final: " + mNoteInitial.toString());
//
//                // If the note was altered, save it.
//                if(!mFinalNote.getContent().equals(mNoteInitial.getContent())
//                        || !mFinalNote.getTitle().equals(mNoteInitial.getTitle())){
//                    Log.d(TAG, "disableEditMode: called?");
//                    saveChanges();
//                }}}

            String temp = mLinedEditText.getText().toString() ;
            temp = temp.replace("\n","") ;
            temp = temp.replace(" ","") ;
            if (temp.length() > 0){
                mFinalNote.setTitle(mEditTitle.getText().toString());
                mFinalNote.setContent(mLinedEditText.getText().toString());
                String timestamp =Utility.getCurrentTimeStamp() ;
                mFinalNote.setTimestamp(timestamp);
                if (!mFinalNote.getTitle().equals(mNoteInitial.getTitle())
                        || !mFinalNote.getContent().equals(mNoteInitial.getContent())){
                    saveChanges();
                }
            }
    }

        private void setNewNoteProprietes() {
        mEditTitle.setText("Note Title");
        mTextTitle.setText("Note Title");
        mNoteInitial = new Notes() ;
        mFinalNote = new Notes() ;
        mNoteInitial.setTitle("Note Title");

        }
        private void setNoteProprietes(){
            mEditTitle.setText(mNoteInitial.getTitle());
            mTextTitle.setText(mNoteInitial.getTitle());
            mLinedEditText.setText(mNoteInitial.getContent());
        }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        enabledEditMode();
        Log.d(TAG,"onDouble Tap : double tapped!") ;
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.toolbar_check : {
                disabledEditMode();
                break ;
            }
            case R.id.toolbar_back_arrow : {
                finish();
                break ;
            }
            case R.id.note_text_title : {
                enabledEditMode();
                mEditTitle.requestFocus();
                 mEditTitle.setSelection(mEditTitle.length());
                break;
            }
        }
    }

   @Override
   public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED){
            onClick(mCheck);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode",mMode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode =savedInstanceState.getInt("mode") ;
        if (mMode == EDIT_MODE_ENABLED) {
            enabledEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mTextTitle.setText(s.toString());

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

