package cn.tedu.sp11.falback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import cn.tedu.sp01.util.JsonResult;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ItemServiceFallback implements FallbackProvider {

	@Override
	public String getRoute() {
		// TODO Auto-generated method stub
		return "item-service";
		//return "*";         所有后台服务失败，都执行当前奖及代码
		//return "null";
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		// TODO Auto-generated method stub
		return response();
	}

	private ClientHttpResponse response() {
		// TODO Auto-generated method stub
		return new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
			}
			
			@Override
			public InputStream getBody() throws IOException {
				log.info("fallback body");
            	String s = JsonResult.err().msg("后台服务错误").toString();
                return new ByteArrayInputStream(s.getBytes("UTF-8"));
			}
			
			@Override
			public String getStatusText() throws IOException {
				// TODO Auto-generated method stub
				return HttpStatus.OK.getReasonPhrase();
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				// TODO Auto-generated method stub
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				// TODO Auto-generated method stub
				return HttpStatus.OK.value();
			}
			
			@Override
			public void close() {
				// TODO Auto-generated method stub
				
			}
		};
	}

}
