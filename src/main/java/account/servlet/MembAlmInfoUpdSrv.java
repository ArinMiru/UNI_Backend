/*
 * 2023.05.09 김도원 생성 (API 상세명세서 기반 개발)
 * MembAlmInfoUpdSrv : 알림정보수정
 * 
 */

/*
 * 회원 ID : MEMB_ID
 * 앱 알림 여부 : APP_NOTICE_YN
 * 공지사항 알림 여부 : DEP_NOTICE_YN
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

@WebServlet("/MembAlmInfoUpd")
public class MembAlmInfoUpdSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MembAlmInfoUpdSrv() {
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
		
		String userId="";	// 회원 ID : MEMB_ID
		String appNotice_YN="";	// 앱 알림 여부 : APP_NOTICE_YN
		String depNotice_YN="";	// 공지사항 알림 여부 : DEP_NOTICE_YN
		
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
		
		userId = (jobj.get("MEMB_ID") == null) ? "" : jobj.get("MEMB_ID").toString(); 			 // 회원 ID : MEMB_ID
		appNotice_YN = (jobj.get("APP_NOTICE_YN") == null) ? "" : jobj.get("APP_NOTICE_YN").toString(); // 앱 알림 여부 : APP_NOTICE_YN
		depNotice_YN = (jobj.get("DEP_NOTICE_YN") == null) ? "" : jobj.get("DEP_NOTICE_YN").toString(); // 공지사항 알림 여부 : DEP_NOTICE_YN
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("MEMB_ID", userId);
		param.put("APP_NOTICE_YN", appNotice_YN);
		param.put("DEP_NOTICE_YN", depNotice_YN);
		
		System.out.println("userId :".concat(userId));
		System.out.println("appNotice_YN :".concat(appNotice_YN));
		System.out.println("depNotice_YN :".concat(depNotice_YN));
		
		account.process.MembAlmInfoUpd membalminfoupd = new account.process.MembAlmInfoUpd(param);
		
		resltObj = membalminfoupd.getResult();
	
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
				
	}

}
