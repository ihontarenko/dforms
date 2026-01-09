package df.application;

import df.common.commans.CommandsManager;
import df.common.commans.CommandsManagerFactory;
import org.jmouse.common.mapping.Mapping;
import org.jmouse.common.mapping.MappingFactory;
import org.jmouse.core.events.EventManager;
import org.jmouse.core.events.EventManagerFactory;

public interface Instances {

    EventManager    EVENT_MANAGER    = EventManagerFactory.create(PackageCoreRoot.class);
    CommandsManager COMMANDS_MANAGER = CommandsManagerFactory.create(PackageCoreRoot.class);
    Mapping         MAPPING          = MappingFactory.create(PackageCoreRoot.class);

}
