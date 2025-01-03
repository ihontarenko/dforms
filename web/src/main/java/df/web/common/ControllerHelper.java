package df.web.common;

import df.application.service.RedirectAware;
import df.web.common.flash.FlashMessage;
import df.web.common.flash.FlashMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.function.Supplier;

import static df.application.Messages.*;
import static df.web.common.flash.FlashMessage.error;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

@SuppressWarnings({"unused"})
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ControllerHelper implements RedirectAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);

    private final FlashMessageService flash;
    private final ModelAndView        mav;
    private       String              redirectUrl;
    private       RedirectAttributes  attributes;
    private       BindingResult       bindingResult;

    public ControllerHelper(FlashMessageService flash) {
        this.mav = new ModelAndView();
        this.flash = flash;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public String getViewName() {
        return mav.getViewName();
    }

    public void setViewName(String viewName) {
        mav.setViewName(viewName);
    }

    public RedirectAttributes getRedirectAttributes() {
        return attributes;
    }

    public void setRedirectAttributes(RedirectAttributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getRedirectUrl() {
        return requireNonNull(redirectUrl, ERROR_REDIRECT_URL_MUST_BE_SPECIFIED);
    }

    @Override
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void addMessage(FlashMessage flashMessage) {
        requireNonNull(flash, ERROR_FLASH_MESSAGE_SERVICE_IS_REQUIRED)
                .addMessage(requireNonNull(attributes, ERROR_REDIRECT_ATTRIBUTES_REQUIRED), flashMessage);
    }

    public ControllerHelper attribute(Object attributeValue) {
        mav.addObject(attributeValue);

        return this;
    }

    public ControllerHelper attribute(String attributeName, Object attributeValue) {
        mav.addObject(attributeName, attributeValue);

        return this;
    }

    public ControllerHelper attributes(Map<String, ?> objects) {
        mav.addAllObjects(objects);

        return this;
    }

    public ModelAndView redirect(Throwable throwable) {
        if (throwable instanceof RedirectAware redirectAware) {
            setRedirectUrl(requireNonNullElse(redirectAware.getRedirectUrl(), redirectUrl));
        }

        LOGGER.error("REDIRECTING DUE TO ERROR", throwable);

        addMessage(error(throwable.getMessage()));

        return redirect();
    }

    public ModelAndView redirect() {
        return new ModelAndView("redirect:" + getRedirectUrl());
    }

    public ModelAndView resolveWithRedirect() {
        if (requireNonNull(bindingResult, ERROR_BINDING_RESULT_IS_REQUIRED).hasFieldErrors()) {
            LOGGER.warn("VALIDATION ERRORS FOUND, RESOLVING WITHOUT REDIRECT.");
            return resolveWithoutRedirect();
        }

        LOGGER.info("NO VALIDATION ERRORS, REDIRECTING TO: {}", getRedirectUrl());

        return redirect();
    }

    public ModelAndView resolveWithoutRedirect() {
        requireNonNull(mav.getViewName(), ERROR_VIEW_NAME_MUST_BE_SPECIFIED);
        return mav;
    }

    public static ControllerHelper create(String viewName) {
        return create(viewName, null, null, null, null);
    }

    public static ControllerHelper create(String viewName, String redirectUrl, BindingResult bindingResult) {
        return create(viewName, redirectUrl, null, bindingResult, null);
    }

    public static ControllerHelper create(String viewName, String redirectUrl, RedirectAttributes redirectAttributes,
                                          BindingResult bindingResult, FlashMessageService flashMessageService) {
        ControllerHelper helper = new ControllerHelper(flashMessageService);

        helper.setViewName(viewName);
        helper.setRedirectUrl(redirectUrl);
        helper.setRedirectAttributes(redirectAttributes);
        helper.setBindingResult(bindingResult);

        return helper;
    }

    public ControllerHelper bindAttributes(Supplier<Map<String, Object>> supplier) {
        Map<String, Object> attributes = supplier.get();

        attributes(attributes);

        return this;
    }

}
