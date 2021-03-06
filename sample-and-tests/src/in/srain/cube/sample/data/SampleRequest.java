package in.srain.cube.sample.data;

import in.srain.cube.request.*;
import in.srain.cube.request.CacheAbleRequest;

/**
 * <p>
 * When requesting data from web API, it is a good practice to encapsulate all the request logic in a module.
 * </p>
 * <p>
 * This class has some methods to show how to encapsulate the web request.
 * <p/>
 * </p>
 * <p/>
 * <p>
 * Every method can take some parameters which are related to the specific business logic, and a
 * <p/>
 * callback which will be applied after the request is finished.
 * </p>
 *
 * @author http://www.liaohuqiu.net
 */
public class SampleRequest {

    /**
     * Show how to encapsulate the calling of a web API by Request
     */
    public static void reverse(final String str, final RequestJsonHandler handler) {
        new SimpleRequest<JsonData>(new BeforeRequestHandler() {

            public <T> void beforeRequest(RequestBase<T> request) {

                String url = "http://cube-server.liaohuqiu.net/api_demo/reverse.php?str=" + str;
                request.getRequestData().setRequestUrl(url);
            }
        }, handler).send();
    }

    /**
     * Show how to encapsulate the calling of a web API by CacheableRequest
     */
    public static void getCacheableRequestSampleData(final String msg, final CacheAbleRequestJsonHandler handler) {

        new CacheAbleRequest<JsonData>(new CacheAbleRequestDefaultPreHandler() {

            public <T> void beforeRequest(SimpleRequest<T> request) {
                String url = "http://cube-server.liaohuqiu.net/api_demo/request.php";
                request.getRequestData().setRequestUrl(url);
            }

            @Override
            public String getSpecificCacheKey() {
                return "sample_data";
            }

            @Override
            public String getInitFileAssertPath() {
                return "request_init/sample_data.json";
            }

            @Override
            public int getCacheTime() {
                return 30;
            }
        }, handler);
    }
}
