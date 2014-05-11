package com.lhweb.load.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by s47id on 4/26/14.
 */


public class SparePart {

    private String sparePartName;
    private String briefDescription;
    private String brand;
    private String brandNumber;
    private String partNumber;
    private HashMap<String,List<String>> systems = new HashMap<String, List<String>>();
    private String barCode;
    private HashMap<String, HashMap<String,HashMap<String, List<Integer>>>> compatibility
            = new HashMap<String, HashMap<String, HashMap<String, List<Integer>>>>();
    private HashMap<String,List<String>> providers = new HashMap<String, List<String>>();
    private int stockMin;
    private int existence;
    private Double salePrice;
    private Double specialOfferPrice;
    private Double balance;


    public SparePart(){

        this.sparePartName = "";
        this.briefDescription = "";
        this.brand = "";
        this.brandNumber = "";
        this.partNumber  = "";
        this.barCode = "";
        this.stockMin = 0;
        this.existence = 0 ;
        this.salePrice = 0.0;
        this.specialOfferPrice = 0.0;
        this.balance = 0.0;

        /*Initialization of Systems*/
        List<String> systemsList = new ArrayList<String>();
        this.systems.clear();
        this.systems.put("systems",systemsList);

        /*Initialization of compatibility cars, editions and models */
        this.compatibility.clear();
        HashMap<String,HashMap<String, List<Integer>>> cars = new HashMap<String, HashMap<String, List<Integer>>>();
        compatibility.put("compatibility",cars);

        /*Initialization of Providers*/
        List<String> providersList = new ArrayList<String>();
        this.providers.clear();
        this.providers.put("providers",providersList);

    }


    public String getSparePartName( ){
        return this.sparePartName;
    }

    public void setSparePartName(String sparePartName ){
        this.sparePartName = sparePartName;
    }

    public String getBriefDescription( ){
        return this.briefDescription;
    }

    public void setBriefDescription(String briefDescription ){
        this.briefDescription = briefDescription;
    }

    public String getBrand( ){
        return this.brand;
    }


    public void setBrand( String brand){
        this.brand=brand;
    }

    public String getBrandNumber() {
        return this.brandNumber;
    }

    public void setBrandNumber(String brandNumber) {
        this.brandNumber = brandNumber;
    }

    public String getPartNumber(){
        return this.partNumber;
    }

    public void setPartNumber(String partNumber){
        this.partNumber = partNumber;
    }

    public HashMap<String, List<String>> getSystem() {
        return this.systems;
    }

    public void setSystems(HashMap<String, List<String>> system) {
        this.systems = system;
    }

    public void addSystem(String system){

        //List<String> systemsList = new ArrayList<String>();
        //systemsList = this.systems.get("systems");
        //systemsList.add(newSystem);
        //this.systems.clear();
        //this.systems.put("systems",systemsList);
        if(!this.systems.get("systems").contains(system)){
            this.systems.get("systems").add(system);
        }


    }

    public void removeSystem(String system){
        List<String> systemsList = new ArrayList<String>();
        systemsList = this.systems.get("systems");


        if (systemsList.contains(system)) {
            systemsList.remove(system);
        } else {
            System.out.println("System Not Found");
        }

        this.systems.clear();
        this.systems.put("systems",systemsList);

    }

