package club.iskyc.lulech.elden.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTest {
	public static Connection conn;
	public static Statement stmt;
	private static String connectUrl = "club.iskyc.lulech.elden.jdbc:oracle:thin:@localhost:1521:orcl";
	private static String userName = "username";
	private static String password = "password";

	public static void main(String[] args) {
		getStatement(connectUrl, userName, password);
	}
	
	public static Statement getStatement(String connectUrl,String userName,String password) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(connectUrl,userName, password);
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return stmt;
	}

	public static void close(){
		if(null != stmt){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(null != conn){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//【故障处理】ORA-28040: No matching authentication protocol
	/** https://www.cnblogs.com/lhrbest/p/6219687.html
	 使用oerr命令来查看，在Oracle 11g下：

	 [oracle@orcltest ~]$ oerr ora 28040

	 28040, 0000, "No matching authentication protocol"

	 // *Cause:  No acceptible authentication protocol for both client and server

	 // *Action: Administrator should set SQLNET_ALLOWED_LOGON_VERSION parameter

	 //          on both client and servers to values that matches the minimum

	 //          version supported in the system.

	 [oracle@orcltest ~]$



	 12c下：

	 oracle@HQsPSL-PSCV-R02:/oracle/app/oracle> oerr ora 28040

	 28040, 0000, "No matching authentication protocol"

	 // *Cause:  There was no acceptable authentication protocol for

	 //          either client or server.

	 // *Action: The administrator should set the values of the

	 //          SQLNET.ALLOWED_LOGON_VERSION_SERVER and

	 //          SQLNET.ALLOWED_LOGON_VERSION_CLIENT parameters, on both the

	 //          client and on the server, to values that match the minimum

	 //          version software supported in the system.

	 //          This error is also raised when the client is authenticating to

	 //          a user account which was created without a verifier suitable for

	 //          the client software version. In this situation, that account's

	 //          password must be reset, in order for the required verifier to



	 可以看到，该参数在11g和12c下的解决方案是不同的。

	 查询了一下参数SQLNET.ALLOWED_LOGON_VERSION，发现该参数在12c中以废弃，而是采用SQLNET.ALLOWED_LOGON_VERSION_CLIENT和SQLNET.ALLOWED_LOGON_VERSION_SERVER代替。

	 客户说是之前碰到了ORA-28040: No matching authentication protocol的错误才加上该参数的。

	 解决：在Oracle用户（不是grid用户）下，将$ORACLE_HOME/network/admin/sqlnet.ora文件原来的SQLNET.ALLOWED_LOGON_VERSION=8注释掉（如果没有sqlnet.ora文件，那么就创建一个），修改为如下的行：

	 SQLNET.ALLOWED_LOGON_VERSION_SERVER=8

	 SQLNET.ALLOWED_LOGON_VERSION_CLIENT=8
	 */
}
