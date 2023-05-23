/*
 * 2023.05.17 안재경 <생성>
 * 
 * 게시판 모니터링 (장터 계시판)
 * 
 * 2023.05.19 안재경 <수정>
 * 서블릿파일 미수정 process 내 장터게시판 모니터링 배열부분만 수정됨.
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
import admin.process.MarketAdmin;

/**
 * Servlet implementation class LoginSrv
 */
@WebServlet("/MarketAdminSrv")
public class MarketAdminSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MarketAdminSrv() {
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
		
		
		
		
		MarketAdmin marketadmin = new MarketAdmin(param);
		
		resltObj = marketadmin.getResult();
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
		
		
	}

}
