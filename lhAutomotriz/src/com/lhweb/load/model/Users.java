package com.lhweb.load.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by s47id on 5/10/14.
 */
public class Users {

    private String username = "";
    private String password = "";
    private String hashPassword = "";

    public Users(){
        this.username = "";
        this.password = "";
        this.hashPassword = "";
    }

    public void setUsername(String username) {
        this.username = username;

    }

    public void setPassword(String password) {
        this.hashPassword = md5(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return hashPassword;
    }


    public void createUser(){
        Stock stock = new Stock();
        stock.connect();

        HashMap<String,Object> user =  new HashMap<String, Object>();
        user.put("username",this.username);
        user.put("password",this.hashPassword);
        try{
            stock.storeJSONObject(user,"users");
        }
        catch (Throwable e ){
            System.out.println("Error - User Not Created");
            System.out.println(e);
        }

        stock.close();

    }

    public int authenticate(String username, String password){
        /**
         * 0 => successful , 1,7 => Error
         */

        int result = 7 ;

        Stock stock = new Stock();
        stock.connect();

        HashMap<String,Object> user = new HashMap<String, Object>();
        user = stock.loadObject("username",username,"users");

        stock.close();


        if(user.get("password").equals(md5(password))){
            System.out.println("User " + "\"" +username+ "\"" + " Successful Auth");
            result = 0;
        }
        else {

            System.out.println("User "+ "\"" +username+ "\"" +"  Not Authenticated");
            result = 1;
        }

        return result;
    }






    public String md5(String password)
    {
        String passwordToHash = password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        //System.out.println(generatedPassword);
        return generatedPassword;

    }


}
