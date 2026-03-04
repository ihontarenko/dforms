package df.application.persistence.entity.form;

import df.application.persistence.entity.support.ElementType;
import org.jmouse.core.access.ObjectAccessorWrapper;
import org.jmouse.core.access.descriptor.structured.DescriptorResolver;
import org.jmouse.dom.CommentInfoCorrector;
import org.jmouse.dom.CorrectNodeDepth;
import org.jmouse.dom.Node;
import org.jmouse.dom.TagName;
import org.jmouse.dom.meterializer.*;
import org.jmouse.dom.renderer.RendererContext;
import org.jmouse.dom.renderer.Renderers;
import org.jmouse.dom.renderer.RenderingProcessor;
import org.jmouse.meterializer.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Demo {

    public static void main(String... arguments) {
        ThemeAssembly<Node, DOMRenderingPipeline>   assembler = ThemeAssembly.forTheme(new BootstrapThemeModule());
        PipelineBuilder<Node, DOMRenderingPipeline> builder   = new PipelineBuilder<>();

        builder.instanceFactory(DOMRenderingPipeline::new);
        configureTemplates(assembler.templateRegistry());

        DOMRenderingPipeline pipeline = assembler.build(new ObjectAccessorWrapper(), builder, new DOMMaterializer());

        Node node = pipeline.render(ModelReference.of(
                "df/form", getForm()
        ));

        node.execute(new CorrectNodeDepth());
        node.execute(new CommentInfoCorrector(n -> {
            if (n.getTagName() == TagName.INPUT) {
                return "Before Input!";
            }
            return null;
        }, n -> null));

        RenderingProcessor engine = new RenderingProcessor(Renderers.html());
        String             html    = engine.render(node, RendererContext.defaultsHtmlPretty());

        System.out.println(html);
    }

    private static void configureTemplates(TemplateRegistry registry) {
        registry.register("df/form", Templates.defaultForm("/form/_/id_f_0001/\"2\"", "POST"));
        registry.register("default.button.submit", BootstrapTemplates.submitButton("submit"));

        registry.register("field.type.SELECT", Html5Templates.select("name", "description", "options", "option.optionValue", "option.optionLabel"));
        registry.register("field.type.TEXT", BootstrapTemplates.inputText("name", "description", "value"));
    }

    public static Form getForm() {
        Form form = new Form();

        form.setDescription("Resistor & Other");
        form.setId("resistor_1");
        form.setName("add_resistor");

        Field title = new Field();
        title.setElementType(ElementType.TEXT);
        title.setName("title");
        title.setDescription("Name Of Resistor");
        title.setId("title_1");

        form.addField(title);

        Field vendor = new Field();
        FieldAttribute attribute = new FieldAttribute();

        attribute.setName("data-vendor");
        attribute.setValue("TI");

        vendor.setAttributes(Set.of(attribute));

        vendor.setElementType(ElementType.SELECT);
        vendor.setId("vendor_1");
        vendor.setName("vendor");
        vendor.setDescription("Vendor: ");

        Set<FieldOption> options = new HashSet<>();

        Map<String, String> vendors = new HashMap<>();

        vendors.put("ti",       "Texas Instruments");
        vendors.put("vishay",   "Vishay");
        vendors.put("nxp",      "NXP Semiconductors");
        vendors.put("stm",      "STMicroelectronics");
        vendors.put("onsemi",  "onsemi");
        vendors.put("infineon","Infineon Technologies");
        vendors.put("microchip","Microchip Technology");
        vendors.put("analog",  "Analog Devices");
        vendors.put("renesas", "Renesas Electronics");
        vendors.put("rohm",    "ROHM Semiconductor");

        for (Map.Entry<String, String> e : vendors.entrySet()) {
            FieldOption option = new FieldOption();
            option.setId("option_" + e.getKey());
            option.setOptionLabel(e.getValue());
            option.setOptionValue(e.getKey());
            options.add(option);
        }

        vendor.setOptions(options);

        form.addField(vendor);

        return form;
    }

}
