package com.example.user_.bookinguser;

        import java.io.Serializable;

class  ListBooking implements Serializable {
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
    String id;
    String name;
    String model;
    String price;
    String numperson;
    String numdoor;
    String numbag;
    String conditon;
    String type;
    String image;
    String userid;





    ListBooking(String fdate, String ldate, String ftime, String ltime,
                String totalprice, String location,String covers,String mychosen,String idCar,String id, String name, String model, String price,
                String numperson, String numbag, String numdoor, String condtion, String type,
                String image,String nameOerson,String IDNA,String PhonePerson){
        this.Fdate =fdate;
        this.Ldate =ldate;
        this.Ftime=ftime;
        this.Ltime=ltime;
        this.Totalprice =totalprice;
        this.IDCar=idCar;
        this.Locations=location;
        this.id=id;
        this.name =name;
        this.model =model;
        this.price =price;
        this.Covers=covers;
        this.Mychosen=mychosen;
        this.numperson =numperson;
        this.numdoor =numdoor;
        this.numbag=numbag;
        this.conditon =condtion;
        this.type =type;
        this.image=image;
        this.Name =nameOerson;
        this.ID=IDNA;
        this.Phone=PhonePerson;


    }


    ListBooking(String fdate, String ldate, String ftime, String ltime, String covers,
                String totalprice, String mychosen,String location ,String name, String phone, String id, String idcar) {
        this.Fdate =fdate;
        this.Ldate =ldate;
        this.Ftime=ftime;
        this.Ltime=ltime;
        this.Covers =covers;
        this.Mychosen =mychosen;
        this.Totalprice =totalprice;
        this.Name=name;
        this.Phone=phone;
        this.ID=id;
        this.IDCar=idcar;
        this.Locations=location;
    }
}
