/*
 * LonginSrv : 로그인_공통
 * 
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
import account.process.TokenOut;

/**
 * Servlet implementation class LoginSrv
 */

@WebServlet("/TokenSvc")
public class TokenSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TokenSrv() {
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
		String tokenId="";
		String type="";
		
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
		

		tokenId = (jobj.get("TOKEN_ID") == null) ? "" : jobj.get("TOKEN_ID").toString(); // TOKEN_ID 
		type = (jobj.get("TYPE") == null) ? "" : jobj.get("TYPE").toString(); // TOKEN_ID
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("TOKEN_ID", tokenId);
		param.put("TYPE", type);
		
		
		TokenOut tokenOut = new TokenOut(param);
		
		resltObj = tokenOut.getResult();
		
		
		System.out.println("resltObj :".concat(resltObj.toString()));

		response.getWriter().print(resltObj);
		
		
	}

}
