package df.application.configuration;

import org.jmouse.core.access.ObjectAccessorWrapper;
import org.jmouse.dom.Node;
import org.jmouse.dom.meterializer.*;
import org.jmouse.dom.meterializer.hooks.NodeRuleHook;
import org.jmouse.dom.meterializer.rules.FillActionMethod;
import org.jmouse.meterializer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.jmouse.meterializer.NodeTemplate.element;
import static org.jmouse.meterializer.NodeTemplate.text;
import static org.jmouse.meterializer.ValueExpression.constant;

@Configuration
public class DomRenderingConfiguration {

    @Bean
    public Rendering<Node> domRendering() {
        ThemeAssembly<Node, DOMRenderingPipeline>   assembler = ThemeAssembly.forTheme(new BootstrapThemeModule());
        PipelineBuilder<Node, DOMRenderingPipeline> builder   = new PipelineBuilder<>();

        builder.instanceFactory(DOMRenderingPipeline::new);

        assembler.addHook(new NodeRuleHook(
                new NodeRuleSet().add(new FillActionMethod("post"))
        ));

        configureTemplates(assembler.templateRegistry());

        return assembler.build(new ObjectAccessorWrapper(), builder, new DOMMaterializer());
    }

    private static void configureTemplates(TemplateRegistry registry) {
        // default components
        registry.register("default.form", Templates.defaultForm());
        registry.register("default.button.submit", Html5Templates.submitButton("submit"));
        // default fields
        registry.register("field.type.NONE", element("h5", block -> block.child(text(constant("none block")))));
        registry.register("field.type.SELECT", Html5Templates.select("name", "description", "options", "option.optionValue", "option.optionLabel"));
        registry.register("field.type.TEXT", Html5Templates.inputText("name", "label", "value"));
    }

}
