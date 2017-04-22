package minor.hackathon123;

import android.widget.Button;


        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
        import org.apache.http.entity.StringEntity;
        import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class signup extends AppCompatActivity {

            EditText name, address, email, password,confirm;
            static String name1;
            static String address1;
            static String email1;
            static String password1;
            String confirm1;

            Button signUp;
            ProgressDialog p;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_signup);
                name=(EditText) findViewById(R.id.textView5);
                address=(EditText)findViewById(R.id.textView6);
                email=(EditText)findViewById(R.id.textView7);
                password=(EditText)findViewById(R.id.textView8);
                confirm=(EditText)findViewById(R.id.textView9);
                signUp =(Button) findViewById(R.id.signUp);

                p=new ProgressDialog(this);
                p.setTitle("Signup");
                p.setMessage("Signup in process");




                signUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name1=name.getText().toString();
                        email1=email.getText().toString();
                        confirm1=confirm.getText().toString();
                        password1=password.getText().toString();
                        address1=address.getText().toString();


                        if(name1.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Please enter name",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(password1.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_LONG).show();
                            return;
                        }if(address1.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Please enter address",Toast.LENGTH_LONG).show();
                            return;
                        }if(email1.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_LONG).show();
                            return;
                        }



                        if(!confirm1.equals(password1))
                        {
                            Toast.makeText(getApplicationContext(),"Confirm password and password does not match",Toast.LENGTH_LONG).show();
                            return;
                        }
                        p.show();
                        new HttpAsyncTask().execute();


                    }
                });



            }
            public static String POST(){
                InputStream inputStream = null;
                String result = "";
                try {
                    Log.e("tag","reached post");

                    // 1. create HttpClient
                    HttpClient httpclient = new DefaultHttpClient();

                    // 2. make POST request to the given URL
                    HttpPost httpPost = new HttpPost("http://172.16.104.215:9000/app1/signup");

                    String json = "";

                    // 3. build jsonObject
//            String movie=movie_name.getText().toString();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("email", email1);
                    jsonObject.accumulate("password", password1);
                    jsonObject.accumulate("name", name1);
                    jsonObject.accumulate("address", address1);


                    // 4. convert JSONObject to JSON to String
                    json = jsonObject.toString();

                    // ** Alternative way to convert Person object to JSON string usin Jackson Lib
                    // ObjectMapper mapper = new ObjectMapper();
                    // json = mapper.writeValueAsString(person);

                    // 5. set json to StringEntity

                    StringEntity se = new StringEntity(json);

                    // 6. set httpPost Entity
                 /*   List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("email", email1));
                    nameValuePairs.add(new BasicNameValuePair("password", password1));
                    nameValuePairs.add(new BasicNameValuePair("address", address1));
                    nameValuePairs.add(new BasicNameValuePair("name", name1));

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                   */
                    httpPost.setEntity(se);

                    // 7. Set some headers to inform server about the type of the content
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
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
                    p.dismiss();
                    try {
                        reader = new JSONObject(result);

                      Log.e("http",  reader.getString("error"));

                    Log.e("http"," "+result);

                    if(reader.getString("error").equals("1"))
                    {
                        Toast.makeText(getBaseContext(), "Signup Failed", Toast.LENGTH_LONG).show();

                    }
                    if(reader.getString("error").equals("0"))
                    {
                        Toast.makeText(getBaseContext(), "Signed up successfully", Toast.LENGTH_LONG).show();


                        Intent i =new Intent(signup.this, MainActivity.class);
                        startActivity(i);

                    }

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

        }
