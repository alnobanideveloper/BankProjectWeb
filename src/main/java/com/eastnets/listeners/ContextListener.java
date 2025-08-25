package com.eastnets.listeners;

import com.eastnets.config.AppConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class ContextListener implements ServletContextListener {
    private AnnotationConfigApplicationContext ctx;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        context.setAttribute("context" , ctx);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ctx.close();
    }
}
