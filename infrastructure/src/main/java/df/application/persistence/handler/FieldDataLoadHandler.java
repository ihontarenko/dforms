package df.application.persistence.handler;

import df.application.dto.form.FieldOptionDTO;
import df.application.service.form.FieldOptionService;
import df.common.commans.CommandRequest;
import df.common.commans.annotation.Action;
import df.common.commans.annotation.Command;
import svit.context.Context;
import svit.provider.data.DataProvider;
import svit.reflection.Reflections;
import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.form.FieldConfig;
import df.application.persistence.entity.form.FieldOption;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unused"})
@Command("action")
public class FieldDataLoadHandler {

    @Action({"load"})
    public void preUpdate(String actionName, CommandRequest request) {
        Context context = request.context();
        StringUtils
        if (context.getProperty("entity") instanceof FieldConfig entity) {
            Field              field   = entity.getField();
            FieldOptionService service = request.context().getBean(FieldOptionService.class);

            if (request.queryParameter("class") instanceof Class<?> dataProviderType) {
                DataProvider<String, Object> dataProvider = (DataProvider<String, Object>) Reflections.instantiate(
                        Reflections.findFirstConstructor(dataProviderType));

                Set<FieldOption> options = new HashSet<>();

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
