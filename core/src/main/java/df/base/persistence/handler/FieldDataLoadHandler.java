package df.base.persistence.handler;

import df.base.common.commans.CommandRequest;
import df.base.common.commans.annotation.Action;
import df.base.common.commans.annotation.Command;
import df.base.common.context.Context;
import df.base.common.context.provider.data.DataProvider;
import df.base.common.reflection.Reflections;
import df.base.dto.form.FieldOptionDTO;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldConfig;
import df.base.persistence.entity.form.FieldOption;
import df.base.service.form.FieldOptionService;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unused"})
@Command("action")
public class FieldDataLoadHandler {

    @Action({"load"})
    public void preUpdate(String actionName, CommandRequest request) {
        Context context = request.context();

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
