package chen.example.security.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //默认，直接弹出登陆窗口
        //http.httpBasic();

        // 表单方式，可以指定页面和接口
        http.formLogin()
                .loginPage("/index.html").permitAll()
                .loginProcessingUrl("/login")
                //添加配置  注意：可以通过and（）来添加，也可以再起http。
                .and()
                .logout().permitAll();

        //授权配置 -> 放行路径 -> 所有请求都需要认证
        http.authorizeRequests()
                .antMatchers("/home").permitAll()
                .anyRequest().authenticated();

        //关闭CSRF攻击
        http.csrf().disable();


    }

    //将单个用户设置在内存中。该用户的用户名为“user”，密码为“password”，角色为“USER”。
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }


}
