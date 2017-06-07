package com.example.admin.gangwon.Controller;

import android.util.Log;

import net.daum.mf.map.api.MapPOIItem;

import static java.lang.Double.valueOf;

/**
 * Created by Administrator on 2016-07-06.
 */
public class SearchNearPoint {


    private static final String TAG = SearchNearPoint.class.getSimpleName();

    String nearMarker = null;
    private int shortindex = 0;


    private double templat;
    private  double templng;
    private double x;
    private double y;
    private double index;
    private MapPOIItem[] poiItems;
    private String currentlat;
    private  String cuurentlng;


    public SearchNearPoint(MapPOIItem[] poiItem,String lat, String lng) {
        this.currentlat = lat;
        this.cuurentlng = lng;
        this.poiItems = poiItem;

    }



    public String getNearMarker(){
        Log.d("Search Near Point : ", "search");

        // poiItems = mapView.getPOIItems();
        double resultxy[]= new double[poiItems.length];


        if(poiItems !=null){


            for(int i =0;i <poiItems.length;i++){


                templat = valueOf(poiItems[i].getMapPoint().getMapPointGeoCoord().latitude) ;
                templng = valueOf(poiItems[i].getMapPoint().getMapPointGeoCoord().longitude) ;
                Log.d("templat :",Double.toString(templat));
                Log.d("templng :",Double.toString(templng));


                x = (double)(templat - valueOf(currentlat).doubleValue());
                y = (double)(templng - valueOf(cuurentlng).doubleValue());
                Log.d("x :",Double.toString(x));
                Log.d("y :",Double.toString(y));

                index = (double)Math.sqrt(x*x + y*y);
                resultxy[i] = index;


            }

            double min = resultxy[0];


            for(int i =1; i<resultxy.length; i++)
            {
                if (min >resultxy[i]){
                    min = resultxy[i];
                    shortindex = i;
                }
            }
            nearMarker  = poiItems[shortindex].getItemName();
            Log.d("nearMarker :",nearMarker);


        }
        else{
            Log.d("Null","Null");
        }

        return nearMarker;
    }


}
