package com.example.uttarakhand_relief;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databasing {

	public static final String KEY_ROW_ID = "_id";
	public static final String KEY_CHILD_NAME = "Name";
	public static final String KEY_AGE = "Age";
	public static final String KEY_CONTACT = "Contact";
	public static final String KEY_BACKGROUND = "Background";
		
	private static final String DATABASE_TABLE = "Orphans";
	private static final String DATABASE_NAME = "Orphan_details";
	private static final int DATABASE_VERSION = 1;
	
	private dbHelper ourhelper;
	private Context ourcontext;
	private SQLiteDatabase orphans_database;

	private static class dbHelper extends SQLiteOpenHelper {

		

		

		public dbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROW_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_CHILD_NAME+ " TEXT, "
					+ KEY_AGE + " TEXT, "
					+ KEY_CONTACT + " TEXT, "
					
					 + KEY_BACKGROUND+ " TEXT");
			

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public databasing(Context c) {
		ourcontext = c;
	}

	public databasing open() throws SQLException {
		ourhelper = new dbHelper(ourcontext);
		orphans_database = ourhelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourhelper.close();
	}
// creates an entry in database
	public long createEntry ( String name, String age, String contact, String offhradd,
			String lec_v,String lab_v,String tut_v,String backg,String email, String schedule,String credits)throws Exception {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_CHILD_NAME , name);
		cv.put(KEY_AGE , age);
		cv.put(KEY_CONTACT , contact);
		cv.put(KEY_BACKGROUND , backg);	
		return orphans_database.insertOrThrow(DATABASE_TABLE, null, cv);
	}
	
	
// returns all data in a string seperated by -
	public String getdata() {
		// TODO Auto-generated method stub
		String[] columns ={KEY_ROW_ID,KEY_CHILD_NAME,KEY_AGE,KEY_CONTACT,KEY_BACKGROUND};
		Cursor c = orphans_database.query(DATABASE_TABLE, columns, null, null, null, null, null);
		
		
		String result = "";
		
		for(c.moveToFirst();!(c.isAfterLast());c.moveToNext())
		{
			for(int i=0;i<columns.length;i++)
			{
			result = result + c.getString(i)+ "\t";
					
			}
			result = result + "\n";
		}
		return result;
	}

	public String GetCourseDetailById(long l) {
		// TODO Auto-generated method stub
		String[] columns ={KEY_ROW_ID,KEY_CHILD_NAME,KEY_AGE,KEY_CONTACT,KEY_BACKGROUND};
		Cursor c = orphans_database.query(DATABASE_TABLE, columns, KEY_ROW_ID + "=" + l, null, null, null, null);
		if(c!=null && c.moveToFirst())
		{
			
			String name="";
			for(int i=1;i<columns.length;i++)
			{
				name = name + c.getString(i) + "---"  ;
			}
			return name;
		}
		return null;
	}
	public String GetCourseNameById(long l) {
		// TODO Auto-generated method stub
		String[] columns ={KEY_ROW_ID,KEY_CHILD_NAME,KEY_AGE,KEY_CONTACT,KEY_BACKGROUND};
		Cursor c = orphans_database.query(DATABASE_TABLE, columns, KEY_ROW_ID + "=" + l, null, null, null, null);
		if(c!=null)
		{
			c.moveToFirst();
			String name = c.getString(1);
			return name;
		}
		return null;
	}
	public String GetAllCourses(){
		String[] columns ={KEY_ROW_ID,KEY_CHILD_NAME,KEY_AGE,KEY_CONTACT,KEY_BACKGROUND};

		Cursor c = orphans_database.query(DATABASE_TABLE, columns, null, null, null, null, null);
		
		int iName = c.getColumnIndex(KEY_CHILD_NAME);
		String result = "";
		if (c.getCount()>0)
		{
			
			for(c.moveToFirst();!(c.isAfterLast());c.moveToNext())
			{
				result = result + c.getString(iName)+ "---" ;
			
			}
			return result; 
		}
		return null;
		
	}

	public void UpdateCourse(long l, String age, String contact, String offhradd,
			String backg,String lec_v,String lab_v,String tut_v,String email,String schedule,String credits) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_AGE , age);
		cv.put(KEY_CONTACT , contact);
		cv.put(KEY_BACKGROUND , backg);
		orphans_database.update(DATABASE_TABLE, cv,KEY_ROW_ID +"="+ l, null);
		return;
		
	}
	public void KeyRowIdUpdate(){
		ContentValues cv = new ContentValues();
		String[] columns ={KEY_ROW_ID,KEY_CHILD_NAME,KEY_AGE,KEY_CONTACT,KEY_BACKGROUND};
		
		Cursor c = orphans_database.query(DATABASE_TABLE, columns,null, null, null, null, null);
		int m=1;
		for(c.moveToFirst();!(c.isAfterLast());c.moveToNext())
		{
			cv.put(KEY_ROW_ID, m);
			orphans_database.update(DATABASE_TABLE, cv, KEY_ROW_ID +"="+ c.getString(0), null);
			m++;
		}
		
		
	}

	public void deleteEntry(int m) {
		// TODO Auto-generated method stub
		
		orphans_database.delete(DATABASE_TABLE, KEY_ROW_ID + "="+m, null);
		KeyRowIdUpdate();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		String[] columns ={KEY_ROW_ID,KEY_CHILD_NAME,KEY_AGE,KEY_CONTACT,KEY_BACKGROUND};
		Cursor c = orphans_database.query(DATABASE_TABLE, columns, null, null, null, null, null);
		int i = c.getCount();
		
		return i;
	}
}
