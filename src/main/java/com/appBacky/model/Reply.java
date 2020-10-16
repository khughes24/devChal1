package com.appBacky.model;

import com.appBacky.model.Reaction;
import com.appBacky.model.ReactionCounts;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class Reply {

    public Date Created;
    public Integer Author;
    public String Text;
    public String GIFLink;
    public ReactionCounts reactionCounts;
    public List<Reaction> reaction;

    //Dummy constructor
    public Reply(){

    }

    //validate method incoming
    public String validateOutGoing(){
        return "a";
    }



    //Returns a sample post to be used for easier debugginh
    public Reply debugReply(){

        ReactionCounts react = new ReactionCounts();
        react.Angry = 0;
        react.Awesome = 0;
        react.Boring = 8;
        react.Care = 0;
        react.Crazy = 0;
        react.FakeNews = 0;
        react.Haha = 0;
        react.Lame = 0;
        react.Legal = 0;
        react.Like = 0;
        react.Love = 0;
        react.Meal = 0;
        react.Sad = 1;
        react.Scary = 0;
        react.Wow = 0;

        Reaction reactionReply = new Reaction();
        reactionReply.Created = new Date();
        reactionReply.ReactedBy = 1;
        reactionReply.Reaction = 1;
        List reactionList = new ArrayList<Reaction>();
        reactionList.add(reactionReply);

        Reply reply = new Reply();
        reply.Created = new Date();
        reply.Author = 4;
        reply.Text = "texty";
        reply.GIFLink = "giffy linky";
        reply.reactionCounts = react;
        reply.reaction = reactionList;

        return reply;

    }

    public String deleteReply(Integer replyInt, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);
        MongoCollection<Document> collection = database.getCollection("Reply");
        Bson filter = eq("Id", replyInt);
        DeleteResult result = collection.deleteOne(filter);
        System.out.println(result);
        if (result == null){
            return "Error";
        }
        return "Success";
    }


    public String reactToReply(Integer replyInt, String reactionId, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);
        MongoCollection<Document> collection = database.getCollection("Reply");
        Document reply = collection.find(eq("id", replyInt)).first();
        Reaction reaction = new Reaction(Integer.parseInt(reactionId), 1);
        //reply.reaction.add(reaction);
        ///ReactionCounts reactionCounts = new ReactionCounts();
        //reply.reactionCounts = reactionCounts.updateReactionCounts(reply.reactionCounts, reactionId);

        //System.out.println(result);
        //if (result == null){
          //  return "Error";
        //}
        return "Success";
    }

    public String updateReply(Integer replyInt, String replyString,  MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);
        MongoCollection<Document> collection = database.getCollection("Reply");
        Document query = new Document();
        query.append("Id","replyInt");
        Document setData = new Document();
        setData.append("reply", replyString);
        Document update = new Document();
        update.append("$set", setData);
        //To update single Document
        collection.updateOne(query, update);
        return "Success";
    }

    public Document getReply(Integer replyInt, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);


        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("Replys");
        Document reply = collection.find(eq("id", replyInt)).first();

        return reply;
    }


    public String updateReplyText( MongoClient mongo, Integer replyId, String text){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);


        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("Replys");
        Document reply = collection.find(eq("id", replyId)).first();
        reply.append("text", text);
        //collection.insertOne(reply);

        return "OK";
    }

    public String addReplyReaction( MongoClient mongo, Integer replyId, Reaction reaction){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);


        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("Replys");
        Document reply = collection.find(eq("id", replyId)).first();
        List<Reaction> reactionList = (List<Reaction>) reply.get("reaction");
        reactionList.add(reaction);
        reply.append("reaction", reaction);
        //collection.insertOne(reply);

        return "OK";
    }






}
