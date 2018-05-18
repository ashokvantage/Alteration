package com.tdevelopers.alteration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "MyDBName.db", null, 1);
    }

    String selectQuery;
    Cursor c;
    int tablelength;
    String First_NewsId;
    boolean contain = false;
    byte[] img;

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table allAlter " +
                        "(id integer primary key, p_id text,p_imageurl text,insert_position integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertImagePath(String p_id, String p_imageurl, int insert_position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("p_id", p_id);
        values.put("p_imageurl", p_imageurl);
        values.put("insert_position", insert_position);
        db.insert("allAlter", null, values);
        return true;
    }

    public Integer deleteImagePath(String p_id, Integer position) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT id FROM allAlter where p_id='" + p_id + "' AND insert_position='"+position+"'", null);
        String id = "";
//        int a = 1;
        if (res != null) {
            res.moveToFirst();
            try {
                do {
                    id = res.getString(res.getColumnIndex("id"));
                    /*if (position == a) {*/
                        return db.delete("allAlter", "id =? ", new String[]{id});
                   /* }*/
//                    a++;
                } while (res.moveToNext());
            } catch (Exception e) {

            }
        }
        return 0;
    }

    public Integer deleteImage(String p_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT id FROM allAlter where p_id='" + p_id + "'", null);
        String id = "";
//        int a = 1;
        if (res != null) {
            res.moveToFirst();
            try {
                do {
                    id = res.getString(res.getColumnIndex("id"));
                    /*if (position == a) {*/
                    db.delete("allAlter", "id =? ", new String[]{id});
                    /* }*/
//                    a++;
                } while (res.moveToNext());
            } catch (Exception e) {

            }
        }
        return 0;
    }

    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from allAlter");
    }

    public Integer delete_last_row(String p_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT id FROM allAlter where p_id='" + p_id + "'", null);

        String id = "";
        if (res != null) {
            res.moveToLast();
            try {
                do {
                    id = res.getString(res.getColumnIndex("id"));
                    break;
                } while (res.moveToPrevious());
            } catch (Exception e) {

            }
        }


        return db.delete("allAlter", "id =? ", new String[]{id});
    }


    public Cursor getallAlterData(String p_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from allAlter where p_id='" + p_id + "' ORDER BY  insert_position", null);
        return res;
    }

    public Cursor gettrendingData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from allnews where trending_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor gettopstoryData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from topstory ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where topstory_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor getbookmarkData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from bookmark", null);
        return res;
    }

    public Cursor getunreadData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from unread ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where readtype='Unread' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
//newsid,newstitle,newssummary,newsvideo,newsimageurl,newsdate,newssource,newslike,likestatus,newsurl,shareurl,published_by,readtype,category,newsimage
        return res;
    }

    public Cursor getlocalData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from local ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where local_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor getinternationalData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from international ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where international_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor getbusinessandfinanceData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from businessandfinance ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where business_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor getpoliticsData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from politics ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where politics_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor getsportsData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from sports ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where sport_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor getpropertyData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from property", null);
        Cursor res = db.rawQuery("select * from allnews where property_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor gettechnologyData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from technology ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where technology_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor getentertainmentData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from entertainment ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where entertainment_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor getmoviesData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from movies ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where movie_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor gethealthData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from health ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where health_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor getlifestyleData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from lifestyle ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where lifestyle_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor gettriviaData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from trivia ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where trivia_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public Cursor getjobsData(int a, int b) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from jobs ORDER BY  newsdate DESC", null);
        Cursor res = db.rawQuery("select * from allnews where jobs_cat='true' and from_notification='false' ORDER BY  newsdate DESC LIMIT " + a + " , " + b, null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "contacts");
        return numRows;
    }

    public boolean readupdate(String newsid, String readtype, String Cat_Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("readtype", readtype);
        if (Cat_Name.equalsIgnoreCase("All News")) {
            db.update("allnews", contentValues, "newsid=" + newsid, null);
        }

        return true;
    }
