package df.base.common.application_context.sandbox.services;

import df.base.common.application_context.bean.Bean;
import df.base.common.application_context.bean.BeanInjection;
import df.base.common.application_context.bean.PropertyValue;

@Bean
public interface Storage {

    class InMemoryStorage implements Storage {

        @BeanInjection("SERVICE_D")
        private ServiceInterface service;

        @PropertyValue("java.home")
        private String javaHome;

        public ServiceInterface getService() {
            return service;
        }

        public String getJavaHome() {
            return javaHome;
        }

        @Override
        public String toString() {
            return "InMemoryStorage{" +
                    "\njavaHome=" + javaHome +
                    "\nservice=" + service +
                    "\n}";
        }
    }

}
