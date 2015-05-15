<%@page import="blackboard.platform.filesystem.*,
                java.io.*,
			com.ilrn.session.services.*,
			blackboard.data.course.*,
			blackboard.data.user.*,
			blackboard.persist.*,
			blackboard.persist.course.*,
			blackboard.platform.*,
			blackboard.cms.filesystem.*,
			java.util.*"                
%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<bbNG:genericPage>
<bbNG:pageHeader>
<bbNG:pageTitleBar title="My Page Title"></bbNG:pageTitleBar>
<%
    CSContext ctxCS = CSContext.getContext();
    Course course = new Course();
    course.setCourseId("PRAGYA-COURSE-2");
    CSDirectory csd = ctxCS.getCourseDirectory(course);
    List files = csd.getDirectoryContents();
    for (int f = 0; (files != null) && (f < files.size()); f++) {
    CSEntry cse = (CSEntry)files.get(f);
	  Properties p = new Properties();
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
	  } catch (Exception e) {
         //
    	}


//	response.setContentType("APPLICATION/OCTET-STREAM");   
//    response.setHeader("Content-Disposition","attachment; filename=\"" + cse.getBaseName() + "\"");   



%>
    	<%=cse.getBaseName()%>   ---- <%=p.getProperty("contentIDs")%>
<%
   break;
	}

  
String name = "Deepak";
%>
<%=files%>  <%=files.size()%>
</bbNG:pageHeader>
</bbNG:genericPage>