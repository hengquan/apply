var _url = location.href.split('#')[0];

$(function(){
  // 加载中
  var c = 0, w = 100;

  // var mainfest = [
  // { src: "images/bg_fdj.png" },
  // { src: "images/loading-bg.png" },
  // { src: "images/loading-car.png" },
  // { src: "images/loading-car0.png" },
  // { src: "images/loading-door.png" },
  // { src: "images/loading-line.png" },
  // { src: "images/loading-zi.png" },
  // { src: "images/page-wangge.png" },
  // { src: "images/page03a.jpg" },
  // { src: "images/page1-bg.png" },
  // { src: "images/page3-bg.png" },
  // { src: "images/page3-btn.png" },
  // { src: "images/page3-logo.png" },
  // { src: "images/page3-logo1.png" },
  // { src: "images/page3-phone.png" },
  // ];
   
  // var preload = {
  //   // 预加载函数
  //   startPreload: function () {
  //     var preload = new createjs.LoadQueue(true);
  //     //为preloaded添加整个队列变化时展示的进度事件
  //     preload.addEventListener("progress", this.handleFileProgress);
  //     //注意加载音频文件需要调用如下代码行
  //     preload.installPlugin(createjs.SOUND);
  //     //为preloaded添加当队列完成全部加载后触发事件
  //     preload.addEventListener("complete", this.loadComplete);
  //     //设置最大并发连接数  最大值为10
  //     preload.setMaxConnections(1);
  //     preload.loadManifest(mainfest);
  //   },
  //   // 当整个队列变化时展示的进度事件的处理函数
  //   handleFileProgress: function (event) {
  //     c = Math.ceil(event.loaded * 100)
  //     console.log('c-----: ', c)
  //     $(".loading-page .counter .line").css("width", c + "%");
  //   },
  //   // 处理preload添加当队列完成全部加载后触发事件
  //   loadComplete: function () {
      
  //     // mySwiper.slideTo(2, 1000, false);
  //     window.location.href='page1.html';
  //   }
  // }
  // preload.startPreload();


$(document).ready(function() {

  var counter = 0;
  var c = 0;
  var i = setInterval(function() {
    $(".loading-page .counter .line").css("width", c + "%");
    counter++;
    c++;

    if (counter == 101) {
      clearInterval(i);
       window.location.href='page1.html';
    }
  }, 50);
});

});

