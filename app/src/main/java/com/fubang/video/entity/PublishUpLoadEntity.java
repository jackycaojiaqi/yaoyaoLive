package com.fubang.video.entity;

/**
 * Created by jacky on 2017/7/19.
 */
public class PublishUpLoadEntity {


    /**
     * status : success
     * info : {"UploadAddress":"eyJFbmRwb2ludCI6Imh0dHBzOi8vb3NzLWNuLXNoYW5naGFpLmFsaXl1bmNzLmNvbSIsIkJ1Y2tldCI6ImluLTIwMTcwNzE3MTgxMDM5OTc0LWJ1cWdwM3MzNjYiLCJGaWxlTmFtZSI6InZpZGVvLzQyOTYzRTQyLTE1RDU4OEZEOTk4LTE1MDctNTkxOS04NDgtOTIwMzEubXA0In0=","VideoId":"0af12eb2a261483198cd1915e7429458","RequestId":"716B8A29-B561-4FC1-8924-AFDB0161E26F","UploadAuth":"eyJTZWN1cml0eVRva2VuIjoiQ0FJU3NnUjFxNkZ0NUIyeWZTaklwb0wvQjkzaW1PdEQvL1hjY1ZYYTExWS9TUFpFdnB6dXBqejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsclVxRnNjYUdCMmRQSnNzdnNVTnFWajRKcGZadjh1ODRhQURpNUNqUWNscitMQkduNTI4V2Y3d2FmK0FVSFBIQ1RtZDVLRVpvOWJUY1RHbFFDWVRXUC90b0pWN2I5TVJjeENsWkQ1ZGZybC9MUmRqcjhsb21CS3pVUEcyS1V6U24zYjNCa2hsc1JZZThtUms4dmFhMzhtQTZ3TFhsa0hIMHVzU3JvaVRVWisvZEp0Tk9wWmpVdDZwMGNscmNyYkF5Q2RLOXlWUzhLQi9nTTRpL2l6YzdPeUZCMTVZN3kyUEtmYTJpTnAwTjExRWZxdzlFcUo4cGVMZ3Z2cGdxNDZna0ovc21UMUtPUGxSWGpqU1laMmszTXJjRWZtMUM4NDljci8zV1FUS3ljdmRINVB5cVI4QmZIWUhOUnREWWNZY01udDNBQkJPT2piQk1mMkQrVXZXUnhxblY2bWQyYnNxN0lKeHlGems1cmp2SUVPVUVaR1l5am9nTTRVZ2REazhWWGdzMEhmbWI3VU5maUZWYmxwakhNejFkNGhvYXcxRW9xNmF0Vy9rWHlaN25IWk1wSys4TmJHRXU2WmFNOTJtQWNvWmlOVkVOTTBlNkRwN0VBaXlRYSszaFY5UGJ6YzdIL0ZJMGFYZ1B0ampzdTdibThqUE82dWZWOXhhNmdWMFhXR0EvMi9VRkNzUGFIQ2d2NHhtT1FiSXZwV0xsUGVWdFpaNFRDbFd2NEFBVmwvYUlZczE4UVU4di9QdnNDNytxckd6RGlxWW1VTWo0Y1RkOG9WMTdrOXBRK3lwbWVmNnZFU0Y1aUxOUC9kaHlKYUtCall4R1VYdUt5Wmp2Tm1XaDJNYW9TNEduMXE5TWcwazkwK3IwbUs5YkR5MUpUcC9MYkV2R29BQmo5ZmE4MThJMVhIejhUdjhVN3dBU08xSjBCM0t6VENpRGl3amVoVEFYZFRaVUVrRHRUT0NLVEIxaVdiRDN6MUNkZ1N1S2lwam1uTFhUYjluTjhGTE5EV3NyM1Vzbm5EL1loZUJLbHVENkl6L1ExQ2E5OEt5U0lMZVdKR3hFQjJVdi9DRTNHOGk2UWFPUU5kYTUyOHoxT09vYWx3b0JCUEV4TW9lTXVvNGFvMD0iLCJBY2Nlc3NLZXlJZCI6IlNUUy5HQUpMZ1Z1NGJINzdzc2szVmpEeWhRWUVEIiwiQWNjZXNzS2V5U2VjcmV0IjoiQVZqOEJ5RlR4Y0RmS2RRc0c1dTdDOUt3RmJwOFlLV3paTDlXaWpBaUdoYjciLCJFeHBpcmF0aW9uIjoiMzYwMCJ9"}
     */

