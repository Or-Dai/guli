package com.lyf.cmsservice.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/21 10:05
 * @Modified By:
 */
public class VodSdkTest {

    //根据视频Id获取视频地址
//    String accessKeyId = "你的accessKeyId";
//    String accessKeySecret = "你的accessKeySecret";
    String accessKeyId = "LTAI5tE7ZhPpKYrvs8jFmU6m";
    String accessKeySecret = "kITHWJoe85U2E0tYmsM7gkf5fzGnIy";

    /**
     * 根据视频ID获取视频播放凭证
     * @throws ClientException
     */
    @Test
    public void testGetVideoPlayAuth() throws ClientException {

        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

        //创建获取视频地址request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        try {

            //设置请求参数
            //request.setVideoId("视频ID");
            request.setVideoId("c752eecf3477401782e18a32e06044c2");
            //获取请求响应
            response = client.getAcsResponse(request);

            //输出请求结果
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }

        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }


    /**
     * 获取视频播放地址
     * @throws ClientException
     */
    @Test
    public void testGetPlayInfo() throws ClientException {

        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        try {

            //设置请求参数
            //注意：这里只能获取非加密视频的播放地址
            request.setVideoId("b87b780e9573478fa549b91008565958");
            //获取请求响应
            response = client.getAcsResponse(request);

            //输出请求结果
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }

        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
