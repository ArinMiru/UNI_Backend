/*
 * BudlistAdminTtableOutSrv.java -> 공지사항리스트 명세서를 참조 게시글 관리자 모니터링 페이지 -> 서블릿파일
 * 
 * userId - 사용자 이름
 * userschool - 학교이름
 * userdep - 사용자 학과
 * budname - 게시글 제목
 * cretime - 작성시간
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
import process.BudlistAdminTtableOut;

/**
 * Servlet implementation class LoginSrv
 */
@WebServlet("/BudListAdmin")
public class BudlistAdminTtableOutSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BudlistAdminTtableOutSrv() {
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
		String userschool="";
		String userdep="";
		String budname="";
		String cretime="";
		
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
		userschool = (jobj.get("school") == null) ? "" : jobj.get("school").toString();
		userdep = (jobj.get("userdep") == null) ? "" : jobj.get("userdep").toString();
		budname = (jobj.get("budname") == null) ? "" : jobj.get("budname").toString();
		cretime = (jobj.get("cretime") == null) ? "" : jobj.get("cretime").toString();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("userId", userId);
		param.put("userschool", userschool);
		param.put("userdep", userdep);
		param.put("budname", budname);
		param.put("cretime", cretime);
		
		System.out.println("userId :".concat(userId));
		System.out.println("userschool :".concat(userschool));
		System.out.println("userdep :".concat(userdep));
		System.out.println("budname :".concat(budname));
		System.out.println("cretime :".concat(cretime));
		
		
		UserInfoAdminTtableOut userinfoadminTtableOut = new UserInfoAdminTtableOut(param);
		
		resltObj = userinfoadminTtableOut.getResult();
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
		
		
	}

}
