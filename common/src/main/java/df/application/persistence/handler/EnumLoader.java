package df.application.persistence.handler;

import org.jmouse.action.ActionRequest;
import org.jmouse.action.annotation.Action;

public class EnumLoader {

    @Action("load")
    public Object load(ActionRequest request) {
        System.out.println(getClass());
        System.out.println(request.arguments());
        return null;
    }

}
