/*
 * 2023.05.09 김도원 생성 (API 상세명세서 기반 개발)
 * MembInfoUpdSrv : 프로파일 이미지변경
 * 
 */

/*
 * 회원 ID : MEMB_ID
 * 업로드 파일 경로 : PROF_IMG_PATH
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
import account.process.MembInfoUpd;

/**
 * Servlet implementation class LoginSrv
 */

@WebServlet("/MembInfoUpdSvc")
public class MembInfoUpdSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MembInfoUpdSrv() {
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
		String fileBase64="";	// 업로드 파일 경로 : PROF_IMG_PATH
		
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
		
		//userId = (jobj.get("MEMB_ID") == null) ? "" : jobj.get("MEMB_ID").toString(); 			 // 회원 ID : MEMB_ID
		//fileBase64 = (jobj.get("FILE_BASE64") == null) ? "" : jobj.get("FILE_BASE64").toString(); // 업로드 파일 경로 : PROF_IMG_PATH

		
		//Map<String, Object> param = new HashMap<String, Object>();
		
		//param.put("MEMB_ID", userId);
		//param.put("FILE_BASE64", fileBase64);

		
		//System.out.println("userId :".concat(userId));
		//System.out.println("prof_img_path :".concat(prof_img_path));
		
		account.process.MembInfoUpd membinfoupd = new account.process.MembInfoUpd(jobj);
		
		resltObj = membinfoupd.getResult();
	
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
				
	}

}
