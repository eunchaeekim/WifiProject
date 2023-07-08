package Api;

import Dto.Root;
import com.google.gson.Gson;

/*
 * JSON 데이터를 Java 객체로 변환하기 위한 Gson 라이브러리를 사용하여 JSON 파싱을 수행
 */

// Gson은 Java 객체와 JSON 데이터 간의 직렬화와 역직렬화를 지원하는 라이브러리
// 직렬화(Serialization): 객체나 데이터 구조를 일련의 바이트나 텍스트 형식으로 변환하여 저장하거나 전송하기 위한 과정 
// 역직렬화(Deserialization): 직렬화된 데이터를 다시 원래의 객체나 데이터 구조로 변환하는 과정(응용 프로그램에서 사용할 수 있는 형태로 변환)

public class GsonApi {

    public Root gsonData (int start, int end) {
    	// 시작과 끝 값을 인자로 받아 API에서 데이터를 가져온다 
        String jsonData = GetApiData.run(start, end);

        Gson gson = new Gson();
        Root root = gson.fromJson(jsonData, Root.class);
       // jsonData를 Root 클래스의 객체로 변환 (Gson을 사용하여 JSON 데이터를 Java 객체로 역직렬화(=Json 파싱) 
        return root;
    }

}
