package df.base.common.libs.jbm.example.services;

import df.base.common.libs.jbm.bean.Bean;
import df.base.common.libs.jbm.bean.EnvironmentValue;

@Bean
public interface Storage {

    class InMemoryStorage implements Storage {

        @EnvironmentValue("java.home")
        private String javaHome;

        @EnvironmentValue("project.name")
        private String projectName;

        public String getJavaHome() {
            return javaHome;
        }

        @Override
        public String toString() {
            return "InMemoryStorage{javaHome=%s, projectName=%s}".formatted(javaHome, projectName);
        }
    }

}
