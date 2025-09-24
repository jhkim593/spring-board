package jhkim593.springboard.common.client;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeignErrorDecoder implements ErrorDecoder {
    private static Logger logger = LoggerFactory.getLogger(FeignErrorDecoder.class);
    @Override
    public Exception decode(String s, Response response) {
        logger.error("feign request fail {}  {}", response.request().url(), response.status());
        return new RetryableException(response.status(), response.reason(), response.request().httpMethod(), (Long) null, response.request());
    }
}
