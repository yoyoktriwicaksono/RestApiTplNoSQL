/**
 * Created by Yoyok_T on 28/09/2018.
 */
$(document).ready(function() {
    $('#getTest').click(function(){
        $.get("/api/v1/test/gettest",null,function(result){
            $('#msg').html(result.name);
        })

    });
});