import com.daqsoft.commons.feign.support.SpringMvcFeign;
import com.daqsoft.commons.responseEntity.DataResponse;
import com.daqsoft.user.client.UserLogin;
import com.daqsoft.user.domain.CenterAuthenticationResponse;
import org.junit.Test;

/**
 * Created by ShawnShoper on 2017/5/24.
 */
public class UserCenterTest {
    @Test
    public void connection(){
        UserLogin target = SpringMvcFeign.target(UserLogin.class, "http://192.168.2.16:8090");
        DataResponse<CenterAuthenticationResponse> login = target.login("shawnshoper", "123456", "http://www.baidu.com", "cs315228", "123");
        if(login.getCode()==0){
            CenterAuthenticationResponse data = login.getData();
            System.out.println(data.getToken());
        }
    }
}
