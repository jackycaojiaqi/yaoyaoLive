package com.fubang.video.entity;

/**
 * Created by jacky on 2017/7/19.
 */
public class PlayVideoEntity {


    /**
     * status : success
     * info : {"RequestId":"C39A9CB7-F4B6-4474-A8C6-1BF5532C7575","VideoMeta":{"CoverURL":"http://www.oooktv.com/snapshot/257131d9a7714f1f9b46569edbffb0f800001.jpg","Status":"Normal","VideoId":"257131d9a7714f1f9b46569edbffb0f8","Duration":6.1700000762939,"Title":"IMG_0133"},"PlayAuth":"eyJTZWN1cml0eVRva2VuIjoiQ0FJU3VBSjFxNkZ0NUIyeWZTaklxb1NIZU9EbWpyd1p6dkd4ZDFYRHFGSVpOTngrb29IOWpEejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsck1xRnNjYUdCMmRQSnNzdnNVTnFWajRKcExGc3QycDZyOEpqc1ZuamZoZnRsMnBzdlhKYXNEVkVmbkFHSjcwR1VpbSt3WjN4YnpsRHh2QU8zV3VMWnlPajdOK2M5MFRSWFBXUkRGYUJkQlFWRTBBenNvR0xpbnBNZis4UHdURG4zYlFiaTV0cGhFdXNXZDIrSVc1ek1yK2pCL0Nsdy9XeCtRUHRwenRBWlcxRmI0T1dxMXlTTkNveHVkN1c3UGMyU3BMa1hodytieHhrYlpQOUVXczNMamZJU0VJczByZGJiV0VyNFV4ZFZVbFB2VmxJY01lOHFpZ3o4OGZrL2ZJaW9INnh5eEtPZXhvU0NuRlRPaWl1cENlUnI3M2FJNXBKT3VsWUNxVmc0bmZiSU9KbWdjbGNHOGRDZ1JHUU5Nb01VUnNFaVlyVGp6SzJ3RlFQcVFob0d3YWdBRlFJOWVUSDJYd0dQOHZaN1F0bFpEeFJxSTJDQ2xZWW14aHlIUDUwQkY4c0tla3oxc3JPcDBaMVdzMzBSWll2V25CV0x1bUtuUlNvNkJLT25aWVdiNjhpNGNBOVV6aTlZamUxTEE0ZmViTlBLSUtsS1V2WFIxbGlFeGVBM000cTNyeVE0Tk1YbGVkUnFKYSt1MXBBSkFib0o5WXpuT05oWmcwSVJQaVF2RnVmZz09IiwiQXV0aEluZm8iOiJ7XCJFeHBpcmVUaW1lXCI6XCIyMDE3LTA3LTE4VDA2OjU2OjI3WlwiLFwiTWVkaWFJZFwiOlwiMjU3MTMxZDlhNzcxNGYxZjliNDY1NjllZGJmZmIwZjhcIixcIlNpZ25hdHVyZVwiOlwiTGtKSG0wTjVWSzJmcHJ0bHE1d1JwcXBoY3lvPVwifSIsIlZpZGVvTWV0YSI6eyJTdGF0dXMiOiJOb3JtYWwiLCJWaWRlb0lkIjoiMjU3MTMxZDlhNzcxNGYxZjliNDY1NjllZGJmZmIwZjgiLCJUaXRsZSI6IklNR18wMTMzIiwiQ292ZXJVUkwiOiJodHRwOi8vd3d3Lm9vb2t0di5jb20vc25hcHNob3QvMjU3MTMxZDlhNzcxNGYxZjliNDY1NjllZGJmZmIwZjgwMDAwMS5qcGciLCJEdXJhdGlvbiI6Ni4xN30sIkFjY2Vzc0tleUlkIjoiU1RTLktHMjNaUmNjOHkzWnVzckxSTDhTUk1EVm4iLCJQbGF5RG9tYWluIjoid3d3Lm9vb2t0di5jb20iLCJBY2Nlc3NLZXlTZWNyZXQiOiJDQjNWbkZod2lFUlQyc2p3Z01tQnBvSExIeEhOTEN4SjVUOUpjeWRvYlNGViIsIlJlZ2lvbiI6ImNuLXNoYW5naGFpIiwiQ3VzdG9tZXJJZCI6MTUwNzU5MTk4NDg5MjAzMX0="}
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
         * RequestId : C39A9CB7-F4B6-4474-A8C6-1BF5532C7575
         * VideoMeta : {"CoverURL":"http://www.oooktv.com/snapshot/257131d9a7714f1f9b46569edbffb0f800001.jpg","Status":"Normal","VideoId":"257131d9a7714f1f9b46569edbffb0f8","Duration":6.1700000762939,"Title":"IMG_0133"}
         * PlayAuth : eyJTZWN1cml0eVRva2VuIjoiQ0FJU3VBSjFxNkZ0NUIyeWZTaklxb1NIZU9EbWpyd1p6dkd4ZDFYRHFGSVpOTngrb29IOWpEejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsck1xRnNjYUdCMmRQSnNzdnNVTnFWajRKcExGc3QycDZyOEpqc1ZuamZoZnRsMnBzdlhKYXNEVkVmbkFHSjcwR1VpbSt3WjN4YnpsRHh2QU8zV3VMWnlPajdOK2M5MFRSWFBXUkRGYUJkQlFWRTBBenNvR0xpbnBNZis4UHdURG4zYlFiaTV0cGhFdXNXZDIrSVc1ek1yK2pCL0Nsdy9XeCtRUHRwenRBWlcxRmI0T1dxMXlTTkNveHVkN1c3UGMyU3BMa1hodytieHhrYlpQOUVXczNMamZJU0VJczByZGJiV0VyNFV4ZFZVbFB2VmxJY01lOHFpZ3o4OGZrL2ZJaW9INnh5eEtPZXhvU0NuRlRPaWl1cENlUnI3M2FJNXBKT3VsWUNxVmc0bmZiSU9KbWdjbGNHOGRDZ1JHUU5Nb01VUnNFaVlyVGp6SzJ3RlFQcVFob0d3YWdBRlFJOWVUSDJYd0dQOHZaN1F0bFpEeFJxSTJDQ2xZWW14aHlIUDUwQkY4c0tla3oxc3JPcDBaMVdzMzBSWll2V25CV0x1bUtuUlNvNkJLT25aWVdiNjhpNGNBOVV6aTlZamUxTEE0ZmViTlBLSUtsS1V2WFIxbGlFeGVBM000cTNyeVE0Tk1YbGVkUnFKYSt1MXBBSkFib0o5WXpuT05oWmcwSVJQaVF2RnVmZz09IiwiQXV0aEluZm8iOiJ7XCJFeHBpcmVUaW1lXCI6XCIyMDE3LTA3LTE4VDA2OjU2OjI3WlwiLFwiTWVkaWFJZFwiOlwiMjU3MTMxZDlhNzcxNGYxZjliNDY1NjllZGJmZmIwZjhcIixcIlNpZ25hdHVyZVwiOlwiTGtKSG0wTjVWSzJmcHJ0bHE1d1JwcXBoY3lvPVwifSIsIlZpZGVvTWV0YSI6eyJTdGF0dXMiOiJOb3JtYWwiLCJWaWRlb0lkIjoiMjU3MTMxZDlhNzcxNGYxZjliNDY1NjllZGJmZmIwZjgiLCJUaXRsZSI6IklNR18wMTMzIiwiQ292ZXJVUkwiOiJodHRwOi8vd3d3Lm9vb2t0di5jb20vc25hcHNob3QvMjU3MTMxZDlhNzcxNGYxZjliNDY1NjllZGJmZmIwZjgwMDAwMS5qcGciLCJEdXJhdGlvbiI6Ni4xN30sIkFjY2Vzc0tleUlkIjoiU1RTLktHMjNaUmNjOHkzWnVzckxSTDhTUk1EVm4iLCJQbGF5RG9tYWluIjoid3d3Lm9vb2t0di5jb20iLCJBY2Nlc3NLZXlTZWNyZXQiOiJDQjNWbkZod2lFUlQyc2p3Z01tQnBvSExIeEhOTEN4SjVUOUpjeWRvYlNGViIsIlJlZ2lvbiI6ImNuLXNoYW5naGFpIiwiQ3VzdG9tZXJJZCI6MTUwNzU5MTk4NDg5MjAzMX0=
         */

