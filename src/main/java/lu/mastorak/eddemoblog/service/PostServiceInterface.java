package lu.mastorak.eddemoblog.service;

import java.util.List;

import org.bson.types.ObjectId;

import lu.mastorak.eddemoblog.persistence.model.Post;

/**
 * 
 * @author mastorak
 *
 */
public interface PostServiceInterface {

	public Post find(String id);
	public Post find(ObjectId id);
	public List<Post> getAllPosts();
	public ObjectId saveOrUpdate(Post post); 
	public boolean delete(String id);
}