//    public boolean update(String newsid, String readtype, String Cat_Name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("readtype", readtype);
//        if (Cat_Name.equalsIgnoreCase("All News")) {
//            db.update("allnews", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Trending")) {
//            db.update("trending", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Top Stories")) {
//            db.update("topstory", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Saved Article/Bookmarks")) {
//            db.update("bookmark", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Unread")) {
//            db.update("unread", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Local")) {
//            db.update("local", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("International")) {
//            db.update("international", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Business and Finance")) {
//            db.update("businessandfinance", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Politics")) {
//            db.update("politics", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Sports")) {
//            db.update("sports", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Property")) {
//            db.update("property", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Technology")) {
//            db.update("technology", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Entertainment & Gossip")) {
//            db.update("entertainment", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Movies and Series")) {
//            db.update("movies", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Health/Science")) {
//            db.update("health", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Lifestyle")) {
//            db.update("lifestyle", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Trivia")) {
//            db.update("trivia", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Jobs")) {
//            db.update("jobs", contentValues, "newsid=" + newsid, null);
//        }
//
//        return true;
//    }

    public boolean updateBitmap(String newsid, String Cat_Name, Bitmap imagebitmap) {
        if (imagebitmap == null) {

        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imagebitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            img = bos.toByteArray();
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("newsimage", img);
            if (Cat_Name.equalsIgnoreCase("All News")) {
                db.update("allnews", contentValues, "newsid=" + newsid, null);
            }
        }


//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("newsimage", img);
//        if (Cat_Name.equalsIgnoreCase("All News")) {
//            db.update("allnews", contentValues, "newsid=" + newsid, null);
//        }
//        else if (Cat_Name.equalsIgnoreCase("Trending")) {
//            db.update("trending", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Top Stories")) {
//            db.update("topstory", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Saved Article/Bookmarks")) {
//            db.update("bookmark", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Unread")) {
//            db.update("unread", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Local")) {
//            db.update("local", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("International")) {
//            db.update("international", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Business and Finance")) {
//            db.update("businessandfinance", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Politics")) {
//            db.update("politics", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Sports")) {
//            db.update("sports", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Property")) {
//            db.update("property", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Technology")) {
//            db.update("technology", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Entertainment & Gossip")) {
//            db.update("entertainment", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Movies and Series")) {
//            db.update("movies", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Health/Science")) {
//            db.update("health", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Lifestyle")) {
//            db.update("lifestyle", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Trivia")) {
//            db.update("trivia", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Jobs")) {
//            db.update("jobs", contentValues, "newsid=" + newsid, null);
//        }

        return true;
    }

    //    public boolean updateunread(String newsid, String readtype) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("readtype", readtype);
//        db.update("unread", contentValues, "newsid=" + newsid, null);
//
//        return true;
//    }
    public boolean updateLike(String newsid, String like, boolean like_status, String Cat_Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("likestatus", like_status);
        contentValues.put("newslike", like);
        if (Cat_Name.equalsIgnoreCase("All News")) {
            db.update("allnews", contentValues, "newsid=" + newsid, null);
        }
//        else if (Cat_Name.equalsIgnoreCase("Trending")) {
//            db.update("trending", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Top Stories")) {
//            db.update("topstory", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Saved Article/Bookmarks")) {
//            db.update("bookmark", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Unread")) {
//            db.update("unread", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Local")) {
//            db.update("local", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("International")) {
//            db.update("international", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Business and Finance")) {
//            db.update("businessandfinance", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Politics")) {
//            db.update("politics", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Sports")) {
//            db.update("sports", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Property")) {
//            db.update("property", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Technology")) {
//            db.update("technology", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Entertainment & Gossip")) {
//            db.update("entertainment", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Movies and Series")) {
//            db.update("movies", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Health/Science")) {
//            db.update("health", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Lifestyle")) {
//            db.update("lifestyle", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Trivia")) {
//            db.update("trivia", contentValues, "newsid=" + newsid, null);
//        } else if (Cat_Name.equalsIgnoreCase("Jobs")) {
//            db.update("jobs", contentValues, "newsid=" + newsid, null);
//        }

        return true;
    }

    public Integer deleteBookmark(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("bookmark",
                "newsid = ? ",
                new String[]{Integer.toString(id)});
    }

