package profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("requestCreator")
public abstract class RequestCreator {
    protected RequestsService requestsService;

    @Autowired
    public RequestCreator(RequestsService requestsService) {
        this.requestsService = requestsService;
    }
}
