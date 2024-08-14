package df.base.jpa;

import df.base.common.hibernate5.generator.IdPrefixGenerator;
import df.base.common.hibernate5.generator.PrefixedId;

import java.util.Random;

public class DefaultIdGenerator implements IdPrefixGenerator {

    @Override
    public String generate(Object ordinalID, PrefixedId annotation, Object entity) {
        String prefix    = annotation.prefixValue();
        String separator = annotation.prefixSeparator();
        String id        = annotation.numberFormat().formatted(ordinalID);
        String hash      = "%04x".formatted(getRandomInt());

        return "%s%s%s%s%s".formatted(prefix, separator, hash, separator, id).toUpperCase();
    }

    private int getRandomInt() {
        return new Random().nextInt(0xFFFF - 0x1000) + 0x1000;
    }

}
