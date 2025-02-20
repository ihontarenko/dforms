package df.common.extensions.spring.resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.AbstractVersionStrategy;
import org.springframework.web.servlet.resource.VersionPathStrategy;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class ContentHashVersionStrategy extends AbstractVersionStrategy {

    public ContentHashVersionStrategy() {
        super(new FileNameHashVersionPathStrategy());
    }

    @Override
    public String getResourceVersion(Resource resource) {
        try {
            byte[] content = FileCopyUtils.copyToByteArray(resource.getInputStream());
            return DigestUtils.sha256Hex(content);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    String.format("Failed to calculate hash for %s", resource), exception);
        }
    }

    protected static class FileNameHashVersionPathStrategy implements VersionPathStrategy {

        private static final Pattern pattern = Pattern.compile("\\/(\\S*)\\.");

        @Override
        @Nullable
        public String extractVersion(String path) {
            String  version = null;
            Matcher matcher = pattern.matcher(path);

            if (matcher.find()) {
                version = matcher.group(1);
                version = (version.contains("/") ? version.substring(version.lastIndexOf('/') + 1) : version);
            }

            return version;
        }

        @Override
        public String removeVersion(String path, String version) {
            return StringUtils.delete(path, format("/%s", version));
        }

        @Override
        public String addVersion(String path, String version) {
            return String.format("%s/%s.%s", StringUtils.stripFilenameExtension(path), version, StringUtils.getFilenameExtension(path));
        }

    }

}
