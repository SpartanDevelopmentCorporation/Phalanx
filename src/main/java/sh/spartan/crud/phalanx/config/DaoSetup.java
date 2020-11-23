package sh.spartan.crud.phalanx.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import sh.spartan.crud.phalanx.service.BaseService;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
public class DaoSetup {

    private static final Logger LOG = LoggerFactory.getLogger(DaoSetup.class);

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {

        //Setting up Data Access Object for each FullService
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(FullService.class);

        beans.forEach((beanName, bean) -> {
            FullService annotation = bean.getClass().getAnnotation(FullService.class);
            BaseService service = (BaseService) bean;

            if (annotation != null) {
                Class<Object> dao = annotation.dao();
                service.setEntity(dao);
            } else {
                LOG.warn(beanName + " was not able to set up dao");
            }
        });

    }
}
