package com.appBacky.model;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class Comment {

    public Date Created;
    public Integer Author;
    public String Text;
    public String GIFLink;
    public ReactionCounts reactionCounts;
    public List<Reply> reply;
    public List<Reaction> reaction;


    //Dummy constructor
    public Comment(){

    }

    //validate method incoming
    public String validateOutGoing(){
        return "a";
    }


    //Returns a sample comment to be used for easier debugginh
    public Comment debugComment(){
        Reaction reactionReply = new Reaction();
        reactionReply.Created = new Date();
        reactionReply.ReactedBy = 1;
        reactionReply.Reaction = 1;
        this.reaction = reaction;

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

        Comment comment = new Comment();
        comment.Created = new Date();
        comment.Author = 1;
        comment.Text = "Example comment text";
        comment.GIFLink = "http gif";
        comment.reactionCounts = react;
        List reactionList = new ArrayList<Reaction>();
        reactionList.add(reactionReply);
        comment.reaction = reactionList;


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

        return comment;
    }

    public String deleteComment(Integer commentInt, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);
        MongoCollection<Document> collection = database.getCollection("Comments");
        Bson filter = eq("Id", commentInt);
        DeleteResult result = collection.deleteOne(filter);
        System.out.println(result);
        if (result == null){
            return "Error";
        }
        return "Success";
    }

    //Updates the comment text to the passed variable
    public String updateComment(Integer commentInt, String commentString, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);
        MongoCollection<Document> collection = database.getCollection("Comments");
        Document query = new Document();
        query.append("Id","commentInt");
        Document setData = new Document();
        setData.append("comment", commentString);
        Document update = new Document();
        update.append("$set", setData);
        //To update single Document
        collection.updateOne(query, update);
        return "Success";
    }

    public Document getComment(Integer commentInt, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);


        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("Comments");
        Document comment = collection.find(eq("id", commentInt)).first();

        return comment;
    }

    public List<Reply> getCommentReplies(Integer commentInt, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);


        // Retrieving a collection
        MongoCollection<Comment> collection = database.getCollection("Comments", Comment.class);
        Comment comment = collection.find(eq("id", commentInt)).first();


        return comment.reply;
    }


    public List<Document>  getAllComment(Integer postInt, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);


        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("Comments");
        List<Document> comments = (List<Document>) collection.find(eq("postid", postInt));

        return comments;
    }

    public String  addComment(Integer postInt, String text, Integer user, MongoClient mongo){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);
        MongoCollection<Document> collection = database.getCollection("Comments");
        Document comment = new Document("_id", new ObjectId());
        comment.append("postId", postInt)
                .append("text", text)
                .append("user",user);
        //collection.insertOne(comment);
        // Retrieving a collection

        return "ok";
    }


    public String  addReply(Integer commentInt,  MongoClient mongo, Reply reply){
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("appyChat");
        System.out.println("Credentials ::"+ credential);
        MongoCollection<Document> collection = database.getCollection("Comments");
        Document comment = collection.find(eq("id", commentInt)).first();
        comment.append("reply", reply);
        //collection.insertOne(comment);
        // Retrieving a collection

        return "ok";
    }



}
