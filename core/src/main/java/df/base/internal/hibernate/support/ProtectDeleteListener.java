package df.base.internal.hibernate.support;

import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.springframework.stereotype.Component;

@Component
public class ProtectDeleteListener implements PreDeleteEventListener {

    @Override
    public boolean onPreDelete(PreDeleteEvent event) {
        Object object = event.getEntity();

        if (object instanceof ProtectedEntity entity) {
//            return entity.nonRemovable();
        }

        return false;
    }

}
