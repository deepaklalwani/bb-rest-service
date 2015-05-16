# bb-rest-service
https://104.130.69.172:8443/webapps/ps-connector-BBLEARN/rest/hello/Next-Fix
Where ps- is the id of bb defined in bb-menifest file
      connector- is handle defined in menifest file
      -BBLEARN is installation information of BB or use bb_bb60
      rest is sub path defined in the web.xml
      hello is resourse. defined in java 


Access using the rest client by adding custom header (get the cookie value from browser active session).

name : Cookie
value : web_client_cache_guid=a01c71f5-44e2-4dfb-bdff-48e27ce47bd9; xythosdrive=0; JSESSIONID=AD80963A34386C16342A33C68373D858; session_id=616210E9C5B38C27D09D5B6D934C855B; s_session_id=C38B82F174FB939D47EBE20B11E68302

Code for using the token based auth
https://github.com/eveoh/blackboard-mytimetable


