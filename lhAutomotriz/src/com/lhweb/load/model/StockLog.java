package com.lhweb.load.model;


import java.util.Date;
import java.util.HashMap;

/**
 * Created by s47id on 5/3/14.
 */

public class StockLog {

    private Date eventDate;
    private String barCode;
    private String partNumber;
    private String who;
    private String comment;
    private int howMany;
    private Double purchasePrice;
    private String provider;

    public StockLog(){

        eventDate = new Date();
        barCode = "";
        partNumber = "";
        who = "";
        comment = "";
        howMany = 0;
        purchasePrice = 0.0;
        provider = "";

    }


    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }



    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }


    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getHowMany() {
        return howMany;
    }

    public void setHowMany(int howMany) {
        this.howMany = howMany;
    }


    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getProvider() {
        return provider;
    }


    public void setProvider(String provider) {
        this.provider = provider;
    }



    public int incoming(){

        Stock stock = new Stock();
        stock.connect();

        /*Your Calculations*/
        HashMap<String,Object> incomingObject = new HashMap<String, Object>();

        incomingObject.put("eventDate",this.getEventDate());
        incomingObject.put("barCode",this.getBarCode());
        incomingObject.put("partNumber",this.getPartNumber());
        incomingObject.put("who",this.getWho());
        incomingObject.put("comment",this.getComment());
        incomingObject.put("howMany",this.getHowMany());
        incomingObject.put("purchasePrice",this.getPurchasePrice());
        incomingObject.put("provider",this.getProvider());

        System.out.println("stockLog object=> " + incomingObject);

        stock.storeJSONObject(incomingObject,"stockLog");

        //update Balance & Existence
        int existence = 0;
        double balance = 0.0;
        int response = 0;
        try{
        	existence = stock.getExistence(incomingObject.get("barCode").toString());
            balance = stock.getBalance(incomingObject.get("barCode").toString());


            stock.setExistence(existence + (int)Double.parseDouble(incomingObject.get("howMany").toString())
                    ,incomingObject.get("barCode").toString());

            stock.setBalance(balance + Double.parseDouble(incomingObject.get("howMany").toString())*Double.parseDouble(incomingObject.get("purchasePrice").toString())
                    ,incomingObject.get("barCode").toString());




            stock.close();
            response = -1;
        }catch(Exception e){

        }
        
        return response;
    }
    public  void  outgoing(){

        Stock stock = new Stock();
        stock.connect();

        /*Your Calculations*/

        stock.close();


    }



}