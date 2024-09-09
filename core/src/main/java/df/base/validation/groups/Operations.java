package df.base.validation.groups;

import jakarta.validation.GroupSequence;

public interface Operations {
    interface Create { }
    interface Update { }
    @GroupSequence({Create.class, Update.class})
    interface CreateOrUpdate { }
    interface Simple { }
    interface Advanced { }
    interface Primary { }
    interface Secondary { }
    interface PrimaryId { }
}
