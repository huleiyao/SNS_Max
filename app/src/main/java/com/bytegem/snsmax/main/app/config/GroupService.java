package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.group.DATAGroup;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.app.bean.group.LISTDiscusses;
import com.bytegem.snsmax.main.app.bean.group.LISTGroup;
import com.bytegem.snsmax.main.app.bean.user.LISTUser;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupService {

    //创建圈子
    @POST("/groups")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> createGroup(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    //圈子详情
    @GET("/groups/{id}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<DATAGroup> getGroupDetails(@Header("Authorization") String authorization, @Path("id") int groupId);

    //发现页圈子列表
    @GET("/groups")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTGroup> getGroupList(@Header("Authorization") String authorization);

    //获取圈子管理员列表
    @GET("/groups/{id}/managers")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTUser> getGroupManagers(@Header("Authorization") String authorizationid, @Path("id") int id);

    //设置管理员身份
    @PUT("/groups/{groupid}/managers/{userid}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<MBaseBean> putGroupManager(@Header("Authorization") String authorizationid, @Path("groupid") int groupid, @Path("userid") int userid);

    //取消管理员身份
    @DELETE("/groups/{groupid}/managers/{userid}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<MBaseBean> deleteGroupManager(@Header("Authorization") String authorizationid, @Path("groupid") int groupid, @Path("userid") int userid);

    //加入圈子
    @PUT("/user/groups/{groupid}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<MBaseBean> joinGroup(@Header("Authorization") String authorizationid, @Path("groupid") int groupid);

    //退出圈子
    @DELETE("/user/groups/{groupid}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<MBaseBean> exitGroup(@Header("Authorization") String authorizationid, @Path("groupid") int groupid);

    //修改圈子昵称
    @PATCH("/user/groups/{groupid}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<MBaseBean> patchGroupNickName(@Header("Authorization") String authorizationid, @Path("groupid") int groupid, @Query("nickname") String nickname);

    //获取成员列表
    @GET("/groups/{id}/members")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTUser> getGroupMember(@Header("Authorization") String authorizationid, @Path("id") int id, @Query("per_page") String per_page, @Query("page") String page);

    //获取一个圈子讨论列表,第一次
    @GET("/groups/{id}/discusses")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTDiscusses> getGroupDiscusses(@Header("Authorization") String authorizationid, @Path("id") int id);

    //获取一个圈子讨论列表，
    @GET("/groups/{id}/discusses")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTDiscusses> getGroupDiscusses(@Header("Authorization") String authorizationid, @Path("id") int id, @Query("after") int after);

    /*
    发布讨论
    request:
        title<100   desc<199   media*/
    @POST("/groups/{id}/discusses")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> sendDiscusses(@Header("Authorization") String authorization, @Path("id") int id, @Body RequestBody requestBody);

}
