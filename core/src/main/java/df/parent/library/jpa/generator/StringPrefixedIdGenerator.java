package df.parent.library.jpa.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.lang.reflect.Member;
import java.util.Properties;

public class StringPrefixedIdGenerator implements IdentifierGenerator {

    private final PrefixedId                       annotation;
    private final Member                           member;
    private final CustomIdGeneratorCreationContext context;

    public StringPrefixedIdGenerator(PrefixedId annotation, Member member, CustomIdGeneratorCreationContext context) {
        this.annotation = annotation;
        this.member = member;
        this.context = context;
    }

    @Override
    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) {
//        ConfigurationHelper.getString()
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        System.out.println(this);
        return null;
    }

}
