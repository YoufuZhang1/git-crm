package com.youfuzhang.listener;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class MyServletContext implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //tomcat死的时候，需要强行搞死MySQLCJ版本的驱动的一个线程
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
