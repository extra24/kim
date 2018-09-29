package jdbc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//디비 커넥션 툴을 준비하기 위한 컨텍스트 리스너 클래스
public class DBCPInitListener implements ServletContextListener{

	//시작할 때 디비 커넥션 풀을 셋팅하기
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//서블릿 컨텍스트를 받고 
		ServletContext sc = sce.getServletContext();
		
/*		//서블릿 컨텍스트를 통해서 init파라미터(디비정보가 있는 properties)를 받음
		sc.getInitParameter("poolConfigFile");*/
		
		//파일주소로 파일을 읽어야함 -> 시스템 주소
		String poolConfigFile = sc.getRealPath(sc.getInitParameter("poolConfigFile"));
		
		//파일주소를 프로퍼티스 객체에 파일에 있는 데이타를 넣을 것임
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(poolConfigFile));
			prop.list(System.out);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("not found pooolConfigFile",e);
		} catch (IOException e) {
			throw new RuntimeException("fail to read poolConfigFile",e);
		}
		//jdbc드라이버 로드
		loadJDBCDriver(prop);
		
		//커넥션 풀 초기화
		initConnectionPool(prop);
		
	}

	
	//jdbc드라이버를 로드하는 메소드를 정의
	private void loadJDBCDriver(Properties prop) {
		try {
			Class.forName(prop.getProperty("jdbcDriver")).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private void initConnectionPool(Properties prop) {
		try {
			
		//디비 접속 정보를 스트링으로 받음
		String jdbcUri =prop.getProperty("jdbcUri");
		String dbUser =prop.getProperty("dbUser");
		String dbPwd =prop.getProperty("dbPwd");
	
		//디비 접속정보를 인자로 넣고 커넥션을 만들어 주는 팩토리 객체를 생성
		ConnectionFactory connFactory = new DriverManagerConnectionFactory(jdbcUri,dbUser,dbPwd);
		
		//풀에서 쓸 수 있는 커넥션을 만들어주는 팩토리에 커넥션 팩토리를 넣고 생성
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connFactory,null);
		
		//커넥션이 유효한지 체크하기 위한 쿼리를 지정
		//getProperty의 첫번째 인자는 파일에 정의 되어있는 값이고, 두번째 인자는 없을 시 기본값으로 해줄 것
		poolableConnectionFactory.setValidationQuery(prop.getProperty("validationQuery"));
		
		//커넥션 풀의 설정 정보를 다루는 객체 생성하고 설정정보 셋팅
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setTimeBetweenEvictionRunsMillis(1000L*60L*5L);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setMinIdle(Integer.parseInt(prop.getProperty("minIdle","5")));
		poolConfig.setMaxTotal(Integer.parseInt(prop.getProperty("maxTotal","50"))); //총 커넥션의 갯수
		
		//커넥션 풀을 생성
		GenericObjectPool<PoolableConnection>connectionPool = new GenericObjectPool<>(poolableConnectionFactory,poolConfig);
		poolableConnectionFactory.setPool(connectionPool);
		
		//풀링 드라이버 로드
		Class.forName(prop.getProperty("poolingDriver"));
		PoolingDriver driver = (PoolingDriver)DriverManager.getDriver("jdbc:apache:commons:dbcp:");
		
		//생성한 커넥션 풀을 커넥션 풀 드라이버에 등록
		String poolName = prop.getProperty("poolName");
		driver.registerPool(poolName, connectionPool);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
}





















