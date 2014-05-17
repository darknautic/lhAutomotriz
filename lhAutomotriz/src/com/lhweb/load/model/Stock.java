package com.lhweb.load.model;


/**
 * Created by s47id on 11/04/2014.
 */



import com.mongodb.*;
import java.lang.Object;
import java.net.UnknownHostException;
import java.util.*;
import java.util.ArrayList;



public class Stock {

    public String _ip;
    public int _port;
    public String _dbName;
    private DB db;
    private MongoClient mongoClient;

    public Stock(){
        //_ip="192.168.1.67";
        _ip="10.29.210.41";
        _port=27017;
        _dbName="LHA";
    };


    public void connect(){
        try {
            //MongoClient mongoClient = new MongoClient(_ip,_port);
            mongoClient = new MongoClient(_ip,_port);
            db = mongoClient.getDB(_dbName);

        }catch (UnknownHostException e){
            System.err.println("Connection Failed: " + e);

        }
    }

    public void close(){
        mongoClient.close();
    }

    public Set<String> collectionList(){

        Set<String> theList = new HashSet<String>();
        theList = db.getCollectionNames();
        return theList;
    }


    public List getSpareOptions(){
        DBCollection spares = db.getCollection("spares");
        //List obj = collection.find( query ).skip( 1000 ).limit( 100 ).toArray();
        List a = spares.find().toArray();
        return a;

    }






    public ArrayList<String> getSpareOptionsArray(){
        DBCollection spares = db.getCollection("spares");
        DBCursor spareOptions = spares.find();
        ArrayList<String> a = new ArrayList<String>();

        try{
            while (spareOptions.hasNext()){
                a.add(spareOptions.next().toString());
            }
        }finally {
            spareOptions.close();
        }


        return a;


    }

    /**
     *
     * list of all spares
     */

    public List all(){
        DBCollection spares = db.getCollection("spares");
        //List obj = collection.find( query ).skip( 1000 ).limit( 100 ).toArray();
        List a = spares.find().toArray();
        return a;

    }






    public void nuevoRegistro(HashMap<String,String> newRecord){

        /*Init Collection */
        DBCollection spares = db.getCollection("spares");


        /*handling system array*/
        List<String> systemArray = new ArrayList<String>();
        systemArray = stringFormattedToStringList(newRecord.get("system").toLowerCase());



        /*handling models JSON document*/
        String modelString;
        modelString = newRecord.get("model").toLowerCase();
        HashMap<String,Object> modelHash;
        modelHash = stringModelToHashModel(modelString);


        /*handling providers array*/
        List<String> providersArray = new ArrayList<String>();
        providersArray = stringFormattedToStringList(newRecord.get("providers").toLowerCase());


        /*building document to be inserted*/
        BasicDBObject newDocument = new BasicDBObject("sparePart",newRecord.get("sparePart").toLowerCase()).
                append("briefDescription", newRecord.get("briefDescription").toLowerCase()).
                append("brand", newRecord.get("brand").toLowerCase()).
                append("brandNumber", newRecord.get("brandNumber").toLowerCase()).
                append("partNumber",newRecord.get("partNumber").toLowerCase()).
                append("system",systemArray).
                append("barCode",newRecord.get("barCode").toLowerCase()).
                append("model", modelHash).
                append("provider",providersArray).
                append("stockMin",Integer.parseInt(newRecord.get("stockMin"))).
                append("salePrice",Double.parseDouble(newRecord.get("salePrice"))).
                append("specialOfferPrice",Double.parseDouble(newRecord.get("specialOfferPrice")));



        spares.insert(newDocument);

    }




    public void incoming(HashMap<String,Object> entrada){
        DBCollection spares = db.getCollection("spares");
        DBCollection stockLog = db.getCollection("stockLog");
        int existence = 0;
        double balance = 0.0;

        existence = getExistence(entrada.get("barCode").toString().toLowerCase());
        balance = getBalance(entrada.get("barCode").toString().toLowerCase());

        //howManyBefore
        //System.out.println(getExistence(entrada.get("barCode").toString().toLowerCase()));

        BasicDBObject newLog = new BasicDBObject("eventDate",entrada.get("eventDate")).
                append("io","in").
                append("partNumber",entrada.get("partNumber").toString().toLowerCase()).
                append("who",entrada.get("who")).
                append("comment",entrada.get("comment")).
                append("barCode",entrada.get("barCode")).
                append("howMany",entrada.get("howMany")). //how many are entering
                append("howManyBefore",existence). //how many exist before this entrance
                append("purchasePrice",entrada.get("purchasePrice")). //unitPrice
                append("provider", entrada.get("provider").toString().toLowerCase());


        stockLog.insert(newLog);

        //Updating Existence of this sparePart
        setExistence(existence + (int)Double.parseDouble(entrada.get("howMany").toString()),
                entrada.get("barCode").toString().toLowerCase());

        //Updating Balance
        setBalance(balance + Double.parseDouble(entrada.get("howMany").toString()) * Double.parseDouble(entrada.get("purchasePrice").toString()),
                entrada.get("barCode").toString().toLowerCase());

    }



