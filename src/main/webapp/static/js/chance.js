(function($) {
    $.imageFileVisible = function(options) {
        // 默认选项
        var defaults = {
//包裹图片的元素
            wrapSelector: null,
//<input type=file />元素
            fileSelector:  null ,
            width : '100%',
            height: 'auto',
            errorMessage: "不是图片"
        };
        // Extend our default options with those provided.
        var opts = $.extend(defaults, options);
        $(opts.fileSelector).on("change",function(){
            var file = this.files[0];
            var imageType = /image.*/;
            if (file.type.match(imageType)) {
                var reader = new FileReader();
                reader.onload = function(){
                    var img = new Image();
                    img.src = reader.result;
                    $(img).width( opts.width);
                    $(img).height( opts.height);
                    $( opts.wrapSelector ).append(img);
                };
                reader.readAsDataURL(file);
            }else{
                alert(opts.errorMessage);
            }
        });
    };
})(jQuery);
// 引用
$(document).ready(function(){
//图片显示插件
    $.imageFileVisible({wrapSelector: "#image-wrap",
        fileSelector: "#file",
        width: 100,
        height: 100
    });
    $.imageFileVisible({wrapSelector: "#image-wrap2",
        fileSelector: "#file2",
        width: 100,
        height: 100
    });
    $.imageFileVisible({wrapSelector: "#image-wrap3",
        fileSelector: "#file3",
        width: 100,
        height: 100
    });
});
// 2019/5/29  臧紫薇
