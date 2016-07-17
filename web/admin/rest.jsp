<%@page import="com.pj.actions.rest.IncludableRestAction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String rest = request.getParameter("rest");
    if(rest != null){
        IncludableRestAction action = new IncludableRestAction();
        action.executeRestURI(rest, request);
    }
%>