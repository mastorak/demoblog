package lu.mastorak.eddemoblog.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Utility class to retrieve a bean Object from the application context
 * @author mastorak
 *
 */
public class SpringApplicationContext implements ApplicationContextAware {

	  private static ApplicationContext CONTEXT;

	  /**
	   * Setter is called from within the application context loaded during init
	   * @param context 
	   */
	  public void setApplicationContext(ApplicationContext context) throws BeansException {
	    CONTEXT = context;
	  }

	  /**
	   * Static method to retrieve the Bean
	   * @param beanName 
	   * @return bean Object
	   */
	  public static Object getBean(String beanName) {
	    return CONTEXT.getBean(beanName);
	  }
	}