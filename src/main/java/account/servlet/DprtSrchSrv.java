/*
 * DprtSrchSrv : 학과명 검색
 * 2023.09.03 김도원 생성	 
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
import account.process.DprtSrch;

/**
 * Servlet implementation class LoginSrv
 */

@WebServlet("/DprtSrch")
public class DprtSrchSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DprtSrchSrv() {
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
		int sch_cd = 0;
		String sch_nm="";
		String dprt_cd="";
        StringBuffer jb = new StringBuffer();
        String line = null;

        System.out.println("start");

        JSONObject resltObj = new JSONObject();

        System.out.println("start2");

        request.setCharacterEncoding("utf8");
        response.setContentType("application/x-json; charset=UTF-8");

        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }

        JSONObject jobj = JSONObject.fromObject(jb.toString());

        sch_cd = (jobj.get("SCH_CD") == null) ? 0 : jobj.getInt("SCH_CD");
        sch_nm = (jobj.get("SCH_NM") == null) ? "" : jobj.get("SCH_NM").toString();
        dprt_cd = (jobj.get("DPRT_CD") == null) ? "" : jobj.get("DPRT_CD").toString();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("SCH_CD", sch_cd);
        param.put("SCH_NM", sch_nm);
        param.put("DPRT_CD", dprt_cd);

        System.out.println("sch_cd :".concat(String.valueOf(sch_cd)));
        System.out.println("sch_nm :".concat(String.valueOf(sch_nm)));
        System.out.println("dprt_cd :".concat(String.valueOf(dprt_cd)));
		
		DprtSrch dprtsrch = new DprtSrch(param);
		
		resltObj = dprtsrch.getResult();
		
		System.out.println("resltObj :".concat(resltObj.toString()));

		response.getWriter().print(resltObj);
		
		
	}

}
