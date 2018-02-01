function onCreate(savedInstanceState){
 setContentView("main")
 var btn = findViewById("btn")
 btn.setOnClickListener(function(view){
 android.widget.Toast.makeText(activity,"Button!",1).show();
 })
}
function onKeyDown(keycode,event){
 return activity.Msuper("onKeyDown",keycode,event);
}