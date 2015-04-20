package lu.mastorak.eddemoblog.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lu.mastorak.eddemoblog.persistence.model.Post;
import lu.mastorak.eddemoblog.service.PostService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.util.JSON;

/**
 * Controller that exposes methods as REST services for the Post object
 * @author mastorak
 *
 */
@Controller
public class PostController {

	private PostService postService;
	
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	/**
	 * REST Service method to retrieve a single Post object based on id.
	 * @param id
	 * @param request
	 * @param response
	 * @return Post
	 */
	@RequestMapping(value = "/getpost",  params="id", method = RequestMethod.GET)
	public @ResponseBody Post getPost(@RequestParam Object id,HttpServletRequest request, HttpServletResponse response) {
 		
		Post post=postService.find(id.toString());
		
		return post;
	}
	
	/**
	 * REST Service method to retrieve the list of Posts Objects
	 * @param request
	 * @param response
	 * @return Post[]
	 */
	@RequestMapping(value = "/getallposts", method=RequestMethod.GET)
	public @ResponseBody Post[] getAllPosts(HttpServletRequest request, HttpServletResponse response) {
		
		Post[] posts=null;
		List<Post> postList=postService.getAllPosts();
		
		posts=new Post[postList.size()];
		int i=0;
		for(Post post:postList){
			posts[i]=post;
			i++;
		}
		return posts;
	}
	
	/**
	 * Method to save or update a Post. It returns the id of the inserted/updated post
	 * @param viewPost
	 * @param request
	 * @param response
	 * @return String 
	 */
	@RequestMapping(value = "/saveorupdatepost",  method = RequestMethod.POST)
	public @ResponseBody String saveOrUpdatePost(@RequestBody  Post viewPost,HttpServletRequest request, HttpServletResponse response){
		
		String id = null;
		Post post = null;
		
		id = viewPost.getId();
		
		if (id == null || id.isEmpty()) {
			post = new Post("defaultuser", viewPost.getTitle(),
			viewPost.getText(), new Date());
		} else {
			post = postService.find(id);
			post.setTitle(viewPost.getTitle());
			post.setText(viewPost.getText());
		}

		id = postService.saveOrUpdate(post).toString();
			
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("id", id);
		String data=JSON.serialize(map);
		
		return data;
	}
	
	@RequestMapping(value = "/deletepost",  method = RequestMethod.DELETE)
	public @ResponseBody String deletePost(@RequestBody Post viewPost,HttpServletRequest request, HttpServletResponse response) {
		
		boolean result=false;
		String data=null;
	
		result = postService.delete(viewPost.getId());

		if (result == true) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", viewPost.getId());
		data = JSON.serialize(map);

		return data;
		
	}
}
