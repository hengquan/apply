var _url = location.href.split('#')[0];

$(function(){
   weixinShare()
});
function signUp() {
  window.location.href="sign.html";
}
$.ajax({
    type : "GET",
    url : "../api/getWxData",
    dataType : "json",
    data : {
      url : "http://ws.ruikj.cn/apply/page/index.html"
    },
    success : function(data) {
      var obj = data.data;
      wxLoading(obj);
    }
  })

// method 判断是否为微信浏览器
let isWeixin = () => {
  let ua = window.navigator.userAgent.toLowerCase();
  return /micromessenger/.test(ua)
}
function wxLoading(obj) {
  wx.config({
    debug : false,// 是否开启调试模式
    appId : obj.appId,//appid
    timestamp : obj.timestamp,// 时间戳
    nonceStr : obj.noncestr,// 随机字符串
    signature : obj.signature,// 签名
    jsApiList : [ 'onMenuShareTimeline', 'onMenuShareAppMessage',
        'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone' ]
  // 需要使用的JS接口列表
  })

  wx.ready(function() {
    // 分享给好友
    wx.onMenuShareAppMessage({
      title : "同方威视",// 分享标题
      desc : "高能产品本部2019年您产品推介会",// 分享描述
      link : "http://ws.ruikj.cn/apply/page/index.html",// 分享链接
      imgUrl : "http://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRt8Qia4lv7k3M9J1SKqKCImxJCt7j9rHYicKDI45jRPBxdzdyREWnk0ia0N5TMnMfth7SdxtzMvVgXg/0",// 分享图标
      success : function() {
        doShareDone()
      },
      cancel : function() {
        doShareCancel()
      }
    })
    // 分享到朋友圈
    wx.onMenuShareTimeline({
      title : "测试分享标题",// 分享标题
      desc : "这是描述",// 分享描述
      link : "http://ws.ruikj.cn/apply/page/test.html",// 分享链接
      imgUrl : "http://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRt8Qia4lv7k3M9J1SKqKCImxJCt7j9rHYicKDI45jRPBxdzdyREWnk0ia0N5TMnMfth7SdxtzMvVgXg/0",// 分享图标
      success : function() {
        doShareDone()
      },
      cancel : function() {
        doShareCancel()
      }
    })
  })
}

// 分享成功回调
function doShareDone() {
  console.log('分享成功')
}
// 取消分享回调
function doShareCancel() {
  console.log('取消了分享')
}



