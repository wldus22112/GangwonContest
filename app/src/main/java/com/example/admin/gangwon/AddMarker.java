package com.example.admin.gangwon;

import android.app.Application;
import android.util.Log;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

/**
 * Created by admin on 2016-05-27.
 */
public class AddMarker extends Application {

    private static final String TAG = AddMarker.class.getSimpleName();


    public AddMarker() {
        Log.i(TAG, "AddMarker");
    }


    public MapPOIItem getPoint(double x, double y, String name, int num){


        MapPOIItem[] poiItems;

        MapPoint makerpoint = MapPoint.mapPointWithGeoCoord(x,y);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(name);
        marker.setUserObject(name);
        //marker.setTag(0);
        marker.setMapPoint(makerpoint);
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);


        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        if(num==1){
            marker.setRightSideButtonResourceIdOnCalloutBalloon(R.drawable.call2);
            marker.getRightSideButtonResourceIdOnCalloutBalloon();
        }
        else{
            marker.setShowDisclosureButtonOnCalloutBalloon(false);
        }






        //mapView.addPOIItem(marker);

        return marker;

    }




}
