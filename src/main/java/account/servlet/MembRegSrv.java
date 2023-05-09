/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)\
 * 
 * 
 */
package account.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import account.process.MembReglns;

/**
 * Servlet implementation class LoginSrv
 */
@WebServlet("/MembRegSvc")
public class MembRegSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MembRegSrv() {
        super();
        // TODO Auto-generated constructor stub    
    } 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String userId=""; 		// 회원 ID : MEMB_ID 
		String userPasswrd="";	// 비밀번호 : PASS
		String userName="";	    // 회원명 : MEMB_NM
		
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
		
		userId = (jobj.get("MEMB_ID") == null) ? "" : jobj.get("MEMB_ID").toString();
		userName = (jobj.get("PASS") == null) ? "" : jobj.get("PASS").toString();
		userPasswrd = (jobj.get("MEMB_NM") == null) ? "" : jobj.get("MEMB_NM").toString();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("MEMB_ID", userId);
		param.put("PASS", userPasswrd);
		param.put("MEMB_NM", userName);
		
		System.out.println("userId :".concat(userId));
		System.out.println("userPasswrd :".concat(userPasswrd));
		System.out.println("userName :".concat(userName));
		

		account.process.MembReglns membreglns = new account.process.MembReglns(param);
		
		resltObj = membreglns.getResult();
		
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
		
		
	}

}
