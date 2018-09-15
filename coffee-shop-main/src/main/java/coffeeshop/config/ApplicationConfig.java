package coffeeshop.config;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/CoffeeShop",
        callerQuery = "SELECT password FROM user_info WHERE username = ?",
        groupsQuery = "SELECT role FROM user_info WHERE username = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        priorityExpression = "${100}",
        hashAlgorithmParameters = {
            "${applicationConfig.hashAlgorithmParametersAsPairStream}"
        })
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/faces/login.xhtml"
        ))
@DeclareRoles({"admin", "staff", "customer"})
@Named
@ApplicationScoped
public class ApplicationConfig {
    
    private Map<String, String> hashAlgorithmParameters;
    
    @PostConstruct
    void init() {
        hashAlgorithmParameters = new HashMap<>();
        hashAlgorithmParameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        hashAlgorithmParameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        hashAlgorithmParameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
    }

    public Stream<String> getHashAlgorithmParametersAsPairStream() {
        return hashAlgorithmParameters.entrySet().stream().map((entry) -> {
            return entry.getKey() + "=" + entry.getValue();
        });
    }
    
    public Map<String, String> getHashAlgorithmParameters() {
        return hashAlgorithmParameters;
    }
}
