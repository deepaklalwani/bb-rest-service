package com.pragya.bb.connector;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.WebApplicationException;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import blackboard.platform.filesystem.*;
import blackboard.platform.session.BbSession;
import blackboard.platform.session.BbSessionManagerService;
import blackboard.platform.session.UserContext;
import javax.ws.rs.core.Context;
import blackboard.data.user.*;
import blackboard.persist.course.CourseDbLoader;
import blackboard.data.navigation.CourseToc;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.navigation.CourseTocDbLoader;
import blackboard.persist.Id;


import                java.io.*;
import			blackboard.data.course.*;
import			blackboard.data.user.*;
import			blackboard.persist.*;
import			blackboard.persist.course.*;
import			blackboard.platform.*;
import			blackboard.cms.filesystem.*;
import			java.util.*;
import javax.servlet.http.HttpServletRequest;
 
@Path("/course")
public class CourseService {
 
	@GET
//	@Produces("APPLICATION/JSON")
	public String getUserCourses(@Context HttpServletRequest request)  {
 		List<Course> courses = new ArrayList<Course>();
 		StringBuffer courseJSON  = new StringBuffer();
 		try{
			UserContext userCtx = null;
			// Create a BbSessionManagerService to obtain a session object
			BbSessionManagerService sessionService = 
						BbServiceManager.getSessionManagerService();
			//Create a BbSession object 
			BbSession bbSession = sessionService.getSession(request);
			//Obtain a UserContext object to obtain the current user's information 
			userCtx = bbSession.lookupUserContext( request);
			User user = userCtx.getUser();
			courses = CourseDbLoader.Default.getInstance().loadByUserIdAndCourseMembershipRole(user.getId(),CourseMembership.Role.INSTRUCTOR);
		}catch(Exception e){
			// Unable to get courses for user....
		}
		courseJSON.append("[");
		int size = courses.size();
		int counter = 0;
		for(Course course:courses){
			counter++;
			courseJSON.append("{ ");
			courseJSON.append("\"id\":\"").append(course.getId().toExternalString()).append("\",");
			courseJSON.append("\"courseId\":\"").append(course.getCourseId()).append("\",");
			courseJSON.append("\"title\":\"").append(course.getTitle()).append("\",");
			courseJSON.append("\"description\":\"").append(course.getDescription()).append("\"");
			courseJSON.append("}");
			if(counter!=size){
				courseJSON.append(",");
			}
		}
		courseJSON.append("]");

		return courseJSON.toString();
	}

	@GET
	@Path("/{bbCourseId}/toc")
	public String getCourseTOC(@PathParam("bbCourseId") String bbCourseId, @Context HttpServletRequest request)  {
 		List<CourseToc> courseTOCs = new ArrayList<CourseToc>();
 		StringBuffer courseTOCJSON  = new StringBuffer();
 		BbPersistenceManager bbPm = new BbServiceManager().getPersistenceService().getDbPersistenceManager();

 		try{
			UserContext userCtx = null;
			// Create a BbSessionManagerService to obtain a session object
			BbSessionManagerService sessionService = 
						BbServiceManager.getSessionManagerService();
			//Create a BbSession object 
			BbSession bbSession = sessionService.getSession(request);
			//Obtain a UserContext object to obtain the current user's information 
			userCtx = bbSession.lookupUserContext( request);
			User user = userCtx.getUser();
			Id id = bbPm.generateId(Course.DATA_TYPE, bbCourseId);
			CourseTocDbLoader ctl = (CourseTocDbLoader) bbPm.getLoader(CourseTocDbLoader.TYPE);
			courseTOCs = ctl.loadByCourseId(id);

			courseTOCJSON.append("[");
			int size = courseTOCs.size();
			int counter = 0;
			for(CourseToc toc:courseTOCs){
				counter++;
				courseTOCJSON.append("{ ");
				courseTOCJSON.append("\"id\":\"").append(toc.getId().toExternalString()).append("\",");
				courseTOCJSON.append("\"contentId\":\"").append(toc.getContentId().toExternalString()).append("\",");
				courseTOCJSON.append("\"title\":\"").append(toc.getLabel()).append("\",");
				courseTOCJSON.append("\"enabled\":\"").append(toc.getIsEnabled()).append("\",");
				courseTOCJSON.append("\"isEntryPoint\":\"").append(toc.getIsEntryPoint()).append("\",");
				courseTOCJSON.append("\"position\":\"").append(toc.getPosition()).append("\",");
				courseTOCJSON.append("\"url\":\"").append(toc.getUrl()).append("\",");
				courseTOCJSON.append("\"targetType\":\"").append(toc.getTargetType().toFieldName()).append("\",");		
				courseTOCJSON.append("\"type\":\"").append(toc.getDataType().toString()).append("\"");
				courseTOCJSON.append("}");
				if(counter!=size){
					courseTOCJSON.append(",");
				}
			}
			courseTOCJSON.append("]");
		}catch(Exception e){
			// Unable to get courses for user....
		}
		return courseTOCJSON.toString();
	}

}