    public void storeJSONObject(HashMap<String,Object> sparePart,String collectionName){

        DBCollection spares = db.getCollection(collectionName);
        BasicDBObject sparePartDBObject = new BasicDBObject();

        for( String key : sparePart.keySet())
        {
            sparePartDBObject.append(key,sparePart.get(key));
        }


        spares.insert(sparePartDBObject);



    }


    public int getExistence(String barCode){
        int result = 0;
        DBCollection spares = db.getCollection("spares");
        BasicDBObject query = new BasicDBObject("barCode", barCode);
        DBCursor existence = spares.find(query);
        DBObject existenceDoc ;


        try {
            if(existence.hasNext()) {  //Not while function  used - because search by barCode must return only 1 Document
                existenceDoc = existence.next();

                result =(int) Double.parseDouble(existenceDoc.get("existence").toString());

            }
        } finally {
            existence.close();
        }




        return result;
    }


    public int setExistence(int existence,String barCode){

        /**
         * 0 - Successfully ,  1 -  Wrong , other number = totally Wrong
         */

        int result = 7;
        DBCollection spares = db.getCollection("spares");
        BasicDBObject query = new BasicDBObject("barCode", barCode);
        BasicDBObject newDoc = new BasicDBObject();
        newDoc.append("$set",new BasicDBObject().append("existence",existence));

        try {

            spares.update(query,newDoc);
            result = 0;

        }catch (Throwable e){
            System.out.println("Error - Setting Existence" + e);
            result = 1;
        }

        return result;
    }


    public double getBalance(String barCode){
        double result = 0;
        DBCollection spares = db.getCollection("spares");
        BasicDBObject query = new BasicDBObject("barCode", barCode);
        DBCursor cursor = spares.find(query);
        DBObject doc ;

        try {
            if(cursor.hasNext()) {  //Not while used - because search by barCode must return only 1 Document
                doc = cursor.next();
                result = Double.parseDouble(doc.get("balance").toString());

            }
        } finally {
            cursor.close();
        }



        return result;
    }


    public void setBalance(double newBalance,String barCode){
        DBCollection spares = db.getCollection("spares");

        BasicDBObject query = new BasicDBObject("barCode", barCode);
        BasicDBObject newDoc = new BasicDBObject();
        newDoc.append("$set",new BasicDBObject().append("balance",newBalance));

        spares.update(query,newDoc);

    }




    public void updateJSONObject(String keyName,String keyValue,HashMap<String,Object> modifierDoc,String collectionName){

        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject query = new BasicDBObject(keyName, keyValue);
        BasicDBObject modifier = new BasicDBObject();


        for(String key : modifierDoc.keySet())
        {
            modifier.append(key,modifierDoc.get(key));
        }


        collection.update(query,modifier);

    }



    public HashMap<String,Object> loadObject(String key,String keyValue, String collectionName){

        HashMap<String,Object> JSONObject = new HashMap<String, Object>();
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject query = new BasicDBObject(key, keyValue);
        DBCursor cursor = collection.find(query);

        JSONObject.clear();
        //System.out.println("from Stock " + "key :" + key + " " + "keyValue : "+ keyValue );

        try {
            if(cursor.hasNext()) {
                //System.out.println("result :::" + cursor.next());

                HashMap<String,Object> JSONObjectAux = new HashMap<String, Object>();
                JSONObjectAux.put("1",cursor.next());

                for(String attrKeys : ((HashMap<String,Object>)JSONObjectAux.get("1")).keySet()){
                    //System.out.println(attrKeys);
                    JSONObject.put(attrKeys,((HashMap<String,Object>)JSONObjectAux.get("1")).get(attrKeys));

                }
                //System.out.println("loaded Document =>" + JSONObject);



            }
            else
            {
                JSONObject.clear();
            }
        } finally {
            cursor.close();
        }


        return JSONObject;
    }


