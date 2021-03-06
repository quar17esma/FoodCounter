package com.quar17esma.filter;

import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.Client;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthSecurityFilter implements Filter {
    private List<String> ignoredUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String param = filterConfig.getInitParameter("ignoredUrls");
        if (param != null) {
            String[] ignoredUrlsArray = param.split(",");
            ignoredUrls = Arrays.asList(ignoredUrlsArray);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            String url = request.getRequestURI().replaceAll(request.getContextPath(), "");

            if (!ignoredUrls.contains(url)) {
                String page = ConfigurationManager.getProperty("path.page.index");
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
