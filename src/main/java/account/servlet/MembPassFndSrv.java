/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)\
 * MembPassFndSrv : 비밀번호 찾기
 * 
 */

/*
 * 회원ID	 : MEMB_ID
 * 이메일 : MEMB_EM
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
import account.process.MembPassFnd;

/**
 * Servlet implementation class LoginSrv
 */

@WebServlet("/MembPassFndSvc")
public class MembPassFndSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MembPassFndSrv() {
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
		
		String userId=""; 		// 회원ID	 : MEMB_ID
		String userEmail="";	// 이메일 : MEMB_EM
		
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
		
		userId = (jobj.get("MEMB_ID") == null) ? "" : jobj.get("MEMB_ID").toString();    // 회원ID : MEMB_ID
		userEmail = (jobj.get("MEMB_EM") == null) ? "" : jobj.get("MEMB_EM").toString(); // 이메일 : MEMB_EM
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("MEMB_ID", userId);
		param.put("MEMB_EM", userEmail);
		
		System.out.println("userId :".concat(userId));
		System.out.println("userEmail :".concat(userEmail));
		
		MembPassFnd membpassfnd = new MembPassFnd(param);
		
		resltObj = membpassfnd.getResult();
	
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
				
	}

}