    public void sparesFullTextSearch(String textQuery){
        /**
         *  FullText Search
         *  sparePartName , briefDescription, partNumber and barCode are included
         *
         * */

        List<String> splitText = stringToList(textQuery);
        String formattedSplitText ="";
        System.out.println("splitText => " + splitText);

        BasicDBObject textJSONQuery = new BasicDBObject();
        //List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        //obj.add(new BasicDBObject("$search", "rolcar"));
        //obj.add(new BasicDBObject("$language", "spanish"));
        for(String s : splitText){
            formattedSplitText = formattedSplitText.concat("\""+s+"\" ");
        }

        //System.out.println("spliteTextFormatted => " + formattedSplitText);
        BasicDBObject Obj =  new BasicDBObject();
        //Obj.put("$search","\"rolcar\" \"sagaji\" \"laser\"");
        Obj.put("$search",formattedSplitText);
        Obj.put("$language","spanish");
        textJSONQuery.put("$text",Obj);
        System.out.println("fulltext" + textJSONQuery);

        HashMap<String,Object> JSONObject = new HashMap<String, Object>();
        DBCollection collection = db.getCollection("spares");
        DBCursor cursor = collection.find(textJSONQuery);
        System.out.println("\n\n-----------------------");
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        System.out.println("\n\n-----------------------");



    }

    public List getAllSales(){


        DBCollection collection = db.getCollection("sales");
        //List obj = collection.find( query ).skip( 1000 ).limit( 100 ).toArray();
        List a = collection.find().toArray();

        return a;


    }






    /**
     * useful functions for above methods
     *
     */


    public List<String> stringToList(String text){

        /**
         *
         * Convert a String ( words separated by white spaces) to List<String>
         *  and remove white spaces
         * */

        List<String> result = new ArrayList<String>();

        List<String> aux ;
        aux = Arrays.asList(text.replace(" ","/").split("/"));

        //System.out.println(aux);
        for(String s : aux){

            /*remove empty values*/
            if(s.isEmpty() | s == null | s.length() <= 0 ){
            }
            else {
                result.add(s);
            }

        }

        //System.out.println(result);

        return result;
    }



    public List<String> stringFormattedToStringList(String s) {
    //public static List<String> stringFormattedToStringList(String s) {

        List<String> result = new ArrayList<String>();
        List<String> aux;
        aux =  Arrays.asList(s.substring(1,s.length()-1).replaceAll("\"", "").split(","));
        // .substring  removes the first an last characters from the string ('[' & ']')
        // .replaceAll removes all quotation marks from the string (replaces with empty string)
        // .split brakes the string into a string array on commas (omitting the commas)
        // Arrays.asList converts the array to a List
        /**
         * Example of string that handle
         * [2008, 2009, 2010]
         *
         */

        for(int i=0;i<aux.size() ; i++) {
            result.add(aux.get(i).trim()); //.trim() - clean extra white spaces
        }

        return result;
    }

    public  List<Integer> stringFormattedToIntegerList(String s) {

        List<Integer> result = new ArrayList<Integer>();
        List<String> aux;
        aux =  Arrays.asList(s.substring(1,s.length()-1).replaceAll("\"","").split(","));
        // .substring  removes the first an last characters from the string ('[' & ']')
        // .replaceAll removes all quotation marks from the string (replaces with empty string)
        // .split brakes the string into a string array on commas (omitting the commas)
        // Arrays.asList converts the array to a List
        /**
         * Example of string that handle
         * [2008, 2009, 2010]
         *
         */

        for(int i=0;i<aux.size() ; i++) {
            result.add(Integer.parseInt(aux.get(i).trim())); //.trim() - clean extra white spaces
        }

        return result;
    }





