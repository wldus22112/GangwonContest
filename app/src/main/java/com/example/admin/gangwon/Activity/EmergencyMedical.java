package com.example.admin.gangwon.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.admin.gangwon.AddMarker;
import com.example.admin.gangwon.CallNumber;
import com.example.admin.gangwon.Controller.SearchNearPoint;
import com.example.admin.gangwon.StateCheck.GPSCheck;
import com.example.admin.gangwon.MapViewEvent.MapViewEventListener;
import com.example.admin.gangwon.MapViewEvent.POIItemEventListener;
import com.example.admin.gangwon.R;
import com.example.admin.gangwon.SpinnerListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import static java.lang.Double.valueOf;

/**
 * Created by admin on 2016-06-28.
 */
public class EmergencyMedical  extends AppCompatActivity  implements  MapView.CurrentLocationEventListener{

    private static final String TAG = EmergencyMedical.class.getSimpleName();


    private String key = "90fd8851c587596b42ff622eefd2ec98";
    private MapView mapView;
    private ViewGroup mapViewContainer ;
    private AQuery aq = new AQuery( this );
    private String open_api = "6b546475576b6b393337426d666762";
    private TextView textView;
    private AddMarker addmarker = new AddMarker();
    private Document doc = null;
    private String url = null;
    private String intentName = null;
    private int intentNum=0;
    public String currentlat = null;
    public String cuurentlng = null;

    public Button tab1;
    public Button tab2;
    public Button tab3;
    private Toolbar toolbar;

    private CallNumber callnum;
    public boolean ButtonClick = false;

    public ProgressDialog dialog;
    public Button exploration;

    public  MapPOIItem[] poiItemsMain;
    public  MapPOIItem[] searchResult;
    public  MapPOIItem[] poiItems;
    public  String nearMarker = null;
    public Spinner spinner;
    public Spinner spinner2;

    public SpinnerListener spinnerListener;
    public String getSpinner;
    public Button search;

    private boolean searchTrue = false;
    public String zone = null;
    public String treat = null;
    public String objectUrl = null;
    private GPSCheck gpscheck;

   // CurrentLocationEventListener currentLocationEventListener;
    MapViewEventListener mapViewEventListener;
    POIItemEventListener poiItemEventListener;



    SearchNearPoint searchNearPoint;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);

        Log.d("TAB : ", TAG);

      //  toolbar = (Toolbar)findViewById(R.id.toolbar);

        //MenuInflater inflater = getMenuInflater();
       // toolbar.inflateMenu(R.menu.menu_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent intent = getIntent();
        intentName = intent.getStringExtra("Name");
        intentNum = intent.getIntExtra("Number",0);
        Log.d("intent : ", intentName);
        Log.d("intent : ", Integer.toString(intentNum));

        aq = new AQuery(this);
        spinnerListener = new SpinnerListener();
       // currentLocationEventListener = new CurrentLocationEventListener();
        mapViewEventListener = new MapViewEventListener();
        poiItemEventListener = new POIItemEventListener(this, intentNum);
        gpscheck = new GPSCheck(this);


        mapView = new MapView(this);
        mapView.setDaumMapApiKey(key);
        mapViewContainer = (ViewGroup) findViewById(R.id.map);
        mapViewContainer.addView(mapView);
        mapView.setCurrentLocationEventListener((MapView.CurrentLocationEventListener)this);
       // mapView.setCurrentLocationEventListener(currentLocationEventListener);
        mapView.setMapViewEventListener(mapViewEventListener);
        mapView.setPOIItemEventListener(poiItemEventListener);

        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);



        tab1 = (Button)findViewById(R.id.tab1);
        tab2 = (Button)findViewById(R.id.tab2);
        tab3 = (Button)findViewById(R.id.tab3);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        search = (Button) findViewById(R.id.spinnerbtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.planets_array2, android.R.layout.simple_spinner_item);

        spinner2.setAdapter(adapter);

        exploration = (Button) findViewById(R.id.exploration);
        exploration.bringToFront();

        if(intentNum==1){

            spinner.setVisibility(View.GONE);
            spinner2.setVisibility(View.GONE);
            search.setVisibility(View.GONE);

            tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


            url = "http://data.gwd.go.kr/apiservice/6b546475576b6b393337426d666762/json/localdata-health_medica-emergency_medical_care_center/1/30/";
            objectUrl = "localdata-health_medica-emergency_medical_care_center";
            getSupportActionBar().setTitle(intentName);
            dialog = ProgressDialog.show(EmergencyMedical.this, "",
                    "로딩 중입니다. 잠시 기다려주세요", true);
            aq.ajax(url, JSONObject.class, this, "jsonCallback");
            Log.d("execute : ", "jsonCallback");
        }
       else if(intentNum==2){

            spinner.setVisibility(View.VISIBLE);
            spinner2.setVisibility(View.VISIBLE);

            getSupportActionBar().setTitle(intentName);

            tab2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));



        }
        else if(intentNum==3){


            spinner.setVisibility(View.VISIBLE);
            spinner2.setVisibility(View.GONE);

            getSupportActionBar().setTitle(intentName);

            tab3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            exploration.setText("가까운 약국 찾기");


        }


    }





