package com.rexicore.eejhey.mycal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Eddie on 6/6/2015.
 */
public class mDayView extends ActionBarActivity {
    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();
    private ArrayList<HashMap<String, String>> usersList;
    private static String
            get_users_url = "http://waterbanana.comuv.com/dbhandler/get_userinfo.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USER = "user";
    private static final String TAG_USERID = "userid";
    private static final String TAG_DBID = "id";
    private static final String TAG_DATE = "date";
    private static final String TAG_START = "start";
    private static final String TAG_END = "end";
    private JSONArray users = null;
    private short ferror = 0;
    private TextView tv1, tv2, tv3, tv4,
                        tv5, tv6, tv7, tv8,
                        tv9, tv10, tv11, tv12,
                        tv13, tv14, tv15, tv16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        /*Intent intent = getIntent();
        String date = intent.getStringExtra("DATE");

        TextView tv = (TextView) findViewById(R.id.dateView);
        tv.setText(date);*/

        usersList = new ArrayList<HashMap<String, String>>();

        new LoadUserInfo().execute();

         tv1 = (TextView) findViewById( R.id.useridTV1 );
         tv2 = (TextView) findViewById( R.id.dateTV1 );
         tv3 = (TextView) findViewById( R.id.startTV1 );
         tv4 = (TextView) findViewById( R.id.endTV1 );

         tv5 = (TextView) findViewById( R.id.useridTV2 );
         tv6 = (TextView) findViewById( R.id.dateTV2 );
         tv7 = (TextView) findViewById( R.id.startTV2 );
         tv8 = (TextView) findViewById(R.id.endTV2 );

         tv9 = (TextView) findViewById( R.id.useridTV3 );
         tv10 = (TextView) findViewById(R.id.dateTV3 );
         tv11 = (TextView) findViewById( R.id.startTV3 );
         tv12 = (TextView) findViewById( R.id.endTV3 );

         tv13 = (TextView) findViewById( R.id.useridTV4 );
         tv14 = (TextView) findViewById( R.id.dateTV4 );
         tv15 = (TextView) findViewById( R.id.startTV4 );
         tv16 = (TextView) findViewById( R.id.endTV4 );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_m_day_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadUserInfo extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog( mDayView.this );
            pDialog.setMessage( "Loading..." );
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jsonParser.makeHttpRequest( get_users_url, "GET", params );
            Log.d("Get users: ", json.toString());
            try{
                int success = json.getInt( TAG_SUCCESS );
                if( success == 1 ){
                    users = json.getJSONArray( TAG_USER );

                    for( int i = 0; i < users.length(); i++ ){
                        JSONObject u = users.getJSONObject( i );
                        String userid = u.getString(TAG_USERID);
                        String date = u.getString(TAG_DATE);
                        int start = u.getInt(TAG_START);
                        int end = u.getInt( TAG_END );

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put( TAG_USERID, userid );
                        map.put( TAG_DATE, date );
                        map.put( TAG_START, String.valueOf(start) );
                        map.put( TAG_END, String.valueOf(end) );

                        usersList.add( map );
                    }
                }
                else{
                    ferror = 1;
                }
            } catch ( JSONException e ){
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute( String file_url ){
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv1.setText( usersList.get(0).get(TAG_USERID));
                    tv2.setText( usersList.get(0).get(TAG_DATE));
                    tv3.setText( usersList.get(0).get(TAG_START));
                    tv4.setText( usersList.get(0).get(TAG_END));

                    tv5.setText( usersList.get(1).get(TAG_USERID));
                    tv6.setText( usersList.get(1).get(TAG_DATE));
                    tv7.setText( usersList.get(1).get(TAG_START));
                    tv8.setText( usersList.get(1).get(TAG_END));

                    tv9.setText( usersList.get(2).get(TAG_USERID));
                    tv10.setText( usersList.get(2).get(TAG_DATE));
                    tv11.setText( usersList.get(2).get(TAG_START));
                    tv12.setText( usersList.get(2).get(TAG_END));

                    tv13.setText( usersList.get(3).get(TAG_USERID));
                    tv14.setText( usersList.get(3).get(TAG_DATE));
                    tv15.setText( usersList.get(3).get(TAG_START));
                    tv16.setText( usersList.get(3).get(TAG_END));
                }
            });
        }
    }
}
