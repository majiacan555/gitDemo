package LiberyUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    //��������������ݿ�����
    private final static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=Corpus";
    private static final String USER="sa";
    private static final String PASSWORD="123456";
    
    private static Connection conn=null;
    //��̬����飨�������������������ݿ���뾲̬���У�
    static{
        try {
            //1.������������
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //2.������ݿ������
            conn=(Connection)DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //�����ṩһ����������ȡ���ݿ�����
    public static Connection getConnection(){
        return conn;
    }
    
    
    //��������
    public static void main(String[] args) throws Exception{
        
        //3.ͨ�����ݿ�����Ӳ������ݿ⣬ʵ����ɾ�Ĳ�
        Statement stmt = conn.createStatement();
        //ResultSet executeQuery(String sqlString)��ִ�в�ѯ���ݿ��SQL���   ������һ���������ResultSet������
        ResultSet rs = stmt.executeQuery("select data from BasicInfo");
        while(rs.next()){//��������������ݣ��ͻ�ѭ����ӡ����
            System.out.println(rs.getInt("id")+","+rs.getString("name")+","+rs.getInt("age"));
        }
    }

}
