package com.lhweb.load.model;


import java.util.*;

public class Main {

    public static void main(String[] args) {


        /*initialize default settings*/
        Stock stock = new Stock();

        /*overwrite default settings,
         useful settings you want another DB or PORT or IP*/
        //stock._ip="10.29.208.70";
        //stock._port=27017;
        //stock._dbName="MyDB";


        // connection attempt
        stock.connect();


        //List  (Faster)
        System.out.println();
        List b = stock.getSpareOptions();
        System.out.println(b.size());
        //System.out.println(b.get(2));


        // Array
        //System.out.println();
        Object a[] = stock.getSpareOptionsArray().toArray();
        //System.out.println(a.length);
        //System.out.println(a[2]);



        /**
         *
         *
         * Listar todas las Refacciones
         *
         *
         * */
        System.out.println();
        System.out.println("Method \"all\".. ");
        b = stock.all();
        for(int i = 0; i < b.size();i++)
        {
            //System.out.println(b.get(i));
        }




        /**
         *
         *
         * Registrar Nueva Refaccion
         *
         *
         * */

        HashMap<String, String> newRecord = new HashMap<String, String>();

        /*nombre de la refaccion*/
        newRecord.put("sparePart", "Led");

        /*descripcion breve de la refaccion -- este campo tambien es utilizado en las busquedas*/
        newRecord.put("briefDescription", "Led super luminoso");


        /*Marca de  la refaccion*/
        newRecord.put("brand", "Infra");

        /*Numero  que maneja el fabricante o marca*/
        newRecord.put("brandNumber", "Infra-2525");

        /*Codigo de la refaccion */
        newRecord.put("partNumber", "INFR-3232-327");


        /*definiendo sistemas compatibles con esta refaccion*/
        List<String> sistemas = new ArrayList<String>();
        sistemas.add("Electrico");
        sistemas.add("Iluminacion");
        sistemas.add("Super Faros");
        newRecord.put("system",sistemas.toString());


        /*Codigo de barras*/
        newRecord.put("barCode","849406967");


        /*definiendo los automobiles ,  modelos y ediciones compatibles con esta refaccion*/
        HashMap<String, String> models = new HashMap<String, String>();

        HashMap<String, String> Golf = new HashMap<String, String>();
        HashMap<String, String> Aveo = new HashMap<String, String>();
        HashMap<String, String> Ferrari = new HashMap<String, String>();

        List<String> golfStandar = new ArrayList<String>();
        List<String> golfLimited = new ArrayList<String>();
        List<String> aveoStandar = new ArrayList<String>();
        List<String> ferrariV6 = new ArrayList<String>();
        List<String> ferrariV8 = new ArrayList<String>();
        List<String> ferrariV12 = new ArrayList<String>();

        golfStandar.add("2008");golfStandar.add("2009");golfStandar.add("2010");
        golfLimited.add("2013");
        aveoStandar.add("2005");
        ferrariV6.add("2011");ferrariV6.add("2012");ferrariV6.add("2013");ferrariV6.add("2014");
        ferrariV8.add("2015");
        ferrariV12.add("2014");ferrariV12.add("2015");


        Golf.put("standar",golfStandar.toString());
        Golf.put("limited",golfLimited.toString());
        Aveo.put("standar",aveoStandar.toString());
        Ferrari.put("v6",ferrariV6.toString());
        Ferrari.put("v8",ferrariV8.toString());
        Ferrari.put("v12",ferrariV12.toString());


        models.put("Golf",Golf.toString());
        models.put("Aveo",Aveo.toString());
        models.put("Ferrari",Ferrari.toString());

        newRecord.put("model",models.toString());
        //System.out.println("models : " + models.toString());



        /*definiendo proveedores  de esta refaccion, el order es la prioridad*/
        List<String> proveedores = new ArrayList<String>();
        proveedores.add("Sagaji");
        proveedores.add("Egarama");
        proveedores.add("Proveedor-3");
        proveedores.add("Proveedor-4");
        newRecord.put("providers",proveedores.toString());


        /*stock minimo de la refaccion*/
        int stockMin = 40;
        newRecord.put("stockMin",Integer.toString(stockMin));


        /*definiendo precio de venta desde que se registra*/
        double salePrice = 16.00;
        newRecord.put("salePrice",Double.toString(salePrice));

        /*definiendo precio de venta especial (descuento) */
        double specialOfferPrice = 13.75;
        newRecord.put("specialOfferPrice",Double.toString(specialOfferPrice));


        /*Creando  nuevo  registro de una refaccion*/
        //stock.nuevoRegistro(newRecord);





        /**
         *
         * Stock Incoming using stock class
         *
         *        Nueva Entrada a Almacen
         *  (Refaccion previamente registrada)
         *
         *
         * */

        HashMap<String, Object> entrada = new HashMap<String, Object>();

        /*Fecha de acceso*/
        Calendar eventDate = Calendar.getInstance();
        TimeZone mexicoTime = TimeZone.getTimeZone("America/Mexico_City");
        eventDate.setTimeZone(mexicoTime);
        //System.out.println(eventDate.getTime());
        entrada.put("eventDate", eventDate.getTime());

        /*part Code Number*/
        entrada.put("partNumber", "ngk-2021-1");

        /*Quien modifico*/
        entrada.put("who", "tony stark");

        /* comentario */
        entrada.put("comment", "pedido urgent bla bla");

        /*codigo de barra*/
        entrada.put("barCode", "4495394803947");

        /* Cantidad de refacciones que accesan*/
        entrada.put("howMany", 10);

        /* Precio Unitario*/
        entrada.put("purchasePrice", 7.00);

        /*Proveedor*/
        entrada.put("provider", "sagaji");



        //stock.incoming(entrada);







        /**
         *
         *
         *
         *  Actualizar Registro
         *
         * Mantener el formato base del JSON para evitar incosistencias
         * */

        HashMap<String,Object> modifierDoc = new HashMap<String, Object>();
        String barCode = "";
        //stock.update(modifierDoc,barCode);





        /**
         *
         *
         *
         *  FullText Search
         *
         *  fields - sparePartName , briefDescription, partNumber,
         *  barCode, brand, brandNumber, systems, providers.
         *
         *  -No Case Sensitive, it doesn't matter upper or lower case.
         *  -it doesn't matter spaces between words.
         *  -Text index (language spanish)
         *
         * */

        stock.sparesFullTextSearch("  rolcar   SAGAJI      Laser");






        //closing connection
        stock.close();





        //*********************************************************************
        //*********************************************************************

        /**
         *
         *
         *
         *  SparePart Class
         *
         *
         * */



        SparePart sparePart = new SparePart();

        /*Setting Name,description and Brand*/
        sparePart.setSparePartName("Llanta");
        sparePart.setBriefDescription("Llanta a todo terreno");
        sparePart.setBrand("Michelline");
        sparePart.setBrandNumber("Miche-4379");
        sparePart.setBarCode("xxxyyy");
        //System.out.println(sparePart.getSparePartName());
        //System.out.println(sparePart.getBriefDescription());
        //System.out.println(sparePart.getBrand());


        /*Setting Systems*/
        sparePart.addSystem("Afinacion");
        sparePart.addSystem("Balanceo");
        sparePart.addSystem("Defensa");
        //System.out.println(sparePart.getSystem());
        sparePart.removeSystem("Defensa");
        //System.out.println(sparePart.getSystem());


        /*
         * Setting Compatibility,is used to know what  cars,editions and models
         * are compatible with this sparePart.
         *
         * */

        /* Adding compatible cars for this sparePart*/
        sparePart.addCarToCompatibility("Gol");
        sparePart.addCarToCompatibility("Jetta");
        sparePart.addCarToCompatibility("Jetta");
        sparePart.addCarToCompatibility("Polo");
        //System.out.println("compatibilityy => " + sparePart.getCompatibility());


        /*Removing a car from compatibility*/
        sparePart.removeCarFromCompatibility("Jetta");
        //System.out.println(sparePart.getCompatibility());




        /*Adding compatible Editions to every compatible car */
        sparePart.addEditionToCar("CL","Gol");
        sparePart.addEditionToCar("Sport","Gol");
        sparePart.addEditionToCar("V8","Jetta");
        sparePart.addEditionToCar("CL","Polo");
        sparePart.addEditionToCar("Sport","Polo");
        sparePart.addEditionToCar("Basic","Polo");
        //System.out.println(sparePart.getCompatibility());

        /*Remove editions from a car*/
        sparePart.removeEditionFromCar("CL","Gol");
        sparePart.removeEditionFromCar("V8","Jetta");
        //System.out.println(sparePart.getCompatibility());


        /*Adding compatible Models to editions and to cars*/
        sparePart.addModelToEdition(2008,"CL","Gol");
        sparePart.addModelToEdition(2009,"CL","Gol");
        sparePart.addModelToEdition(2010,"CL","Gol");
        sparePart.addModelToEdition(2011,"CL","Gol");
        sparePart.addModelToEdition(2014,"Basic","Polo");
        //System.out.println(sparePart.getCompatibility());



        /*You can add car,edition and model at the same time as follow */
        sparePart.addModelToEdition(2015,"V12","Camaro");
        sparePart.removeModelFromEdition(2015,"V12","Camaro");
        sparePart.removeModelFromEdition(2010,"CL","Gol");
        //System.out.println(sparePart.getCompatibility());


        /*Add Provider*/
        sparePart.addProvider("Sagaji");
        sparePart.addProvider("FYI");
        //System.out.println(sparePart.getProviders());

        /*Remove Provider*/
        sparePart.removeProvider("Sagaji");
        //System.out.println(sparePart.getProviders());


        /**
         * store/save SparePart JSON document on Database
         *
         */

        sparePart.storeSparePart();
        //System.out.println(sparePart.getCompatibility());


        /**
         * get sparePart from database
         */

        /*get sparePart by barCode (barCode is the primary Key)*/
        sparePart.loadSparePart("xxxyyy");
        //System.out.println(sparePart.getCompatibility());

        /*if that key does Not exist , it is loaded a empty document,that 's mean all variables are cleaned */
        sparePart.loadSparePart("otherID");
        //System.out.println(sparePart.getCompatibility());


        /**
         * Get ALL SparePArts
         */
        List<Object> X = sparePart.getAll();
        //System.out.println(X.get(0));




        /**
         * Get Existence Report
         */
        List<HashMap<String,Object>> Y = sparePart.getExistenceReport();
        for(int i = 0; i < Y.size(); i++){
            //System.out.println("Existence Report => " + Y.get(i));
            //System.out.println("Existence Report => " + Y.get(i).get("existence"));
            //System.out.println("Existence Report => " + Y.get(i).get("brand"));
            //System.out.println("Existence Report => " + Y.get(i));

        }






        /**
         * update sparePart and save it to DB
         */

        //System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");

        sparePart.loadSparePart("01582524564564");
        //System.out.println("before :" + sparePart.getBrand());
        sparePart.setBrand("WOW");
        //System.out.println("on memory :" + sparePart.getBrand());
        sparePart.storeSparePart();
        sparePart.loadSparePart("01582524564564");
        //System.out.println("after :" + sparePart.getBrand());


        /**
         * Sales
         */

        Sales sales = new Sales();
        sales.setWho("Sajid");  // user who commit the sale
        sales.addItem("1234567"); //add one by one to order
        sales.addItem("1234567");
        sales.addItem("1234567");
        sales.addItems("998877",2); // add many articles
        sales.setTotal(34.54);
        sales.completeSale();


        /**
         * Stock incoming with class
         */

        StockLog stockLog = new StockLog();
        stockLog.setWho("Encargado Almacen");
        stockLog.setComment("Producto de Emergencia");
        stockLog.setBarCode("998877");
        stockLog.setPartNumber("7532");
        stockLog.setHowMany(7);
        stockLog.setPurchasePrice(15.34);
        stockLog.setProvider("Sagaji");

        stockLog.incoming();






        /**
         * get all Sales Records
         *
         */

        Stock salesList = new Stock();

        salesList._ip="127.0.0.1";
        salesList._port=27017;
        salesList._dbName="LHA";
        salesList.connect();
        for(int i = 0 ; i < salesList.getAllSales().size(); i++ ){
            //System.out.println(salesList.getAllSales().get(i));
        }

        salesList.close();



        /**
         * Users
         *
         */

        System.out.println("\n\n\n************  users  **************");
        Users user = new Users();

        /*  create a user  */
        //user.setUsername("user2");
        //user.setPassword("passs");
        //user.createUser();

        /*  Authenticate  a user   */
        int x = user.authenticate("sajid","qwert");
        System.out.println(x);

















    }


}

/**
 * Help
 */

// jackson JSON : http://wiki.fasterxml.com/JacksonDownload
// http://api.mongodb.org/java/2.9.2/index.html?com/mongodb/DBCursor.html
// http://jsonformatter.curiousconcept.com/
// http://stackoverflow.com/questions/9562150/java-parsing-an-arraylist-and-setting-them-into-a-object
// http://www.programcreek.com/2013/03/hashset-vs-treeset-vs-linkedhashset/