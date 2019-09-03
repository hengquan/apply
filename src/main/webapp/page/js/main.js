var _url = location.href.split('#')[0];

$(function(){
   weixinShare()
});
function signUp() {
  window.location.href="sign.html";
}

function weixinShare() {
  $.ajax({
      type:"GET",
      url:"../api/getWxData",
      dataType:"json", 
      data: {url: _url},
      success:function(data){
          console.log('ajax success: ', data)
      var _data = data.data
      if(JSON.stringify(_data) != '{}'){
        wx.config({
          debug:false,// 是否开启调试模式
          appId:_data.appId,//appid
          timestamp:_data.timestamp,// 时间戳
          nonceStr:_data.nonceStr,// 随机字符串
          signature:_data.signature,// 签名
          jsApiList:[
            'onMenuShareTimeline',   
            'onMenuShareAppMessage',   
            'onMenuShareQQ', 
            'onMenuShareQZone'
          ]// 需要使用的JS接口列表
        })

        wx.ready(function(){
          share();
        })
        wx.error(function(res){
              // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
            console.log("wx.error", res);
        });
      }
     },
     error:function(e){
       console.log("ajax error："+ e.status);
     }
  });
}

// method 判断是否为微信浏览器
let isWeixin = () => {
  let ua = window.navigator.userAgent.toLowerCase();
  return /micromessenger/.test(ua)
}

function share() {
  console.log("share")
  var title="同方威视",
  desc = "高能产品本部2019年您产品推介会",
  link = "http://ws.ruikj.cn/apply/page/loading.html",
  imgUrl = "http://ws.ruikj.cn/apply/page/images/page3-checkbox";

  // 分享给好友
  wx.onMenuShareAppMessage({
    title:title,// 分享标题
    desc:desc,// 分享描述
    link:link,// 分享链接
    imgUrl:imgUrl,// 分享图标
    success: function(){
      doShareDone()
    },
    cancel:function(){
      doShareCancel()
    }
  })

   // 分享到朋友圈
  wx.onMenuShareTimeline({
    title:title,// 分享标题
    link:link,// 分享链接
    imgUrl:imgUrl,// 分享图标
    success:function(){
      doShareDone()
    },
    cancel:function(){
       doShareCancel()
    }
  })

  // 分享给qq
  wx.onMenuShareQQ({
    title:title,// 分享标题
    desc:desc,// 分享描述
    link:link,// 分享链接
    imgUrl:imgUrl,// 分享图标
    success: function(){
      doShareDone()
    },
    cancel:function(){
      doShareCancel()
    }
  })

  //qq空间
  wx.onMenuShareQQ({
    title:title,// 分享标题
    link:link,// 分享链接
    imgUrl:imgUrl,// 分享图标
    success:function(){
      doShareDone()
    },
    cancel:function(){
       doShareCancel()
    }
  });
}
function doShareDone() {
  console.log('分享成功')
}

function doShareCancel() {
  console.log('取消了分享')
}




