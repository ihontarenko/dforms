package df.base.security;

import java.util.Map;

public record UserRequest(
        String email, String name, String password, Provider provider, Map<String, Object> attributes) {
}
