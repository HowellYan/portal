package cn.com.bestpay.portal.common.service;


import cn.com.bestpay.portal.common.utils.Charset;
import cn.com.bestpay.portal.common.utils.MD5;
import cn.com.bestpay.portal.common.utils.RandomNumber;


public class PwdManager {
	//Session session = HibernateSessionFactory.getSession();

	private static final String ADD_KEY = "aienbiei22&*#*(@ieizewbxwerq?";
	
	//验证支付密码
	public boolean verifyPayPwd(String pwdFromForm, String staffId) throws Exception {
		if (Charset.isEmpty(pwdFromForm)) {
			throw new Exception("支付密码为空值");
		}
		
		String payPwdFromDB = getPayPwdFromDB(staffId);
		
		try {
			if(-1==pwdFromForm.indexOf("_")){//新控件方式
				pwdFromForm = encryptPwd(pwdFromForm,staffId);
				return payPwdFromDB.equals(pwdFromForm);
			}
		} catch (Exception e) {
			return false; 
		}

		//没有控件的时候，传递明文
		return verify(pwdFromForm, payPwdFromDB);
	}
	
	public String getPayPwdFromDB(String staffId) {
		//TSymStaffAttrManger mgrIntance = new TSymStaffAttrManger();
		//TSymStaffAttr staffAttr = mgrIntance.getAllByStaffIdType(staffId);
		//String payPwdFromDB = staffAttr.getAttrValue();
		return "payPwdFromDB";
	}
	
	//验证登录密码
	public boolean verifyLoginPwd(String pwdFromForm, String staffId) throws Exception {
		if (Charset.isEmpty(pwdFromForm)) {
			throw new Exception("登录密码为空值");
		}

		String logPwdFromDB = getLoginPwdFromDB(staffId);

		if(-1==pwdFromForm.indexOf("_")){//新控件方式
			pwdFromForm = encryptPwd(pwdFromForm,staffId);
			return logPwdFromDB.equals(pwdFromForm);
		}
		//没有控件的时候，传递明文
		return verify(pwdFromForm, logPwdFromDB);
	}

	//密码控件传输的字符串pwdFromForm为： id + "_" + md5(pwdFromDB + id + rand)
	private boolean verify(String pwdFromForm, String pwdFromDB) throws Exception {
		String[] ss = pwdFromForm.split("_");

		long id = Long.valueOf(ss[0]).longValue();
		String randFromDB = getRandFromTSymScrand(id);
		String newPwd = MD5.MD5Encode(id + pwdFromDB + randFromDB);

		//删除数据库数据
		delRandFromTSymScrand(id);

		return newPwd.equals(ss[1]);
	}

	public String getLoginPwdFromDB(String staffId) throws Exception {
		//StaffManager m = new StaffManager();
		//TSymStaff staff = m.findByStaffCode(staffId);
		return "staff.getPassword()";
	}

	//生成密码的时候，加密密码明文，得到的密文保存到数据库
	public static String encryptPwd(String pwd) throws Exception {
		String s = pwd + ADD_KEY + pwd;
		return MD5.MD5Encode(MD5.MD5Encode(s));
	}

	//生成密码的时候，添加登录用户名，加密密码明文，得到的密文保存到数据库
	public static String encryptPwd(String pwd, String staffId) throws Exception {
		String s = staffId + pwd+ ADD_KEY;
		return MD5.MD5Encode(MD5.MD5Encode(s));
	}

	public String createSCRand() throws Exception {
		//获得随机码，然后
		String rand = RandomNumber.getrannumber();
		long id = addTSymScrand(rand);
		return "" + id + "_" + rand;		
	}
	
	//记录随机码到T_SYM_SCRAND表
	private long addTSymScrand(String rand) throws Exception {
		//Session session = HibernateSessionFactory.getSession();
		// Long id =(Long)session.createSQLQuery("select SQ_SYM_SCRAND.nextval as id from dual")
         //.addScalar("id",Hibernate.LONG)
        // .uniqueResult();

		 //String sql = "insert into T_SYM_SCRAND (SCRAND_ID, RAND_NUM, CREATE_DATE) "
		//	 + " values (" + id + ", '" + rand + "', sysdate)";
		// executeUpdate(sql);

		//return id;
		return 0;
	}

	private void executeUpdate(String sql) throws Exception {
//		Session session = HibernateSessionFactory.getSession();
//		 Transaction tx=session.beginTransaction();
//		 session.connection().createStatement().executeUpdate(sql);
//		tx.commit();
	}

