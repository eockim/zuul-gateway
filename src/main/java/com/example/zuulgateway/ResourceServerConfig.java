package com.example.zuulgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatcher(new RequestHeaderRequestMatcher("Authorization"))
                .authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('member.admin')");
    }

//    @Override
//    public void configure(ResourceServerSecurityConfigurer config) {
//        config.tokenServices(tokenServices());
//        config.resourceId("cmi");
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("secret");
//        return converter;
//    }
//
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore());
//        return defaultTokenServices;
//    }

    @Autowired
    private ResourceServerProperties sso;

    @Bean
    public MyUserInfoTokenServices myUserInfoTokenServices() {
        return new MyUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
    }


}
