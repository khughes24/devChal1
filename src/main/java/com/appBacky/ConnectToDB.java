package com.appBacky;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoCredential;
import org.bson.Document;

import java.util.Iterator;

public  class ConnectToDB {
    public static void demo( MongoClient mongo  ) {

        try{
            // Creating a Mongo client
            //MongoClient mongo = new MongoClient( "localhost" , 27017 );

            // Creating Credentials
            MongoCredential credential;
            credential = MongoCredential.createCredential("sampleUser", "myDb",
                    "password".toCharArray());
            System.out.println("Connected to the database successfully");

            // Accessing the database
            MongoDatabase database = mongo.getDatabase("test");
            System.out.println("Credentials ::"+ credential);


            // Retrieving a collection
            MongoCollection<Document> collection = database.getCollection("collection1");
            System.out.println("Collection myCollection selected successfully");
            // Getting the iterable object
            FindIterable<Document> iterDoc = collection.find();
            int i = 1;
            // Getting the iterator
            Iterator it = iterDoc.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
                i++;
            }
        }catch (Exception ex){
            System.out.println("Unable to connect to db");
        }

    }
}
