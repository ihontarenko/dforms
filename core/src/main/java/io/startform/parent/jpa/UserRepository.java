package io.startform.parent.jpa;

import io.startform.parent.library.jpa.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractRepository<User> {

    @Override
    public Class<User> getEntityClassType() {
        return User.class;
    }

}
