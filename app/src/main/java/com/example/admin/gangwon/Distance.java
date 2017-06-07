package com.example.admin.gangwon;

/**
 * Created by admin on 2016-06-28.
 */
public class Distance {

    private static final String TAG = Distance.class.getSimpleName();

    public Distance(){

    }


    CityCode[] cityCode;
    class CityCode{
        String mCityCode;
        double mlat;
        double mlon;
    }

    public float spacing(CityCode citycode,Double lat, Double lng ){


        float x = (float)(citycode.mlat-lat);
        float y = (float)(citycode.mlon - lng);

        float index = (float)Math.sqrt(x*x + y*y);


        return index;

    }

    public String shortspacing(Double lat, Double lng){


        float spacing[] = new float[cityCode.length];
        for(int i =0;i<cityCode.length;i++){
            spacing[i] = spacing(cityCode[i], lat, lng);

        }

        float shortSpacing = spacing[0];
        int shortIndex = -1;
        for(int i =0;i<spacing.length;i++){
            if(shortIndex>=spacing[i]){
                shortSpacing = spacing[i];
                shortIndex = i;
            }
        }

        String nearCutyCode = cityCode[shortIndex].mCityCode;

        return nearCutyCode;

    }






    public void getCitycode (){
        cityCode = new CityCode[16];
        cityCode[0] = new CityCode();
        cityCode[0].mCityCode = "춘천";
        cityCode[0].mlat = 37.881288;
        cityCode[0].mlon = 127.730082;

        cityCode[1] = new CityCode();
        cityCode[1].mCityCode = "백령도";
        cityCode[1].mlat = 37.950966;
        cityCode[1].mlon = 124.656029;

        cityCode[2] = new CityCode();
        cityCode[2].mCityCode = "강릉";
        cityCode[2].mlat = 37.751853;
        cityCode[2].mlon = 128.876058;


        cityCode[3] = new CityCode();
        cityCode[3].mCityCode = "서울";
        cityCode[3].mlat = 37.564341;
        cityCode[3].mlon = 126.975609;

        cityCode[4] = new CityCode();
        cityCode[4].mCityCode = "인천";
        cityCode[4].mlat = 37.455682;
        cityCode[4].mlon = 126.704472;

        cityCode[5] = new CityCode();
        cityCode[5].mCityCode = "울릉도";
        cityCode[5].mlat = 37.506368;
        cityCode[5].mlon = 130.857154;

        cityCode[6] = new CityCode();
        cityCode[6].mCityCode = "수원";
        cityCode[6].mlat = 37.263406;
        cityCode[6].mlon = 127.488752;

        cityCode[7] = new CityCode();
        cityCode[7].mCityCode = "청주";
        cityCode[7].mlat = 36.641879;
        cityCode[7].mlon = 127.488752;

        cityCode[8] = new CityCode();
        cityCode[8].mCityCode = "대전";
       cityCode[8].mlat = 36.350412;
        cityCode[8].mlon = 127.384547;

        cityCode[9] = new CityCode();
        cityCode[9].mCityCode = "대구";
        cityCode[9].mlat = 35.871436;
        cityCode[9].mlon = 128.601445;

        cityCode[10] = new CityCode();
        cityCode[10].mCityCode = "전주";
        cityCode[10].mlat = 35.824193;
        cityCode[10].mlon = 127.148000;

        cityCode[11] = new CityCode();
        cityCode[11].mCityCode = "울산";
        cityCode[11].mlat = 35.538739;
        cityCode[11].mlon = 129.581433;

        cityCode[12] = new CityCode();
        cityCode[12].mCityCode = "마산";
        cityCode[12].mlat = 35.213516;
        cityCode[12].mlon = 128.581433;

        cityCode[13] = new CityCode();
        cityCode[13].mCityCode = "광주";
        cityCode[13].mlat = 35.160073;
        cityCode[13].mlon = 126.851434;

        cityCode[14] = new CityCode();
        cityCode[14].mCityCode = "부산";
        cityCode[14].mlat = 35.179955;
        cityCode[14].mlon = 129.075642;

        cityCode[15] = new CityCode();
        cityCode[15].mCityCode = "제주";
        cityCode[15].mlat = 33.499597;
        cityCode[15].mlon = 126.531254;

    }


}
