package lu.mastorak.eddemoblog.persistence.dao;

import java.util.List;

import org.bson.types.ObjectId;

import lu.mastorak.eddemoblog.persistence.model.Post;

/**
 * 
 * @author mastorak
 *
 */
public interface PostDAOInterface {

	public List<Post> findAll();
	public Post findById(ObjectId id);
	public Post findById(String id);
	public Post findOne();
	public ObjectId saveOrUpdate(Post post);
	public ObjectId save(Post post);
	public ObjectId update(Post post);
	public boolean delete(String id);
}
