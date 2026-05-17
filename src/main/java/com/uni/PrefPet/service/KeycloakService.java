package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Enum.Role;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public String criarUsuario(
            String email,
            String senha,
            Role role,
            String nome
    ) {
        // ... criação do usuário (igual ao que você tem) ...

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(email);
        user.setEmail(email);
        user.setFirstName(nome);
        user.setLastName(nome);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(senha);
        credential.setTemporary(false);
        user.setCredentials(List.of(credential));

        Response response = keycloak.realm(realm).users().create(user);

        String location = response.getHeaderString("Location");
        if (location == null) {
            throw new RuntimeException("Keycloak não retornou Location");
        }
        String userId = location.substring(location.lastIndexOf("/") + 1);

        // 👇 Aqui está a mudança — busca no CLIENT, não no realm
        String clientId = keycloak.realm(realm)
                .clients()
                .findByClientId("prefpet") // nome do seu client
                .get(0)
                .getId(); // UUID interno do client no Keycloak

        RoleRepresentation roleRep = keycloak.realm(realm)
                .clients()
                .get(clientId)
                .roles()
                .get(role.name())
                .toRepresentation();

        keycloak.realm(realm)
                .users()
                .get(userId)
                .roles()
                .clientLevel(clientId)
                .add(List.of(roleRep));

        return userId;
    }
}