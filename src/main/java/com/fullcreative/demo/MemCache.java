package com.fullcreative.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.annotation.Contract;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@Controller
public class MemCache extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@RequestMapping("/cache")
	protected void memCache(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MemcacheService mcs = MemcacheServiceFactory.getMemcacheService();

		// mcs.put("hello", "Hello1");
		 System.out.println(mcs.get("Maran5 Mallow@t15"));
		 System.out.println("Created");

	}
}
