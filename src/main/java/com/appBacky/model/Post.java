package com.appBacky.model;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static java.lang.System.err;

public class Post {

    public ObjectId id; ///this is the right type for the id
    @BsonProperty(value = "author")
    public Integer author;
    @BsonProperty(value = "created")
    public Date created;
    @BsonProperty(value = "multimedia")
    public List<String> multimedia;
    @BsonProperty(value = "distribution")
    public List<Integer> distribution;
    @BsonProperty(value = "stats")
    public Stats stats;
    @BsonProperty(value = "comment")
    public List<Comment> comment;
    @BsonProperty(value = "reaction")
    public List<Reaction> reaction;

    //Dummy constructor
    public Post(){

    }

    //validate method incoming
    public String validateOutGoing(){
        return "a";
    }



    //Returns a sample post to be used for easier debugginh
    public Post debugPost(){

        this.id = new ObjectId("507f1f77bcf86cd799439011"); ///Create an object id with a default value
        this.author = 10;
        this.created = new Date();

        //Gen multimedia strings
        List multi = new ArrayList<String>();
        multi.add("a Really long string to denote a url address for a giff");
        multi.add("b Really long string to denote a url address for a giff");
        this.multimedia = multi;

        //Gen distribution strings
        List distri = new ArrayList<Integer>();
        distri.add(1);
        distri.add(2);
        this.distribution = distri;

        //Gen stats
        Stats debugStats = new Stats();
        debugStats.shares = 13;
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
        debugStats.reactionCounts = react;
        this.stats = debugStats;

        Reaction reactionReply = new Reaction();
        reactionReply.Created = new Date();
        reactionReply.ReactedBy = 1;
        reactionReply.Reaction = 1;
        this.reaction = reaction;

        Comment comment = new Comment();
        comment.Created = new Date();
        comment.Author = 1;
        comment.Text = "Example comment text";
        comment.GIFLink = "http gif";
        comment.reactionCounts = react;
        List reactionList = new ArrayList<Reaction>();
        reactionList.add(reactionReply);
        comment.reaction = reactionList;
        List commentList = new ArrayList<Comment>();
        commentList.add(comment);


        Reply reply = new Reply();
        reply.Created = new Date();
        reply.Author = 4;
        reply.Text = "texty";
        reply.GIFLink = "giffy linky";
        reply.reactionCounts = react;
        reply.reaction = reactionList;
        List replyList = new ArrayList<Reply>();
        replyList.add(reply);
        comment.reply = replyList;
        this.comment = commentList;


        return this;

    }
    //POJO test--------------------------------
    public Post getPostPojo(Integer postInt, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);


        // Retrieving a collection
        MongoCollection<Post> collection = database.getCollection("Posts" , Post.class);
        Post post = collection.find(eq("id", postInt)).first();

        return post;
    }


    public String insertPostPojo(Post newpost, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);


        // Retrieving a collection
        String status = "OK";
        try{
            MongoCollection<Post> collection = database.getCollection("Posts" , Post.class);
            collection.countDocuments();
            //collection.insertOne(newpost);
        }catch (Exception ex){
            status = "ERROR";
        }


        return status;
    }





    //-----------------------------------------



    public Document getPost(Integer postInt, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);


        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("Posts");
        Document post = collection.find(eq("id", postInt)).first();

        return post;
    }

    public List<Document> getPostList(Integer postInt, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);


        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("Posts");
        List<Document> posts = (List<Document>) collection.find(eq("id", postInt));

        return posts;

    }

    public String deletePost(Integer postInt, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);
        MongoCollection<Document> collection = database.getCollection("Posts");
        Bson filter = eq("Id", postInt);
        DeleteResult  result = collection.deleteOne(filter);
        System.out.println(result);
        if (result == null){
            return "Error";
        }
        return "Success";
    }


    public String updatePost(Integer postInt, String postString,  MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);
        MongoCollection<Document> collection = database.getCollection("Posts");
        Document query = new Document();
        query.append("Id","postInt");
        Document setData = new Document();
        setData.append("post", postString);
        Document update = new Document();
        update.append("$set", setData);
        //To update single Document
        collection.updateOne(query, update);
        return "Success";
    }

    //POJO test--------------------------------
    public Post addreaction(Integer postInt, MongoClient mongo, Integer reactionId){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);


        // Retrieving a collection
        MongoCollection<Post> collection = database.getCollection("Posts" , Post.class);
        Post post = collection.find(eq("id", postInt)).first();

        return post;
    }






}
