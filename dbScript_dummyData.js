use LHA;

//create collections
db.createCollection("spares");
db.createCollection("stockLog");
db.createCollection("users");
db.createCollection("sales");
//create index
db.spares.ensureIndex( {"brandNumber": 1,"barCode": 1},{ unique: true });
db.users.ensureIndex( { "username": 1 }, { unique: true } );
db.spares.ensureIndex( {
                         barCode: "text",
                         sparePart: "text",
                         briefDescription: "text",
                         brand: "text",
                         brandNumber: "text",
                         partNumber: "text",
                         systems: "text",
                         providers: "text" 

                       },
                       {
                        name: "fullTextSearch-Spares",
                        default_language: "spanish"
                      }
                     );


//sample data
db.spares.insert({
  sparePart : "bujia",
  briefDescription : "", 
  brand : "ngk",
  brandNumber : "ngk4341",
  partNumber : "ngk-2021-1",
  systems : ["afinacion"],  
  barCode : "11222334455",  
  compatibility :  {  
              aveo : {
                      standar : [2007,2008,2009,2010]
                     }    
  },
  providers : ["sagaji" ,"egarama"],  
  stockMin : 40,
  existence : 60,  
  specialOfferPrice : 10.0,
  salePrice : 16.0,
  balance : 340.0  
});

db.spares.insert({
  sparePart : "bujia laser",
  briefDescription : "",
  brand : "ngk",
  brandNumber : "ngk4342",
  partNumber : "ngk-2021-1-l",
  systems : ["afinacion"],  
  barCode : "0870935409504",  
  compatibility :  {  aveo : {
                      limited : [2010,2011,2012,2013]                      
                     }
    
  },
  providers : ["sagaji" ,"rolcar"],
  stockMin : 20,
  existence : 28,  
  specialOfferPrice : null,
  salePrice : 40.00,
  balance : 40.00  
});

db.spares.insert({
  sparePart : "bujia platinium",
  briefDescription : "",
  brand : "ngk",
  brandNumber : "ngk4343",
  partNumber : "ngk-2021-1-p",
  systems : ["afinacion"],  
  barCode : "01582524564564",  
  compatibility :  {  aveo : {                      
                      sport : [2014],
                      limited : [2013]
                     }
    
  },
  providers : ["sagaji" ,"cordero"],
  stockMin : 7,
  existence : 28,  
  specialOfferPrice : null,
  salePrice : 180.00,
  balance : 21.00  
}); 



db.spares.insert({
  sparePart : "amortiguador delantero izq",
  briefDescription : "",
  brand : "bogue",
  brandNumber : "ngk4344",
  partNumber : "mp-2840",
  systems : ["suspension"],  
  barCode : "84657775992",  
  compatibility :  {  gol : {
                      standar : [2009,2010,2011,2012]
                     }
    
  },
  providers : ["syd"],
  stockMin : 8,
  existence : 8,  
  specialOfferPrice : null,
  salePrice : 990.00,
  balance : 0  
});

db.spares.insert({
  sparePart : "amortiguador delantero der",
  briefDescription : "derecho",
  brand : "bogue",
  brandNumber : "ngk4345",
  partNumber : "mp-2841",
  systems : ["suspension"],  
  barCode : "846577755457",  
  compatibility :  {  gol : {
                      standar : [2009,2010,2011,2012]
                     }
    
  },
  providers : ["syd"],
  stockMin : 8,
  existence : 8,  
  specialOfferPrice : null,
  salePrice : 990.00,
  balance : 0  
});

db.spares.insert({
  sparePart : "amortiguador delantero izq (gas)",
  briefDescription : "gas izquierdo",
  brand : "bogue",
  brandNumber : "ngk4346",
  partNumber : "mp-2840-g",
  systems : ["suspension"],  
  barCode : "85736800223",  
  compatibility :  {  gol : {
                      standar : [2009,2010,2011,2012]
                     }
    
  },
  providers : ["syd"],
  stockMin : 8,
  existence : 8,  
  specialOfferPrice : null,
  salePrice : 1400.00,
  balance : 0  
});

db.spares.insert({
  sparePart : "amortiguador delantero der (gas)",
  briefDescription : "",
  brand : "bogue",
  brandNumber : "ngk4347",
  partNumber : "mp-2841-g",
  systems : ["suspension"],  
  barCode : "8573680048485",  
  compatibility :  {  gol : {
                      standar : [2009,2010,2011,2012]
                     }
    
  },
  providers : ["syd"],
  stockMin : 8,
  existence : 8,  
  specialOfferPrice : null,
  salePrice : 1400.00,
  balance : 0  
});

db.spares.insert({
  sparePart : "amortiguador trasero izq y der",
  briefDescription : "",
  brand : "bogue",
  brandNumber : "ngk4348",
  partNumber : "mp-2841-g",
  systems : ["suspension"],  
  barCode : "1573680048485",  
  compatibility :  {  gol : {
                      standar : [2009,2010,2011,2012]
                     }
    
  },
  providers : ["syd"],
  stockMin : 8,
  existence : 8,  
  specialOfferPrice : null,
  salePrice : 600.00,
  balance : 0  
});

db.spares.insert({
  sparePart : "amortiguador delantero izq",
  briefDescription : "",
  brand : "kyb",
  brandNumber : "ngk4349",
  partNumber : "kb-2840",
  systems : ["suspension"],  
  barCode : "9989889898",  
  compatibility :  {  gol : {
                      standar : [2009,2010,2011,2012]
                     }
    
  },
  providers : ["sagaji"],
  stockMin : 8,
  existence : 8,  
  specialOfferPrice : null,
  salePrice : 780.50,
  balance : 0  
});


db.spares.insert({
  sparePart : "amortiguador delantero der",
  briefDescription : "",
  brand : "kyb",
  brandNumber : "ngk4340",
  partNumber : "kb-2841",
  systems : ["suspension"],  
  barCode : "99757575757",  
  compatibility :  {  gol : {
                      standar : [2009,2010,2011,2012]
                     }
    
  },
  providers : ["sagaji"],
  stockMin : 8,
  existence : 8,  
  specialOfferPrice : null,
  salePrice : 780.50,
  balance : 0  
});





db.stockLog.insert({
  eventDate : new Date(),
  who : "Tony Stark",
  comment : "pedido urgente",
  io : "in",  
  sparePartId : ObjectId("536c11b1e4b020aacd0bd396"), 
  howMany : 17,
  howManyBefore : 1,  
  purchasePrice : 5.10,
  provider : "sagaji"
});


db.users.insert({
  username : "sajid",
  password : "myPassword"  
});
