package com.xiu.common.web.utils;

import org.apache.commons.configuration.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : xiu@xiu.com
 * @DATE :2012-3-27 下午3:16:03
 *       </p>
 **************************************************************** 
 */
public class SpringResourceLocator implements BeanFactoryAware {
    private static BeanFactory beanFactory = null;
    private static Configuration configuration = null;

    @Override
    public void setBeanFactory(final BeanFactory factory) throws BeansException {
        beanFactory = factory;
    }

    public static Configuration getDatabaseConfiguration() {
        if (configuration == null) {
            beanFactory.getBean("databaseConfiguration");
            configuration = (Configuration) beanFactory.getBean("databaseConfiguration");
        }
        return configuration;
    }

    public static Configuration getConfiguration() {
        return getDatabaseConfiguration();
    }
    
    

    
}
