

C0m3t3unsn1k3

============================================================================================
====================                     ===================================================
====================     Docs            ===================================================
====================                     ===================================================
==========================================================================================

http://blog.mongodb.org/post/94065240033/getting-started-with-mongodb-and-java-part-i
http://blog.mongodb.org/post/94724924068/getting-started-with-mongodb-and-java-part-ii
http://docs.mongodb.org/manual/administration/security/

http://api.mongodb.org/java/current/
http://docs.mongodb.org/manual/core/replica-set-high-availability/
http://api.mongodb.org/java/2.12/com/mongodb/MongoURI.html
http://reactivemongo.org/
http://docs.mongodb.org/manual/
https://github.com/mongodb/mongo-java-driver/tree/master/src/examples/example



============================================================================================
====================                     ===================================================
====================     JSON DB Model   ===================================================
====================                     ===================================================
============================================================================================


----------------------------
sparePart
----------------------------
    db.spares.insert({
      sparePart : "bujia",  //sparepart name
      briefDescription : "",
      brand : "ngk",  //who make this sparepart
      brandNumber : "ngk02020", // Brand or Factory Number, sometimes the factory is equal to reference
      partNumber : "ngk-2021-1", // Reference Number
      systems : ["afinacion","arranque"],  
      barCode : "449539480394",  
      compatibility :  { 
          aveo : {
                      standar : [2007,2008,2009,2010],
                      limited : [2007,2008],
                      sport : [2010]
                      },
          camaro : {
                      v6 : [2007,2008,2009,2010],
                      v8 : [2014]                    
                     },
          ferrari : {
                      class01 : [2010],
                      class02 : [2011],
                      class03 : [2012],
                      class04 : [2013],
                      class05 : [2014],
                    }
      },
      compatibilityText : ["camaro","limited","2007","camaro","limited","2008"],
      providers : ["sagaji" ,"egarama","proveedor-3","proveedor-4"],  
      stockMin : 40,
      existence : 60,  
      salePrice : 16.00, // it is set by manager or business owner
      purchasePrice : 10.00, // last provider price (last time when LHAutomotriz bougth this sparepart)
      roe : 20.00,  // Return on Equity (rentabilidad)
      specialOfferPrice : 10.00,   /* calculado por porcentage o pesos descontados esto es manejado desde la 
                                interface y aqui solo se guarda el resultado
                                */
      balance : 340.00  /**
                    goal : to know how money is in stock : 
                    in(+)(unitprice*how many bougth)  out,sale(-)(salesprice)
                    useful to know la rentabilidad
                    */
    });




----------------------------
stockLog
----------------------------
    db.stockLog.insert({
      eventDate : new Date(),
      who : "",
      comment : "",
      io : "in",  // [in,out,registration,modification]
      sparePartId : ObjectId("536c11b1e4b020aacd0bd396"),  
      howMany : 17,
      howManyBefore : 1,  // cuantos habia antes de esta llegada
      purchasePrice : 5.10,
      provider : ""      
    });
//--- other version
    db.stockLog.insert({
      eventDate : new Date(),
      who : "",
      comment : "",
      io : "in",  // [in,out,registration,modification]
      sparePartId : {
                  "$ref" : "spares",
                  "$id" : ObjectId("5126bc054aed4daf9e2ab772"),
                  "$db" : "LHA"
                },
      howMany : 17,
      howManyBefore : 1,  // cuantos habia antes de esta llegada
      purchasePrice : 5.10,
      provider : "sagaji"      
    });


----------------------------
sparePartTrace
----------------------------
db.sparePartTrace.insert({
  eventDate : new Date(),
  sparePartId : ObjectId("536c11b1e4b020aacd0bd396"),
  quantity : 50 
});




----------------------------
users
----------------------------
db.users.insert({
  username : "sajid",
  password : "myPassword"  
});




============================================================================================
====================                     ===================================================
====================     Queries & DML   ===================================================
====================                     ===================================================
============================================================================================

//proveedor 1
 db.spares.find({Sparepart : "Bugia"})[1].Provider.Priority1;

// Todos los registros que contengan el string "Bujia" en sparePart para el  Aveo
  db.spares.find({$and : [ {sparepart :  /Bujia/ }, {'model.Aveo' : {$exists : true} }]  });
  db.spares.find({$and : [ {sparepart :  /.*Bujia.*/ }, {'model.Aveo' : {$exists : true} }]  });

// Todos los registros que contienen el string "2021" en partNumber
  db.spares.find({$and : [ {partNumber :  /2021/ }, {}]  });


// Todas las buigias para corvette modelo 2014
  db.spares.find({$and : [  {Sparepart : "Bugia"}, {'Model.Corvette' : {$in : [2014]} }]  })

//solo estos campos
  db.spares.find({},{barCode : 1, brandNumber : 1, existence : 1 , balance :1})


// full text search 
   db.spares.find({$text : { $search: "\"rolcar\" \"sagaji\" \"laserr\"", $language: "spanish"}})
   ***// AND operator every string


----------------------------
Update  $set
----------------------------
      db.spares.update({"barCode" : "1234567"},{$set : {existence : 50}})
      db.spares.update({"barCode" : "998877"},{$set : {existence : 50}})


