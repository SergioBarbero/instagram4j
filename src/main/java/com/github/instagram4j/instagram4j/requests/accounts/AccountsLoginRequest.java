package com.github.instagram4j.instagram4j.requests.accounts;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.models.IGPayload;
import com.github.instagram4j.instagram4j.requests.IGPostRequest;
import com.github.instagram4j.instagram4j.responses.accounts.LoginResponse;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class AccountsLoginRequest extends IGPostRequest<LoginResponse> {

    public static String phoneId = UUID.randomUUID().toString();
    @NonNull
    private String username;
    @NonNull
    private String password;

    @Override
    public String path() {
        return "accounts/login/";
    }

    @Override
    public IGPayload getPayload(IGClient client) {
        LoginPayload loginPayload = new LoginPayload(username, password);
        loginPayload.set_csrftoken(null);
        loginPayload.setAdid(client.getAdvertisingId());
        loginPayload.setJazoest(String.valueOf(AccountsLoginRequest.genJazoest(phoneId)));
        loginPayload.setAdid(client.getAdvertisingId());
        loginPayload.setGuid(client.getGuid());
        return loginPayload;
    }

    @Override
    public Class<LoginResponse> getResponseType() {
        return LoginResponse.class;
    }

    @Data
    public static class LoginPayload extends IGPayload {
        @NonNull
        private String username;
        @NonNull
        private String enc_password;
        private String login_attempt_count = "0";
        private String jazoest;
        private String country_codes = "[{\"country_code\":\"1\",\"source\":[\"default\"]}]";
        private String adid;
        private String guid;
        private String google_tokens = "[]";
    }

    public static int genJazoest(String poneId) {
        int sum = 0;
        for (int i = 0; i < poneId.length(); i++) {
            // Print current character
            char c = poneId.charAt(i);
            int code = Character.getType(c);
            sum += code;
        }
        return sum;
    }

}
