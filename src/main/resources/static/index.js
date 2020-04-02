$(function () {

    Date.prototype.format = function () {
        var y = this.getFullYear(); 
        var M = this.getMonth() + 1; 
        var d = this.getDate(); 
        var h = this.getHours(); 
        var m = this.getMinutes();
        M=M<10?'0'+M:M
        m=m<10?'0'+m:m
        fmt = y + '-' + M + '-' + d + ' ' + h + ':' + m;
        return fmt;
    }
    function hide() {
        $('#freed').hide()
        $('#youhuad').hide()
        $('#youhua1d').hide()
        $('#yhloading').hide()
    }
    var ajaxGet =null
    // 免费获取展示
    $('.freeget').click(function () {
        hide()
        $('.dialog-wrap').show()
        $('#freed').show()

    })
    $('#freed button').click(function () {
        $('.dialog-wrap').hide()
        hide()
        var obj = {
            phone: $('#phone').val(),
            name: $('#textarea').val(),
            province:$('#province').val(),
            city:$('#city').val(),
            sid:$('#saleid').val(),
            rftel:$('#rftel').val(),
            referrer:$('#referrer').val()
        }
        $.get('/api/submit', obj, function (res) {
 
                alert('提交成功')
      
            
        })
    })
    $('#getfraction').click(function () {
        var urlweb = $('.input-wrap input').val()

        if (urlweb.length == 0) {
            alert('请输入网址')
            return
        }
        hide()
        $('.dialog-wrap').show()
        $('#yhloading').show()
        ajaxGet =$.get('/api/check', { url: urlweb }, function (res) {
            $('.yh-time').text("测试时间：" + new Date().format())
            if (res.score >= 60) {
                $(".dialog").addClass("active0")
                $('#youhuad').show()
            } else {
                $(".dialog").addClass("active60")
                $('#youhua1d').show()
            }
            $('#yhloading').hide()
        })
    })
    $('.dialog').click(function (e) {
        e.stopImmediatePropagation();
        return false
    })
    $('.dialog-wrap').click(function (e) {
        if(ajaxGet){
            ajaxGet.abort()
        }
        e.stopImmediatePropagation();
        hide()
        $(this).hide()
        return false
    })
    $('#call_sale').click(function () {
        window.location.href="tel:"+$("#tel").val();
        return false
    })


        //滚动条
        window.onload = roll(200);

        function roll(t) {
            var ul1 = document.getElementById("comment1");
            var ul2 = document.getElementById("comment2");
            var ulbox = document.getElementById("review_box");
            ul2.innerHTML = ul1.innerHTML;
            ulbox.scrollTop = 0; // 开始无滚动时设为0
            var timer = setInterval(rollStart, t); // 设置定时器，参数t用在这为间隔时间（单位毫秒），参数t越小，滚动速度越快
            // 鼠标移入div时暂停滚动
            ulbox.onmouseover = function () {
                clearInterval(timer);
            }
            // 鼠标移出div后继续滚动
            ulbox.onmouseout = function () {
                timer = setInterval(rollStart, t);
            }
        }
        
        // 开始滚动函数
        function rollStart() {
            // 上面声明的DOM对象为局部对象需要再次声明
            var ul1 = document.getElementById("comment1");
            var ul2 = document.getElementById("comment2");
            var ulbox = document.getElementById("review_box");
            // 正常滚动不断给scrollTop的值+1,当滚动高度大于列表内容高度时恢复为0
            if (ulbox.scrollTop >= ul1.scrollHeight) {
                ulbox.scrollTop = 0;
            } else {
                ulbox.scrollTop++;
            }
        }

})


