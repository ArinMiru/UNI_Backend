/*
 * LonginSrv : 로그인_공통
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
import account.process.LonginTtableOut;

/**
 * Servlet implementation class LoginSrv
 */

@WebServlet("/LoginSvc")
public class LoginSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginSrv() {
        super();
        // TODO Auto-generated constructor stub    
    } 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // WEB GET 호출시
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	// WEB POST 호출시
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		// 
		String userId="";
		String userPasswrd="";
		String tokenId="";
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		
		System.out.println("start");
		
		// JSON 
		JSONObject resltObj = new JSONObject();
		
		System.out.println("start2");
		
		// UTF8
		request.setCharacterEncoding("utf8");
		response.setContentType("application/x-json; charset=UTF-8");
		

		
		BufferedReader reader = request.getReader();
		while (( line = reader.readLine()) != null )
		{
			jb.append(line);
		}

		JSONObject jobj = JSONObject.fromObject(jb.toString());
		

		userId = (jobj.get("LOGIN_ID") == null) ? "" : jobj.get("LOGIN_ID").toString(); // 로그인 ID : LOGIN_ID 
		userPasswrd = (jobj.get("LOGIN_PASS") == null) ? "" : jobj.get("LOGIN_PASS").toString(); // 로그인 비밀번호 : LOGIN_PASS
		tokenId = (jobj.get("TOKEN_ID") == null) ? "" : jobj.get("TOKEN_ID").toString(); // 로그인 비밀번호 : LOGIN_PASS
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("LOGIN_ID", userId);
		param.put("LOGIN_PASS", userPasswrd);
		param.put("TOKEN_ID", tokenId);
		
		System.out.println("userId :".concat(userId));
		System.out.println("userPasswrd :".concat(userPasswrd));
		System.out.println("token_id :".concat(tokenId));
		
		LonginTtableOut longinTtableOut = new LonginTtableOut(param);
		
		resltObj = longinTtableOut.getResult();
		
		
		System.out.println("resltObj :".concat(resltObj.toString()));

		response.getWriter().print(resltObj);
		
		
	}

}