	private String getRandFromTSymScrand(long id) throws Exception {
//		Session session = HibernateSessionFactory.getSession();
//		String rand = (String) session.createSQLQuery("select RAND_NUM as rand from T_SYM_SCRAND where SCRAND_ID = " + id)
//        .addScalar("rand",Hibernate.STRING).uniqueResult();
		return "rand";
	}

	private void delRandFromTSymScrand(long id) throws Exception {
		String sql = "delete from T_SYM_SCRAND where SCRAND_ID = " + id;
		executeUpdate(sql);
	}

	public void updateOldPwd() throws Exception {

	}

	public static void main(String[] args) throws Exception {

		if(true){
			System.out.println(encryptPwd("123456", "ti02001"));
		}else

		try {
//			String pwd = "123456";
//			String s = pwd  + pwd;
//			System.out.println(MD5.MD5Encode(s));
//			System.out.println(MD5.MD5Encode(MD5.MD5Encode(s)));
//			System.out.println(encryptPwd("123456"));
//			System.out.println(encryptPwd("123456", "yyplml008001"));
			//String staffIds = "yyplml007001, yyplml006007, yyplml008001";
			String staffIds =  "01, "
			 + "1, "
			 + "asda001, "
			 + "en, "
			 + "familymart001, "
			 + "familyoper, "
			 + "hej, "
			 + "jigou01, "
			 + "jj007001, "
			 + "liyu, "
			 + "oper003, "
			 + "qiye01001, "
			 + "qiye02001, "
			 + "qiye03001, "
			 + "qiye04001, "
			 + "qiye05001, "
			 + "qiye06001, "
			 + "qiye06oper, "
			 + "qiye07001, "
			 + "qy01001, "
			 + "qy02001, "
			 + "qyadmin001, "
			 + "qytestkkk001, "
			 + "tisson01001, "
			 + "tisson011001, "
			 + "tisson02001, "
			 + "tisson022001, "
			 + "tisson03001, "
			 + "tisson033001, "
			 + "tisson04001, "
			 + "tisson044001, "
			 + "tisson05001, "
			 + "tisson055001, "
			 + "tisson211001, "
			 + "tisson222001, "
			 + "tt001001, "
			 + "tx006001, "
			 + "tx01003, "
			 + "tx013001, "
			 + "tx02, "
			 + "tx03001, "
			 + "tx04001, "
			 + "tx05001, "
			 + "tx06001, "
			 + "tx83001, "
			 + "tx93001, "
			 + "tx_oper, "
			 + "txadmin001, "
			 + "ty00001, "
			 + "ty01001, "
			 + "ty01oper, "
			 + "ty02001, "
			 + "ty03001, "
			 + "ty04001, "
			 + "ty05001, "
			 + "ty06001, "
			 + "ty07001, "
			 + "ty08001, "
			 + "ty09001, "
			 + "ty10001, "
			 + "ty11001, "
			 + "ty12001, "
			 + "ty13001, "
			 + "ty14001, "
			 + "ty15001, "
			 + "ty16001, "
			 + "ty17001, "
			 + "ty18001, "
			 + "ty19001, "
			 + "ty20001, "
			 + "ty21001, "
			 + "ty22001, "
			 + "xian_qy01001, "
			 + "xian_qy02001, "
			 + "xian_qy03001, "
			 + "xian_qy04001, "
			 + "xishiduo001, "
			 + "xishiduooper, "
			 + "yyplml001001, "

;

			String pswPlainText = "123456";
			String updateSql1 = " update T_SYM_STAFF set password = '#ENCPWD#' where STAFF_ID = '#STAFFID#'; ";
			String updateSql2 = " update T_SYM_STAFFATTR set attr_value = '#ENCPWD#' where STAFF_ID = '#STAFFID#' and attr_type = 'SA0100'; ";
			String sqls1 = "";
			String sqls2 = "";

			String[] staffIdList = staffIds.split(",");
			for (String staffId : staffIdList) {
				staffId = staffId.trim();
				if (Charset.isEmpty(staffId)) continue;

				String encPwd = encryptPwd(pswPlainText, staffId);
				String sql1 = updateSql1.replace("#ENCPWD#", encPwd);
				sql1 = sql1.replace("#STAFFID#", staffId);
				sqls1 = sqls1 + "\n" + sql1;

				String sql2 = updateSql2.replace("#ENCPWD#", encPwd);
				sql2 = sql2.replace("#STAFFID#", staffId);
				sqls2 = sqls2 + "\n" + sql2;
			}

			System.out.println(sqls1);
			System.out.println();
			System.out.println(sqls2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
