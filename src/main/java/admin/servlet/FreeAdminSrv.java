/*
 * 2023.05.09 김도원 <생성>
 * 
 * 2023.05.15 개발 담당 : 안재경
 * 
 * 2023.05.17 파일이름 수정 BudlistAdminTtableOut.java -> FreeAdmin
 * 
 * 게시판모니터링 (자유게시판)
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
import admin.process.FreeAdmin;

/**
 * Servlet implementation class LoginSrv
 */
@WebServlet("/FreeAdminSrv")
public class FreeAdminSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeAdminSrv() {
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
		String content="";
		
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
		
		userId = (jobj.get("NICK_NM") == null) ? "" : jobj.get("NICK_NM").toString();
		userschool = (jobj.get("MEMB_SC_CD") == null) ? "" : jobj.get("MEMB_SC_CD").toString();
		userdep = (jobj.get("MEMB_DEP_CD") == null) ? "" : jobj.get("MEMB_DEP_CD").toString();
		budname = (jobj.get("TIT") == null) ? "" : jobj.get("TIT").toString();
		cretime = (jobj.get("CRE_DAT") == null) ? "" : jobj.get("CRE_DAT").toString();
		content = (jobj.get("CONT") == null) ? "" : jobj.get("CONT").toString();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("NICK_NM", userId);
		param.put("MEMB_SC_CD", userschool);
		param.put("MEMB_DEP_CD", userdep);
		param.put("TIT", budname);
		param.put("CRE_DAT", cretime);
		param.put("CONT", content);
		
		System.out.println("userId :".concat(userId));
		System.out.println("userschool :".concat(userschool));
		System.out.println("userdep :".concat(userdep));
		System.out.println("budname :".concat(budname));
		System.out.println("cretime :".concat(cretime));
		System.out.println("content :".concat(content));
		
		
		FreeAdmin freeadmin = new FreeAdmin(param);
		
		resltObj = freeadmin.getResult();
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
		
		
	}

}
