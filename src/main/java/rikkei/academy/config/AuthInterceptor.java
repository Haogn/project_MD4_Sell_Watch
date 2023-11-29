package rikkei.academy.config;

import org.springframework.web.servlet.HandlerInterceptor;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//public class AuthInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession httpSession = request.getSession();
//        UserRegisterDTO admin = (UserRegisterDTO) httpSession.getAttribute("admin");
//        if (admin != null ) { // neu session bang null thi bat dang nhap
//            return true;
//        }
//        response.sendRedirect("/loginAdmin"); // chuyen den trang dang nhap
//        return false;
//    }
//}
public class AuthInterceptor  {

}
