package rikkei.academy.config;

import org.springframework.web.servlet.HandlerInterceptor;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.dto.response.RespUserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CartInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        RespUserDTO user = (RespUserDTO) httpSession.getAttribute("user");
        if (user != null ) { // neu session bang null thi bat dang nhap
            return true;
        }
        response.sendRedirect("/login"); // chuyen den trang dang nhap
        return false;
    }
}
