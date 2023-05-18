/*
 * 2023.05.09 김도원 <개발 중지>
 * 
 * 2023.05.15 개발 담당 : 최서은
 * userInfo : 사용자 정보 
 * 
 * 2023.05.18 16:06 최서은
 * 학교 코드대로 분류하도록 수정 
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
		
		String schoolCode="";
		
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
		
		schoolCode = (jobj.get("MEMB_SC_CD") == null) ? "" : jobj.get("MEMB_SC_CD").toString();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("MEMB_SC_CD", schoolCode);
		
		System.out.println("MEMB_SC_CD :".concat(schoolCode));
		
		
		UserInfoAdminTtableOut userinfoadminTtableOut = new UserInfoAdminTtableOut(param);
		
		resltObj = userinfoadminTtableOut.getResult();
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
		
		
	}

}
