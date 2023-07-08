package com.github.instagram4j.instagram4j.requests;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.IGConstants;
import com.github.instagram4j.instagram4j.exceptions.IGResponseException;
import com.github.instagram4j.instagram4j.responses.IGResponse;
import com.github.instagram4j.instagram4j.utils.IGUtils;

import kotlin.Pair;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
public abstract class IGRequest<T extends IGResponse> {

    public String getApiDomain() {
        return IGConstants.API_DOMAIN;
    }

    public abstract String path();

    public abstract Request formRequest(IGClient client);

    public abstract Class<T> getResponseType();

    public String apiPath() {
        return IGConstants.API_V1;
    }

    public String baseApiUrl() {
        return IGConstants.BASE_API_URL;
    }

    public String getQueryString(IGClient client) {
        return "";
    }

    public HttpUrl formUrl(IGClient client) {
        return HttpUrl.parse(baseApiUrl() + apiPath() + path() + getQueryString(client));
    }

    public CompletableFuture<T> execute(IGClient client) {
        return client.sendRequest(this);
    }

    @SneakyThrows
    protected String mapQueryString(Object... strings) {
        StringBuilder builder = new StringBuilder("?");

        for (int i = 0; i < strings.length; i += 2) {
            if (i + 1 < strings.length && strings[i] != null && strings[i + 1] != null
                    && !strings[i].toString().isEmpty()
                    && !strings[i + 1].toString().isEmpty()) {
                builder.append(URLEncoder.encode(strings[i].toString(), "utf-8")).append("=")
                        .append(URLEncoder.encode(strings[i + 1].toString(), "utf-8")).append("&");
            }
        }

        return builder.substring(0, builder.length() - 1);
    }

    @SneakyThrows(IOException.class)
    public T parseResponse(Pair<Response, String> response) {
        T igResponse = parseResponse(response.getSecond());
        igResponse.setStatusCode(response.getFirst().code());
        if (!response.getFirst().isSuccessful() || (igResponse.getStatus() != null && igResponse.getStatus().equals("fail"))) {
            throw new IGResponseException(igResponse);
        }

        return igResponse;
    }

    public T parseResponse(String json) throws JsonProcessingException {
        return parseResponse(json, getResponseType());
    }

    public <U> U parseResponse(String json, Class<U> type) throws JsonProcessingException {
        log.debug("{} parsing response : {}", apiPath() + path(), json);
        U response = IGUtils.jsonToObject(json, type);

        return response;
    }

    protected Request.Builder applyHeaders(IGClient client, Request.Builder req) {
        req.addHeader("X-IG-Mapped-Locale", "en_US");
        req.addHeader("X-Bloks-Version-Id", "ce555e5500576acd8e84a66018f54a05720f2dce29f0bb5a1f97f0c10d6fac48");
        req.addHeader("X-IG-WWW-Claim", "0");
        req.addHeader("X-IG-App-Startup-Country", "US");
        req.addHeader("X-Bloks-Is-Layout-RTL", "false");
        req.addHeader("X-Bloks-Is-Panorama-Enabled", "true");
        req.addHeader("X-IG-Family-Device-ID", client.getPhone_id());
        req.addHeader("X-IG-Timezone-Offset", "-14400");
        req.addHeader("Priority", "u=3");
        req.addHeader("Accept-Encoding", "deflate");
        req.addHeader("Host", this.getApiDomain());
        req.addHeader("X-FB-HTTP-Engine", "Liger");
        req.addHeader("Connection", "keep-alive");
        req.addHeader("X-FB-Client-IP", "True");
        req.addHeader("Accept-Language", "en-US");
        req.addHeader("X-IG-Capabilities","3brTvx0=");
        req.addHeader("X-IG-App-ID", IGConstants.APP_ID);
        req.addHeader("User-Agent", client.getDevice().getUserAgent());
        req.addHeader("X-IG-Connection-Type", "WIFI");
        req.addHeader("X-CM-Bandwidth-KBPS", "-1.000");
        req.addHeader("X-IG-App-Locale", "en_US");
        req.addHeader("X-IG-Device-Locale", "en_US");
        req.addHeader("X-Pigeon-Session-Id", "UFS-" + UUID.randomUUID() + "-1");
        req.addHeader("X-Pigeon-Rawclienttime", System.currentTimeMillis() + "");
        req.addHeader("X-IG-Bandwidth-Speed-KBPS", String.valueOf(getRandomInt(2500000, 3000000) / 1000));
        req.addHeader("X-IG-Bandwidth-TotalBytes-B", String.valueOf(getRandomInt(5000000, 90000000)));
        req.addHeader("X-IG-Bandwidth-TotalTime-MS", String.valueOf(getRandomInt(2000, 9000)));
        req.addHeader("X-IG-Device-ID", client.getGuid());
        req.addHeader("X-IG-Android-ID", client.getDeviceId());
        Optional.ofNullable(client.getAuthorization())
                .ifPresent(s -> req.addHeader("Authorization", s));
        Optional.ofNullable(client.getMid())
                .ifPresent(s -> req.addHeader("X-MID", s));
        if (client.isLoggedIn()) {
            int nextYear = (int) ((System.currentTimeMillis() / 1000) + 31536000);
            int currentTimeSeconds = (int) (System.currentTimeMillis() / 1000);
            req.addHeader("IG-U-DS-USER-ID", client.getGuid());
            req.addHeader("IG-U-IG-DIRECT-REGION-HINT", "LLA" + "," + client.getGuid() + "," + nextYear + ":" + "01f7bae7d8b131877d8e0ae1493252280d72f6d0d554447cb1dc9049b6b2c507c08605b7");
            req.addHeader("IG-U-SHBID", "12695," + client.getGuid() + "," + nextYear + ":" + "01f778d9c9f7546cf3722578fbf9b85143cd6e5132723e5c93f40f55ca0459c8ef8a0d9f");
            req.addHeader("IG-U-SHBTS", currentTimeSeconds + "," + client.getGuid() + "," + nextYear + ":" + "01f7ace11925d0388080078d0282b75b8059844855da27e23c90a362270fddfb3fae7e28");
            req.addHeader("IG-U-RUR", "RVA," + client.getGuid() + "," + nextYear + ":" + "01f7f627f9ae4ce2874b2e04463efdb184340968b1b006fa88cb4cc69a942a04201e544c");
        }

        return req;
    }

    public static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static long getRandomLong(long min, long max) {
        return (long) ((Math.random() * (max - min)) + min);
    }

}
