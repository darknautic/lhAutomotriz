package com.lhweb.load.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by s47id on 5/3/14.
 */
public class Cart {

    private List<HashMap<String,Object>> items = new ArrayList<HashMap<String, Object>>();

    public Cart(){

        items.clear();

    }

    public List<HashMap<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<HashMap<String, Object>> items) {
        this.items = items;
    }




    public void addItem(String barCode){

        SparePart sparePart = new SparePart();
        sparePart.loadSparePart(barCode);

        int existence = sparePart.getExistence();

        HashMap<String,Object> item = new HashMap<String, Object>();
        item.clear();
        item.put("barCode",barCode);
        item.put("howMany",1);
        item.put("unitSalePrice", sparePart.getSalePrice());

        /* verify if it is  possible add one to the order */
        if(existence > 0 ){

            HashMap<String,Object> auxItem = getItemFromCart(barCode);

            if(auxItem.size() > 0 ){

                int count = Integer.parseInt(auxItem.get("howMany").toString());
                item.put("howMany", count + 1);
                this.items.set(this.items.indexOf(auxItem),item);
                //this.items.add(item);

            }
            else{
                // The item does Not exist on cart, so add it.
                this.items.add(item);

            }


        }
        else{
            System.out.println("Cart - AddItem Failure - No more items available ");
        }

    }



    private HashMap<String,Object> getItemFromCart(String barCode){

        HashMap<String,Object> item = new HashMap<String, Object>();
        item.clear();

        HashMap<String,Object> a = new HashMap<String, Object>();



        for( int i = 0; i <  items.size() ; i++ ){

            if(items.get(i).get("barCode").toString() == barCode){
                System.out.println("Cart - Item Found !¡");
                item = items.get(i);
            }
            else
            {
                System.out.println("Cart - No Item Included");
            }

        }

        return item;

    }




}

