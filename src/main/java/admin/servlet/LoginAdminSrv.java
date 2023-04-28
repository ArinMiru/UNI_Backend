package admin.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.process.LoginAdminTtableOut;
import net.sf.json.JSONObject;
import process.LonginTtableOut;

/**
 * Servlet implementation class LoginSrv
 */
@WebServlet("/Loginadmin")
public class LoginAdminSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAdminSrv() {
        super();  
    } 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId="";
		String userType="";
		String userPasswrd="";
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		
		System.out.println("start");
		
		JSONObject resltObj = new JSONObject();
		
		System.out.println("start2");
		
		request.setCharacterEncoding("utf8");
		response.setContentType("application/x-json; charset=UTF-8");
		
		BufferedReader reader = request.getReader();
		while (( line = reader.readLine()) != null )
		{
			jb.append(line);
		}
		JSONObject jobj = JSONObject.fromObject(jb.toString());
		
		userId = (jobj.get("id") == null) ? "" : jobj.get("id").toString();
		userType = (jobj.get("userType") == null) ? "" : jobj.get("userType").toString();
		userPasswrd = (jobj.get("pass") == null) ? "" : jobj.get("pass").toString();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("userId", userId);
		param.put("userType", userType);
		param.put("userPasswrd", userPasswrd);
		
		System.out.println("userId :".concat(userId));
		System.out.println("userPasswrd :".concat(userPasswrd));
		System.out.println("userType :".concat(userType));
		
		LoginAdminTtableOut loginadminTtableOut = new LoginAdminTtableOut(param);
		
		resltObj = loginadminTtableOut.getResult();
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
		
		
	}

}