//地区
    var city=new Array();
   city["北京市"]=["东城区","西城区", "崇文区", "宣武区","朝阳区","海淀区","丰台区","石景山区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷 区","门头沟区","密云区","延庆区","其他"];
   city["广东省"]=["广州","深圳", "珠海","汕头","韶关","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远","东 莞","中山","潮州","揭阳","云浮","其他"];
   city["上海市"]=["黄浦区","卢湾区", "徐汇区","长宁区","静安区","普陀区","闸北区","虹口区","杨浦区","宝山区","闵行区","嘉定区","松江区","金山 区","青浦区","南汇区","奉贤区","浦东新区","崇明区","其他"];
   city["天津市"]=["和平区","河东区", "河西区","南开区","河北区","红桥区","塘沽区","汉沽区","大港区","东丽区","西青区","北辰区","津南区","武清 区","宝坻区","静海县","宁河县","蓟县","其他"];
   city["重庆市"]=["渝中区","大渡口区", "江北区","南岸区","北碚区","渝北区","巴南区","长寿区","双桥区","沙坪坝区","万盛区","万州区","涪陵区","黔江 区","永川区","合川区","江津区","九龙坡区","南川区","綦江县","潼南区","荣昌区","璧山区","大足区","铜梁县","梁 平县","开县","忠县","城口县","垫江区","武隆县","丰都县","奉节县","云阳县","巫溪县","巫山县","其他"];
   city["辽宁省"]=["沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛","其他"];
   city["江苏省"]=["南京","苏州","无锡","常州","镇江","南通","泰州","扬州","盐城","连云港","徐州","淮安","宿州","其他"];
   city["湖北省"]=["武汉","黄石","十堰","荆州","宜昌","襄樊","鄂州","荆门","孝感","黄冈","咸宁","随州","仙桃","天门","潜江","神农架","其他"];
   city["四川省"]=["成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","眉山","宜宾","广安","达州","雅安","巴中","资阳","其他"];
   city["陕西省"]=["西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林","安康","商洛","其他"];
   city["河北省"]=["石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水","其他"];
   city["山西省"]=["太原","大同","阳泉","长治","晋城","朔州","晋中","运城","忻州","临汾","吕梁","其他"];
   city["河南省"]=["郑州","开封","洛阳","平顶山","安阳","鹤壁","新乡","焦作","濮阳","许昌","漯河","三门峡","南阳","商丘","信阳","周口","驻马店","焦作","其他"];
   city["吉林省"]=["吉林","四平","辽源","通化","白山","松原","白城","延边朝鲜自治区","其他"];
   city["黑龙江"]=["哈尔滨","齐齐哈尔","鹤岗","双鸭山","鸡西","大庆","伊春","牡丹江","佳木斯","七台河","黑河","绥远","大兴安岭地区","其他"];
   city["内蒙古"]=["呼和浩特","包头","乌海","赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布","锡林郭勒盟","兴安盟","阿拉善盟"],
   city["山东省"]=["济南","青岛","淄博","枣庄","东营","烟台","潍坊","济宁","泰安","威海","日照","莱芜","临沂","德州","聊城","滨州","菏泽","其他"];
   city["安徽省"]=["合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安","亳州","池州","宣城"],
   city["浙江省"]=["杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州","丽水","其他"];
   city["福建省"]=["福州","厦门","莆田","三明","泉州","漳州","南平","龙岩","宁德","其他"];
   city["湖南省"]=["长沙","株洲","湘潭","衡阳","邵阳","岳阳","常德","张家界","益阳","滨州","永州","怀化","娄底","其他"];
   city["广西省"]=["南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林","百色","贺州","河池","来宾","崇左","其他"];
   city["江西省"]=["南昌","景德镇","萍乡","九江","新余","鹰潭","赣州","吉安","宜春","抚州","上饶","其他"];
   city["贵州省"]=["贵阳","六盘水","遵义","安顺","铜仁","毕节","其他"];
   city["云南省"]=["昆明","曲靖","玉溪","保山","邵通","丽江","普洱","临沧","其他"];
   city["西藏"]=["拉萨","那曲地区","昌都地区","林芝地区","山南区","阿里区","日喀则","其他"];
   city["海南省"]=["海口","三亚","五指山","琼海","儋州","文昌","万宁","东方","澄迈县","定安县","屯昌县","临高县","其他"];
   city["宁夏"]=["兰州","嘉峪关","金昌","白银","天水","武威","酒泉","张掖","庆阳","平凉","定西","陇南","临夏","甘南","其他"];
   city["甘肃省"]=["银川","石嘴山","吴忠","固原","中卫","其他"];
   city["青海"]=["西宁","海东地区","海北藏族自治区","海南藏族自治区","黄南藏族自治区","果洛藏族自治区","玉树藏族自治州","还西藏族自治区","其他"];
   city["新疆"]=["乌鲁木齐","克拉玛依","吐鲁番地区","哈密地区","和田地区","阿克苏地区","喀什地区","克孜勒苏柯尔克 孜","巴音郭楞蒙古自治区","昌吉回族自治州","博尔塔拉蒙古自治区","石河子","阿拉尔","图木舒克","五家渠","伊犁哈萨克自治 区","其他"];
    function getId(id){
      return document.getElementById(id);
    }
    function hidep(v){
      //先将城市循环调用出来
      var innerhtml="";
      for(var city_name in city){
         innerhtml += "<span onclick=\"pickp('"+city_name+"')\">"+city_name+"</span>";
      }
      getId("province_input").innerHTML = innerhtml;
      //如果input的字被换成了#FF0000，则再换回#788F72
      if(getId("province").style.color == "red"){
         getId("province").style.color = "#788F72";
      }   
      getId(v).style.display = getId(v).style.display == "none" ? "" : "none";
    }
    function hidec(v){
      if(getId("province").value!="请选择"){//如果先点击城市下拉框，则将省份的input的字被换成了#FF0000
         getId(v).style.display = getId(v).style.display == "none" ? "" : "none";
      }else{
         getId("province").style.color = "red";
      }
    }
    function pickp(v) {
      // 改变城市下拉框的函数
      getId("province").value=v;
      hidep('province_input');  
      var province_city = city[getId("province").value];
      var innerhtml="";
      for ( i = 0 ; i < province_city.length; i++ ) {
         innerhtml += "<span onclick=\"pickc('"+province_city[i]+"')\">"+province_city[i]+"</span>";
      }
       getId("city").value = "请选择";
       getId("city_input").innerHTML = innerhtml;
    }
    function pickc(v) {
      getId("city").value=v;
      hidec('city_input');
    }