//    public Integer deleteletest(Integer id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete("letest",
//                "newsid = ? ",
//                new String[]{Integer.toString(id)});
//    }

    //    public ArrayList<String> getunread(){
//    ArrayList<String> array_list = new ArrayList<String>();
//    SQLiteDatabase db = this.getReadableDatabase();
//        String selectQuery = "SELECT readtype FROM local where readtype='"+"Unread"+"'";
//    Cursor res = db.rawQuery(selectQuery, null);
//    res.moveToFirst();
//
//    while (res.isAfterLast() == false) {
//        array_list.add(res.getString(res.getColumnIndex("readtype")));
//        res.moveToNext();
//    }
//    return array_list;
//}
    public ArrayList<String> getunread(String Cat_Name) {
        if (Cat_Name.equalsIgnoreCase("All News")) {
//            selectQuery = "SELECT readtype FROM allnews where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where all_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Trending")) {
//            selectQuery = "SELECT readtype FROM trending where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where trending_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Top Stories")) {
//            selectQuery = "SELECT readtype FROM topstory where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where topstory_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Saved Article/Bookmarks")) {
//            selectQuery = "SELECT readtype FROM bookmark where readtype='" + "Unread" + "'";
//            selectQuery = "SELECT readtype FROM allnews where trending_cat='true' and readtype='" + "Unread" + "'";
        } else if (Cat_Name.equalsIgnoreCase("Unread")) {
//            selectQuery = "SELECT readtype FROM unread where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where all_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Local")) {
//            selectQuery = "SELECT readtype FROM local where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where local_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("International")) {
//            selectQuery = "SELECT readtype FROM international where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where international_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Business and Finance")) {
//            selectQuery = "SELECT readtype FROM businessandfinance where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where business_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Politics")) {
//            selectQuery = "SELECT readtype FROM politics where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where politics_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Sports")) {
//            selectQuery = "SELECT readtype FROM sports where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where sport_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Property")) {
//            selectQuery = "SELECT readtype FROM property where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where property_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Technology")) {
//            selectQuery = "SELECT readtype FROM technology where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where technology_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Entertainment & Gossip")) {
//            selectQuery = "SELECT readtype FROM entertainment where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where entertainment_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Movies and Series")) {
//            selectQuery = "SELECT readtype FROM movies where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where movie_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Health/Science")) {
//            selectQuery = "SELECT readtype FROM health where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where health_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Lifestyle")) {
//            selectQuery = "SELECT readtype FROM lifestyle where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where lifestyle_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Trivia")) {
//            selectQuery = "SELECT readtype FROM trivia where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where trivia_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        } else if (Cat_Name.equalsIgnoreCase("Jobs")) {
//            selectQuery = "SELECT readtype FROM jobs where readtype='" + "Unread" + "'";
            selectQuery = "SELECT readtype FROM allnews where jobs_cat='true' and readtype='" + "Unread" + "'" + "and from_notification='false'";
        }

        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(selectQuery, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("readtype")));
            res.moveToNext();
        }
        return array_list;
    }


    public ArrayList<String> getalldata(String p_id) {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select p_id from allAlter where p_id='" + p_id + "'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("p_id")));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getalldatawithposition(String p_id,int position) {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select p_id from allAlter where p_id='" + p_id + "' AND insert_position='"+position+"'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("p_id")));
            res.moveToNext();
        }
        return array_list;
    }

