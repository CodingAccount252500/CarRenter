package com.example.user_.bookinguser;

import java.io.Serializable;

class  ListModel implements Serializable {
    String Fdate;
    String Ldate;
    String Ftime;
    String Ltime;
    String Mychosen;
    String Covers;
    String Locations;
    String Totalprice;
    String Name;
    String Phone;
    String ID;
    String IDCar;
    String userid;


    ListModel(String fdate, String ldate, String ftime, String ltime, String covers, String mychosen,
              String totalprice, String idCar,String userid){
        Fdate =fdate;
        Ldate =ldate;
        Ftime=ftime;
        Ltime=ltime;
        Covers =covers;
        Mychosen =mychosen;
        Totalprice =totalprice;
        IDCar=idCar;


    }


    ListModel(String fdate, String ldate, String ftime, String ltime, String covers,
                String totalprice, String mychosen,String location ,String name, String phone, String id, String idcar,
              String userid){
        Fdate =fdate;
        Ldate =ldate;
        Ftime=ftime;
        Ltime=ltime;
        Covers =covers;
        Mychosen =mychosen;
        Totalprice =totalprice;
        Name=name;
        Phone=phone;
        ID=id;
        IDCar=idcar;
        Locations=location;
        this.userid =userid;

    }
}