    private String status;
    private InfoBean info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * UploadAddress : eyJFbmRwb2ludCI6Imh0dHBzOi8vb3NzLWNuLXNoYW5naGFpLmFsaXl1bmNzLmNvbSIsIkJ1Y2tldCI6ImluLTIwMTcwNzE3MTgxMDM5OTc0LWJ1cWdwM3MzNjYiLCJGaWxlTmFtZSI6InZpZGVvLzQyOTYzRTQyLTE1RDU4OEZEOTk4LTE1MDctNTkxOS04NDgtOTIwMzEubXA0In0=
         * VideoId : 0af12eb2a261483198cd1915e7429458
         * RequestId : 716B8A29-B561-4FC1-8924-AFDB0161E26F
         * UploadAuth : eyJTZWN1cml0eVRva2VuIjoiQ0FJU3NnUjFxNkZ0NUIyeWZTaklwb0wvQjkzaW1PdEQvL1hjY1ZYYTExWS9TUFpFdnB6dXBqejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsclVxRnNjYUdCMmRQSnNzdnNVTnFWajRKcGZadjh1ODRhQURpNUNqUWNscitMQkduNTI4V2Y3d2FmK0FVSFBIQ1RtZDVLRVpvOWJUY1RHbFFDWVRXUC90b0pWN2I5TVJjeENsWkQ1ZGZybC9MUmRqcjhsb21CS3pVUEcyS1V6U24zYjNCa2hsc1JZZThtUms4dmFhMzhtQTZ3TFhsa0hIMHVzU3JvaVRVWisvZEp0Tk9wWmpVdDZwMGNscmNyYkF5Q2RLOXlWUzhLQi9nTTRpL2l6YzdPeUZCMTVZN3kyUEtmYTJpTnAwTjExRWZxdzlFcUo4cGVMZ3Z2cGdxNDZna0ovc21UMUtPUGxSWGpqU1laMmszTXJjRWZtMUM4NDljci8zV1FUS3ljdmRINVB5cVI4QmZIWUhOUnREWWNZY01udDNBQkJPT2piQk1mMkQrVXZXUnhxblY2bWQyYnNxN0lKeHlGems1cmp2SUVPVUVaR1l5am9nTTRVZ2REazhWWGdzMEhmbWI3VU5maUZWYmxwakhNejFkNGhvYXcxRW9xNmF0Vy9rWHlaN25IWk1wSys4TmJHRXU2WmFNOTJtQWNvWmlOVkVOTTBlNkRwN0VBaXlRYSszaFY5UGJ6YzdIL0ZJMGFYZ1B0ampzdTdibThqUE82dWZWOXhhNmdWMFhXR0EvMi9VRkNzUGFIQ2d2NHhtT1FiSXZwV0xsUGVWdFpaNFRDbFd2NEFBVmwvYUlZczE4UVU4di9QdnNDNytxckd6RGlxWW1VTWo0Y1RkOG9WMTdrOXBRK3lwbWVmNnZFU0Y1aUxOUC9kaHlKYUtCall4R1VYdUt5Wmp2Tm1XaDJNYW9TNEduMXE5TWcwazkwK3IwbUs5YkR5MUpUcC9MYkV2R29BQmo5ZmE4MThJMVhIejhUdjhVN3dBU08xSjBCM0t6VENpRGl3amVoVEFYZFRaVUVrRHRUT0NLVEIxaVdiRDN6MUNkZ1N1S2lwam1uTFhUYjluTjhGTE5EV3NyM1Vzbm5EL1loZUJLbHVENkl6L1ExQ2E5OEt5U0lMZVdKR3hFQjJVdi9DRTNHOGk2UWFPUU5kYTUyOHoxT09vYWx3b0JCUEV4TW9lTXVvNGFvMD0iLCJBY2Nlc3NLZXlJZCI6IlNUUy5HQUpMZ1Z1NGJINzdzc2szVmpEeWhRWUVEIiwiQWNjZXNzS2V5U2VjcmV0IjoiQVZqOEJ5RlR4Y0RmS2RRc0c1dTdDOUt3RmJwOFlLV3paTDlXaWpBaUdoYjciLCJFeHBpcmF0aW9uIjoiMzYwMCJ9
         */

        private String UploadAddress;
        private String VideoId;
        private String RequestId;
        private String UploadAuth;

        public String getUploadAddress() {
            return UploadAddress;
        }

        public void setUploadAddress(String UploadAddress) {
            this.UploadAddress = UploadAddress;
        }

        public String getVideoId() {
            return VideoId;
        }

        public void setVideoId(String VideoId) {
            this.VideoId = VideoId;
        }

        public String getRequestId() {
            return RequestId;
        }

        public void setRequestId(String RequestId) {
            this.RequestId = RequestId;
        }

        public String getUploadAuth() {
            return UploadAuth;
        }

        public void setUploadAuth(String UploadAuth) {
            this.UploadAuth = UploadAuth;
        }
    }
}
