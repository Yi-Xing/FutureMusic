var url = window.location.search;
var musicId = url.substring(url.lastIndexOf('=') + 1, url.length);
console.log('音乐ID='+musicId);
