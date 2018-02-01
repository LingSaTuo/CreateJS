libs_inthis.useEZ();
var txt = Jsoup.connect("http://www.baidu.com").get();
libs_inthis.sendMessage("",txt.toString());
