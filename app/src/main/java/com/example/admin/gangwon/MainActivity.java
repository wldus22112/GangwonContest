package com.example.admin.gangwon;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.gangwon.Activity.EmergencyMedical;
import com.example.admin.gangwon.Activity.Splash;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static java.lang.Double.valueOf;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();



    private LinearLayout button1;
    private LinearLayout button2;
    private LinearLayout button3;
    private EmergencyMedical map ;

    int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;

    private Document doc = null;
    private String nowWeather = null;
    private String temp = null;
    public String s = null;

    private TextView textView;
    private TextView textView1;

    String result = null;
    GetXMLTask task;

    private ImageView weatherIcon;

    long now;
    Date date;

    String strCurHour;
    String strCurMinute;
    int hour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        startActivity(new Intent(this,Splash.class));
        permissionCheck();


        button1 = (LinearLayout)findViewById(R.id.button1);
        button2 = (LinearLayout)findViewById(R.id.button2);
        button3 = (LinearLayout) findViewById(R.id.button3);

        textView = (TextView)findViewById(R.id.textView);
        textView1 = (TextView)findViewById(R.id.textView1);
        //textView.setText(temp);



        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);

        task = new GetXMLTask();
        task.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=93&gridy=131");

        now = System.currentTimeMillis();
        date = new Date(now);
        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");

        strCurHour = CurHourFormat.format(date);
        strCurMinute = CurMinuteFormat.format(date);
       // textView.setText(strCurHour +":"+ strCurMinute);
        hour = valueOf(strCurHour).intValue();





    }

    public void permissionCheck(){
        if((ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                )

        {
            ActivityCompat.requestPermissions
                    (MainActivity.this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE
                    },MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE);
        }


    }


    public void onClick(View view) {
        Intent intent = new Intent(this,EmergencyMedical.class);
        int number = 0;
        String name =  null;
        switch (view.getId()){
            case R.id.button1:
                number =1;
                name = "응급의료센터";
                intent.putExtra("Number",number);
                intent.putExtra("Name",name);
                startActivity(intent);
                break;
            case R.id.button2:

                number =2;
                name = "병원 정보";
                intent.putExtra("Number",number);
                intent.putExtra("Name",name);
                startActivity(intent);

                break;
            case R.id.button3:

                number =3;
                name = "약국 정보";
                intent.putExtra("Number",number);
                intent.putExtra("Name",name);
                startActivity(intent);


                break;



        }

    }


    //private inner class extending AsyncTask
    private class GetXMLTask extends AsyncTask<String, Void, Document>{

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {

            String s = "";
            //data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
            NodeList nodeList = doc.getElementsByTagName("data");
            //data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환

            for(int i = 0; i< nodeList.getLength(); i++){

                //날씨 데이터를 추출
                s += "" +i + ": 날씨 정보: ";
                Node node = nodeList.item(i); //data엘리먼트 노드
                Element fstElmnt = (Element) node;
                NodeList nameList  = fstElmnt.getElementsByTagName("temp");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                s += "온도 = "+ ((Node) nameList.item(0)).getNodeValue() +" ,";


                NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
                //<wfKor>맑음</wfKor> =====> <wfKor> 태그의 첫번째 자식노드는 TextNode 이고 TextNode의 값은 맑음
                s += "날씨 = "+  websiteList.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                if(i==0){
                    nowWeather = websiteList.item(0).getChildNodes().item(0).getNodeValue();
                    temp = ((Node) nameList.item(0)).getNodeValue();
                }


            }

            super.onPostExecute(doc);
            Log.v("nowWeatehr : ",nowWeather);
            Log.v("temp : ",temp);
            //textView.setText(s);
            Log.v("s",s);
            weatherIconChange(nowWeather);
            temp = temp + " ℃";
            textView1.setText(temp);
            textView.setText(nowWeather);

        }


    }//end inner class - GetXMLTask


    private void weatherIconChange(String input) {

        if(input.equals("구름 많음")){
            weatherIcon.setImageResource(R.drawable.cloud);
        }
        else if(input.equals("맑음")){
            //달
            if((hour<7)||(hour>18)){
                weatherIcon.setImageResource(R.drawable.moon6);
            }
            //해
            else{
                weatherIcon.setImageResource(R.drawable.sun);
            }



        }
        else if(input.equals("구름 조금")){

            //밤
            if((hour<7)||(hour>18)){
                weatherIcon.setImageResource(R.drawable.cloud1);
            }
            //낮
            else{
                weatherIcon.setImageResource(R.drawable.cloudy);
            }

        }
        else if(input.equals("흐림")){
            weatherIcon.setImageResource(R.drawable.cloud);
        }
        else if(input.equals("비")){
            weatherIcon.setImageResource(R.drawable.rain1);
        }
       else if(input.equals("눈")){
            weatherIcon.setImageResource(R.drawable.snowflake);
        }
        else if(input.equals("소나기")){
            weatherIcon.setImageResource(R.drawable.rain);
        }
        else if(input.equals("연무")||input.equals("박무")||input.equals("안개")){
            weatherIcon.setImageResource(R.drawable.fog);
        }
        else if(input.equals("가끔 비")||input.equals("한때 비")||input.equals("가끔 눈")||input.equals("한때 눈")||input.equals("가끔 비 또는 눈")
                ||input.equals("한때 비 또는 눈")||input.equals("가끔 눈 또는 비")||input.equals("한때 눈 또는 비")){
            weatherIcon.setImageResource(R.drawable.drop);
        }
        else if(input.equals("천둥번개")){
            weatherIcon.setImageResource(R.drawable.bolt);
        }
    }


}
