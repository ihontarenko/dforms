package df.base.common.pipeline;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import static df.base.common.resources.ResourceReader.readFileToString;

public abstract class ConfigLoader {

    abstract PipelineConfig load(String source);

    enum FileType {
        XML, JSON, YAML, YML, PROPERTIES
    }

    public static ConfigLoader createLoader(String file) {
        final int lastIndex = file.lastIndexOf('.');
        FileType  fileType  = FileType.valueOf(file.substring(lastIndex + 1).toUpperCase());

        return switch (fileType) {
            case XML -> new XML();
            case JSON -> new JSON();
            case PROPERTIES -> new JavaProperties();
            case YAML, YML -> new YAML();
        };
    }

    public PipelineConfig readValue(ObjectMapper mapper, String source) {
        try {
            return mapper.readValue(readFileToString(source), PipelineConfig.class);
        } catch (JsonProcessingException e) {
            throw new PipelineConfigLoadingException(e);
        }
    }

    public static class XML extends ConfigLoader {

        @Override
        public PipelineConfig load(String source) {
            return readValue(new XmlMapper(), source);
        }

    }

    public static class JSON extends ConfigLoader {

        @Override
        public PipelineConfig load(String source) {
            return readValue(new ObjectMapper(), source);
        }

    }

    public static class YAML extends ConfigLoader {

        @Override
        public PipelineConfig load(String source) {
            return readValue(new YAMLMapper(), source);
        }

    }

    public static class JavaProperties extends ConfigLoader {

        @Override
        public PipelineConfig load(String source) {
            return readValue(new JavaPropsMapper(), source);
        }

    }

}
