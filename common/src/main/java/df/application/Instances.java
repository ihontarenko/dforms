package df.application;

import org.jmouse.common.mapping.Mapping;
import org.jmouse.common.mapping.MappingFactory;

public interface Instances {
    Mapping         MAPPING          = MappingFactory.create(PackageCoreRoot.class);
}
