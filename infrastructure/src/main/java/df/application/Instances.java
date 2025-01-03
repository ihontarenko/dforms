package df.application;

import df.common.commans.CommandsManager;
import df.common.commans.CommandsManagerFactory;
import df.common.mapping.Mapping;
import df.common.mapping.MappingFactory;
import df.common.observer.EventManager;
import df.common.observer.EventManagerFactory;

public interface Instances {

    EventManager    EVENT_MANAGER    = EventManagerFactory.create(PackageCoreRoot.class);
    CommandsManager COMMANDS_MANAGER = CommandsManagerFactory.create(PackageCoreRoot.class);
    Mapping         MAPPING          = MappingFactory.create(PackageCoreRoot.class);

}
