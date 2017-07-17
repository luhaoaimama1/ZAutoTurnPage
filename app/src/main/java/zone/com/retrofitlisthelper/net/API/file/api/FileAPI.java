package zone.com.retrofitlisthelper.net.API.file.api;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * [2017] by Zone
 */

public interface FileAPI {

    Call<String> uploadFile(MultipartBody.Part file);

    Call<ResponseBody> downLoad();

    Call<String> uploadFiles(Map<String, RequestBody> maps);
}
