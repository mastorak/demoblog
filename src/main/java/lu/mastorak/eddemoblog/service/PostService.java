package lu.mastorak.eddemoblog.service;

import java.util.List;

import org.bson.types.ObjectId;

import lu.mastorak.eddemoblog.persistence.dao.PostDAO;
import lu.mastorak.eddemoblog.persistence.model.Post;

/**
 * Service class giving access to the DAO
 * @author mastorak
 *
 */
public class PostService implements PostServiceInterface{

	private PostDAO dao;

	public void setDao(PostDAO dao) {
		this.dao = dao;
	}
	
	public Post find(String id){
		Post post=null;
		post=dao.findById(id);
		return post;
	}
	
	public Post find(ObjectId id){
		Post post=null;
		post=dao.findById(id);
		return post;
	}
	
	public List<Post> getAllPosts(){
		return dao.findAll();
	}
	
	public ObjectId saveOrUpdate(Post post){
		 return dao.saveOrUpdate(post);
	}
	
	public boolean delete(String id){
		return dao.delete(id);
	}
	
}
