package net.ftxf.dapps.spring;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import net.ftxf.dapps.service.EmailService;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@SpringBootApplication
@EntityScan("net.shk.net.entities")
@ComponentScan("net.shk.net.controllers")
@ComponentScan("net.shk.net.service")
@ComponentScan("net.shk.net.config")
@EnableJpaRepositories("net.shk.net.repositories")
@EnableJpaAuditing
public class Application {

	
  public static void main(String[] args) throws AddressException, MessagingException {
	  
      
	
	ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
    for (String name : applicationContext.getBeanDefinitionNames()) {
      System.out.println(name);
    }
    
    
  }

  @Bean
  public SpringTemplateEngine templateEngine(ApplicationContext ctx) {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();

    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setApplicationContext(ctx);
    templateResolver.setPrefix("templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setCacheable(false);

    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateEngine.setTemplateResolver(templateResolver);
    templateEngine.addDialect(new LayoutDialect());
    return templateEngine;
  }
}
