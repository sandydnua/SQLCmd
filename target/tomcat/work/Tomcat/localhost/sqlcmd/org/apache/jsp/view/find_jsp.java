/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2017-09-22 21:31:00 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.view;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class find_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <title>Find</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<a href=\"mainpage\">На главную</a><br>\r\n");
      out.write("\r\n");
      out.write("<p>\r\n");
      out.write("Таблица ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${tablename}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\r\n");
      out.write("<p>\r\n");
      out.write("<table border=\"0\">\r\n");
      out.write("    ");

        String[][] table = (String[][])request.getAttribute("table");
        StringBuilder row = new StringBuilder("<tr>");

        if (table != null) for (int i = 0; i < table[0].length; i++) {
            row.append("<th>");
            row.append(table[0][i]);
            row.append("</th>");
        }
        row.append("<th></th>");
        row.append("<th></th></tr>");

        if (table != null) for (int i = 1; i < table.length; i++) {
            row.append(String.format("<tr><form id=\"delete%d\" action=\"delete\" method=\"post\">", i));
            row.append(String.format("<form id=\"update%d\" action=\"update\" method=\"post\">", i));

            for (int j = 0; j < table[i].length; j++) {
                row.append("<td>");
                row.append(String.format("<input form=\"update%d\" type=\"text\" name=\"valuesNew\" value=\"%s\"/>", i, table[i][j]));
                row.append(String.format("<input form=\"update%d\" type=\"text\" name=\"values\" value=\"%s\" hidden/>", i, table[i][j]));
                row.append(String.format("<input form=\"update%d\" type=\"text\" name=\"fields\" value=\"%s\" hidden/>", i, table[0][j]));

                row.append(String.format("<input form=\"delete%d\" type=\"text\" name=\"fields\" value=\"%s\" hidden/>", i, table[0][j]));
                row.append(String.format("<input form=\"delete%d\" type=\"text\" name=\"values\" value=\"%s\" hidden/>", i, table[i][j]));
                row.append("</td>");
            }
            row.append("<td>");
            row.append(String.format("<input  id=\"delete%d\" type=\"text\" name=\"table\" value=\"%s\" hidden/>", i, request.getAttribute("tablename")));
            row.append("<input   type=\"submit\" value=\"Delete\"/></td>");
            row.append("</form></td>");
//            row.append("</tr>");

            row.append("<td>");
            row.append(String.format("<input   type=\"text\" name=\"table\" value=\"%s\" hidden/>", request.getAttribute("tablename")));
            row.append("<input  type=\"submit\" value=\"Update\"/></td>");
            row.append("</form></td></tr>");


        }
        row.append("<tr><form action=\"insert\" method=\"post\">");
        if (table != null) for (int i = 0; i < table[0].length; i++) {
            row.append("<td>");
            row.append(String.format("<input type=\"text\" name=\"values\"/>"));
            row.append(String.format("<input type=\"text\" name=\"fields\" value=\"%s\" hidden/>",table[0][i]));
            row.append("</td>");
        }
        row.append(String.format("<input type=\"text\" name=\"table\" value=\"%s\" hidden/>", request.getAttribute("tablename")));
        row.append("<td><input type=\"submit\" value=\"Insert\"/></td>");
        row.append("<td></td>");
        row.append("</form></tr>");
            
      out.write("\r\n");
      out.write("    ");
      out.print(row.toString());
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