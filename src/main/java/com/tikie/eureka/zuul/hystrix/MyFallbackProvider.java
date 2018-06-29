/**
 * 
 * 项目名称：tikie-eureka-zuul-client
 * 创建日期：2018年6月28日
 * 修改历史：
 * 		1、[2018年6月28日]创建文件 by zhaocs
 */
package com.tikie.eureka.zuul.hystrix;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * @author zhaocs
 * 自定义熔断器
 */
@Component
public class MyFallbackProvider implements FallbackProvider {

    /* (non-Javadoc)
     * @see org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider#fallbackResponse(java.lang.String, java.lang.Throwable)
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        System.out.println("route:"+route);
        System.out.println("exception:"+cause.getMessage());
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "ok";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("oooops!error,i'm the fallback.".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

    /* (non-Javadoc)
     * @see org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider#getRoute()
     */
    @Override
    public String getRoute() {
        return "eureka-provider";
    }

}
