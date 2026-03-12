package df.application.persistence.action;

import df.application.dto.form.FieldOptionDTO;
import df.application.service.form.FieldOptionService;
import org.jmouse.action.ActionRequest;
import org.jmouse.action.annotation.Action;
import org.jmouse.core.access.data.DataProvider;
import org.jmouse.core.scope.Context;
import org.jmouse.core.reflection.Reflections;
import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.form.FieldConfig;
import df.application.persistence.entity.form.FieldOption;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unused"})
public class FieldDataLoadHandler {

    @Action("action:load")
    public void preUpdate(ActionRequest request) {
        Context context = request.context();

        if (context.getProperty("entity") instanceof FieldConfig entity) {
            Field              field   = entity.getField();
            FieldOptionService service = request.context().getBean(FieldOptionService.class);

            if (request.argument("class") instanceof Class<?> dataProviderType) {
                DataProvider<String, Object> dataProvider = (DataProvider<String, Object>) Reflections.instantiate(
                        Reflections.findFirstConstructor(dataProviderType));

                Set<FieldOption> options = new HashSet<>();

                entity.setConfigValue("PROCESSED! " + entity.getConfigValue());

                dataProvider.getValuesMap().forEach((key, value) -> {
                    FieldOptionDTO option = new FieldOptionDTO();

                    option.setKey(key);
                    option.setValue((String) value);

                    service.createOrUpdate(field, option);
                });
            }
        }
    }

}
