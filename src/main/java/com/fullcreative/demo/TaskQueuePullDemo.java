package com.fullcreative.demo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskQueuePb.TaskQueueQueryTasksResponse.Task;

@Controller
public class TaskQueuePullDemo extends HttpServlet {

	private static final Logger log = Logger.getLogger(TaskQueuePullDemo.class.getName());

	@RequestMapping("/pullqueueadd")
	public void taskqueue(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String newTasks =  req.getParameter("add");
		int taskCount = Integer.valueOf(newTasks);
		
		Queue q = QueueFactory.getQueue("myPullQueue");

		for(int index=0;index<taskCount;index++) {
		TaskOptions taskoption = TaskOptions.Builder.withMethod(TaskOptions.Method.PULL).payload(newTasks);

		q.add(taskoption);
		}
		// System.out.println(q.getQueueName()); //myPullQueue
		// q.deleteTask(userName);

		

		String message = "Number of new tasks added are: " + newTasks;

		req.setAttribute("message", message);
		req.getRequestDispatcher("pullq.jsp").forward(req, res);

		// System.out.println(tasks);
		/**
		 * if (leasedTasks.size() == 0) { System.out.println("No tasks to lease and
		 * hence exiting"); }
		 * 
		 * for (TaskHandle leasedTask : leasedTasks) { System.out.println(leasedTask);
		 * 
		 * }
		 */
		// 2 is the no of task that is inserted first
		// 60 sec is given to the first 2 tasks
	}

		
	@RequestMapping("/pullqueuelease")
	public void pullqueuelease(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int leaseTasks =  Integer.valueOf(req.getParameter("lease"));
		
		
		Queue q = QueueFactory.getQueue("myPullQueue");


		//we need to give enough time for processing of each task
		List<TaskHandle> leasedTasks = q.leaseTasks(1, TimeUnit.SECONDS, leaseTasks);

		String message = processTask(leasedTasks, q);

		req.setAttribute("message", message);
		req.getRequestDispatcher("pullq.jsp").forward(req, res);

		
	}
	
	private String processTask(List<TaskHandle> leasedTasks, Queue q) {
		// TODO Auto-generated method stub
		String message;
		int deletedTask = 0;

		for (TaskHandle task : leasedTasks) {
			// String payload = new String(task.getPayload());
			String output = String.format("Processing: taskName='%s'  payload='%s'", task.getName().toString(),
					task.getPayload().toString());
			log.info(output.toString());
			
			output = String.format("Deleting taskName='%s'", task.getName().toString());
			log.info(output.toString());

			q.deleteTask(task);

			deletedTask++;
		}

		if (deletedTask > 0) {
			message = "Processed and deleted " + deletedTask + " tasks from the " + " task queue.";
		} else {
			message = "Task Queue has no tasks available for lease.";
		}

		return message;

	}


}