//병원, 약국 찾기
    public void jsonCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.d("url : ", url);
        Log.d("json", json.toString());
        Log.d("status", status.toString());
        Log.d("json", json.getJSONObject(objectUrl).toString());

        String temp = null;
        String result = null;
        int count = 0;


        if(json != null){
            //json.getJSONArray("air").length()
            //  textView.setText(json.toString());

            for(int i =0; i<json.getJSONObject(objectUrl).getJSONArray("row").length(); i++)
            {

                try {
                    if (intentNum == 1) {

                        mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LAT").toString()).doubleValue(),
                                valueOf(json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LNG").toString()).doubleValue(),
                                json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("BIZPLC_NM").toString(), 1));

                    } else if (intentNum == 2) {
                        //종합병원일 경우
                        if (treat.contains("종합병원") == true) {

                            if (json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LOCPLC_LOTNO_ADDR").toString().contains(zone) == true) {

                                mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LAT").toString()).doubleValue(),
                                        valueOf(json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LNG").toString()).doubleValue(),
                                        json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("BIZPLC_NM").toString(), 2));
                                count++;
                            }
                            //그외 진료과목을 선택했을 경우
                        } else {

                            //treat / zone을 포함하고 있는 병원만 마커 표시
                            if (json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("TREAT_SBJECT_CONT").toString().contains(treat) == true &&
                                    json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LOCPLC_LOTNO_ADDR").toString().contains(zone) == true) {

                                mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LAT").toString()).doubleValue(),
                                        valueOf(json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LNG").toString()).doubleValue(),
                                        json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("BIZPLC_NM").toString(), 2));
                                count++;

                            }
                        }

                    } else if (intentNum == 3) {
                        //약국 지역별 표시
                        if (json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LOCPLC_LOTNO_ADDR").toString().contains(zone) == true &&
                                json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LAT").toString() != "null" &&
                                json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LNG").toString() != "null" &&
                                json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("BIZPLC_NM").toString() != "null") {


                            mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LAT").toString()).doubleValue(),
                                    valueOf(json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("LNG").toString()).doubleValue(),
                                    json.getJSONObject(objectUrl).getJSONArray("row").getJSONObject(i).get("BIZPLC_NM").toString(), 2));
                            count++;

                        }

                    }



                }catch (NumberFormatException e){
                    Log.d("NumberFormatException :",Integer.toString(i));
                }
            }

            if(count==0&&intentNum==2){
                Toast.makeText(EmergencyMedical.this, "해당 지역에는 병원이 존재하지 않습니다", Toast.LENGTH_SHORT).show();
            }
            else if(count==0&&intentNum==3){
                Toast.makeText(EmergencyMedical.this, "해당 지역에는 약국이 존재하지 않습니다", Toast.LENGTH_SHORT).show();
            }

            Log.d("count : ",Integer.toString(count));

        }else{
            Log.d("Null","Null");
        }

        //textView.setText(result);


        mapView.fitMapViewAreaToShowAllPOIItems();// 지도 위 마커가 모두 나타나도록 지도 확대 레벨 자동 조정
        dialog.dismiss();

    }












    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }





    public void onClick(View view) {

        switch (view.getId()){

          case  R.id.tab1 :

              ButtonClick = false;
              mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
              Log.d("TrackingMode : ", "trackin Mode OFF");

              spinner.setVisibility(View.GONE);
              spinner2.setVisibility(View.GONE);
              search.setVisibility(View.GONE);

              getSupportActionBar().setTitle("응급의료센터");
              exploration.setText("가까운 병원 찾기");

              mapView.removeAllPOIItems();
              intentNum=1;
              tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
              tab2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
              tab3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

              if(poiItemsMain!=null){
                  mapView.addPOIItems(poiItemsMain);
                  mapView.fitMapViewAreaToShowAllPOIItems();
              }
              else{
                  url = "http://data.gwd.go.kr/apiservice/6b546475576b6b393337426d666762/json/localdata-health_medica-emergency_medical_care_center/1/30/";
                  objectUrl = "localdata-health_medica-emergency_medical_care_center";
                  dialog = ProgressDialog.show(EmergencyMedical.this, "",
                          "로딩 중입니다. 잠시 기다려주세요", true);
                  aq.ajax(url, JSONObject.class, this, "jsonCallback");
                  Log.d("execute : ", "jsonCallback");
              }


              break;
            case R.id.tab2 :


                ButtonClick = false;
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                Log.d("TrackingMode : ", "trackin Mode OFF");

                spinner.setVisibility(View.VISIBLE);
                spinner2.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);

                getSupportActionBar().setTitle("병원 정보");
                exploration.setText("가까운 병원 찾기");

                tab2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                if(intentNum==1){
                    poiItemsMain = mapView.getPOIItems();
                }


                mapView.removeAllPOIItems();
                intentNum=2;
                break;
            case R.id.tab3 :

                ButtonClick = false;
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                Log.d("TrackingMode : ", "trackin Mode OFF");

                spinner.setVisibility(View.VISIBLE);
                spinner2.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);

                getSupportActionBar().setTitle("약국 정보");
                exploration.setText("가까운 약국 찾기");


                tab3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                tab2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                if(intentNum==1){
                    poiItemsMain = mapView.getPOIItems();
                }

                mapView.removeAllPOIItems();
                intentNum =3;

                break;

            case R.id.exploration:

                if(ButtonClick==true &&mapView.getCurrentLocationTrackingMode() == MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading){
                  //  ButtonClick = false;
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);

                    Log.d("TrackingMode : ", "trackin Mode OFF");

                    Log.v("gps", "gps 끄기");
                    if(intentNum==3){
                        exploration.setText("가까운 약국 찾기");
                    }else{
                        exploration.setText("가까운 병원 찾기");
                    }


                }
                else {

                    ButtonClick = true;
                    String gpstype = gpscheck.getLocation();
                    if(gpstype!="NOT"){

                        if(mapView.getPOIItems().length!=0){

                            gpstype = gpscheck.getLocation();
                           // ButtonClick = true;
                            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

                            Log.d("gps", "gps 켜기");
                            exploration.setText("GPS를 끄려면 클릭하세요");

                            dialog = ProgressDialog.show(EmergencyMedical.this, "", "로딩 중입니다. 잠시 기다려주세요", true);


                            //Toast.makeText(getBaseContext(), "Searching", Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(getBaseContext(), "지역을 선택하고 Search 를 클릭한 후 다시 시도해주세요", Toast.LENGTH_LONG).show();
                        }


                    }else{
                        Log.d("gps", "gps 연결 안됨");
                        gpscheck.showSettingAlert();
                    }




                }

                 poiItemsMain = mapView.getPOIItems();


                break;
            case R.id.spinnerbtn :

                if(intentNum==2){

                    mapView.removeAllPOIItems();
                    searchTrue = true;
                    Toast.makeText(getBaseContext(), spinner.getSelectedItem().toString() + " " + spinner2.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                    zone = spinner.getSelectedItem().toString();
                    treat = spinner2.getSelectedItem().toString();

                    if(treat.contains("종합병원")==true && zone!=null){

                        dialog = ProgressDialog.show(EmergencyMedical.this, "", "로딩 중입니다. 잠시 기다려주세요", true);
                        objectUrl = "localdata-health_medica-general_hospital";
                        url = "http://data.gwd.go.kr/apiservice/6b546475576b6b393337426d666762/json/localdata-health_medica-general_hospital/1/33/";

                        aq.ajax(url, JSONObject.class, this, "jsonCallback");
                        Log.d("execute : ", "jsonCallback");

                    }
                    else if(treat.contains("종합병원")!=true && zone!=null){

                        dialog = ProgressDialog.show(EmergencyMedical.this, "",
                                "로딩 중입니다. 잠시 기다려주세요", true);
                        objectUrl = "localdata-health_medica-clinic";
                        url = "http://data.gwd.go.kr/apiservice/6b546475576b6b393337426d666762/json/localdata-health_medica-clinic/1/800/";

                        aq.ajax(url, JSONObject.class, this, "jsonCallback");
                        Log.d("execute : ", "jsonCallback");


                    }
                    else if(zone==null || spinner2.getSelectedItem().toString()=="null"){
                        Toast.makeText(getBaseContext(),"지역과 진료과목을 선택하세요", Toast.LENGTH_SHORT).show();
                    }

                }else if(intentNum==3)
                {
                    mapView.removeAllPOIItems();

                    dialog = ProgressDialog.show(EmergencyMedical.this, "",
                            "로딩 중입니다. 잠시 기다려주세요", true);
                    zone = spinner.getSelectedItem().toString();
                    Log.d("pharmach : ",zone);
                    url = "http://data.gwd.go.kr/apiservice/6b546475576b6b393337426d666762/json/localdata-health_medica-pharmacy/1/700/";
                    objectUrl = "localdata-health_medica-pharmacy";
                    aq.ajax(url, JSONObject.class, this, "jsonCallback");
                    searchTrue = true;
                    Toast.makeText(getBaseContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }


    //ProgressBar dismiss를 위해 EmergencyMedical내에 구현
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {


        mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        Log.d("LocationUpdate : ","gps 탐색");

        String lat = String.valueOf(mapPoint.getMapPointGeoCoord().latitude);
        String lng = String.valueOf(mapPoint.getMapPointGeoCoord().longitude);
        Log.d("lat",lat);
        Log.d("lng",lng);

        currentlat = lat;
        cuurentlng = lng;

        // Toast.makeText(EmergencyMedical.this, lat + ", " +lng, Toast.LENGTH_SHORT).show();


        searchNearPoint = new SearchNearPoint(mapView.getPOIItems(),currentlat,cuurentlng);
        nearMarker= searchNearPoint.getNearMarker();

        searchResult =  mapView.findPOIItemByName(nearMarker);
        Log.d("searchResult : ", Integer.toString(searchResult.length));
        mapView.selectPOIItem(searchResult[0],true);
        mapView.setMapCenterPoint(searchResult[0].getMapPoint(),true);

        dialog.dismiss();

        Log.i("Search : ","execute");


    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }
}
