package io.startform.parent.jpa;

import io.startform.parent.jpa.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractRepository<User> {

    @Override
    public Class<User> getReflection() {
        return User.class;
    }

}
