/*
 * 2023.05.10 김도원 생성 (API 상세명세서 기반 개발)
 * ChkAndCertSrv : 코드검증 및 인증완료
 * 
 */

/*
 * 회원ID : MEMB_ID / userId
 * 인증일련번호 : CERT_SEQ / certSeq
 * 입력코드 : INPUT_CD / inputCd
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

@WebServlet("/ChkAndCertSvc")
public class ChkAndCertSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChkAndCertSrv() {
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
		
		String userId="";	// 회원ID : MEMB_ID
		String certSeq="";	// 인증일련번호 : CERT_SEQ
		String inputCd="";	// 입력코드 : INPUT_CD
		
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
		
		userId = (jobj.get("MEMB_ID") == null) ? "" : jobj.get("MEMB_ID").toString(); // 회원ID : MEMB_ID
		certSeq = (jobj.get("CERT_SEQ") == null) ? "" : jobj.get("CERT_SEQ").toString(); // 인증일련번호 : CERT_SEQ
		inputCd = (jobj.get("INPUT_CD") == null) ? "" : jobj.get("INPUT_CD").toString(); // 입력코드 : INPUT_CD
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("MEMB_ID", userId);
		param.put("CERT_SEQ", certSeq);
		param.put("INPUT_CD", inputCd);
		
		System.out.println("userId :".concat(userId));
		System.out.println("certSeq :".concat(certSeq));
		System.out.println("inputCd :".concat(inputCd));
		
		account.process.ChkAndCert chkandcert = new account.process.ChkAndCert(param);
		
		resltObj = chkandcert.getResult();
	
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		response.getWriter().print(resltObj);
				
	}

}
