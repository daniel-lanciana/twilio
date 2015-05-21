package services;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.instance.Account;
import models.Call;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

public class TwilloCallServiceTest extends UnitTest {
    private static final String TEST_NUMBER = "123";
    private static final String TEST_TIMEOUT = "10";
    private TwilioCallService callService;
    private CallFactory mockCallFactory;
    private com.twilio.sdk.resource.instance.Call mockCall;

    @Before
    public void setUp() throws TwilioRestException {
        TwilioRestClient mockClient = mock(TwilioRestClient.class);
        Account mockAccount = mock(Account.class);
        mockCallFactory = mock(CallFactory.class);
        mockCall = mock(com.twilio.sdk.resource.instance.Call.class);

        when(mockClient.getAccount()).thenReturn(mockAccount);
        when(mockAccount.getCallFactory()).thenReturn(mockCallFactory);
        when(mockCallFactory.create(anyMap())).thenReturn(mockCall);
        when(mockCall.getSid()).thenReturn(TEST_NUMBER);

        callService = new TwilioCallService(mockClient);
    }

    @Test
    public void shouldReturnCallObject() {
        assertThat(callService.makeCall(TEST_NUMBER), is(instanceOf(Call.class)));
    }

    @Test
    public void shouldReturnNullOnException() throws TwilioRestException {
        when(mockCallFactory.create(anyMap())).thenThrow(TwilioRestException.class);
        assertThat(callService.makeCall(TEST_NUMBER), is(nullValue()));
    }

    @Test
    public void shouldPassCustomTimeout() throws TwilioRestException {
        callService.makeCall(TEST_NUMBER, TEST_TIMEOUT);
        verify(mockCallFactory).create((Map<String, String>) argThat(hasEntry("Timeout", TEST_TIMEOUT)));
    }

}
