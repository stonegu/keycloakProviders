package com.bizislife.keycloak.util;

import com.bizislife.keycloak.model.rep.PermittedEmailRep;
import com.bizislife.keycloak.model.rep.ProspectingUserRep;
import com.bizislife.keycloak.spi.PermittedEmailProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AuthenticationManager;

import java.util.List;

public class Validator {

    // ------------------------------ for prospecting user -----------------------------
    // only biz admin user or client account with same same realm Id can do it
    public static ValidationResult isActionForProspectingAllowed(AuthenticationManager.AuthResult auth, ProspectingUserRep prospectingUser) {
        if ( (AuthenticateUtil.isBizUser(auth) && AuthenticateUtil.hasAdminRealmRole(auth)) ||
                (AuthenticateUtil.hasClientRealmRole(auth) && auth.getSession().getRealm().getId().equals(prospectingUser.getRealmId()))) {
            return new ValidationResult(true, null);
        }
        return new ValidationResult(false, "Permission is not allowed.");
    }

    public static ValidationResult validateAddProspectingUser(ProspectingUserRep user) {
        if (StringUtils.isNotEmpty(user.getRealmId()) && GeneralUtil.emailValidate(user.getEmail())) {
            return new ValidationResult(true, null);
        } else {
            return new ValidationResult(false, "Realm can't be empty and email should be a valid email address");
        }
    }

    public static ValidationResult isAddProspectingUserAllowed(ProspectingUserRep prospectingUser, KeycloakSession session) {
        List<PermittedEmailRep> permittedEmailList = session.getProvider(PermittedEmailProvider.class).listEmail(prospectingUser.getRealmId());
        if (CollectionUtils.isEmpty(permittedEmailList)) { // not specific settings
            return new ValidationResult(true, null);
        } else { // has permission settings
            if (permittedEmailList.stream().parallel().filter(permittedEmailRep -> permittedEmailRep.getEmail().equals(prospectingUser.getEmail())).count() > 0) {
                return new ValidationResult(true, null);
            } else {
                return new ValidationResult(false, "Your email is not in the permitted email list.");
            }
        }
    }




    // -------------------------------------- for permitted email ---------------------

    // only biz admin user or client account with same same realm Id can do it
    public static ValidationResult isActionForPermittedEmailAllowed(AuthenticationManager.AuthResult auth, PermittedEmailRep permittedEmail) {
        if ( (AuthenticateUtil.isBizUser(auth) && AuthenticateUtil.hasAdminRealmRole(auth)) ||
                (AuthenticateUtil.hasClientRealmRole(auth) && auth.getSession().getRealm().getId().equals(permittedEmail.getRealmId()))) {
            return new ValidationResult(true, null);
        }
        return new ValidationResult(false, "Permission is not allowed.");
    }

    public static ValidationResult validateAddPermittedEmail(PermittedEmailRep email) {
        if (StringUtils.isNotEmpty(email.getRealmId()) && GeneralUtil.emailValidate(email.getEmail())) {
            return new ValidationResult(true, null);
        } else {
            return new ValidationResult(false, "Realm can't be empty and email should be a valid email address");
        }
    }









    @Getter
    @Setter
    @AllArgsConstructor
    static public class ValidationResult {
        private boolean passed;
        private String message;
    }
}