    public  HashMap<String, Object> stringModelToHashModel(String modelString){

        HashMap<String, Object> auxHash = new HashMap<String, Object>();
        List<String> aux;

        aux = Arrays.asList(modelString.substring(1, modelString.length() - 1).split("},"));
        /**
         *
         * Example of string that can handle
         * {Aveo={standar=[2005]}, Ferrari={v6=[2011, 2012, 2013, 2014], v8=[2015], v12=[2014, 2015]}, Golf={limited=[2013], standar=[2008, 2009, 2010]}}
         *
         *
         * {
         Aveo={
         standar=[2005]
         },
         Ferrari={
         v6=[2011, 2012, 2013, 2014],
         v8=[2015],
         v12=[2014, 2015]
         },
         Golf={
         limited=[2013],
         standar=[2008, 2009, 2010]
         }
         }
         *
         *
         */



        String s;
        for(int i=0; i < aux.size(); i++)
        {
            if(i < (aux.size()-1) )
                s = aux.get(i).trim()+"}";
            else
                s = aux.get(i).trim();


            //System.out.println(">>"+s.replace(s.substring(0,s.indexOf("=")+1),""));
            //auxHash.put(s.substring(0,s.indexOf("=")),s.replace(s.substring(0,s.indexOf("=")+1),""));
            auxHash.put(s.substring(0,s.indexOf("=")),
                    arrayStringFormattedToHashMap(s.replace(s.substring(0,s.indexOf("=")+1),"")));

        }


        return auxHash;

    }





    public  HashMap<String, Object> arrayStringFormattedToHashMap(String str) {

        List<String> aux ;
        HashMap<String, Object> auxHash = new HashMap<String, Object>();

        aux = Arrays.asList(str.substring(1, str.length() - 1).split("],"));
        /**
         * .substring -  removes the first an last characters from the string ('{' & '}')
         * .split - brakes the string into a string array on commas (omitting the commas)
         *
         * Example of String that this function can handle
         * {v6=[2011, 2012, 2013, 2014], v8=[2015], v12=[2014, 2015]}
         */


        String s;
        for (int i = 0; i < aux.size(); i++) {
            if (i < (aux.size() - 1))
                s = aux.get(i).trim() + "]";
            else
                s = aux.get(i).trim();

            //System.out.println("><>>" +s);
            //System.out.println("><>>Key:" +s.substring(0,s.indexOf("=")));
            //System.out.println("><>>value:" +s.replace( s.substring(0, s.indexOf("=") + 1), "" ));
            auxHash.put(s.substring(0,s.indexOf("=")),
                    stringFormattedToIntegerList(s.replace(s.substring(0, s.indexOf("=") + 1), "")));

        }



        return auxHash;

    }
    
    /**
    *
    * Customized Reprt List of all Spares
    *
    */

    public List<HashMap<String,Object>> existenceReport(){
        DBCollection spares = db.getCollection("spares");
        BasicDBObject query = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject("partNumber",1).
                append("sparePart",1).
                append("brand",1).
                append("brandNumber",1).
                append("existence",1).
                append("stockMin", 1);
        //List obj = collection.find( query ).skip( 1000 ).limit( 100 ).toArray();
        List a = spares.find(query,fields).toArray();
        List<HashMap<String,Object>> report = new ArrayList<HashMap<String, Object>>();

        System.out.println("---->>>>>>< " + a.size());



        for(int i = 0; i < a.size() ; i ++)
        {
            //arrayStringFormattedToHashMap(a.get(i).toString());
            //aux = Arrays.asList();
            List<String> aux  = new ArrayList<String>();
            HashMap<String,Object> map2 = new HashMap<String, Object>();
            aux = Arrays.asList(a.get(i).toString().substring(1, a.get(i).toString().length() - 1).split(",")) ;

            HashMap<String,Object> map = new HashMap<String, Object>();

            System.out.println("*********************" + aux.size());
            for(int ii = 0; ii < aux.size() ; ii++){


                //System.out.println("auxxxxxx" + aux.get(ii));
                List<String> row = new ArrayList<String>();
                row = Arrays.asList(aux.get(ii).replace(": { \"$oid\"", "").replace("{", "").replace("}", "").replace("\"", "").split(":"));
                //System.out.println("Rowwwww " + row);

                map.put(row.get(0).trim(),row.get(1).trim());
                //System.out.println("row000000 >>"+row.get(0).trim()+"<<");
                //System.out.println("row111111 >>"+row.get(1).trim()+"<<");
                //System.out.println("MApppppp " +map);
            }

            //System.out.println("MApppppp " + map);
            report.add(map);
            //map.clear();
            //System.out.println("reporttttttt" + report);


        }

        return report;

    }
    
    public void removeObject(String key,String keyValue, String collectionName){

        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject query = new BasicDBObject(key, keyValue);
        query.put(key,keyValue);
        
        collection.remove(query);


    }



}