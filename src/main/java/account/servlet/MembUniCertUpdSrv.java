/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)\
 * 
 * 
 */

/*	
 * 회원ID	 : MEMB_ID
 * 학교코드 : MEMB_SC_CD
 * 학과코드 : MEMB_DEP_CD
 * 학번 : MEMB_NUM
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

/**
 * Servlet implementation class LoginSrv
 */
@WebServlet("/MembUniCertUpdSvc")
public class MembUniCertUpdSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MembUniCertUpdSrv() {
        super();  
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
		
		String userId="";	// 회원 ID
		String userSccd=""; // 학교코드
		String userDepcd=""; // 학과코드
		String userNum=""; // 학번
		String userEm=""; // 이메일
		
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
		userSccd = (jobj.get("MEMB_SC_CD") == null) ? "" : jobj.get("MEMB_SC_CD").toString();
		userDepcd = (jobj.get("MEMB_DEP_CD") == null) ? "" : jobj.get("MEMB_DEP_CD").toString();
		userNum = (jobj.get("MEMB_NUM") == null) ? "" : jobj.get("MEMB_NUM").toString();
		userEm = (jobj.get("MEMB_EM") == null) ? "" : jobj.get("MEMB_EM").toString();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("MEMB_ID", userId);
		param.put("MEMB_SC_CD", userSccd);
		param.put("MEMB_DEP_CD", userDepcd);
		param.put("MEMB_NUM", userNum);
		param.put("MEMB_EM", userEm);
		
		System.out.println("userId :".concat(userId)); 		 // 회원ID : MEMB_ID
		System.out.println("userSccd :".concat(userSccd));   // 학교코드 : MEMB_SC_CD
		System.out.println("userDepcd :".concat(userDepcd)); // 학과코드 : MEMB_DEP_CD
		System.out.println("userNum :".concat(userNum));     // 학번 : MEMB_NUM
		System.out.println("userEm :".concat(userEm));       // 이메일 : MEMB_EM
		
		
		account.process.MembUniCertUpd membunicertupd = new account.process.MembUniCertUpd(param);
		
		resltObj = membunicertupd.getResult();
		
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
		
		
	}

}