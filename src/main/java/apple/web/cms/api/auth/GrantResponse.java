package apple.web.cms.api.auth;

public class GrantResponse {

    private final String username;
    private final String role;
    
    public GrantResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }
}
