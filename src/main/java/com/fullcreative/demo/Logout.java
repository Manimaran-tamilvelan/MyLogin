package com.fullcreative.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Servlet implementation class Logout
 */
@Controller
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse res) {

		HttpSession sess = req.getSession();
		sess.removeAttribute("usr");
		sess.removeAttribute("taskacc1");
		//sess.removeAttribute("userName");

		sess.invalidate();

		// res.getWriter().print("<script>alert(\"SuccessFully LoggedOut\")</script>");
		// res.sendRedirect("index.jsp");
		// RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");

		// rd.include(req, res);

		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("login.jsp");
		String loggedOut = "LoggedOut Successfully";
		modelView.addObject("message", loggedOut);
		
	

		return modelView;

	}

}
