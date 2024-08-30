package df.web.controller;

public interface MAVConstants {

    // user
    String VIEW_USER            = "user/index";
    String REQUEST_MAPPING_USER = "/user";
    String REDIRECT_USER        = REQUEST_MAPPING_USER;

    // role
    String VIEW_ROLE            = "user/role";
    String REQUEST_MAPPING_ROLE = "/user/role";
    String REDIRECT_ROLE        = REQUEST_MAPPING_ROLE;

    // privilege
    String VIEW_PRIVILEGE            = "user/privilege";
    String REQUEST_MAPPING_PRIVILEGE = "/user/privilege";
    String REDIRECT_PRIVILEGE        = REQUEST_MAPPING_PRIVILEGE;

    // form
    String VIEW_FORM_INDEX      = "form/index";
    String REQUEST_MAPPING_FORM = "/form";
    String REDIRECT_FORM        = REQUEST_MAPPING_FORM;

    // form config
    String VIEW_FORM_CONFIG            = "form/config";
    String REQUEST_MAPPING_FORM_CONFIG = "/form/config";
    String REDIRECT_FORM_CONFIG        = REQUEST_MAPPING_FORM_CONFIG + "/%s";

    // form field
    String REQUEST_MAPPING_FORM_FIELD = "/form/field";
    String REDIRECT_FORM_FIELD        = REQUEST_MAPPING_FORM_FIELD;
    String VIEW_FORM_FIELD_LIST       = "form/field_list";
    String VIEW_FORM_FIELD_FORM       = "form/field_form";

}
