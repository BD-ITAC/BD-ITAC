package br.ita.bditac.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import br.ita.bditac.model.AlertaDAO;

@Component
public class LocalHandlerExceptionResolver implements HandlerExceptionResolver, Ordered {

    @SuppressWarnings("unused")
    @Autowired
    private AlertaDAO service;

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println(ex.getMessage());

        return null;
    }

}