----------------------------
Find - $or
----------------------------
      db.spares.find({$or : [ {"barCode" : "998877"},{"barCode" : "1234567"}  ] }, {existence : true}).pretty();





----------------------------
Remove a Document or embedded Document from  JSON 
----------------------------

      ------
      1
      ------
      db.toys.insert({ vowels : [
                              { letter : "a", count : 1 },
                              { letter : "e", count : 3 },
                              { letter : "i", count : 2 },
                              { letter : "o", count : 5 },
                              { letter : "u", count : 7 }
                              ],
                        ABC : [
                              { letter : "a", count : 10 },
                              { letter : "b", count : 2 },
                              { letter : "c", count : 3 },
                              { letter : "z", count : 7 }
                        ]
                        });

      db.toys.update({'_id' : ObjectId("534ccd251a21489c50c8cc79")},{$pull : {'vowels.$.letter' : "u"}})
      db.toys.find({'_id' : ObjectId("534ccd251a21489c50c8cc79")})
      db.presentations.update({'content.assets._id': ObjectId('4fc63def5b20fb722900010e')},
        {$pull: {'content.$.assets': {'_id': ObjectId('4fc63def5b20fb722900010e')}}})





      ------
      2
      ------
      db.a.insert({   a : "a",  b : "b",  c : "c" })
      db.a.update({ "_id" :  ObjectId("534da52852e6e9f2e7353309")},{$unset : {a : ""}});




      ------
      3
      ------      
      db.z.insert({
      name : "theName", 
      worth : [
            { p1 : [{existence : 1 },{ price : 1 }]},
            { p2 : [{existence : 2 },{ price : 2 }]},
            { p3 : [{existence : 3 },{ price : 3 }]}
          ]
          });



      db.z.update({"_id" : ObjectId("534db9897dac5d149b60b49e")},{$unset : {'worth.0' : ""}})
      db.z.update({"_id" : ObjectId("534db9897dac5d149b60b49e")}, {$pull : {"worth" : null}})
      db.z.find({ "_id" : ObjectId("534dbe587dac5d149b60b49f")},{'worth' : true}).pretty();







============================================================================================
====================                     ===================================================
====================   DDL & DBA Tasks   ===================================================
====================                     ===================================================
============================================================================================


----------------------------
  Index and Constraint
----------------------------
      add -   db.spares.ensureIndex( { "barCode": 1 }, { unique: true } )
              db.spares.ensureIndex( { "brandNumber": 1 }, { unique: true } )
              db.users.ensureIndex( { "username": 1 }, { unique: true } )
            compound :
              db.spares.ensureIndex( {"brandNumber": 1,"barCode": 1},{ unique: true });


      remove - db.spares.dropIndex( { "barCode": 1 } )
              db.spares.dropIndex("barCode_1");



      Text Index all Fields 
      ----------------------
            db.spares.ensureIndex(
                                 { "$**": "text" },
                                 { name: "sparesTextIndex" , default_language: "spanish"}
                               );

            db.spares.ensureIndex(
                           {
                             barCode: "text",
                             brandNumber: "text"
                           }
                         );

            db.spares.ensureIndex(
                           {
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
            db.spares.ensureIndex({barCode: "text",sparePart: "text",briefDescription: "text",brand: "text",brandNumber: "text",partNumber: "text",systems: "text",providers: "text" },{name: "fullTextSearch-Spares",default_language: "spanish"});


            *** in case requiere enable add  setParameter=textSearchEnabled=true   to mongodb.conf


      list indexs
      ------
            db.spares.getIndexes()
            db.system.indexes.find()



      rebuil
      ---------
          db.accounts.reIndex()


----------------------------
  Drop db & collection
----------------------------
      mongo <dbname> --eval "db.dropDatabase()"
      db.collection.drop();


----------------------------
Backups
----------------------------
    $ cd /home/s47id/projects/lha-spareparts
    $ mongodump --db LHA --out bkp_LHA_04May2014
    
    backup--
b = db.products_bak; db.products.find().forEach( function(o){ b.insert(o) } )
update---
p = db.products
p.find({for : "ac3"}).forEach(function(o){o.price=2; p.save(o);})




----------------------------
Restore
----------------------------
    $cd /home/s47id/projects/lha-spareparts
    $ mongorestore  bkp_LHA_04May2014

    o

    $ cd /home/s47id/projects/lha-spareparts/bkp_LHA_04May2014
    $ mongorestore  --db LHA  LHA/


    mongoimport --host 10.29.210.41  --db test --collection md5 --type csv --fields _id,md5 <  file.csv

----------------------------
Export
----------------------------
  $ mongoexport --collection spares --db LHA --out export.json

----------------------------
Replace MongoDB IP in every source file
----------------------------
    $ cd /home/s47id/EclipseProjects/lhaWeb/lhWeb/src/com/lhweb/load/model
    $ find ./  -type f -print | xargs  grep -in --color  "192.168.1.67"
    $ find ./  -type f -print | xargs  sed -i "s/192.168.1.67/127.0.0.1/g"
    $ find ./  -type f -print | xargs  grep -in --color  "127.0.0.1"
