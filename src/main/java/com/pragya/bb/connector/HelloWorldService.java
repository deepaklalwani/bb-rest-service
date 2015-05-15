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
import                java.io.*;
import			blackboard.data.course.*;
import			blackboard.data.user.*;
import			blackboard.persist.*;
import			blackboard.persist.course.*;
import			blackboard.platform.*;
import			blackboard.cms.filesystem.*;
import			java.util.*;
 
@Path("/hello")
public class HelloWorldService {
 
	@GET
	@Path("/{param}")
	@Produces("APPLICATION/OCTET-STREAM")
	public Response getMsg(@PathParam("param") String fileName)throws IOException   {
 
		String output = "Jersey say : " + fileName ;
		final CSFile csFile = (CSFile)getFile(fileName);

		if (csFile != null) {
			StreamingOutput streamingOutput = new StreamingOutput() {
				@Override
				public void write(OutputStream output) throws IOException,
				WebApplicationException {
					csFile.getFileContent(output);
				}
			};
		ResponseBuilder response = Response.ok(streamingOutput);
//		response.setContentType("APPLICATION/OCTET-STREAM"); 
		response.header("Content-Disposition","attachment; filename=\"" + fileName + "\"");   
		return response.build();
		}


		return null;
 
	}
 



 	private String getPropertyValueFromFile(){
	 	CSContext ctxCS = CSContext.getContext();
	    Course course = new Course();
	    course.setCourseId("PRAGYA-COURSE-2");
	    CSDirectory csd = ctxCS.getCourseDirectory(course);
	    Properties p = new Properties();
	    List files = csd.getDirectoryContents();
	    for (int f = 0; (files != null) && (f < files.size()); f++) {
	    	CSEntry cse = (CSEntry)files.get(f);
		  	
		  	CSFile csFile = (CSFile)cse;
	      	try {
		      ByteArrayOutputStream outFile = new ByteArrayOutputStream();
		      if (csFile != null) {
				csFile.getFileContent(outFile);
		      }
		      InputStream is  = new ByteArrayInputStream(outFile.toByteArray());
		      p.load(is);
		      is.close();
		      outFile.close();
			} 
			catch (Exception e) {
			         //
			}
			break;
		}
		return p.getProperty("contentIDs");
	}

 	private CSEntry getFile(String fileName){
	 	
	 	CSContext ctxCS = CSContext.getContext();
	    Course course = new Course();
	    course.setCourseId("PRAGYA-COURSE-2");
	    CSDirectory csd = ctxCS.getCourseDirectory(course);

	    List files = csd.getDirectoryContents();
	    for (int f = 0; (files != null) && (f < files.size()); f++) {
	    	CSEntry cse = (CSEntry)files.get(f);
			if(fileName.equals(cse.getBaseName())){
				return cse;
			}		  	
		}
		return null;
	}

}