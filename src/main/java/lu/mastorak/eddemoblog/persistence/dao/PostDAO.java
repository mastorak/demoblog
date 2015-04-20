package lu.mastorak.eddemoblog.persistence.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lu.mastorak.eddemoblog.persistence.model.Post;
import lu.mastorak.eddemoblog.util.Constants;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDbFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

/**
 * CRUD operations for Post objects
 * @author mastorak
 *
 */
public class PostDAO implements PostDAOInterface{

	final static Logger logger = Logger.getLogger(PostDAO.class);
	
	private MongoDbFactory factory;

	public void setFactory(MongoDbFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Utility method to marshal the DBObject into a Post object
	 * @param postObject
	 * @return Post
	 */
	private Post marshalPost(DBObject postObject){
		
		Post post=null;
		
		if(postObject!=null){
			ObjectId id=(ObjectId)postObject.get(Constants.ID);
			String userId=(String)postObject.get(Constants.USERID);
			String title=(String)postObject.get(Constants.TITLE);
			String text=(String)postObject.get(Constants.TEXT);
			Date created=(Date)postObject.get(Constants.CREATED);
			Date updated=(Date)postObject.get(Constants.UPDATED);
			
		    post= new Post(id.toString(),userId, title, text, created,updated);
		}else{
			logger.warn("Cannot marshal post. Object is null");
		}
		return post;
	}
	
	/**
	 * Method to return a list of posts sorted by creation date
	 * @return List<Post>
	 */
	public List<Post> findAll(){
		
		List<Post> list=new ArrayList<Post>();
		DBCollection collection = factory.getDb().getCollection("posts");
		
		DBCursor cursorDocJSON = collection.find();
		BasicDBObject orderBy=new BasicDBObject(Constants.CREATED, -1);
		cursorDocJSON.limit(100).sort(orderBy);
		
		logger.info("Retrieving all Posts");
		while (cursorDocJSON.hasNext()) {
			DBObject postObject =cursorDocJSON.next();
			logger.info(postObject);
			Post post= marshalPost(postObject);
			list.add(post);
		}
		return list;
	}
	
	
	
	/**
	 * Method to get a Post object based on the ObjectId
	 * @param ObjectId 
	 * @return Post
	 */
	public Post findById(ObjectId id){
		
		Post post= null;
		
		DBCollection collection = factory.getDb().getCollection("posts");
		
		BasicDBObject query = new BasicDBObject();
		query.put(Constants.ID, id);	
		
		DBObject postObject=collection.findOne(query);
		logger.info("Retrieving "+postObject);
		
		if(postObject!=null){
			 post= marshalPost(postObject);
		}
		return post;
	}
	
	/**
	 * Method to get a Post object based on the String representation of the id
	 * @param String id
	 * @return Post
	 */
	public Post findById(String id){
		
		Post post=null;
		
		try {
			post=findById(new ObjectId(id));
		} catch (IllegalArgumentException e) {
			logger.error("Invalid id string to create ObjectId",e);
		}
		
		return post;
	}
	
	/**
	 * Method to get the first post in the collection
	 * @return Post
	 */
	public Post findOne(){
		Post post= null;
		DBCollection collection = factory.getDb().getCollection("posts");
		
		logger.info("Retrieving first Post in the colection");
		DBObject postObject=collection.findOne();
		
		post=marshalPost(postObject);
		
		return post;
		
	}
	
	/**
	 * Method to create or update a Post object. If the id is null we create a new post else we update the existing
	 * @param Post
	 * @return ObjectId
	 */
	public ObjectId saveOrUpdate(Post post){
		ObjectId id=null;
		
		if(post!=null){
			if(post.getId()==null){
				id=save(post);
			}
			else{
				id=update(post);
			}
		}
		return id;
	}
	
	/**
	 * Save a new Post object
	 * @param Post
	 * @return ObjectId
	 */
	public ObjectId save(Post post){		

		ObjectId id=null;
		
		if(post!=null){
			DBCollection collection = factory.getDb().getCollection("posts");
			
			BasicDBObject postObject = new BasicDBObject();
			id=new ObjectId();
			
			postObject.put(Constants.ID, id);
			postObject.put(Constants.USERID, post.getUserId());
			postObject.put(Constants.TITLE,post.getTitle());
			postObject.put(Constants.TEXT, post.getText());
			postObject.put(Constants.CREATED,post.getCreated());
			
			logger.info("Inserting new post "+postObject);
			collection.insert(postObject);
			
		}
		return id;
	}
	
	/**
	 * Update an existing Post object
	 * @param Post
	 * @return ObjectId
	 */
	public ObjectId update(Post post){
		
		ObjectId id=null;
		
		if(post!=null){
			DBCollection collection = factory.getDb().getCollection("posts");
			
			BasicDBObject updatedDocument = new BasicDBObject();
			updatedDocument.put("$set", 
					new BasicDBObject(Constants.USERID, post.getUserId()).
					append(Constants.TITLE, post.getTitle()).
					append(Constants.TEXT, post.getText()).
					append(Constants.UPDATED, new Date())
					);
		
			
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put(Constants.ID, new ObjectId(post.getId()));
			
			logger.info("Updating post "+updatedDocument);
			
			collection.update(searchQuery, updatedDocument);
			id=new ObjectId(post.getId());
		}
		
		return id;
	}
	
	/**
	 * Method to delete a post from the collection
	 * @param String id
	 * @return boolean true if operation was successful
	 */
	public boolean delete(String id){
		
		boolean success= false;
		DBCollection collection = factory.getDb().getCollection("posts");
		
		BasicDBObject query = new BasicDBObject();
		query.put(Constants.ID, new ObjectId(id));
		
		logger.info("Removing document with id: "+id);
		WriteResult result=collection.remove(query);
		
		//we should have deleted exactly 1 record
		if(result.getN()==1){
			success=true;
		}else{
			logger.warn("Error deleting post. Number of posts deleted:"+result.getN());
		}
			
		return success;
	}
	
}
