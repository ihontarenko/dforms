package df.base.service.form;

import df.base.jpa.User;
import df.base.jpa.form.Form;
import df.base.security.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class FormManagementService {

    @Autowired
    private FormService formService;

    public boolean checkUserPermission(String formId, Authentication authentication) {
        if (formId != null) {
            return formService.getById(formId)
                    .map(form -> checkUserPermission(form, authentication)).orElse(true);
        }

        return true;
    }

    public boolean checkUserPermission(Form form, Authentication authentication) {
        User owner = form.getUser();
        User user  = ((UserInfo) authentication.getPrincipal()).getUser();

        return owner.equals(user);
    }

}
