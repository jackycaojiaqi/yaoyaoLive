package com.fubang.video.entity;

/**
 * Created by jacky on 2017/7/19.
 */
public class GetPhoneMsgEntity {


    /**
     * status : fail
     * info : {"success":false,"statusCode":400,"requestData":{"mobile":"+16267807552","text":"【妖妖直播】您的验证码是9781","apikey":"e69c2b00fa5a133d29e8b96da83882af"},"responseData":{"http_status_code":400,"code":23,"msg":"号码归属地不在模板可发送的地区内","detail":"请提交模板可发送国家列表内的号码"},"error":null}
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
         * success : false
         * statusCode : 400
         * requestData : {"mobile":"+16267807552","text":"【妖妖直播】您的验证码是9781","apikey":"e69c2b00fa5a133d29e8b96da83882af"}
         * responseData : {"http_status_code":400,"code":23,"msg":"号码归属地不在模板可发送的地区内","detail":"请提交模板可发送国家列表内的号码"}
         * error : null
         */

        private boolean success;
        private int statusCode;
        private RequestDataBean requestData;
        private ResponseDataBean responseData;
        private String error;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public RequestDataBean getRequestData() {
            return requestData;
        }

        public void setRequestData(RequestDataBean requestData) {
            this.requestData = requestData;
        }

        public ResponseDataBean getResponseData() {
            return responseData;
        }

        public void setResponseData(ResponseDataBean responseData) {
            this.responseData = responseData;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public static class RequestDataBean {
            /**
             * mobile : +16267807552
             * text : 【妖妖直播】您的验证码是9781
             * apikey : e69c2b00fa5a133d29e8b96da83882af
             */

            private String mobile;
            private String text;
            private String apikey;

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getApikey() {
                return apikey;
            }

            public void setApikey(String apikey) {
                this.apikey = apikey;
            }
        }

        public static class ResponseDataBean {
            /**
             * http_status_code : 400
             * code : 23
             * msg : 号码归属地不在模板可发送的地区内
             * detail : 请提交模板可发送国家列表内的号码
             */

            private int http_status_code;
            private int code;
            private String msg;
            private String detail;

            public int getHttp_status_code() {
                return http_status_code;
            }

            public void setHttp_status_code(int http_status_code) {
                this.http_status_code = http_status_code;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }
    }
}
