package df.application;

import df.common.commans.CommandsManager;
import df.common.commans.CommandsManagerFactory;
import svit.mapping.Mapping;
import svit.mapping.MappingFactory;
import svit.observer.EventManager;
import svit.observer.EventManagerFactory;

public interface Instances {

    EventManager    EVENT_MANAGER    = EventManagerFactory.create(PackageCoreRoot.class);
    CommandsManager COMMANDS_MANAGER = CommandsManagerFactory.create(PackageCoreRoot.class);
    Mapping         MAPPING          = MappingFactory.create(PackageCoreRoot.class);

}
