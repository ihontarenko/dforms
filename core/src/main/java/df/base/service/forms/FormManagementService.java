package df.base.service.forms;

import df.base.jpa.User;
import df.base.jpa.form.Form;
import df.base.security.UserInfo;
import df.base.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class FormManagementService {

    @Autowired
    private FormService formService;

    public boolean checkUserPermission(String formId, Authentication authentication) {
        if (formId != null) {
            return formService.getFormById(formId)
                    .map(form -> checkUserPermission(form, authentication)).orElse(true);
        }

        return true;
    }

    public boolean checkUserPermission(Form form, Authentication authentication) {
        User owner = form.getUser();
        User user  = ((UserInfo) authentication.getPrincipal()).getUser();

        return owner.equals(user);
    }

    public Form requireFormById(final String formId) {
        return formService.getFormById(formId).orElseThrow(()
                -> new ResourceNotFoundException("Form '%s' couldn't be found".formatted(formId)));
    }

}
