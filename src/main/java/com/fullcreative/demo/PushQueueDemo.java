package com.fullcreative.demo;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@Controller
public class PushQueueDemo extends HttpServlet {

	// static final int DELAY_MS = 50000;

	@RequestMapping("/pushqueue")
	public void pushQueue(HttpServletRequest req, HttpServletResponse res) throws IOException {

		String key = req.getParameter("key");

		// we could create any no of queue to execute bulk of task by partitioning,
		// the tasks to the no of queues
		Queue queue = QueueFactory.getDefaultQueue();

		// queue.add(TaskOptions.Builder.withUrl("/path/to/my/worker"));

		// adding task to default queue
		// task is done by a worker
		// we can add any no if task into a queue

		//task1
		queue.add(TaskOptions.Builder.withUrl("/worker").param("key", key));
		//task2
		queue.add(TaskOptions.Builder.withUrl("/worker").param("key", key));

		res.sendRedirect("/pushq.jsp");

	}

}
