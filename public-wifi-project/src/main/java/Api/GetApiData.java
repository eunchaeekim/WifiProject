package Api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
/*
 * API로부터 데이터를 가져오기 위한 HTTP 요청 및 응답 처리
 */

public class GetApiData {

    final OkHttpClient client = new OkHttpClient();
    
    // 2. HTTP 요청 
    // 주어진 URL로 GET 요청을 보내고, 응답 데이터를 문자열로 반환하는 메서드  (인스턴스 메소드) 
    String run(String url) throws IOException {
        Request request = new Request.Builder()
        		// 요청에 URL 설정
                .url(url)
                .build();
        
        // 3. HTTP 응답 처리
        // GET 요청을 보내고, 응답을 받음  
        try (Response response = client.newCall(request).execute()) {
        	// 응답 본문을 문자열로 변환  
            return response.body().string();
        }
    }
    // 1. URL 생성
    // API에서 데이터를 가져오기 위한 메서드 (정적 메소드) 
    // start와 end 인덱스를 매개변수로 받습니다.
    public static String run (int start, int end) {
        try {
        	// API 엔드포인트를 포함한 기본 URL 생성  
            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
            // API 키를 URL 인코딩하여 추가
            urlBuilder.append("/" + URLEncoder.encode("694165777a6b6f63313132735342684a", "UTF-8"));
            // 데이터 형식을 URL 인코딩하여 추가  
            urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
            // 요청할 데이터 종류를 URL 인코딩하여 추가  
            urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8"));
            // 가져올 데이터의 시작 인덱스를 URL 인코딩하여 추가  
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(start), "UTF-8"));
            // 가져올 데이터의 끝 인덱스를 URL 인코딩하여 추가  
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(end), "UTF-8"));
            // 최종 URL 문자열을 생성
            String url = urlBuilder.toString();
            
            // GetApiData 객체를 생성
            GetApiData getApi = new GetApiData();
           // 생성한 URL로 API 요청
            String response = getApi.run(url);
            // 응답 데이터를 반환
            return response;



        } catch (Exception e) {
            e.printStackTrace();
        }
       // 예외 발생 시 null을 반환
       return null;
    }
}
