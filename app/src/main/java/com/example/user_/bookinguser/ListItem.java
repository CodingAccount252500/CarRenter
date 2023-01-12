package com.example.user_.bookinguser;

class ListItem  {
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

    ListItem(String id, String name, String model, String price,
             String numperson, String numbag, String numdoor, String condtion, String type, String image
    ){
        this.id=id;
        this.name =name;
        this.model =model;
        this.price =price;
        this.numperson =numperson;
        this.numdoor =numdoor;
        this.numbag=numbag;
        this.conditon =condtion;
        this.type =type;
        this.image=image;

    }


    public ListItem() {

    }
}