//    public ArrayList<String> getletestdataString(String Cat_Name) {
//        ArrayList<String> array_list = new ArrayList<String>();
//
//        //hp = new HashMap();
//        Date date = new Date(System.currentTimeMillis() - (20 * 60 * 60 * 1000));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//         String datetime = sdf.format(date);
//        Cursor res = null;
//        SQLiteDatabase db = this.getReadableDatabase();
//        if (Cat_Name.equalsIgnoreCase("All News")) {
//             res = db.rawQuery("select newsid from allnews where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Trending")) {
//            res = db.rawQuery("select newsid from trending where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Top Stories")) {
//            res = db.rawQuery("select newsid from topstory where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Saved Article/Bookmarks")) {
//            res = db.rawQuery("select newsid from bookmark where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Unread")) {
//            res = db.rawQuery("select newsid from unread where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Local")) {
//            res = db.rawQuery("select newsid from local where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("International")) {
//            res = db.rawQuery("select newsid from international where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Business and Finance")) {
//            res = db.rawQuery("select newsid from businessandfinance where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Politics")) {
//            res = db.rawQuery("select newsid from politics where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Sports")) {
//            res = db.rawQuery("select newsid from sports where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Property")) {
//            res = db.rawQuery("select newsid from property where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Technology")) {
//            res = db.rawQuery("select newsid from technology where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Entertainment & Gossip")) {
//            res = db.rawQuery("select newsid from entertainment where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Movies and Series")) {
//            res = db.rawQuery("select newsid from movies where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Health/Science")) {
//            res = db.rawQuery("select newsid from health where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Lifestyle")) {
//            res = db.rawQuery("select newsid from lifestyle where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Trivia")) {
//            res = db.rawQuery("select newsid from trivia where datetime >=datetime", null);
//        } else if (Cat_Name.equalsIgnoreCase("Jobs")) {
//            res = db.rawQuery("select newsid from jobs where datetime >=datetime", null);
//        }
//
//        res.moveToFirst();
//
//        while (res.isAfterLast() == false) {
//            array_list.add(res.getString(res.getColumnIndex("newsid")));
//            res.moveToNext();
//        }
//        return array_list;
//    }

    public ArrayList<String> gettrendingdata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from trending", null);
        Cursor res = db.rawQuery("select newsid from allnews where trending_cat='true' and from_notification='false'", null);

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> gettopstorydata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from topstory", null);
        Cursor res = db.rawQuery("select newsid from allnews where topstory_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getbookmarkdata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select newsid from bookmark", null);
//        Cursor res = db.rawQuery("select newsid from allnews where trending_cat='true'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

//    public ArrayList<String> getunreaddata() {
//        ArrayList<String> array_list = new ArrayList<String>();
//
//        //hp = new HashMap();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from unread", null);
//        res.moveToFirst();
//
//        while (res.isAfterLast() == false) {
//            array_list.add(res.getString(res.getColumnIndex("newsid")));
//            res.moveToNext();
//        }
//        return array_list;
//    }

    public ArrayList<String> getlocaldata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from local", null);
        Cursor res = db.rawQuery("select newsid from allnews where local_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getunreaddata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from local", null);
        Cursor res = db.rawQuery("select newsid from allnews where all_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getinternationaldata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from international", null);
        Cursor res = db.rawQuery("select newsid from allnews where international_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getbusinessdata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from businessandfinance", null);
        Cursor res = db.rawQuery("select newsid from allnews where business_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getpoliticsdata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from politics", null);
        Cursor res = db.rawQuery("select newsid from allnews where politics_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getsportsdata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from sports", null);
        Cursor res = db.rawQuery("select newsid from allnews where sport_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getpropertydata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from property", null);
        Cursor res = db.rawQuery("select newsid from allnews where property_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> gettechnologydata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from technology", null);
        Cursor res = db.rawQuery("select newsid from allnews where technology_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getentertainmentdata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from entertainment", null);
        Cursor res = db.rawQuery("select newsid from allnews where entertainment_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getmoviesdata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from movies", null);
        Cursor res = db.rawQuery("select newsid from allnews where movie_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> gethealthdata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from health", null);
        Cursor res = db.rawQuery("select newsid from allnews where health_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getlifestyledata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from lifestyle", null);
        Cursor res = db.rawQuery("select newsid from allnews where lifestyle_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> gettriviadata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from trivia", null);
        Cursor res = db.rawQuery("select newsid from allnews where trivia_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getjobsdata() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select newsid from jobs", null);
        Cursor res = db.rawQuery("select newsid from allnews where jobs_cat='true' and from_notification='false'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("newsid")));
            res.moveToNext();
        }
        return array_list;
    }


}