        private String RequestId;
        private VideoMetaBean VideoMeta;
        private String PlayAuth;

        public String getRequestId() {
            return RequestId;
        }

        public void setRequestId(String RequestId) {
            this.RequestId = RequestId;
        }

        public VideoMetaBean getVideoMeta() {
            return VideoMeta;
        }

        public void setVideoMeta(VideoMetaBean VideoMeta) {
            this.VideoMeta = VideoMeta;
        }

        public String getPlayAuth() {
            return PlayAuth;
        }

        public void setPlayAuth(String PlayAuth) {
            this.PlayAuth = PlayAuth;
        }

        public static class VideoMetaBean {
            /**
             * CoverURL : http://www.oooktv.com/snapshot/257131d9a7714f1f9b46569edbffb0f800001.jpg
             * Status : Normal
             * VideoId : 257131d9a7714f1f9b46569edbffb0f8
             * Duration : 6.1700000762939
             * Title : IMG_0133
             */

            private String CoverURL;
            private String Status;
            private String VideoId;
            private double Duration;
            private String Title;

            public String getCoverURL() {
                return CoverURL;
            }

            public void setCoverURL(String CoverURL) {
                this.CoverURL = CoverURL;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public String getVideoId() {
                return VideoId;
            }

            public void setVideoId(String VideoId) {
                this.VideoId = VideoId;
            }

            public double getDuration() {
                return Duration;
            }

            public void setDuration(double Duration) {
                this.Duration = Duration;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }
        }
    }
}
