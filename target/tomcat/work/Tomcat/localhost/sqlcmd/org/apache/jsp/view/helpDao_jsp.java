/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2017-11-03 13:43:24 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.view;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import sandy.sqlcmd.model.entity.HelpInformation;

public final class helpDao_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <title>Help</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<p>\r\n");
      out.write("    Справка <p>\r\n");
      out.write("<p>\r\n");
      out.write("<table border=\"1\">\r\n");
      out.write("    ");


        List<HelpInformation> list= (List<HelpInformation>) request.getAttribute("help");

        String[][] table;
        int quantityListElements;
        if ( null == list || list.size() == 0) {
            table = new String[][]{{}};
            quantityListElements = 0;
        } else {
            table = new String[list.size()+1][];
            quantityListElements = list.size();
        }

        table[0] = new String[]{"id", "Name", "Description"};
        for(int i = 0; i < quantityListElements; i++) {
            HelpInformation item = list.get(i);
            table[i+1] = new String[]{String.valueOf(item.getId()), item.getCommand().getCommandName()  , item.getDescription()};
        }
        StringBuilder row = new StringBuilder("<tr>");

        if (table != null) for (int i = 0; i < 3; i++) {
            row.append("<th>");
            row.append(table[0][i]);
            row.append("</th>");
        }
        row.append("<th></th>");
        row.append("<th></th></tr>");

        if (table != null) for (int i = 1; i < table.length; i++) {
            row.append("<tr>");
            row.append(String.format("<form id=\"delHelp%d\" action=\"deleteHelp\" method=\"post\"></form>", i));
            row.append(String.format("<form id=\"upHelp%d\" action=\"updateHelp\" method=\"post\"></form>", i));
            row.append("<td>");


            row.append(String.format("<input form=\"upHelp%d\" type=\"text\" name=\"id\" value=\"%s\" hidden/>", i, table[i][0]));
            row.append(String.format("<input form=\"delHelp%d\" type=\"text\" name=\"id\" value=\"%s\" hidden/>",i,  table[i][0]));
            row.append(String.format("<input form=\"default\" type=\"text\" name=\"id\" value=\"%s\" disabled/>",  table[i][0]));
            row.append("</td>");

            row.append("<td>");
            row.append(String.format("<input form=\"upHelp%d\" type=\"text\" name=\"commandName\" value=\"%s\"/>", i, table[i][1]));
            row.append("</td>");

            row.append("<td>");
            row.append(String.format("<input form=\"upHelp%d\" type=\"text\" name=\"description\" value=\"%s\"/>", i, table[i][2]));
            row.append("</td>");

            row.append("<td>");

            row.append(String.format("<input   form=\"delHelp%d\" type=\"submit\" value=\"Delete\"/>", i));
            row.append("</td>");

            row.append("<td>");

            row.append(String.format("<input   form=\"upHelp%d\" type=\"submit\" value=\"Update\"/>", i));
            row.append("</td>");
            row.append("</tr>");


        }
        row.append("<tr><form action=\"insertHelp\" method=\"post\"/>");

        row.append("<td>");
        row.append("</td>");

        row.append("<td>");

        row.append("<input type=\"text\" name=\"commandName\" />");
        row.append("</td>");

        row.append("<td>");

        row.append("<input type=\"text\" name=\"description\" />");
        row.append("</td>");

        row.append("<td><input type=\"submit\" value=\"Insert\"/>");
        row.append("</td>");

        row.append("<td></td>");
        row.append("</form></tr>");
        out.println(row.toString());
    
      out.write("\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
