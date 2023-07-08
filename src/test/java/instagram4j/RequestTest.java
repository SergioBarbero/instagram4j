package instagram4j;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.requests.IGRequest;
import com.github.instagram4j.instagram4j.requests.direct.DirectThreadsBroadcastRequest;
import com.github.instagram4j.instagram4j.requests.qe.QeSyncRequest;
import com.github.instagram4j.instagram4j.requests.users.UsersUsernameInfoRequest;
import com.github.instagram4j.instagram4j.responses.IGResponse;
import com.github.instagram4j.instagram4j.responses.accounts.LoginResponse;
import com.github.instagram4j.instagram4j.responses.users.UserResponse;
import okhttp3.Request;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class RequestTest {
    private class IGTestRequest extends IGRequest<IGResponse> {
        @Override
        public String path() {
            return "test/path/";
        }

        @Override
        public Request formRequest(IGClient client) {
            // dummy
            return null;
        }

        @Override
        public Class<IGResponse> getResponseType() {
            return IGResponse.class;
        }

    }

    @Test
    public void testUrlFormation() {
        IGTestRequest test = new IGTestRequest();
        Assert.assertEquals("https://i.instagram.com/api/v1/test/path/",
                test.formUrl(null).toString());
    }
}
