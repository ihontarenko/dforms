package df.base.persistence.generator;

import df.base.common.hibernate.generator.IdPrefixGenerator;
import df.base.common.hibernate.generator.PrefixedId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DefaultIdGenerator implements IdPrefixGenerator {

    @Override
    public String generate(Object ordinalID, PrefixedId annotation, Object entity) {
        String prefix    = annotation.prefixValue();
        String separator = annotation.prefixSeparator();
        String id        = annotation.numberFormat().formatted(ordinalID);
        String hash      = "%x".formatted(getHash());

        return "%s%s%s%s%s".formatted(prefix, separator, hash, separator, id).toUpperCase();
    }

    private long getHash() {
        return Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ssmmHHuuMMdd")));
    }

}
