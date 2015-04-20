package eddemoblog;

import java.util.Date;

import lu.mastorak.eddemoblog.persistence.dao.PostDAO;
import lu.mastorak.eddemoblog.persistence.model.Post;
import lu.mastorak.eddemoblog.util.SpringApplicationContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/springcontext.xml"})
public class PostDAOTest {

	
	
	private Post createTestPost(){
		Post post = new Post("testUser",
				"Test title",
				"This is the test text of the Post",
				new Date());
		
		return post;
	}
	
	@Test
	public void testInsert() {
		PostDAO dao = (PostDAO) SpringApplicationContext.getBean("postDAO");
		dao.saveOrUpdate(createTestPost());
	}
	
	@Test
	public void testFindById(){
		PostDAO dao = (PostDAO) SpringApplicationContext.getBean("postDAO");
		
		dao.findById(dao.findOne().getId());
	}
	
	@Test
	public void testUpdate(){
		PostDAO dao = (PostDAO) SpringApplicationContext.getBean("postDAO");
		Post post = dao.findById(dao.findOne().getId());
		dao.update(post);
	}

	@Test
	public void testFindAll(){
		PostDAO dao = (PostDAO) SpringApplicationContext.getBean("postDAO");
		dao.findAll();
	}
	
	@Test
	public void testDelete(){
		PostDAO dao = (PostDAO) SpringApplicationContext.getBean("postDAO");
		dao.delete(dao.findOne().getId());
	}
}
