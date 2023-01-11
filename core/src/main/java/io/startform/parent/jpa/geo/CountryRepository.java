package io.startform.parent.jpa.geo;

import io.startform.parent.library.jpa.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CountryRepository extends AbstractRepository<Country> {

    @Override
    public Class<Country> getEntityClassType() {
        return Country.class;
    }

}
