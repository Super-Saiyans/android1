package minor.hackathon123;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

public class Detector extends AppCompatActivity implements BeaconConsumer, RangeNotifier {
    private static final int PERMISSION_LOCATION_REQUEST_CODE =2 ;
    private static String TAG = "MyActivity";
    private BeaconManager mBeaconManager;
public static long t1,t2,t3,t4,t5,t6,t7,t8;
    public static long l1,l2,l3;

    public int last=0;
    public long state=0;
    public long state1=0;
Button b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector);
        t1=0;
        t2=0;
        t3=0;
        t4=0;
        t5=0;
        t6=0;
        t7=0;
        t8=0;
        b3=(Button) findViewById(R.id.button3);


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpAsyncTask().execute();




            }
        });
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();// mBluetoothAdapter.enable();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            return;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR);

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_LOCATION_REQUEST_CODE);

        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        // Detect the URL frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
        mBeaconManager.bind(this);

    }

        @Override
        public void onResume() {
            super.onResume();
            mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
            // Detect the URL frame:
            mBeaconManager.getBeaconParsers().add(new BeaconParser().
                    setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
            mBeaconManager.bind(this);
        }

        public void onBeaconServiceConnect() {
            Region region = new Region("all-beacons-region", null, null, null);
            try {
                mBeaconManager.startRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mBeaconManager.setRangeNotifier(this);
        }

        @Override
        public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
            for (Beacon beacon: beacons) {
                if (beacon.getServiceUuid() == 0xfeaa && beacon.getBeaconTypeCode() == 0x10) {

                 byte array[]=   beacon.getId1().toByteArray();
                    if(array[0]==(byte)(0xF1))
                    {
                        if(last==1)
                        {
                              t1+=System.currentTimeMillis()-l1;
                              l1=System.currentTimeMillis();
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            TextView t=(TextView) findViewById(R.id.textView);
                                            t.setText("Current Activity: Sleeping for "+t1/1000+" hours");
                                        }
                                    }, 1);

                                }
                            });


                        }

                        l1=System.currentTimeMillis();

                        last=1;
                        Log.d(TAG, "t1 " + t1/60000 );
                    }

                    if(array[0]==(byte)(0xF2))
                    {
                        if(last!=2)
                        {
                            t2++;
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            TextView t=(TextView) findViewById(R.id.textView);
                                            t.setText("Current Activity: Eating count"+t2);
                                        }
                                    }, 1);

                                }
                            });


                        }


                        last=2;
                    }

                    if(array[0]==(byte)(0xF3))
                    {
                        if(last!=3)
                        {
                            t3++;
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            TextView t=(TextView) findViewById(R.id.textView);
                                            t.setText("Current Activity: Drinking water count"+t3);
                                        }
                                    }, 1);

                                }
                            });


                        }


                        last=3;
                    }
                    if(array[0]==(byte)(0xF4))
                    {
                        if(last!=4)
                        {
                            t4++;
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            TextView t=(TextView) findViewById(R.id.textView);
                                            t.setText("Current Activity: Washroom count"+t4);
                                        }
                                    }, 1);

                                }
                            });


                        }


                        last=4;
                    }
                    if(array[0]==(byte)(0xF5))
                    {
                        if(last!=5)
                        {
                            t5++;
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            TextView t=(TextView) findViewById(R.id.textView);
                                            t.setText("Current Activity: Shower count"+t5);
                                        }
                                    }, 1);

                                }
                            });


                        }


                        last=5;
                    }

                    if(array[0]==(byte)(0xF6))
                    {

                        if(last!=6)
                        {

                            if(state==0)
                            {
                                l2=System.currentTimeMillis();
                                state=1;
                            }
                           else if(state==1) {
                                t6 += System.currentTimeMillis() - l2;
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                TextView t = (TextView) findViewById(R.id.textView);
                                                t.setText("Time in house" + t6 / 1000 + " minutes");
                                            }
                                        }, 1);

                                    }
                                });
                         state=0;
                            }

                        }


                        last=6;
                        Log.d(TAG, "t1 " + t1/60000 );



                    }

                    if(array[0]==(byte)(0xF7))
                    {

                        if(last!=7)
                        {

                            if(state1==0)
                            {
                                l3=System.currentTimeMillis();
                                state1=1;
                            }
                            else if(state1==1) {
                                t7 += System.currentTimeMillis() - l3;
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                TextView t = (TextView) findViewById(R.id.textView);
                                                t.setText("Time out of house" + t7 / 1000 + " minutes");
                                            }
                                        }, 1);

                                    }
                                });
                                state1=0;
                            }

                        }


                        last=7;
                        Log.d(TAG, "t1 " + t1/60000 );


                    }


                    }





                    Log.d(TAG, "I see a beacon transmitting a url: " + getHex(beacon.getId1().toByteArray()) );
                }
            }


        @Override
        public void onPause() {
            super.onPause();
            mBeaconManager.unbind(this);
        }
    public static String getHex( byte [] raw ) {
        final String HEXES = "0123456789ABCDEF";
        if ( raw == null ) {
            return null;
        }
        final StringBuilder hex = new StringBuilder( 2 * raw.length );
        for ( final byte b : raw ) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }



    public static String POST(){
        InputStream inputStream = null;
        String result = "";
        try {
            Log.e("tag","reached post");

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost("http://192.168.137.133:9000/app1/req");

            String json = "";

            // 3. build jsonObject
//            String movie=movie_name.getText().toString();
            JSONObject jsonObject = new JSONObject();
          /*  jsonObject.accumulate("eating", 3);
            jsonObject.accumulate("drinking",9);
            jsonObject.accumulate("toileting", 4);
            jsonObject.accumulate("leaving",8);
            jsonObject.accumulate("showering", 3);
            jsonObject.accumulate("sleeping",10);
            jsonObject.accumulate("weight", 110.0);
*/
            jsonObject.accumulate("eating", t2);
            jsonObject.accumulate("drinking",t3);
            jsonObject.accumulate("toileting", t4);
            jsonObject.accumulate("leaving",8);
            jsonObject.accumulate("showering", t5);
            jsonObject.accumulate("sleeping",t1/1000);
            jsonObject.accumulate("weight", 110.0);


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity

            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            Log.i("Tag",json+" ");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result

        Log.e("tag",result);
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        String var="";
        @Override
        protected String doInBackground(String... urls) {


            Log.e("http","reached async");

            var= POST();

            return var;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            JSONObject reader;
//            p.dismiss();
            try {
                reader = new JSONObject(result);

                Log.e("http",  reader.getString("error"));

                Log.e("http"," "+result);


                int a1=1, a2=1, a3=1, a4=1, a5=1, a6=1;
                int a11=1, a21=1, a31=1, a41=1, a51=1, a61=1;

                double a7,a71;
                int d1=2, d2=2, d3=2, d4=2, d5=2, d6=2, d7=-2;
                int d11=-2, d21=1, d31=2, d41=-2, d51=2, d61=1, d71=1;

                a1=Integer.parseInt(reader.getString("eating"));
                a2=Integer.parseInt(reader.getString("drinking"));
                a3=Integer.parseInt(reader.getString("toileting"));
                a4=Integer.parseInt(reader.getString("leaving"));
                a5=Integer.parseInt(reader.getString("showering"));
                a6=Integer.parseInt(reader.getString("sleeping"));
                a7=Double.parseDouble(reader.getString("weight"));

                a11=Integer.parseInt(reader.getString("eating"));
                a21=Integer.parseInt(reader.getString("drinking"));
                a31=Integer.parseInt(reader.getString("toileting"));
                a41=Integer.parseInt(reader.getString("leaving"));
                a51=Integer.parseInt(reader.getString("showering"));
                a61=Integer.parseInt(reader.getString("sleeping"));
                a71=Double.parseDouble(reader.getString("weight"));

                /*
                jsonObject.accumulate("drinking",t2);
                jsonObject.accumulate("toileting", t3);
                jsonObject.accumulate("leaving",t4);
                jsonObject.accumulate("showering", t5);
                jsonObject.accumulate("sleeping",t6);
                jsonObject.accumulate("weight", 60.0);*/



                a1 = d1 - a1;
    a1 = (int)Math.pow(a1, 2);
    a2 = d2 - a2;
    a2 = (int)Math.pow(a2, 2);
    a3 = d3 - a3;
    a3 = (int)Math.pow(a3, 2);
    a4 = d4 - a4;
    a4 = (int)Math.pow(a4, 2);
    a5 = d5 - a5;
    a5 = (int) Math.pow(a5, 2);
    a6 = d6 - a6;
    a6 = (int) Math.pow(a6, 2);
    a7 = d7 - a7;
    a7 = (int) Math.pow(a7, 2);


                a11 = d11 - a11;
                a11 = (int)Math.pow(a11, 2);
                a21 = d21 - a21;
                a21 = (int)Math.pow(a21, 2);
                a31 = d31 - a31;
                a31 = (int)Math.pow(a31, 2);
                a41 = d41 - a41;
                a41 = (int)Math.pow(a41, 2);
                a51 = d51 - a51;
                a51 = (int) Math.pow(a51, 2);
                a61 = d61 - a61;
                a61 = (int) Math.pow(a61, 2);
                a71 = d71 - a71;
                a71 = (int) Math.pow(a71, 2);

                int sum = a1 + a2 + a3 + a4 + a5 + a6 + (int)a7;
                double res = (double)sum / 112;

Log.e("http",sum+" ");

                final double prob = 1 - Math.pow(res, 0.5);


                int sum1 = a11+ a21 + a31 + a41 + a51 + a61 + (int)a71;
                double res1 = (double)sum1 / 112;

                Log.e("http",sum+" ");

                final double prob1 = 1 - Math.pow(res1, 0.5);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                TextView t=(TextView) findViewById(R.id.textView);
                                t.setText("Probability for diabetes "+prob +"\n"+"Probability for depression"+prob1);
                            }
                        }, 1000);

                    }
                });



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
public void function() {
    int a1, a2, a3, a4, a5, a6, a7;
    int d1, d2, d3, d4, d5, d6, d7;
 /*   a1 = d1 - a1;
    a1 = Math.pow(a1, 2);
    a2 = d2 - a2;
    a2 = Math.pow(a2, 2);
    a3 = d3 - a3;
    a3 = Math.pow(a3, 2);
    a4 = d4 - a4;
    a4 = Math.pow(a4, 2);
    a5 = d5 - a5;
    a5 = Math.pow(a5, 2);
    a6 = d6 - a6;
    a6 = Math.pow(a6, 2);
    a7 = d7 - a7
    a7 = Math.pow(a7, 2);
 */  // int sum = a1 + a2 + a3 + a4 + a5 + a6 + a7;
    //int res = sum / 112;
    //double prob = 1 - Math.pow(res, 2);
}

}
