package df.base.common.container.example.services;

import df.base.common.container.bean.annotation.Provide;
import df.base.common.container.bean.annotation.EnvironmentValue;

@Provide
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
