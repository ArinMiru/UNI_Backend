package home.membershipfee.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import home.membershipfee.process.CostBubListTtableOut;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class LoginSrv
 */
@WebServlet("/CostBubListSvc")
public class CostBubListSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CostBubListSrv() {
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
		//doGet(request, response);
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		
		// JSON
		JSONObject resltObj = new JSONObject();
		
		// UTF8
		request.setCharacterEncoding("utf8");
		response.setContentType("application/x-json; charset=UTF-8");
		
		BufferedReader reader = request.getReader();
		while (( line = reader.readLine()) != null )
		{
			jb.append(line);
		}

		JSONObject jobj = JSONObject.fromObject(jb.toString());
		
		// MAP
		Map<String, Object> param = new HashMap<String, Object>();
				
		param.put("MEMB_ID",jobj.get("LOGIN_ID"));
		param.put("MEMB_SC_CD",jobj.get("MEMB_SC_CD"));
		param.put("MEMB_DEP_CD",jobj.get("MEMB_DEP_CD"));
		param.put("TIT_CD",jobj.get("TIT_CD"));
		

		CostBubListTtableOut costTtableOut = new CostBubListTtableOut(param);
		
		resltObj = costTtableOut.getResult();
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
		
		
	}

}
