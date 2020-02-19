package com.egovorushkin.telrostestapp.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class CustomRequestCache extends HttpSessionRequestCache {

    // Save unauthenticated requests and redirect the user to the page they were trying
    // to access once they’re logged in.
    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) { //
        if (!SecurityUtils.isFrameworkInternalRequest(request)) {
            super.saveRequest(request, response);
        }
    }

}
