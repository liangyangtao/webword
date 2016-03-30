	
(function($) { 
 
 $.fn.posfix=function(options){
	  
     var defaults = {
		 effect:"fixtop",
		 scrolltop:0,
		 time:400	
     }
     var options = $.extend(defaults, options);

	 return this.each(function(){
		
	   var self=$(this); 
		 
	   switch(options.effect){
		  case "fixtop": fixtop(self);break;
		  case "center": center(self);break;
		  case "left": left(self);break;
		  default: 
	   }
	   
	   function fixtop(self){
			var loaded = true; 
            var top = self.offset().top; 
            function Add_Data() 
            { 
                var scrolla=$(window).scrollTop(); 
                var cha=parseInt(top)-parseInt( scrolla); 
                if(loaded && cha<=options.scrolltop) 
                { 
				self.css({"position":"fixed", "top":-top+options.scrolltop});
                self.html("我已经固定了。"); 
				//self.text(top);
                loaded=false; 
                } 
                if(!loaded && cha>options.scrolltop) 
                { 
				self.css({"position":"absolute", "top":0});
                self.html("我还没固定了。"); 
                loaded=true; 
                } 
            } 
            $(window).scroll(Add_Data);	 
	   }
	   
	   function center(self){
		   var mwidth=self.width();
		   var mheight=self.height();
		   var outerwidth=self.outerWidth();
		   var outerheight=self.outerHeight();
		   var leftpy=outerwidth-mwidth;
		   var toppy=outerheight-mheight;
		   var bl=outerwidth/outerheight;
		   var init=true;
           self.css({"position":"absolute"});	
		   self.find("img").css({"width":mwidth, "height":mheight});	
	
		   function fixresize(){
			   if($(window).width()<=$(window).height()+((outerwidth-outerheight)*0.7)){
				 var width=($(window).width()*0.9)<outerwidth?$(window).width()*0.9:outerwidth;
				 var height=width/bl;
				 var left=($(window).width()-width)/2;
			     var top=($(window).height()-height)/2 + $(document).scrollTop();
				 self.stop().animate({"width":width-leftpy, "height":height-toppy, "left": left, "top": top},10);
				 self.find("img").stop().animate({"width":width-leftpy, "height":height-toppy, "left": left, "top": top},options.time);
			   }
			   else{
				 var height=($(window).height()*0.9)<outerheight?$(window).height()*0.9:outerheight;
				 var width=height*bl;
				 var left=($(window).width()-width)/2;
			     var top=($(window).height()-height)/2 + $(document).scrollTop();
				 self.stop().animate({"width":width-leftpy, "height":height-toppy, "left": left, "top": top},10);
				 self.find("img").stop().animate({"width":width-leftpy, "height":height-toppy, "left": left, "top": top},options.time);
			   }
	 
		    }
		
		    $(window).ready(function() {
			    self.css({"top":top-mheight*0.2});
			    fixresize();
            });
			
	        $(window).scroll(function(){
			    var top=($(window).height() - self.outerHeight())/2 + $(document).scrollTop();
			    if(init){
				  self.css({"top":top-mheight*0.2});
				  fixresize();
			      init=false;
			    }
			    else{
				  self.stop(true,false).animate({top: top},370);
			   }
		    });
			
		    $(window).resize(function(){		
                fixresize();
		    }) 
	        
	    }
		
		function left(self){
            var top=options.scrolltop;
		    var init=true;
            self.css({"position":"absolute"});	
		   
		    $(window).ready(function() {
			    self.css({"top":ntop});
            });
			
			$(window).scroll(function(){
			    if(init){
				  self.css({"top":$(document).scrollTop()+top});
			      init=false;
			    }
			    else{
				  self.stop(true,false).animate({top:$(document).scrollTop()+top},600);
			   }
		    });
		}
		
		})
		
	 }
	 
})( jQuery );