    public String getBarCode() {
        return this.barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


    public HashMap<String, HashMap<String,HashMap<String, List<Integer>>>> getCompatibility() {
        return this.compatibility;
    }


    public void setCompatibility(HashMap<String, HashMap<String,HashMap<String, List<Integer>>>> compatibility) {
        this.compatibility = compatibility;
    }

    public void addCarToCompatibility(String carName){

        HashMap<String,HashMap<String, List<Integer>>> cars;
        HashMap<String, List<Integer>> newCar= new HashMap<String, List<Integer>>();

        cars = this.compatibility.get("compatibility");


        cars.put(carName, newCar);
        this.compatibility.put("compatibility",cars);


    }

    public void removeCarFromCompatibility(String carName){

        HashMap<String,HashMap<String, List<Integer>>> cars
                = new HashMap<String, HashMap<String, List<Integer>>>();
        cars = this.compatibility.get("compatibility");
        if(cars.containsKey(carName)){
            cars.remove(carName);
        }
        else{
            System.out.println(carName +" Not Found !¡");
        }

        this.compatibility.put("compatibility",cars);


    }


    public void addEditionToCar(String editionName, String carName){

        HashMap<String,HashMap<String, List<Integer>>> cars;
        cars = this.compatibility.get("compatibility");
        List<Integer> models = new ArrayList<Integer>();


        if(cars.containsKey(carName)){

            /*car found - so  add an edition*/
            this.compatibility.get("compatibility").get(carName).put(editionName,models);

        }
        else {
            addCarToCompatibility(carName);
            addEditionToCar(editionName,carName);
        }

    }


    public void removeEditionFromCar(String editionName, String carName){
        HashMap<String,HashMap<String, List<Integer>>> cars;
        cars = this.compatibility.get("compatibility");
        if(cars.containsKey(carName)){

            /*car found - so  remove  edition*/
            HashMap<String,List<Integer>> editions = new HashMap<String, List<Integer>>();
            editions = this.compatibility.get("compatibility").get(carName);

            if(editions.containsKey(editionName)){
                /*edition found - so  remove  it*/
                this.compatibility.get("compatibility").get(carName).remove(editionName);


            }
            else {
                System.out.println("Edition Not Found");
            }



        }
        else {
            System.out.println("Edition not removed because of Car Not Found");
        }


    }


    public void addModelToEdition(Integer model, String editionName, String carName){
        HashMap<String,HashMap<String, List<Integer>>> cars;
        cars = this.compatibility.get("compatibility");

        if(cars.containsKey(carName)){

            if(cars.get(carName).containsKey(editionName)){

                if(!cars.get(carName).get(editionName).contains(model)) {
                    this.compatibility.get("compatibility").get(carName).get(editionName).add(model);
                }

            }
            else{
                addEditionToCar(editionName,carName);
                addModelToEdition(model,editionName,carName);
            }

        }
        else{
            addCarToCompatibility(carName);
            addModelToEdition(model,editionName,carName);
        }

    }


    public void removeModelFromEdition(Integer model, String editionName, String carName){
        HashMap<String,HashMap<String, List<Integer>>> cars;
        cars = this.compatibility.get("compatibility");

        if(cars.containsKey(carName)){

            if(cars.get(carName).containsKey(editionName)){
                this.compatibility.get("compatibility").get(carName).get(editionName).remove(model);
            }
            else{
                System.out.println("Model not removed because Edition Not Found");

            }

        }
        else{
            System.out.println("Model not removed because of Car Not Found");
        }

    }


    public HashMap<String, List<String>> getProviders() {
        return this.providers;
    }

    public void setProviders(HashMap<String, List<String>> providers) {
        this.providers = providers;
    }

    public void addProvider(String provider){


        //To avoid duplicates and keep integrity
        if(!this.providers.get("providers").contains(provider)){

            this.providers.get("providers").add(provider);
        }



    }

    public void removeProvider(String provider){
        if(this.providers.get("providers").contains(provider)){

            this.providers.get("providers").remove(provider);

        }
        else {
            System.out.println("Provider Not Found");
        }
    }


    public int getStockMin() {
        return this.stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }


    public int getExistence() {
        int result = 7;
        result = existenceSparePart(this.barCode);

        return result;
    }

    public void setExistence(int existence) {

        //this.existence = existence;
        //this kind of variables must be written immediately to db to prevent inconsistency
        Stock stock = new Stock();
        stock.connect();

        if(stock.setExistence(existence,this.barCode) == 0){
            this.existence = existence;
        }

        stock.close();


    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getSpecialOfferPrice() {
        return this.specialOfferPrice;
    }

    public void setSpecialOfferPrice(Double specialOfferPrice) {
        this.specialOfferPrice = specialOfferPrice;
    }


    public Double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    public int storeSparePart( ){

        Stock stock = new Stock();
        stock.connect();

        HashMap<String,Object> sparePart = new HashMap<String, Object>();

        /* -- Building sparePart JSON --*/
        sparePart.put("sparePart",this.sparePartName);
        sparePart.put("briefDescription",this.briefDescription);
        sparePart.put("brand",this.brand);
        sparePart.put("brandNumber",this.brandNumber);
        sparePart.put("partNumber",this.partNumber);
        sparePart.put("systems",this.systems.get("systems"));
        sparePart.put("barCode",this.barCode);
        sparePart.put("compatibility",this.compatibility.get("compatibility"));
        sparePart.put("providers",this.providers.get("providers"));
        sparePart.put("stockMin",this.stockMin);
        sparePart.put("existence",this.existence);
        sparePart.put("salePrice",this.salePrice);
        sparePart.put("specialOfferPrice",this.specialOfferPrice);
        sparePart.put("balance",this.balance);


        /*we require a new instance , in order to call existenceSparePart method in a cleaned instance */
        SparePart aux = new SparePart();

        // less than zero means the document does Not exist
        int response = aux.existenceSparePart(this.barCode); 
        if( response < 0 )
        {
            /* create a new record if barCode is not null or empty */
            if(sparePart.get("barCode") != null & ((String)sparePart.get("barCode")).length() > 0 )
            {
                stock.storeJSONObject(sparePart,"spares");
            }

        }
        else{
            /* update existent record */
            stock.updateJSONObject("barCode",this.barCode,sparePart,"spares");
        }

        System.out.println("storeSpare => " + sparePart);

        stock.close();
        return response;
    }


    public void loadSparePart(String barCode){


        Stock stock = new Stock();
        stock.connect();

        HashMap<String,Object> sparePart;
        sparePart = stock.loadObject("barCode",barCode,"spares");





        if( !sparePart.isEmpty() & ((String) sparePart.get("barCode")) != null ) {


            this.sparePartName = (String) sparePart.get("sparePart");
            this.briefDescription = (String) sparePart.get("briefDescription");
            this.brand = (String) sparePart.get("brand");
            this.brandNumber = (String) sparePart.get("brandNumber");
            this.partNumber = (String) sparePart.get("partNumber");
            this.systems.put("systems", (List<String>) sparePart.get("systems"));
            this.barCode = (String) sparePart.get("barCode");
            this.compatibility.put("compatibility", (HashMap<String, HashMap<String, List<Integer>>>) sparePart.get("compatibility"));
            this.providers.put("providers", (List<String>) sparePart.get("providers"));

            Double aux;
            aux = Double.parseDouble(sparePart.get("stockMin").toString());
            this.stockMin = aux.intValue();

            aux = Double.parseDouble(sparePart.get("existence").toString());
            this.existence = aux.intValue();

            this.salePrice = (Double) sparePart.get("salePrice");
            this.specialOfferPrice = (Double) sparePart.get("specialOfferPrice");
            this.balance =  (Double) sparePart.get("balance");

        }
        else
        {
            sparePart.clear();
            List<String> emptyList = new ArrayList<String>();
            HashMap<String,HashMap<String, List<Integer>>> emptyHash = new HashMap<String, HashMap<String, List<Integer>>>();


            /*the next lines are used for cleaning SparePart instance,  so ,
             that's mean if you want to load and a new document from db,
              it is supposed that you  don't want to work with previous values
              */

            this.sparePartName = "";
            this.briefDescription = "";
            this.brand = "";
            this.brandNumber = "";
            this.partNumber = "";
            this.systems.put("systems", emptyList);
            this.barCode = "";
            this.compatibility.put("compatibility", emptyHash);
            this.providers.put("providers", emptyList);
            this.stockMin = 0;
            this.existence = 0;
            this.salePrice = 0.0;
            this.specialOfferPrice = 0.0;
            this.balance = 0.0;

        }






        stock.close();


    }





    public void loadSparePartByBrandNumber(String brandNumber){


        Stock stock = new Stock();
        stock.connect();

        HashMap<String,Object> sparePart;
        sparePart = stock.loadObject("brandNumber",brandNumber,"spares");





        if( !sparePart.isEmpty() & ((String) sparePart.get("brandNumber")) != null ) {


            this.sparePartName = (String) sparePart.get("sparePart");
            this.briefDescription = (String) sparePart.get("briefDescription");
            this.brand = (String) sparePart.get("brand");
            this.brandNumber = (String) sparePart.get("brandNumber");
            this.partNumber = (String) sparePart.get("partNumber");
            this.systems.put("systems", (List<String>) sparePart.get("systems"));
            this.barCode = (String) sparePart.get("barCode");
            this.compatibility.put("compatibility", (HashMap<String, HashMap<String, List<Integer>>>) sparePart.get("compatibility"));
            this.providers.put("providers", (List<String>) sparePart.get("providers"));

            Double aux;
            aux = Double.parseDouble(sparePart.get("stockMin").toString());
            this.stockMin = aux.intValue();

            aux = Double.parseDouble(sparePart.get("existence").toString());
            this.existence = aux.intValue();

            this.salePrice = (Double) sparePart.get("salePrice");
            this.specialOfferPrice = (Double) sparePart.get("specialOfferPrice");
            this.balance =  (Double) sparePart.get("balance");

        }
        else
        {
            sparePart.clear();
            List<String> emptyList = new ArrayList<String>();
            HashMap<String,HashMap<String, List<Integer>>> emptyHash = new HashMap<String, HashMap<String, List<Integer>>>();


            /*the next lines are used for cleaning SparePart instance,  so ,
             that's mean if you want to load and a new document from db,
              it is supposed that you  don't want to work with previous values
              */

            this.sparePartName = "";
            this.briefDescription = "";
            this.brand = "";
            this.brandNumber = "";
            this.partNumber = "";
            this.systems.put("systems", emptyList);
            this.barCode = "";
            this.compatibility.put("compatibility", emptyHash);
            this.providers.put("providers", emptyList);
            this.stockMin = 0;
            this.existence = 0;
            this.salePrice = 0.0;
            this.specialOfferPrice = 0.0;
            this.balance = 0.0;

        }






        stock.close();


    }




    public int existenceSparePart(String barCode){

        /* [-1] => Not Registered no document , [0-*] => existence on Store */
        int result = 7;

        Stock stock = new Stock();
        stock.connect();

        HashMap<String,Object> sparePart;
        sparePart = stock.loadObject("barCode",barCode,"spares");

        if(!sparePart.isEmpty()){

            if(sparePart.get("existence") != null ){

                Double aux;
                aux = Double.parseDouble(sparePart.get("existence").toString());
                result = aux.intValue();

            }
            else
            {
                System.out.println("Existence - Not Field Defined.");
                result = 0;

            }

        }
        else{
            System.out.println("Existence - Document Not Found with that barCode key.");
            result = -1;
        }

        stock.close();

        return result;
    }
    
    public List getAll(){

        Stock stock =  new Stock();
        stock.connect();
        List a = stock.all();
        return  a;

    }
    
    public List<HashMap<String,Object>> getExistenceReport(){

        Stock stock =  new Stock();
        stock.connect();
        List<HashMap<String,Object>> a = stock.existenceReport();
        stock.close();
        return  a;

    }

    public void removeSparePart(){

        Stock stock = new Stock();
        stock.connect();
        stock.removeObject("brandNumber",this.brandNumber,"spares");
        //System.out.println("brandNumber" + this.brandNumber);
        stock.close();


    }


}
