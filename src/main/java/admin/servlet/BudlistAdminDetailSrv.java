/* 재경재경
 * BudlistAdminDetailSrv.java -> 게시판관리자 상세페이지 -> 서블릿파일
 * 
 * -> 서블릿 경로 /BudListAdminDetail
 * 
 * userId - 사용자 아이디
 * username - 사용자 닉네임
 * userschool - 학교 이름
 * userdep - 학과 이름
 * budname - 게시글 제목
 * cretime - 작성시간
 * budcontent - 게시글 내용
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

import net.sf.json.JSONObject;
import admin.process.BudlistAdminDetail;

/**
 * Servlet implementation class LoginSrv
 */
@WebServlet("/BudListAdminDetail")
public class BudlistAdminDetailSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BudlistAdminDetailSrv() {
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
		String username="";
		String userschool="";
		String userdep="";
		String budname="";
		String cretime="";
		String budcontent="";
		
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
		username = (jobj.get("username") == null) ? "" : jobj.get("username").toString();
		userschool = (jobj.get("school") == null) ? "" : jobj.get("school").toString();
		userdep = (jobj.get("userdep") == null) ? "" : jobj.get("userdep").toString();
		budname = (jobj.get("budname") == null) ? "" : jobj.get("budname").toString();
		cretime = (jobj.get("cretime") == null) ? "" : jobj.get("cretime").toString();
		budcontent = (jobj.get("budcontent") == null) ? "" : jobj.get("budcontent").toString();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("userId", userId);
		param.put("username", username);
		param.put("userschool", userschool);
		param.put("userdep", userdep);
		param.put("budname", budname);
		param.put("cretime", cretime);
		param.put("budcontent", budcontent);
		
		System.out.println("userId :".concat(userId));
		System.out.println("username :".concat(username));
		System.out.println("userschool :".concat(userschool));
		System.out.println("userdep :".concat(userdep));
		System.out.println("budname :".concat(budname));
		System.out.println("cretime :".concat(cretime));
		System.out.println("budcontent :".concat(budcontent));
		
		
		BudlistAdminDetail budlistAdminDetail= new BudlistAdminDetail(param);
		
		resltObj = budlistAdminDetail.getResult();
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
		
		
	}

}
