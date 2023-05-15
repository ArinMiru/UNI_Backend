/*
 * 2023.05.09 김도원 <개발 중지>
 * 
 * 2023.05.15 개발 담당 : 안재경
 * userInfo : 사용자 정보 
 */

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

import admin.process.UserInfoAdminTtableOut;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class LoginSrv
 */
@WebServlet("/UserInfoAdmin")
public class UserInfoAdminSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoAdminSrv() {
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
		String userschool="";
		String usersdep="";
		
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
		userschool = (jobj.get("school") == null) ? "" : jobj.get("school").toString();
		usersdep = (jobj.get("usersdep") == null) ? "" : jobj.get("usersdep").toString();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("userId", userId);
		param.put("userType", userType);
		param.put("userschool", userschool);
		param.put("usersdep", usersdep);
		
		System.out.println("userId :".concat(userId));
		System.out.println("userschool :".concat(userschool));
		System.out.println("usersdep :".concat(usersdep));
		
		
		UserInfoAdminTtableOut userinfoadminTtableOut = new UserInfoAdminTtableOut(param);
		
		resltObj = userinfoadminTtableOut.getResult();
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
		
		
	}

}
