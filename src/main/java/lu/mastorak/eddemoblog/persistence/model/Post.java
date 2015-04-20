package lu.mastorak.eddemoblog.persistence.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Post object
 * @author mastorak
 *
 */
@JsonAutoDetect
public class Post implements Serializable{
	
	private static final long serialVersionUID = 3176995926785488827L;
	
	private String id;
	private String userId;
	private String title;
	private String text;
	private Date created;
	private Date updated;
	
	
	/**
	 * Default Post constructor
	 */
	public Post() {
		super();
	}

	/**
	 * Use this constructor for new Posts objects
	 * @param userId
	 * @param title
	 * @param text
	 * @param created
	 */
	public Post(String userId, String title, String text, Date created) {
		super();
		this.userId = userId;
		this.title = title;
		this.text = text;
		this.created = created;
	}
	
	/**
	 * Use this constructor for existing Post objects
	 * @param id
	 * @param userId
	 * @param title
	 * @param text
	 * @param created
	 * @param updated
	 */
	public Post(String id, String userId, String title, String text, Date created,Date updated) {
		super();
		this.id=id;
		this.userId = userId;
		this.title = title;
		this.text = text;
		this.created = created;
		this.updated= updated; 
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	

	
}
